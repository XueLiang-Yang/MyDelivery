package com.liang.delivery.Tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

public class MailRunnable implements Runnable {
    private String mail;
    private MailTool mailTool;
    private String group;

    public MailRunnable(String mail,MailTool mailTool,String group) {
        this.mail = mail;
        this.mailTool = mailTool;
        this.group = group;
    }

    @Override
    public void run() {
        mailTool.sendMail(group,mail);
    }
}
