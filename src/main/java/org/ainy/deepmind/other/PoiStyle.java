package org.ainy.deepmind.other;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author AINY_uan
 * @description POI主题
 * @date 2020-03-15 00:48
 */
public class PoiStyle {

    /**
     * 表头字体样式
     *
     * @param wb 工作簿
     * @return 表头字体样式
     */
    public static XSSFFont createHeadFontStyle(XSSFWorkbook wb) {

        XSSFFont headFontStyle = wb.createFont();
        headFontStyle.setFontName("幼圆");
        headFontStyle.setFontHeightInPoints((short) 22);
        headFontStyle.setBold(true);
        return headFontStyle;

    }

    /**
     * 表头样式（包含字体以及外观）
     *
     * @param wb       工作簿
     * @param headFont 表头字体
     * @return 表头样式（包含字体以及外观）
     */
    public static XSSFCellStyle createHeadCellStyle(XSSFWorkbook wb, XSSFFont headFont) {
        XSSFCellStyle headCellStyle = wb.createCellStyle();
        headCellStyle.setFont(headFont);
        // 左右居中
        headCellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 上下居中
        headCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 左边框的颜色
        headCellStyle.setLeftBorderColor((short) 0);
        // 点边框
        headCellStyle.setBorderLeft(BorderStyle.DOTTED);
        // 右边框的颜色
        headCellStyle.setRightBorderColor((short) 0);
        // 点边框
        headCellStyle.setBorderRight(BorderStyle.DOTTED);
        // 上边框的颜色
        headCellStyle.setTopBorderColor((short) 0);
        // 点边框
        headCellStyle.setBorderTop(BorderStyle.DOTTED);
        // 下边框的颜色
        headCellStyle.setBottomBorderColor((short) 0);
        // 点边框
        headCellStyle.setBorderBottom(BorderStyle.DOTTED);
        headCellStyle.setLocked(true);
        // 自动换行
        headCellStyle.setWrapText(true);
        return headCellStyle;
    }

    /**
     * 列显示字体样式
     *
     * @param wb 工作簿
     * @return 列显示字体样式
     */
    public static XSSFFont createColumnFontStyle(XSSFWorkbook wb) {
        XSSFFont columnFontStyle = wb.createFont();
        columnFontStyle.setFontName("幼圆");
        columnFontStyle.setFontHeightInPoints((short) 14);
        columnFontStyle.setBold(true);
        return columnFontStyle;
    }

    /**
     * 列样式（包含字体以及外观）
     *
     * @param wb         工作簿
     * @param columnFont 列字体
     * @return 列样式（包含字体以及外观）
     */
    public static XSSFCellStyle createColumnCellStyle(XSSFWorkbook wb, XSSFFont columnFont) {
        XSSFCellStyle columnCellStyle = wb.createCellStyle();
        columnCellStyle.setFont(columnFont);
        // 左右居中
        columnCellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 上下居中
        columnCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 左边框的颜色
        columnCellStyle.setLeftBorderColor((short) 0);
        // 点边框
        columnCellStyle.setBorderLeft(BorderStyle.DOTTED);
        // 右边框的颜色
        columnCellStyle.setRightBorderColor((short) 0);
        // 点边框
        columnCellStyle.setBorderRight(BorderStyle.DOTTED);
        // 上边框的颜色
        columnCellStyle.setTopBorderColor((short) 0);
        // 点边框
        columnCellStyle.setBorderTop(BorderStyle.DOTTED);
        // 下边框的颜色
        columnCellStyle.setBottomBorderColor((short) 0);
        // 点边框
        columnCellStyle.setBorderBottom(BorderStyle.DOTTED);
        columnCellStyle.setLocked(true);
        // 自动换行
        columnCellStyle.setWrapText(true);
        return columnCellStyle;
    }

    /**
     * 内容显示字体样式
     *
     * @param wb 工作簿
     * @return 内容显示字体样式
     */
    public static XSSFFont createContentFont(XSSFWorkbook wb) {
        XSSFFont contentFontStyle = wb.createFont();
        contentFontStyle.setFontName("幼圆");
        contentFontStyle.setFontHeightInPoints((short) 14);
        return contentFontStyle;
    }

    /**
     * 内容样式（包含字体以及外观）
     *
     * @param wb          工作簿
     * @param contentFont 被人字体
     * @return 内容样式（包含字体以及外观）
     */
    public static XSSFCellStyle createContentCellStyle(XSSFWorkbook wb, XSSFFont contentFont) {
        XSSFCellStyle contentCellStyle = wb.createCellStyle();
        contentCellStyle.setFont(contentFont);
        // 左右居中
        contentCellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 上下居中
        contentCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 左边框的颜色
        contentCellStyle.setLeftBorderColor((short) 0);
        // 点边框
        contentCellStyle.setBorderLeft(BorderStyle.DOTTED);
        // 右边框的颜色
        contentCellStyle.setRightBorderColor((short) 0);
        // 点边框
        contentCellStyle.setBorderRight(BorderStyle.DOTTED);
        // 上边框的颜色
        contentCellStyle.setTopBorderColor((short) 0);
        // 点边框
        contentCellStyle.setBorderTop(BorderStyle.DOTTED);
        // 下边框的颜色
        contentCellStyle.setBottomBorderColor((short) 0);
        // 点边框
        contentCellStyle.setBorderBottom(BorderStyle.DOTTED);
        contentCellStyle.setLocked(true);
        // 自动换行
        contentCellStyle.setWrapText(true);
        return contentCellStyle;
    }
}
