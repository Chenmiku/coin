package kr.co.queenssmile.core.domain.board.faq;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class FaqRepositoryImpl implements FaqRepositoryCustom {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

//    @Override
//    public Long highestOrder(long idCategory) {
//
//        QEBFaq qebFaq = QEBFaq.eBFaq;
//        QEBFCategory qebfCategory = QEBFCategory.eBFCategory;
//
//        return jpaQueryFactory
//                .select(qebFaq.orderAscending.max())
//                .from(qebFaq)
//                .leftJoin(qebFaq.categories, qebfCategory)
//                .where(qebfCategory.id.eq(idCategory))
//                .fetchOne();
//    }
//
//    @Override
//    public EBFaq previous(long orderAscending, long idCategory) {
//
//        QEBFaq qebFaq = QEBFaq.eBFaq;
//        QEBFCategory qebfCategory = QEBFCategory.eBFCategory;
//
//        return jpaQueryFactory
//                .selectFrom(qebFaq)
//                .leftJoin(qebFaq.categories, qebfCategory)
//                .where(qebFaq.orderAscending.gt(orderAscending))
//                .where(qebfCategory.id.eq(idCategory))
//                .orderBy(qebFaq.orderAscending.asc())
//                .limit(1)
//                .fetchFirst();
//    }
//
//    @Override
//    public EBFaq next(long orderAscending, long idCategory) {
//
//        QEBFaq qebFaq = QEBFaq.eBFaq;
//        QEBFCategory qebfCategory = QEBFCategory.eBFCategory;
//
//        return jpaQueryFactory
//                .selectFrom(qebFaq)
//                .leftJoin(qebFaq.categories, qebfCategory)
//                .where(qebFaq.orderAscending.lt(orderAscending))
//                .where(qebfCategory.id.eq(idCategory))
//                .orderBy(qebFaq.orderAscending.desc())
//                .limit(1)
//                .fetchFirst();
//    }
//    @Override
//    public List<Faq> getAllByCategory(long idCategory) {
//        QEBFaq qebFaq = QEBFaq.eBFaq;
//        QFaq qFaq = QFaq.faq;
//        QCategory qCategory = QCategory.category;
//
//        return jpaQueryFactory
//                .selectFrom(qFaq)
//                .leftJoin(qFaq.categories, q)
//                .where(qebFaq.orderAscending.lt(orderAscending))
//                .where(qebfCategory.id.eq(idCategory))
//                .orderBy(qebFaq.orderAscending.desc())
//                .limit(1)
//                .fetchFirst();
//    }
}
