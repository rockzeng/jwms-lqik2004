package com.res0w.jwms.method;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

public class XMLRW {
	private Document doc = null;

	private void init(String XMLFile) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		doc = db.parse(new File(XMLFile));
		doc.getDocumentElement().normalize();
//		System.out.println(doc.getDocumentElement().getNodeName());
	}

	
	@SuppressWarnings("unchecked")
	public Object[] viewXML(String XMLFile,String sqlName) throws Exception {
		List result=new Vector();
		this.init(XMLFile);
		int sqlNum=doc.getElementsByTagName(sqlName).getLength();
		System.out.println(sqlNum);
		Element sqlType = (Element) doc.getElementsByTagName(sqlName)
				.item(0);
//		System.out.println(sqlType.toString());
		NodeList listOfSql = sqlType.getElementsByTagName("STA");
		for (int i = 0; i <listOfSql.getLength(); i++) {
			Element staNode = (Element)listOfSql.item(i);
			NodeList staNL=staNode.getChildNodes();
			String staa = (String) staNL.item(0).getNodeValue();
//			System.out.println(staa);
			result.add(staa);
		}
		return result.toArray();
	}

	public static void main(String[] args) throws Exception {
		XMLRW xml = new XMLRW();
		Object[] sta;
		int cont=0;//sta长度计数
		sta = xml.viewXML("data/SQL_STA.xml","SQLSERVER");
		while(cont<sta.length){
			System.out.println(sta[0].toString());
			cont++;
		}
	}
}
