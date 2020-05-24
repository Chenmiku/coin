package kr.co.queenssmile.core.domain.board.faq.category;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class FaqCategoryRepositoryImpl implements FaqCategoryRepositoryCustom {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public boolean existsByNameAndKoKr(String name) {
        QFaqCategory qFaqCategory = QFaqCategory.faqCategory;

        return jpaQueryFactory.from(qFaqCategory)
                .where(qFaqCategory.name.textKoKr.eq(name)).fetchCount() > 0;
    }

    @Override
    public boolean existsByNameAndEnUs(String name) {
        QFaqCategory qFaqCategory = QFaqCategory.faqCategory;

        return jpaQueryFactory.from(qFaqCategory)
                .where(qFaqCategory.name.textEnUs.eq(name)).fetchCount() > 0;
    }

    @Override
    public boolean existsByNameAndZhCn(String name) {
        QFaqCategory qFaqCategory = QFaqCategory.faqCategory;

        return jpaQueryFactory.from(qFaqCategory)
                .where(qFaqCategory.name.textZhCn.eq(name)).fetchCount() > 0;
    }

    @Override
    public boolean existsByNameAndZhTw(String name) {
        QFaqCategory qFaqCategory = QFaqCategory.faqCategory;

        return jpaQueryFactory.from(qFaqCategory)
                .where(qFaqCategory.name.textZhTw.eq(name)).fetchCount() > 0;
    }

    @Override
    public boolean existsByNameAndJaJp(String name) {
        QFaqCategory qFaqCategory = QFaqCategory.faqCategory;

        return jpaQueryFactory.from(qFaqCategory)
                .where(qFaqCategory.name.textJaJp.eq(name)).fetchCount() > 0;
    }


}
