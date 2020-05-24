package kr.co.queenssmile.core.domain.user.sns;

import kr.co.queenssmile.core.utils.StringUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class Kakao {

  private String id;
  private String email;
  private String nickName;
  private String profileImage;
  private boolean kaccountEmailVerified;

  public static Kakao of(String id, String email, String kaccountEmailVerified, java.util.Map<String, String> properties) {

    Kakao kakao = new Kakao();
    kakao.setId(id);
    kakao.setEmail(email);
    kakao.setKaccountEmailVerified(Objects.equals(kaccountEmailVerified, "true"));

    if (properties != null) {
        properties.keySet().forEach(key -> {
            if (Objects.equals(key, "profile_image")) {
                kakao.setProfileImage(properties.get("profile_image"));
            } else if (Objects.equals(key, "nickname")) {
                kakao.setNickName(properties.get("nickname"));
            }
        });
    }

    if (StringUtils.isEmpty(kakao.getNickName())) {
      String _email = kakao.getEmail();
      if (StringUtils.isNotEmpty(_email)) {
        kakao.setNickName(_email.substring(0, _email.indexOf("@")));
      }
    }

    return kakao;
  }
}
