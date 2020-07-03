package com.juchia.tutor.auth.base.authentication.code.impl.sms;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juchia.tutor.auth.base.authentication.code.core.Provider;
import com.juchia.tutor.auth.base.authentication.code.core.ValidateCode;
import com.juchia.tutor.auth.base.authentication.code.support.DefaultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class SmsCodeProvider implements Provider {

    private ObjectMapper objectMapper = new ObjectMapper();
    private static Socket socket = null;
    private static PrintWriter out = null;
    private static BufferedReader in = null;
    private static String serverIP="192.168.1.6"; //服务器端ip地址
    private static int port=8888;    //服务器端端口号
    private static int maxTryReconnect = 1;

    static {
        reConnect();
    }

    @Override
    public String support() {
        return "smsCode";
    }

    @Override
    public ValidateCode generate(HttpServletRequest request) {
        String randomNumber = RandomStringUtils.randomNumeric(4);
        return new DefaultCode(randomNumber, 60);
    }

    @Override
    public void sent(HttpServletRequest request, HttpServletResponse response, ValidateCode code) {
        String mobileNumber = request.getParameter("mobileNumber");
        log.info("向 {} 发送短信验证码 {} ", mobileNumber, code.getCode());
        socketSend(mobileNumber,code.getCode());
    }


    private void socketSend(String mobileNumber,String code){
        try {
            Map<String,String> map = new HashMap<>();
            map.put("phone",mobileNumber);
            map.put("msg",code);
            synchronized (out){
                out.println(objectMapper.writeValueAsString(map));
            }
            System.out.println("发送成功："+map);
        } catch(IOException e)
        {
            e.printStackTrace();
            if(maxTryReconnect>0){
                reConnect();
                maxTryReconnect--;
            }

        }
    }

    private static void reConnect(){
        try {
            socket=new Socket(serverIP,port);
            in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out=new PrintWriter(socket.getOutputStream(),true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
