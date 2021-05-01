package org.iqa.suite.commons;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JavaUtils {
    private static final Logger logger = LoggerFactory.getLogger(JavaUtils.class);

	protected static String getClassFilePath(Class<?> cls){
		logger.info("******** getFilePath for class "+cls.getName());
		String strSourceClassName = cls.getResource(cls.getSimpleName()+".class").getPath();
		logger.info("*************** resource path is "+strSourceClassName);
		try {
			strSourceClassName = URLDecoder.decode(strSourceClassName,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuffer strFilePath = new StringBuffer();
		strFilePath.append(strSourceClassName.subSequence(1, strSourceClassName.indexOf("com")));
		strFilePath.append(cls.getName().replace(".","/"));
		strFilePath.append(".xlsx");
		logger.info("Class path is - "+strFilePath);
		//return strFilePath.toString();
		return strSourceClassName.replace(".class", ".xlsx");
	}
}
