package com.juchia.tutor.api.pay.bo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

//转账
@Data
public class TransferBizContent implements Serializable {


    //String	必选	64	商户端的唯一订单号，对于同一笔转账请求，商户需保证该订单号唯一。	201806300001
    private String outBizNo;

    //Price	必选	20	订单总金额，单位为元，精确到小数点后两位，STD_RED_PACKET产品取值范围[0.01,100000000]；
    //TRANS_ACCOUNT_NO_PWD产品取值范围[0.1,100000000]	23.00
    private BigDecimal transAmount;

    //String	必选	64	业务产品码，
    //收发现金红包固定为：STD_RED_PACKET；
    //单笔无密转账到支付宝账户固定为：TRANS_ACCOUNT_NO_PWD；
    //单笔无密转账到银行卡固定为：TRANS_BANKCARD_NO_PWD
    private String productCode="TRANS_ACCOUNT_NO_PWD";

    //描述特定的业务场景，可传的参数如下：
    //PERSONAL_COLLECTION：C2C现金红包-领红包；
    //DIRECT_TRANSFER：B2C现金红包、单笔无密转账到支付宝/银行卡
    private String bizScene = "DIRECT_TRANSFER";


    //    必选
    private PayeeInfo payeeInfo;

    //String	可选	64	转账业务的标题，用于在支付宝用户的账单里显示	营销红包
    private String orderTitle;

    //	String	可选	200	业务备注	红包领取
    private String remark;

    @Data
    public static class PayeeInfo implements Serializable{

        //String	必填	64	参与方的唯一标识	2088123412341234
        private String identity;

        //String	必填	64	参与方的标识类型，目前支持如下类型：
        //1、ALIPAY_USER_ID 支付宝的会员ID
        //2、ALIPAY_LOGON_ID：支付宝登录号，支持邮箱和手机号格式	ALIPAY_USER_ID
        private String identityType="ALIPAY_LOGON_ID";

        //String	可选	256	参与方真实姓名，如果非空，将校验收款支付宝账号姓名一致性。当identity_type=ALIPAY_LOGON_ID时，本字段必填。	黄龙国际有限公司
        private String name;
    }

}
