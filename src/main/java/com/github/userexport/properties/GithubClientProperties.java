package com.github.userexport.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "app.config.web-client.github-web-client")
public class GithubClientProperties {

  private static final Duration DURATION_5_SECOND = Duration.ofMillis(5000L);

  private Duration connectTimeout = DURATION_5_SECOND;
  private Duration readTimeout = DURATION_5_SECOND;
  private Duration writeTimeout = DURATION_5_SECOND;

  private String baseUrl;
}
