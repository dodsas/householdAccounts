package org.ysnam.householdAccounts.core.bdd;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.ysnam.householdAccounts.core.fileManage.HouseholderFileUtils;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Default {

    @Autowired
    HouseholderFileUtils householderFileUtils;

    @Test
    public void 파일_깃에서읽어와서_저장까지_한방에(){
        householderFileUtils.update();
    }
}
