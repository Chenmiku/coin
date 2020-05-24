package kr.co.queenssmile.core.domain.embedd;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * SEO 설정
 */
@Getter
@Setter
@ToString
@Embeddable
public class SEO implements java.io.Serializable {

  private static final long serialVersionUID = 5720405917303058622L;

  @Column
  private String description;

  @Column
  private String keywords;

  @Column
  private String ogImage;
}
