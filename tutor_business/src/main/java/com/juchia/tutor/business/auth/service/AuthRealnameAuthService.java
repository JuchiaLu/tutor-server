package com.juchia.tutor.business.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.business.auth.entity.bo.RealnameAuthBO1;
import com.juchia.tutor.business.common.entity.po.RealnameAuth;
import com.juchia.tutor.business.common.exception.ResourceNotFondException;
import com.juchia.tutor.business.common.mapper.RealnameAuthMapper;
import com.tuyang.beanutils.BeanCopyUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 实名认证 服务实现类
 * </p>
 *
 * @author juchia
 * @since 2020-01-27
 */
@Service
public class AuthRealnameAuthService extends ServiceImpl<RealnameAuthMapper, RealnameAuth>{

    public RealnameAuth getRealnameAuthForTeacher(Long id) throws ResourceNotFondException {
        RealnameAuth condition = new RealnameAuth();
        condition.setTeacherId(id); //注意这里是　userId
        RealnameAuth realnameAuth = getBaseMapper().selectOne(new QueryWrapper<>(condition));
        if(realnameAuth==null){
            throw new ResourceNotFondException("资源不存在");
        }
        return realnameAuth;
    }

    public RealnameAuth saveOrUpdateRealnameAuthForTeacher(Long id, RealnameAuthBO1 realnameAuthBO) {
        realnameAuthBO.setTeacherId(id);
        realnameAuthBO.setState(1); //每次重新提交状态变为待审核

//        删除掉原来的
        RealnameAuth condition = new RealnameAuth();
        condition.setTeacherId(id);
        RealnameAuth realnameAuth = getBaseMapper().selectOne(new QueryWrapper<>(condition));
        if(realnameAuth!=null){
            realnameAuthBO.setId(realnameAuth.getId());
            getBaseMapper().deleteById(realnameAuth.getId());
        }

//        重新保存
        RealnameAuth saving = BeanCopyUtils.copyBean(realnameAuthBO, RealnameAuth.class);
        saveOrUpdate(saving);

        return null;
    }
}
