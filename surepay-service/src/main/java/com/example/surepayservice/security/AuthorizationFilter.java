package com.example.surepayservice.security;

import com.example.surepayservice.models.Merchant;
import com.example.surepayservice.repositories.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final MerchantRepository merchantRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //get authorization header
        String authorizationHeader = request.getHeader("Authorization");
        //check if authorization header is null or does not start with Basic
        if(authorizationHeader == null || !authorizationHeader.startsWith("Basic")){
            filterChain.doFilter(request, response);
            return;
        }
        //get the base64 encoded username and password
        String base64EncodedUsernameAndPassword = authorizationHeader.substring("Basic".length()).trim();
        //decode the username and password
        String decodedUsernameAndPassword = new String(Base64.getDecoder().decode(base64EncodedUsernameAndPassword));
        //split the username and password as merchantCode and apiSecret
        String[] usernameAndPassword = decodedUsernameAndPassword.split(":");
        //check if username and password is not equal to 2
        if(usernameAndPassword.length != 2){
            filterChain.doFilter(request, response);
            return;
        }
        //merchantCode
        String merchantCode = usernameAndPassword[0];
        //apiSecret
        String apiSecret = usernameAndPassword[1];
        //find merchant by merchantCode
        Merchant merchant = merchantRepository.findByMerchantCode(merchantCode)
                .filter(m-> isValidApiSecret(m, apiSecret))
                .orElse(null);
        //check if merchant is null
        if(merchant != null){
          //create username password authentication and also set into security context
            MerchantAuthentication authenticationToken = new MerchantAuthentication(merchant);
            authenticationToken.setAuthenticated(true);
            //set authentication token into security context
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        //do filter
        filterChain.doFilter(request, response);

    }

    private boolean isValidApiSecret(Merchant m, String apiSecret) {
        return bCryptPasswordEncoder.matches(apiSecret, m.getApiSecret());
    }
}