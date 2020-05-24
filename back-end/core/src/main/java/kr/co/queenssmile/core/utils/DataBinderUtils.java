package kr.co.queenssmile.core.utils;

import kr.co.queenssmile.core.config.exception.ForbiddenException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataBinderUtils {

  public static void objectValidate(org.springframework.validation.BindingResult result) {
    if (result.hasErrors()) {
      result.getAllErrors().forEach(objectError -> {
        log.error("name -> " + objectError.getObjectName() + ", msg -> " + objectError.getDefaultMessage());
      });
      throw new ForbiddenException(result.getAllErrors().toString());
    }
  }
}
