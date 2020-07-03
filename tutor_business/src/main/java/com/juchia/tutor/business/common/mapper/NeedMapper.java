package com.juchia.tutor.business.common.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.juchia.tutor.business.common.entity.dto.NeedDTO;
import com.juchia.tutor.business.common.entity.po.Need;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 预约表 Mapper 接口
 * </p>
 *
 * @author juchia
 * @since 2020-01-25
 */
public interface NeedMapper extends BaseMapper<Need> {

    IPage<NeedDTO> selectPageDTO(@Param("page") Page<?> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    NeedDTO selectDTOById(Long id);
}
