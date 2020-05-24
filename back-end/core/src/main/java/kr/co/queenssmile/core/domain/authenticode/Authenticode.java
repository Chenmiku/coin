package kr.co.queenssmile.core.domain.authenticode;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.co.queenssmile.core.config.serializer.JsonLocalDateTimeDeserializer;
import kr.co.queenssmile.core.config.serializer.JsonLocalDateTimeSerializer;
import kr.co.queenssmile.core.domain.AbstractEntity;
import kr.co.queenssmile.core.utils.DateUtils;
import kr.co.queenssmile.core.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Getter
@Setter
@ToString
public class Authenticode extends AbstractEntity<String> {

    private static final long serialVersionUID = 6785591286605130537L;

    // 모바일 인증 만료 time (초)
    public static final long EXPIRE_TIME_MOBILE = 60 * 3;

    // 이메일 인증 만료 time (초)
    public static final long EXPIRE_TIME_EMAIL = 60 * 10;

    // Token
    public static final int RESULT_SUCCESS = 1; // 성공
    public static final int RESULT_FAIL = -1; // 실패
    public static final int RESULT_EXPIRE = -2; // 만료
    public static final int RESULT_NOT_EXISTS = -3; // 존재하지 않음

    @Id
    @Column(unique = true, length = 50)
    private String id; // 토큰

    @Column
    private String value; // json 으로 정리

    @DateTimeFormat(pattern = DateUtils.FORMAT_DATE_TIME_UNIT)
    @JsonSerialize(using = JsonLocalDateTimeSerializer.class)
    @JsonDeserialize(using = JsonLocalDateTimeDeserializer.class)
    private LocalDateTime expireTime; // 만료 시간 (스케줄링 삭제)

    public Map<String, String> toMap() {
        if (!StringUtils.isEmpty(value)) {
            return JsonUtils.toMap(value);
        }
        return null;
    }

    @Override
    public void delete() {

    }

    @Override
    public void lazy() {

    }
}
