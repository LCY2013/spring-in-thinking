package org.fufeng.security.auth.handler;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class CustomFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Getter
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        logger.info("Record unsuccessful login attempt");
        String targetUrl = "";
        if (exception instanceof BadCredentialsException) {
            targetUrl = "/login?error=" + exception.getMessage();
        } else {
            targetUrl = "/login?error=" + true;
        }

        if (response.isCommitted()) {
            System.out.println("Internal problem in redirection");
            return;
        }

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

}
