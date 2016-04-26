package com.avalon.workbench.services.mail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

@Component
public class MailSend {

	@Autowired
	JavaMailSenderImpl jMailSenderImpl;
	
	public  void sendMail(String to ,String location,String fileName){
		 try{  
		 MimeMessage message = jMailSenderImpl.createMimeMessage();  
		  
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);  
	        helper.setFrom("pc.sekhar83@gmail.com");  
	        helper.setTo(to);  
	        helper.setSubject("Oracle Apps Report");  
	        helper.setText("Please Find the Oracle Apps Report");  
	  
	        // attach the file  
	        FileSystemResource file = new FileSystemResource(new File(location));
	        
	        helper.addAttachment(fileName, file);
	  
	        jMailSenderImpl.send(message);  
	        System.out.println("Mail has been Sent");
	        }catch(MessagingException e){e.printStackTrace();}  
		
	}
	public static void main(String[] args) {
		MailSend mailsend=new MailSend();
		ApplicationContext context=new ClassPathXmlApplicationContext("application-service.xml");
		mailsend.jMailSenderImpl=(JavaMailSenderImpl)context.getBean("mailSender");
		//System.out.println(new Date().getDate());
		//mailsend.sendMail("polineni.chandrasekhar@gmail.com","c:/sample.txt","sample.txt");
	}
	
	
}
