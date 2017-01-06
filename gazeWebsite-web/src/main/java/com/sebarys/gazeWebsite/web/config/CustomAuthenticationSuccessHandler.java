package com.sebarys.gazeWebsite.web.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        final String url = request.getParameter("url");
        if(url == null) {
            redirectStrategy.sendRedirect(request, response, "/#");
        }

        StringBuilder stringBuilder = new StringBuilder();
        
        for(GrantedAuthority grantedAuthority: authentication.getAuthorities()){
            if("ROLE_USER".equals(grantedAuthority.getAuthority())){
                stringBuilder.append("/#");
                break;
            } else if("ROLE_ADMIN".equals(grantedAuthority.getAuthority())) {
                stringBuilder.append("/#");
                break;
            }
        }
        stringBuilder.append(url);
        redirectStrategy.sendRedirect(request, response, stringBuilder.toString());
    }
}
