package com.juchia.tutor.business.back.entity.query;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 在职教师认证
 * </p>
 *
 * @author juchia
 * @since 2020-01-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)
@TableName("teacher_auth")
public class TeacherAuthQuery1 implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 所在院校
     */
    private String school;

    /**
     * 教师资格证图片1
     */
    private String image1;

    /**
     * 教师资格证图片2
     */
    private String image2;

    /**
     * 教师资格证图片3
     */
    private String image3;

    /**
     * 教师资格证图片4
     */
    private String image4;

    /**
     * 教师资格证图片5
     */
    private String image5;

    /**
     * 老师id_外键
     */
    private Long teacherId;

    /**
     * 状态,1未审核, 2审核通过 3审核不通过
     */
    private Integer state;

    /**
     * 审核失败原因
     */
    private String reason;

    /**
     * 排序
     */
    private Long weight;

    /**
     * 状态 0禁用 1启用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


}
