package com.example.surepayservice.config;

import com.example.surepayservice.security.AuthorizationFilter;
import com.example.surepayservice.security.SecurityAuthenticationEntrypoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final AuthorizationFilter authorizationFilter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/swagger-ui/**","/swagger-ui.html","/configuration/**",
                        "/swagger-resources/**","/v3/api-docs/**","/webjars/**","/merchant/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(authorizationFilter, BasicAuthenticationFilter.class)
                .httpBasic().disable()
                .formLogin().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new SecurityAuthenticationEntrypoint())
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
                .build();
    }


}