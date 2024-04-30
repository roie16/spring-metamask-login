package com.example.metamasklogin.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class NonceService {

    private Map<String, Integer> map;

    @PostConstruct
    public void init() {
        map = new LinkedCaseInsensitiveMap<>();
    }

    public Integer getNonce(String address) {
        map.putIfAbsent(address, Math.abs(new Random().nextInt()));
        return map.get(address);
    }

    public void replaceNonce(String address) {
        map.put(address, Math.abs(new Random().nextInt()));
    }
}
