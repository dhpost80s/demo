package org.example.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PdfLineExtractor2 {
    public static void main(String[] args) {
        String fileName = "D:/study/zhengzhi.pdf";

        try (PDDocument document = PDDocument.load(new File(fileName))) {
            PDFTextStripper stripper = new PDFTextStripper() {
                private List<TextPosition> line = new ArrayList<>();

                @Override
                protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
                    for (TextPosition textPosition : textPositions) {
                        if (line.isEmpty() || isOnSameLine(textPosition, line.get(line.size() - 1))) {
                            line.add(textPosition);
                        } else {
                            processLine(line);
                            line.clear();
                            line.add(textPosition);
                        }
                    }
                }

                @Override
                protected void writeLineSeparator() throws IOException {
                    processLine(line);
                    line.clear();
                }

                private void processLine(List<TextPosition> line) {
                    if (line.size() > 0) {
                        TextPosition firstChar = line.get(0);
                        if (isBold(firstChar.getFont())) {
                            System.out.println("Line is bold: " + getText(line));
                        } else {
                            System.out.println("Line is not bold: " + getText(line));
                        }
                    }
                }

                private String getText(List<TextPosition> line) {
                    StringBuilder sb = new StringBuilder();
                    for (TextPosition text : line) {
                        sb.append(text.getUnicode());
                    }
                    return sb.toString();
                }

                private boolean isOnSameLine(TextPosition pos1, TextPosition pos2) {
                    return pos1.getTextMatrix().getScaleY() == pos2.getTextMatrix().getScaleY();
                }
            };
            String text = stripper.getText(document);
            System.out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isBold(PDFont font) {
        // Check if font is bold
        return font.getFontDescriptor().getFontWeight() > Font.BOLD;
    }
}

