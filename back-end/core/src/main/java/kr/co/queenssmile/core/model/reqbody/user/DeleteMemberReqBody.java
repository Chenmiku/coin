package kr.co.queenssmile.core.model.reqbody.user;

import kr.co.queenssmile.core.model.BaseRequestBody;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeleteMemberReqBody extends BaseRequestBody {

    private static final long serialVersionUID = 1638777415301124661L;

    private boolean leaveTerms; // 탈퇴약관 동의 유/무
}
