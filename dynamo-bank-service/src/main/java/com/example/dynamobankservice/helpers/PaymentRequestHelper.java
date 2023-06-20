package com.example.dynamobankservice.helpers;

import com.example.dynamobankservice.dtos.requests.PaymentRequestDTO;
import com.example.dynamobankservice.dtos.responses.PaymentResponseDTO;
import com.example.dynamobankservice.models.Payee;
import com.example.dynamobankservice.models.Payer;
import com.example.dynamobankservice.models.PaymentRequest;

import java.time.LocalDateTime;

public class PaymentRequestHelper {
    private PaymentRequestHelper() {
    }

    public static PaymentRequest convertToPaymentRequest(PaymentRequestDTO paymentRequestDTO) {
        return PaymentRequest.builder()
                .referenceNumber(paymentRequestDTO.getReferenceNumber())
                .amount(Float.valueOf(paymentRequestDTO.getAmount()))
                .currency(paymentRequestDTO.getCurrency())
                .payer(buildPayer(paymentRequestDTO))
                .payee(buildPayee(paymentRequestDTO))
                .expiryDateTime(LocalDateTime.parse(paymentRequestDTO.getExpiryDateTimeUTC()))
                .payerCollectionFeeShare(Float.valueOf(paymentRequestDTO.getPayerCollectionFeeShare()))
                .payeeCollectionFeeShare(Float.valueOf(paymentRequestDTO.getPayeeCollectionFeeShare()))
                .allowPartialPayments(Boolean.parseBoolean(paymentRequestDTO.getAllowPartialPayments()))
                .allowOverPayments(Boolean.parseBoolean(paymentRequestDTO.getAllowOverPayments()))
                .callBackUrl(paymentRequestDTO.getCallBackUrl())
                .paymentType(paymentRequestDTO.getPaymentType())
                .build();
    }

    public static PaymentResponseDTO convertToPaymentResponseDTO(PaymentRequest paymentRequest) {
        return PaymentResponseDTO.builder()
                .referenceNumber(paymentRequest.getReferenceNumber())
                .statusMessage("success")
                .requestAmount(paymentRequest.getAmount())
                .totalPaymentAmount(calculateTotalPaymentAmount(paymentRequest))
                .currency(paymentRequest.getCurrency())
                .accountNumber(paymentRequest.getAccountNumber())
                .accountName(paymentRequest.getPayee().getName()+"/"+paymentRequest.getPayer().getName())
                .paymentType(paymentRequest.getPaymentType())
                .expiryDateTimeUTC(String.valueOf(paymentRequest.getExpiryDateTime()))
                .isPayerDynamoAccountHolder(false)
                .build();
    }

    private static float calculateTotalPaymentAmount(PaymentRequest paymentRequest) {
        float requestAmount = paymentRequest.getAmount();
        float payerCollectionFee = requestAmount * paymentRequest.getPayerCollectionFeeShare();
        return requestAmount + payerCollectionFee;
    }


    private static Payer buildPayer(PaymentRequestDTO paymentRequestDTO) {
        return Payer.builder()
                .name(paymentRequestDTO.getPayeeName())
                .phoneNumber(paymentRequestDTO.getPayerPhoneNumber())
                .email(paymentRequestDTO.getPayerEmail())
                .build();
    }

    private static Payee buildPayee(PaymentRequestDTO paymentRequestDTO) {
        return Payee.builder()
                .name(paymentRequestDTO.getPayerName())
                .build();
    }
}
