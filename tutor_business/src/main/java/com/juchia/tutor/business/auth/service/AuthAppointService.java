package com.juchia.tutor.business.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.api.pay.bo.CashinBO;
import com.juchia.tutor.api.pay.bo.CloseBizContent;
import com.juchia.tutor.api.pay.bo.PayBizContent;
import com.juchia.tutor.api.pay.bo.RefundBizContent;
import com.juchia.tutor.api.pay.client.IFeignAliPay;
import com.juchia.tutor.api.pay.client.IFeignCashin;
import com.juchia.tutor.api.pay.vo.CloseResponse;
import com.juchia.tutor.api.pay.vo.RefundResponse;
import com.juchia.tutor.business.auth.entity.query.AppointQuery1;
import com.juchia.tutor.business.common.config.RabbitConfig;
import com.juchia.tutor.business.common.entity.po.*;
import com.juchia.tutor.business.common.exception.BusinessException;
import com.juchia.tutor.business.common.mapper.*;
import com.juchia.tutor.common.entity.query.PageQuery;
import com.juchia.tutor.common.entity.vo.MyPage;
import com.rabbitmq.client.Channel;
import com.tuyang.beanutils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * <p>
 * 老师预约表 服务实现类
 * </p>
 *
 * @author juchia
 * @since 2020-01-27
 */
@Service
@Slf4j
public class AuthAppointService extends ServiceImpl<AppointMapper, Appoint> {

    @Autowired
    NeedMapper needMapper;

    @Autowired
    IFeignAliPay iFeignAliPay;

    @Autowired
    TeacherMapper teacherMapper;

    @Autowired
    IFeignCashin iFeignCashin;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    AreaMapper areaMapper;

    @Autowired
    MessageMapper messageMapper;

//   教员端新添预约
    public Appoint saveAppointForTeacher(Long id, Long needId) {

//      检测老师是否审核通过
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.eq("user_id",id);
        Teacher teacher = teacherMapper.selectOne(teacherQueryWrapper);
        if(teacher==null){
            throw new BusinessException("教师不存在");
        }
        if(teacher.getState().compareTo(3)!=0){ //1已提交 2失败 3通过
            throw new BusinessException("教师审核未通过,禁止预约");
        }


//      检测是否已经预约过
        Appoint condition = new Appoint();
        condition.setNeedId(needId);
        condition.setTeacherId(id);
        Appoint appoint = getBaseMapper().selectOne(new QueryWrapper<>(condition));
        if(appoint!=null){
            throw new BusinessException("重复预约");
        }

//      检测需求状态是否是允许预约的状态
        Need need = needMapper.selectById(needId);
        if(need==null){
            throw new BusinessException("非法请求,需求不存在");
        }
        if(need.getState().compareTo(3)!=0){ //只有状态在3(审核通过时才能预约)
            throw new BusinessException("状态错误,此需求不允许预约");
        }


//       保存一条预约记录
        Appoint saving = new Appoint();
        saving.setNeedId(needId);
        saving.setStudentId(need.getStudentId());
        saving.setTeacherId(id);

        saving.setStudentCommentState(0); //学生未评论
        saving.setTeacherCommentState(0); //老师未评论

        saving.setState(1); //状态1 已预约

        saving.setCreateTime( LocalDateTime.now());

        saving.setFrequency(need.getFrequency());
        saving.setTimeHour(need.getTimeHour());
        saving.setHourPrice(need.getHourPrice());
        saving.setTotalPrice(need.getTotalPrice());
        saving.setNickname(need.getNickname());
        saving.setPhone(need.getPhone());
        saving.setWechat(need.getWechat());
        saving.setQq(need.getQq());

        Area city = areaMapper.selectById(need.getCityId());
        Area area = areaMapper.selectById(need.getAreaId());
        saving.setAddress(city.getName()+" "+area.getName()+" "+need.getAddress());

        Category category1 = categoryMapper.selectById(need.getFirstCategoryId());
        Category category2 = categoryMapper.selectById(need.getSecondCategoryId());
        Category category3 = categoryMapper.selectById(need.getThirdCategoryId());
        saving.setSubject(category1.getName()+" "+category2.getName()+" "+category3.getName());

        getBaseMapper().insert(saving);


//      需求预约人数加1
        need.setTotalAppoint(need.getTotalAppoint()+1);
        needMapper.updateById(need);
        return null;
    }

//   教员端分页获取我的预约
    public MyPage<Appoint> myPage(Long id, PageQuery pageQuery, AppointQuery1 appointQuery) {
        //      1 将我们的分页对象转换成mybatis plus 的
        Page<Appoint> page = new Page<>();
        page.setSize(pageQuery.getSize());
        page.setCurrent(pageQuery.getCurrent());

//      2根据我们的Query对象构建QueryWrapper
        QueryWrapper<Appoint> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id",id); //userId
        queryWrapper.eq("teacher_delete_state",0); //未软删除的
        //queryWrapper.ne("state",2); //排除掉已中标
        if(appointQuery.getState()!=null){
            queryWrapper.eq("state",appointQuery.getState());
        }
//        //TODO 评论状态
//        if(appointQuery.getTeacherCommentState()!=null){
//            queryWrapper.eq("teacher_comment_state",appointQuery.getTeacherCommentState());
//        }

//        3调用Mapper
        IPage<Appoint> iPage = getBaseMapper().selectPage(page, queryWrapper);

//        将mybatis plus 分页对象转换成我们的
        MyPage<Appoint> myPage = BeanCopyUtils.copyBean(iPage, MyPage.class);
        myPage.setPages(iPage.getPages());//mybatis plus 没有这个属性,而是直接get方法
        return myPage;
    }

//  教员端取消预约
    @Transactional
    public Appoint cancelAppointForTeacher(Long id, Long appointId) {
//        判断是否有权限
        Appoint appoint = getBaseMapper().selectById(appointId);
        if(appoint==null){
            throw new BusinessException("appointId不存在");
        }
        if(appoint.getTeacherId().compareTo(id)!=0){
            throw new BusinessException("无权限");
        }

//        判断状态是否满足
        if(appoint.getState().compareTo(1)!=0){ //只能是已预约状态可以取消预约
            throw new BusinessException("状态错误");
        }
        appoint.setState(10); //已关闭
        appoint.setStudentDeleteState(1); //隐藏学生订单
        appoint.setReason("教员主动取消预约");
        getBaseMapper().updateById(appoint);

        Need need = needMapper.selectById(appoint.getNeedId());
        need.setTotalAppoint(need.getTotalAppoint()-1); //预约人数减1
        needMapper.updateById(need);

        return appoint;
    }

//   教员端提出结课
    public Appoint endCourseAppointForTeacher(Long id, Long appointId) {
        //        判断是否有权限
        Appoint appoint = getBaseMapper().selectById(appointId);
        if(appoint==null){
            throw new BusinessException("appointId不存在");
        }
        if(appoint.getTeacherId().compareTo(id)!=0){
            throw new BusinessException("无权限");
        }
//        判断状态是否满足
        if(appoint.getState().compareTo(4)!=0){ //进行中才能结课
            throw new BusinessException("状态错误");
        }
        appoint.setState(5); //待结课
        getBaseMapper().updateById(appoint);

        return appoint;
    }

//  教员端维权
    @Transactional
    public Appoint rightAppointForTeacher(Long id, Long appointId) {
        //        判断是否有权限
        Appoint appoint = getBaseMapper().selectById(appointId);
        if(appoint==null){
            throw new BusinessException("appointId不存在");
        }
        if(appoint.getTeacherId().compareTo(id)!=0){
            throw new BusinessException("无权限");
        }
//        判断状态是否满足
        if(appoint.getState().compareTo(4)!=0 && appoint.getState().compareTo(5)!=0 && appoint.getState().compareTo(6)!=0){ //待关闭, 待结课, 进行中才能维权
            throw new BusinessException("状态错误");
        }
        appoint.setState(9); //已维权
        getBaseMapper().updateById(appoint);

//      同步更新需求状态
        Need need = needMapper.selectById(appoint.getNeedId());
        need.setState(6); //已关闭
        need.setReason("教员维权");
        needMapper.updateById(need);

        return appoint;
    }

//   教员端同意关闭
    @Transactional
    public Appoint agreeCloseAppointForTeacher(Long id, Long appointId) {
        //        判断是否有权限
        Appoint appoint = getBaseMapper().selectById(appointId);
        if(appoint==null){
            throw new BusinessException("appointId不存在");
        }
        if(appoint.getTeacherId().compareTo(id)!=0){
            throw new BusinessException("无权限");
        }
//        判断状态是否满足
        if(appoint.getState().compareTo(6)!=0){ //待关闭
            throw new BusinessException("状态错误");
        }

//        退款给学员
        RefundBizContent refundBizContent = new RefundBizContent();
        refundBizContent.setOutTradeNo(appoint.getTradeNo());
        refundBizContent.setRefundAmount(appoint.getTotalPrice());
        refundBizContent.setRefundReason("学员提出关闭后教员同意关闭");
        RefundResponse refundResponse;
        try {
            refundResponse = iFeignAliPay.refund(refundBizContent);
        } catch (Exception e) {
            throw new BusinessException("支付宝退款异常");
        }
        if(!refundResponse.getAlipayTradeRefundResponse().getCode().equals("10000")){
            throw new BusinessException(refundResponse.getAlipayTradeRefundResponse().getSubMsg());
        }

//       修改预约状态
        appoint.setState(10); //已关闭
        appoint.setReason("学生提出关闭后教师同意关闭");
        getBaseMapper().updateById(appoint);

//        同步修改需求状态
        Need need = needMapper.selectById(appoint.getNeedId());
        need.setState(6); //已关闭
        need.setReason("学生提出关闭后老师同意关闭");
        needMapper.updateById(need);

        return appoint;
    }

//  教员端拒绝关闭
    public Appoint rejectCloseAppointForTeacher(Long id, Long appointId) {
//        判断是否有权限
        Appoint appoint = getBaseMapper().selectById(appointId);
        if(appoint==null){
            throw new BusinessException("appointId不存在");
        }
        if(appoint.getTeacherId().compareTo(id)!=0){
            throw new BusinessException("无权限");
        }

//        判断状态是否满足
        if(appoint.getState().compareTo(6)!=0){ //待关闭
            throw new BusinessException("状态错误");
        }

//
        appoint.setState(4); //进行中
        getBaseMapper().updateById(appoint);

        return appoint;
    }

//  教员端软删除
    public Appoint deleteAppointForTeacher(Long id, Long appointId) {
        //        判断是否有权限
        Appoint appoint = getBaseMapper().selectById(appointId);
        if(appoint==null){
            throw new BusinessException("appointId不存在");
        }
        if(appoint.getTeacherId().compareTo(id)!=0){
            throw new BusinessException("无权限");
        }
//        判断状态是否满足
        if(appoint.getState().compareTo(7)!=0 && appoint.getState().compareTo(9)!=0 && appoint.getState().compareTo(10)!=0){ // 已完成 已维权 已关闭 的才能删除
            throw new BusinessException("状态错误");
        }
        if(appoint.getTeacherCommentState().compareTo(1)!=0){ // 评论后才能删除
            throw new BusinessException("请评论后删除");
        }

//       软删除
        appoint.setTeacherDeleteState(1); //0 未删除 1已删除
        getBaseMapper().updateById(appoint);

        return appoint;
    }


//    ------------------------------------------------------------------------------

//  学员端获取支付宝跳转地址
    @Transactional
    public String getPayRedirectUrl(Long id, Long appointId) {

//        验证是否有权限
        Appoint appoint = getBaseMapper().selectById(appointId);
        if(appoint==null){
            throw new BusinessException("appointId不存在");
        }
        if(appoint.getStudentId().compareTo(id)!=0){
            throw new BusinessException("无权操作");
        }

//        验证此 appoint 的状态是否为待付款状态
        if(appoint.getState().compareTo(2)!=0){
            throw new BusinessException("状态异常");
        }


//        PayInfoBO payInfoBO = new PayInfoBO();
//        payInfoBO.setAppointId(appoint.getId());
//        payInfoBO.setUserId(id);
//        payInfoBO.setToId(appoint.getTeacherId());
//        payInfoBO.setType(1); //支付类型, 目前写死 只支持支付宝
//        payInfoBO.setState(1); //订单状态 1未支付
//        payInfoBO.setTradeNo(appoint.getTradeNo()); //订单号
//        payInfoBO.setTotalAmount(appoint.getTotalPrice()); //支付金额
//        payInfoBO.setName("闽师家教" + appoint.getNickname() +"上门支教"); //订单名称
//        payInfoBO.setTimeoutExpress("1m"); //该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m
//        payInfoBO.setCteateTime(LocalDateTime.now());

//      创建支付宝支付订单
        PayBizContent payBizContent = new PayBizContent();
        payBizContent.setOutTradeNo(appoint.getTradeNo()); //订单号
        payBizContent.setTotalAmount(appoint.getTotalPrice()); //金额
        payBizContent.setSubject("闽师家教" + appoint.getNickname() +"上门支教"); //订单名称
        //payBizContent.setTimeoutExpress("10m"); //该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m
//    body 可选 订单描述
//    private String body;
//    time_expire 可选 订单绝对超时时间 格式为yyyy-MM-dd HH:mm:ss	2016-12-31 10:05:01
//    private LocalDateTime timeExpire;


        String url=null;
        try {
            url =  iFeignAliPay.pay(payBizContent); //TODO 未判断跳转地址是否正确
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return url;
    }

    /**
     * 15分钟订单超时 关闭订单监听处理
     */
    @RabbitHandler // 声明接收方法
    @RabbitListener(queues = {RabbitConfig.QUEUE_DLX}) //声明监听死信队列
    @Transactional
    public void payCloseProcess(Message message, Channel channel,PayBizContent payBizContent) throws IOException {
//        PayInfoBO payInfoBO = (PayInfoBO) message.getBody(); //也可以直接用 @Payload 注解直接获取body
        log.info("接收处理DLX队列当中的消息: {}, 当前时间: {}", payBizContent, LocalDateTime.now().toString());
        try {
//              消息的标识，false只确认当前一个消息收到，true确认consumer获得的所有消息
//              channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//              ack返回false，并重新回到队列
//              channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
//              拒绝消息
//              channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
//
            Appoint condiion = new Appoint();
            condiion.setTradeNo(payBizContent.getOutTradeNo());
            Appoint appoint = getBaseMapper().selectOne(new QueryWrapper<>(condiion));
            if(appoint.getState().compareTo(2)==0){
                appoint.setState(10);
                appoint.setReason("支付超时关闭");
                getBaseMapper().updateById(appoint);

                Need need = needMapper.selectById(appoint.getNeedId());
                need.setState(6);
                need.setReason("支付超时关闭");
                needMapper.updateById(need);
            }

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            // TODO 如果报错了,那么我们可以进行容错处理,比如转移当前消息进入其它队列
            // 处理消息失败，将消息重新放回队列
            try {
//                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }


    /**
     * 支付成功监听处理
     */
    @RabbitHandler // 声明接收方法
    @RabbitListener(queues = {RabbitConfig.QUEUE_PAY_SUCCESS}) //声明监听队列
    @Transactional
    public void paySuccessProcess(Message message, Channel channel,PayBizContent payBizContent){
//        String AppointId = new String(message.getBody()); //也可以直接用 @Payload 注解直接获取body
        log.info("接收处理DLX队列当中的消息: {}, 当前时间: {}", payBizContent, LocalDateTime.now().toString());
        try {
//              消息的标识，false只确认当前一个消息收到，true确认consumer获得的所有消息
//              channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//              ack返回false，并重新回到队列
//              channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
//              拒绝消息
//              channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
            Appoint condiion = new Appoint();
            condiion.setTradeNo(payBizContent.getOutTradeNo());
            Appoint appoint = getBaseMapper().selectOne(new QueryWrapper<>(condiion));
            if(appoint.getState().compareTo(2)==0){ //待支付状态才改订单
                appoint.setState(3); //已支付，试教中
                getBaseMapper().updateById(appoint);

                //      发送消息
                com.juchia.tutor.business.common.entity.po.Message message2 = new com.juchia.tutor.business.common.entity.po.Message();
                message2.setToId(appoint.getTeacherId());
                message2.setType(1); //系统消息
                message2.setContent("你预约的家教："+ appoint.getNickname() +" "+ appoint.getSubject() + " " + appoint.getAddress()+" 已被家长选为家教老师并已付款，快上门试教吧！");
                messageMapper.insert(message2);
            }

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            // TODO 如果报错了,那么我们可以进行容错处理,比如转移当前消息进入其它队列
            // 处理消息失败，将消息重新放回队列
            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,true);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }


//    -----------------------------------------------------------------------------------


//  学生端分页
    public MyPage<Appoint> myStudentPage(Long id, PageQuery pageQuery, AppointQuery1 appointQuery) {
        //      1 将我们的分页对象转换成mybatis plus 的
        Page<Appoint> page = new Page<>();
        page.setSize(pageQuery.getSize());
        page.setCurrent(pageQuery.getCurrent());

//      2根据我们的Query对象构建QueryWrapper
        QueryWrapper<Appoint> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id",id);
        queryWrapper.ne("state",1); //排除掉已预约
        queryWrapper.eq("student_delete_state",0); //未软删除的
        if(appointQuery.getState()!=null){
            queryWrapper.eq("state",appointQuery.getState());
        }

////        TODO 这里有问题
//        if(appointQuery.getStudentCommentState()!=null){
//            queryWrapper.eq("student_comment_state",appointQuery.getStudentCommentState());
//        }

//       TODO 只有需求是进行中状态时才和appoint有关


//        3调用Mapper
        IPage<Appoint> iPage = getBaseMapper().selectPage(page, queryWrapper);

//        将mybatis plus 分页对象转换成我们的
        MyPage<Appoint> myPage = BeanCopyUtils.copyBean(iPage, MyPage.class);
        myPage.setPages(iPage.getPages());//mybatis plus 没有这个属性,而是直接get方法
        return myPage;
    }

//  关闭订单（取消付款）
    @Transactional
    public Appoint closeOrder(Long id, Long appointId){
        Appoint appoint = getBaseMapper().selectById(appointId);
        if(appoint==null){
            throw new BusinessException("appointId不存在");
        }
        if(appoint.getStudentId().compareTo(id)!=0){
            throw new BusinessException("无权操作");
        }
        if(appoint.getState().compareTo(2)!=0){ //只要待支付状态才能取消付款
            throw new BusinessException("状态错误");
        }

//      发送支付宝请求
        CloseBizContent closeBizContent = new CloseBizContent();
        closeBizContent.setOutTradeNo(appoint.getTradeNo());
        CloseResponse close;
        try {
             close = iFeignAliPay.close(closeBizContent);
        } catch (Exception e) {
            throw new BusinessException("支付宝关闭订单异常");
        }
        if(!close.getAlipayTradeCloseResponse().getCode().equals("10000")){
            throw new BusinessException(close.getAlipayTradeCloseResponse().getSubMsg());
        }

//      关闭订单
        appoint.setState(10);
        appoint.setReason("学员取消付款关闭");
        getBaseMapper().updateById(appoint);


//      同步更新需求
        Need need = needMapper.selectById(appoint.getNeedId());
        need.setState(6); //已关闭
        //need.setTotalAppoint(0L); //预约人数改为0
        need.setReason("学员取消付款关闭");
        needMapper.updateById(need);


        return appoint;

    }

//    试教通过
    public Appoint passOrder(Long id, Long appointId) {
        Appoint appoint = getBaseMapper().selectById(appointId);
        if(appoint==null){
            throw new BusinessException("appointId不存在");
        }
        if(appoint.getStudentId().compareTo(id)!=0){
            throw new BusinessException("无权操作");
        }
        if(appoint.getState().compareTo(3)!=0){ //试教中
            throw new BusinessException("状态错误");
        }
        appoint.setState(4); //进行中
        getBaseMapper().updateById(appoint);

        return appoint;
    }

//    试教不通过
    @Transactional
    public Appoint notPassOrder(Long id, Long appointId) {
        Appoint appoint = getBaseMapper().selectById(appointId);
        if(appoint==null){
            throw new BusinessException("appointId不存在");
        }
        if(appoint.getStudentId().compareTo(id)!=0){
            throw new BusinessException("无权操作");
        }
        if(appoint.getState().compareTo(3)!=0){ //试教中
            throw new BusinessException("状态错误");
        }


//      支付宝退款
        RefundBizContent refundBizContent = new RefundBizContent();
        refundBizContent.setOutTradeNo(appoint.getTradeNo());
        refundBizContent.setRefundAmount(appoint.getTotalPrice());
        refundBizContent.setRefundReason("教员试教不通过关");
        RefundResponse refundResponse;
        try {
            refundResponse = iFeignAliPay.refund(refundBizContent);
        } catch (Exception e) {
            throw new BusinessException("支付宝退款异常");
        }
        if(!refundResponse.getAlipayTradeRefundResponse().getCode().equals("10000")){
            throw new BusinessException(refundResponse.getAlipayTradeRefundResponse().getSubMsg());
        }


//      关闭订单
        appoint.setState(10);
        appoint.setReason("教员试教不通过关");
        getBaseMapper().updateById(appoint);


//      同步更新需求
        Need need = needMapper.selectById(appoint.getNeedId());
        need.setState(6); //已关闭
        //need.setTotalAppoint(0L); //预约人数改为0
        need.setReason("教员试教不通过关");
        needMapper.updateById(need);

        return appoint;
    }

//    主动结课
    @Transactional
    public Appoint endCourse(Long id, Long appointId) {
        Appoint appoint = getBaseMapper().selectById(appointId);
        if(appoint==null){
            throw new BusinessException("appointId不存在");
        }
        if(appoint.getStudentId().compareTo(id)!=0){
            throw new BusinessException("无权操作");
        }
        if(appoint.getState().compareTo(4)!=0){ //进行中
            throw new BusinessException("状态错误");
        }

//        更新订单
        appoint.setState(7); //已完成
        getBaseMapper().updateById(appoint);



        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",appoint.getTeacherId());
        Teacher teacher = teacherMapper.selectOne(queryWrapper);
//      更新老师余额
        teacher.setBalance(teacher.getBalance().add(appoint.getTotalPrice()));

//      更新老师成功次数
        teacher.setTotalSuccess(teacher.getTotalComment()+1);

        teacherMapper.updateById(teacher);

//      添加一条老师收入信息
        CashinBO cashinBO = new CashinBO();
        cashinBO.setAppointId(appointId);
        cashinBO.setNeedId(appoint.getNeedId());
        cashinBO.setUserId(appoint.getTeacherId());
        cashinBO.setTotalAmount(appoint.getTotalPrice());
        cashinBO.setName("上门家教："+appoint.getNickname());
        cashinBO.setDescription("家教地址："+appoint.getAddress());
        cashinBO.setBalance(teacher.getBalance());
        cashinBO.setCteateTime(LocalDateTime.now());
        iFeignCashin.saveCashin(cashinBO);

        return appoint;
    }

//    提出关闭
    public Appoint closeCourse(Long id, Long appointId) {
        Appoint appoint = getBaseMapper().selectById(appointId);
        if(appoint==null){
            throw new BusinessException("appointId不存在");
        }
        if(appoint.getStudentId().compareTo(id)!=0){
            throw new BusinessException("无权操作");
        }
        if(appoint.getState().compareTo(4)!=0){ //进行中
            throw new BusinessException("状态错误");
        }

        appoint.setState(6); //待关闭
        getBaseMapper().updateById(appoint);
        return appoint;
    }

//    同意结课
    @Transactional
    public Appoint agreeCloseCourse(Long id, Long appointId) {
        Appoint appoint = getBaseMapper().selectById(appointId);
        if(appoint==null){
            throw new BusinessException("appointId不存在");
        }
        if(appoint.getStudentId().compareTo(id)!=0){
            throw new BusinessException("无权操作");
        }
        if(appoint.getState().compareTo(5)!=0){ //待结课
            throw new BusinessException("状态错误");
        }

        appoint.setState(7); //已完成
        getBaseMapper().updateById(appoint);

//      修改老师信息
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",appoint.getTeacherId());
        Teacher teacher = teacherMapper.selectOne(queryWrapper);
//        更新老师余额
        teacher.setBalance(teacher.getBalance().add(appoint.getTotalPrice()));
//        更新老师成功次数
        teacher.setTotalSuccess(teacher.getTotalSuccess()+1L);
        teacherMapper.updateById(teacher);

//       添加一条老师收入信息
        CashinBO cashinBO = new CashinBO();
        cashinBO.setAppointId(appointId);
        cashinBO.setNeedId(appoint.getNeedId());
        cashinBO.setUserId(appoint.getTeacherId());
        cashinBO.setTotalAmount(appoint.getTotalPrice());
        cashinBO.setName("上门家教："+appoint.getNickname());
        cashinBO.setDescription("家教地址："+appoint.getAddress());
        cashinBO.setBalance(teacher.getBalance());
        cashinBO.setCteateTime(LocalDateTime.now());
        iFeignCashin.saveCashin(cashinBO);

        return appoint;
    }

//    拒绝结课
    public Appoint rejectCloseCourse(Long id, Long appointId) {
        Appoint appoint = getBaseMapper().selectById(appointId);
        if(appoint==null){
            throw new BusinessException("appointId不存在");
        }
        if(appoint.getStudentId().compareTo(id)!=0){
            throw new BusinessException("无权操作");
        }
        if(appoint.getState().compareTo(5)!=0){ //待结课
            throw new BusinessException("状态错误");
        }

        appoint.setState(4); //进行中
        getBaseMapper().updateById(appoint);

        return appoint;
    }

//   软删除
    public Appoint deleteAppointForStudent(Long id, Long appointId) {
        Appoint appoint = getBaseMapper().selectById(appointId);
        if(appoint==null){
            throw new BusinessException("appointId错误");
        }
        if(appoint.getStudentId().compareTo(id)!=0){
            throw new BusinessException("无权限");
        }
//        判断状态是否满足
        if(appoint.getState().compareTo(7)!=0 && appoint.getState().compareTo(9)!=0 && appoint.getState().compareTo(10)!=0){ // 已完成 已维权 已关闭 的才能删除
            throw new BusinessException("状态错误");
        }
        if(appoint.getStudentCommentState().compareTo(1)!=0){ // 评论后才能删除
            throw new BusinessException("请评论后删除");
        }

//       软删除
        appoint.setStudentDeleteState(1); //0 未删除 1已删除
        getBaseMapper().updateById(appoint);

        return appoint;
    }

//    维权
    @Transactional
    public Appoint rightAppointForStudent(Long id, Long appointId) {
        //        判断是否有权限
        Appoint appoint = getBaseMapper().selectById(appointId);
        if(appoint==null){
            throw new BusinessException("appointId错误");
        }
        if(appoint.getStudentId().compareTo(id)!=0){
            throw new BusinessException("无权限");
        }
//        判断状态是否满足
        if(appoint.getState().compareTo(4)!=0 && appoint.getState().compareTo(5)!=0 && appoint.getState().compareTo(6)!=0){ //待关闭, 待结课, 进行中才能维权
            throw new BusinessException("状态错误");
        }

        appoint.setState(9); //已维权
        getBaseMapper().updateById(appoint);

//       同步关闭需求
        Need need = needMapper.selectById(appoint.getNeedId());
        need.setState(6); //已关闭
        need.setReason("学生维权");
        needMapper.updateById(need);

        return appoint;
    }

//    学生端取消掉老师的预约
    public Appoint cancelAppointForStudent(Long id, Long appointId) {
        //        判断是否有权限
        Appoint appoint = getBaseMapper().selectById(appointId);
        if(appoint==null){
            throw new BusinessException("appointId错误");
        }
        if(appoint.getStudentId().compareTo(id)!=0){
            throw new BusinessException("无权限");
        }
//        判断状态是否满足
        if(appoint.getState().compareTo(1)!=0){ //已预约
            throw new BusinessException("状态错误");
        }

        appoint.setState(10); //已关闭
        appoint.setReason("学员取消你的预约");
        getBaseMapper().updateById(appoint);

        return appoint;
    }
}
