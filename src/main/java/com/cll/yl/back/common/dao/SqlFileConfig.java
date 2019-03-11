package com.cll.yl.back.common.dao;

import com.cll.yl.back.common.DataBaseConstant;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述信息:
 * sql文件存储配置
 * @author CLL
 * @version 1.0
 * @date 2019/3/5 11:21
 */
public class SqlFileConfig {

    private static final Logger logger = LoggerFactory.getLogger(SqlFileConfig.class);

    /**
     * 定位单例对象
     */
    private volatile static SqlFileConfig singleton = null;

    /**
     * xml文件中的sql缓存语句
     */
    private Map<String, String> sqlFileMap = null;

    /**
     * 私有构造方法
     */
    private SqlFileConfig() {
        sqlFileMap = new HashMap<>();
    }

    /**
     * 单例构造方法
     * @return  单例对象
     */
    public static SqlFileConfig getInstance() {
        if (null == singleton) {
            logger.info("======不锁定检测为空");
            synchronized (SqlFileConfig.class) {
                if (null == singleton) {
                    logger.info("======锁定检测为空");
                    singleton = new SqlFileConfig();
                }
            }
        }
        return singleton;
    }

    /**
     * 加载SQL语句
     * @param resource  资源文件名称
     * @param id    sql语句标志
     * @return  sql语句
     */
    public String getLocalSql(String resource, String id) {
        String dataBaseName = PropertiesUtil.getInstance().getValue(DataBaseConstant.DATA_BASE_TYPE);
        if (StringUtils.isEmpty(dataBaseName)) {
            dataBaseName = DataBaseConstant.MYSQL;
        }
        dataBaseName = dataBaseName.toLowerCase();
        boolean expectFlag = (DataBaseConstant.MYSQL.equals(dataBaseName) || DataBaseConstant.ORACLE.equals(dataBaseName) || DataBaseConstant.SQLSERVER.equals(dataBaseName));
        if (!expectFlag) {
            logger.error("不支持该数据库 = {}", dataBaseName);
            return null;
        }
        String key = dataBaseName + "." + resource + "." + id;
        if (!sqlFileMap.containsKey(key)) {
            String filePath = DataBaseConstant.SQL_XML_PREFIX_DIRECTORY + File.separator + dataBaseName + File.separator + resource + DataBaseConstant.SQL_XML_SUFFIX_DIRECTORY;
            String xmlConfig = loadXmlConfig(filePath, id);
            sqlFileMap.put(key, xmlConfig);
        }
        return MapUtils.getString(sqlFileMap, key);
    }

    /**
     * 加载指定sql配置文件（.xml）
     * @param filePath  文件路径
     * @param contentId 内容标志
     * @return  加载内容
     */
    private String loadXmlConfig(String filePath, String contentId){
        if (StringUtils.isEmpty(filePath) || StringUtils.isEmpty(contentId)) {
            logger.error("******拒绝加载sql。filePath = {}, contentId = {}", filePath, contentId);
            return "";
        }
        try {
            // 原来使用
            String rootPath = this.getClass().getResource("/").getPath();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document parse = documentBuilder.parse(new File(rootPath + filePath));
            // 获取约定的root节点
            Node root = parse.getElementsByTagName("root").item(0);
            if (null == root) {
                logger.error("******获取root节点失败,请检查xml文件");
                return "";
            }
            Element rootElement = (Element) root;
            // 获取约定的content内容
            NodeList content = rootElement.getElementsByTagName("content");
            if (null == content) {
                logger.error("******获取content节点失败,请检查xml文件");
                return "";
            }
            int contentLength = content.getLength();
            contentId = contentId.toLowerCase();
            for (int i = 0; i < contentLength; i++) {
                Element node = (Element) content.item(i);
                String id = node.getAttribute("id");
                if (contentId.equals(id.toLowerCase())) {
                    return node.getTextContent();
                }
            }
            logger.error("******filePath = {}, contentId = {}未找到当前需要的sql，请检查xml文件。", filePath, contentId);
            return "";
        } catch (NullPointerException npe) {
            logger.error("******获取项目磁盘路径，抛出空指针异常" + npe.getMessage(), npe);
        } catch (ParserConfigurationException pce) {
            logger.error("******获取DocumentBuilder对象抛出异常" + pce.getMessage(), pce);
        } catch (SAXException saxe) {
            logger.error("******将XML文件转换为Document节点对象抛出异常" + saxe.getMessage(), saxe);
        } catch (IOException ioe) {
            logger.error("******将XML文件转换为Document节点对象抛出IO异常" + ioe.getMessage(), ioe);
        }
        return null;
    }



    /**
     * 清空xml文件中的sql语句
     */
    public void clearSqlFileMap() {
        sqlFileMap.clear();
    }

}