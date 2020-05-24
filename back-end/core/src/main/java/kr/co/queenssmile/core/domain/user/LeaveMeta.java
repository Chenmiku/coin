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
public class LeaveMeta implements java.io.Serializable {

  private static final long serialVersionUID = -996911928026611692L;

  @Column(name = "isLeave", columnDefinition = "BIT(1) default 0")
  private boolean leave; // 탈퇴 여부

  @Column(columnDefinition = "TEXT")
  private String leaveReason; // 탈퇴사유

  @JsonIgnore
  @DateTimeFormat(pattern = DateUtils.FORMAT_DATE_TIME_UNIT_BAR)
  private LocalDateTime leaveTime; // 탈퇴한 시간

  @JsonIgnore
  @DateTimeFormat(pattern = DateUtils.FORMAT_DATE_TIME_UNIT_BAR)
  private LocalDateTime removePrivacyTime; // 개인정보 삭제 시간
}