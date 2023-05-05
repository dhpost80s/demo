package org.example;

import java.io.File;

public class ListDirectory {
    public static void main(String[] args) {
        // 指定目录的路径
        String directoryPath = "./";

        // 创建File对象
        File directory = new File(directoryPath);

        // 检查目录是否存在
        if (!directory.exists()) {
            System.out.println("目录不存在");
            return;
        }

        // 列出目录下的文件和子目录
        File[] files = directory.listFiles();
        for (File file : files) {
            // 如果是文件，则打印文件的绝对路径
            System.out.println("文件：" + file.getAbsolutePath());

        }
    }
}