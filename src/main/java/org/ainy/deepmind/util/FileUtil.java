package org.ainy.deepmind.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @author 阿拉丁省油的灯
 * @description 文件操作类
 * @date 2018-07-12 15:45
 */
@Slf4j
public class FileUtil {

    /**
     * 删除文件夹
     *
     * @param folderPath 源文件夹路径
     */
    private static void delFolder(String folderPath) {

        try {
            // 删除完里面所有内容
            delAllFile(folderPath);
            File myFilePath = new File(folderPath);
            // 删除空文件夹
            boolean flag = myFilePath.delete();
            if (!flag) {
                log.error("删除文件夹失败");
            }
        } catch (Exception e) {
            throw new RuntimeException("删除文件夹操作出错.");
        }
    }

    /**
     * 删除一个文件夹下的所有文件 如删除名叫Test的文件夹下的所有文件，但不删除Test文件夹
     *
     * @param folderPath 源文件夹路径
     */
    private static void delAllFile(String folderPath) {

        File file = new File(folderPath);
        if (!file.exists()) {
            try {
                throw new FileNotFoundException("源文件夹未找到, 删除文件夹失败.");
            } catch (FileNotFoundException e) {
                log.error("[ERROR]", e);
            }
        }
        if (!file.isDirectory()) {
            try {
                throw new RuntimeException("选择的文件不是目录, 删除文件夹失败.");
            } catch (Exception e) {
                log.error("", e);
            }

        }
        String[] tempList = file.list();
        File temp;
        for (String s : tempList) {
            if (folderPath.endsWith(File.separator)) {
                temp = new File(folderPath + s);
            } else {
                temp = new File(folderPath + File.separator + s);
            }
            if (temp.isFile()) {
                boolean flag = temp.delete();
                if (!flag) {
                    log.error("文件删除失败, 文件路径: " + temp.getAbsolutePath());
                }
            }
            if (temp.isDirectory()) {
                // 先删除文件夹里面的文件
                delAllFile(folderPath + File.separator + s);
                // 再删除空文件夹
                delFolder(folderPath + File.separator + s);
            }
        }
    }

    /**
     * 拷贝一个文件
     *
     * @param srcFile  源文件（全文件名）
     * @param savePath 保存文件路径
     * @return true: 拷贝成功; other: 拷贝失败
     */
    public static boolean copyFile(String srcFile, String savePath) {

        File file = new File(srcFile);
        if (!file.exists()) {
            log.error("源文件未找到，拷贝失败！");
            return false;
        }
        if (!file.isFile()) {
            log.error("源文件不是一个文件，拷贝失败！");
            return false;
        }
        File saveFloder = new File(savePath);
        if (!saveFloder.exists()) {
            boolean flag = saveFloder.mkdirs();
            if (!flag) {
                log.error("创建保存文件夹失败，拷贝失败！");
                return false;
            }
        }

        try {
            FileInputStream fis = new FileInputStream(srcFile);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fis);

            FileOutputStream fos = new FileOutputStream(saveFloder + File.separator + file.getName());
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fos);
            int len;
            while ((len = bufferedInputStream.read()) != -1) {
                bufferedOutputStream.write(len);
            }
            bufferedOutputStream.close();
            bufferedInputStream.close();
            return true;
        } catch (IOException e) {
            log.error("[ERROR]", e);
            return false;
        }
    }

    public static String[] findFile(String path, String keyword) {

        File dir = new File(path);
        FilenameFilter filter = (dir1, name) -> name.endsWith(keyword);
        String[] children = dir.list(filter);
        return children;
    }
}
