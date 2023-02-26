package com.github.userexport.controller;

import com.github.userexport.client.GithubClient;
import com.github.userexport.model.GetListUserGithubResponse;
import com.github.userexport.service.GithubService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private GithubService githubService;

  @GetMapping(value = "/export", produces =
      MediaType.APPLICATION_PDF_VALUE)
  public Mono<ResponseEntity<InputStreamResource>> exportUser() throws DocumentException {

    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Disposition", "inline; filename=list-user.pdf");

    return githubService.exportListUserToPdf()
        .map(byteArrayInputStream -> ResponseEntity.ok().headers(headers).contentType
            (MediaType.APPLICATION_PDF)
        .body(new InputStreamResource(byteArrayInputStream)));
  }
}
