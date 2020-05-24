package kr.co.queenssmile.core.config.exception.shop;

public class PaymentException extends RuntimeException {

    private static final long serialVersionUID = -9002061244045006044L;

    public PaymentException() {
        super("결제 에러");
    }

    public PaymentException(String message) {
        super(message);
    }
}
