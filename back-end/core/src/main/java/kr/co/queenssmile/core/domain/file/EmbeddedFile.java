package kr.co.queenssmile.core.domain.file;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Embeddable;

@Setter
@Getter
@ToString
@Embeddable
public class EmbeddedFile implements java.io.Serializable {

  private static final long serialVersionUID = 7968477030744912294L;

  private String url;
  private String originalFilename;
  private String filename;
  private String mimeType;
  private Long size;
}

