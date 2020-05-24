package kr.co.queenssmile.core.domain.board.qna.category;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class QnaCategoryRepositoryImpl implements QnaCategoryRepositoryCustom {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public boolean existsByNameAndKoKr(String name) {

        QQnaCategory qQnaCategory = QQnaCategory.qnaCategory;

        return jpaQueryFactory.from(qQnaCategory)
                .where(qQnaCategory.name.textKoKr.eq(name)).fetchCount() > 0;
    }

    @Override
    public boolean existsByNameAndEnUs(String name) {

        QQnaCategory qQnaCategory = QQnaCategory.qnaCategory;

        return jpaQueryFactory.from(qQnaCategory)
                .where(qQnaCategory.name.textEnUs.eq(name)).fetchCount() > 0;
    }

    @Override
    public boolean existsByNameAndZhCn(String name) {

        QQnaCategory qQnaCategory = QQnaCategory.qnaCategory;

        return jpaQueryFactory.from(qQnaCategory)
                .where(qQnaCategory.name.textZhCn.eq(name)).fetchCount() > 0;
    }

    @Override
    public boolean existsByNameAndZhTw(String name) {

        QQnaCategory qQnaCategory = QQnaCategory.qnaCategory;

        return jpaQueryFactory.from(qQnaCategory)
                .where(qQnaCategory.name.textZhTw.eq(name)).fetchCount() > 0;
    }

    @Override
    public boolean existsByNameAndJaJp(String name) {

        QQnaCategory qQnaCategory = QQnaCategory.qnaCategory;

        return jpaQueryFactory.from(qQnaCategory)
                .where(qQnaCategory.name.textJaJp.eq(name)).fetchCount() > 0;
    }
}
