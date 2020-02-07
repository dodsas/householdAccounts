package org.ysnam.householdAccounts.core.fileManage;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ysnam.householdAccounts.core.filter.Normalization;
import org.ysnam.householdAccounts.core.filter.Split;
import org.ysnam.householdAccounts.core.model.Item;
import org.ysnam.householdAccounts.core.model.ItemRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

@Service
@Log4j2
public class HouseholderFileUtils {

    private static final String PDF_NAME = "householdAccounts.pdf";
    private static final String TXT_NAME = "householdAccounts.txt";

    public static void makeTextFile() {

        File pdfFile = new File(PDF_NAME);
        File txtFile = new File(TXT_NAME);
        PdfUtils.getFileFromUrl(
                "https://api.bitbucket.org/2.0/repositories/dodsas/data/src/master/householdAccounts.pdf",
//                "https://api.bitbucket.org/2.0/repositories/dodsas/home/src/master/doc/householdAccounts.pdf",
                pdfFile);

        PdfUtils.extractingText(
                pdfFile,
                txtFile
        );
    }

    public static BufferedReader getTxtBufferedReader() {
        try {
            return new BufferedReader(new FileReader(new File(TXT_NAME)));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static List<Item> getItemList() {
        makeTextFile();
        Split split = Split.getInstance();
        return split.split(Normalization.doNormalization(false));
    }

    @Autowired
    ItemRepository itemRepository;

    public void update(){
        List<Item> itemList = getItemList();
        itemList.forEach(
            c -> {
                log.info(c);
                itemRepository.save(c);
            }
        );
//        itemRepository.saveAll(getItemList());
    }
}

