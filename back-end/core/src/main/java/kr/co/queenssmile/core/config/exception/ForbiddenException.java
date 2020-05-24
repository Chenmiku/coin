package kr.co.queenssmile.core.config.exception;

public class ForbiddenException extends RuntimeException {

    private static final long serialVersionUID = -5536045671279790729L;

    public ForbiddenException() {
        super("접근권한이 없습니다.");
    }

    public ForbiddenException(String message) {
        super(message);
    }
}
