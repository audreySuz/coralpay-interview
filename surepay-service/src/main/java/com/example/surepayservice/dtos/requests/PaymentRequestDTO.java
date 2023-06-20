package com.example.surepayservice.dtos.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentRequestDTO {
    private String referenceNumber;
    private String amount;
    private String currency;
    private String payerName;
    private String payerPhoneNumber;
    private String payerEmail;
    private String payeeName;
    private String expiryDateTimeUTC;
    private String payerCollectionFeeShare;
    private String payeeCollectionFeeShare;
    private String allowPartialPayments;
    private String allowOverPayments;
    private String callBackUrl;
    private String paymentType;
}