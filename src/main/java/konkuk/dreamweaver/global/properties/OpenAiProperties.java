package konkuk.dreamweaver.global.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "openai")
public record OpenAiProperties(
        String secretKey,
        String baseUrl,
        ChatProperties chat,
        ImageProperties image
) {
    public record ChatProperties(
            String model,
            int maxTokens,
            double temperature
    ) {}

    public record ImageProperties(
            String model,
            String size
    ) {}
}

