package com.flowna.app.stripe;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.stereotype.Service;
import com.stripe.param.checkout.SessionCreateParams;
@Service
public class CheckoutService {
    private static final String DOMAIN = "http://localhost:5173";


    public Session createCheckoutSession(SubscriptionRequest subscriptionRequest) throws StripeException {
        // Create session params
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .setCustomerEmail(subscriptionRequest.getCustomerEmail())
                .setSuccessUrl(DOMAIN + "/success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(DOMAIN + "/cancel")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPrice(subscriptionRequest.getPriceId())
                                .build())
                .build();

        // Create session
        return Session.create(params);
    }

}
