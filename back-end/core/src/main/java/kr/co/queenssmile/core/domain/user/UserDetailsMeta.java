package kr.co.queenssmile.core.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Embeddable
public class UserDetailsMeta implements java.io.Serializable {

    private static final long serialVersionUID = -5648236996917421048L;

    //== 초기화
    public UserDetailsMeta() {
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
        this.updatedPasswordDateTime = LocalDateTime.now();
    }

    @Column(columnDefinition = "BIT(1) default 1", nullable = true)
    private boolean accountNonExpired; // 만료된 계정입니다.

    @Column(columnDefinition = "BIT(1) default 1", nullable = true)
    private boolean accountNonLocked; // 잠겨있는 계정입니다.

    @Column(columnDefinition = "BIT(1) default 1", nullable = true)
    private boolean enabled; // 존재하지 않는 계정입니다. (탈퇴)

    @Column(columnDefinition = "BIT(1) default 1", nullable = true)
    private boolean credentialsNonExpired; // 비밀번호의 유효기간이 만료되었습니다.

    @JsonIgnore
    @Column(columnDefinition = "DATETIME default CURRENT_TIMESTAMP", nullable = false)
    private LocalDateTime updatedPasswordDateTime; // 비밀번호 변경 날짜

}
