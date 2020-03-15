package org.ainy.deepmind.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 加载properties属性文件
 * 
 * @author demo
 *
 */
public class PropertyUtil {

	private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);
	private static Properties props;

	static {

		loadProps();
	}

	synchronized static private void loadProps() {

		logger.info("开始加载properties文件内容......");
		props = new Properties();
		InputStream in = null;
		try {

			/*
			 * 第一种
			 * 通过类加载器进行获取properties文件流 可获取项目中resources文件夹下的properties文件
			 */
			in = PropertyUtil.class.getClassLoader().getResourceAsStream("json.properties");
			
			/*
			 * 第二种
			 * 直接获取FileInputStream
			 * 可获取系统任意位置下的properties文件
			 */
			// in = new FileInputStream("E:/Works/Test/json.properties");
			
			/*
			 * 第三种
			 * 通过类进行获取properties文件流 
			 */
			/*in = PropertyUtil.class.getResourceAsStream("json.properties");*/
			
			props.load(in);
		} catch (FileNotFoundException e) {
			logger.error("properties文件未找到");
		} catch (IOException e) {
			logger.error("出现IOException");
		} finally {
			try {
				if (null != in) {
					in.close();
				}
			} catch (IOException e) {
				logger.error("jdbc.properties文件流关闭出现异常");
			}
		}
		logger.info("加载properties文件内容完成...........");
		logger.info("properties文件内容：" + props);
	}

	public static String getProperty(String key) {
		if (null == props) {
			loadProps();
		}
		return props.getProperty(key);
	}

	public static String getProperty(String key, String defaultValue) {
		if (null == props) {
			loadProps();
		}
		return props.getProperty(key, defaultValue);
	}
}
