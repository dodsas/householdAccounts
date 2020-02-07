package org.ysnam.householdAccounts.web.pagination;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.ysnam.householdAccounts.core.model.Item;
import org.ysnam.householdAccounts.core.model.ItemConditionYearly;
import org.ysnam.householdAccounts.core.model.ItemRepository;
import org.ysnam.householdAccounts.core.model.ItemRepositoryYear;

@Service
public class PaginationServiceYearly extends PaginationService<Item, ItemConditionYearly, ItemRepositoryYear> {

}
