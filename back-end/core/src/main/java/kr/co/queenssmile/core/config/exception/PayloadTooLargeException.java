package kr.co.queenssmile.core.config.exception;

public class PayloadTooLargeException extends RuntimeException {

    private static final long serialVersionUID = 3417477999028683608L;

    public PayloadTooLargeException() {
        super("요청한 값이 너무 큽니다.");
    }

    public PayloadTooLargeException(Integer limitSize) {
        super(String.format("요청한 값이 너무 큽니다. (%sMB 이하)", limitSize));
    }

    public PayloadTooLargeException(String message) {
        super(message);
    }
}
