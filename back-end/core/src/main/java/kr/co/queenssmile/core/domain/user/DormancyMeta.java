package kr.co.queenssmile.core.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.queenssmile.core.utils.DateUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Embeddable
public class DormancyMeta implements java.io.Serializable {

  private static final long serialVersionUID = -3326356618361617962L;

  public DormancyMeta() {
    this.lastLoginDate = LocalDateTime.now();
  }

  @Column(name = "isDormancy", columnDefinition = "BIT(1) default 0")
  private boolean dormancy; // 휴면 여부

  @JsonIgnore
  @DateTimeFormat(pattern = DateUtils.FORMAT_DATE_TIME_UNIT_BAR)
  private LocalDateTime dormancyTime; // 휴면된 시간

  @JsonIgnore
  @DateTimeFormat(pattern = DateUtils.FORMAT_DATE_TIME_UNIT_BAR)
  private LocalDateTime lastLoginDate; // 마지막 로그인 날짜
}
