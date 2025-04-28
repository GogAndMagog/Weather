package org.fizz_buzz.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.fizz_buzz.model.Session;
import org.fizz_buzz.model.User;
import org.fizz_buzz.service.SessionService;
import org.fizz_buzz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import java.util.Objects;
import java.util.UUID;

public class UserLoginInterceptor implements HandlerInterceptor {

    private SessionService sessionService;

    public UserLoginInterceptor() {
    }

    @Autowired
    public void setSessionService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {

        Session session;
        User user;

        try {
            var sessionId = UUID.fromString(Objects.requireNonNull(WebUtils.getCookie(request, "SessionId")).getValue());
            session = sessionService.getSession(sessionId);
            user = session.getUser();

            modelAndView.addObject("login", user.getLogin());
        } catch (Exception ignored) {
        }
    }
}
