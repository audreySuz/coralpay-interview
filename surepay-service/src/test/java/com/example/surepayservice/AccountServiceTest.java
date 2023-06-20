package com.example.surepayservice;

import com.example.surepayservice.clients.BankClient;
import com.example.surepayservice.clients.impl.DynamoBankClient;
import com.example.surepayservice.domains.AppResponse;
import com.example.surepayservice.dtos.requests.AccountRequest;
import com.example.surepayservice.dtos.responses.PaymentResponseDTO;
import com.example.surepayservice.enums.Bank;
import com.example.surepayservice.enums.PaymentType;
import com.example.surepayservice.exceptions.NotFoundException;
import com.example.surepayservice.models.AccountType;
import com.example.surepayservice.models.Merchant;
import com.example.surepayservice.repositories.MerchantRepository;
import com.example.surepayservice.repositories.VirtualAccountRepository;
import com.example.surepayservice.services.AccountService;
import com.example.surepayservice.services.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountServiceTest {
    private AccountService accountService;
    private BankClient dynamoBank;
    private VirtualAccountRepository virtualAccountRepository;
    private MerchantRepository merchantRepository;

    @BeforeEach
    void setUp() {
        dynamoBank = mock(DynamoBankClient.class);
        Map<Bank, BankClient> bankClients = new EnumMap<Bank, BankClient>(Bank.class);
        bankClients.put(Bank.DB, dynamoBank);
        merchantRepository = mock(MerchantRepository.class);
        virtualAccountRepository = mock(VirtualAccountRepository.class);
        accountService = new AccountServiceImpl(bankClients,virtualAccountRepository,merchantRepository);
    }

    @Test
    void generateAccount_ShouldCallServiceWithRequestDto() {
        //subs
        when(dynamoBank.createBankAccount(any())).thenReturn(generateTestpaymetResponse());
        when(merchantRepository.findMerchantByMerchantCode(anyString())).thenReturn(Optional.of(generateTestMerchant()));
        when(virtualAccountRepository.save(any())).then(invocation -> invocation.getArgument(0));

        //given
        AccountRequest requestDto = AccountRequest.builder()
                .preferredBank(Bank.DB)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("1234567890")
                .merchantCode("M123")
                .callBackUrl("https://example.com/callback")
                .paymentType(PaymentType.PWT)
                .build();
        AppResponse expectedResponse = new AppResponse(200, "Account created successfully",
                "Account number generated", mockPaymentResponseDTO(), null);
        AppResponse actualResponse = accountService.generateAccount(requestDto);
        assertEquals(expectedResponse.getStatus(), actualResponse.getStatus());
        assertEquals(expectedResponse.getMessage(), actualResponse.getMessage());
        assertEquals(expectedResponse.getSupportDescriptiveMessage(), actualResponse.getSupportDescriptiveMessage());
        assertEquals(expectedResponse.getData(), actualResponse.getData());
        assertEquals(expectedResponse.getError(), actualResponse.getError());
    }

    private Merchant generateTestMerchant() {
        Long id = 1L;
        String merchantCode = "M123";
        String name = "Example Merchant";
        String email = "merchant@example.com";
        String phone = "1234567890";
        AccountType accountType =  AccountType.builder()
                .id(1L)
                .currency("USD")
                .expiryDateTime("30")
                .collectionFeeShare(0.5f)
                .allowPartialPayments(true)
                .allowOverPayments(false)
                .paymentType("Credit Card")
                .build();

        return Merchant.builder()
                .id(id)
                .merchantCode(merchantCode)
                .name(name)
                .email(email)
                .phone(phone)
                .accountType(accountType)
                .build();
    }

    private static PaymentResponseDTO mockPaymentResponseDTO() {
        return PaymentResponseDTO.builder()
                .referenceNumber("REF123")
                .statusMessage("Success")
                .requestAmount(100.0f)
                .totalPaymentAmount(100.0f)
                .currency("USD")
                .paymentType("Credit Card")
                .accountNumber("1234567890")
                .accountName("John Doe")
                .expiryDateTimeUTC("30")
                .isPayerDynamoAccountHolder(true)
                .build();
    }

    private PaymentResponseDTO generateTestpaymetResponse() {
        String referenceNumber = "REF123";
        String statusMessage = "Success";
        float requestAmount = 100.0f;
        float totalPaymentAmount = 100.0f;
        String currency = "USD";
        String paymentType = "Credit Card";
        String accountNumber = "1234567890";
        String accountName = "John Doe";
        String expiryDateTimeUTC = "30";
        boolean isPayerDynamoAccountHolder = true;
        return PaymentResponseDTO.builder()
                .referenceNumber(referenceNumber)
                .statusMessage(statusMessage)
                .requestAmount(requestAmount)
                .totalPaymentAmount(totalPaymentAmount)
                .currency(currency)
                .paymentType(paymentType)
                .accountNumber(accountNumber)
                .accountName(accountName)
                .expiryDateTimeUTC(expiryDateTimeUTC)
                .isPayerDynamoAccountHolder(isPayerDynamoAccountHolder)
                .build();
    }

    @Test
    void generateAccount_WhenMerchantNotFound_ShouldThrowNotFoundException() {
        // Create a sample AccountRequest
        AccountRequest requestDto = AccountRequest.builder()
                // Set the necessary fields
                .build();
        when(dynamoBank.createBankAccount(any())).thenReturn(generateTestpaymetResponse());
        when(merchantRepository.findMerchantByMerchantCode(anyString())).thenReturn(Optional.of(generateTestMerchant()));
        when(virtualAccountRepository.save(any())).then(arg -> arg.getArgument(0));
        // Mock the behavior of the MerchantRepository to return an empty optional
        when(merchantRepository.findMerchantByMerchantCode(requestDto.getMerchantCode()))
                .thenReturn(Optional.empty());

        // Assertions
        assertThrows(NotFoundException.class, () -> accountService.generateAccount(requestDto));
        // Additional assertions if needed
    }

}
