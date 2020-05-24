package kr.co.queenssmile.core.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.server.core.Relation;

@Relation(value = "success", collectionRelation = "successes")
@Slf4j
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(buildMethodName = "successBuild", builderMethodName = "successBuilder")
public class SuccessResponseBody extends BaseResponseBody {

    private static final long serialVersionUID = 2813017186081236225L;

    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";

    @Builder.Default
    private String result = SUCCESS;

    private String message;

    public static SuccessResponseBody of(boolean result) {
        SuccessResponseBody successResponseBody = new SuccessResponseBody();
        if (result) {
            successResponseBody.setResult(SuccessResponseBody.SUCCESS);
        } else {
            successResponseBody.setResult(SuccessResponseBody.FAIL);
        }
        return successResponseBody;
    }

    public static SuccessResponseBody of(boolean result, String message) {
        SuccessResponseBody successResponseBody = new SuccessResponseBody();
        if (result) {
            successResponseBody.setResult(SuccessResponseBody.SUCCESS);
        } else {
            successResponseBody.setResult(SuccessResponseBody.FAIL);
        }
        successResponseBody.setMessage(message);
        return successResponseBody;
    }
}
