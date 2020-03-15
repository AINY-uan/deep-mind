package org.ainy.deepmind.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.*;

/**
 * @author 阿拉丁省油的灯
 * @description zip压缩文件工具类
 * @date 2019-05-21 10:12
 */
@Slf4j
public class CompressUtil {

    private static final int BUFFER = 8192;

    /**
     * 压缩文件
     *
     * @param srcPath 源文件（文件夹）路径
     * @param dstPath 压缩包存放路径
     * @throws IOException IOException
     */
    public static void compress(String srcPath, String dstPath) throws IOException {

        File srcFile = new File(srcPath);
        File dstFile = new File(dstPath);
        if (!srcFile.exists()) {
            throw new FileNotFoundException(srcPath + "不存在！");
        }

        FileOutputStream out = null;
        ZipOutputStream zipOut = null;
        try {
            out = new FileOutputStream(dstFile);
            CheckedOutputStream cos = new CheckedOutputStream(out, new CRC32());
            zipOut = new ZipOutputStream(cos);
            String baseDir = "";
            compress(srcFile, zipOut, baseDir);
        } finally {
            if (null != zipOut) {
                zipOut.close();
                out = null;
            }

            if (null != out) {
                out.close();
            }
        }
    }

    private static void compress(File file, ZipOutputStream zipOut, String baseDir) throws IOException {

        if (file.isDirectory()) {
            compressDirectory(file, zipOut, baseDir);
        } else {
            compressFile(file, zipOut, baseDir);
        }
    }

    /**
     * 压缩一个目录
     */
    private static void compressDirectory(File dir, ZipOutputStream zipOut, String baseDir) throws IOException {

        File[] files = dir.listFiles();
        for (File file : files) {
            compress(file, zipOut, baseDir + dir.getName() + "/");
        }
    }

    /**
     * 压缩一个文件
     */
    private static void compressFile(File file, ZipOutputStream zipOut, String baseDir) throws IOException {

        if (!file.exists()) {
            return;
        }

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            ZipEntry entry = new ZipEntry(baseDir + file.getName());
            zipOut.putNextEntry(entry);
            int count;
            byte[] data = new byte[BUFFER];
            while ((count = bis.read(data, 0, BUFFER)) != -1) {
                zipOut.write(data, 0, count);
            }

        }
    }

    /**
     * 解压缩文件
     *
     * @param zipFile           压缩包文件名（绝对路径）
     * @param decompressionPath 解压路径
     * @throws IOException IO异常
     */
    public static void decompress(String zipFile, String decompressionPath) throws IOException {

        // 解压文件夹
        File decompressionDir = new File(decompressionPath);
        if (!decompressionDir.exists()) {
            boolean flag = decompressionDir.mkdirs();
            if (flag) {
                ZipFile zip = new ZipFile(zipFile);
                for (Enumeration<?> entries = zip.entries(); entries.hasMoreElements(); ) {
                    ZipEntry entry = (ZipEntry) entries.nextElement();
                    String zipEntryName = entry.getName();
                    InputStream in = null;
                    OutputStream out = null;
                    try {
                        in = zip.getInputStream(entry);
                        String outPath = (decompressionPath + "/" + zipEntryName).replaceAll("\\*", "/");

                        //判断路径是否存在,不存在则创建文件路径
                        File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        //判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
                        if (new File(outPath).isDirectory()) {
                            continue;
                        }

                        out = new FileOutputStream(outPath);
                        byte[] buf1 = new byte[1024];
                        int len;
                        while ((len = in.read(buf1)) > 0) {
                            out.write(buf1, 0, len);
                        }
                    } finally {
                        if (null != in) {
                            in.close();
                        }

                        if (null != out) {
                            out.close();
                        }
                    }
                }
            } else {
                log.error("解压文件夹创建失败");
            }
        }
    }
}