package com.juchia.tutor.auth.base.authentication.code.impl.email;


import com.juchia.tutor.auth.base.authentication.code.core.Provider;
import com.juchia.tutor.auth.base.authentication.code.core.ValidateCode;
import com.juchia.tutor.auth.base.authentication.code.support.DefaultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class EmailCodeProvider implements Provider {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public String support() {
        return "emailCode";
    }

    @Override
    public ValidateCode generate(HttpServletRequest request) {
        String randomNumber = RandomStringUtils.randomNumeric(4);
        return new DefaultCode(randomNumber, 60);
    }

    @Override
    public void sent(HttpServletRequest request, HttpServletResponse response, ValidateCode code) {
        //MimeMessage mimeMessage = mailSender.createMimeMessage();

        String email = request.getParameter("email");
        log.info("向 {} 发送邮箱验证码 {} ", email, code.getCode());

        SimpleMailMessage mimeMessage = new SimpleMailMessage();
        //mimeMessage.setFrom("");
        mimeMessage.setTo(email);
        mimeMessage.setSubject("毕业设计系统");
        mimeMessage.setText("你的验证码为:"+code.getCode());
        mailSender.send(mimeMessage);
    }
}
