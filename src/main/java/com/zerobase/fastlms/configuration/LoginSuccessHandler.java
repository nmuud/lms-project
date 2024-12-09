package com.zerobase.fastlms.configuration;

import com.zerobase.fastlms.log.model.AccessParam;
import com.zerobase.fastlms.log.model.LogParam;
import com.zerobase.fastlms.log.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final LogService logService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        AccessParam accessParam = logService.checkData(request);

        logService.save(LogParam.builder()
                .id(request.getParameter("username"))
                .loginDt(LocalDateTime.now())
                .accessIp(accessParam.getAccessIp())
                .accessUserAgent(accessParam.getAccessUserAgent())
                .build());

        response.sendRedirect("/");
    }

}
