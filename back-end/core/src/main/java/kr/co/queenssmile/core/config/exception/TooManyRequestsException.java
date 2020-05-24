package kr.co.queenssmile.core.config.exception;

public class TooManyRequestsException extends RuntimeException {

    private static final long serialVersionUID = -5978720005633427455L;

    public TooManyRequestsException(){
        super("너무 많은 요청을 하였습니다.");
    }

    public TooManyRequestsException(String message){
        super(message);
    }
}
