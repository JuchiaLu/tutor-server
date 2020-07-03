package com.juchia.tutor.business.auth.entity.query;

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
@TableName("message")
public class MessageQuery1 implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 消息发送者id_外键(用户)
     */
    private Long fromId;

    /**
     * 消息接收者id_外键(用户)
     */
    private Long toId;

    /**
     * 消息类型 1系统消息 2用户消息
     */
    private Integer type;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 点击系统消息时的跳转url
     */
    private String url;

    /**
     * 读取状态 0未读 1已读
     */
    private Integer state;

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
