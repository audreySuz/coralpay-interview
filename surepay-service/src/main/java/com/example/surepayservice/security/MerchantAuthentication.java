package com.example.surepayservice.security;

import com.example.surepayservice.models.Merchant;
import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.ArrayList;

public class MerchantAuthentication extends AbstractAuthenticationToken {

    private final Merchant merchant;

    public MerchantAuthentication(Merchant merchant) {
        super(new ArrayList<>());
        this.merchant = merchant;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return merchant;
    }

}
