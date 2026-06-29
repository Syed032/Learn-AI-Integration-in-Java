package com.rahman.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rahman.dto.ChatRequest;
import com.rahman.service.AIService;

@RestController
@RequestMapping("/ai")
public class GeminiController {

    private final AIService aiService;

    public GeminiController(AIService aiService) {
        this.aiService = aiService;
    }

    @GetMapping("/test")
    public String test() {
        return "AI Controller Working";
    }

    @PostMapping("/chat")
    public String chat(
            @RequestBody ChatRequest request)
            throws Exception {

        return aiService.askAI(
                request.getMessage());
    }
}