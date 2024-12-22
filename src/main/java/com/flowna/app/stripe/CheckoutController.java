package com.flowna.app.stripe;


import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/swagger-ui/")
public class CheckoutController {

    private final CheckoutService checkoutService;
    @Autowired
    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/create-checkout-session")
    public ResponseEntity<CreateCheckoutSessionResponse> createCheckoutSession(@RequestBody SubscriptionRequest request) {
        try {
            Session session = checkoutService.createCheckoutSession(request);
            return ResponseEntity.ok(new CreateCheckoutSessionResponse(session.getId()));
        } catch (StripeException e) {
            // Handle exception
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }


}
