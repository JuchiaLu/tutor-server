package com.juchia.tutor.system.common.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 附件表
 * </p>
 *
 * @author juchia
 * @since 2020-01-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)
@TableName("attachment")
public class Attachment implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 文件名
     */
    private String name;

    /**
     * 后缀
     */
    private String type;

    /**
     * 大小(字节)
     */
    private Long size;

    /**
     * 地址
     */
    private String url;

    /**
     * 用户id_外键
     */
    private Long userId;

    /**
     * 文件储存类型 1本地 2七牛
     */
    private Integer storeType;

    /**
     * 排序
     */
    private Long weight;

    /**
     * 状态 0启用 1禁用
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
