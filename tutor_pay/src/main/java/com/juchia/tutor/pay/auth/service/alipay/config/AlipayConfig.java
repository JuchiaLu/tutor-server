//package com.juchia.tutor.pay.auth.service.alipay.config;
//
///* *
// *类名：AlipayConfig
// *功能：基础配置类
// *详细：设置帐户有关信息及返回路径
// *修改日期：2017-04-05
// *说明：
// *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
// *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
// */
//
//import com.juchia.tutor.pay.common.properties.PayProperties;
//import org.springframework.beans.factory.annotation.Autowired;
//
//
//public class AlipayConfig {
//
////↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
//    @Autowired
//    private static PayProperties payProperties;
//
//    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
//    public static String app_id = payProperties.getAliPay().getAppId();
//
//    // 商户私钥，您的PKCS8格式RSA2私钥
//    public static String merchant_private_key =  payProperties.getAliPay().getMerchantPrivateKey();
//
//    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
//    public static String alipay_public_key = payProperties.getAliPay().getAlipayPublicKey();;
//
//    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
//    public static String notify_url = payProperties.getAliPay().getNotifyUrl();;
//
//    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
//    public static String return_url = payProperties.getAliPay().getReturnUrl();
//
//    // 签名方式
//    public static String sign_type = payProperties.getAliPay().getSignType();
//
//    // 字符编码格式
//    public static String charset = payProperties.getAliPay().getCharset();
//
//    // 支付宝网关
//    public static String gatewayUrl = payProperties.getAliPay().getGatewayUrl();
//
////↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
//}
//
//
