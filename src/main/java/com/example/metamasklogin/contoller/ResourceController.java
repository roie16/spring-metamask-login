package com.example.metamasklogin.contoller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/resource")
public class ResourceController {



    @GetMapping
    public ResponseEntity<Set<String>> getResource() {
        return ResponseEntity.ok(Set.of("My secure resource"));
    }
}
