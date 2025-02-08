package org.fizz_buzz.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.fizz_buzz.service.AuthenticationAndAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@Component
@WebFilter("/*")
public class AuthorizationFilter extends HttpFilter {

    private final static String COOKIE_SESSION_ID = "SessionId";

    @Autowired
    private AuthenticationAndAuthorizationService authenticationAndAuthorizationService;

    public AuthorizationFilter(AuthenticationAndAuthorizationService authenticationAndAuthorizationService) {
        this.authenticationAndAuthorizationService = authenticationAndAuthorizationService;
    }

//    @Override
//    public void init(FilterConfig config) throws ServletException {
//        authenticationAndAuthorizationService = WebApplicationContextUtils
//                .getRequiredWebApplicationContext(config.getServletContext())
//                .getBean(AuthenticationAndAuthorizationService.class);
//    }

//    @Autowired
//    public AuthorizationFilter(AuthenticationAndAuthorizationService authenticationAndAuthorizationService) {
//        this.authenticationAndAuthorizationService = authenticationAndAuthorizationService;
//    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(req, res);
//        Arrays.stream(req.getCookies())
//                .filter(cookie ->
//                        cookie.getName().equals(COOKIE_SESSION_ID)
//                                && !cookie.getValue().isEmpty())
//                .findAny()
//                .ifPresent(cookie -> {
//                    var sessionId = UUID.fromString(cookie.getValue());
//
//                    if (authenticationAndAuthorizationService.isAuthorized(sessionId))
//                    {
//                        try {
//                            chain.doFilter(req, res);
//                        } catch (IOException | ServletException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                    else {
//                        try {
//                            res.sendRedirect("greetings");
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
//                    }
//                });

    }
}
