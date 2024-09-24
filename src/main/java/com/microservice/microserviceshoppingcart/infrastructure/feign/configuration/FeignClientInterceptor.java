package com.microservice.microserviceshoppingcart.infrastructure.feign.configuration;

import com.microservice.microserviceshoppingcart.infrastructure.security.SecurityAdapter;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import feign.RequestInterceptor;

import static com.microservice.microserviceshoppingcart.utils.Constants.AUTH_TOKEN;

@Component
@RequiredArgsConstructor
public class FeignClientInterceptor implements RequestInterceptor {

    private final SecurityAdapter securityAdapter;

    @Override
    public void apply(RequestTemplate template) {
        String token = securityAdapter.getToken();

        if (token != null && !token.isEmpty()) {
            template.header(AUTH_TOKEN, token);
        }
    }
}
