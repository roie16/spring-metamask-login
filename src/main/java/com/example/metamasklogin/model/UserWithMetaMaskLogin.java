package com.example.metamasklogin.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class UserWithMetaMaskLogin extends User {

    private final String publicKey;

    public UserWithMetaMaskLogin(String username, String password, Collection<? extends GrantedAuthority> authorities, String publicKey) {
        super(username, password, authorities);
        this.publicKey = publicKey;
    }

}
