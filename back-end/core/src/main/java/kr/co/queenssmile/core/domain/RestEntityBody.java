package kr.co.queenssmile.core.domain;

/**
 * REST API
 * Entity To REST Body
 *
 * @param <T> Original
 */
public interface RestEntityBody<T> {

  T toBody(java.util.Locale locale);
}
