package com.finance.communication.common;
public class RespMessage {
	private Integer code = 0;
	
	private String message;
	
	private Object obj;
	
	private Object param;
	
	public RespMessage(){
	}
	
	public RespMessage(Integer code,String message,Object obj,Object param){
		this.setCode(code);
		this.setMessage(message);
	    this.obj = obj;
	    this.param=param;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getParam() {
		return param;
	}

	public void setParam(Object param) {
		this.param = param;
	}
}
