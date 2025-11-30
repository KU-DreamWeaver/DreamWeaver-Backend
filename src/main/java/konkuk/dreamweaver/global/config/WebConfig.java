package konkuk.dreamweaver.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private static final String frondEndOrigin = "https://dreamweavers.netlify.app";

    private static final String backEndOrigin = "https://dreamweaverss.store";

    private static final String localAddress = "localhost";

    private static final String frontEndPort = "5173";

    private static final String httpPrefix = "http://";


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedOrigins(frondEndOrigin,
                        httpPrefix + localAddress + ":" + frontEndPort,
                        backEndOrigin);
    }
}
