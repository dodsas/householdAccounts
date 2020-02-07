package org.ysnam.householdAccounts.web.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class PageInfo {
    public static final String ACTIVE = " active";
    public static final String INACTIVE = "";
    String activeMode;
    Integer number;

    public PageInfo(Integer number){
        this.number = number;
        activeMode = INACTIVE;
    }

    public void active() {
        activeMode = ACTIVE;
    }

    public void inactive() {
        activeMode = INACTIVE;
    }
}
