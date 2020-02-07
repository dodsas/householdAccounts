package org.ysnam.householdAccounts.core.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.ysnam.householdAccounts.core.model.ItemRepository;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
public class NativeQuery {

    @Autowired
    ItemRepository itemRepository;

    @Test
    public void yyyy그룹바이조회(){
        List<String> aa = itemRepository.getListOfyyyy();

        System.out.println(aa);
    }
}
