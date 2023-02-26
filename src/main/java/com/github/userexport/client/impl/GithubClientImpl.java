package com.github.userexport.client.impl;

import com.github.userexport.client.GithubClient;
import com.github.userexport.model.GetListUserGithubResponse;
import com.github.userexport.properties.GithubClientProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class GithubClientImpl implements GithubClient {

  @Autowired
  private GithubClientProperties githubClientProperties;

  @Autowired
  private WebClient githubWebClient;

  ParameterizedTypeReference<List<GetListUserGithubResponse>> response =
      new ParameterizedTypeReference<>() {
      };
  @Override
  public Mono<ResponseEntity<List<GetListUserGithubResponse>>> getListUser() {
    return githubWebClient
        .get()
        .uri(uriBuilder -> uriBuilder.
                path("/users").queryParam("per_page", 50).build())
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
        .retrieve()
        .toEntity(response);
  }
}
