package org.ysnam.householdAccounts.core.filter;

import lombok.extern.log4j.Log4j2;
import org.ysnam.householdAccounts.core.fileManage.HouseholderFileUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
public class Normalization {
    private static final String pattern1 = "(^20[0-9][0-9]\\. 1?[0-9]\\. [1-9]?[0-9]\\. .. .*$)";
    private static final String pattern2 = "|(^16449999.*$)";
    // 그저께 오전 5.55 | 어제 오후 12.41 | 오늘 오전 8.48 | 그저께 오전 11,00
//    private static final String pattern3 = "|(^.?.. .. [1-9]?[0-9]\\.[0-9]?[0-9]$)";
    private static final String pattern3 = "|(^그저께 오.*$)|(^어제 오.*$)|(^오늘 오.*$)";
    private static final String pattern4 = "|(^\\[Web.*$)";

    public static File doNormalization(boolean inverse){

        File outputFile = new File("normalizedFile");

        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
            BufferedReader reader = HouseholderFileUtils.getTxtBufferedReader()){

            Pattern pattern = Pattern.compile(pattern1 + pattern2 + pattern3 + pattern4);
            Pattern yearPattern = Pattern.compile(pattern1);
            String year = "2018";

            String line;
            while ((line = reader.readLine()) != null) {

                log.info(line);
                Matcher yearMatcher = yearPattern.matcher(line);
                if(yearMatcher.find()){
                    year = line.substring(0, 4);
                }

                Matcher matcher = pattern.matcher(line);
                if(inverse){
                    if(matcher.find()){
                        bufferedWriter.append(year).append(line);
                        bufferedWriter.newLine();
                    }
                } else {
                    if(!matcher.find()){
                        bufferedWriter.append(year).append(line);
                        bufferedWriter.newLine();
                    }
                }
            }
        } catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

        return outputFile;
    }
}
