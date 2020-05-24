package kr.co.queenssmile.core.domain.board.qna;

import kr.co.queenssmile.core.config.security.CryptoAESConverter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

// 비회원
@Setter
@Getter
@ToString
@Embeddable
public class QnaNoMember implements java.io.Serializable {

  private static final long serialVersionUID = -7751720516339490382L;

  @PrePersist
  public void prePersist() {

  }

  @PreUpdate
  public void PreUpdate() {
  }

  @Column(length = 255)
  @Convert(converter = CryptoAESConverter.class)
  private String fullName;

  @Column(length = 255)
  @Convert(converter = CryptoAESConverter.class)
  private String mobile;

  @Column(length = 255)
  @Convert(converter = CryptoAESConverter.class)
  private String email;


  @Column
  private String password;
}
