package com.java.spring;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DefaultBeanFactory {
	//存储bean
	private Map<String, BeanDefinition> beanMap = new ConcurrentHashMap<String, BeanDefinition>();
	//存储实例对象
	private Map<String,Object> instanceMap = new ConcurrentHashMap<String, Object>();
	public DefaultBeanFactory(String cfgFile) throws ParserConfigurationException, SAXException, IOException {
		//1.获取文件对应的流对象
		InputStream in = ClassLoader.getSystemResourceAsStream(cfgFile);
		//2.处理流对象
		handleStream(in);
	}
	private void handleStream(InputStream in) throws ParserConfigurationException, SAXException, IOException {
		//构建解析器对象
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		//解析流对象
		Document doc = builder.parse(in);
		//处理对象
		handleDocument(doc);
		
	}
	private void handleDocument(Document doc) {
		//获取所有的bean元素
		NodeList list = doc.getElementsByTagName("bean");
		for(int i=0;i<list.getLength();i++) {
			Node node = list.item(i);
			//处理对象封装
			BeanDefinition bd = handleNode(node);
			beanMap.put(bd.getId(), bd);
//			System.out.println(node.getNodeName());
		}
	}
	private BeanDefinition handleNode(Node node) {
		BeanDefinition bd = new BeanDefinition();
		NamedNodeMap attrs = node.getAttributes();
		String id = attrs.getNamedItem("id").getNodeValue();
		String targetcls = attrs.getNamedItem("class").getNodeValue();
		String lazy = attrs.getNamedItem("lazy").getNodeValue();
		bd.setId(id);
		bd.setTargetClass(targetcls);
		bd.setLazy(Boolean.valueOf(lazy));
		return bd;
	}
	private Object newInstance(Class<?> cls) {
		try {
			Constructor<?> con = cls.getDeclaredConstructor();
			if(!con.isAccessible())
				con.setAccessible(true);
			return con.newInstance();
		} catch (Exception e) {
			return null;
		}
	}
	public <T>T getObject(String key,Class<T> cls){
		if(!beanMap.containsKey(key))
			throw new RuntimeException("no such key");
		BeanDefinition bd = beanMap.get(key);
		if(!bd.getTargetClass().equals(cls.getName()))
			throw new RuntimeException("no such bean class");
		Object obj = instanceMap.get(key);
		if(obj==null) {
			obj = newInstance(cls);
			instanceMap.put(key, obj);
		}
		return (T)obj;
	}
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		DefaultBeanFactory defaultBeanFactory = new DefaultBeanFactory("spring-configs.xml");
		System.out.println(defaultBeanFactory.beanMap);
		Object ob1 = defaultBeanFactory.getObject("date", java.util.Date.class);
		Object ob2 = defaultBeanFactory.getObject("date", java.util.Date.class);
		System.out.println(ob1==ob2);
	}
}
