package com.example.metamasklogin.contoller;

import com.example.metamasklogin.service.NonceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final NonceService nonceService;

    @GetMapping("/nonce/{address}")
    public Integer getNonce(@PathVariable String address) {
        return nonceService.getNonce(address);
    }
}
