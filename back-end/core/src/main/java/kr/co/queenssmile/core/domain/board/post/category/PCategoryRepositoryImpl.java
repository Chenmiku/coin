package kr.co.queenssmile.core.domain.board.post.category;

import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.co.queenssmile.core.domain.board.post.Post;
import org.springframework.beans.factory.annotation.Autowired;

public class PCategoryRepositoryImpl implements PCategoryRepositoryCustom {

  @Autowired
  private JPAQueryFactory jpaQueryFactory;

  @Override
  public boolean existsByNameAndTypeAndKoKr(String name, Post.Type type) {
    QPCategory qCategory = QPCategory.pCategory;

    return jpaQueryFactory.from(qCategory)
      .where(qCategory.type.eq(type))
      .where(qCategory.name.textKoKr.eq(name)).fetchCount() > 0;
  }

  @Override
  public boolean existsByNameAndTypeAndEnUs(String name, Post.Type type) {
    QPCategory qCategory = QPCategory.pCategory;

    return jpaQueryFactory.from(qCategory)
      .where(qCategory.type.eq(type))
      .where(qCategory.name.textEnUs.eq(name)).fetchCount() > 0;
  }

  @Override
  public boolean existsByNameAndTypeAndZhCn(String name, Post.Type type) {
    QPCategory qCategory = QPCategory.pCategory;

    return jpaQueryFactory.from(qCategory)
      .where(qCategory.type.eq(type))
      .where(qCategory.name.textZhCn.eq(name)).fetchCount() > 0;
  }

  @Override
  public boolean existsByNameAndTypeAndZhTw(String name, Post.Type type) {
    QPCategory qCategory = QPCategory.pCategory;

    return jpaQueryFactory.from(qCategory)
      .where(qCategory.type.eq(type))
      .where(qCategory.name.textZhTw.eq(name)).fetchCount() > 0;
  }

  @Override
  public boolean existsByNameAndTypeAndJaJp(String name, Post.Type type) {
    QPCategory qCategory = QPCategory.pCategory;

    return jpaQueryFactory.from(qCategory)
      .where(qCategory.type.eq(type))
      .where(qCategory.name.textJaJp.eq(name)).fetchCount() > 0;
  }
}
