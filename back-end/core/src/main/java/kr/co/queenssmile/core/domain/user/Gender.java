package kr.co.queenssmile.core.domain.user;

import lombok.Getter;

@Getter
public enum Gender {

    MALE("남자"),
    FEMALE("여자");

    private final String value;

    Gender(final String value) {
        this.value = value;
    }
}
