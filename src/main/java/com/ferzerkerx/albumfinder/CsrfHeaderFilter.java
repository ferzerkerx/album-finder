package com.ferzerkerx.albumfinder;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class CsrfHeaderFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
        HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrf != null) {
            Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
            String token = csrf.getToken();
            if (isCsrfCookieNotSet(cookie, token)) {
                cookie = new Cookie("XSRF-TOKEN", token);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
        filterChain.doFilter(request, response);
    }

    private static boolean isCsrfCookieNotSet(Cookie cookie, String token) {
        return Objects.isNull(cookie) || (token != null && !token.equals(cookie.getValue()));
    }

}
