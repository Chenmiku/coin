package kr.co.queenssmile.core.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.co.queenssmile.core.config.serializer.JsonLocalDateTimeSerializer;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY) // 모든 필드에 json 적용
@JsonInclude(value = JsonInclude.Include.ALWAYS) // 모든 데이터에 json 적용
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntityMtoM implements java.io.Serializable {

    private static final long serialVersionUID = -74778130931080178L;

    // 생성한 날짜
    @Getter
    @Column(insertable = true, updatable = false)
    @CreatedDate
    @JsonSerialize(using = JsonLocalDateTimeSerializer.class)
    protected LocalDateTime createdDate;

    // 생성한 사용자 아이디
    @JsonIgnore
    @Column(insertable = true, updatable = false)
    @CreatedBy
    protected Long createdBy;

    // 업데이트한 날짜
    @JsonIgnore
    @Getter
    @Column(insertable = true, updatable = true)
    @LastModifiedDate
    protected LocalDateTime updatedDate;

    // 업데이트한 사용자 아이디
    @JsonIgnore
    @Column(insertable = true, updatable = true)
    @LastModifiedBy
    protected Long updatedBy;

    // 버전관리 트렉젝션이 동작할때마다 버전이 +1이 업데이트 된다. ORM책 p.689 참고
//    @Getter
//    @Version
//    @JsonIgnore
//    private Long version;

}
