package com.github.userexport.service.impl;

import com.github.userexport.client.GithubClient;
import com.github.userexport.model.GetListUserGithubResponse;
import com.github.userexport.service.GithubService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

@Service
public class GithubServiceImpl implements GithubService {

  @Autowired
  private GithubClient githubClient;

  @Override
  public Mono<ByteArrayInputStream> exportListUserToPdf() throws DocumentException {
    Document document = new Document();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PdfWriter.getInstance(document, out);
    document.open();
    PdfPTable table = new PdfPTable(6);

    return Mono.just(createHeaderTable(table))
        .flatMap(__ -> githubClient.getListUser())
        .map(HttpEntity::getBody)
        .map(getListUserGithubResponses -> addDataToPdf(table, document, getListUserGithubResponses, out));
  }

  private ByteArrayInputStream addDataToPdf(PdfPTable pdfPTable,
      Document document,
      List<GetListUserGithubResponse> getListUserGithubResponses, ByteArrayOutputStream out) {
    for (GetListUserGithubResponse userGithubResponse : getListUserGithubResponses) {
      PdfPCell idCell = new PdfPCell(new Phrase(userGithubResponse.getId().
          toString()));
      idCell.setPaddingLeft(4);
      idCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
      pdfPTable.addCell(idCell);

      PdfPCell login = new PdfPCell(new Phrase
          (userGithubResponse.getLogin()));
      login.setPaddingLeft(4);
      login.setVerticalAlignment(Element.ALIGN_MIDDLE);
      pdfPTable.addCell(login);

      PdfPCell url = new PdfPCell(new Phrase
          (String.valueOf(userGithubResponse.getUrl())));
      url.setVerticalAlignment(Element.ALIGN_MIDDLE);
      url.setPaddingRight(4);
      pdfPTable.addCell(url);

      PdfPCell htmlUrl = new PdfPCell(new Phrase
          (String.valueOf(userGithubResponse.getHtmlUrl())));
      htmlUrl.setVerticalAlignment(Element.ALIGN_MIDDLE);
      htmlUrl.setPaddingRight(4);
      pdfPTable.addCell(htmlUrl);

      PdfPCell reposUrl = new PdfPCell(new Phrase
          (String.valueOf(userGithubResponse.getReposUrl())));
      reposUrl.setVerticalAlignment(Element.ALIGN_MIDDLE);
      reposUrl.setPaddingRight(4);
      pdfPTable.addCell(reposUrl);

      PdfPCell type = new PdfPCell(new Phrase
          (String.valueOf(userGithubResponse.getType())));
      type.setVerticalAlignment(Element.ALIGN_MIDDLE);
      type.setPaddingRight(4);
      pdfPTable.addCell(type);
    }
    try {
      Font font = FontFactory
          .getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
      Paragraph para = new Paragraph("Github User Table", font);
      para.setAlignment(Element.ALIGN_CENTER);
      document.add(para);
      document.add(Chunk.NEWLINE);
      document.add(pdfPTable);
    } catch (DocumentException e) {
      throw new RuntimeException(e);
    }

    document.close();
    return new ByteArrayInputStream(out.toByteArray());
  }

  private PdfPTable createHeaderTable(PdfPTable pdfPTable) {
    Stream.of("Id", "Login", "Url", "html url", "repos url", "type")
        .forEach(headerTitle ->
        {
          PdfPCell header = new PdfPCell();
          Font headFont = FontFactory.
              getFont(FontFactory.HELVETICA_BOLD);
          header.setBackgroundColor(BaseColor.LIGHT_GRAY);
          header.setHorizontalAlignment(Element.ALIGN_CENTER);
          header.setBorderWidth(2);
          header.setPhrase(new Phrase(headerTitle, headFont));
          pdfPTable.addCell(header);
        });
    return pdfPTable;
  }

}
