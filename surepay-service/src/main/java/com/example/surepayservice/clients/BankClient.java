package com.example.surepayservice.clients;

import com.example.surepayservice.dtos.requests.PaymentRequestDTO;
import com.example.surepayservice.dtos.responses.PaymentResponseDTO;

public interface BankClient {

    PaymentResponseDTO createBankAccount(PaymentRequestDTO paymentRequestDTO);
}
