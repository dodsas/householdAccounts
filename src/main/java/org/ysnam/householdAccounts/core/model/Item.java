package org.ysnam.householdAccounts.core.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

@Data
@Entity(name = "HOUSEHOLD_ACCOUNT")
public class Item {

    @EmbeddedId
    ItemKey id;
    String category;
    String detail;
    boolean deposit;

    @Enumerated(EnumType.STRING)
    @Column(name = "MERCHANT_TYPE")
    MerchantType merchantType;

    Date date;
    String merchant;

    @Column(name = "AMOUNT")
    Integer amount;

    String yyyy;

    public void setDate(String date){
        try {
            this.date = new SimpleDateFormat("yyyy/MM/dd HH/mm").parse(date);
            this.id.setDateLong(this.date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Item(){
        id = new ItemKey();
    }

    public void setMerchant(String merchant){
        this.merchant=merchant;

        if( merchant.contains("월급여") ||
            merchant.contains("출금취소") ||
            merchant.contains("ATM입금") ||
            merchant.contains("캐쉬백")
        ){
            deposit = true;
            merchantType = MerchantType.입금;
        }

        setMerchantType();
    }

    public void setDetail(String detail){
        this.detail = detail
            .replace("(   ", "");

        if(detail.contains("입금")){
            deposit = true;
            merchantType = MerchantType.입금;
        }
    }

    public void setMerchantType(){

        for(MerchantType merchantType : MerchantType.values()) {
            if(c(merchantType.getFilter())){
                this.merchantType = merchantType;
                break;
            }
        }
    }

    private boolean c(String merchantType){
        Pattern pattern = Pattern.compile("^.*(" + merchantType + ").*$");
        return pattern.matcher(merchant).find();
    }

    public void setRemainAmount(Integer remainAmount){
        id.setRemainAmount(remainAmount);
    }

    public String getAmount(){
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(amount);
    }

    public String getDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
        return simpleDateFormat.format(date);
    }

    public Integer getRemainAmount(){
        return id.getRemainAmount();
    }

    public Long getDateLong(){
        return id.getDateLong();
    }
}
