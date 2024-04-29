package com.example.metamasklogin.security;

import com.example.metamasklogin.service.NonceService;
import com.example.metamasklogin.service.SigValidationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

import static org.springframework.util.StringUtils.hasText;

@Component
@RequiredArgsConstructor
public class MetamaskLoginFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final SigValidationService sigValidationService;
    private final NonceService nonceService;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        String address = getAddress(request);
        if (hasText(address) && sigValidationService.isSignatureValid(request.getHeader("signature"), address, nonceService.getNonce(address))) {
            nonceService.replaceNonce(address);
            UserDetails user = userDetailsService.loadUserByUsername(request.getHeader("name"));
            SecurityContextHolder.setContext(new SecurityContextImpl(UsernamePasswordAuthenticationToken.authenticated(user.getUsername(), null, user.getAuthorities())));
        }
        filterChain.doFilter(request, response);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private String getAddress(@NotNull HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("name"))
                .map(userDetailsService::loadUserByUsername)
                .map(userDetails -> userDetails.getAuthorities().stream().findFirst().get().getAuthority())
                .orElse("");
    }
}
