package com.cll.yl.back.common.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 描述信息:
 * 属性配置文件加载
 * @author CLL
 * @version 1.0
 * @date 2019/3/5 11:38
 */
public class PropertiesUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    /**
     * 单例对象
     */
    private volatile static PropertiesUtil singleton = null;

    /**
     * 属性对象
     */
    private Properties properties = null;

    public static PropertiesUtil getInstance() {
        if (null == singleton) {
            synchronized (PropertiesUtil.class) {
                if (null == singleton) {
                    singleton = new PropertiesUtil();
                }
            }
        }
        return singleton;
    }

    /**
     * 根据key获取配置文件中的value
     * @param key   属性名
     * @return  属性值
     */
    public String getValue(String key) {
        return properties.getProperty(key);
    }

    private PropertiesUtil() {
        try {
//            String path = this.getClass().getClassLoader().getResource("/").getPath();
//            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(path + "sys.properties"));
            InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("sys.properties");
            properties = new Properties();
            properties.load(resourceAsStream);
        } catch (NullPointerException npe) {
            logger.error("******获取项目磁盘路径抛出异常" + npe.getMessage(), npe);
        } catch (FileNotFoundException nfe) {
            logger.error("******为找到文件异常" + nfe.getMessage(), nfe);
        } catch (IOException ioe) {
            logger.error("******加载文件抛出异常" + ioe.getMessage(), ioe);
        }
    }

}