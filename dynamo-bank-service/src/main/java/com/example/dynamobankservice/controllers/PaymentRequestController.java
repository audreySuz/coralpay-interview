package com.example.dynamobankservice.controllers;

import com.example.dynamobankservice.domains.AppResponse;
import com.example.dynamobankservice.dtos.requests.PaymentRequestDTO;

import com.example.dynamobankservice.services.PaymentRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dynamobank")
@RequiredArgsConstructor
public class PaymentRequestController {
    private final PaymentRequestService paymentRequestService;

    @PostMapping("/createAccount")
    public ResponseEntity<AppResponse> createPaymentRequest(@RequestBody PaymentRequestDTO paymentRequestDTO) {
        return ResponseEntity.ok().body(paymentRequestService.createPaymentRequest(paymentRequestDTO));
    }

}
