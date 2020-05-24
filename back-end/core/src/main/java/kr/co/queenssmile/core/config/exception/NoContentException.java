package kr.co.queenssmile.core.config.exception;

public class NoContentException  extends RuntimeException {

  private static final long serialVersionUID = -3801729157314628374L;

  public NoContentException() {
    super("내용이 없습니다.");
  }

  public NoContentException(String message) {
    super(message);
  }
}
