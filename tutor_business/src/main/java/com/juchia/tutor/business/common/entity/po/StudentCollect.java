package com.juchia.tutor.business.common.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author juchia
 * @since 2020-01-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)
@TableName("student_collect")
public class StudentCollect implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 学生id_外键(用户)
     */
    private Long teacherId;

    /**
     * 老师id_外键(用户)
     */
    private Long studentId;

    /**
     * 状态 0禁用 1启用
     */
    private Integer status;

    /**
     * 排序
     */
    private Long weight;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}
