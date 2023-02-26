package com.github.userexport.client;

import com.github.userexport.model.GetListUserGithubResponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import java.util.List;

public interface GithubClient {

  Mono<ResponseEntity<List<GetListUserGithubResponse>>> getListUser();
}
