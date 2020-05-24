package kr.co.queenssmile.core.model.reqbody.user;

import kr.co.queenssmile.core.model.BaseRequestBody;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveUserReqBody extends BaseRequestBody {

    private static final long serialVersionUID = -3422607201168752231L;

    private String password;
    private boolean leaveTerms; // 탈퇴약관 동의 유/무
    private String leaveReason; // 탈퇴사유
}
