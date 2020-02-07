package org.ysnam.householdAccounts.core.filter;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.StringUtils;
import org.ysnam.householdAccounts.core.model.Item;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Log4j2
public class Split {

    private List<Item> itemList;

    private Split() {
        itemList = new ArrayList<>();
    }

    public List<Item> split(File file){

        try {
            HouseholdReader reader = new HouseholdReader(new BufferedReader(new FileReader(file)));
            while(true) {

                String date = reader.readLine();
                if(StringUtils.isEmpty(date)){
                    break;
                }

                Item item = new Item();
                //yyyy
                item.setYyyy(reader.getYear());

                //[KB]07/03 16/30
                //[KB]01/18 18.16
                item.setDate(date.replace(".", "/").replace(
                        "[KB]",
                        reader.getYear()+"/"));

                //471002**939
                reader.readLine();

                //딸기슈퍼 or 제휴CD출금 (제휴출금경우 다음라인 점프)
                item.setMerchant(reader.readLine());

                if (
                        !item.getMerchant().equals("출금취소") &&
                        !item.getMerchant().equals("제휴CD출금") &&
                        !item.getMerchant().equals("ATM입금")
                ){
                    //체크카드출금
                    item.setDetail(reader.readLine());
                }

                //6,000
                item.setAmount(Integer.valueOf(reader.readLine().replace(",","")));

                //잔액991
                item.setRemainAmount(Integer.valueOf(
                        reader.readLine()
                                .replace(",","")
                                .replace("잔액", "")
                ));

//                log.info(item);

                itemList.add(item);
            }

            return itemList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private static Split split = new Split();
    public static Split getInstance(){
        return split;
    }
}

