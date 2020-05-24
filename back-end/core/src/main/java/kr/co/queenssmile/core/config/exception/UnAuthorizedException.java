package kr.co.queenssmile.core.config.exception;

public class UnAuthorizedException extends RuntimeException {

    private static final long serialVersionUID = -1620942756190363170L;

    public UnAuthorizedException() {
        super("인증되지 않은 계정입니다.");
    }

    public UnAuthorizedException(String message) {
        super(message);
    }
}
