package com.example.todo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/api/home")
    public Map<String, String> home() {
        return Map.of("message", "Todo API is running");
    }
}
