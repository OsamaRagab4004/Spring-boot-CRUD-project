package com.flowna.app.stripe;

import com.stripe.model.Event;
import com.stripe.net.Webhook;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/swagger-ui/")
public class StripeWebhookController {

    private static final String ENDPOINT_SECRET = "whsec_qc6IFPAKRHJv6KdXZRUEQzifMX6hGHOm";

    @PostMapping("/stripe")
    public ResponseEntity<String> handleStripeWebhook(HttpServletRequest request, @RequestBody String payload) {
        String sigHeader = request.getHeader("Stripe-Signature");
        Event event = null;

        try {
            event = Webhook.constructEvent(
                    payload, sigHeader, ENDPOINT_SECRET
            );
        } catch (Exception e) {
            // Invalid signature
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        }

        // Handle the event
        switch (event.getType()) {
            case "invoice.payment_succeeded":
                handleInvoicePaymentSucceeded(event);
                break;
            // ... handle other event types
            default:
                // Unexpected event type
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        }

        return ResponseEntity.ok("");
    }


    private void handleInvoicePaymentSucceeded(Event event) {
        // Handle successful invoice payment (e.g., subscription renewal)
        // Deserialize and process event data as needed
        System.out.println("Invoice payment succeeded.");
    }
}
