package com.example.dynamobankservice.services.impl;

import com.example.dynamobankservice.domains.AppResponse;
import com.example.dynamobankservice.dtos.requests.PaymentRequestDTO;
import com.example.dynamobankservice.dtos.responses.PaymentResponseDTO;
import com.example.dynamobankservice.helpers.PaymentRequestHelper;
import com.example.dynamobankservice.models.Payee;
import com.example.dynamobankservice.models.Payer;
import com.example.dynamobankservice.models.PaymentRequest;
import com.example.dynamobankservice.repositories.PaymentRepository;
import com.example.dynamobankservice.services.PaymentRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PaymentRequestServiceImpl implements PaymentRequestService {
    private final PaymentRepository paymentRepository;


    @Override
    public AppResponse createPaymentRequest(PaymentRequestDTO requestDto) {
        PaymentRequest paymentRequest = PaymentRequestHelper.convertToPaymentRequest(requestDto);
        Random random = new Random();
        String accountNumber= String.valueOf(random.nextInt(1000000));
        paymentRequest.setAccountNumber(accountNumber);
        PaymentRequest savedPaymentRequest = paymentRepository.save(paymentRequest);
        return new AppResponse(HttpStatus.OK.value(), "Account created successfully", "Account number generated",
                PaymentRequestHelper.convertToPaymentResponseDTO(savedPaymentRequest), null);
    }

}
