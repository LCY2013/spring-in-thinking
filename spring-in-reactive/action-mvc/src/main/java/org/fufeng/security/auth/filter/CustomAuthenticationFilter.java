package org.fufeng.security.auth.filter;

import org.fufeng.security.auth.token.CustomAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
        HashMap<String, String> customizedDetails = new HashMap<>();
        customizedDetails.put("RemoteAddress", request.getRemoteAddr());
        customizedDetails.put("RiskLevel", calculateRiskLevel());
        authRequest.setDetails(customizedDetails);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {

        response.sendRedirect("/login.html?error=true");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String name = obtainUsername(request);
        String password = obtainPassword(request);

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = null;
        if (context.getAuthentication() == null) {
            auth = new CustomAuthenticationToken(name, password);
            setDetails(request, (UsernamePasswordAuthenticationToken) auth);
            return this.getAuthenticationManager().authenticate(auth);
        } else {
            auth = context.getAuthentication();
            return auth;
        }
    }

    private String calculateRiskLevel() {
        //pretend complex logic of calculating client risk
        return "LOW";
    }
}
