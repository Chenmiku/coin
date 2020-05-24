package kr.co.queenssmile.core.domain.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.PrePersist;

@Getter
@Setter
@ToString
@Embeddable
public class SocialId implements java.io.Serializable {

  private static final long serialVersionUID = -3142066120722816609L;

  public SocialId() {
    this.facebookId = "";
    this.facebookName = "";
    this.kakaoTalkId = "";
    this.kakaoTalkName = "";
    this.naverId = "";
    this.naverName = "";
  }

  @PrePersist
  public void prePersist() {
    if (this.facebookId == null) {
      this.facebookId = "";
    }
    if (this.facebookName == null) {
      this.facebookName = "";
    }
    if (this.kakaoTalkId == null) {
      this.kakaoTalkId = "";
    }
    if (this.kakaoTalkName == null) {
      this.kakaoTalkName = "";
    }
    if (this.naverId == null) {
      this.naverId = "";
    }
    if (this.naverName == null) {
      this.naverName = "";
    }

  }

  @Column(length = 50, columnDefinition = "VARCHAR(50) default ''")
  // Freemarker string?eval 때문에 null값은 들어가면 안되고 default는 비어있는 값으로 설정해야됨.
  private String facebookId;

  @Column(length = 50, columnDefinition = "VARCHAR(50) default ''")
  // Freemarker string?eval 때문에 null값은 들어가면 안되고 default는 비어있는 값으로 설정해야됨.
  private String facebookName;

  @Column(length = 50, columnDefinition = "VARCHAR(50) default ''")
  // Freemarker string?eval 때문에 null값은 들어가면 안되고 default는 비어있는 값으로 설정해야됨.
  private String kakaoTalkId;

  @Column(length = 50, columnDefinition = "VARCHAR(50) default ''")
  // Freemarker string?eval 때문에 null값은 들어가면 안되고 default는 비어있는 값으로 설정해야됨.
  private String kakaoTalkName;

  @Column(length = 50, columnDefinition = "VARCHAR(50) default ''")
  // Freemarker string?eval 때문에 null값은 들어가면 안되고 default는 비어있는 값으로 설정해야됨.
  private String naverId;

  @Column(length = 50, columnDefinition = "VARCHAR(50) default ''")
  // Freemarker string?eval 때문에 null값은 들어가면 안되고 default는 비어있는 값으로 설정해야됨.
  private String naverName;

  public boolean isEmpty() {
    return StringUtils.isEmpty(facebookId)
        && StringUtils.isEmpty(kakaoTalkId)
        && StringUtils.isEmpty(naverId)
        ;
  }

  public boolean isFacebook() {
    return !StringUtils.isEmpty(this.facebookId);
  }

  public boolean isKakao() {
    return !StringUtils.isEmpty(this.kakaoTalkId);
  }

  public boolean isNaver() {
    return !StringUtils.isEmpty(this.naverId);
  }
}
