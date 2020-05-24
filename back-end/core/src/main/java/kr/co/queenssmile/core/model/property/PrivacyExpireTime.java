package kr.co.queenssmile.core.model.property;

import kr.co.queenssmile.core.utils.DateUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Getter
@Setter
@ToString
@Component
@ConfigurationProperties(prefix = "core.privacy.expired")
public class PrivacyExpireTime implements java.io.Serializable {

  private static final long serialVersionUID = 7937922741709606074L;

  // 1. 회원탈퇴후 보관 기간 : 1년
  private int leave;
  // 2. 비밀번호 만료기간 : 6개월
  private int password;
  // 3. 로그인 기록: 3개월
  private int login;
  // 4. 계약 또는 청약철회 등에 관한 기록: 5년 보관
  private int agreement;
  // 5. 대금결제 및 재화 등의 공급에 관한 기록: 5년 보관
  private int payment;
  // 6. 전자금융 거래에 관한 기록: 5년 보관
  private int financial;
  // 7. 소비자의 불만 또는 분쟁처리에 관한 기록: 3년 보관
  private int consumer;
  // 8. 위치정보의 보호 및 이용 등에 관한 법률: 6개월
  private int location;
  // 7. 표시/광고에 관한 기록 : 6개월
  private int ad;

  public boolean isLeave(LocalDateTime time) {
    LocalDateTime leaveTime = LocalDateTime.now().minusDays(this.leave);
    return DateUtils.after(leaveTime, time);
  }
}
