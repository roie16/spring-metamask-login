package com.example.metamasklogin.service;

import com.example.metamasklogin.model.MyAwesomeUser;
import com.example.metamasklogin.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class NonceService {

    private final UserRepository userRepository;

    public Integer getNonce(String address) {
        return userRepository.findByAddress(address)
                .map(this::calculateAndReturnNonce)
                .orElseThrow();
    }

    public void replaceNonce(String address) {
        userRepository.findByAddress(address).ifPresent(this::updateNonce);
    }

    private Integer calculateAndReturnNonce(MyAwesomeUser myAwesomeUser) {
        if (myAwesomeUser.getNonce() == null) {
            updateNonce(myAwesomeUser);
        }
        return myAwesomeUser.getNonce();
    }

    private void updateNonce(MyAwesomeUser myAwesomeUser) {
        myAwesomeUser.setNonce(Math.abs(new Random().nextInt()));
        userRepository.save(myAwesomeUser);
    }
}
