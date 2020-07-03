package com.juchia.tutor.business.auth.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.api.pay.bo.CashoutBO;
import com.juchia.tutor.api.pay.client.IFeignCashout;
import com.juchia.tutor.business.auth.entity.bo.CashoutBO1;
import com.juchia.tutor.business.auth.entity.bo.TeacherBO1;
import com.juchia.tutor.business.common.entity.dto.TeacherDTO;
import com.juchia.tutor.business.common.entity.po.Teacher;
import com.juchia.tutor.business.common.exception.BusinessException;
import com.juchia.tutor.business.common.exception.ResourceNotFondException;
import com.juchia.tutor.business.common.mapper.TeacherMapper;
import com.tuyang.beanutils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class AuthTeacherService extends ServiceImpl<TeacherMapper, Teacher> {

    @Autowired
    IFeignCashout iFeignCashout;

    public TeacherDTO getDTOById(Long id) throws ResourceNotFondException {
        //传进来的id是userId 不是TeacherId 先由userId获取TeacherId 再查
        Teacher condition = new Teacher();
        condition.setUserId(id);
        Teacher teacher = getBaseMapper().selectOne(new QueryWrapper<>(condition));
        if(teacher==null){
            throw new ResourceNotFondException("资源不存在");
        }

        TeacherDTO teacherDTO = getBaseMapper().selectDTOById(teacher.getId());

        return teacherDTO;
    }


    public TeacherDTO patchById(Long id, TeacherBO1 teacherBO) {
        //传进来的id是userId 不是TeacherId 先由userId获取TeacherId 再查
        Teacher condition = new Teacher();
        condition.setUserId(id);
        Teacher teacher = getBaseMapper().selectOne(new QueryWrapper<>(condition));

        Teacher saving = BeanCopyUtils.copyBean(teacherBO, Teacher.class);
        saving.setId(teacher.getId());
//      TODO 业务开始
        saving.setState(1); //审核状态改为审核中

//      TODO 业务结束
        getBaseMapper().updateById(saving);
        return getBaseMapper().selectDTOById(saving.getId());
    }

    @Transactional
    public void cashout(Long id, CashoutBO1 cashoutBO) {
        Teacher condition = new Teacher();
        condition.setUserId(id);
        Teacher teacher = getBaseMapper().selectOne(new QueryWrapper<>(condition));
        if(teacher==null){
            throw new BusinessException("参数错误");
        }
        if(cashoutBO.getCash().compareTo(new BigDecimal("1"))<0){
            throw new BusinessException("提现金额不能小于1元");
        }
        if(teacher.getBalance().compareTo(cashoutBO.getCash())<0){
            throw new BusinessException("提现金额大于余额");
        }
        //       老师帐号减去余额
        teacher.setBalance(teacher.getBalance().subtract(cashoutBO.getCash()));

        //填加体现一条记录
        CashoutBO cashoutBO1 = BeanCopyUtils.copyBean(cashoutBO, CashoutBO.class); // TODO CashoutBO1 字段删除
        cashoutBO1.setUserId(id);
        cashoutBO1.setState(1); //状态为 处理中
        cashoutBO1.setBalance(teacher.getBalance()); //余额
        cashoutBO1.setCashoutType(1); //支付宝
        cashoutBO1.setCreateTime(LocalDateTime.now());
        iFeignCashout.saveCashout(cashoutBO1);


        getBaseMapper().updateById(teacher);
    }
}
