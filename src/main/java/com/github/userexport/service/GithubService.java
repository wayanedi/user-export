package com.github.userexport.service;

import com.itextpdf.text.DocumentException;
import reactor.core.publisher.Mono;

import java.io.ByteArrayInputStream;

public interface GithubService {

  Mono<ByteArrayInputStream> exportListUserToPdf() throws DocumentException;
}
