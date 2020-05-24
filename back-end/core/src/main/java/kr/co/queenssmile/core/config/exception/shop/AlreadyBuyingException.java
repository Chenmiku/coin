package kr.co.queenssmile.core.config.exception.shop;

public class AlreadyBuyingException extends RuntimeException {

    private static final long serialVersionUID = -4296575337629289473L;

    public AlreadyBuyingException() {
        super("이미 구매진행중인 상품입니다.");
    }

    public AlreadyBuyingException(String message) {
        super(message);
    }
}
