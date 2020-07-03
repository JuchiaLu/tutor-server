package com.juchia.tutor.business.auth.entity.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("comment")
public class CommentBO1 implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 学生id_外键(用户) 评论人
     */
    private Long fromId;

    /**
     * 老师id_外键(用户) 评论给谁
     */
    private Long toId;

    /**
     * 需求id_外键 评论给哪个需求
     */
    private Long needId;

    /**
     * 父id_外键 预留
     */
    private Long parentId;

    /**
     * 预约id_外键
     */
    private Long appointId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 类型 1好评 2中评 3差评
     */
    private Integer type;

    /**
     * 评分等级 1-5
     */
    private Integer rank;

    /**
     * 评分标题 预留
     */
    private String title;

    /**
     * 标签 空格隔开 预留
     */
    private String tag;

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
