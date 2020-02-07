package org.ysnam.householdAccounts.web.data;

import lombok.Data;

@Data
public class MainPage {
    Integer currentPage; // ", required = false) Integer currentPage,
    String moveIndicator; // ", required = false) Move moveIndicator,
    Integer startPage; // ", required = false) Integer startPage,
    Integer lastPage; // ", required = false) Integer lastPage,
}
