package com.ferzerkerx.albumfinder.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

@RestController
public class AppController {

    @PostMapping("/user")
    public Object getUserInfo(Authentication authentication) {
        requireNonNull(authentication);
        return authentication.getPrincipal();
    }

    @PostMapping(value="/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (nonNull(auth)) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

    }
}
