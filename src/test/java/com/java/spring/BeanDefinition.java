package com.java.spring;
/**
 * VO 封装对象xml文件数据《bean》*/
public class BeanDefinition {
	private String id;
	private String targetClass;
	private boolean lazy;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTargetClass() {
		return targetClass;
	}
	public void setTargetClass(String targetClass) {
		this.targetClass = targetClass;
	}
	public boolean isLazy() {
		return lazy;
	}
	public void setLazy(boolean lazy) {
		this.lazy = lazy;
	}
	@Override
	public String toString() {
		return "BeanDefinition [id=" + id + ", targetClass=" + targetClass + ", lazy=" + lazy + "]";
	}
	
}
