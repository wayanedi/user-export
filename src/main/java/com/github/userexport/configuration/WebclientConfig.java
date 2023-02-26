package com.github.userexport.configuration;

import com.github.userexport.properties.GithubClientProperties;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import java.util.concurrent.TimeUnit;

@Component
public class WebclientConfig {

  @Autowired
  private GithubClientProperties githubClientProperties;

  @Bean
  public WebClient githubWebClient() {
    return WebClient
        .builder()
        .clientConnector(
            new ReactorClientHttpConnector(
                HttpClient
                    .create()
                    .tcpConfiguration(tcpClient -> tcpClient
                        .option(
                            ChannelOption.CONNECT_TIMEOUT_MILLIS,
                            Math.toIntExact(githubClientProperties.getConnectTimeout().toMillis())
                        )
                        .doOnConnected(connection -> connection
                            .addHandlerLast(new WriteTimeoutHandler(
                                githubClientProperties.getWriteTimeout().toMillis(), TimeUnit.MILLISECONDS
                            ))
                            .addHandlerLast(new ReadTimeoutHandler(
                                githubClientProperties.getReadTimeout().toMillis(), TimeUnit.MILLISECONDS
                            ))
                        )
                    )
            )
        )
        .baseUrl(githubClientProperties.getBaseUrl())
        .build();
  }
}
