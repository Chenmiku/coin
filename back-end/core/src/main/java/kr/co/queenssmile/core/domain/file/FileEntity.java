package kr.co.queenssmile.core.domain.file;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.queenssmile.core.domain.AbstractEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

@Entity(name = "File")
@Getter
@Setter
@ToString
public class FileEntity extends AbstractEntity<Long> {

  private static final long serialVersionUID = -5683577710431281569L;

  @PrePersist
  public void prePersist() {
  }

  @Id
  @GeneratedValue
  @Column(unique = true)
  private Long id;

  @Column
  private String host; // https://image.example.com

  @Column
  private String path; // /images/a.jpg

  public URL getUrl() {
    if(!StringUtils.isEmpty(host) && !StringUtils.isEmpty(path)) {
      try {
        return new URL(host + path);
      } catch (MalformedURLException e) {
        e.printStackTrace();
      }
    }
    return null;
  }

  @Column
  private String filename;

  @Column
  private String originalFilename;

  @Column(length = 50)
  private String mimeType;

  public String getMimeType() {
    if (StringUtils.isEmpty(this.mimeType)) {
      return "application/octet-stream";
    }
    return mimeType;
  }

  @Column(columnDefinition = "BIGINT(20) default 0")
  private long size;

  @Column(columnDefinition = "DECIMAL(19,0) default 0")
  private BigDecimal width;

  @Column(columnDefinition = "DECIMAL(19,0) default 0")
  private BigDecimal height;

  @JsonIgnore
  @Column(columnDefinition = "BIGINT(20) default 0")
  private long orderAscending; // 순서, 오름차순


  public void changeOrder(FileEntity target) {

    FileEntity source = this;

    long sourceOrderAscending = source.getOrderAscending();
    long targetOrderAscending = target.getOrderAscending();

    target.setOrderAscending(sourceOrderAscending);
    source.setOrderAscending(targetOrderAscending);
  }

  @Override
  public void delete() {

  }

  @Override
  public void lazy() {

  }
}
