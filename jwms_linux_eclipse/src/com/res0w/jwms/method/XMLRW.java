package com.res0w.jwms.method;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

public class XMLRW {
	private Document doc = null;

	private void init(String XMLFile) throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		doc = db.parse(new File(XMLFile));
	}

	
	public ArrayList<String> viewXML(String XMLFile) throws Exception {
		ArrayList<String> result = null;
		this.init(XMLFile);
		Element sqlType = (Element) doc.getElementsByTagName("SQL_SERVER")
				.item(0);
		NodeList listOfSql = sqlType.getElementsByTagName("STA");
		for (int i = 0; i <= listOfSql.getLength(); i++) {
			Node staNode = listOfSql.item(i);
			String sta = staNode.getNodeValue().trim();
			System.out.println(sta);
			//result.add(sta);
		}
		return (result);
	}

	public static void main(String[] args) throws Exception {
		XMLRW xml = new XMLRW();
		ArrayList<String> sta;
		sta = xml.viewXML("data\\SQL_STA.xml");
		for (int s = 0; s < sta.size(); s++) {
			System.out.println(sta.get(s).toString().trim());
		}
	}
}
