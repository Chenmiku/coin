package kr.co.queenssmile.api.service.snsauth;

import kr.co.queenssmile.core.domain.user.sns.Facebook;
import kr.co.queenssmile.core.domain.user.sns.Kakao;
import kr.co.queenssmile.core.domain.user.sns.Naver;
import kr.co.queenssmile.core.domain.user.sns.SNSStatus;

public interface SNSAuthService {

  SNSStatus getStatus(Facebook facebook);
  SNSStatus getStatus(Naver naver);
  SNSStatus getStatus(Kakao kakao);
}
