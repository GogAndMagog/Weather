package org.fizz_buzz.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.fizz_buzz.service.AuthenticationService;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@WebFilter("/*")
public class AuthorizationFilter extends HttpFilter {

    private final static String COOKIE_SESSION_ID = "SessionId";

    private final String[] ALLOWED_URLS = {"/templates/registration.html",
            "/templates/authentication.html",
            "/register",
            "/authenticate"};

    private AuthenticationService authenticationService;

    @Override
    public void init(FilterConfig config) throws ServletException {
        authenticationService = WebApplicationContextUtils
                .getRequiredWebApplicationContext(config.getServletContext())
                .getBean(AuthenticationService.class);
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        if (Arrays.asList(ALLOWED_URLS).contains(req.getServletPath())) {
            chain.doFilter(req, res);
            return;
        }

        var session = Arrays.stream(req.getCookies())
                .filter(cookie ->
                        cookie.getName().equals(COOKIE_SESSION_ID)
                                && !cookie.getValue().isEmpty())
                .findAny();
        if (session.isPresent()) {
            var sessionId = UUID.fromString(session.get().getValue());

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
