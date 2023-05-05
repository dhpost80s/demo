package org.example.pdf;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class PdfLineExtractor {

    private static String firstLine;

    public static void main(String[] args) {
        getQuestionsV1();
//        test();
    }

    private static void getQuestionsV2() {
        String fileName = "D:/study/zhengzhi.pdf";

        try (PDDocument document = PDDocument.load(new File(fileName))) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);

            String[] lines = text.split("\\r?\\n"); // split text into lines
            int i = 1;
            for (String line: lines) {

//                System.out.println(lines[i]);

                if (StringUtils.isNotBlank(firstLine)) {
                    System.out.println(i + ": " + firstLine + line);
                    i++;
                    firstLine = null;
                    continue;
                }

                if (isStartNumberAndDot(line) && isEndBracket(line)) {
                    System.out.println(i + ": " + line);
                    i++;
                } else if (isStartNumberAndDot(line) && !isEndBracket(line)) {
                    firstLine = line;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void getQuestionsV1() {
        String fileName = "D:/study/zhengzhi.pdf";

        try (PDDocument document = PDDocument.load(new File(fileName))) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);

            String[] lines = text.split("\\r?\\n"); // split text into lines
            int i = 1;
            for (String line : lines) {
                if (isStartNumberAndDot(line)) {
                    System.out.println(i + ": " + line);
                    i++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static boolean isStartNumberAndDot(String str) {
        String pattern = "^\\d+\\.[\\u4E00-\\u9FA5\\d\\s\\S]*$";
        return str.matches(pattern);
    }

    private static boolean isEndBracket(String str) {
        return str.endsWith("）");
    }

}
