package com.example.dynamobankservice.dtos.responses;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PaymentResponseDTO {
    private String referenceNumber;
    private String statusCode;
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
