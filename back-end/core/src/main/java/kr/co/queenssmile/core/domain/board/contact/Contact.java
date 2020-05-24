package kr.co.queenssmile.core.domain.board.contact;


import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.queenssmile.core.config.security.CryptoAESConverter;
import kr.co.queenssmile.core.domain.AbstractEntity;
import kr.co.queenssmile.core.domain.RestEntityBody;
import kr.co.queenssmile.core.domain.user.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Locale;

@Entity(name = "Contact")
@Getter
@Setter
@ToString
public class Contact extends AbstractEntity<Long> implements RestEntityBody<Contact> {

  private static final long serialVersionUID = 445831443233566901L;

  public static String[] IGNORE_PROPERTIES = {
      "id",
      "privacyAgree",
      "relativeUser"
  };

  @Id
  @GeneratedValue
  @Column(unique = true)
  private Long id;

  @Lob
  @Column(columnDefinition = "TEXT", length = 65535)
  private String content;

  @Column(columnDefinition = "BIT(1) default 0")
  private boolean privacyAgree; // 개인정보 수집 동의

  // == 비로그인 데이터
  @Column
  @Convert(converter = CryptoAESConverter.class)
  private String fullName; // 성명 (비로그인)

  @Column
  @Convert(converter = CryptoAESConverter.class)
  private String mobile; // 휴대전화번호 (비로그인)

  @Column
  @Convert(converter = CryptoAESConverter.class)
  private String email; // 이메일 (비로그인)

  // == 로그인 데이터
  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "idUser", referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_User_For_Contact")) // Column name, 참조하는 ID(pk) name
  private User relativeUser; // 작성자 (로그인)

  // == 관리자
  @Column(columnDefinition = "BIT(1) default 1")
  private boolean complete; // 답변완료/답변미완료

  @Lob
  @Column(columnDefinition = "TEXT", length = 65535)
  private String memo;

  @Override
  public void delete() {

  }

  @Override
  public void lazy() {
    if (this.relativeUser != null) {
      this.relativeUser.getId();
    }
  }

  @Override
  public Contact toBody(Locale locale) {
    return null;
  }
}

