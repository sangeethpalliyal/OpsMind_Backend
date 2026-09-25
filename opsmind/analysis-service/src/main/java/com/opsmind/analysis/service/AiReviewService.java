package com.opsmind.analysis.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AiReviewService {
    private final ChatClient client;
    private final boolean enabled;

    public AiReviewService(ObjectProvider<ChatClient.Builder> builderProvider,
                           @Value("${opsmind.ai.enabled:false}") boolean enabled) {
        this.enabled = enabled;
        this.client = builderProvider.getIfAvailable() == null
                ? null
                : builderProvider.getIfAvailable().build();
    }

    public String review(String code) {
        if (!enabled || client == null) {
            return "AI mode is OFF. Rule-based static analysis was used. Set OPENAI_API_KEY and opsmind.ai.enabled=true to enable Spring AI review.";
        }
        return client.prompt()
                .system("You are a senior Java code reviewer. Identify bugs, security issues, performance concerns, and likely Big-O complexity. Be concise and factual.")
                .user("Review this code:\n" + code)
                .call()
                .content();
    }
}
