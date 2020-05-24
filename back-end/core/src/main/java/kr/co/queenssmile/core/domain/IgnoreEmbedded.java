package kr.co.queenssmile.core.domain;


/**
 * 데이터를 수정할때 BeanUtils.copyProperties 메소드를 사용할 경우 변경되지 않아야 할 필드는 ignoreProperties 인자값으로 처리하는데,
 *
 * @Embedded 필드(객체타입필드)는 적용되지 않는 문제를 해결 하기 위한 메소드이다.
 * - 객체를 copy 하기 전에 이 메소드를 먼저 수행해야 한다.
 */
public interface IgnoreEmbedded<T> {

  void ignoreEmbedded(T ori);
}
