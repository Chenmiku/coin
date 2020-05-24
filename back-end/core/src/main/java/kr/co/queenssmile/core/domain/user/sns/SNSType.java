package kr.co.queenssmile.core.domain.user.sns;

import lombok.Getter;

@Getter
public enum SNSType {

    FACEBOOK("페이스북"),
    NAVER("네이버"),
    KAKAO("카카오");

    final private String value;

    SNSType(final String value) {
        this.value = value;
    }
}
