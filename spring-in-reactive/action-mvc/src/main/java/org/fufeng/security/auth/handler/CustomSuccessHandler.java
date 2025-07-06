package org.fufeng.security.auth.handler;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@Slf4j
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Getter
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        logger.info("Recorded successful login from host");

        String targetUrl = targetUrl(authentication);
        if (response.isCommitted()) {
            System.out.println("Can't redirect");
            return;
        }
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String targetUrl(Authentication authentication) {
        String url = "";

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = new ArrayList<String>();
        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        System.out.println(roles);
        if (isUserRole(roles)) {
            url = "/subscription";
        } else if (isSudoRole(roles)) {
            url = "/";
        } else {
            url = "/";
        }

        return url;
    }

    private boolean isUserRole(List<String> roles) {
        if (roles.contains("ROLE_USER")) {
            return true;
        }
        return false;
    }

    private boolean isSudoRole(List<String> roles) {
        if (roles.contains("ROLE_SUDO")) {
            return true;
        }
        return false;
    }
}
