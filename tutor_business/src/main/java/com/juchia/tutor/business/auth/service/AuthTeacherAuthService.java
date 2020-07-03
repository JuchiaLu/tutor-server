package com.juchia.tutor.business.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.business.auth.entity.bo.TeacherAuthBO1;
import com.juchia.tutor.business.common.entity.po.TeacherAuth;
import com.juchia.tutor.business.common.exception.ResourceNotFondException;
import com.juchia.tutor.business.common.mapper.TeacherAuthMapper;
import com.tuyang.beanutils.BeanCopyUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 在职教师认证 服务实现类
 * </p>
 *
 * @author juchia
 * @since 2020-01-27
 */
@Service
public class AuthTeacherAuthService extends ServiceImpl<TeacherAuthMapper, TeacherAuth> {

    public TeacherAuth getTeacherAuthForTeacher(Long id) throws ResourceNotFondException {
        TeacherAuth condition = new TeacherAuth();
        condition.setTeacherId(id); //注意这里是　userId
        TeacherAuth teacherAuth = getBaseMapper().selectOne(new QueryWrapper<>(condition));
        if(teacherAuth==null){
            throw new ResourceNotFondException("资源不存在");
        }
        return teacherAuth;
    }

    public TeacherAuth saveOrUpdateTeacherAuthForTeacher(Long id, TeacherAuthBO1 teacherAuthBO) {
        teacherAuthBO.setTeacherId(id);
        teacherAuthBO.setState(1);

        TeacherAuth condition = new TeacherAuth();
        condition.setTeacherId(id);
        TeacherAuth teacherAuth = getBaseMapper().selectOne(new QueryWrapper<>(condition));
        if(teacherAuth!=null){
            teacherAuthBO.setId(teacherAuth.getId());
            getBaseMapper().deleteById(teacherAuth.getId());
        }

        TeacherAuth saving = BeanCopyUtils.copyBean(teacherAuthBO, TeacherAuth.class);
        saveOrUpdate(saving);
        return null;
    }
}
