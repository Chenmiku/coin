package kr.co.queenssmile.api.web.account;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import kr.co.queenssmile.api.service.snsauth.APIFacebookService;
import kr.co.queenssmile.api.service.snsauth.SNSAuthService;
import kr.co.queenssmile.api.service.user.UserService;
import kr.co.queenssmile.core.config.exception.BadRequestException;
import kr.co.queenssmile.core.domain.user.User;
import kr.co.queenssmile.core.domain.user.UserRepository;
import kr.co.queenssmile.core.domain.user.sns.Facebook;
import kr.co.queenssmile.core.domain.user.sns.SNSStatus;
import kr.co.queenssmile.core.domain.user.sns.SNSType;
import kr.co.queenssmile.core.model.reqbody.user.ChangeSnsReqBody;
import kr.co.queenssmile.core.model.resbody.user.SNSResBody;
import kr.co.queenssmile.core.utils.ValidUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("api/v1/auth")
public class APISNSAuthController {

  @Autowired
  private APIFacebookService apiFacebookService;

  @Autowired
  private SNSAuthService snsAuthService;

  @Autowired
  private UserService userService;

  @Autowired
  private UserRepository userRepository;

  @Operation(summary = "[user-sns-auth-1] Facebook (페이스북)", description = "")
  @PostMapping("facebook")
  public ResponseEntity<?> facebook(@RequestBody ChangeSnsReqBody requestBody) {

    if (requestBody == null) {
      throw new BadRequestException();
    }

    String code = requestBody.getCode();
    String redirectUri = requestBody.getRedirectUri();

    log.debug("===facebook");
    log.debug("code ::: {}", code);
    log.debug("redirectUri ::: {}", redirectUri);

    Map accessTokenResult = apiFacebookService.getAccessToken(code, redirectUri);

    String accessToken = accessTokenResult.get("access_token").toString();
    String tokenType = accessTokenResult.get("token_type").toString();
    String expiresIn = accessTokenResult.get("expires_in").toString();

    log.debug("===facebook accessTokenResult");
    log.debug("accessToken ::: {}", accessToken);
    log.debug("tokenType ::: {}", tokenType);
    log.debug("expiresIn ::: {}", expiresIn);

    Map meResult = apiFacebookService.getMe(accessToken);

    String id = meResult.get("id").toString();
    String name = meResult.get("name").toString();
    String email = meResult.get("email") == null ? null : meResult.get("email").toString();
    Map pictureMap = meResult.get("picture") == null ? null : (Map) meResult.get("picture");
    String gender = meResult.get("gender") == null ? null : meResult.get("gender").toString();

    log.debug("===facebook meResult");
    log.debug("id ::: {}", id);
    log.debug("name ::: {}", name);
    log.debug("email ::: {}", email);
    log.debug("pictureMap ::: {}", pictureMap);
    log.debug("gender ::: {}", gender);

    Facebook facebook = Facebook.of(id, name, email, pictureMap, gender);
    return facebookNative(facebook);
  }

  @Operation(summary = "[user-sns-auth-2] Facebook Token (페이스북 Token)", description = "")
  @PostMapping("facebook/token")
  public ResponseEntity<?> facebook(@RequestBody ChangeSnsReqBody requestBody,
                                    @Parameter(hidden = true) OAuth2Authentication oAuth2Authentication) {

    ValidUtils.isForbidden(oAuth2Authentication);

    if (requestBody == null) {
      throw new BadRequestException();
    }

    User user = userRepository.findByEmail(oAuth2Authentication.getPrincipal().toString());

    String code = requestBody.getCode();
    String redirectUri = requestBody.getRedirectUri();

    log.debug("===facebook");
    log.debug("code ::: {}", code);
    log.debug("redirectUri ::: {}", redirectUri);

    Map accessTokenResult = apiFacebookService.getAccessToken(code, redirectUri);

    String accessToken = accessTokenResult.get("access_token").toString();
    String tokenType = accessTokenResult.get("token_type").toString();
    String expiresIn = accessTokenResult.get("expires_in").toString();

    log.debug("===facebook accessTokenResult");
    log.debug("accessToken ::: {}", accessToken);
    log.debug("tokenType ::: {}", tokenType);
    log.debug("expiresIn ::: {}", expiresIn);

    Map meResult = apiFacebookService.getMe(accessToken);

    String id = meResult.get("id").toString();
    String name = meResult.get("name").toString();
    String email = meResult.get("email") == null ? null : meResult.get("email").toString();
    Map pictureMap = meResult.get("picture") == null ? null : (Map) meResult.get("picture");
    String gender = meResult.get("gender") == null ? null : meResult.get("gender").toString();

    log.debug("===facebook meResult");
    log.debug("id ::: {}", id);
    log.debug("name ::: {}", name);
    log.debug("email ::: {}", email);
    log.debug("pictureMap ::: {}", pictureMap);
    log.debug("gender ::: {}", gender);

    if (user.getSocialId() == null || user.getSocialId().getFacebookId() == null || !user.getSocialId().getFacebookId().equals(id)) {
      throw new BadRequestException("사용자와 연결되지 않은 상태입니다.");
    }

    return ResponseEntity.ok(accessTokenResult);
  }

  @Operation(summary = "[user-sns-auth-3] Facebook Native (페이스북 Native)", description = "")
  @PostMapping("facebook/native")
  public ResponseEntity<?> facebookNative(@RequestBody Facebook facebook) {
    return facebookAuth(facebook);
  }

  private ResponseEntity<?> facebookAuth(Facebook facebook) {
    log.debug("facebook ::: {}", facebook);
    SNSStatus status = snsAuthService.getStatus(facebook);
    SNSResBody body = SNSResBody.of(true);

    log.debug("status ::: {} ", status);

    switch (status) {
      // AccessToken 발급
      case CONNECT:
        body.setMessage(SNSStatus.CONNECT.name());
      case LINKED:
        body.setMessage(SNSStatus.LINKED.name());
        body.setSnsType(SNSType.NAVER);
        body.setId(facebook.getId());
        body.setName(facebook.getName());
        body.setEmail(facebook.getEmail());
        body.setImage(facebook.getPicture());

        String accessToken = userService.getJWT(userRepository.findBySocialIdFacebookId(facebook.getId()));
        body.setAccess_token(accessToken);
//        body.setRefresh_token(jwtUtils.generatorByRefreshToken(accessToken));
        return ResponseEntity.ok(body);

      case LEAVED_ACCOUNT:

        body = SNSResBody.of(false);
        body.setMessage(SNSStatus.LEAVED_ACCOUNT.name());
        body.setSnsType(SNSType.FACEBOOK);
        body.setId(facebook.getId());
        body.setName(facebook.getName());
        body.setEmail(facebook.getEmail());
        body.setImage(facebook.getPicture());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);

      case NOT_MATCH_SNS:

        body = SNSResBody.of(false);
        body.setMessage(SNSStatus.NOT_MATCH_SNS.name());
        body.setSnsType(SNSType.FACEBOOK);
        body.setId(facebook.getId());
        body.setName(facebook.getName());
        body.setEmail(facebook.getEmail());
        body.setImage(facebook.getPicture());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);

      case NOT_EXISTED_ACCOUNT:

        body = SNSResBody.of(false);
        body.setMessage(SNSStatus.NOT_EXISTED_ACCOUNT.name());
        body.setSnsType(SNSType.FACEBOOK);
        body.setId(facebook.getId());
        body.setName(facebook.getName());
        body.setEmail(facebook.getEmail());
        body.setImage(facebook.getPicture());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);

      case NOT_PROVIDED_EMAIL:

        body = SNSResBody.of(false);
        body.setMessage(SNSStatus.NOT_PROVIDED_EMAIL.name());
        body.setSnsType(SNSType.FACEBOOK);
        body.setId(facebook.getId());
        body.setName(facebook.getName());
        body.setImage(facebook.getPicture());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);

      default:
        throw new BadRequestException("페이스북 로그인에서 나올수 없는 경우");
    }
  }
}
