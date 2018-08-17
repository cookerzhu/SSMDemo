package com.ssmdemo.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 解析excel服务
 * Created by zhuguangchuan on 2018/4/16.
 */
public interface ExcelService<T,E extends Exception> {

     Logger LOG = LoggerFactory.getLogger(ExcelService.class);
    /**
     * 获取表头，判断表头和文件名是否一致
     * @param fileName
     * @param formulaEvaluator
     * @param sheet
     * @throws E
     */
    void checkTableHeader(String fileName, FormulaEvaluator formulaEvaluator, Sheet sheet) throws E;

    /**
     * 获取单元格数据,单元格如果是excel函数，需要根据结果值类型type取值
     * @param cell
     * @param formulaEvaluator
     * @param type cellValue的类型
     * @return
     */
    default String getCellValue(Cell cell, FormulaEvaluator formulaEvaluator, String type){
        if ( cell == null || formulaEvaluator == null)
            return "";

        switch(cell.getCellType()) {
            case Cell.CELL_TYPE_BLANK:
                return "";
            case Cell.CELL_TYPE_STRING:
                return  cell.getStringCellValue().trim();
            case Cell.CELL_TYPE_BOOLEAN:
                return String.valueOf( cell.getBooleanCellValue());
            case Cell.CELL_TYPE_NUMERIC:
                String result = NumberFormat.getInstance().format(cell.getNumericCellValue());
                if(result.indexOf(",") >= 0){
                    result = result.replace(",", "");
                }
                return  result;
            case Cell.CELL_TYPE_FORMULA:
                switch (type){
                    case "B" :
                        return String.valueOf(formulaEvaluator.evaluate(cell).getBooleanValue());
                    case "N" :
                        return String.valueOf(formulaEvaluator.evaluate(cell).getNumberValue());
                    case "S":
                        return String.valueOf(formulaEvaluator.evaluate(cell).getStringValue());
                    default:
                        return String.valueOf(formulaEvaluator.evaluate(cell).getErrorValue());
                }
            default:
                return "";
        }
    }

    /**
     * 解析excel，默认取第一张sheet
     * @param fileName 文件名
     * @param inputStream 文件输入流
     * @param dataPoint 开始解析的数据节点值
     * @param fields 要映射的实体的字段名数组，需要与excel列对应，动态对应
     * public static final String[] fields = {"excel列名1=entity属性1","excel列名2=entity属性2","excel列名3=entity属性3"}
     * @param supplier 提供T的supplier
     * @param consumer 遍历entityList 的consumer
     * @return List<T> 实体类集合
     * @throws Exception
     */
    default List<T> parseExcel(String fileName, InputStream inputStream, String dataPoint, String[] fields,Supplier<T> supplier,
                               Consumer<T> consumer)
            throws Exception{
        Workbook wb ;
        FormulaEvaluator formulaEvaluator;

        if (fileName.endsWith("xlsx")) {
            wb = new XSSFWorkbook(inputStream);
            formulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) wb);
        } else  {
            wb = new HSSFWorkbook(inputStream);
            formulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) wb);
        }

        Sheet sheet = wb.getSheetAt(0);
        int firstRow = sheet.getFirstRowNum();
        int lastRow = sheet.getLastRowNum();
        int minRowIx = 0 ,minColIx = 0, maxColIx = 0;

        //获取表头，判断表头和文件名是否一致
        checkTableHeader(fileName, formulaEvaluator, sheet);

        int headerFirstRow=0,headerLastRow=0;
        boolean merged = false;
        //寻找数据起始行，起始列
        outer:for (int i = firstRow; i < lastRow; i++) {
            Row row = sheet.getRow(i);
            if(row == null || isEmptyRow(row)){
                continue ;
            }
            for(int j=row.getFirstCellNum(),k=row.getLastCellNum();j<k;j++){
                Cell cell = row.getCell(j);
                if(dataPoint.equals(getCellValue(cell, formulaEvaluator,"S"))){
                    int[] rowColumn = isMerged(sheet,i,j) ;
                    if(rowColumn != null) {
                        minRowIx = rowColumn[1] + 1 ;//表头下一行是数据
                        minColIx = rowColumn[3] + 1  ;//跳过 头部标识 这一列
                        headerFirstRow= rowColumn[0];
                        headerLastRow = rowColumn[1];
                        merged =true;
                    }else{
                        minRowIx = i + 1 ;//表头下一行是数据
                        minColIx = j + 1  ;//跳过 头部标识 这一列
                        merged = false;
                    }
                        maxColIx = row.getLastCellNum();//表头最后一列
                    break outer;
                }
            }
        }

        //检查excel列数量是否与字段名fields数量匹配
        if(maxColIx-minColIx < fields.length){
            throw new Exception("Excel模板缺少列");
        }
        //excel列名和entity属性的匹配
        Map<String,String> fieldMap = new HashMap(fields.length);
        for (String field: fields) {
            String[] arr = field.split("=");
            fieldMap.put(arr[0],arr[1]);
        }

        //表头行 列名重排序
        String[] headers = new String[fields.length];
        if(merged){//合并的
            for (int i = headerFirstRow; i <= headerLastRow; i++) {
                Row row = sheet.getRow(i);
                for(int j = minColIx,k=0;j<maxColIx;j++,k++){
                    Cell cell = row.getCell(j);
                    String cellValue = getCellValue(cell, formulaEvaluator,"S");
                    headers[k] = (headers[k] == null ? "" : headers[k]) + cellValue;
                }
            }
            //headers(列名)  ->  headers(对象field)
            for (int i = 0; i < headers.length; i++) {
                String key = headers[i];
                headers[i] = fieldMap.get(key);
                if(headers[i] == null){
                    throw new Exception("列名错误："+ key);
                }
            }
        }else{
            Row headerRow = sheet.getRow(minRowIx - 1);//header
            for(int i = minColIx,j=0;i<maxColIx;i++,j++){
                Cell cell = headerRow.getCell(i);
                String cellValue = getCellValue(cell, formulaEvaluator,"S");
                headers[j] = fieldMap.get(cellValue);
                if(headers[j] == null){
                    throw new Exception("列名错误："+ cellValue);
                }
            }
        }

        //利用反射解析实体类
        List<T> entityList = new ArrayList<>();
        for (int i = minRowIx; i <= lastRow; i++) {
            Row row = sheet.getRow(i);
            if(row == null || isEmptyRow(row)){
                continue ;
            }
            T entity = supplier.get();
            Class clazz = entity.getClass();
            for (int j = minColIx,k=0; j < maxColIx ; j++,k++) {
                Cell cell = row.getCell(j);
                String fieldK = headers[k];
                if(StringUtils.isBlank(fieldK)){
                    continue;
                }
                String methodName = "set" + fieldK.substring(0, 1).toUpperCase() + fieldK.substring(1);
                String type = clazz.getDeclaredField(fieldK).getGenericType().getTypeName();

                Class parameterType ;
                Object value ;

                if("double".equals(type) || "java.lang.Double".equals(type)){
                    parameterType = Double.class;
                    value = Double.parseDouble(StringUtils.isBlank(getCellValue(cell,formulaEvaluator,"N")) ? "0" :getCellValue(cell,formulaEvaluator,"N"));
                }else if("java.math.BigDecimal".equals(type)){
                    parameterType = BigDecimal.class;
                    value = BigDecimal.valueOf(Double.parseDouble(StringUtils.isBlank(getCellValue(cell,formulaEvaluator,"N")) ? "0" : getCellValue(cell,formulaEvaluator,"N")));
                }else if("java.lang.String".equals(type)){
                    parameterType = String.class;
                    value = getCellValue(cell,formulaEvaluator,"S");
                }else if("java.lang.Integer".equals(type) ||"int".equals(type)){
                	parameterType = Integer.class;
                	value = Integer.parseInt(StringUtils.isBlank(getCellValue(cell, formulaEvaluator,"N")) ? "0" : getCellValue(cell, formulaEvaluator,"N"));
                }else if("java.lang.Long".equals(type) || "long".equals(type)){
                    parameterType = Long.class;
                    value = Long.parseLong(StringUtils.isBlank(getCellValue(cell, formulaEvaluator,"N")) ? "0" : getCellValue(cell, formulaEvaluator,"N"));
                }
                else{
                	continue;
                }
                Method method =  clazz.getMethod(methodName,parameterType);
                method.invoke(entity,value);
            }
            entityList.add(entity);
        }
        if(consumer != null){
            entityList.stream().forEach(consumer);
        }
        return entityList;
    }

    /**
     * 是否是合并单元格
     * @param sheet
     * @param row 第几行
     * @param column 第几列
     * @return 如果是合并单元格，则返回该单元格的lastRow和lastColumn
     */
    default int[] isMerged(Sheet sheet, int row, int column){
        int sheetMergeCount = sheet.getNumMergedRegions();

        for(int i = 0 ; i < sheetMergeCount ; i++){
            int firstRow= sheet.getMergedRegion(i).getFirstRow();
            int lastRow = sheet.getMergedRegion(i).getLastRow();
            int firstColumn = sheet.getMergedRegion(i).getFirstColumn();
            int lastColumn = sheet.getMergedRegion(i).getLastColumn();

            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    return new int[]{firstRow,lastRow,firstColumn,lastColumn} ;
                }
            }
        }
        return null ;
    }

    /**
     * 是否是空行
     * @param row
     * @return
     */
    default  boolean isEmptyRow(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
                return false;
        }
        return true;
    }

}
