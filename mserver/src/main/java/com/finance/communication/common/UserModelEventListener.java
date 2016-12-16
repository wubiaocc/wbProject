package com.finance.communication.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.eventusermodel.EventWorkbookBuilder.SheetRecordCollectingListener;
import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BlankRecord;
import org.apache.poi.hssf.record.BoolErrRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.FormulaRecord;
import org.apache.poi.hssf.record.LabelRecord;
import org.apache.poi.hssf.record.LabelSSTRecord;
import org.apache.poi.hssf.record.NumberRecord;
import org.apache.poi.hssf.record.RowRecord;
import org.apache.poi.hssf.record.SSTRecord;
import org.apache.poi.hssf.record.chart.DataFormatRecord;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;

/**
 * 基于POI HSSF的eventmodel 模型的时间解析方式 优点：解析数据相当快。
 * 缺点：1.仅仅支持97~2003版本的excel，不支持2007版本的excel。 2.只能读Excel中一个Sheet页面。
 * 
 */
public class UserModelEventListener implements HSSFListener {
	private SSTRecord sstrec;
	/** Should we output the formula, or the value it has? */
	private boolean outputFormulaValues = true;
	/** For parsing Formulas */
	private SheetRecordCollectingListener workbookBuildingListener;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// 当前Sheet的内容
	private List<Map<String, String>> currentSheetDataMap = new ArrayList<Map<String, String>>();
	// 列对应的字段
	// private static String[] trianListheadTitle=new
	// String[]{"trainCode","firstStation","lastStation","startStation","arriveStation","startTime","arriveTime","fistLevelPrice","secondLevelPrice","km","useDate","1","2","3","4","5","6","7","8"};

	private FormatTrackingHSSFListener formatListener;

	public FormatTrackingHSSFListener getFormatListener() {
		return formatListener;
	}

	public void setFormatListener(FormatTrackingHSSFListener formatListener) {
		this.formatListener = formatListener;
	}

	private String[] trianListheadTitle;

	public String[] getTrianListheadTitle() {
		return trianListheadTitle;
	}

	public void setTrianListheadTitle(String[] trianListheadTitle) {
		this.trianListheadTitle = trianListheadTitle;
	}

	// 一行记录
	private Map<String, String> currentSheetRowDataMap = new HashMap<String, String>();
	private int curRowNum = 0;
	private int ignoreRowNum = 1;
	private int sheetNo = 0;

	@Override
	public void processRecord(org.apache.poi.hssf.record.Record record) {

		switch (record.getSid()) {

		case BOFRecord.sid:
			BOFRecord bof = (BOFRecord) record;
			// 顺序进入新的Workbook
			if (bof.getType() == BOFRecord.TYPE_WORKBOOK) {
				// 顺序进入新的Worksheet，因为Event API不会把Excel文件里的所有数据结构都关联起来，
				// 所以这儿一定要记录现在进入第几个sheet了。
			} else if (bof.getType() == BOFRecord.TYPE_WORKSHEET) {
				sheetNo++;
				if (sheetNo == 1) {
					// 读取新的一个Sheet页
					currentSheetDataMap = new ArrayList<Map<String, String>>();

				}
			}
			break;
		// 开始解析Sheet的信息，记录sheet，这儿会把所有的sheet都顺序打印出来，如果有多个sheet的话，可以顺序记入到一个List里
		case BoundSheetRecord.sid:
			BoundSheetRecord bsr = (BoundSheetRecord) record;
			break;
		// 执行行记录事件
		case RowRecord.sid:
			RowRecord rowrec = (RowRecord) record;
			break;
		// SSTRecords store a array of unique strings used in Excel.
		case SSTRecord.sid:
			sstrec = (SSTRecord) record;
			break;

		// 发现数字类型的cell，因为数字和日期都是用这个格式，所以下面一定要判断是不是日期格式，另外默认的数字也会被视为日期格式，所以如果是数字的话，一定要明确指定格式！！！！！！！
		case NumberRecord.sid:
			NumberRecord numrec = (NumberRecord) record;

			String formatStr = formatListener.getFormatString(numrec);
			String data = String.valueOf(numrec.getValue());

			if (formatStr.toLowerCase().contains("dd") || formatStr.toLowerCase().contains("m")
					|| formatStr.toLowerCase().contains("y") || formatStr.toLowerCase().contains("h") || formatStr.toLowerCase().contains("s")) {
				data = sdf.format(HSSFDateUtil.getJavaDate(Double.parseDouble(data)));

				addDataAndrChangeRow(numrec.getRow(), numrec.getColumn(), data);
			} else {

				Object value = "";

				long longVal = Math.round(numrec.getValue());
				double doubleVal = numrec.getValue();

				if (Double.parseDouble(longVal + ".0") == doubleVal)
					value = longVal;
				else
					value = doubleVal;

				addDataAndrChangeRow(numrec.getRow(), numrec.getColumn(), value);
			}

			break;
		case DataFormatRecord.sid:

			break;

		case LabelRecord.sid:
			LabelRecord lrec = (LabelRecord) record;

			String valuel = lrec.getValue().trim();
			addDataAndrChangeRow(lrec.getRow(), lrec.getColumn(), valuel);

			break;

		// 发现字符串类型，这儿要取字符串的值的话，跟据其index去字符串表里读取
		case LabelSSTRecord.sid:
			LabelSSTRecord lsr = (LabelSSTRecord) record;
			String valuels = sstrec.getString(lsr.getSSTIndex()).toString().trim();

			addDataAndrChangeRow(lsr.getRow(), lsr.getColumn(), valuels);

			break;

		case BoolErrRecord.sid: // 解析boolean错误信息
			BoolErrRecord ber = (BoolErrRecord) record;
			if (ber.isBoolean()) {
				String thisStr = ber.getBooleanValue() + "";

				addDataAndrChangeRow(ber.getRow(), ber.getColumn(), thisStr);
			}
			if (ber.isError()) {
			}
			break;
		// 空白记录的信息
		case BlankRecord.sid:
			BlankRecord br = (BlankRecord) record;

			addDataAndrChangeRow(br.getRow(), br.getColumn(), "");
			break;
		case FormulaRecord.sid: // 数式
			FormulaRecord fr = (FormulaRecord) record;
			String thisStr = fr.getValue() + "";

			addDataAndrChangeRow(fr.getRow(), fr.getColumn(), thisStr);

			break;
		default:
			break;
		}

	}

	/**
	 * HH:MM格式时间的数字转换方法</li>
	 * 
	 * @param sNum
	 * @return
	 */
	private static String getTime(double daynum) {
		double totalSeconds = daynum * 86400.0D;
		// 总的分钟数
		int seconds = (int) totalSeconds / 60;
		// 实际小时数
		int hours = seconds / 60;
		int minutes = seconds - hours * 60;
		// 剩余的实际分钟数
		StringBuffer sb = new StringBuffer();
		if (String.valueOf(hours).length() == 1) {
			sb.append("0" + hours);
		} else {
			sb.append(hours);
		}
		sb.append(":");
		if (String.valueOf(minutes).length() == 1) {
			sb.append("0" + minutes);
		} else {
			sb.append(minutes);
		}
		return sb.toString();
	}

	/**
	 * 添加数据记录并检查是否换行
	 * 
	 * @param row
	 *            实际当前行号
	 * @param col
	 *            实际记录当前列
	 * @param value
	 *            当前cell的值
	 */
	public void addDataAndrChangeRow(int row, int col, Object value) {
		if (row != 0) {
			if (curRowNum != row) {
				if (CollectionUtils.isEmpty(currentSheetDataMap)) {
					currentSheetDataMap = new ArrayList<Map<String, String>>();
				}
				// currentSheetDataMap.add(currentSheetRowDataMap);
				currentSheetRowDataMap = new HashMap<String, String>();
				currentSheetRowDataMap.put(trianListheadTitle[col], value == null ? "" : value.toString());

				currentSheetDataMap.add(currentSheetRowDataMap);

				curRowNum = row;
			} else {
				currentSheetRowDataMap.put(trianListheadTitle[col], value == null ? "" : value.toString());
			}
		}
		// 当前行如果大于实际行表示改行忽略，不记录

	}

	public List<Map<String, String>> getCurrentSheetDataMap() {
		return currentSheetDataMap;
	}

	public void setCurrentSheetDataMap(List<Map<String, String>> currentSheetDataMap) {
		this.currentSheetDataMap = currentSheetDataMap;
	}

	public Map<String, String> getCurrentSheetRowDataMap() {
		return currentSheetRowDataMap;
	}

	public void setCurrentSheetRowDataMap(Map<String, String> currentSheetRowDataMap) {
		this.currentSheetRowDataMap = currentSheetRowDataMap;
	}

	public int getCurRowNum() {
		return curRowNum;
	}

	public void setCurRowNum(int curRowNum) {
		this.curRowNum = curRowNum;
	}

	public int getIgnoreRowNum() {
		return ignoreRowNum;
	}

	public void setIgnoreRowNum(int ignoreRowNum) {
		this.ignoreRowNum = ignoreRowNum;
	}

}
