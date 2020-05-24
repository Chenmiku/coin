package kr.co.queenssmile.core.domain.user.sns;

import kr.co.queenssmile.core.domain.user.Gender;
import kr.co.queenssmile.core.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class Naver {

    private String id;
    private String nickname;
    private String enc_id;
    private String profile_image;
    private String age;
    private Gender gender;
    private String email;
    private String name;
    private String birthday;

    public static Naver of(java.util.Map<String, String> profile) {

        Naver naver = new Naver();

        profile.keySet().forEach(key -> {
            if (Objects.equals(key, "id")) {
                naver.setId(profile.get("id"));
            } else if (Objects.equals(key, "nickname")) {
                naver.setNickname(profile.get("nickname"));
            } else if (Objects.equals(key, "enc_id")) {
                naver.setEnc_id(profile.get("enc_id"));
            } else if (Objects.equals(key, "profile_image")) {
                naver.setProfile_image(profile.get("profile_image"));
            } else if (Objects.equals(key, "age")) {
                naver.setAge(profile.get("age"));
            } else if (Objects.equals(key, "gender")) {
                if (Objects.equals(profile.get("gender"), "M")) {
                    naver.setGender(Gender.MALE);
                } else if (Objects.equals(profile.get("gender"), "F")) {
                    naver.setGender(Gender.FEMALE);
                }
            } else if (Objects.equals(key, "email")) {
                naver.setEmail(profile.get("email"));
            } else if (Objects.equals(key, "name")) {
                naver.setName(profile.get("name"));
            } else if (Objects.equals(key, "birthday")) {
                naver.setBirthday(profile.get("birthday"));
            }
        });

        if (StringUtils.isEmpty(naver.getName())) {
            String email = naver.getEmail();
            if (StringUtils.isNotEmpty(email)) {
                naver.setName(email.substring(0, email.indexOf("@")));
            }
        }

        return naver;
    }
}
