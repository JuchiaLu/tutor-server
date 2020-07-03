package com.juchia.tutor.business.common.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.juchia.tutor.business.common.entity.dto.TeacherDTO;
import com.juchia.tutor.business.common.entity.po.Teacher;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 教员表 Mapper 接口
 * </p>
 *
 * @author juchia
 * @since 2020-01-24
 */
public interface TeacherMapper extends BaseMapper<Teacher> {

    IPage<TeacherDTO> selectPageDTO(@Param("page") Page<?> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    TeacherDTO selectDTOById(Long id);
}
