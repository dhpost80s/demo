package org.example.word;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class WordParser {

    public static void main(String[] args) {
        getQuestionsV2();
    }

    /**
     * Start with number and end with bracket
     */
    private static void getQuestionsV2() {
        List<String> lines = getLines();
        int i = 1;
        for (String line : lines) {
            if (isStartNumberAndDot(line) && isEndBracket(line)) {
                System.out.println(i + ": " + getText(line) + " ---- " + getPage(line));
                i++;
            }
        }
    }

    /**
     * Find start with number
     */
    private static void getQuestionsV1() {
        List<String> lines = getLines();
        int i = 1;
        for (String line : lines) {
            if (isStartNumberAndDot(line)) {
                System.out.println(i + ": " + line);
                i++;
            }
        }
    }

    /**
     * Find start with number and do not end with bracket
     */
    private static void findInvalid() {
        List<String> lines = getLines();
        int i = 1;
        for (String line : lines) {
            if (isStartNumberAndDot(line) && !isEndBracket(line)) {
                System.out.println(i + ": " + line);
                i++;
            }
        }
    }

    private static List getLines() {
        List<String> lines = new ArrayList<>();
        try {
            // 读取Word文档
            FileInputStream fis = new FileInputStream("./files/zhengzhi.docx");
            XWPFDocument document = new XWPFDocument(fis);

            for (XWPFParagraph para : document.getParagraphs()) {
                String text = para.getText();
                lines.add(text.strip());
            }

            // 关闭文档
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lines;
    }

    private static boolean isStartNumberAndDot(String str) {
        String pattern = "^\\d+\\.[\\u4E00-\\u9FA5\\d\\s\\S]*$";
        return str.matches(pattern);
    }

    private static boolean isEndBracket(String str) {
        return str.endsWith("）") || str.endsWith(")");
    }

    private static String getText(String line) {
        return line.substring(line.indexOf(".") + 1, line.lastIndexOf("（")).strip();
    }

    private static String getPage(String line) {
        return line.substring(line.lastIndexOf("（") + 1, line.lastIndexOf("）")).strip();
    }
}