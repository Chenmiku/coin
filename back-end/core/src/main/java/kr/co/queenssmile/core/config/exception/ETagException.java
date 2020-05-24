package kr.co.queenssmile.core.config.exception;

public class ETagException extends RuntimeException {

    public ETagException() {
        super("ETag 버전이 동일하지 않습니다.");
    }

    public ETagException(String message) {
        super(message);
    }
}
