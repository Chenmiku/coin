package kr.co.queenssmile.core.domain.setting;

import kr.co.queenssmile.core.domain.AbstractEntity;
import kr.co.queenssmile.core.domain.International.InternationalMode;
import kr.co.queenssmile.core.domain.RestEntityBody;
import kr.co.queenssmile.core.model.resbody.setting.AppSettingResBody;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Locale;

@Entity
@Getter
@Setter
@ToString
public class AppSetting extends AbstractEntity<Long> implements RestEntityBody<AppSettingResBody> {

  private static final long serialVersionUID = 3667972003401550990L;

  public static String[] IGNORE_PROPERTIES = {"id"};

  @PrePersist
  public void prePersist() {
    if (this.defaultLocale == null) {
      this.setDefaultLocale(java.util.Locale.KOREA);
    }
  }

  @Id
  @GeneratedValue
  @Column(unique = true)
  private Long id;

  /**
   * 언어 설정 (국제화)
   */
  @Column
  private Locale defaultLocale;

  @Column
  private boolean international; // 국제화 모드

  @Embedded
  private InternationalMode internationalMode;

  @Override
  public void delete() {

  }

  @Override
  public void lazy() {

  }

  @Override
  public AppSettingResBody toBody(Locale locale) {
    return AppSettingResBody.builder()
        .defaultLocale(defaultLocale)
        .international(international)
        .internationalMode(internationalMode)
        .build();
  }
}
