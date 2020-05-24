package kr.co.queenssmile.core.config.exception.crud;

public class GetErrorException extends CRUDException {

  private static final long serialVersionUID = -7840105682252209337L;

  public GetErrorException(Long id, String entityName) {
    super(id, entityName, "GET ERROR");
  }
}
