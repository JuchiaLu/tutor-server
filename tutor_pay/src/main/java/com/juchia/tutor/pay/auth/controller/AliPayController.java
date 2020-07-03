package com.juchia.tutor.pay.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.juchia.tutor.pay.auth.service.alipay.AlipayService;
import com.juchia.tutor.pay.auth.service.alipay.entity.request.*;
import com.juchia.tutor.pay.auth.service.alipay.entity.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@RestController
public class AliPayController {

    @Autowired
    AlipayService alipayService;


    /**
     * 付款
     */
    @GetMapping("/pay")
    public String pay(PayBizContent payBizContent) throws UnsupportedEncodingException, JsonProcessingException {
        return alipayService.pay(payBizContent);
    }

    /**
     * 交易查询
     */
    @GetMapping("/payQuery")
    public PayQueryResponse payQuery(PayQueryBizContent payQueryBizContent) throws IOException {
        return alipayService.payQuery(payQueryBizContent);
    }


    /**
     * 关闭订单 未付款前可以关闭交易
     * @param
     */
    @GetMapping("/close")
    public CloseResponse close(CloseBizContent closeBizContent) throws IOException {
        return alipayService.close(closeBizContent);
    }


    /**
     * 退款 退款日期未达到前可以退款
     * @param
     */
    @GetMapping("/refund")
    public RefundResponse refund(RefundBizContent refundBizContent) throws IOException {
        return alipayService.refund(refundBizContent);
    }

    /**
     * 退款查询
     * @param
     */
    @GetMapping("/refundQuery")
    public RefundQueryResponse refundQuery(RefundQueryBizContent refundQueryBizContent) throws IOException {

        return alipayService.refundQuery(refundQueryBizContent);

    }

    /**
     * 转账
     * @param
     */
    @GetMapping("/transfer")
    public TransferResponse transfer(TransferBizContent transferBizContent) throws IOException {

        return alipayService.transfer(transferBizContent);

    }

    /**
     * 异步通知
     */
    @PostMapping("/asyncNotify")
    public String asyncNotify(HttpServletRequest request)throws UnsupportedEncodingException{
        return alipayService.asyncNotify(request);
    }

    /**
     * 同步通知
     */
    @GetMapping("/syncReturn")
    public String syncReturn(HttpServletRequest request, HttpServletResponse response)throws UnsupportedEncodingException{
        alipayService.syncReturn(request);

        // TODO
        response.setHeader("Content-Type","text/html; charset=utf-8");
        return "<html>\n" +
                "<head>\n" +
                "<meta charset=\"utf-8\">\n" +
                "<title>支付成功</title>\n" +
                "<style type=\"text/css\">\n" +
                ".info{ \n" +
                "    padding: 30px;\n" +
                "    background: #ff9326;\n" +
                "    color: #FFF;\n" +
                "}\n" +
                "\n" +
                ".mark{\n" +
                "  width:100px;\n" +
                "  margin:auto;\n" +
                "  padding-top:130px;\n" +
                "  fill:#6eb700;\n" +
                "}\n" +
                "\n" +
                ".text{\n" +
                "    align:center;\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                " \n" +
                "<body>\n" +
                "<div class=\"info\">\n" +
                "<span class=\"text\">\n" +
                "您的订单支付成功，请返回个人中心查看\n" +
                "<span>\n" +
                "</div>\n" +
                "<div class='mark'>\n" +
                "<svg version=\"1.1\" id=\"Layer_1\" xmlns:x=\"&ns_extend;\" xmlns:i=\"&ns_ai;\" xmlns:graph=\"&ns_graphs;\"\n" +
                "\t xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" x=\"0px\" y=\"0px\" width=\"100px\" height=\"100px\"\n" +
                "\t viewBox=\"0 0 24 24\" enable-background=\"new 0 0 24 24\" xml:space=\"preserve\">\n" +
                "<metadata>\n" +
                "\t<sfw  xmlns=\"&ns_sfw;\">\n" +
                "\t\t<slices></slices>\n" +
                "\t\t<sliceSourceBounds  width=\"505\" height=\"984\" bottomLeftOrigin=\"true\" x=\"0\" y=\"-984\"></sliceSourceBounds>\n" +
                "\t</sfw>\n" +
                "</metadata>\n" +
                "<g>\n" +
                "\t<g>\n" +
                "\t\t<g>\n" +
                "\t\t\t<path d=\"M12,24C5.4,24,0,18.6,0,12S5.4,0,12,0s12,5.4,12,12S18.6,24,12,24z M12,2C6.5,2,2,6.5,2,12s4.5,10,10,10s10-4.5,10-10\n" +
                "\t\t\t\tS17.5,2,12,2z\"/>\n" +
                "\t\t</g>\n" +
                "\t</g>\n" +
                "\t<g>\n" +
                "\t\t<g>\n" +
                "\t\t\t<path d=\"M11,16c-0.3,0-0.5-0.1-0.7-0.3l-3-3c-0.4-0.4-0.4-1,0-1.4s1-0.4,1.4,0l3,3c0.4,0.4,0.4,1,0,1.4C11.5,15.9,11.3,16,11,16z\n" +
                "\t\t\t\t\"/>\n" +
                "\t\t</g>\n" +
                "\t</g>\n" +
                "\t<g>\n" +
                "\t\t<g>\n" +
                "\t\t\t<path d=\"M11,16c-0.3,0-0.5-0.1-0.7-0.3c-0.4-0.4-0.4-1,0-1.4l6-6c0.4-0.4,1-0.4,1.4,0s0.4,1,0,1.4l-6,6C11.5,15.9,11.3,16,11,16z\n" +
                "\t\t\t\t\"/>\n" +
                "\t\t</g>\n" +
                "\t</g>\n" +
                "</g>\n" +
                "</svg></div>  \n" +
                "</body>\n" +
                "</html>";
    }


}
