package kr.co.queenssmile.core.service.authenticode;

import kr.co.queenssmile.core.domain.authenticode.Authenticode;
import kr.co.queenssmile.core.domain.authenticode.AuthenticodeRepository;
import kr.co.queenssmile.core.domain.user.User;
import kr.co.queenssmile.core.domain.user.UserRepository;
import kr.co.queenssmile.core.model.AuthentiCodeResBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;

@Slf4j
@Service
public class AuthenticodeServiceImpl implements AuthenticodeService {

  @Autowired
  private AuthenticodeRepository tokenStorageRepository;

  @Autowired
  private UserRepository userRepository;

  @Override
  @Transactional
  public String tokenCode(Authenticode authenticode) {

    String token = this.generateToken();
    authenticode.setId(token);
    Authenticode created = tokenStorageRepository.save(authenticode);
    return created.getId();
  }

  @Override
  @Transactional
  public AuthentiCodeResBody confirmByMobile(String token, String mobile) {
    AuthentiCodeResBody result = new AuthentiCodeResBody();

    Optional<Authenticode> authenticodeOpt = tokenStorageRepository.findById(token);

    if (!authenticodeOpt.isPresent()) {
      return new AuthentiCodeResBody(Authenticode.RESULT_EXPIRE);
    }

    Authenticode authenticode = authenticodeOpt.get();
//        log.debug("authenticode ::: {}", authenticode);
//        log.debug("LocalDateTime.now() ::: {}", LocalDateTime.now());
//        log.debug("authenticode.getExpireTime() ::: {}", authenticode.getExpireTime());
//        log.debug("LocalDateTime.now().isAfter(authenticode.getExpireTime()) ::: {}", LocalDateTime.now().isAfter(authenticode.getExpireTime()));
    if (authenticode.getExpireTime() == null || LocalDateTime.now().isAfter(authenticode.getExpireTime())) {
      tokenStorageRepository.deleteById(token);
      return new AuthentiCodeResBody(Authenticode.RESULT_EXPIRE);
    }

    Map<String, String> value = authenticode.toMap();
    Integer resultCode;
    log.debug(value.get("mobile"));
    log.debug(mobile);
    log.debug("@@@ : {}", value != null);
    log.debug("### : {}", Objects.equals(value.get("mobile"), mobile));
    if (value != null && Objects.equals(value.get("mobile"), mobile)) {
      resultCode = Authenticode.RESULT_SUCCESS;
      result.setResultCode(resultCode);
      result.setMobile(value.get("mobile"));
    } else {
      resultCode = Authenticode.RESULT_FAIL;
      result.setResultCode(resultCode);
    }

    log.debug("authenticode ::: {}", authenticodeOpt.toString());
    log.debug("result authenticode ::: {}", result.toString());

    tokenStorageRepository.deleteById(token);
    return result;
  }

  @Override
  public AuthentiCodeResBody getAuthCode(String token) {
    AuthentiCodeResBody result = new AuthentiCodeResBody();

    Optional<Authenticode> authenticodeOpt = tokenStorageRepository.findById(token);

    if (!authenticodeOpt.isPresent()) {
      return new AuthentiCodeResBody(Authenticode.RESULT_EXPIRE);
    }

    Authenticode authenticode = authenticodeOpt.get();

    if (authenticode.getExpireTime() == null || LocalDateTime.now().isAfter(authenticode.getExpireTime())) {
//            tokenStorageRepository.delete(token);
      return new AuthentiCodeResBody(Authenticode.RESULT_EXPIRE);
    }

    Map<String, String> value = authenticode.toMap();
    Integer resultCode;

    if (value != null && value.get("email") != null) {

      final String email = value.get("email");
      log.debug(email);
      User user = userRepository.findByEmail(value.get("email"));

      if (user == null) {
        resultCode = Authenticode.RESULT_NOT_EXISTS;
        result.setResultCode(resultCode);
      } else {
        user.getVerification().setEmail(true);
        resultCode = Authenticode.RESULT_SUCCESS;
        result.setResultCode(resultCode);
        result.setEmail(value.get("email"));
      }
    } else {
      resultCode = Authenticode.RESULT_FAIL;
      result.setResultCode(resultCode);
    }
    return result;
  }


  @Override
  @Transactional
  public AuthentiCodeResBody confirmByEmail(String token) {
    AuthentiCodeResBody result = this.getAuthCode(token);
    tokenStorageRepository.findById(token).ifPresent(authenticode-> {
      tokenStorageRepository.delete(authenticode);
    });
    return result;
  }

  @Override
  @Transactional
  public String generateToken() {
    RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('0', '6').filteredBy(LETTERS, DIGITS).build();
    String token = generator.generate(6);
    if (tokenStorageRepository.existsById(token)) {
      token = this.generateToken();
    }
    return token;
  }

  @Override
  @Transactional
  public void deletePreviousValue(String mobile) {
    this.tokenStorageRepository.deleteAllByMobile(mobile);
  }
}
