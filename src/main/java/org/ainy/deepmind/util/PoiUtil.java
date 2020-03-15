package org.ainy.deepmind.util;

import au.com.bytecode.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author 阿拉丁省油的灯
 * @date 2018-12-12 12:12
 * @description Office操作类工具类
 */
@Slf4j
public class PoiUtil {

    private static final String XLS = "xls";
    private static final String XLSX = "xlsx";

    /**
     * 将数据写入CSV文件
     *
     * @param data     待写入的数据
     * @param path     文件路径
     * @param fileName 文件名
     */
    public static void writeCsvFile(List<List<String>> data, String path, String fileName) {

        try {
            File outputFile = new File(path + fileName + ".csv");
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8));
            for (List<String> rowData : data) {
                for (String cellData : rowData) {
                    out.write(delQuota(cellData));
                    out.write(",");
                }
                out.newLine();
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            log.error("[ERROR]", e);
        }
    }

    /**
     * 将数据写入EXCEL文件
     *
     * @param data     待写入的数据
     * @param filePath 文件路径
     * @param fileName 文件名
     */
    public static void writeExcelFile(List<List<List<String>>> data, String filePath, String fileName) {

        try {

            Workbook wb = new XSSFWorkbook();

            for (int i = 0; i < data.size(); i++) {
                // 创建sheet页
                Sheet sheet = wb.createSheet("第" + i + "页");
                // 一页的行数
                for (int j = 0; j < data.get(i).size(); j++) {
                    // 创建行
                    Row row = sheet.createRow(j);
                    // 一行的列数
                    for (int k = 0; k < data.get(i).get(j).size(); k++) {
                        // 创建列
                        Cell cell = row.createCell(k);
                        // 写入数据
                        cell.setCellValue(data.get(i).get(j).get(k));
                    }
                }
            }

            FileOutputStream fos = new FileOutputStream(filePath + fileName + ".xlsx");

            wb.write(fos);
            wb.close();

        } catch (IOException e) {
            log.error("[ERROR]", e);
        }
    }

    /**
     * 读取CSV文件，仅支持单页的CSV文件
     *
     * @param filePath 文件路径
     * @return 返回一个整个sheet页的数据
     */
    public static List<List<String>> readCsvFile(String filePath) {

        File csvFile = new File(filePath);

        if (!csvFile.exists()) {
            log.error(filePath + " is not exist.");
            return null;
        }

        if (!csvFile.isFile()) {
            log.error(filePath + " is not a file.");
            return null;
        }

        InputStreamReader ins = null;
        CSVReader reader = null;
        int rowSize = 0;
        try {
            ins = new InputStreamReader(new FileInputStream(filePath), "GBK");
            reader = new CSVReader(ins);
            String[] nextRow;
            List<String> rowList;
            List<List<String>> sheetList = new ArrayList<>();

            while ((nextRow = reader.readNext()) != null) {

                if (nextRow.length <= 0) {
                    continue;
                }
                int tempRowSize = nextRow.length;
                if (tempRowSize > rowSize) {
                    rowSize = tempRowSize;
                }
                rowList = new ArrayList<>(Arrays.asList(nextRow));
                List<String> newRowList = new ArrayList<>();
                for (String s : rowList) {
                    newRowList.add(s.replaceAll("[\\t\\n\\r]", ""));
                }
                sheetList.add(newRowList);

            }
            reader.close();
            ins.close();
            return sheetList;
        } catch (IOException e) {
            log.error("[ERROE]", e);
        } finally {
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException e) {
                    log.error("[ERROE]", e);
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error("[ERROE]", e);
                }
            }
        }
        return null;
    }

    /**
     * 读取CSV格式文件，返回二维字符串数组
     * 一维数组存储的是多少行，二维数组存储的每一行是多少列
     *
     * @param filePath   文件完整路径
     * @param ignoreRows 读取数据忽略的行数
     * @return 返回二维字符串数组
     * @throws IOException IOException
     */
    private static String[][] readCsvFile(String filePath, int ignoreRows) throws IOException {

        /* 验证文件是否存在 */
        if (validateFileExist(filePath)) {
            throw new IOException(filePath + "文件不存在");
        }

        /* 验证文件是否合法 */
        if (!isCsv(filePath)) {
            throw new RuntimeException("不是CSV格式的文件");
        }

        InputStreamReader inputStream = null;
        CSVReader reader = null;
        List<String[]> result = new ArrayList<>();
        int rowSize = 0;
        try {
            inputStream = new InputStreamReader(new FileInputStream(filePath), "GBK");
            reader = new CSVReader(inputStream);
            String[] nextRow;
            int i = 0;
            while ((nextRow = reader.readNext()) != null) {
                ++i;
                if (i <= ignoreRows) {
                    continue;
                }
                if (nextRow.length <= 0) {
                    continue;
                }
                int tempRowSize = nextRow.length;
                if (tempRowSize > rowSize) {
                    rowSize = tempRowSize;
                }
                result.add(nextRow);
            }

            reader.close();
            inputStream.close();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String[][] returnArray = new String[result.size()][rowSize];
        for (int i = 0; i < returnArray.length; i++) {
            returnArray[i] = result.get(i);
        }
        return returnArray;
    }

    /**
     * 读取Excel的内容
     * 一维数组存储的是多少行，二维数组存储的每一行是多少列
     * 兼容xls和xlsx文件，同时还支持读取CSV格式的文件
     *
     * @param filePath   文件完整路径
     * @param ignoreRows 读取数据忽略的行数
     * @return 返回一个二维字符串数组
     * @throws IOException IOException
     */
    @SuppressWarnings("deprecation")
    public static String[][] readExcelFile(String filePath, int ignoreRows) throws IOException {

        /*
         * 验证文件是否存在
         */
        if (validateFileExist(filePath)) {
            throw new IOException(filePath + "文件不存在");
        }

        /*
         * 如果是CSV格式调用ImportCsvFile方法，直接返回结果
         */
        if (isCsv(filePath)) {
            return readCsvFile(filePath, ignoreRows);
        }

        /*
         * 验证文件是否合法
         */
        if (!validateExcel(filePath)) {
            throw new RuntimeException("不是Excel格式的文件");
        }

        // 对应的文件数据
        List<String[]> result = new ArrayList<>();
        Workbook workbook = null;
        InputStream inputStream = null;
        int rowSize = 0;

        try {

            File file = new File(filePath);
            inputStream = new FileInputStream(file);

            if (isXls(filePath)) {
                workbook = new HSSFWorkbook(inputStream);
            } else {
                workbook = new XSSFWorkbook(inputStream);
            }

            // 遍历sheet页
            for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
                // 对应的sheet页
                Sheet sheet = workbook.getSheetAt(sheetIndex);
                for (int rowIndex = ignoreRows; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                    Row row = sheet.getRow(rowIndex);
                    if (row == null) {
                        continue;
                    }
                    int tempRowSize = row.getLastCellNum() + 1;
                    if (tempRowSize > rowSize) {
                        rowSize = tempRowSize;
                    }
                    String[] values = new String[rowSize];
                    Arrays.fill(values, "");
                    boolean hasValue = false;
                    for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
                        String value = "";
                        Cell cell = row.getCell(columnIndex);
                        if (cell != null) {
                            switch (cell.getCellType()) {
                                case HSSFCell.CELL_TYPE_STRING:
                                    value = cell.getStringCellValue();
                                    break;
                                case HSSFCell.CELL_TYPE_NUMERIC:
                                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                        Date date = cell.getDateCellValue();
                                        if (date != null) {
                                            value = new SimpleDateFormat("yyyy-MM-dd").format(date);
                                        } else {
                                            value = "";
                                        }
                                    } else {
                                        value = new DecimalFormat("0").format(cell.getNumericCellValue());
                                    }
                                    break;
                                case HSSFCell.CELL_TYPE_FORMULA:
                                    // 导入时如果为公式生成的数据则无值
                                    if (!"".equals(cell.getStringCellValue())) {
                                        value = cell.getStringCellValue();
                                    } else {
                                        value = cell.getNumericCellValue() + "";
                                    }
                                    break;
                                case HSSFCell.CELL_TYPE_BLANK:
                                    break;
                                case HSSFCell.CELL_TYPE_BOOLEAN:
                                    value = (cell.getBooleanCellValue() ? "Y" : "N");
                                    break;
                                default:
                                    value = "";
                            }
                        }
                        if (columnIndex == 0 && "".equals(value.trim())) {
                            break;
                        }
                        values[columnIndex] = rightTrim(value);
                        hasValue = true;
                    }

                    if (hasValue) {
                        result.add(values);
                    }
                }
            }

            inputStream.close();
            workbook.close();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        String[][] returnArray = new String[result.size()][rowSize];
        for (int i = 0; i < returnArray.length; i++) {
            returnArray[i] = result.get(i);
        }
        return returnArray;
    }

    /**
     * 读取excel文件，支持多页的excel文件
     *
     * @param filePath 文件路径
     * @return List<List < List < String>>>
     * @throws Exception Exception
     */
    public static List<List<List<String>>> readExcelFile(String filePath) throws Exception {

        File f = new File(filePath);
        if (!f.exists()) {
            throw new FileNotFoundException("No file named " + filePath + " was found");
        }

        String fileType = filePath.substring(filePath.lastIndexOf(".") + 1);
        InputStream is = null;
        Workbook wb = null;
        try {
            is = new FileInputStream(filePath);

            if (XLS.equals(fileType)) {
                wb = new XSSFWorkbook(is);
            } else if (XLSX.equals(fileType)) {
                wb = new XSSFWorkbook(is);
            } else {
                throw new Exception("File type is wrong");
            }

            // 对应excel文件
            List<List<List<String>>> result = new ArrayList<>();

            int sheetSize = wb.getNumberOfSheets();
            // 遍历sheet页
            for (int i = 0; i < sheetSize; i++) {
                Sheet sheet = wb.getSheetAt(i);
                // 对应sheet页
                List<List<String>> sheetList = new ArrayList<>();

                int rowSize = sheet.getLastRowNum() + 1;
                // 遍历行
                for (int j = 0; j < rowSize; j++) {
                    Row row = sheet.getRow(j);
                    // 略过空行
                    if (row == null) {
                        continue;
                    }
                    // 行中有多少个单元格，也就是有多少列
                    int cellSize = row.getLastCellNum();
                    // 对应一个数据行
                    List<String> rowList = new ArrayList<>();
                    for (int k = 0; k < cellSize; k++) {
                        Cell cell = row.getCell(k);
                        String value = null;
                        if (cell != null) {
                            value = cell.toString();
                        }
                        rowList.add(value);
                    }
                    sheetList.add(rowList);
                }
                result.add(sheetList);
            }
            return result;
        } finally {
            if (wb != null) {
                wb.close();
            }
            if (is != null) {
                is.close();
            }
        }
    }

    /**
     * 去除无用字符
     *
     * @param str 需要处理的字符串
     * @return 去除无用字符后的字符串
     */
    private static String delQuota(String str) {
        String result = str;
        String[] strQuota = {"~", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "`", ";", "'", ",", ".", "/", ":",
                "/,", "<", ">", "?"};
        for (String s : strQuota) {
            if (result.contains(s)) {
                result = result.replace(s, "");
            }
        }
        return result;
    }

    /**
     * 去掉字符串右边的空格
     *
     * @param str 要处理的字符串
     * @return 处理后的字符串
     */
    private static String rightTrim(String str) {
        if (str == null) {
            return "";
        }
        int length = str.length();
        for (int i = length - 1; i >= 0; i--) {
            if (str.charAt(i) != 0x20) {
                break;
            }
            length--;
        }
        return str.substring(0, length);
    }

    /**
     * 验证excel文件
     *
     * @param filePath 文件完整路径
     * @return boolean
     */
    private static boolean validateExcel(String filePath) {
        /* 检查文件名是否为空或者是否是Excel格式的文件 */
        return filePath != null && (isXls(filePath) || isXlsx(filePath));
    }

    /**
     * 检查文件是否存在
     *
     * @param filePath 文件路径
     * @return true: 文件不存在
     */
    private static boolean validateFileExist(String filePath) {

        File file = new File(filePath);
        return !file.exists();
    }

    /**
     * 判断是否是CSV格式文件
     *
     * @param filePath 文件完整路径
     * @return boolean
     */
    private static boolean isCsv(String filePath) {
        return filePath.matches("^.+\\.(?i)(csv)$");
    }

    /**
     * 判断是否是xls文件
     *
     * @param filePath 文件完整路径
     * @return boolean
     */
    private static boolean isXls(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    /**
     * 判断是否是xlsx文件
     *
     * @param filePath 文件完整路径
     * @return boolean
     */
    private static boolean isXlsx(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

}
