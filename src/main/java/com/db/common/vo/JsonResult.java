package com.db.common.vo;

import java.io.Serializable;

public class JsonResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4138033536625725437L;
	private int state = 1;
	private String message = "ok";
	private Object data;
	public JsonResult() {
		super();
	}
	public JsonResult(String message) {
		super();
		this.message = message;
	}
	public JsonResult(Object data) {
		super();
		this.data = data;
	}
	public JsonResult(Throwable t) {
		this.state = 0;
		this.message = t.getMessage();
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
