package com.flowna.app.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.util.Map;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;

    @Async
    public void sendSimpleEmail(String toEmail,
                                String subject,
                                String templateName,
                                Map<String, Object>variables) {
    try {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        // Set 'multipart' to true to send attachments or inline resources
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        // Prepare the context and process the template
        Context context = new Context();
        context.setVariables(variables);
        String htmlContent = templateEngine.process(templateName, context);

        helper.setFrom("confirmation@profy.com");
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // Set 'true' to indicate HTML content

        mailSender.send(mimeMessage);

        System.out.println("Mail sent successfully to " + toEmail);
    }catch(MessagingException e) {
        System.err.println("Failed to send email to " + toEmail + ": " + e.getMessage());
        // Optionally, you can rethrow the exception or handle it according to your application's needs
    }

}




}
