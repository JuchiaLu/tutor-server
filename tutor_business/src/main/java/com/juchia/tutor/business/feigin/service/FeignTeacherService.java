package com.juchia.tutor.business.feigin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.api.business.bo.FeignTeacherBO1;
import com.juchia.tutor.business.common.entity.po.Teacher;
import com.juchia.tutor.business.common.exception.BusinessException;
import com.juchia.tutor.business.common.mapper.TeacherMapper;
import com.tuyang.beanutils.BeanCopyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FeignTeacherService extends ServiceImpl<TeacherMapper, Teacher> {

    public Teacher saveTeacher(FeignTeacherBO1 feignTeacherBO) {

//       重复校验
        Teacher condition = new Teacher();
        condition.setUserId(feignTeacherBO.getUserId());
        int count = count(new QueryWrapper<>(condition));
        if (count>0) {
            log.info("userId已存在:{}",feignTeacherBO.getUserId());
            throw new BusinessException("userId已存在!");
        }

//      bo 转 po 并字段处理
        Teacher saving = BeanCopyUtils.copyBean(feignTeacherBO,Teacher.class);
        saving.setId(null); //TODO 在service层处理还是controller层?

        getBaseMapper().insert(saving);
        return saving;
    }

    public Teacher patchById(FeignTeacherBO1 feignTeacherBO) {
        Teacher condition = new Teacher();
        condition.setUserId(feignTeacherBO.getUserId());
        Teacher teacher1 = getBaseMapper().selectOne(new QueryWrapper<>(condition));
        if(teacher1==null){
            throw new BusinessException("参数错误");
        }
        Teacher updating = BeanCopyUtils.copyBean(feignTeacherBO, Teacher.class);
        getBaseMapper().updateById(updating);
        return updating;
    }

    public Teacher myGetById(Long id) {
        Teacher condition = new Teacher();
        condition.setUserId(id);
        Teacher teacher = getBaseMapper().selectOne(new QueryWrapper<>(condition));
        if(teacher==null){
            throw new BusinessException("参数错误");
        }
        return teacher;
    }
}
