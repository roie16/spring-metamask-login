package com.example.metamasklogin.contoller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resource")
public class ResourceController {



    @GetMapping
    public ResponseEntity<String> getResource() {
        return ResponseEntity.ok("My secure resource");
    }
}
