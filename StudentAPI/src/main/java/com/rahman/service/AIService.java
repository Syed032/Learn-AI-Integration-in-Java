        package com.rahman.service;

        import java.util.List;

        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.beans.factory.annotation.Value;
        import org.springframework.stereotype.Service;
        import org.springframework.web.reactive.function.client.WebClient;

        import com.fasterxml.jackson.databind.JsonNode;
        import com.fasterxml.jackson.databind.ObjectMapper;
        import com.rahman.dto.Message;
        import com.rahman.dto.OpenRouterRequest;

        @Service
        public class AIService {

        private final WebClient webClient;

        private static final Logger log =
                LoggerFactory.getLogger(AIService.class);

        @Value("${openrouter.api.key}")
        private String apiKey;

        public AIService(WebClient webClient) {
                this.webClient = webClient;
        }

        public String askAI(String userMessage) throws Exception {

                try {

                log.info(
                        "Received user message: {}",
                        userMessage);

                Message message = new Message();
                message.setRole("user");
                message.setContent(userMessage);

                OpenRouterRequest request =
                        new OpenRouterRequest();

                request.setModel(
                        "openai/gpt-oss-20b:free");

                request.setMessages(
                        List.of(message));

                String response = webClient.post()
                        .uri("https://openrouter.ai/api/v1/chat/completions")
                        .header("Authorization",
                                "Bearer " + apiKey)
                        .header("Content-Type",
                                "application/json")
                        .bodyValue(request)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();

                ObjectMapper mapper =
                        new ObjectMapper();

                JsonNode root =
                        mapper.readTree(response);

                String answer =
                        root.path("choices")
                        .get(0)
                        .path("message")
                        .path("content")
                        .asText();

                log.info(
                        "AI Response Generated Successfully");

                return answer;

                } catch (Exception e) {

                log.error(
                        "Error while calling OpenRouter API",
                        e);

                throw e;
                }
        }
        }