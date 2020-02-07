package org.ysnam.householdAccounts.core.simple;

import org.junit.Test;
import org.ysnam.householdAccounts.core.fileManage.HouseholderFileUtils;
import org.ysnam.householdAccounts.core.fileManage.PdfUtils;
import org.ysnam.householdAccounts.core.filter.Normalization;
import org.ysnam.householdAccounts.core.filter.Split;
import org.ysnam.householdAccounts.core.model.Item;

import java.io.*;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IoTest {

    @Test
    public void GIT에서_파일읽기() {
        PdfUtils.getFileFromUrl(
        "https://api.bitbucket.org/2.0/repositories/dodsas/home/src/master/doc/householdAccounts.pdf",
                new File("householdAccounts.pdf"));
    }

    @Test
    public void PDF파일스트링으로변환(){
        PdfUtils.extractingText(
                new File("householdAccounts.pdf"),
                new File("householdAccounts.txt") );
    }

    @Test
    public void 깃에서받아와서_텍스변환까지한방에(){
        HouseholderFileUtils.makeTextFile();
    }

    @Test
    public void 스트링변환된파일_노멀라이제이션() throws IOException {
        BufferedReader bufferedReader = HouseholderFileUtils.getTxtBufferedReader();
        String line;
        String pattern1 = "(^20[0-9][0-9]\\. 1?[0-9]\\. [1-9]?[0-9]\\. .. .*$)";
        String pattern2 = "(^16449999.*$)";
        Pattern pattern = Pattern.compile(pattern1+"|"+pattern2);

        while((line = bufferedReader.readLine()) != null){
            //2018. 9. 26. 오전 1.14
            Matcher matcher = pattern.matcher(line);
            if(matcher.find()){
                System.out.println(line);
            }
        }
    }

    @Test
    public void 스트링변환된파일_노말라이제이션2() throws IOException {
        File f = Normalization.doNormalization(true);

        BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
        String line;
        while((line = bufferedReader.readLine()) != null){
            System.out.println(line);
        }
    }

    @Test
    public void 노말라이제이션된파일_각거래별로쪼개기() {
        Split split = Split.getInstance();
        List<Item> itemList = split.split(Normalization.doNormalization(false));

        /*for(Item item : itemList){
            System.out.println(item);
        }*/
        for(Item item : itemList){
            if(item.getMerchantType() == null){
                //System.out.println(item.getMerchant());
                System.out.println(item);
            }
        }

    }

    @Test
    public void 년도만추출하기(){
        System.out.println(Calendar.getInstance().get(Calendar.YEAR)+"/");
    }
}
