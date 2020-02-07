package org.ysnam.householdAccounts.core.simple;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.ysnam.householdAccounts.core.fileManage.HouseholderFileUtils;
import org.ysnam.householdAccounts.core.model.Item;
import org.ysnam.householdAccounts.core.model.ItemRepository;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
public class dbTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void 기본적인_입력(){
        List<Item> list = HouseholderFileUtils.getItemList();
        Item one = list.get(0);
        itemRepository.save(one);
    }

    @Test
    public void 검색(){
        기본적인_입력();
        List<Item> list = HouseholderFileUtils.getItemList();
        Item one = list.get(0);
        Item find = itemRepository.findById(one.getId()).get();
        System.out.println(find);
        find.setCategory("temp");
        itemRepository.save(find);
    }
}
