package kr.co.queenssmile.core.domain.board.contact;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ContactRepository extends
    PagingAndSortingRepository<Contact, Long>,
    QuerydslPredicateExecutor<Contact> {
}
