package com.tensquare.sms.listenser;

import com.tensquare.sms.utils.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/*
* 接受来自rabbitMq的消息
* */
@Component
@RabbitListener(queues = "sms")
public class SmsListenser {

    @Autowired
    private SmsUtil smsUtil;

    @Value("${aliyun.sms.template_code}")
    private String template_code;
    @Value("${aliyun.sms.sign_name}")
    private String sign_name;

    @RabbitHandler
    public void sendSms(Map<String,String> message){

        System.out.println("验证码是" + message.get("code"));
        System.out.println("手机号是" + message.get("mobile"));
        try{
            smsUtil.sendSms(message.get("mobile"),template_code,sign_name,"{\"number\":\""+message.get("code")+"\"}");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
