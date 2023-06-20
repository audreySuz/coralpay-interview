package com.example.surepayservice.clients.impl;

import com.example.surepayservice.clients.BankClient;
import com.example.surepayservice.domains.AppResponse;
import com.example.surepayservice.dtos.requests.PaymentRequestDTO;
import com.example.surepayservice.dtos.responses.PaymentResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.Optional;

@Service
@Slf4j
public class DynamoBankClient implements BankClient {
    private final RestTemplate restTemplate;
    private final ModelMapper modelMapper;

    @Value("${db.callback.url}")
    private String dynamobankurl;

    public DynamoBankClient(RestTemplate restTemplate, ModelMapper modelMapper) {
        this.restTemplate = restTemplate;
        this.modelMapper = modelMapper;
    }

    @Override
    public PaymentResponseDTO createBankAccount(PaymentRequestDTO paymentRequestDTO) {
        log.info("Callback URL: {}", dynamobankurl);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<PaymentRequestDTO> requestEntity = new HttpEntity<>(paymentRequestDTO, headers);
        ResponseEntity<AppResponse> responseEntity = restTemplate.exchange(dynamobankurl, HttpMethod.POST, requestEntity, AppResponse.class);
        AppResponse response = responseEntity.getBody();
        log.info("Response retrieved: {}", response);
        return Optional.ofNullable(response)
                .map(AppResponse::getData)
                .map(data -> modelMapper.map(data, PaymentResponseDTO.class))
                .orElse(null);
    }
}
