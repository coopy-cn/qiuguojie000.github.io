package com.core.teamwork.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@SuppressWarnings({ "unchecked", "deprecation" })  
public class ExcelParseUtil<T> {

	public void exportExcel(List<String[]> dataset, OutputStream out)  
    {  
        exportExcel("sheet1", null, dataset, out, "yyyy-MM-dd");  
    }  
  
    public void exportExcel(String[] headers, List<String[]> dataset,  
            OutputStream out)  
    {  
        exportExcel("sheet1", headers, dataset, out, "yyyy-MM-dd");  
    }  
  
    public void exportExcel(String[] headers, List<String[]> dataset,  
            OutputStream out, String pattern)  
    {  
        exportExcel("sheet1", headers, dataset, out, pattern);  
    }
    
	/** 
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上 
     *  
     * @param title 
     *            表格标题名 
     * @param headers 
     *            表格属性列名数组 
     * @param dataset 
     *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的 
     *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据) 
     * @param out 
     *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中 
     * @param pattern 
     *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd" 
     */  
    public void exportExcel(String title, String[] headers,  
    		List<String[]> dataset, OutputStream out, String pattern)  
    {  
        // 声明一个工作薄  
        HSSFWorkbook workbook = new HSSFWorkbook();  
        // 生成一个表格  
        HSSFSheet sheet = workbook.createSheet(title);  
        
        //设置列宽
        sheet.setColumnWidth((short) 1, (short) 10000);
        sheet.setColumnWidth((short) 7, (short) 8000);
        
        // 设置表格默认列宽度为15个字节  
        sheet.setDefaultColumnWidth((short) 15);  
        // 生成一个样式  
        HSSFCellStyle style = workbook.createCellStyle();  
        // 设置这些样式  
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);  
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
        // 生成一个字体  
        HSSFFont font = workbook.createFont();  
        font.setColor(HSSFColor.BLACK.index);  
        font.setFontHeightInPoints((short) 12);  
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
        // 把字体应用到当前的样式  
        style.setFont(font);  
        // 生成并设置另一个样式  
        HSSFCellStyle style2 = workbook.createCellStyle();  
        style2.setFillForegroundColor(HSSFColor.WHITE.index);  
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
        style2.setAlignment(HSSFCellStyle.ALIGN_LEFT);  
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
        // 生成另一个字体  
        HSSFFont font2 = workbook.createFont();  
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);  
        // 把字体应用到当前的样式  
        style2.setFont(font2);  
  
        // 声明一个画图的顶级管理器  
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();  
        // 定义注释的大小和位置,详见文档  
        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,  
                0, 0, 0, (short) 4, 2, (short) 6, 5));  
        // 设置注释内容  
//        comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));  
//        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.  
//        comment.setAuthor("leno");  
  
        // 产生表格标题行  
        HSSFRow row = sheet.createRow(0);  
        for (short i = 0; i < headers.length; i++)  
        {  
            HSSFCell cell = row.createCell(i);  
            cell.setCellStyle(style);  
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);  
            cell.setCellValue(text);  
        }  
  
        HSSFFont font3 = workbook.createFont();  
        font3.setColor(HSSFColor.BLACK.index);  
        // 遍历集合数据，产生数据行  
        for(int k = 0; k < dataset.size(); k ++){
        	row = sheet.createRow(k + 1);
        	String[] arr = dataset.get(k);
        	for(int i = 0; i < arr.length; i++){
        		HSSFCell cell = row.createCell(i);  
	                cell.setCellStyle(style2);  
        		String textValue = arr[i];
        		if (textValue != null)  
                {  
                    Pattern p = Pattern.compile("^//d+(//.//d+)?$");  
                    Matcher matcher = p.matcher(textValue);  
                    if (matcher.matches()){  
                        // 是数字当作double处理  
                        cell.setCellValue(Double.parseDouble(textValue));  
                    }else{  
                        HSSFRichTextString richString = new HSSFRichTextString(  
                                textValue);  
                        richString.applyFont(font3);  
                        cell.setCellValue(richString);  
                    }  
                }  
        	}
        }
        try  
        {  
            workbook.write(out);  
        }  
        catch (IOException e)  
        {  
            e.printStackTrace();  
        }  
    }  
    
    
	/**
	 * 解析Excel文件2003 以下的版本 
	 **/
	private static Map<String, String> parseXls(InputStream in)
			throws IOException {

		Map<String, String> excelInfo = new HashMap<String, String>();

		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(in);
		// 循环工作表Sheet
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {

			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if (hssfSheet == null) {
				continue;
			}

			for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				
				HSSFCell username = hssfRow.getCell((short)0);
				if (username == null) {
					continue;
				}
				String nameString=TypeToString(username);
				HSSFCell password = hssfRow.getCell((short)1);
				if (password == null) {
					continue;
				}
				String passwordString =TypeToString(password);
				
				excelInfo.put(nameString, passwordString);
				
				//System.out.println("useranme:"+nameString+"-----password:"+passwordString);
			}
		}
		
		return excelInfo;
	}
	

	/**
	 * 解析Excel文件2007 以上的版本 
	 **/
	private static Map<String, String> parseXlsx(InputStream in) throws IOException{	

		Map<String, String> excelInfo = new HashMap<String, String>();

		XSSFWorkbook hssfWorkbook = new XSSFWorkbook(in);
		// 循环工作表Sheet
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {

			XSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if (hssfSheet == null) {
				continue;
			}

			for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				
				XSSFCell username = hssfRow.getCell((short)0);
				if (username == null) {
					continue;
				}
				String nameString=TypeToString(username);
				XSSFCell password = hssfRow.getCell((short)1);
				if (password == null) {
					continue;
				}
				String passwordString =TypeToString(password);
				
				excelInfo.put(nameString, passwordString);
				
				//System.out.println("useranme:"+nameString+"-----password:"+passwordString);
			}
		}
		
		return excelInfo;
	}
	private static String TypeToString(HSSFCell hss){
		
		switch(hss.getCellType()){
		
		case XSSFCell.CELL_TYPE_NUMERIC:
			return String.valueOf(Double.valueOf(hss.getNumericCellValue()).intValue());
		case XSSFCell.CELL_TYPE_STRING:
			return hss.getStringCellValue();
		default:
			return null;
		}
	}
	private static String TypeToString(XSSFCell xss){
		
		switch(xss.getCellType()){
		
		case XSSFCell.CELL_TYPE_NUMERIC:
			return String.valueOf(Double.valueOf(xss.getNumericCellValue()).intValue());
		case XSSFCell.CELL_TYPE_STRING:
			return xss.getStringCellValue();
		default:
			return null;
		}
	}
	/**
	 * 判断Excel文件是属于xls xlsx哪种类型
	 **/
	private static String judgeFileType(InputStream in) throws IOException{
		try {
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(in);	
		} catch (OfficeXmlFileException e) {
			// TODO: handle exception
			return "xlsx";
		} 
		
		return "xls";
	}

	/**
	 * 对外解析Exlcel文件 接口
	 **/
	public static Map<String, String> parseExcel(InputStream in,String path) throws IOException{
		
		String fileType = judgeFileType(in);
		
		in = new FileInputStream(new File(path));
		
		if(fileType.equals("xls"))
			return parseXls(in);
		else if(fileType.equals("xlsx"))
			return parseXlsx(in);
		else 
			return null;
	}
	
//	
	public static void main(String[] args) throws IOException {
		
		InputStream in = new FileInputStream(new File("d:\\Administrator\\Desktop\\BiuBiu_20151216091707887.xls"));
		
		Map<String,String> map = parseExcel(in,"d:\\Administrator\\Desktop\\BiuBiu_20151216091707887.xls");
		System.out.println(map);
	}
}
