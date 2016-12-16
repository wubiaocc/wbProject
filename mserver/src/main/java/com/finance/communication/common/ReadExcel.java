package com.finance.communication.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.eventusermodel.EventWorkbookBuilder.SheetRecordCollectingListener;
import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.eventusermodel.MissingRecordAwareHSSFListener;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * poi 处理excel xls、xlsx
 * 
 * @author LiXiaoPeng
 * 
 *         2013-10-30 下午3:14:59
 */
public class ReadExcel
{
	// 2003
	private HSSFWorkbook hwb;
	private HSSFSheet hSSFSheet;
	// 2007
//	private XSSFWorkbook xwb;
//	private XSSFSheet xSSFSheet;
	// 标题
	private String[] title;
	// 标识
	private String isXlsOrXlsx;
	// 总行数
	private Integer rows;

	public Integer getRows()
	{
		return rows;
	}

	public void setRows(Integer rows)
	{
		this.rows = rows;
	}

	public ReadExcel(File file) throws FileNotFoundException, IOException
	{
		String fileName = file.getName();
		String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName
				.substring(fileName.lastIndexOf(".") + 1);
		if ("xls".equals(extension))
		{
			isXlsOrXlsx = "xls";

			hwb = new HSSFWorkbook(new FileInputStream(file));
			hSSFSheet = hwb.getSheetAt(0);
			// 初始化总行数
			setRows(hSSFSheet.getPhysicalNumberOfRows());
			// 标题行
			HSSFRow titleRow = hSSFSheet.getRow(0);
			if (titleRow != null)
			{
				// 标题总列数
				int titleRows = titleRow.getPhysicalNumberOfCells();
				title = new String[titleRows];
				for (int i = 0; i < titleRows; i++)
				{
					title[i] = titleRow.getCell(i).toString();
				}
			}

		} 
//		else if ("xlsx".equals(extension))
//		{
//			isXlsOrXlsx = "xlsx";
//			xwb = new XSSFWorkbook(new FileInputStream(file));
//			xSSFSheet = xwb.getSheetAt(0);
//			// 初始化总行数
//			setRows(xSSFSheet.getPhysicalNumberOfRows());
//			// 标题行
//			XSSFRow titleRow = xSSFSheet.getRow(0);
//			if (titleRow != null)
//			{
//				// 标题总列数
//				int titleRows = titleRow.getPhysicalNumberOfCells();
//				title = new String[titleRows];
//				for (int i = 0; i < titleRows; i++)
//				{
//					title[i] = titleRow.getCell(i).toString();
//
//				}
//			}
//		}
	}
	
	/**
	 * 第二行为标题行
	 * 
	 * @param file
	 * @param num
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public ReadExcel(File file, int num) throws FileNotFoundException, IOException
	{
		String fileName = file.getName();
		String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName
				.substring(fileName.lastIndexOf(".") + 1);
		if ("xls".equals(extension))
		{
			isXlsOrXlsx = "xls";
			
			hwb = new HSSFWorkbook(new FileInputStream(file));
			hSSFSheet = hwb.getSheetAt(0);
			// 初始化总行数
			setRows(hSSFSheet.getPhysicalNumberOfRows());
			// 标题行
			HSSFRow titleRow = hSSFSheet.getRow(1);
			if (titleRow != null)
			{
				// 标题总列数
				int titleRows = titleRow.getPhysicalNumberOfCells();
				title = new String[titleRows];
				for (int i = 0; i < titleRows; i++)
				{
					title[i] = titleRow.getCell(i).toString();
				}
			}
			
		} 
//		else if ("xlsx".equals(extension))
//		{
//			isXlsOrXlsx = "xlsx";
//			xwb = new XSSFWorkbook(new FileInputStream(file));
//			xSSFSheet = xwb.getSheetAt(0);
//			// 初始化总行数
//			setRows(xSSFSheet.getPhysicalNumberOfRows());
//			// 标题行
//			XSSFRow titleRow = xSSFSheet.getRow(1);
//			if (titleRow != null)
//			{
//				// 标题总列数
//				int titleRows = titleRow.getPhysicalNumberOfCells();
//				title = new String[titleRows];
//				for (int i = 0; i < titleRows; i++)
//				{
//					title[i] = titleRow.getCell(i).toString();
//					
//				}
//			}
//		}
	}

	/**
	 * 读取指定行的一行记录，返回一个map
	 * 
	 * @param rowIndex
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> readNextRowFromExcel(int rowIndex)
			throws Exception
	{
		Map<String, String> map = new HashMap<String, String>();
		Row row = null;
		if ("xls".equals(isXlsOrXlsx))
		{

			row = (HSSFRow) hSSFSheet.getRow(rowIndex);
		} 
//		else if ("xlsx".equals(isXlsOrXlsx))
//		{
//			row = (XSSFRow) xSSFSheet.getRow(rowIndex);
//
//		}
		// System.out.println("titie长度   "+title.length);
		// for (int i = 0; i < title.length; i++) {
		// System.out.println(title[i]);
		// }
		for (int j = row.getFirstCellNum(); j < title.length; j++)
		{
			map.put(title[j], getCell(row.getCell(j)));
		}
		return map;
	}

	/**
	 * 读取Excel 处理为Map数据格式
	 * 
	 * @param filePath
	 * @param titles
	 * @return
	 * @throws Exception 
	 */
	public List<Map<String, String>> getSheetDataMap(String filePath, String[] titles) throws Exception {

			UserModelEventListener xlsEventListener = new UserModelEventListener();

			xlsEventListener.setTrianListheadTitle(titles);

			MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(xlsEventListener);
			FormatTrackingHSSFListener formatListener = new FormatTrackingHSSFListener(listener);
			xlsEventListener.setFormatListener(formatListener);
			// 创建一个excel输入流
			FileInputStream fin = new FileInputStream(filePath);
			// 创建一个 org.apache.poi.poifs.filesystem.Filesystem
			POIFSFileSystem poifs = new POIFSFileSystem(fin);
			// 将excel 2003格式POI文档输入流
			InputStream din = poifs.createDocumentInputStream("Workbook");
			// 这儿为所有类型的Record都注册了监听器，如果需求明确的话，可以用addListener方法，并指定所需的Record类型
			HSSFRequest req = new HSSFRequest();
			// 添加监听记录的事件
			req.addListenerForAllRecords(xlsEventListener);

			boolean outputFormulaValues = true;
			if (outputFormulaValues) {
				req.addListenerForAllRecords(formatListener);
			} else {
				SheetRecordCollectingListener workbookBuildingListener = new SheetRecordCollectingListener(
						formatListener);
				req.addListenerForAllRecords(workbookBuildingListener);
			}

			// 创建时间工厂
			HSSFEventFactory factory = new HSSFEventFactory();
			// 处理基于时间文档流
			factory.processEvents(req, din);
			// 关闭文件流
			fin.close();
			// 关闭基于POI文档流
			din.close();

			return xlsEventListener.getCurrentSheetDataMap();

	}
	
	/**
	 * 获取单元格的内容
	 * 
	 * @param cell
	 * @return
	 */
	public static String getCell(Cell cell)
	{
		if (cell instanceof HSSFCell)
		{
			cell = (HSSFCell) cell;
		} 
//		else if (cell instanceof XSSFCell)
//		{
//			cell = (XSSFCell) cell;
//		}

		Object value = "";

		if (cell == null)
			return "";
		switch (cell.getCellType())
		{
		case HSSFCell.CELL_TYPE_STRING:
			value = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			long longVal = Math.round(cell.getNumericCellValue());
			double doubleVal = cell.getNumericCellValue();

			if (Double.parseDouble(longVal + ".0") == doubleVal)
				value = longVal;
			else
				value = doubleVal;
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			if (HSSFDateUtil.isCellDateFormatted(cell))
			{
				Date date = cell.getDateCellValue();
				if (date != null)
				{
					value = new SimpleDateFormat("yyyy-MM-dd").format(date);
				} else
				{
					value = "";
				}
			} else
			{
				value = String.valueOf(cell.getNumericCellValue());
				if (value.equals("NaN"))
				{// 如果获取的数据值为非法值,则转换为获取字符串
					value = cell.getRichStringCellValue().toString();
				}
			}
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			value = "";
			break;
		case HSSFCell.CELL_TYPE_ERROR:
			value = "";
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			value = (cell.getBooleanCellValue() == true ? "Y" : "N");
			break;
		default:
			value = "";
		}
		return value.toString();
	}
	
	public String[] getTitle() {
		return title;
	}
}
