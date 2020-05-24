package kr.co.queenssmile.core.model.file;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.net.URL;

@Setter
@Getter
@ToString
public class FileMeta implements Serializable {

  private static final long serialVersionUID = -3770830091024680811L;

  private Long id;
  private URL url;

  private String originalFilename;
  private String filename;
  private String mimeType;
  private long size;
}

