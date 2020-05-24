package kr.co.queenssmile.core.domain.setting;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AppSettingRepository extends
    PagingAndSortingRepository<AppSetting, Long>,
    QuerydslPredicateExecutor<AppSetting> {
}
