package kr.co.queenssmile.core.config.exception.crud;

public class DeleteErrorException extends CRUDException {

  private static final long serialVersionUID = 938368102383727294L;

  public DeleteErrorException(Long id, String entityName) {
    super(id, entityName, "DELETE ERROR");
  }
}

