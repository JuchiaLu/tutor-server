package com.juchia.tutor.business.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.business.auth.entity.bo.StudentAuthBO1;
import com.juchia.tutor.business.common.entity.po.StudentAuth;
import com.juchia.tutor.business.common.exception.ResourceNotFondException;
import com.juchia.tutor.business.common.mapper.StudentAuthMapper;
import com.tuyang.beanutils.BeanCopyUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 大学生认证 服务实现类
 * </p>
 *
 * @author juchia
 * @since 2020-01-27
 */
@Service
public class AuthStudentAuthService extends ServiceImpl<StudentAuthMapper, StudentAuth>{

    public StudentAuth getStudentAuthForTeacher(Long id) throws ResourceNotFondException {
        StudentAuth condition = new StudentAuth();
        condition.setTeacherId(id); //注意这里是　userId
        StudentAuth studentAuth = getBaseMapper().selectOne(new QueryWrapper<>(condition));
        if(studentAuth==null){
            throw new ResourceNotFondException("资源不存在");
        }
        return studentAuth;
    }

    public StudentAuth saveOrUpdateStudentAuthForTeacher(Long id, StudentAuthBO1 studentAuthBO) {
        studentAuthBO.setTeacherId(id);
        studentAuthBO.setState(1);//每次重新提交状态变为待审核


//     删除原来的
        StudentAuth condition = new StudentAuth();
        condition.setTeacherId(id);
        StudentAuth studentAuth = getBaseMapper().selectOne(new QueryWrapper<>(condition));
        if(studentAuth!=null){
            studentAuthBO.setId(studentAuth.getId());
            getBaseMapper().deleteById(studentAuth.getId());
        }

//      重新保存
        StudentAuth saving = BeanCopyUtils.copyBean(studentAuthBO, StudentAuth.class);
        saveOrUpdate(saving);

        return null;
    }
}
