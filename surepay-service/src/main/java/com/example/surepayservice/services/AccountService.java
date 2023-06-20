package com.example.surepayservice.services;

import com.example.surepayservice.domains.AppResponse;
import com.example.surepayservice.dtos.requests.AccountRequest;

public interface AccountService {
    AppResponse generateAccount(AccountRequest requestDto);

}
