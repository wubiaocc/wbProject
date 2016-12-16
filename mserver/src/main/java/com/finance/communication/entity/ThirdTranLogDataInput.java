package com.finance.communication.entity;

import java.util.List;

public class ThirdTranLogDataInput {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 类别：1 交易流水， 2 商户数量统计
	 */
	private String serviceType;
	
	/**
	 * 交易流水待发送数据
	 */
	private List<ThirdTranLogInput> logList;
	
	/**
	 * 新增商户待发送数据
	 */
	private List<ThirdTranLogMrtInput> mrtList;
	
	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public List<ThirdTranLogInput> getLogList() {
		return logList;
	}

	public void setLogList(List<ThirdTranLogInput> logList) {
		this.logList = logList;
	}

	public List<ThirdTranLogMrtInput> getMrtList() {
		return mrtList;
	}

	public void setMrtList(List<ThirdTranLogMrtInput> mrtList) {
		this.mrtList = mrtList;
	}
	
}
