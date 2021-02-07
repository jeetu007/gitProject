package com.urssystems.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class EmailConfig {
		
	@Autowired
	Environment environment;
	
	@Bean
    public JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
 
        mailSender.setHost(environment.getRequiredProperty("spring.mail.host"));
        mailSender.setPort(Integer.parseInt(environment.getRequiredProperty("spring.mail.port")));
        mailSender.setUsername(environment.getRequiredProperty("spring.mail.username"));
        mailSender.setPassword(environment.getRequiredProperty("spring.mail.password"));
 
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable", "true");
        javaMailProperties.put("mail.smtp.auth", "true");
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailProperties.put("mail.debug", "true");
 
        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;
    }
 
    
}

