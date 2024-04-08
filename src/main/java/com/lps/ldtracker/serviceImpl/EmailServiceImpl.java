package com.lps.ldtracker.serviceImpl;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.lps.ldtracker.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service 
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

	private final JavaMailSender javaMailSender;

	@Override
	public void sendEmail(String to, String subject, String body) {

		try {
			MimeMessage message = javaMailSender.createMimeMessage(); 
			MimeMessageHelper helper;
			helper = new MimeMessageHelper(message, true);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body);
			javaMailSender.send(message);
		} catch (MessagingException msge) {
			msge.printStackTrace();
			log.debug("Error: sendEmail messaging " + msge.getMessage());
		} catch (RuntimeException e) {
			e.printStackTrace();
			log.debug("Error: sendEmail " + e.getMessage());
		}

	}

}
