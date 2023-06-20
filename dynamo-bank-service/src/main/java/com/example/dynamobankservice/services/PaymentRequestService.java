package com.example.dynamobankservice.services;

import com.example.dynamobankservice.domains.AppResponse;
import com.example.dynamobankservice.dtos.requests.PaymentRequestDTO;

public interface PaymentRequestService {
    AppResponse createPaymentRequest(PaymentRequestDTO requestDto);
}
