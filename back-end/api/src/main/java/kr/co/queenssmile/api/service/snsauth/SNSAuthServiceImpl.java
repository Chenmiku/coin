package kr.co.queenssmile.api.service.snsauth;

import kr.co.queenssmile.core.domain.user.SocialId;
import kr.co.queenssmile.core.domain.user.User;
import kr.co.queenssmile.core.domain.user.UserRepository;
import kr.co.queenssmile.core.domain.user.sns.Facebook;
import kr.co.queenssmile.core.domain.user.sns.Kakao;
import kr.co.queenssmile.core.domain.user.sns.Naver;
import kr.co.queenssmile.core.domain.user.sns.SNSStatus;
import kr.co.queenssmile.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
public class SNSAuthServiceImpl implements SNSAuthService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public SNSStatus getStatus(Facebook facebook) {

    Optional<User> userOpt = Optional.ofNullable(userRepository.findBySocialIdFacebookId(facebook.getId()));

    if (!userOpt.isPresent()) {
      /* DB에 연동된 계정이 없다면 */

      if (StringUtils.isNotEmpty(facebook.getEmail())) {

        // 페이스북에서 제공한 이메일 정보로 계정 조회
        userOpt = Optional.ofNullable(userRepository.findByEmail(facebook.getEmail()));

        if (userOpt.isPresent()) {
          /* 페이스북에서 제공한 이메일 정보로 조회한 계정이 존재한다면 */

          User user = userOpt.get();

          if (!user.getUserDetailsMeta().isEnabled()
            || user.getLeaveMeta().isLeave()) {
            /* 탈퇴한 계졍이라면 */
            return SNSStatus.LEAVED_ACCOUNT;
          }

          if (user.getSocialId() == null || StringUtils.isEmpty(user.getSocialId().getFacebookId())) {
            /* 계정에 페이스북 ID가 연동되어 있지 않으면 연동 시켜준다. */

            SocialId socialId = user.getSocialId() == null ? new SocialId() : user.getSocialId();
            socialId.setFacebookId(facebook.getId());
            socialId.setFacebookName(facebook.getName());

            user.setSocialId(socialId);

//                        if (StringUtils.isNotEmpty(facebook.getPicture())) {
//                            user.setThumbnail(facebook.getPicture());
//                        }
//
//                        if (facebook.getGender() != null) {
//                            user.setGender(facebook.getGender());
//                        }

            userRepository.save(user);
            return SNSStatus.CONNECT;
          } else {
            /* 계정에 다른 페이스북 ID가 연동되어 있다면 */
            return SNSStatus.NOT_MATCH_SNS;
          }
        } else {
          /* 페이스북에서 제공한 정보와 동일한 이메일과 페이스북 ID 가 존재하지 않는다. -> 회원가입 */
          return SNSStatus.NOT_EXISTED_ACCOUNT;
        }
      } else {
        /* 페이스북에서 이메일을 제공하지 않았다면 -> 회원가입 */
        return SNSStatus.NOT_PROVIDED_EMAIL;
      }
    }

    return SNSStatus.LINKED;
  }

  @Override
  public SNSStatus getStatus(Naver naver) {

    Optional<User> userOpt = Optional.ofNullable(userRepository.findBySocialIdNaverId(naver.getId()));

    if (!userOpt.isPresent()) {
      /* DB에 연동된 계정이 없다면 */

      if (StringUtils.isNotEmpty(naver.getEmail())) {

        // 네이버에서 제공한 이메일 정보로 계정 조회
        userOpt = Optional.ofNullable(userRepository.findByEmail(naver.getEmail()));

        if (userOpt.isPresent()) {
          /* 네이버에서 제공한 이메일 정보로 조회한 계정이 존재한다면 */

          User user = userOpt.get();

          if (!user.getUserDetailsMeta().isEnabled()
            || user.getLeaveMeta().isLeave()) {
            /* 탈퇴한 계졍이라면 */
            return SNSStatus.LEAVED_ACCOUNT;
          }

          if (user.getSocialId() == null || StringUtils.isEmpty(user.getSocialId().getNaverId())) {
            /* 계정에 네이버 ID가 연동되어 있지 않으면 연동 시켜준다. */

            SocialId socialId = user.getSocialId() == null ? new SocialId() : user.getSocialId();
            socialId.setNaverId(naver.getId());
            socialId.setNaverName(naver.getName());

            user.setSocialId(socialId);

//                        if (StringUtils.isNotEmpty(naver.getProfile_image())) {
//                            user.setThumbnail(naver.getProfile_image());
//                        }
//
//                        if (naver.getGender() != null) {
//                            user.setGender(naver.getGender());
//                        }

            userRepository.save(user);
            return SNSStatus.CONNECT;
          } else {
            /* 계정에 다른 페이스북 ID가 연동되어 있다면 */
            return SNSStatus.NOT_MATCH_SNS;
          }
        } else {
          /* 네이버에서 제공한 정보와 동일한 이메일과 네이버 ID 가 존재하지 않는다. -> 회원가입 */
          return SNSStatus.NOT_EXISTED_ACCOUNT;
        }
      } else {
        /* 네이버에서 이메일을 제공하지 않았다면 */
        return SNSStatus.NOT_PROVIDED_EMAIL;
      }
    }

    return SNSStatus.LINKED;
  }

  @Override
  public SNSStatus getStatus(Kakao kakao) {

    Optional<User> userOpt = Optional.ofNullable(userRepository.findBySocialIdKakaoTalkId(kakao.getId()));

    if (!userOpt.isPresent()) {
      /* DB에 연동된 계정이 없다면 */

      if (StringUtils.isNotEmpty(kakao.getEmail())) {

        // 카카오에서 제공한 이메일 정보로 계정 조회
        userOpt = Optional.ofNullable(userRepository.findByEmail(kakao.getEmail()));

        if (userOpt.isPresent()) {
          /* 카카오에서 제공한 이메일 정보로 조회한 계정이 존재한다면 */

          User user = userOpt.get();

          if (!user.getUserDetailsMeta().isEnabled()
            || user.getLeaveMeta().isLeave()) {
            /* 탈퇴한 계졍이라면 */
            return SNSStatus.LEAVED_ACCOUNT;
          }

          if (user.getSocialId() == null || StringUtils.isEmpty(user.getSocialId().getKakaoTalkId())) {
            /* 계정에 카카오 ID가 연동되어 있지 않으면 연동 시켜준다. */

            SocialId socialId = user.getSocialId() == null ? new SocialId() : user.getSocialId();
            socialId.setKakaoTalkId(kakao.getId());
            socialId.setKakaoTalkName(kakao.getNickName());

            user.setSocialId(socialId);

//                        if (StringUtils.isNotEmpty(kakao.getProfileImage())) {
//                            user.setThumbnail(kakao.getProfileImage());
//                        }

            userRepository.save(user);
            return SNSStatus.CONNECT;
          } else {
            /* 계정에 다른 페이스북 ID가 연동되어 있다면 */
            return SNSStatus.NOT_MATCH_SNS;
          }
        } else {
          /* 카카오에서 제공한 정보와 동일한 이메일과 카카오 ID 가 존재하지 않는다. -> 회원가입 */
          return SNSStatus.NOT_EXISTED_ACCOUNT;
        }
      } else {
        /* 카카오에서 이메일을 제공하지 않았다면 */
        return SNSStatus.NOT_PROVIDED_EMAIL;
      }
    }

    return SNSStatus.LINKED;
  }


}
