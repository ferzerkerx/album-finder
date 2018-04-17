package com.ferzerkerx.albumfinder.filter;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class CsrfHeaderFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
        HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (nonNull(csrf)) {
            response.setHeader(csrf.getHeaderName(), csrf.getToken());
        }
//        if (nonNull(csrf)) {
//            Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
//            String token = csrf.getToken();
//            if (isCsrfCookieNotSet(cookie, token)) {
//                cookie = new Cookie("XSRF-TOKEN", token);
//                cookie.setPath("/");
//                response.addCookie(cookie);
//            }
//        }
        filterChain.doFilter(request, response);
    }

    private static boolean isCsrfCookieNotSet(Cookie cookie, String token) {
        return isNull(cookie) || (nonNull(token) && !Objects.equals(token, cookie.getValue()));
    }

}
