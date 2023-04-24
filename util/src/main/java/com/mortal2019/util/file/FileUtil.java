package com.mortal2019.util.file;

import java.io.File;

/**
 * 文件工具类
 *
 * @author wuyiyuan
 * @version v1.0.0.0
 * Created DateTime 2023/4/23 16:16
 */
public class FileUtil {

    public static String getExt(String fileName) {
        String name = getFullFileName(fileName);
        int index = separatorIndex(name);
        return (index + 1) >= name.length() ? "" : name.substring(index + 1);
    }

    private static int separatorIndex(String fileName) {
        return fileName.lastIndexOf(".");
    }

    /**
     * 完整文件名获取（不带目录）
     *
     * @param fileName 文件名
     * @return java.lang.String
     */
    public static String getFullFileName(String fileName) {
        return new File(fileName).getName();
    }

    public static String getFileName(String fileName) {
        String name = getFullFileName(fileName);
        int index = separatorIndex(name);
        return index < 1 ? name : name.substring(0, index);
    }
}
