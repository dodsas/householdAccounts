package org.ysnam.householdAccounts.core.model;

import lombok.Data;

@Data
public class ItemConditionMonthly {
    String year;
    String month;

    public String getYearAndMonth(){
        return year + "-" + month + "-";
    }
}
