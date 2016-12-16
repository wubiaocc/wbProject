package com.finance.communication.common;

import java.util.List;

public class FocusResult {
    private Integer totalCount;
    private List<String> openIds;
    private String nextOpenId;
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public List<String> getOpenIds() {
		return openIds;
	}
	public void setOpenIds(List<String> openIds) {
		this.openIds = openIds;
	}
	public String getNextOpenId() {
		return nextOpenId;
	}
	public void setNextOpenId(String nextOpenId) {
		this.nextOpenId = nextOpenId;
	}
    
}
