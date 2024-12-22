package com.flowna.app.stripe;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCheckoutSessionResponse {
    private String sessionId;
}
