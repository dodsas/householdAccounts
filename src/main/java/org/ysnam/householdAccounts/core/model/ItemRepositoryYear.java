package org.ysnam.householdAccounts.core.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.ysnam.householdAccounts.web.pagination.Findable;

import java.util.List;

@Service
public class ItemRepositoryYear implements Findable<Item, ItemConditionYearly> {

    @Autowired
    ItemRepository itemRepository;

    List<String> getListOfyyyy(){

        return itemRepository.getListOfyyyy();
    }

    @Override
    public Page<Item> findAll(Pageable pageable, ItemConditionYearly conditionYearly){
        return itemRepository.findAll(pageable);
    }

    @Override
    public long count() {
        return itemRepository.count();
    }
}
