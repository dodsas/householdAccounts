package org.ysnam.householdAccounts.core.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.ysnam.householdAccounts.web.pagination.Findable;
import org.ysnam.householdAccounts.web.util.DateUtils;

import javax.persistence.criteria.Predicate;
import java.util.Date;

@Service
public class ItemRepositoryMonthly implements Findable<Item, ItemConditionMonthly> {

    @Autowired
    ItemRepository itemRepository;

    @Override
    public Page<Item> findAll(Pageable pageable, ItemConditionMonthly conditionMonthly){

        Date startDate = DateUtils.convertToDate(conditionMonthly.getYearAndMonth() + "01 00:00");
        Date endDate = DateUtils.getEndOfDate(startDate);

        return itemRepository.findAll(
               (root, query, criteriaBuilder) -> {

                   Predicate condition1 = criteriaBuilder.lessThanOrEqualTo(root.get("date"), endDate);
                   Predicate condition2 = criteriaBuilder.greaterThanOrEqualTo(root.get("date"), startDate);

                   return criteriaBuilder.and(condition1, condition2);
               },
                pageable);
    }

    @Override
    public long count() {
        return itemRepository.count();
    }
}
