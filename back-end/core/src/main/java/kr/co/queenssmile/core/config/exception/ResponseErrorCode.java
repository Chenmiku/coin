package kr.co.queenssmile.core.config.exception;

import lombok.Getter;

@Getter
public enum ResponseErrorCode implements java.io.Serializable {

    // 400
    NOT_ENTERED_USER(400, "회원정보가 입력되지 않았습니다."),
    NOT_ENTERED_PASSWORD(400, "비밀번호가 입력되지 않았습니다."),
    NOT_ENTERED_MOBILE(400, "휴대폰번호가 입력되지 않았습니다."),
    NOT_ENTERED_EMAIL(400, "이메일주소가 입력되지 않았습니다."),
    NOT_EXIST_EMAIL(400, "존재하지 않는 이메일주소 입니다."),
    NOT_EXIST_MOBILE(400, "존재하지 않는 휴대폰번호 입니다."),
    NOT_EXIST_USER(400, "존재하지 않는 회원 입니다."),
    ENTERED_INVALID(400, "입력하신 정보가 유효하지 않습니다."),
    ENTERED_INVALID_EMAIL(400, "입력하신 이메일주소의 형식이 유효하지 않습니다."),
    EXPIRED_VERIFICATION_CODE(400, "만료된 인증번호 입니다."),
    INVALID_VERIFICATION_CODE(400, "인증번호가 유효하지 않습니다."),
    NOT_CERTIFIED(400, "인증되지 않았습니다."),
    INVALID_ENTERED_PASSWORD(400, "입력한 비밀번호가 일치 하지 않습니다."),
    LEAVED_USER(400, "탈퇴한 회원입니다."),
    DORMANCY_USER(400, "휴면계정입니다."),

    // 결제
    LIMIT_POINT(400, "최소단위 포인트를 사용하여야 합니다."),
    MORE_THAN_MY_POINTS(400, "사용자의 포인트보다 많습니다."),
    PAYMENT_NON_MEMBER_NE_PASSWORD(400, "비회원 결제 패스워드가 일치하지 않습니다."),
    COUPON_PRODUCT_LIMIT_AMOUNT(400, "쿠폰사용 최소 상품 구매 한도 미만"),
    NON_EXIST_ORDER_TEMP(400, "임시 주문상품정보가 존재하지 않습니다."),
    BAD_ORDER_TEMP(400, "잘못된 임시 주문 정보입니다."),
    PAYMENT_AMOUNT_DISCORD(400, "결제할 금액이 일치하지 않습니다."),

    // 409
    ALREADY_EXIST_ACCOUNT(409, "이미 존재하는 회원입니다."),
    ALREADY_EXIST_EMAIL(409, "이미 존재하는 이메일입니다."),
    ALREADY_EXIST_USER(409, "이미 존재하는 회원입니다."),
    FAILED_GENERATE_VERIFICATION_CODE(409, "인증번호생성에 실패하였습니다. 재시도 해주시기 바랍니다."),

    // 500
    PAYMENT_ERROR(500, "결제 에러"),

    // 결제
    I_AM_PORT_NOT_COMPLETE(500, "아임포트 검증 실패"),
    ;

    final private int value;
    final private String message;

    ResponseErrorCode(final int value, final String message) {
        this.value = value;
        this.message = message;
    }

}
