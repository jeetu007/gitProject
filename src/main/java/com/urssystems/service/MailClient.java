package com.urssystems.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Base64;

import javax.activation.DataSource;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.StreamUtils;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;
import com.urssystems.DTO.AppointmentDTO;
import com.urssystems.model.Mail;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class MailClient {

	@Autowired
	private JavaMailSender sender;

	@Autowired
	private Configuration freemarkerConfig;

	@Autowired
	private AppointmentService appointmentService;

	public void sendEmail(Mail mail, String role) throws Exception {
		MimeMessage message = sender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message);
		Template t = null;
		// Using a subfolder such as /templates here
		freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");

		if (role.equalsIgnoreCase("vendor")) {
			t = freemarkerConfig.getTemplate("vendorEmailTemplate.html");
		} else if (role.equalsIgnoreCase("zonalmpr")) {
			t = freemarkerConfig.getTemplate("zonalEmailTemplate.html");
		}
		String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, mail.getModel());
		helper.setTo(mail.getMailTo());
		helper.setText(text, true);
		helper.setSubject(mail.getMailSubject());

		sender.send(message);
	}

	public void sendEmailWithAttachment(Mail mail, AppointmentDTO appointmentDTO) throws Exception {
		MimeMessage message = sender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		Template t = null;
		// Using a sub folder such as /templates here
		freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");

		t = freemarkerConfig.getTemplate("vendorOrderReview.html");
		// attachment
		// ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
		// Document document = new Document();
		// PdfWriter.getInstance(document, outputstream);
		// document.open();
		// document = appointmentService.generateDocumentForAttachment(document,
		// appointmentDTO);
		// document.close();
		// InputStream attachmentStream = new
		// ByteArrayInputStream(outputstream.toByteArray());
		// InputStreamSource source = new
		// ByteArrayResource(StreamUtils.copyToByteArray(attachmentStream));
		if (appointmentDTO.getBase64PDF() != null) {
			System.out.println("PDF-> "+appointmentDTO.getBase64PDF());
			DataSource source = new ByteArrayDataSource(Base64.getDecoder().decode(appointmentDTO.getBase64PDF()),
					MediaType.APPLICATION_PDF_VALUE);
			helper.addAttachment("apppointment-slip.pdf", source);
		} else {
			System.out.println("Empty PDF->" + appointmentDTO.getBase64PDF());
		}

		String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, mail.getModel());
		helper.setTo(mail.getMailTo());
		helper.setText(text, true);
		helper.setSubject(mail.getMailSubject());
		sender.send(message);
	}

}
