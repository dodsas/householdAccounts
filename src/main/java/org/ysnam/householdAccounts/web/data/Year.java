package org.ysnam.householdAccounts.web.data;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
public class Year {
    public List<String> years;
    String selectedYear;
}
