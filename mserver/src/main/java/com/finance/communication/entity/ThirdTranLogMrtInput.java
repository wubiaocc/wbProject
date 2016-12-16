package com.finance.communication.entity;

/**
 * 第三方交易导入，商户数量统计
 * @author DaiRongliang
 * @2016年11月21日
 */
public class ThirdTranLogMrtInput {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 服务器
	 */
	private String server;
	
	/**
	 * 时间
	 */
	private String time;
	
	/**
	 * mqtt消息标签 trade productOpen..
	 */
	private String type;
	
	/**
	 * 新增商户数量
	 */
	private String openNum;

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOpenNum() {
		return openNum;
	}

	public void setOpenNum(String openNum) {
		this.openNum = openNum;
	}
	
}
