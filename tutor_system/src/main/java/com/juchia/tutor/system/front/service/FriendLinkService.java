package com.juchia.tutor.system.front.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.juchia.tutor.system.common.entity.po.FriendLink;
import com.juchia.tutor.system.common.mapper.FriendLinkMapper;
import com.juchia.tutor.system.front.entity.query.FriendLinkQuery1;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author juchia
 * @since 2020-01-25
 */
@Service
public class FriendLinkService extends ServiceImpl<FriendLinkMapper, FriendLink> {

    public List<FriendLink> myList(FriendLinkQuery1 friendLinkQuery) {
        QueryWrapper<FriendLink> queryWrapper = new QueryWrapper<>();

        return getBaseMapper().selectList(queryWrapper);
    }
}
