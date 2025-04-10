package org.fizz_buzz.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.fizz_buzz.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.util.UUID;

@Component(value = "authenticationFilter")
public class AuthenticationFilter extends HttpFilter {

    private final static String COOKIE_SESSION_ID = "SessionId";

    private final String[] ALLOWED_URLS = {
            "/register",
            "/css/",
            "/js/",
            "/images/",
            "/authenticate"};

    @Autowired
    private AuthenticationService authenticationService;

    public AuthenticationFilter(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        for (String url : ALLOWED_URLS) {
            if (req.getServletPath().startsWith(url)) {
                chain.doFilter(req, res);
                return;
            }
        }

        var session = WebUtils.getCookie(req, COOKIE_SESSION_ID);

        if (session != null &&
            session.getValue() != null &&
            !session.getValue().isEmpty()) {

            var sessionId = UUID.fromString(session.getValue());

            if (authenticationService.authenticate(sessionId)) {

                chain.doFilter(req, res);
            } else {

                res.sendRedirect("register");
            }
        } else {

            res.sendRedirect("register");
        }

    }
}
