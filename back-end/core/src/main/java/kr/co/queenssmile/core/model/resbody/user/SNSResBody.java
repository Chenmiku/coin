package kr.co.queenssmile.core.model.resbody.user;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.co.queenssmile.core.config.serializer.JsonLocalDateDeserializer;
import kr.co.queenssmile.core.config.serializer.JsonLocalDateSerializer;
import kr.co.queenssmile.core.domain.user.sns.SNSType;
import kr.co.queenssmile.core.model.BaseResponseBody;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDate;

@Relation(value = "sns")
@Slf4j
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SNSResBody extends BaseResponseBody {

    private static final long serialVersionUID = 5181952956419948965L;

    private String access_token;
    private String redirectUri;
    private String message;

    // SNS 정보
    private SNSType snsType;
    private String id;
    private String name;
    private String email;
    private String image;
    private boolean verifiedEmail;

    @JsonSerialize(using = JsonLocalDateSerializer.class)
    @JsonDeserialize(using = JsonLocalDateDeserializer.class)
    private LocalDate birth; // 생년월일

    private boolean result;

    public static SNSResBody of(boolean result) {
        SNSResBody body = new SNSResBody();
        body.setResult(result);
        return body;
    }
}
