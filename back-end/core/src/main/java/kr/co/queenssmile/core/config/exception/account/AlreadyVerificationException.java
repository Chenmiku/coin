package kr.co.queenssmile.core.config.exception.account;

public class AlreadyVerificationException extends RuntimeException {

    private static final long serialVersionUID = -272108918663803429L;

    public AlreadyVerificationException() {
        super("이미 인증된 계정입니다.");
    }

    public AlreadyVerificationException(String message) {
        super(message);
    }
}

