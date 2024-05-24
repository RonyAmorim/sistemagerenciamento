package br.com.sistemagerenciamento.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendEmail(String to, String subject, String templateName, Context context) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8"); // Habilitar HTML e UTF-8
            helper.setTo(to);
            helper.setSubject(subject);

            // Renderizar o template com o Thymeleaf
            String htmlContent = templateEngine.process(templateName, context);

            helper.setText(htmlContent, true); // Definir como HTML

        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar email", e);
        }

        javaMailSender.send(mimeMessage);
    }
}
