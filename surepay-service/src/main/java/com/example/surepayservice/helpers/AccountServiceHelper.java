package com.example.surepayservice.helpers;

import com.example.surepayservice.dtos.requests.AccountRequest;
import com.example.surepayservice.dtos.requests.PaymentRequestDTO;
import com.example.surepayservice.dtos.responses.PaymentResponseDTO;
import com.example.surepayservice.enums.Status;
import com.example.surepayservice.models.Customer;
import com.example.surepayservice.models.Merchant;
import com.example.surepayservice.models.VirtualAccount;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class AccountServiceHelper {
    private AccountServiceHelper() {
    }
    public static VirtualAccount buildVirtualAccount(Optional<Customer> customerOptional, PaymentResponseDTO bankAccount) {
        return VirtualAccount.builder()
                .accountNumber(bankAccount.getAccountNumber())
                .accountID(String.valueOf(UUID.randomUUID()))
                .createdDate(LocalDateTime.now())
                .customer(customerOptional.orElseThrow(() -> new IllegalArgumentException("Customer not found")))
                .createdBy("system")
                .refference(bankAccount.getReferenceNumber())
                .status(Status.ACTIVE)
                .build();
    }

    public static PaymentRequestDTO createBankRequest(AccountRequest requestDto, String reference, Customer customer, Merchant merchant) {
        return PaymentRequestDTO.builder()
                .referenceNumber(reference)
                .amount(requestDto.getAmount())
                .currency(merchant.getAccountType().getCurrency())
                .payerName(requestDto.getFirstName() + " " + requestDto.getLastName())
                .payerPhoneNumber(requestDto.getPhone())
                .payerEmail(requestDto.getEmail())
                .payeeName(merchant.getName())
                .expiryDateTimeUTC(String.valueOf(LocalDateTime.now().plus(Duration.ofMinutes(Long.parseLong(merchant.getAccountType().getExpiryDateTime())))))
                .payerCollectionFeeShare(String.valueOf(merchant.getAccountType().getCollectionFeeShare()))
                .payeeCollectionFeeShare(String.valueOf(merchant.getAccountType().getCollectionFeeShare()))
                .allowPartialPayments(String.valueOf(merchant.getAccountType().isAllowOverPayments()))
                .allowOverPayments(merchant.getAccountType().getCurrency())
                .callBackUrl(requestDto.getCallBackUrl())
                .paymentType(requestDto.getPaymentType().name())
                .build();
    }
    public static Customer buildCustomer(AccountRequest requestDto) {
        Customer customer = Customer.builder()
                .customerId(String.valueOf(UUID.randomUUID()))
                .firstName(requestDto.getFirstName())
                .lastName(requestDto.getLastName())
                .email(requestDto.getEmail())
                .phone(requestDto.getPhone())
                .build();
        return customer;
    }



}
