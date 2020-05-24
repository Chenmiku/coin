package kr.co.queenssmile.core.domain;

import kr.co.queenssmile.core.model.BaseResponseBody;
import kr.co.queenssmile.core.model.BaseResponseThumbBody;

/**
 * REST API
 * Entity To REST Body
 * @param <P> Original
 * @param <T> Thumbnail
 */
public interface RestEntityThumbBody<P extends BaseResponseBody, T extends BaseResponseThumbBody> {

  P toBody(java.util.Locale locale); // ORIGINAL

  T toThumbBody(java.util.Locale locale); // THUMBNAIL
}
