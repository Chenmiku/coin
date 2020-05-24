package kr.co.queenssmile.api.service.user;


import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.co.queenssmile.api.service.snsauth.APIFacebookService;
import kr.co.queenssmile.core.config.database.PwdEncConfig;
import kr.co.queenssmile.core.config.exception.BadRequestException;
import kr.co.queenssmile.core.config.exception.ConflictException;
import kr.co.queenssmile.core.config.exception.ResponseErrorCode;
import kr.co.queenssmile.core.config.exception.UnAuthorizedException;
import kr.co.queenssmile.core.domain.authenticode.Authenticode;
import kr.co.queenssmile.core.domain.user.*;
import kr.co.queenssmile.core.domain.user.sns.SNSType;
import kr.co.queenssmile.core.model.AuthentiCodeResBody;
import kr.co.queenssmile.core.model.file.FileMeta;
import kr.co.queenssmile.core.model.file.FileUploadService;
import kr.co.queenssmile.core.model.property.PrivacyExpireTime;
import kr.co.queenssmile.core.model.reqbody.user.*;
import kr.co.queenssmile.core.model.resbody.user.FindUserResBody;
import kr.co.queenssmile.core.model.resbody.user.MeResBody;
import kr.co.queenssmile.core.model.resbody.user.UserInfoResBody;
import kr.co.queenssmile.core.service.authenticode.AuthenticodeService;
import kr.co.queenssmile.core.utils.DateUtils;
import kr.co.queenssmile.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.security.KeyPair;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

  @Value("${oauth.password.client.id}")
  private String clientId;

  @Value("${jwt.token.expire-time}")
  private int tokenExpireTime;

  @Value("${oauth.password.client.secret}")
  private String secretKey;

  @Autowired
  private KeyPair keyPair;

  @Value("${app.api.host}")
  private String authHost;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AuthorityRepository authorityRepository;

  @Autowired
  private PwdEncConfig pwdEncConfig;

  @Autowired
  private CertService certService;

  @Autowired
  private PrivacyExpireTime privacyExpireTime;

  @Autowired
  private AuthenticodeService authenticodeService;

  @Autowired
  private FileUploadService fileUploadService;

  @Autowired
  private APIFacebookService apiFacebookService;


//  @Autowired
//  private DormancyUserRepository dormancyUserRepository;

  @Override
  @Transactional(readOnly = true)
  public User get(Long id) {
    return userRepository.findById(id)
        .map(user -> {
          user.lazy();
          return user;
        }).orElse(null);
  }

  @Override
  @Transactional(readOnly = true)
  public User get(String email) {
    return Optional.ofNullable(userRepository.findByEmail(email))
        .map(user -> {
          user.lazy();
          return user;
        }).orElse(null);
  }

  @Override
  @Transactional(readOnly = true)
  public Authority authority(Authority.Role role) {
    return authorityRepository.findByRole(role);
  }


  @Override
  @Transactional
  public User join(SignUpReqBody signUpReqBody) {

    // 이메일로 회원가입 할경우 비밀번호 입력 체크
//    if (signUpReqBody.getSocialId() == null
//        && StringUtils.isEmpty(signUpReqBody.getPassword())) {
//      throw new BadRequestException(ResponseErrorCode.NOT_ENTERED_PASSWORD.getMessage());
//    }

    // 이미 존재하는 이메일입니다.
    if (userRepository.existsByEmail(signUpReqBody.getEmail())) {
      throw new ConflictException(ResponseErrorCode.ALREADY_EXIST_ACCOUNT.getMessage());
    }

    User user = signUpReqBody.toUser();

    if (user == null) {
      throw new BadRequestException(ResponseErrorCode.NOT_ENTERED_USER.getMessage());
    }

    if (user.getId() != null) {
      throw new ConflictException(ResponseErrorCode.ALREADY_EXIST_USER.getMessage());
    }

    if (StringUtils.isNotEmpty(user.getPassword())) {
      user.setPassword(pwdEncConfig.getPasswordEncoder().encode(user.getPassword()));
    }
    //user.setImage(StringUtils.isEmpty(user.getImage()) ? User.DEFAULT_PROFILE_IMAGE : user.getImage());


    if (user.getTermsAgree().isEmailRcv()) {
      user.getTermsAgree().setEmailRcvDate(LocalDateTime.now());
    }
    if (user.getTermsAgree().isSmsRcv()) {
      user.getTermsAgree().setSmsRcvDate(LocalDateTime.now());
    }

    User _user = UserServiceImpl.setUserAuthorities(authorityRepository, user);
    _user.getAuthorities().add(authorityRepository.findByRole(Authority.Role.USER));

    return userRepository.save(user);
  }

  @Override
  @Transactional(readOnly = true)
  public String getJWT(Long id) {
    return userRepository.findById(id).map(this::getJWT).orElseThrow(UnAuthorizedException::new);
  }

  @Override
  @Transactional(readOnly = true)
  public String getJWT(User user) {

    return Jwts.builder()
        .setHeaderParam("typ", "JWT")
        .setExpiration(new Date(LocalDateTime.now().plusSeconds(tokenExpireTime).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())) // 만료일
        .setId(UUID.randomUUID().toString()) // 고유값

        .claim("client_id", clientId)
        .claim("user_name", user.getEmail())
        .claim("authorities", user.getRoles())
        .claim("scope", Collections.singletonList("read"))

        .signWith(SignatureAlgorithm.RS256, keyPair.getPrivate())
        .compact();
  }

  @Override
  @Transactional
  public void login(Long id) {
    userRepository.findById(id)
        .map(user -> {
          UserDetails userDetails = user.getUserDetails();
          Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, user.getPassword(), user.getGrantedAuthorities());
          SecurityContextHolder.getContext().setAuthentication(authentication);
          user.getDormancyMeta().setLastLoginDate(LocalDateTime.now());
          return user;
        }).orElseThrow(BadRequestException::new);
  }

  @Override
  @Transactional(readOnly = true)
  public MeResBody profile(String email, Locale locale) {
    if (org.springframework.util.StringUtils.isEmpty(email)) {
      throw new RuntimeException();
    }

    User user = userRepository.findByEmail(email);

    // 계정이 존재하는지 체크
    if (user == null) {
      throw new BadRequestException(ResponseErrorCode.ENTERED_INVALID.getMessage());
    }

    // 탈퇴한 계정인지 체크
    this.isLeaved(user);

    MeResBody meResBody = user.toBody(locale);
    meResBody.setExpiredPassword(this.isExpiredPassword(user));
    log.debug("me ::: {}", meResBody);
    return meResBody;
  }

  @Override
  @Transactional
  public void changePassword(ChangePasswordReqBody changePasswordReqBody, String email) {

    final String password = changePasswordReqBody.getPassword();
    final String newPassword = changePasswordReqBody.getNewPassword();

    User user = userRepository.findByEmail(email);
//    if (StringUtils.isEmpty(user.getPassword()) && !user.getSocialId().isEmpty()) {
//      // 패스워드 가 없고, 쇼셜 ID가 존재할 경우 패스워드 등록한다.
//      user.setPassword(pwdEncConfig.getPasswordEncoder().encode(newPassword));
//    } else
    if (pwdEncConfig.getPasswordEncoder().matches(password, user.getPassword())) {
      user.setPassword(pwdEncConfig.getPasswordEncoder().encode(newPassword));

    } else {
      throw new BadRequestException(ResponseErrorCode.INVALID_ENTERED_PASSWORD.getMessage());
    }
  }

  @Override
  @Transactional
  public void resetPasswordByEmail(String newPassword, String email) {
    User user = userRepository.findByEmail(email);
    if (user == null) {
      throw new BadRequestException("존재하지 않은 계정입니다.");
    }
    user.setPassword(pwdEncConfig.getPasswordEncoder().encode(newPassword));
    user.getUserDetailsMeta().setUpdatedPasswordDateTime(LocalDateTime.now());
  }

  @Override
  @Transactional
  public void changeUser(String email, UpdateUserReqBody updateUserReqBody) {
    Optional.ofNullable(userRepository.findByEmail(email))
        .ifPresent(user -> {
          user.setFullName(updateUserReqBody.getFullName());

          user.getTermsAgree().setSmsRcv(updateUserReqBody.isSmsRcv());
          user.getTermsAgree().setEmailRcv(updateUserReqBody.isEmailRcv());
//          user.getTermsAgree().setKakaoRcv(updateUserReqBody.isKakaoRcv());
        });
  }

  @Override
  @Transactional
  public void changeFullName(String fullName, String email) {
    Optional.ofNullable(userRepository.findByEmail(email))
        .ifPresent(user -> {
          user.setFullName(fullName);
        });
  }

  @Override
  @Transactional
  public void changeMobile(CertConfirmReqBody confirm, String email) {

    AuthentiCodeResBody result = authenticodeService.confirmByMobile(confirm.getCode(), confirm.getMobile());

    // 성공
    if (Objects.equals(result.getResultCode(), Authenticode.RESULT_SUCCESS)) {

      Optional.ofNullable(userRepository.findByEmail(email))
          .ifPresent(user -> {
            user.setMobile(confirm.getMobile());
            user.getVerification().setMobile(true);
          });
    }
    // 실패
    else if (Objects.equals(result.getResultCode(), Authenticode.RESULT_EXPIRE)) {
      throw new BadRequestException(ResponseErrorCode.EXPIRED_VERIFICATION_CODE.getMessage());
    } else {
      throw new BadRequestException(ResponseErrorCode.INVALID_VERIFICATION_CODE.getMessage());
    }
  }

  @Override
  @Transactional
  public void changeEmail(CertReqBody cert, String email, HttpServletRequest request) {

    User _user = userRepository.findByEmail(cert.getEmail());
    if (_user != null && StringUtils.isNotEmpty(_user.getEmail())) {
      throw new BadRequestException(ResponseErrorCode.ALREADY_EXIST_EMAIL.getMessage());
    }

    Optional.ofNullable(userRepository.findByEmail(email))
        .ifPresent(user -> {
          user.setEmail(cert.getEmail());
          user.getVerification().setEmail(false);
        });
  }

  @Override
  @Transactional
  public void changeSns(ChangeSnsReqBody changeSns, String email) {
    Optional.ofNullable(userRepository.findByEmail(email))
        .ifPresent(user -> {

          String code = changeSns.getCode();
          String redirectUri = changeSns.getRedirectUri();
          String state = changeSns.getState();

          if (Objects.equals(changeSns.getSnsType(), SNSType.FACEBOOK)) {

            if (StringUtils.isNotEmpty(code)
                && StringUtils.isNotEmpty(redirectUri)) {
              this.connectFacebook(code, redirectUri, user);
            } else {

              SocialId socialId = user.getSocialId() == null ? new SocialId() : user.getSocialId();
              socialId.setFacebookId(null);
              socialId.setFacebookName(null);

              user.setSocialId(socialId);
            }
          }
        });
  }

  @Override
  @Transactional
  public void changeSnsNative(ChangeSnsNativeReqBody reqBody, String email) {
    log.debug("# changeSnsNative");
    Optional.ofNullable(userRepository.findByEmail(email))
        .ifPresent(user -> {
          SocialId socialId = user.getSocialId() == null ? new SocialId() : user.getSocialId();

          switch (reqBody.getSnsType()) {
            case FACEBOOK:
              if (reqBody.isActive()) {
                // TODO 중복처리 해야함.
                User _user = userRepository.findBySocialIdFacebookId(reqBody.getId());
                if (_user == null || _user.getId().equals(user.getId())) {
                  socialId.setFacebookId(reqBody.getId());
                  socialId.setFacebookName(reqBody.getName());
                } else {
                  throw new BadRequestException("이미 다른계정과 연동되어있습니다.");
                }
              } else {
                socialId.setFacebookId(null);
                socialId.setFacebookName(null);
              }
              this.changeSns(socialId, email);

              break;
            case NAVER:

              if (reqBody.isActive()) {
                User _user = userRepository.findBySocialIdNaverId(reqBody.getId());
                if (_user == null || _user.getId().equals(user.getId())) {
                  socialId.setNaverId(reqBody.getId());
                  socialId.setNaverName(reqBody.getName());
                } else {
                  throw new BadRequestException("이미 다른계정과 연동되어있습니다.");
                }
              } else {
                socialId.setNaverId(null);
                socialId.setNaverName(null);
              }
              this.changeSns(socialId, email);

              break;

            case KAKAO:

              if (reqBody.isActive()) {
                User _user = userRepository.findBySocialIdKakaoTalkId(reqBody.getId());
                if (_user == null || _user.getId().equals(user.getId())) {
                  socialId.setKakaoTalkId(reqBody.getId());
                  socialId.setKakaoTalkName(reqBody.getName());
                } else {
                  throw new BadRequestException("이미 다른계정과 연동되어있습니다.");
                }
              } else {
                socialId.setKakaoTalkId(null);
                socialId.setKakaoTalkName(null);
              }
              this.changeSns(socialId, email);

              break;
          }
        });
  }

  @Override
  @Transactional
  public void changeSns(SocialId socialId, String email) {
    User _user = userRepository.findByEmail(email);

    log.debug("social Id ::: {}", socialId);

    if (StringUtils.isNotEmpty(socialId.getFacebookId())) {
      User user = userRepository.findBySocialIdFacebookId(socialId.getFacebookId());

      if (user != null && !user.getEmail().equals(_user.getEmail())) {
        throw new BadRequestException("이미 다른 사용자 계정에 등록되어있습니다. 한 계정만 연동가능합니다.");
      }
      log.debug("facebook");
      _user.getSocialId().setFacebookId(socialId.getFacebookId());
      _user.getSocialId().setFacebookName(socialId.getFacebookName());
    } else {
      _user.getSocialId().setFacebookId(null);
      _user.getSocialId().setFacebookName(null);
    }

    if (StringUtils.isNotEmpty(socialId.getNaverId())) {
      User user = userRepository.findBySocialIdNaverId(socialId.getNaverId());

      if (user != null && !user.getEmail().equals(_user.getEmail())) {
        throw new BadRequestException("이미 다른 사용자 계정에 등록되어있습니다. 한 계정만 연동가능합니다.");
      }
      log.debug("naver");
      _user.getSocialId().setNaverId(socialId.getNaverId());
      _user.getSocialId().setNaverName(socialId.getNaverName());
    } else {
      _user.getSocialId().setNaverId(null);
      _user.getSocialId().setNaverName(null);
    }

    if (StringUtils.isNotEmpty(socialId.getKakaoTalkId())) {
      User user = userRepository.findBySocialIdKakaoTalkId(socialId.getKakaoTalkId());

      if (user != null && !user.getEmail().equals(_user.getEmail())) {
        throw new BadRequestException("이미 다른 사용자 계정에 등록되어있습니다. 한 계정만 연동가능합니다.");
      }
      log.debug("kakao");
      _user.getSocialId().setKakaoTalkId(socialId.getKakaoTalkId());
      _user.getSocialId().setKakaoTalkName(socialId.getKakaoTalkName());
    } else {
      _user.getSocialId().setKakaoTalkId(null);
      _user.getSocialId().setKakaoTalkName(null);
    }

    if (_user.getSocialId().isEmpty() && StringUtils.isEmpty(_user.getPassword())) {
      throw new BadRequestException("비밀번호를 등록없이 모든 소셜로그인 해제는 불가능합니다.");
    }
  }

  private void connectFacebook(String code, String redirectUri, User user) {

    Map accessTokenResult = apiFacebookService.getAccessToken(code, redirectUri);

    String accessToken = accessTokenResult.get("access_token").toString();

    Map meResult = apiFacebookService.getMe(accessToken);

    String id = meResult.get("id").toString();
    String name = meResult.get("name").toString();
//                        String email = meResult.get("email") == null ? null : meResult.get("email").toString();
//                        Map pictureMap = meResult.get("picture") == null ? null : (Map) meResult.get("picture");
//                        String gender = meResult.get("gender") == null ? null : meResult.get("gender").toString();

    SocialId socialId = user.getSocialId() == null ? new SocialId() : user.getSocialId();
    socialId.setFacebookId(id);
    socialId.setFacebookName(name);

    user.setSocialId(socialId);
  }

  @Override
  @Transactional
  public boolean matchPassword(User user, String password) {

    if (StringUtils.isEmpty(user.getPassword())) {
      throw new BadRequestException("비밀번호가 존재하지 않습니다.");
    }

    return pwdEncConfig.getPasswordEncoder().matches(password, user.getPassword());
  }

  @Override
  @Transactional
  public boolean matchPassword(String email, String password) {
    User user = this.get(email);

    if (user == null) {
      throw new BadRequestException("존재하지 않은 사용자입니다.");
    }

    return this.matchPassword(user, password);
  }

  @Override
  @Transactional
  public FileMeta updateProfileImage(MultipartFile file, String email) {

    return Optional.ofNullable(userRepository.findByEmail(email))
        .map(user -> {
          FileMeta result = fileUploadService.uploadImage(file);
          user.setImage(result.getUrl() != null ? result.getUrl().toString() : null);
          return result;
        }).orElseThrow(BadRequestException::new);
  }

  @Override
  @Transactional
  public void leave(Long id, String reason) {
    userRepository.findById(id)
        .ifPresent(user -> {
          user.getUserDetailsMeta().setEnabled(false);
          user.getLeaveMeta().setLeave(true);
          user.getLeaveMeta().setLeaveTime(LocalDateTime.now());
          user.getLeaveMeta().setLeaveReason(reason);

          user.setSocialId(null); // SNS 연동끊기
        });

  }

  @Override
  @Transactional
  public void deleteMember(Long id) {
    userRepository.findById(id)
            .ifPresent(user -> {
              user.getUserDetailsMeta().setEnabled(false);
              user.getLeaveMeta().setLeave(true);
              user.getLeaveMeta().setLeaveTime(LocalDateTime.now());

              user.setSocialId(null); // SNS 연동끊기
            });
  }

  @Override
  @Transactional
  public void removePrivacy(Long id) {
    userRepository.findById(id)
        .ifPresent(user -> {
          if (!user.getUserDetailsMeta().isEnabled()
              && user.getLeaveMeta().isLeave()) {

            String disableKeyword = "leave" + id;
            user.setEmail(disableKeyword);
            user.setMobile(disableKeyword);
            user.setFullName(disableKeyword);
            user.setImage(null);
            user.setPassword(null);
//            user.setBirth(null);
            user.setSocialId(null);
            user.setAuthorities(null);
            user.getUserDetailsMeta().setEnabled(false);
            user.getLeaveMeta().setLeave(true);
            user.getLeaveMeta().setRemovePrivacyTime(LocalDateTime.now());
          }
        });
  }

  @Override
  @Transactional
  public void findAccountByMobile(String mobile, HttpServletRequest request) {

    List<User> users = userRepository.findByMobileAndUserDetailsMetaEnabledTrue(mobile);

    if (users.size() > 0) {
      CertReqBody certReqBody = new CertReqBody();
      certReqBody.setMobile(mobile);
      certReqBody.setMode(CertReqBody.Mode.FIND_ACCOUNT_MOBILE);
      String code = certService.cert(certReqBody, request);
      log.debug("# find account mobile ::: {}", code);
    } else {
      throw new BadRequestException(ResponseErrorCode.ENTERED_INVALID.getMessage());
    }
  }

  @Override
  public void findPasswordByMobile(String mobile, String email, String fullName, HttpServletRequest request) {
    List<User> users = userRepository.findByMobileAndFullNameAndEmailAndUserDetailsMetaEnabledTrue(mobile, fullName, email);

    if (users.size() > 0) {
      CertReqBody certReqBody = new CertReqBody();
      certReqBody.setMobile(mobile);
      certReqBody.setMode(CertReqBody.Mode.FIND_ACCOUNT_MOBILE);
      String code = certService.cert(certReqBody, request);
      log.debug("# find account mobile ::: {}", code);
    } else {
      throw new BadRequestException(ResponseErrorCode.ENTERED_INVALID.getMessage());
    }
  }

  @Override
  @Transactional
  public List<FindUserResBody> findAccountByMobileConfirm(FindAccountReqBody findAccountReqBody) {

    CertConfirmReqBody confirm = new CertConfirmReqBody();
    confirm.setMode(CertReqBody.Mode.FIND_ACCOUNT_MOBILE);
    confirm.setCode(findAccountReqBody.getCode());
    confirm.setMobile(findAccountReqBody.getMobile());
    AuthentiCodeResBody result = certService.confirm(confirm);

    // 성공
    if (Objects.equals(result.getResultCode(), Authenticode.RESULT_SUCCESS)) {

      List<User> users = userRepository.findByMobileAndUserDetailsMetaEnabledTrue(result.getMobile());

      return users.stream().map(user -> {
        FindUserResBody findUserResBody = new FindUserResBody();
        findUserResBody.setEmail(user.getEmail());
        findUserResBody.setSignUpDate(user.getCreatedDate());
        return findUserResBody;
      }).collect(toList());
    }

    // 실패
    if (Objects.equals(result.getResultCode(), Authenticode.RESULT_EXPIRE)) {
      throw new BadRequestException(ResponseErrorCode.EXPIRED_VERIFICATION_CODE.getMessage());
    } else {
      throw new BadRequestException(ResponseErrorCode.NOT_CERTIFIED.getMessage());
    }
  }

  @Override
  public ResponseEntity findPasswordByMobileConfirm(FindPasswordReqBody findPasswordReqBody) {

    CertConfirmReqBody confirm = new CertConfirmReqBody();
    confirm.setMode(CertReqBody.Mode.FIND_ACCOUNT_MOBILE);
    confirm.setCode(findPasswordReqBody.getCode());
    confirm.setMobile(findPasswordReqBody.getMobile());
    AuthentiCodeResBody result = certService.confirm(confirm);

    if (Objects.equals(result.getResultCode(), Authenticode.RESULT_SUCCESS)) {

      return ResponseEntity.ok().body("인증번호가 일치합니다.");
    }

    if (Objects.equals(result.getResultCode(), Authenticode.RESULT_EXPIRE)) {
      throw new BadRequestException(ResponseErrorCode.EXPIRED_VERIFICATION_CODE.getMessage());
    } else {
      throw new BadRequestException(ResponseErrorCode.NOT_CERTIFIED.getMessage());
    }
  }

  @Override
  @Transactional(readOnly = true)
  public ResponseEntity authAccessToken(LoginReqBody restLoginReqBody, HttpServletRequest request) {

    User user = userRepository.findByEmail(restLoginReqBody.getEmail());
//    DormancyUser dormancyUser = dormancyUserRepository.findByEmail(restLoginReqBody.getEmail());
    // 계정이 존재하는지 체크 (휴면테이블도 확인)
    if (user == null
//        && dormancyUser == null
    ) {
      throw new BadRequestException("존재하지 않는 계정입니다.");
    }

    // 휴면이 아닐 경우
    if (user != null && !pwdEncConfig.getPasswordEncoder().matches(restLoginReqBody.getPassword(), user.getPassword())
    ) {
      throw new UnAuthorizedException("아이디 혹은 비밀번호가 일치하지 않습니다.");
    }

    // 휴면일 경우
//    if(dormancyUser != null && !pwdEncConfig.getPasswordEncoder().matches(restLoginReqBody.getPassword(), dormancyUser.getPassword())){
//      throw new UnAuthorizedException("아이디 혹은 비밀번호가 일치하지 않습니다.");
//    }
//    else if(dormancyUser != null && pwdEncConfig.getPasswordEncoder().matches(restLoginReqBody.getPassword(), dormancyUser.getPassword())){
//      throw new BadRequestException(ResponseErrorCode.DORMANCY_USER.getMessage());
//    }


    // 탈퇴한 계정인지 체크
    this.isLeaved(user);
    //

    if (!user.getRoleTopLevel().equals(Authority.Role.SUPER)
        && !user.getRoleTopLevel().equals(Authority.Role.ADMIN)
    ) {

      throw new UnAuthorizedException("관리자에 의해 중지된 계정입니다.");
    }
/*
    String target = String.format("%s:%s", clientId, secretKey);

    byte[] targetBytes = new byte[0];
    try {
      targetBytes = target.getBytes("UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    Base64.Encoder encoder = Base64.getEncoder();
    byte[] encodedBytes = encoder.encode(targetBytes);

    return AccessTokenResBody.builder()
        .url(String.format("%s/oauth/token", authHost))
        .grantType("password")
        .username(user.getEmail())
        .header(AccessTokenHeaderResBody.builder()
            .authorization(String.format("Basic %s", new String(encodedBytes)))
            .contentType("application/json")
            .build())
        .build();
 */

    user.getDormancyMeta().setLastLoginDate(LocalDateTime.now());
    userRepository.save(user);

    return ResponseEntity.ok(
        ImmutableMap
            .builder()
            .put("access_token", this.getJWT(user))
            .build()
    );
  }

//  @Override
//  @Transactional
//  public ResponseEntity authAccessToken(LoginReqBody restLoginReqBody, HttpServletRequest request) {
//
//    User user = userRepository.findByEmail(restLoginReqBody.getEmail());
//
//    // 계정이 존재하는지 체크
//    if (user == null) {
//      throw new BadRequestException(ResponseErrorCode.ENTERED_INVALID.getMessage());
//    }
//
//    // 탈퇴계정 체크
//    this.isLeaved(user);
//    // 휴면계정 체크
//    this.isDormant(user);
//
//    ResponseEntity responseEntity = null;
//    URL url;
//    String response = "";
//
//    try {
//
////      org.springframework.security.oauth2.provider.endpoint.TokenEndpoint 으로 요청
////      OAuth2AccessToken;
////      BearerTokenExtractor;
//      String password = restLoginReqBody.getPassword();
////      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//      url = UriComponentsBuilder.fromHttpUrl(String.format("%s/oauth/token", authHost))
//          .queryParam("grant_type", "password")
//          .queryParam("username", restLoginReqBody.getEmail())
//          .queryParam("password", password)
//          .build()
//          .encode()
//          .toUri()
//          .toURL();
//
//      log.debug("URL ::: {}", url);
//
//      HttpURLConnection myConnection = (HttpURLConnection) url.openConnection();
//      myConnection.setRequestProperty("Content-Type", "application/json");
//      String target = String.format("%s:%s", clientId, secretKey);
//
//      byte[] targetBytes = new byte[0];
//      targetBytes = target.getBytes(StandardCharsets.UTF_8);
//      Base64.Encoder encoder = Base64.getEncoder();
//      byte[] encodedBytes = encoder.encode(targetBytes);
//
//      log.debug("auth ::: {}", String.format("Basic %s", new String(encodedBytes)));
//      myConnection.setRequestProperty("Authorization", String.format("Basic %s", new String(encodedBytes)));
//
//      myConnection.setReadTimeout(10000);
//      myConnection.setConnectTimeout(15000);
//      myConnection.setRequestMethod("POST");
//      myConnection.setDoInput(true);
//      myConnection.setDoOutput(true);
//
//      log.debug("target ::: {}", target);
//      log.debug("connection URL ::: {}", myConnection.getURL());
//      log.debug("connection method ::: {}", myConnection.getRequestMethod());
//
//      if (myConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
//
//        String line;
//        BufferedReader br = new BufferedReader(new InputStreamReader(myConnection.getInputStream()));
//        while ((line = br.readLine()) != null) {
//          response += line;
//        }
//        responseEntity = ResponseEntity.ok(response);
//      } else {
//        if (myConnection != null)
//          myConnection.disconnect();
//        throw new UnAuthorizedException();
//      }
//      if (myConnection != null)
//        myConnection.disconnect();
//    } catch (MalformedURLException e) {
//      e.printStackTrace();
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//    return responseEntity;
//  }

  @Override
  @Transactional(readOnly = true)
  public ResponseEntity getAccessToken(LoginReqBody restLoginReqBody) {

    User user = userRepository.findByEmail(restLoginReqBody.getEmail());

    // 계정이 존재하는지 체크
    if (user == null) {
      throw new BadRequestException(ResponseErrorCode.ENTERED_INVALID.getMessage());
    }
    // 탈퇴한 계정인지 체크
    this.isLeaved(user);
    // 비밀번호가 일치하는지 체크
    if (!this.matchPassword(user, restLoginReqBody.getPassword())) {
      throw new BadRequestException(ResponseErrorCode.ENTERED_INVALID.getMessage());
    }

    return ResponseEntity.ok(
        ImmutableMap
            .builder()
            .put("access_token", this.getJWT(user))
            .build()
    );
  }

  @Override
  @Transactional
  public String findPasswordByEmail(String email, HttpServletRequest request) {

    if (userRepository.existsByEmail(email)) {
      CertReqBody certReqBody = new CertReqBody();
      certReqBody.setEmail(email);
      certReqBody.setMode(CertReqBody.Mode.FIND_PASSWORD_EMAIL);
      String code = certService.cert(certReqBody, request);
      log.debug("# find password email ::: {}", code);
      return code;
    } else {
      throw new BadRequestException(ResponseErrorCode.NOT_EXIST_EMAIL.getMessage());
    }
  }

  @Override
  @Transactional(readOnly = true)
  public boolean isExpiredPassword(User user) {
    LocalDateTime upd = user.getUserDetailsMeta().getUpdatedPasswordDateTime();
    return DateUtils.after(LocalDateTime.now(), upd.plusDays(privacyExpireTime.getPassword()));
  }

  @Override
  @Transactional
  public TermsAgree subscribeByEmail(boolean subscribe, String email) {
    User user = userRepository.findByEmail(email);

    if (user == null) {
      throw new UnAuthorizedException();
    }

    user.getTermsAgree().setEmailRcv(subscribe);
    user.getTermsAgree().setEmailRcvDate(LocalDateTime.now());
    return user.getTermsAgree();
  }

  @Override
  @Transactional
  public TermsAgree subscribeBySMS(boolean subscribe, String email) {
    User user = userRepository.findByEmail(email);

    if (user == null) {
      throw new UnAuthorizedException();
    }

    user.getTermsAgree().setSmsRcv(subscribe);
    user.getTermsAgree().setSmsRcvDate(LocalDateTime.now());
    return user.getTermsAgree();
  }

  @Override
  @Transactional
  public TermsAgree subscribeByKakao(boolean subscribe, String email) {
    User user = userRepository.findByEmail(email);

    if (user == null) {
      throw new UnAuthorizedException();
    }

    user.getTermsAgree().setKakaoRcv(subscribe);
    user.getTermsAgree().setKakaoRcvDate(LocalDateTime.now());
    return user.getTermsAgree();
  }

  @Override
  @Transactional
  public List<User> userByAdmin() {
    List<User> users = Lists.newArrayList(userRepository.findAll(
        UserPredicate.getInstance()
            .role(Authority.Role.ADMIN)
            .role(Authority.Role.SUPER)
            .values()));
    users.removeIf(user -> Objects.equals(user.getEmail(), "master@galaxia.co.kr"));
    return users;
  }

  private void isLeaved(User user) {
    if (user != null) {
      if (!user.getUserDetailsMeta().isEnabled()
          || user.getLeaveMeta().isLeave()) {
        throw new BadRequestException(ResponseErrorCode.LEAVED_USER.getMessage());
      }
    }
  }

  private void isDormant(User user) {
    if (user != null) {
      if (!user.getUserDetailsMeta().isEnabled()
          || user.getDormancyMeta().isDormancy()) {
        throw new BadRequestException(ResponseErrorCode.DORMANCY_USER.getMessage());
      }
    }
  }


  public static User setUserAuthorities(AuthorityRepository authorityRepository, User user) {

    Authority.Role role = user.getRole();
    if (role != null) {
      Set<Authority> authorities = new HashSet<>();
//      switch (role) {
//        case SUPER:
//          authorities.add(authorityRepository.findByRole(Authority.Role.SUPER));
//        case ADMIN:
//          authorities.add(authorityRepository.findByRole(Authority.Role.ADMIN));
//        case OWNER:
//          authorities.add(authorityRepository.findByRole(Authority.Role.OWNER));
//        case USER:
//          authorities.add(authorityRepository.findByRole(Authority.Role.USER));
//      }
      authorities.add(authorityRepository.findByRole(role));
      user.setAuthorities(authorities);
    }
    return user;
  }

  @Override
  @Transactional
  public void dormantByMobile(String mobile, HttpServletRequest request, DormantReqBody reqBody) {
//    List<User> users = userRepository.findByMobileAndFullNameAndUserDetailsMetaEnabledTrue(mobile, reqBody.getFullName());
//
//
//    if (users.size() > 0) {
//
//      users.forEach(user -> {
//        if (!user.getRelativeBusinessUser().getName().equals(reqBody.getCompanyName())
//
//            || !user.getDormancyMeta().isDormancy()
//        ) {
//          throw new BadRequestException(ResponseErrorCode.ENTERED_INVALID.getMessage());
//        }
//      });
//
//      CertReqBody certReqBody = new CertReqBody();
//      certReqBody.setMobile(mobile);
//      certReqBody.setMode(CertReqBody.Mode.FIND_ACCOUNT_MOBILE);
//      String code = certService.cert(certReqBody, request);
//      log.debug("# dormant mobile ::: {}", code);
//    } else {
//      throw new BadRequestException(ResponseErrorCode.ENTERED_INVALID.getMessage());
//    }
  }

  @Override
  @Transactional
  public void dormantByMobileConfirm(DormantReqBody dormantReqBody) {

//    List<User> users = userRepository.findByMobileAndFullNameAndUserDetailsMetaEnabledTrue(dormantReqBody.getMobile(), dormantReqBody.getFullName());
//
//
//    if (users.size() > 0) {
//
//      users.forEach(user -> {
//        if (!user.getRelativeBusinessUser().getName().equals(dormantReqBody.getCompanyName())
//
//            || !user.getDormancyMeta().isDormancy()
//        ) {
//          throw new BadRequestException(ResponseErrorCode.ENTERED_INVALID.getMessage());
//        } else {
//          CertConfirmReqBody confirm = new CertConfirmReqBody();
//          confirm.setMode(CertReqBody.Mode.FIND_ACCOUNT_MOBILE);
//          confirm.setCode(dormantReqBody.getCode());
//          confirm.setMobile(dormantReqBody.getMobile());
//          AuthentiCodeResBody result = certService.confirm(confirm);
//
//          // 성공
//          if (Objects.equals(result.getResultCode(), Authenticode.RESULT_SUCCESS)) {
//            //휴면정보 갱신, 데이터 원복
//            user.setDormancyMeta(new DormancyMeta());
//            DormancyUser dormancyUser = dormancyUserRepository.findById(user.getId()).get();
//            user.setMobile(dormancyUser.getMobile());
//            user.setFullName(dormancyUser.getFullName());
//            user.setEmail(dormancyUser.getEmail());
//            user.setPassword(dormancyUser.getPassword());
//            dormancyUserRepository.deleteById(dormancyUser.getId());
//            return;
//          }
//
//          // 실패
//          if (Objects.equals(result.getResultCode(), Authenticode.RESULT_EXPIRE)) {
//            throw new BadRequestException(ResponseErrorCode.EXPIRED_VERIFICATION_CODE.getMessage());
//          } else {
//            throw new BadRequestException(ResponseErrorCode.NOT_CERTIFIED.getMessage());
//          }
//        }
//      });
//    }
  }

  @Override
  @Transactional
  public void setDormant() {
    // TODO 휴면 계정 처리
    List<UserInfoResBody> list = new ArrayList<>();

    list.forEach(userInfoResBody -> {
      User user = this.get(userInfoResBody.getId());
      //365일 이상 로그인하지 않았을 시 휴면테이블로 정보 이동(이메일, 비밀번호, 전화번호, 이름)
      if (!user.getDormancyMeta().isDormancy()
          && DateUtils.getNumberOfDays(user.getDormancyMeta().getLastLoginDate(), DateUtils.getLocalDateTime()) > 365) {
        DormancyMeta dormancyMeta = new DormancyMeta();
        dormancyMeta.setDormancy(true);
        dormancyMeta.setDormancyTime(LocalDateTime.now());
        user.setDormancyMeta(dormancyMeta);
        user.setEmail("dormant");
        user.setFullName("dormant");
        String password = user.getPassword();
        user.setPassword("dormant");
        user.setMobile("");
//        dormancyUserRepository.save(new DormancyUser(user.getId(), userInfoResBody.getEmail(), password, userInfoResBody.getName(), userInfoResBody.getMobile()));
      }
    });
  }

  @Override
  @Transactional
  public void updateUserInfo(String email, UpdateUserInfoReqBody updateUserInfoReqBody) {
    Optional.ofNullable(userRepository.findByEmail(email))
            .ifPresent(user -> {
              if (updateUserInfoReqBody.getFullName() != null) {
                user.setFullName(updateUserInfoReqBody.getFullName());
              }
              if (updateUserInfoReqBody.getBirthDate() != null) {
                user.setBirthDate(updateUserInfoReqBody.getBirthDate());
              }
              if (updateUserInfoReqBody.getGender() != null) {
                user.setGender(updateUserInfoReqBody.getGender());
              }
            });
  }

  @Override
  @Transactional
  public Boolean checkSuperAdminAuthAccess(User user) {
    if (!user.getRoleTopLevel().equals(Authority.Role.SUPER)) {
      return false;
    } else {
      return true;
    }
  }

  @Override
  @Transactional
  public Boolean checkAdminAuthAccess(User user) {
    if (!user.getRoleTopLevel().equals(Authority.Role.SUPER)
            && !user.getRoleTopLevel().equals(Authority.Role.ADMIN)) {
      return false;
    } else {
      return true;
    }
  }

  @Override
  @Transactional
  public Boolean checkWriteAuthAccess(User user) {
    if (!user.getRoleTopLevel().equals(Authority.Role.SUPER)
            && !user.getRoleTopLevel().equals(Authority.Role.ADMIN)
            && !user.getRoleTopLevel().equals(Authority.Role.WRITER)
    ) {
      return false;
    } else {
      return true;
    }
  }

  @Override
  @Transactional
  public Boolean checkAgencyAuthAccess(User user) {
    if (!user.getRoleTopLevel().equals(Authority.Role.SUPER)
            && !user.getRoleTopLevel().equals(Authority.Role.ADMIN)
            && !user.getRoleTopLevel().equals(Authority.Role.AGENCY)
    ) {
      return false;
    } else {
      return true;
    }
  }

}
