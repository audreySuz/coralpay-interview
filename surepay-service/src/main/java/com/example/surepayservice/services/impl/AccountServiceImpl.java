package com.example.surepayservice.services.impl;

import com.example.surepayservice.clients.BankClient;
import com.example.surepayservice.clients.impl.DynamoBankClient;
import com.example.surepayservice.domains.AppResponse;
import com.example.surepayservice.dtos.requests.AccountRequest;
import com.example.surepayservice.dtos.requests.PaymentRequestDTO;
import com.example.surepayservice.dtos.responses.PaymentResponseDTO;
import com.example.surepayservice.enums.Bank;
import com.example.surepayservice.enums.Status;
import com.example.surepayservice.exceptions.NotFoundException;
import com.example.surepayservice.helpers.AccountServiceHelper;
import com.example.surepayservice.models.Customer;
import com.example.surepayservice.models.Merchant;
import com.example.surepayservice.models.VirtualAccount;
import com.example.surepayservice.repositories.CustomerRepository;
import com.example.surepayservice.repositories.MerchantRepository;
import com.example.surepayservice.repositories.VirtualAccountRepository;
import com.example.surepayservice.services.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final Map<Bank, BankClient> bankClients;
    private final VirtualAccountRepository virtualAccountRepository;
    private final MerchantRepository merchantRepository;

    /**
     * This method generates an account number by calling the bank api to create an account for the customer.
     * @return account number
     */
    @Override
    public AppResponse generateAccount(AccountRequest requestDto) {
        String reference = generateReferenceNumber();
        Optional<Merchant> merchantOptional = merchantRepository.findMerchantByMerchantCode(requestDto.getMerchantCode());
        if (!merchantOptional.isPresent()) {
            throw new NotFoundException("Merchant Not Found");
        }
        Customer customer = AccountServiceHelper.buildCustomer(requestDto);
        Bank bank = requestDto.getPreferredBank();
        BankClient bankClient = bankClients.get(bank);

        PaymentRequestDTO bankRequest = AccountServiceHelper.createBankRequest(requestDto, reference, customer, merchantOptional.get());

        PaymentResponseDTO bankAccount = bankClient.createBankAccount(bankRequest);

        VirtualAccount virtualAccount = AccountServiceHelper.buildVirtualAccount(Optional.of(customer), bankAccount);
        virtualAccountRepository.save(virtualAccount);

        return new AppResponse(HttpStatus.OK.value(), "Account created successfully", "Account number generated",
                bankAccount, null);
    }


    /**
     * This method generates a reference number for the transaction
     * @return reference number
     */
    private String generateReferenceNumber() {
        long timestamp = System.currentTimeMillis();
        int randomNumber = new Random().nextInt(1000000);
        return timestamp + "_" + randomNumber;
    }

}
