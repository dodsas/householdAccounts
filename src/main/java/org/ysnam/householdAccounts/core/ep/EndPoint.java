package org.ysnam.householdAccounts.core.ep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ysnam.householdAccounts.core.fileManage.HouseholderFileUtils;
import org.ysnam.householdAccounts.core.filter.Normalization;
import org.ysnam.householdAccounts.core.filter.Split;
import org.ysnam.householdAccounts.core.model.Item;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class EndPoint {
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/refresh")
    public String refresh() {

        HouseholderFileUtils.makeTextFile();

        Split split = Split.getInstance();
        List<Item> itemList = split.split(Normalization.doNormalization(false));

        StringBuilder sb = new StringBuilder();
        for (Item item : itemList) {
            sb.append(item + "<br/>");
        }

        return sb.toString();
    }

    @Autowired
    HouseholderFileUtils householderFileUtils;

    @GetMapping("/update")
    public String update(){
        householderFileUtils.update();
        return "success";
    }
}
