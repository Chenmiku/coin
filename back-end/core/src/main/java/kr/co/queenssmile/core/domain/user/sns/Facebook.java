package kr.co.queenssmile.core.domain.user.sns;


import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.co.queenssmile.core.domain.user.Gender;
import kr.co.queenssmile.core.utils.StringUtils;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Facebook implements Serializable {

    private static final long serialVersionUID = 7121830449477439847L;

//    public enum SNSStatus {
//        CONNECT_FB, // 페이스북과 사용자계정 연동, 사용자 계정에 페이스북 아이디가 저장되어있지 않고 이메일만 동일하다.
//        REDIRECT_AUTH_FB, // 인증창으로 리다이렉트, 이메일이 같은 사용자 계정에 다른 페이스북 아이디가 저장되어 있다.
//        CREATE_FB_ACCOUNT, // 페이스북 계정 새로 등록, 페이스북의 정보와 동일한 이메일과 페이스북 아이디가 존재하지 않는다.
//        REDIRECT_EMAIL_AND_FB_ACCOUNT, // 이메일 등록창 리다이렉트, 페이스북의 이메일 정보가 없으면 이메일을 같이 새로 등록해야 된다.
//        AUTHED_FB, // 인증되었다.
//        WITHDRAWAL, // 탈퇴회원이다. 이메일을 다시 등록해야한다.
//        ERROR, // 에러
//    }

    private String id;
    private String name;
    private String email;
    private String picture;
    private String gender;

    public void setGender(String gender) {
        if (StringUtils.isNotEmpty(gender)) {
            if (Objects.equals(gender, "male")) {
                this.setGenderEnum(Gender.MALE);
            } else if (Objects.equals(gender, "female")) {
                this.setGenderEnum(Gender.FEMALE);
            }
        }
        this.gender = gender;
    }

    @JsonIgnore
    private Gender genderEnum;

    public static Facebook of(String id, String name, String email, Map pictureMap, String gender) {

        Facebook facebook = new Facebook();
        facebook.setId(id);
        facebook.setName(name);

        if (StringUtils.isNotEmpty(email)) {
            facebook.setEmail(email);
        }

        if (pictureMap != null) {
            Map data = (Map) pictureMap.get("data");
            String picture = data.get("url").toString();
            facebook.setPicture(picture);
        }

        if (StringUtils.isNotEmpty(gender)) {
            if (Objects.equals(gender, "male")) {
                facebook.setGenderEnum(Gender.MALE);
            } else if (Objects.equals(gender, "female")) {
                facebook.setGenderEnum(Gender.FEMALE);
            }
        }

        return facebook;
    }

    public Map<String, String> toMap() {
        Map<String, String> result = new HashMap<>();
        result.put("access_token", "sign_up");
        result.put("id", this.id);
        result.put("name", this.name);
        result.put("email", StringUtils.isEmpty(this.email) ? null : this.email);
        result.put("thumbnail", StringUtils.isEmpty(this.picture) ? null : this.picture);
        result.put("gender", this.genderEnum == null ? null : this.genderEnum.name());
        return result;
    }
}
