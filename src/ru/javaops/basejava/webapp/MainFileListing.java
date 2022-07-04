package ru.javaops.basejava.webapp;

import java.io.File;
import java.io.IOException;

public class MainFileListing {
    private static final StringBuilder OFFSET = new StringBuilder();
    private static final String CUR_LEVEL = "\u251D "; //Box Drawings Vertical Light and Right Heavy
    private static final String SUB_LEVEL = "|  ";

    public static void main(String[] args) {
//        final File dir = new File("./src/ru/javaops/basejava");
        final File dir = new File("./config");
        try {
            System.out.println("Files listing for the directory: " + dir.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        OFFSET.append(CUR_LEVEL);
        printSubDirs(dir);
    }

    private static void printSubDirs(File dir) {
        if (dir.isDirectory()) {
            final String[] dirList = dir.list();
            if (dirList != null) {
                for (String fileName : dirList) {
                    final File file = new File(dir.getPath() + "/" + fileName);
                    if (file.isDirectory()) {
                        System.out.println(OFFSET + fileName + " - DIRECTORY");
                        OFFSET.insert(0, SUB_LEVEL);
                        printSubDirs(file);
                    } else System.out.println(OFFSET + fileName + " - FILE");
                }
                //reduce offset by one level
                final int length = OFFSET.length();
                if (length > 3) OFFSET.replace(length - CUR_LEVEL.length() - SUB_LEVEL.length(),
                        length, CUR_LEVEL);
            }
        }
    }
}
