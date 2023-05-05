package org.example.word;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PDFToWord {
    public static void main(String[] args) {
        try (PDDocument pdf = PDDocument.load(new File("D:/study/zhengzhi.pdf"));
             XWPFDocument doc = new XWPFDocument()) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(pdf);
            for (String line : text.split("\\r?\\n")) {
                XWPFParagraph para = doc.createParagraph();
                XWPFRun run = para.createRun();
                run.setText(line);
            }
            doc.write(new FileOutputStream("D:/study/example.docx"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
