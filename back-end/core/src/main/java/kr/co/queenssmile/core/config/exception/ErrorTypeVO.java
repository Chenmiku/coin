package kr.co.queenssmile.core.config.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class ErrorTypeVO implements java.io.Serializable {

    private static final long serialVersionUID = -4338435841861016833L;

    private int status;

    private String message;
}

