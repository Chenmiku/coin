package kr.co.queenssmile.core.config.exception.crud;

public class CreateErrorException extends CRUDException {

  private static final long serialVersionUID = -7840105682252209337L;

  public CreateErrorException(String entityName) {
    super(0L, entityName, "CREATE ERROR");
  }
}
