package com.juchia.tutor.pay.back.entity.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author juchia
 * @since 2020-02-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true)
@TableName("cashout")
public class CashoutBO1 implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 用户id_外键
     */
    private Long userId;

    /**
     * 系统支付类型 1支付宝 2微信 3网银
     */
    private Integer payType;

    /**
     * 系统支付帐号
     */
    private String payAccount;

    /**
     * 系统支付流水号
     */
    private String payNo;

    /**
     * 平台支付流水号
     */
    private String platformTradeNo;

    /**
     * 请求提现金额
     */
    private BigDecimal cash;

    /**
     * 提现手续金额
     */
    private BigDecimal fees;

    /**
     * 提现到手金额
     */
    private BigDecimal realCash;


    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 提现类型 1支付宝 2微信 3银行卡
     */
    private Integer cashoutType;


    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 提现帐号
     */
    private String cashoutAccount;

    /**
     * 备注
     */
    private String note;

    /**
     * 状态 1处理中 2成功 3失败
     */
    private Integer state;

    /**
     * 完成时间
     */
    private LocalDateTime finishTime;

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
