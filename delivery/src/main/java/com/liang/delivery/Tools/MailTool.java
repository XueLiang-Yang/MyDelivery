package com.liang.delivery.Tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Component
@ConfigurationProperties(prefix = "mail.server")
public class MailTool {
    private static final Logger logger = LoggerFactory.getLogger(MailTool.class);
    private String from;
    private String host;
    private String password;
    private String nick;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    @Override
    public String toString() {
        return "MailTool{" +
                "from='" + from + '\'' +
                ", host='" + host + '\'' +
                ", password='" + password + '\'' +
                ", nick='" + nick + '\'' +
                '}';
    }

    public void sendMail(String group,String mail){
        // 获取系统属性
        Properties properties = System.getProperties();
        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);

        properties.put("mail.smtp.auth", "true");
        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties,new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(from, password); //发件人邮件用户名、授权码
            }
        });

        try{
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(nick+"<"+from+">"));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(mail));

            // Set Subject: 头部头字段
            message.setSubject(MimeUtility.encodeText("即时信息发布系统", "utf-8", "B"));

            // 发送 HTML 消息, 可以插入html标签
            message.setContent("<p>您好,信息发布系统提醒您：您的收到了来自"+group+"的新信息，请及时查看</p>" +
                            "<a href='https://www.baidu.com'>测试</a>",
                    "text/html;charset=utf-8" );
            // 发送消息
            Transport.send(message);

            System.out.println("Sent message successfully....from runoob.com");
        }catch (
                MessagingException mex) {
            mex.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
