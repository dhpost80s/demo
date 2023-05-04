package org.example.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class PdfLineExtractor {
    public static void main(String[] args) {
        getQuestions();
//        test();
    }

    private static void getQuestions() {
        String fileName = "D:/study/zhengzhi.pdf";

        try (PDDocument document = PDDocument.load(new File(fileName))) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);

            String[] lines = text.split("\\r?\\n"); // split text into lines
            for (int i = 0; i < lines.length; i++) {
//                System.out.println(lines[i]);
                if (startNumberAndDot(lines[i])) {
                    System.out.println("question: " + lines[i]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static boolean startNumberAndDot(String str) {
        String pattern = "^\\d+\\.[\\u4E00-\\u9FA5\\d\\s\\S]*$";
        return str.matches(pattern);
    }

    private static void test() {
        String str1 = "123.45 aaa";
//        System.out.println(str.matches("\\d+\\.\\d+\\s*.*"));

//        String str1 = "123.45 abc";

//        System.out.println(str1.matches());  // true
    }
}
