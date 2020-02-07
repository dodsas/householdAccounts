package org.ysnam.householdAccounts.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;

@Data
@NoArgsConstructor
public class StatisticsData {

    public MerchantType merchantType;
    public Long s;

//    public String getS(){
//        DecimalFormat decimalFormat = new DecimalFormat("#,###");
//        return decimalFormat.format(s);
//    }

//    public StatisticsData(Statistics s){
//        this.merchantType = s.getMerchantType();
//        this.s = s.getS();
//    }
    public String getS(){
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(s);
    }

    public StatisticsData(MerchantType merchantType, Long s){
        this.merchantType = merchantType;
        this.s = s;
    }
}
