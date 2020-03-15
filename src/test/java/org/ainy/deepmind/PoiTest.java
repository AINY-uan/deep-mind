package org.ainy.deepmind;

import org.ainy.deepmind.other.PoiStyle;
import org.ainy.deepmind.util.PoiUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PoiTest {

    @Test
    public void ex1() {

        // 声明一个工作簿
        XSSFWorkbook wb = new XSSFWorkbook();
        // 声明一个单子并命名
        XSSFSheet sheet = wb.createSheet("个人资料");

        // 表头字体样式
        XSSFFont headFontStyl = PoiStyle.createHeadFontStyle(wb);

        // 表头外观样式
        XSSFCellStyle headStyle = PoiStyle.createHeadCellStyle(wb, headFontStyl);

        // 填充色，必须配合setFillForegroundColor使用
        headStyle.setFillPattern(FillPatternType.NO_FILL);
        // 填充色
        headStyle.setFillForegroundColor((short) 9);

        // 列显示字体样式
        XSSFFont columnFontStyl = wb.createFont();

        // 列外观样式
        XSSFCellStyle columnCellStyle = PoiStyle.createColumnCellStyle(wb, columnFontStyl);

        // 填充色，必须配合setFillForegroundColor使用
        columnCellStyle.setFillPattern(FillPatternType.NO_FILL);
        // 填充色
        columnCellStyle.setFillForegroundColor((short) 10);

        // 内容字体样式
        XSSFFont contentFontStyl = PoiStyle.createContentFont(wb);

        // 内容外观样式
        XSSFCellStyle contentCellStyle = PoiStyle.createContentCellStyle(wb, contentFontStyl);

        // 填充色，必须配合setFillForegroundColor使用
        contentCellStyle.setFillPattern(FillPatternType.NO_FILL);
        // 填充色
        contentCellStyle.setFillForegroundColor((short) 11);

        // 参数的意思:起始行号，终止行号， 起始列号，终止列号
        // 合并单元格
        CellRangeAddress range0 = new CellRangeAddress(0, 0, 0, 13);

        CellRangeAddress range1_1 = new CellRangeAddress(1, 1, 0, 2);
        CellRangeAddress range1_2 = new CellRangeAddress(1, 1, 3, 6);
        CellRangeAddress range1_3 = new CellRangeAddress(1, 1, 7, 9);
        CellRangeAddress range1_4 = new CellRangeAddress(1, 1, 10, 13);

        CellRangeAddress range2_1 = new CellRangeAddress(2, 2, 0, 3);
        CellRangeAddress range2_2 = new CellRangeAddress(2, 2, 4, 13);

        CellRangeAddress range3_1 = new CellRangeAddress(3, 3, 0, 3);
        CellRangeAddress range3_2 = new CellRangeAddress(3, 3, 4, 13);

        CellRangeAddress range4_1 = new CellRangeAddress(4, 4, 0, 2);
        CellRangeAddress range4_2 = new CellRangeAddress(4, 4, 3, 6);
        CellRangeAddress range4_3 = new CellRangeAddress(4, 4, 7, 9);
        CellRangeAddress range4_4 = new CellRangeAddress(4, 4, 10, 13);

        CellRangeAddress range5_1 = new CellRangeAddress(5, 5, 0, 3);
        CellRangeAddress range5_2 = new CellRangeAddress(5, 5, 4, 13);

        CellRangeAddress range6_1 = new CellRangeAddress(6, 6, 0, 3);
        CellRangeAddress range6_2 = new CellRangeAddress(6, 6, 4, 13);

        CellRangeAddress range7_1 = new CellRangeAddress(7, 7, 0, 2);
        CellRangeAddress range7_2 = new CellRangeAddress(7, 7, 3, 6);
        CellRangeAddress range7_3 = new CellRangeAddress(7, 7, 7, 9);
        CellRangeAddress range7_4 = new CellRangeAddress(7, 7, 10, 13);

        CellRangeAddress range8_1 = new CellRangeAddress(8, 8, 0, 2);
        CellRangeAddress range8_2 = new CellRangeAddress(8, 8, 3, 6);
        CellRangeAddress range8_3 = new CellRangeAddress(8, 8, 7, 9);
        CellRangeAddress range8_4 = new CellRangeAddress(8, 8, 10, 13);

        CellRangeAddress range9_1 = new CellRangeAddress(9, 9, 0, 2);
        CellRangeAddress range9_2 = new CellRangeAddress(9, 9, 3, 6);
        CellRangeAddress range9_3 = new CellRangeAddress(9, 9, 7, 9);
        CellRangeAddress range9_4 = new CellRangeAddress(9, 9, 10, 13);

        CellRangeAddress range10_1 = new CellRangeAddress(10, 10, 0, 2);
        CellRangeAddress range10_2 = new CellRangeAddress(10, 10, 3, 6);
        CellRangeAddress range10_3 = new CellRangeAddress(10, 10, 7, 9);
        CellRangeAddress range10_4 = new CellRangeAddress(10, 10, 10, 13);

        CellRangeAddress range11_1 = new CellRangeAddress(11, 11, 0, 2);
        CellRangeAddress range11_2 = new CellRangeAddress(11, 11, 3, 6);
        CellRangeAddress range11_3 = new CellRangeAddress(11, 11, 7, 9);
        CellRangeAddress range11_4 = new CellRangeAddress(11, 11, 10, 13);

        sheet.addMergedRegion(range0);
        sheet.addMergedRegion(range1_1);
        sheet.addMergedRegion(range1_2);
        sheet.addMergedRegion(range1_3);
        sheet.addMergedRegion(range1_4);
        sheet.addMergedRegion(range2_1);
        sheet.addMergedRegion(range2_2);
        sheet.addMergedRegion(range3_1);
        sheet.addMergedRegion(range3_2);
        sheet.addMergedRegion(range4_1);
        sheet.addMergedRegion(range4_2);
        sheet.addMergedRegion(range4_3);
        sheet.addMergedRegion(range4_4);
        sheet.addMergedRegion(range5_1);
        sheet.addMergedRegion(range5_2);
        sheet.addMergedRegion(range6_1);
        sheet.addMergedRegion(range6_2);
        sheet.addMergedRegion(range7_1);
        sheet.addMergedRegion(range7_2);
        sheet.addMergedRegion(range7_3);
        sheet.addMergedRegion(range7_4);
        sheet.addMergedRegion(range8_1);
        sheet.addMergedRegion(range8_2);
        sheet.addMergedRegion(range8_3);
        sheet.addMergedRegion(range8_4);
        sheet.addMergedRegion(range9_1);
        sheet.addMergedRegion(range9_2);
        sheet.addMergedRegion(range9_3);
        sheet.addMergedRegion(range9_4);
        sheet.addMergedRegion(range10_1);
        sheet.addMergedRegion(range10_2);
        sheet.addMergedRegion(range10_3);
        sheet.addMergedRegion(range10_4);
        sheet.addMergedRegion(range11_1);
        sheet.addMergedRegion(range11_2);
        sheet.addMergedRegion(range11_3);
        sheet.addMergedRegion(range11_4);

        // 各列的宽度
        sheet.setColumnWidth(0, 2000);
        sheet.setColumnWidth(1, 2000);
        sheet.setColumnWidth(2, 2000);
        sheet.setColumnWidth(3, 2000);
        sheet.setColumnWidth(4, 2000);
        sheet.setColumnWidth(5, 2000);
        sheet.setColumnWidth(6, 2000);
        sheet.setColumnWidth(7, 2000);
        sheet.setColumnWidth(8, 2000);
        sheet.setColumnWidth(9, 2000);
        sheet.setColumnWidth(10, 2000);
        sheet.setColumnWidth(11, 2000);
        sheet.setColumnWidth(12, 2000);
        sheet.setColumnWidth(13, 2000);
        sheet.setColumnWidth(14, 2000);

        // 创建第一行
        Row row0 = sheet.createRow(0);
        // 设置第一行高度
        row0.setHeight((short) 720);
        // 创建第一列
        Cell cell0 = row0.createCell(0);
        // 设置第一行第一列内容，因为第一行合并了单元格，所以第一列的值就是整个一行的值
        cell0.setCellValue("个人资料");
        // 设置第一列样式
        cell0.setCellStyle(headStyle);

        Row row1 = sheet.createRow(1);
        row1.setHeight((short) 360);

        Cell cell1_1 = row1.createCell(0);
        Cell cell1_2 = row1.createCell(1);
        Cell cell1_3 = row1.createCell(2);
        cell1_1.setCellValue("姓名");
        cell1_2.setCellValue("");
        cell1_3.setCellValue("");
        cell1_1.setCellStyle(columnCellStyle);
        cell1_2.setCellStyle(columnCellStyle);
        cell1_3.setCellStyle(columnCellStyle);

        Cell cell2_1 = row1.createCell(3);
        Cell cell2_2 = row1.createCell(4);
        Cell cell2_3 = row1.createCell(5);
        Cell cell2_4 = row1.createCell(6);
        cell2_1.setCellValue("袁东");
        cell2_2.setCellValue("");
        cell2_3.setCellValue("");
        cell2_4.setCellValue("");
        cell2_1.setCellStyle(contentCellStyle);
        cell2_2.setCellStyle(contentCellStyle);
        cell2_3.setCellStyle(contentCellStyle);
        cell2_4.setCellStyle(contentCellStyle);

        Cell cell3_1 = row1.createCell(7);
        Cell cell3_2 = row1.createCell(8);
        Cell cell3_3 = row1.createCell(9);
        cell3_1.setCellValue("电话");
        cell3_2.setCellValue("");
        cell3_3.setCellValue("");
        cell3_1.setCellStyle(columnCellStyle);
        cell3_2.setCellStyle(columnCellStyle);
        cell3_3.setCellStyle(columnCellStyle);

        Cell cell4_1 = row1.createCell(10);
        Cell cell4_2 = row1.createCell(11);
        Cell cell4_3 = row1.createCell(12);
        Cell cell4_4 = row1.createCell(13);
        cell4_1.setCellValue("15679527698");
        cell4_2.setCellValue("");
        cell4_3.setCellValue("");
        cell4_4.setCellValue("");
        cell4_1.setCellStyle(contentCellStyle);
        cell4_2.setCellStyle(contentCellStyle);
        cell4_3.setCellStyle(contentCellStyle);
        cell4_4.setCellStyle(contentCellStyle);

        try {
            FileOutputStream out = new FileOutputStream("E://个人资料.xls");
            wb.write(out);
            out.close();
            JOptionPane.showMessageDialog(null, "导出成功!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "导出失败!");
            e.printStackTrace();
        }
    }

    /**
     * 将数据写入CSV文件
     */
    @Test
    public void ex2() {

        List<String> rowData = new ArrayList<>(16);

        rowData.add("name");
        rowData.add("tony");
        rowData.add("age");
        rowData.add("25");

        List<List<String>> allData = new ArrayList<>(16);

        allData.add(rowData);

        PoiUtil.writeCsvFile(allData, "e:/", String.valueOf(System.currentTimeMillis()));
    }

    /**
     * 将数据写入EXCEL文件
     */
    @Test
    public void ex3() {

        List<String> rowData = new ArrayList<>(16);

        rowData.add("name");
        rowData.add("tony");
        rowData.add("age");
        rowData.add("25");

        List<List<String>> sheetData1 = new ArrayList<>(16);

        sheetData1.add(rowData);

        List<String> rowData2 = new ArrayList<>(16);

        rowData2.add("name");
        rowData2.add("tony");
        rowData2.add("age");
        rowData2.add("26");

        List<List<String>> sheetData2 = new ArrayList<>(16);

        sheetData2.add(rowData2);

        List<List<List<String>>> allData = new ArrayList<>(16);

        allData.add(sheetData1);
        allData.add(sheetData2);

        PoiUtil.writeExcelFile(allData, "e:/", String.valueOf(System.currentTimeMillis()));
    }

    /**
     * 读取CSV文件，仅支持单页的CSV文件
     */
    @Test
    public void ex4() {

        System.out.println(PoiUtil.readCsvFile("e:/1584270314855.csv"));
    }

    /**
     *
     */
    @Test
    public void ex5() throws Exception {

        System.out.println(PoiUtil.readExcelFile("e:/1584270434230.xlsx"));

        System.out.println(Arrays.deepToString(PoiUtil.readExcelFile("e:/1584270434230.xlsx", 1)));
    }
}
