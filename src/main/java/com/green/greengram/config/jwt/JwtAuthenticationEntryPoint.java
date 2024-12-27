package com.green.greengram.config.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

//시큐리티에 등록이 필요하다. (WebSecurityConfig 클래스에서 DI 해줘야한다)
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final HandlerExceptionResolver resolver;

    // Qualifier 에노테이션은 스프링컨테이너로부터 DI 를 받을 때 생성되어있는 빈이 여러개일때 빈을 선택할 수 있음
    public JwtAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }


    @Override
    public void commence(HttpServletRequest request
                        , HttpServletResponse response
                        , AuthenticationException authException) throws IOException, ServletException {


        // GlobalExceptionHandler 에서 exception 을 잡을 수 있도록 연결하는 작업.
        resolver.resolveException(request, response, null, (Exception) request.getAttribute("exception"));
    }
}
