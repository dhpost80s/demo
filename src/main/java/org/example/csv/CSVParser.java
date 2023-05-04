package org.example.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {

    private static final String CSV_FILE = "D:\\Downloads\\missing\\1.csv";

    private static void printProcedures() {
        String line;
        String csvSplitBy = ",";
        boolean firstLine = true; // 标记是否是第一行

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // 跳过第一行
                }
                String[] values = line.split(csvSplitBy);
                System.out.println("CALL HOMER_SYNC.PKG_HOMER_SYNC.create_contract_writeoff_msg('" + values[2] + "', " + values[1] + ", 1);");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void printContracts() {
        String line;
        String csvSplitBy = ",";
        boolean firstLine = true; // 标记是否是第一行

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // 跳过第一行
                }
                String[] values = line.split(csvSplitBy);
                System.out.println(values[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testDuplicate() {
        String line;
        String csvSplitBy = ",";
        boolean firstLine = true; // 标记是否是第一行

        List<String> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // 跳过第一行
                }
                String[] values = line.split(csvSplitBy);

                if (list.contains(values[1])) {
                    System.out.println("Duplicate!!!");
                }
                list.add(values[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        testDuplicate();
    }
}