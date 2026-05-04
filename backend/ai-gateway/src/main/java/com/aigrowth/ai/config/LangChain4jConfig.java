package com.aigrowth.ai.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * LangChain4j configuration placeholder.
 * 
 * This class is a placeholder for actual LangChain4j integration.
 * When API keys are available, configure the actual LangChain4j beans here.
 * 
 * Example configuration:
 * 
 * @Configuration
 * public class LangChain4jConfig {
 *     @Bean
 *     public ChatModel chatModel(Environment env) {
 *         return OpenAiChatModel.builder()
 *             .apiKey(env.getProperty("ai.openai.api-key"))
 *             .modelName("gpt-4")
 *             .temperature(0.7)
 *             .build();
 *     }
 *     
 *     @Bean
 *     public AiServices aiServices(ChatModel chatModel) {
 *         return AiServices.builder(YourMentorClass.class)
 *             .chatModel(chatModel)
 *             .build();
 *     }
 * }
 */
@Configuration
@ConditionalOnProperty(name = "ai.provider", havingValue = "openai", matchIfMissing = false)
public class LangChain4jConfig {

    /*
     * LangChain4j integration will be enabled here when ai.provider=openai or anthropic.
     * 
     * Required dependencies:
     * - dev.langchain4j:langchain4j
     * - dev.langchain4j:langchain4j-open-ai (for OpenAI integration)
     * - dev.langchain4j:langchain4j-anthropic (for Anthropic integration)
     * 
     * Bean definitions will include:
     * - ChatModel (OpenAI or Anthropic)
     * - AiServices with custom mentor behavior
     * - Memory management for conversation context
     */
}