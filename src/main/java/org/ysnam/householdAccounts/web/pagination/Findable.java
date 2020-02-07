package org.ysnam.householdAccounts.web.pagination;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface Findable<T, Condition> {
    Page<T> findAll(Pageable pageable, Condition condition);
    long count();
}
