package com.juchia.tutor.business.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.api.pay.bo.PayBizContent;
import com.juchia.tutor.api.pay.client.IFeignAliPay;
import com.juchia.tutor.business.auth.entity.bo.NeedBO1;
import com.juchia.tutor.business.auth.entity.query.NeedQuery1;
import com.juchia.tutor.business.common.entity.dto.NeedDTO;
import com.juchia.tutor.business.common.entity.po.Appoint;
import com.juchia.tutor.business.common.entity.po.Need;
import com.juchia.tutor.business.common.exception.BusinessException;
import com.juchia.tutor.business.common.mapper.AppointMapper;
import com.juchia.tutor.business.common.mapper.NeedMapper;
import com.juchia.tutor.common.entity.query.PageQuery;
import com.juchia.tutor.common.entity.vo.MyPage;
import com.juchia.tutor.common.util.key.IdWorker;
import com.tuyang.beanutils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>
 * 预约表 服务实现类
 * </p>
 *
 * @author juchia
 * @since 2020-01-27
 */
@Service
public class AuthNeedService extends ServiceImpl<NeedMapper, Need> {

    @Autowired
    AppointMapper appointMapper;

    @Autowired
    IFeignAliPay iFeignAliPay;

    public boolean isMyPublish(Long id, Long needId) {
        Need need = getBaseMapper().selectById(needId);
        if(need==null){
            throw new BusinessException("此需求不存在");
        }
        return need.getStudentId().compareTo(id)==0;
    }

    public boolean isMyAppoint(Long id, Long needId) {
        Appoint condition = new Appoint();
        condition.setNeedId(needId);
        condition.setTeacherId(id);
        Appoint appoint = appointMapper.selectOne(new QueryWrapper<>(condition));
        if(appoint!=null){
            return true;
        }
        return false;
    }

    @Transactional
    public String chooseTeacherForNeed(Long id, Long needId, Long teacherId) {

 //      判读用户是否有权限操作此需求
        Need need = getBaseMapper().selectById(needId);
        if(need==null){
            throw new BusinessException("参数错误: needId");
        }
        if(need.getStudentId().compareTo(id)!=0){
            throw new BusinessException("你没有权限操作此需求");
        }
        if(need.getState().compareTo(3)!=0){ //审核通过
            throw new BusinessException("状态错误,无法操作");
        }


//        获取全部预约了此需求的预约
        Appoint condition = new Appoint();
        condition.setNeedId(needId);
        List<Appoint> appoints = appointMapper.selectList(new QueryWrapper<>(condition));


//        将中标老师的预约状态改为已中标（待付款）
//        其他老师预约的状态改为已关闭, 关闭原因为未中标
        AtomicReference<Appoint> chose = new AtomicReference<>();
        appoints.stream().forEach(appoint -> {
            if(appoint.getTeacherId().compareTo(teacherId)==0){
                appoint.setState(2); //已中标（待付款）
                appoint.setTradeNo(IdWorker.getTimeId()); //订单号
                chose.set(appoint);
            }else {
                appoint.setState(10); //已关闭
                appoint.setReason("未中标");
                appoint.setStudentDeleteState(1); //隐藏掉学员端显示
            }
            appointMapper.updateById(appoint);
        });


//      将此需求改为已选定
        need.setState(4);
        getBaseMapper().updateById(need);


//        生成支付地址
        PayBizContent payBizContent = new PayBizContent();
        payBizContent.setOutTradeNo(chose.get().getTradeNo()); //订单号
        payBizContent.setTotalAmount(chose.get().getTotalPrice()); //金额
        payBizContent.setSubject("闽师家教" + chose.get().getNickname() +"上门支教"); //订单名称
        //payBizContent.setTimeoutExpress("1m"); //该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m
//    body 可选 订单描述
//    private String body;
//    time_expire 可选 订单绝对超时时间 格式为yyyy-MM-dd HH:mm:ss	2016-12-31 10:05:01
//    private LocalDateTime timeExpire;

        String url=null;
        try {
            url =  iFeignAliPay.pay(payBizContent); //TODO 这里其他异常为处理
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        return url;
    }

    public Need saveNeed(Long id,NeedBO1 needBO) {

        Need saving = BeanCopyUtils.copyBean(needBO,Need.class);

//      状态设置为1 审核中
        saving.setState(1);
        saving.setStudentId(id); //发布者userId

//      预约人数设为0
        saving.setTotalAppoint(0L);

//      计算总价格 上课次数 * 每次小时 * 每小时价格
        BigDecimal totalPrice = new BigDecimal(saving.getFrequency().toString())
                .multiply(new BigDecimal(saving.getTimeHour().toString()))
                .multiply(saving.getHourPrice());
        saving.setTotalPrice(totalPrice);

//        创建时间 和 更新时间 设为现在
        LocalDateTime now = LocalDateTime.now();
        saving.setCreateTime(now);
        saving.setUpdateTime(now);

        getBaseMapper().insert(saving);
        return saving;
    }

    public MyPage<NeedDTO> pageDTO(Long id, PageQuery pageQuery, NeedQuery1 needQuery) {
        //      1 将我们的分页对象转换成mybatis plus 的
        Page<NeedDTO> page = new Page<>();
        page.setSize(pageQuery.getSize());
        page.setCurrent(pageQuery.getCurrent());

//      2根据我们的Query对象构建QueryWrapper
        QueryWrapper<NeedDTO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id",id);
        queryWrapper.eq("deleted",0); //未软删除
        if(needQuery.getState()!=null){
            queryWrapper.eq("state",needQuery.getState());
        }

//        3调用Mapper
        IPage<NeedDTO> needDTOIPage = getBaseMapper().selectPageDTO(page, queryWrapper);

//        将mybatis plus 分页对象转换成我们的
        MyPage<NeedDTO> myPage = BeanCopyUtils.copyBean(needDTOIPage, MyPage.class);
        myPage.setPages(needDTOIPage.getPages());//mybatis plus 没有这个属性,而是直接get方法
        return myPage;
    }

    @Transactional
    public Need close(Long id, Long needId) {
        Need need = getBaseMapper().selectById(needId);
        if(need==null){
            throw new BusinessException("参数错误");
        }
        if(need.getStudentId().compareTo(id)!=0){
            throw new BusinessException("无权操作");
        }
        if(need.getState().compareTo(3)>0){ //1 2 3状态能关闭
            throw new BusinessException("状态错误");
        }
        need.setState(6); //关闭
        need.setReason("学员主动关闭需求");
        getBaseMapper().updateById(need);


//        获取全部预约了此需求的预约
        Appoint condition = new Appoint();
        condition.setNeedId(needId);
        List<Appoint> appoints = appointMapper.selectList(new QueryWrapper<>(condition));

//       关闭全部预约
        appoints.stream().forEach(appoint -> {
                appoint.setState(10); //a已关闭
                appoint.setReason("学员关闭需求");
            appointMapper.updateById(appoint);
        });

        return need;
    }

    public Need deleteAppoint(Long id, Long needId) {
        Need need = getBaseMapper().selectById(needId);
        if(need==null){
            throw new BusinessException("参数错误");
        }
        if(need.getStudentId().compareTo(id)!=0){
            throw new BusinessException("无权操作");
        }
        if(need.getState().compareTo(5)!=0 && need.getState().compareTo(6)!=0 ){ //已完成 已关闭的能删除，
            throw new BusinessException("状态错误");
        }
        need.setDeleted(1); //软删除
        getBaseMapper().updateById(need);

        return need;
    }
}
