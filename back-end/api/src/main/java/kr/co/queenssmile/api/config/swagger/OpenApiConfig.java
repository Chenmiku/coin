package kr.co.queenssmile.api.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class OpenApiConfig {

  @Value("${spring.application.name}")
  private String appName;

  @Bean
  public OpenAPI customOpenAPI() {

    // Access Token
    SecurityScheme securityScheme = new SecurityScheme();
    securityScheme.setType(SecurityScheme.Type.HTTP);
    securityScheme.setScheme("bearer");
    securityScheme.setIn(SecurityScheme.In.COOKIE);
    securityScheme.setBearerFormat("JWT");

    Map<String, SecurityScheme> securitySchemes = new HashMap<>();
    securitySchemes.put("bearerAuth", securityScheme);


    // Accept-Language
    Parameter parameter = new Parameter();
    parameter.in("header");
    parameter.description("Language Code");
    parameter.example("ko_KR");

    Components components = new Components();

    components.securitySchemes(securitySchemes);
    components.addParameters("Accept-Language", parameter);

    return new OpenAPI()
        .components(components)
        .info(new Info().title(String.format("%s Application API", appName)).description(
            String.format("This is a %s using springdoc-openapi and OpenAPI 3.", appName)));
  }
}
