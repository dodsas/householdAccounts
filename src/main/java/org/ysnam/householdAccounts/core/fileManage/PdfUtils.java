package org.ysnam.householdAccounts.core.fileManage;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;
import java.net.URL;

public class PdfUtils {

    public static void getFileFromUrl(String urlString, File outputFile){
        try {
            FileUtils.copyURLToFile(
                    new URL(urlString),
                    outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void extractingText(File pdfFile, File txtFile) {
        PDDocument pdDocument = null;
        try {
            pdDocument = PDDocument.load(pdfFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream fileOutputStream = openOutputStream(txtFile);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        PDFTextStripper stripper;

        try {
            stripper = new PDFTextStripper();
        } catch (IOException e) {
            throw new RuntimeException("TextExtraction-extractingText ERROR: PDFTextStripper 객체생성 실패");
        }

        stripper.setStartPage(1);

        try {
            stripper.writeText(pdDocument, bufferedWriter);
        } catch (IOException e) {
            throw new RuntimeException("TextExtraction-extractingText ERROR: text 추출 실패");
        }
        try {
            pdDocument.close();
        } catch (IOException e) {
            throw new RuntimeException("TextExtraction-extractingText ERROR: PDDocument close 실패");
        }

        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static FileOutputStream openOutputStream(File file) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (Exception e) {
            System.out.println("TextExtraction-openOutputStream ERROR: " + e.getMessage());

        }
        return fileOutputStream;
    }
}
