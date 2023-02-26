package com.github.userexport.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(
    ignoreUnknown = true
)
public class GetListUserGithubResponse {

  private String login;

  private String id;

  private String url;

  @JsonProperty("html_url")
  private String htmlUrl;

  @JsonProperty("repos_url")
  private String reposUrl;

  private String type;
}
