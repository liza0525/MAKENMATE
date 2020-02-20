package com.cocktail.service;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    public JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
    	// to : 메일 받을 주소, subject : 메일 제목, text : 메일 내용
    
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        System.out.println(to + " " + subject + " " + text);
        emailSender.send(message);
    }
    public void sendMail(String userEmail, String subject, String msg) throws Exception{
        
        //mail server 설정 
         String charSet ="utf-8";
        String hostSMTP = "smtp.gmail.com";
        
        String hostSMTPid = "smtptest2222@gmail.com";//id
        String hostSMTPpwd = "qweasd1`";
        
        //보내는 이 설정
        String fromEmail = "smtptest2222@gmail.com";
        String fromName = "Cocktail";   
        
        HtmlEmail mail = new HtmlEmail();
        mail.setDebug(true);
        mail.setCharset(charSet);
        mail.setSSLOnConnect(true);
        mail.setHostName(hostSMTP);
        mail.setSmtpPort(587);
//        mail.setAuthentication(hostSMTPid, hostSMTPpwd);
        mail.setAuthenticator(new DefaultAuthenticator(hostSMTPid, hostSMTPpwd));
        mail.setStartTLSEnabled(true);
        mail.addTo(userEmail);
        mail.setSSLOnConnect(true);
        mail.setFrom(fromEmail,fromName,charSet);
        mail.setSubject(subject);
        mail.setHtmlMsg(msg);
        mail.send();
    }
        

}