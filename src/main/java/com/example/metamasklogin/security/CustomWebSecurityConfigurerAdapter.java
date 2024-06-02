package com.example.metamasklogin.security;

import com.example.metamasklogin.model.MyAwesomeUser;
import com.example.metamasklogin.repo.UserRepository;
import com.example.metamasklogin.service.MyUserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.Set;

@Configuration
@EnableWebSecurity
@Slf4j
public class CustomWebSecurityConfigurerAdapter {



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsService userDetailsService, MetamaskLoginFilter metamaskLoginFilter, AuthenticationManager authenticationManager) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(this::pathMatcher)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .userDetailsService(userDetailsService)
                .addFilterBefore(new BasicAuthenticationFilter(authenticationManager), AuthorizationFilter.class)
                .addFilterAfter(metamaskLoginFilter, BasicAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authProvider);
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        MyAwesomeUser roie = MyAwesomeUser.builder()
                .username("roie")
                .password(passwordEncoder.encode("1234"))
                .enabled(true)
                .authorities(Set.of())
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .nonce(null)
                .address("0xc1b7399e7bece19885c88265407368ad71e11451")
                .build();
        userRepository.save(roie);
        return new MyUserDetailService(userRepository);
    }

    @SuppressWarnings("deprecation")
    @Bean
    public NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    private void pathMatcher(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry auth) {
        auth.requestMatchers(HttpMethod.GET, "/user/nonce/**").permitAll();
        auth.anyRequest().authenticated();
    }
}