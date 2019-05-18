package com.liang.delivery.MailTest;


import com.liang.delivery.Tools.MailTool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.annotation.Resource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailTest {
    @Resource
    MailTool mailTool;
    @Test
    public void testMailInfo(){
        //mailTool.setTo("1364775041@qq.com");
        mailTool.sendMail("全校通知","1364775041@qq.com");
    }

   @Test
    public void testMail(){
       // 收件人电子邮箱
       String to = "1364775041@qq.com";

       // 发件人电子邮箱
       String from = "yangxueliang.owen@qq.com";

       // 指定发送邮件的主机为 smtp.qq.com
       String host = "smtp.qq.com";  //QQ 邮件服务器

       // 获取系统属性
       Properties properties = System.getProperties();

       // 设置邮件服务器
       properties.setProperty("mail.smtp.host", host);

       properties.put("mail.smtp.auth", "true");
       // 获取默认session对象
       Session session = Session.getDefaultInstance(properties,new Authenticator(){
           public PasswordAuthentication getPasswordAuthentication()
           {
               return new PasswordAuthentication("yangxueliang.owen@qq.com", "egndbmpxcuyzcbbj"); //发件人邮件用户名、授权码
           }
       });

       try{
           // 创建默认的 MimeMessage 对象
           MimeMessage message = new MimeMessage(session);

           //设置自定义发件人昵称
           String nick="";
           try {
               nick=javax.mail.internet.MimeUtility.encodeText("高校信息发布系统");
           } catch (UnsupportedEncodingException e) {
               e.printStackTrace();
           }
           // Set From: 头部头字段
           message.setFrom(new InternetAddress(nick+"<"+from+">"));

           // Set To: 头部头字段
           message.addRecipient(Message.RecipientType.TO,
                   new InternetAddress(to));

           // Set Subject: 头部头字段
           message.setSubject("This is the Subject Line!");

           // 发送 HTML 消息, 可以插入html标签
           message.setContent("<h1>This is actual message</h1>",
                   "text/html" );
           // 发送消息
           Transport.send(message);

           System.out.println("Sent message successfully....from runoob.com");
       }catch (
               MessagingException mex) {
           mex.printStackTrace();
       }
   }
}
