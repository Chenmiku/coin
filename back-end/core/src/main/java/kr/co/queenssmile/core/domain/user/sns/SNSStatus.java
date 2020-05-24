package kr.co.queenssmile.core.domain.user.sns;

import lombok.Getter;

@Getter
public enum SNSStatus {
  CONNECT("SNS 와 사용자계정 연동, SNS ID(고유키)로 연동된 계정이 존재하지 않고, SNS 정보와 동일한 이메일(계정)이 존재한다. -> SNS 연동후 로그인"),
  LEAVED_ACCOUNT("탈퇴한 계정, SNS ID(고유키)로 연동된 계정이 존재하지 않고, SNS 정보와 동일한 이메일(계정)이 존재하지만 탈퇴한 계정이다. -> 로그인화면에서 로그인 실패사유 안내(팝업, 텍스트 등 으로)"),
  NOT_MATCH_SNS("SNS ID(고유키) 불일치, SNS ID(고유키)로 연동된 계정이 존재하지 않고, SNS 정보와 동일한 이메일(계정)이 존재하지만 계졍에 등록된 SNS ID와 SNS 로그인한 SNS ID가 일치 하지 않는다. -> 로그인 화면 또는 비번찾기 화면 에서 로그인 실패 사유 안내(팝업, 텍스트 등 으로)"),
  NOT_EXISTED_ACCOUNT("존재하지 않는 계정입니다. SNS ID(고유키)로 연동된 계정이 존재하지 않고, SNS 정보와 동일한 이메일(계정)이 존재하지 않는다. -> 회원가입"),
  NOT_PROVIDED_EMAIL("제공되지 않은 이메일입니다. SNS ID(고유키)로 연동된 계정이 존재하지 않고, SNS 에서 이메일을 제공하지 않았다. -> 회원가입"),
  LINKED("이미 연동되어었음, SNS ID(고유키)로 연동되어 있는 계정이 있다. -> 로그인");

  private final String value;

  SNSStatus(final String value) {
    this.value = value;
  }
}
