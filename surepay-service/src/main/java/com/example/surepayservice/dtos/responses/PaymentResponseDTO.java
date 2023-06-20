package com.example.surepayservice.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {
    private String referenceNumber;
    private String statusMessage;
    private float requestAmount;
    private float totalPaymentAmount;
    private String currency;
    private String paymentType;
    private String accountNumber;
    private String accountName;
    private String expiryDateTimeUTC;
    private boolean isPayerDynamoAccountHolder;
}
