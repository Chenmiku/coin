package kr.co.queenssmile.core.model.resbody.user;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.co.queenssmile.core.config.serializer.JsonLocalDateTimeSerializer;
import kr.co.queenssmile.core.model.BaseResponseBody;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Relation(value = "findUser")
@Slf4j
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindUserResBody extends BaseResponseBody {

    private static final long serialVersionUID = 7055621982292038514L;

    private String email;

    @JsonSerialize(using = JsonLocalDateTimeSerializer.class)
    private LocalDateTime signUpDate; // 가입일
}
