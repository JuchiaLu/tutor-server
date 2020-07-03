package com.juchia.tutor.system.back.entity.vo;

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
@TableName("advertisement")
public class AdvertisementVO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 名字或标题
     */
    private String name;

    /**
     * 类型 1站内跳转 2站外跳转
     */
    private Integer type;

    /**
     * 跳转地址或路径
     */
    private String url;

    /**
     * 图片地址
     */
    private String imageUrl;

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
    private LocalDateTime createTiime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;


}
