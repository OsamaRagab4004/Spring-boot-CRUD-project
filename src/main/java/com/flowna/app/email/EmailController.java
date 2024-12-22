package com.flowna.app.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public void sendEmail(@RequestParam String toEmail) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("name", toEmail);
        variables.put("token", "someUniqueToken");
        variables.put("confirmUrl","http://localhost:5173/");

        emailService.sendSimpleEmail(
                toEmail,
                "Email Confirmation",
                "emailTemplate.html",
                variables
        );


    }


}
