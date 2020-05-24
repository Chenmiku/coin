package kr.co.queenssmile.core.domain.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Setter
@Getter
@ToString
@Embeddable
public class Verification implements java.io.Serializable {

    private static final long serialVersionUID = 9109898567003441029L;

    @Column(name = "vEmail", columnDefinition = "BIT(1) default 0")
    private boolean email; // 이메일 인증

    @Column(name = "vMobile", columnDefinition = "BIT(1) default 0")
    private boolean mobile; // 모바일 인증
}

