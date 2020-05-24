package kr.co.queenssmile.core.domain.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.co.queenssmile.core.config.serializer.JsonLocalDateTimeDeserializer;
import kr.co.queenssmile.core.config.serializer.JsonLocalDateTimeSerializer;
import kr.co.queenssmile.core.utils.DateUtils;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.time.LocalDateTime;

/**
 * Ⅰ. 개요
 * • 앱 푸시(App Push)광고의 정의: 스마트폰 등에 어플리케이션(앱)을 설치하고 수신동의를 한 고객에게 앱을 통해 전송하는 광고
 * • 앱 푸시 광고의 정보통신망법 적용: “영리목적의 광고성 정보”의 경우 ‘스팸 관련 정보통신망법 규정(제50조부터 제50조의8)’의 적용 대상이 됨
 * • ‘광고성 정보의 예외: 기존거래관계에 대한 확인이 가능한 자에 대해서는 거래일로부터 6개월간 수신동의 없이 광고성 정보 전송 가능 (유•무료 동일 적용, 단순 문의나 회원가입 제외). 단, 수신자가 수신거부의 의사를 표시한 경우 광고성 정보 전송 금지
 * <p>
 * Ⅱ. 수신동의 단계별 절차
 * ① 앱 설치만 한 단계 (앱 실행 이전): 푸시 광고 전송 불가
 * ② 앱 실행 단계 (로그인 이전 단계): 앱 최초 실행 시 수신동의 창을 띄워 “광고성 정보 수신 동의” 를 받은 이후부터 푸시 광고 전송 가능
 * ※ 아래와 같이 영리목적 광고성 정보 수신 동의를 받아 광고성 정보 전송 가능
 * "해당기기로 영리목적 광고성 정보를 전송하려고 합니다."
 * "해당기기로 이벤트, 상품할인 등의 정보를 전송하려고 합니다."
 * <p>
 * ③ 로그인 이후 단계:
 * • 홈페이지나 오프라인에서 회원가입 시 광고성 정보 수신동의를 한 고객에게는 별도의 동의절차 없이 광고성 정보 전송 가능
 * • 단, 이메일/SMS/앱 푸시 등 ‘매체별로 수신동의’를 받은 경우, ‘앱’에 대한 수신동의는 별도로 받아야 함
 * • 회원가입시 수신동의를 하지 않았더라도 차후 앱 설정에서 광고성 정보 수신동의를 하는 경우 광고성 정보 전송 가능
 * ※ 앱 설정에서 광고성(마케팅) 정보 수신동의 설정
 * <p>
 * • 앱 내 “푸시 알림 승인(ON/OFF)” 기능은 ‘광고성 정보 수신동의’에 해당하지 않음
 * 광고성 정보 수신동의 : 전송자가 수신자의 기기로 광고성 정보를 전송하여도 되는지에 대한 동의
 * 푸쉬 알림 승인 : 수신자의 기기로 들어온 정보(광고성 정보인지를 불문)를 수신자가 볼 수 있게 띄워줄 것인지에 대한 동의
 * • “광고성 정보 수신동의”를 받지 않고, “푸시 알림 승인(알람 ON/OFF)”만으로 광고성 정보를 전송하여서는 안됨
 * ※ 아래와 같은 경우는 푸쉬 알림 승인여부 일뿐 광고성 정보 수신동의에 해당하지 않음
 * • 푸쉬 알림 설정(푸시 알림 승인 ON/OFF) : 의무사항 없음. 승인을 받지 않고 그냥 알림을 띄워도 문제 없음
 * (수신자에게 광고성 정보 수신동의를 명시적으로 받는 것만이 법적 의무사항에 해당)
 * • 비로그인 상태에서 수신동의 & 로그인 상태에서 수신거부 : 비로그인 상태(로그아웃)에서도 광고성 정보 전송 불가
 * • 비로그인 상태에서 수신거부 & 로그인 상태에서 수신동의 : 비로그인 상태(로그아웃)에서 광고성 정보 전송 불가
 * (해당 회원에 대한 수신동의에 해당하고 기기에 대한 수신동의로 보기 어렵기 때문)
 * <p>
 * <주의사항>
 * • 회원가입을 별도로 받지 않는 앱인 경우, 앱 실행시 별도의 창을 띄워 동의를 받은 후 광고성 정보를 전송하여야 함
 * • 회원 탈퇴 이후 해당 기기에 광고성 정보 전송 불가
 * • 공지사항은 “광고성 정보 수신동의”에 상관 없이 “푸시 알림 승인” 고객의 기기에 띄울 수 있음
 * • 하나의 회사에서 여러 개의 앱을 운영하고 앱 별로 제공하는 재화나 서비스가 다른 경우에는 앱 각각 별도로 수신동의를 받아야 함
 * • 광고성 정보 수신거부 시 서비스를 제공하지 않는 경우 법 위반은 아님
 * <p>
 * <p>
 * <야간광고 전송제한>
 * • 오후 9시~익일 오전 8시까지 광고 푸시 전송 제한 (도달시간 기준)
 * 위 시간에 전송 시, 별도의 ‘야간광고전송에 대한 수신동의’ 필요
 * <p>
 * Ⅳ. 처리결과 통지
 * • 앱 설정 등의 메뉴에서 수신동의 설정을 바꾸는 경우, 광고성 정보 전송자는 처리결과를 통지해야 함
 * • 통지 내용
 * ① 전송자의 명칭
 * ② 수신동의, 수신거부(동의안함) 사실과 해당 의사를 표시한 날짜
 * ③ 처리결과
 * • 처리결과 통지는 팝업을 띄워서 통지 하는 방식으로 가능
 * <p>
 * Ⅴ. 매2년마다 수신동의 재확인
 * • 수신 동의 여부를 수신동의를 받은 날부터 매 2년마다 확인 (2년 이내 일괄 확인 가능)
 * ※ 수신자가 별도의 의사 표시를 하지 않을 경우, ‘수신동의 의사 유지’ 로 간주
 * • 명시 내용
 * ① 전송자의 명칭
 * ② 수신동의 날짜 및 수신에 동의한 사실
 * ③ 수신동의에 대한 유지 또는 철회 의사를 표시하는 방법
 * <p>
 * <주의사항>
 * • 수신동의 확인 시, 해당 내용에 광고성 정보나 개인정보이용내역 등의 다른 내용이 포함되어서는 안되며, 수신동의 확인에 대한 내용만 명시되어야 함
 * • 1년 동안 서비스를 이용하지 않은 회원 정보를 파기(별도 저장)하는 경우, 수신동의도 철회된 것으로 간주, 수신동의 확인 의무도 없음.
 * (정보통신망법 제29조 제2항 및 동법 시행령 제16조)
 * • 기존거래관계 성립 여부와 상관없이 수신동의 여부 확인
 * <p>
 * Ⅵ. 광고성 정보의 구분
 * <p>
 * <p>
 * <광고성 정보의 예외>
 * • 포털, 언론사 앱에서 실시간으로 사건 사고 기사를 제공하는 단순 푸시 뉴스 정보
 * • 계정 잔액 정보, 포인트/마일리지 잔액 안내 (해당 포인트를 어디에 쓰라고 직접적으로 상품이나 영업점 안내 시 광고성 정보에 해당)
 * • 금전적 대가를 지불하고 신청한 정보 (뉴스레터, 주식정보 유료 구독 등)
 * • 계약상 지급의무가 있는 쿠폰 발급
 * • 예금만기안내, 대출만기안내, 예약 신청 확인
 * • 주문 처리내역, 배송정보
 * • 회원 등급 변경 안내, 회원가입 처리 결과 안내
 * • 정치인들의 선거운동 관련 선거홍보
 * • 설문조사(단순 선호도 조사, 서비스 만족도 조사만 해당)
 * • 고객의 요청에 의해 발송하는 1회성 정보(경적서 등), 지속적인 발송은 광고성 정보에 해당
 * • 수신자와 체결하였던 거래를 용이하게 하거나, 완성•확인하는 것이 목적인 정보
 * • 제품 및 서비스에 대한 보증, 제품 리콜, 안전 또는 보안 관련 정보
 * • 수신자가 신청한 경품 및 사은품 지급을 위한 정보
 * • 이전에 체결•합의한 상거래 계약 조건에 의거하여 수신자가 수령할 권리가 있는 재화 또는 서비스(업데이트 등) 내용을 전달하기 위한 정보
 * • 전송자가 가지고 있는 채권을 채무자에게 행사하기 위해 전송하는 정보
 * <p>
 * ※ 주된 정보가 광고성 정보가 아니더라도 부수적으로 광고성 정보가 포함되어 있으면 전체가 광고성 정보에 해당 (예. 신용카드 거래내역(결제)정보에 광고성 정보가 포함된 경우, 콘도 예약 확인 정보에 콘도 부대시설 정보가 포함된 경우 광고성 정보에 해당)
 * <p>
 * <p>
 * <광고성 정보>
 * • 재화 또는 서비스의 유무료 구분 없음.
 * • 비영리 법인이라도 수익을 얻기 위한 홍보목적이 있는 경우
 * • 영업사원이나 보험설계사의 새해인사, 생일축하, 기념일 축하 등의 안부 문자
 * • 가입자를 대상으로 최근 동향 및 소식을 전하는 뉴스레터(언론사의 기사 뉴스레터도 해당)
 * • 쿠폰 발급 (계약상 지급의무가 있는 경우는 예외)
 * • 쇼핑몰 장바구니에 담아놓은 상품 안내
 * • 설문조사(특정 제품 선호도 조사 및 소개•홍보 목적인 경우)
 * • 학원에서 합격자를 상대로 합격자 설명회 개최 홍보
 * • SNS 등을 통해 이벤트 공유하기 등으로 친구나 가족에게 보내는 경우
 * <p>
 * <14세미만 아동의 개인정보>
 * 정보통신망 이용촉진 및 정보보호 등에 관한 법률에는 만 14세미만 아동의 개인정보 수집시 법정대리인 동의를 받도록 규정하고 있으며, 만 14세 미만 아동이 법정대리인 동의 없이 회원가입을 하는 경우 회원탈퇴 또는 서비스 이용이 제한 될 수 있습니다.
 */
@Setter
@Getter
@ToString
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TermsAgree implements java.io.Serializable {

  private static final long serialVersionUID = -4631931419522486043L;

  @Column(columnDefinition = "BIT(1) default 0")
  private boolean taService; // 서비스 이용약관 동의

  @Column(columnDefinition = "BIT(1) default 0")
  private boolean taPrivacy; // 개인정보 수집이용 동의 (필수)

//  @Column(columnDefinition = "BIT(1) default 0")
//  private boolean taYouth; // 본인은 만 14세 이상입니다. (필수)

//  @Column(columnDefinition = "BIT(1) default 0")
//  private boolean taEft; // (Electronic financial transactions) 전자금융거래 이용약관 동의 (필수)

  @Column(columnDefinition = "BIT(1) default 0")
  private boolean taLocation; // 위치정보서비스 및 위치기반서비스 이용약관

  @Transient
  private boolean taMarketing; // 마케팅 활용 항목 (이메일, 문자, 카카오톡)

  @Column(columnDefinition = "BIT(1) default 0")
  private boolean taThirdParty; // 제 3자 개인정보 위탁 공유 항목

  public void setTaMarketing(boolean rcv) {
//    this.setSmsRcv(rcv);
//    this.setEmailRcv(rcv);
//    this.setKakaoRcv(rcv);
  }

  public boolean getTaMarketing() {
    return this.smsRcv
        || this.emailRcv
        || this.kakaoRcv
        ;
  }

  @Column(columnDefinition = "BIT(1) default 0")
  private boolean smsRcv; // 문자 수신동의

  public void setSmsRcv(boolean smsRcv) {
    this.smsRcv = smsRcv;
    this.setSmsRcvDate(LocalDateTime.now());
  }

  @DateTimeFormat(pattern = DateUtils.FORMAT_DATE_TIME_UNIT)
  @JsonSerialize(using = JsonLocalDateTimeSerializer.class)
  @JsonDeserialize(using = JsonLocalDateTimeDeserializer.class)
  private LocalDateTime smsRcvDate;

  @Column(columnDefinition = "BIT(1) default 0")
  private boolean emailRcv; // 이메일 수신 동의

  public void setEmailRcv(boolean emailRcv) {
    this.emailRcv = emailRcv;
    this.setEmailRcvDate(LocalDateTime.now());
  }

  @DateTimeFormat(pattern = DateUtils.FORMAT_DATE_TIME_UNIT)
  @JsonSerialize(using = JsonLocalDateTimeSerializer.class)
  @JsonDeserialize(using = JsonLocalDateTimeDeserializer.class)
  private LocalDateTime emailRcvDate;

  @Column(columnDefinition = "BIT(1) default 0")
  private boolean kakaoRcv; // 카카오 알림톡 수신 동의

  public void setKakaoRcv(boolean kakaoRcv) {
    this.kakaoRcv = kakaoRcv;
    this.setKakaoRcvDate(LocalDateTime.now());
  }

  @DateTimeFormat(pattern = DateUtils.FORMAT_DATE_TIME_UNIT)
  @JsonSerialize(using = JsonLocalDateTimeSerializer.class)
  @JsonDeserialize(using = JsonLocalDateTimeDeserializer.class)
  private LocalDateTime kakaoRcvDate;
}
