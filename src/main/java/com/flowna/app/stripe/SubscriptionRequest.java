package com.flowna.app.stripe;

import lombok.Data;

@Data
public class SubscriptionRequest{
    private String customerEmail;
    private String priceId;
}
