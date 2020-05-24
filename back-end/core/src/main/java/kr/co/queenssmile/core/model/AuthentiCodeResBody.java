package kr.co.queenssmile.core.model;

import kr.co.queenssmile.core.domain.authenticode.Authenticode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class AuthentiCodeResBody extends BaseResponseBody {

    private static final long serialVersionUID = -6665263233238745788L;

    public AuthentiCodeResBody() {
    }

    public AuthentiCodeResBody(Integer resultCode) {
        this.resultCode = resultCode;
    }

    private Integer resultCode;
    private String email;
    private String mobile;

    public boolean isSuccess(){
        return this.resultCode == Authenticode.RESULT_SUCCESS;
    }
}
