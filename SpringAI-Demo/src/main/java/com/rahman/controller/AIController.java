package com.rahman.controller;

import org.springframework.web.bind.annotation.*;

import com.rahman.dto.ChatRequest;
import com.rahman.service.AIService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/ai")
public class AIController {

    private final AIService aiService;

    public AIController(AIService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/chat")
    public String chat(
            @RequestBody ChatRequest request) {

        return aiService.askAI(
                request.getConversationId(),
                request.getMessage());
    }
}