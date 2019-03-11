package com.cll.yl.back.common.dao;

import com.cll.yl.back.common.DataBaseConstant;
import com.cll.yl.back.common.convertor.MapToEntryConvertUtil;
import com.cll.yl.back.common.entity.PageData;
import com.cll.yl.back.common.util.StringUtil;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述信息:
 *
 * @author CLL
 * @version 1.0
 * @date 2019/3/5 11:01
 */
public class BaseDao {

    private static final Logger logger = LoggerFactory.getLogger(BaseDao.class);

    @Resource
    protected JdbcTemplate jdbcTemplate;

    /**
     * get方法头
     */
    private static final String FIELD_GET_METHOD_PREFIX = "get";

    /**
     * sql中占位符
     */
    private static final String PLACEHOLDER = "?";

    /**
     * 从xml文件中获取sql语句。默认xml文件名称和持久层中类名一致，方法名称和xml中sql节点的ID一致
     * @return  sql语句
     */
    protected String getXmlSql(){
        // 根据堆栈轨迹获取类名称和方法名称
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        String clazzName = stackTrace[1].getClassName();
        String className = clazzName.substring(clazzName.lastIndexOf(".") + 1, clazzName.length());
        String methodName = stackTrace[1].getMethodName();
        return getXmlSqlByResourceAndId(className, methodName);
    }

    /**
     * 从xml文件中获取sql语句。默认xml文件名称和持久层中类名一致
     * @param methodName    sql节点名称
     * @return  sql语句
     */
    protected String getXmlSqlById(String methodName){
        // 根据堆栈轨迹获取类名称和方法名称
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        String clazzName = stackTrace[1].getClassName();
        String className = clazzName.substring(clazzName.lastIndexOf(".") + 1, clazzName.length());
        return getXmlSqlByResourceAndId(className, methodName);
    }

    /**
     * 根据xml名称和id来获得sql语句
     *
     * @param resource xml名称
     * @param id       xml中sql的id名
     * @return  sql语句
     */
    protected String getXmlSqlByResourceAndId(String resource, String id) {
        return SqlFileConfig.getInstance().getLocalSql(resource, id);
    }


    /**
     * 根据实体类型，查询实体对象，自动进行字段的映射绑定
     * @param sql   查询的SQL
     * @param cs    实体对象的类型。Class:表示所有类的类型
     * @param <T>   支持泛型
     * @return  实体对象
     */
    protected <T> T selectForEntity(String sql, Class<T> cs) {
        if (StringUtils.isEmpty(sql) || null == cs) {
            logger.error("******查询实体对象，参数错误");
            return null;
        }
        List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sql);
        if (CollectionUtils.isEmpty(queryForList)) {
            return null;
        }
        Map<String, Object> map = queryForList.get(0);
        return MapToEntryConvertUtil.convert(map, cs, "");
    }

    /**
     * 根据实体类型，查询实体对象，自动进行字段的映射绑定
     * @param sql   查询的SQL
     * @param cs    实体对象的类型。Class:表示所有类的类型
     * @param <T>   支持泛型
     * @return  实体对象
     */
    protected <T> T selectForEntity(String sql, Object[] params, Class<T> cs) {
        if (StringUtils.isEmpty(sql) || null == cs) {
            logger.error("******查询实体对象，参数错误");
            return null;
        }
        if (null == params || params.length == 0) {
            return selectForEntity(sql, cs);
        }
        List<Map<String, Object>> queryForList = jdbcTemplate.queryForList(sql, params);
        if (CollectionUtils.isEmpty(queryForList)) {
            return null;
        }
        Map<String, Object> map = queryForList.get(0);
        return MapToEntryConvertUtil.convert(map, cs, "");
    }

    /**
     * 获取对象列表
     * @param sql   执行语句
     * @param cs    描述实体对象的类的类的实例
     * @param <T>   泛型支持
     * @return  对象列表
     */
    protected <T> List<T> selectForEntityList(String sql, Class<T> cs) {
        return selectForEntityList(sql, null, cs);
    }

    /**
     * 获取对象列表
     * @param sql   执行语句
     * @param params   查询参数
     * @param cs    描述实体对象的类的类的实例
     * @param <T>   泛型支持
     * @return  对象列表
     */
    protected <T> List<T> selectForEntityList(String sql, Object[] params, Class<T> cs) {
        if (StringUtils.isEmpty(sql) || null == cs) {
            logger.error("******查询参数为空");
            return null;
        }
        List<Map<String, Object>> list;
        if (null == params || params.length == 0) {
            list = jdbcTemplate.queryForList(sql);
        } else {
            list = jdbcTemplate.queryForList(sql, params);
        }
        if (CollectionUtils.isEmpty(list)) {
            logger.info("------查询数据库为空");
            return new ArrayList<>();
        }
        List<T> result = new ArrayList<>();
        for (Map<String, Object> map : list) {
            result.add(MapToEntryConvertUtil.convert(map, cs, ""));
        }
        return result;
    }

    /**
     * 新增对象实体
     * @param sql   新增sql
     * @param entity    对象实体
     * @return  新增结果
     */
    protected Boolean insertEntity(String sql, final Object entity) {
        if (StringUtils.isEmpty(sql) || null == entity) {
            logger.error("******新增实体类，参数错误.sql = {}, entity = {}", sql, entity);
            return null;
        }
        CCJSqlParserManager ccjSqlParserManager = new CCJSqlParserManager();
        try {
            Statement statement = ccjSqlParserManager.parse(new StringReader(sql));
            if (statement instanceof Insert) {
                List<String> columnList = new ArrayList<>();
                List<String> valueList = new ArrayList<>();
                Insert insertStatement = (Insert) statement;
                // sql语句中新增的列
                List<Column> columns = insertStatement.getColumns();
                if (CollectionUtils.isEmpty(columns)) {
                    logger.error("******当前执行的sql = {}中，新增列为空", sql);
                    return false;
                }
                // sql语句中的占位符
                ItemsList itemsList = insertStatement.getItemsList();
                List<Expression> expressions = null;

                // 类型检测
                if (itemsList instanceof ExpressionList) {
                    ExpressionList expressionList = (ExpressionList) itemsList;
                    expressions = expressionList.getExpressions();
                }
                if (CollectionUtils.isNotEmpty(expressions)) {
                    for (Column column : columns) {
                        String columnName = column.getColumnName();
                        if (StringUtils.isEmpty(columnName)) {
                            logger.error("******当前执行的sql = {},获取列名称为空, column = {}", sql, column);
                            return false;
                        }
                        // 解决其它管理SQL编写工具中，自动添加上`的问题
                        if (columnName.contains("`")) {
                            columnName = columnName.replaceAll("`", "");
                        }
                        columnList.add(columnName);
                    }

                    for (Expression expression : expressions) {
                        valueList.add(expression.toString());
                    }
                }
                if (CollectionUtils.isEmpty(columnList) || CollectionUtils.isEmpty(valueList)) {
                    logger.error("******当前执行的sql = {},列名或者占位符为空", sql);
                    return false;
                }
                if (columnList.size() != valueList.size()) {
                    logger.error("******当前执行的sql = {},列名和占位符不匹配", sql);
                    return false;
                }
                return executeUpdate(columnList, valueList, sql, entity) > 0;
            } else {
                logger.error("******当前执行的sql = {},不是插入操作", sql);
                return false;
            }
        } catch (JSQLParserException jSqlPE) {
            logger.error("******解析sql抛出异常" + jSqlPE.getMessage(), jSqlPE);
            return false;
        } catch (Exception e) {
            logger.error("******执行数据库操作抛出异常" + e.getMessage(), e);
            return false;
        }
    }

    /**
     * 修改对象实体记录
     * @param sql   修改的sql
     * @param entity    修改之后的对象
     * @return  修改结果
     */
    protected Boolean updateEntity(String sql, final Object entity) {
        if (StringUtils.isEmpty(sql) || null == entity) {
            logger.error("******新增实体类，参数错误.sql = {}, entity = {}", sql, entity);
            return null;
        }
        CCJSqlParserManager ccjSqlParserManager = new CCJSqlParserManager();
        try {
            Statement statement = ccjSqlParserManager.parse(new StringReader(sql));
            if (statement instanceof Update) {
                Update updateStatement = (Update) statement;
                // 获取修改的列
                List<Column> columns = updateStatement.getColumns();
                if (CollectionUtils.isEmpty(columns)) {
                    logger.error("******需要修改的列为空。sql = {}", sql);
                    return false;
                }
                // 获取占位符
                List<Expression> expressions = updateStatement.getExpressions();
                if (CollectionUtils.isEmpty(expressions)) {
                    logger.error("******占位符为空.sql = {}", sql);
                    return false;
                }
                if (columns.size() != expressions.size()) {
                    logger.error("******列和占位符不匹配.sql = {}", sql);
                    return false;
                }
                // 获取修改条件
                Expression whereExpression = updateStatement.getWhere();
                if (whereExpression instanceof Parenthesis) {
                    whereFiledAndValue((BinaryExpression)((Parenthesis)whereExpression).getExpression(), columns, expressions);
                }
                if (whereExpression instanceof BinaryExpression) {
                    whereFiledAndValue((BinaryExpression)whereExpression, columns, expressions);
                }
                List<String> columnList = new ArrayList<>();
                List<String> expressionList = new ArrayList<>();
                for(Column c :columns){
                    columnList.add(c.getColumnName().replace("`",""));
                }
                for(Expression e :expressions){
                    expressionList.add(e.toString());
                }

                return executeUpdate(columnList, expressionList, sql, entity) > 0;
            } else {
                logger.error("******当前sql = {}，不是修改语句。", sql);
            }
        } catch (JSQLParserException jspe) {
            logger.error("******使用parseManager抛出异常" + jspe.getMessage(), jspe);
            return false;
        } catch (Exception e) {
            logger.error("******执行数据库操作，抛出异常" + e.getMessage(), e);
        }
        return null;
    }

    /**
     * 封装的分页查询对象
     * @param sql   查询语句
     * @param params    查询参数
     * @param pageNo    当前页码
     * @param pageSize  分页大小
     * @param cs    查询的类型
     * @param <T>   泛型支持
     * @return  分页数据对象
     */
    protected <T> PageData<T> selectForPage(String sql, Object[] params, int pageNo, int pageSize, Class<T> cs) {
        String countSql = "select count(1) as rowTotal from (" + sql + ") T";
        String dataBaseType = PropertiesUtil.getInstance().getValue(DataBaseConstant.DATA_BASE_TYPE);
        if (StringUtils.isEmpty(dataBaseType)) {
            dataBaseType = DataBaseConstant.MYSQL;
        }
        if (DataBaseConstant.SQLSERVER.equals(dataBaseType)) {
            countSql = handlerSqlServerOrderBy(countSql);
        }
        List<Map<String, Object>> countList = jdbcTemplate.queryForList(countSql, params);
        List<T> data = new ArrayList<>();
        if (CollectionUtils.isEmpty(countList)) {
            return new PageData<>(0, 1, 1, data);
        }
        Map<String, Object> countMap = countList.get(0);
        if (MapUtils.isEmpty(countMap)) {
            return new PageData<>(0, 1, 1, data);
        }
        int totalSize = MapUtils.getIntValue(countMap, "rowTotal", 0);
        if (totalSize < 1) {
            return new PageData<>(0, 1, 1, data);
        }

        // 合理化数据
        pageNo = (pageNo < 1) ? 1 : pageNo;
        int totalPage = (totalSize % pageSize == 0) ? (totalSize / pageSize) : ((totalSize / pageSize) + 1);
        totalPage = (totalPage < 1) ? 1 : totalPage;
        pageNo = (pageNo > totalPage) ? totalPage : pageNo;

        List<Map<String, Object>> resultList = selectForPage(sql, params, pageNo, pageSize);
        if (CollectionUtils.isEmpty(resultList)) {
            logger.error("******分页查询数据，出现不匹配问题。totalSize = {}, 但是查询数据列表为空", totalSize);
            return new PageData<>(totalSize, totalPage, 1, data);
        }
        for (Map<String, Object> map : resultList) {
            data.add(MapToEntryConvertUtil.convert(map, cs, ""));
        }
        return new PageData<>(totalSize, totalPage, pageNo, data);
    }

    /**
     * 处理SQLSERVER中的order by子句
     * @param countSql  聚合查询数据记录的行数
     * @return  处理之后的sql语句
     */
    private String handlerSqlServerOrderBy(String countSql) {
        String regexp = "order\\s*by\\s*[\\w]*(\\s*(desc|asc))?(,\\s*[\\w]*(\\s*(desc|asc))?)*";
        Pattern compile = Pattern.compile(regexp, Pattern.CASE_INSENSITIVE);
        Matcher matcher = compile.matcher(countSql);
        if (matcher.find()) {
            String group = matcher.group();
            String temp = " OFFSET 0 ROWS ";
            countSql = countSql.replace(group, group + temp);
        }
        return countSql;
    }

    /**
     * 具体查询分页数据
     * @param sql   查询sql
     * @param params    查询参数
     * @param pageNo    当前页码
     * @param pageSize  分页大小
     * @return  查询结果
     */
    private List<Map<String,Object>> selectForPage(String sql, Object[] params, int pageNo, int pageSize) {
        if (StringUtils.isEmpty(sql)) {
            logger.error("******查询分页数据，sql = {}", sql);
            return new ArrayList<>();
        }
        if (null == params) {
            params = new Object[0];
        }
        String dataBaseType = PropertiesUtil.getInstance().getValue(DataBaseConstant.DATA_BASE_TYPE);
        if (StringUtils.isEmpty(dataBaseType)) {
            dataBaseType = DataBaseConstant.MYSQL;
        }
        String pageSql;
        if (DataBaseConstant.MYSQL.equals(dataBaseType)) {
            int offset = (pageNo - 1) * pageSize;
            pageSql = sql + " limit ? offset ?";
            params = ArrayUtils.add(params, params.length, pageSize);
            params = ArrayUtils.add(params, params.length, offset);
            return jdbcTemplate.queryForList(pageSql, params);
        } else if (DataBaseConstant.ORACLE.equals(dataBaseType)) {
            if (pageNo <= 1) {
                // 没有偏移量
                pageSql = getOracleLimitString(sql, false);
                params = ArrayUtils.add(params, params.length, pageSize);
            } else {
                // 有偏移量
                pageSql = getOracleLimitString(sql, true);
                params = ArrayUtils.add(params, params.length, pageNo * pageSize);
                params = ArrayUtils.add(params, params.length, (pageNo - 1) * pageSize);
            }
            if (StringUtils.isEmpty(pageSql)) {
                return new ArrayList<>();
            } else {
                return jdbcTemplate.queryForList(pageSql, params);
            }
        } else if (DataBaseConstant.SQLSERVER.equals(dataBaseType)) {
            if (pageNo > 0 && pageSize > 0) {
                pageSql = fillSqlServerOrderBy(sql);
                pageSql += " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";
                ArrayUtils.add(params, params.length, (pageNo - 1) * pageSize);
                ArrayUtils.add(params, params.length, pageSize);
            } else {
                pageSql = sql;
            }
            return jdbcTemplate.queryForList(pageSql, params);
        } else {
            logger.error("******暂时不支持当前数据库类型。dataBaseName = {}", dataBaseType);
            return new ArrayList<>();
        }
    }

    /**
     * 填充完善SqlServer中的order by子句的分页查询
     * @param sql   完善之前SQL
     * @return  完善之后的SQL
     */
    private String fillSqlServerOrderBy(String sql) {
        String tempSql = sql + " end";
        String regexp = "order\\s*([\\w]*)\\s*[\\w]*(\\s*(desc|asc))?(,\\s*[\\w]*(\\s*(desc|asc))?)*\\s*end$";
        Pattern compile = Pattern.compile(regexp);
        Matcher matcher = compile.matcher(tempSql.toLowerCase());
        if (!matcher.find()) {
            sql += " order by 1 ";
        }
        return sql;
    }

    /**
     * 获取oracle数据库的分页查询语句
     * @param sql   原始语句
     * @param hasOffsetFlag 是否有偏移量的标志
     * @return  封装之后的完整查询语句
     */
    private String getOracleLimitString(String sql, boolean hasOffsetFlag) {
        if (StringUtils.isEmpty(sql)) {
            logger.error("******获取oracle数据库的分页查询语句。sql = {}", sql);
            return null;
        }
        sql = sql.trim();
        boolean isForUpdate = false;
        String forUpdate = " for update";
        if (sql.toLowerCase().endsWith(forUpdate)) {
            sql = sql.substring(0, sql.length() - 11);
            isForUpdate = true;
        }
        StringBuilder pageSql = new StringBuilder(sql.length() + 100);
        if (hasOffsetFlag) {
            pageSql.append(" select * from ( select row_data.*, rownum start_row_num from ( ").append(sql)
                    .append(" ) row_data where rownum <= ? ) where start_row_num > ? ");
        } else {
            pageSql.append(" select * from ( ").append(sql)
                    .append(" ) where rownum <= ? ");
        }
        if (isForUpdate) {
            pageSql.append(forUpdate);
        }
        return pageSql.toString();
    }

    private void whereFiledAndValue(BinaryExpression binaryExpression, List<Column> columns, List<Expression> expressions){
        Expression right=binaryExpression.getRightExpression();
        Expression left=binaryExpression.getLeftExpression();

        if(left instanceof BinaryExpression){
            whereFiledAndValue((BinaryExpression) left, columns, expressions);
        } else if(left instanceof Parenthesis){
            whereFiledAndValue((BinaryExpression)((Parenthesis) left).getExpression(),columns,expressions);
        } else if(left instanceof Column){
            columns.add((Column)left);
            expressions.add(right);
        }

        if(right instanceof BinaryExpression){
            whereFiledAndValue((BinaryExpression)right,columns,expressions);
        } else if(right instanceof Parenthesis){
            whereFiledAndValue((BinaryExpression)((Parenthesis) right).getExpression(),columns,expressions);
        }
    }

    /**
     * 向数据库执行操作
     * @param columnList    列集合
     * @param valueList 列对应的值集合
     * @return  操作记录数
     * @throws Exception 持久层异常
     */
    private int executeUpdate(List<String> columnList, List<String> valueList, String sql, final Object entity) throws Exception {
        boolean expectFlag = StringUtils.isEmpty(sql) || null == entity || CollectionUtils.isEmpty(columnList) || CollectionUtils.isEmpty(valueList) || columnList.size() != valueList.size();
        if (expectFlag) {
            logger.error("******参数错误");
            throw new Exception("向数据库执行操作，参数错误");
        }
        String[] columnArray = columnList.toArray(new String[columnList.size()]);
        String[] valueArray = valueList.toArray(new String[valueList.size()]);
        int columnLength = columnArray.length;
        int valueLength = valueArray.length;
        if (columnLength == valueLength) {
            final Map<String, String> columnGetMethodMap = new HashMap<>(columnLength);
            Field[] beanFieldArray = getBeanFieldArray(entity.getClass(), null);
            if (null == beanFieldArray || beanFieldArray.length == 0) {
                String className = entity.getClass().getName();
                logger.error("******实体类中字段为空，className = {}", className);
                throw new Exception("实体类中字段为空，className = " + className);
            }
            for (Field field : beanFieldArray) {
                String fieldName = field.getName();
                String resultName = fieldName;
                CustomColumn customColumn = field.getAnnotation(CustomColumn.class);
                if (null != customColumn) {
                    resultName = customColumn.name();
                }
                columnGetMethodMap.put(resultName.toLowerCase(), FIELD_GET_METHOD_PREFIX + StringUtil.firstCharToUpperCase(fieldName));
            }
            return jdbcTemplate.update(sql, (ps) -> {
                int parameterIndex = 1;
                for (int i = 0; i < valueLength; i++) {
                    if (PLACEHOLDER.equals(valueArray[i])) {
                        String columnName = columnArray[i].toLowerCase();
                        if (columnGetMethodMap.containsKey(columnName)) {
                            String getMethodName = MapUtils.getString(columnGetMethodMap, columnName);
                            if (StringUtils.isEmpty(getMethodName)) {
                                throw new SQLException("未找到get方法");
                            }
                            try {
                                Method getMethod = entity.getClass().getMethod(getMethodName);
                                if (null != getMethod) {
                                    Object value = getMethod.invoke(entity);
                                    Class<?> returnType = getMethod.getReturnType();
                                    if (null == value) {
                                        ps.setNull(parameterIndex, Types.CHAR);
                                    } else if (returnType == Date.class){
                                        ps.setTimestamp(parameterIndex, new Timestamp(((Date) value).getTime()));
                                    } else if (returnType == Boolean.class || returnType == boolean.class) {
                                        ps.setInt(parameterIndex, ((Boolean)value ? 1 : 0));
                                    } else {
                                        ps.setObject(parameterIndex, value);
                                    }
                                }
                            } catch (Exception e) {
                                logger.error("******" + e.getMessage(), e);
                                throw new SQLException("执行get方法抛出异常" + columnArray[i]);
                            }
                        } else {
                            logger.error("column = {}无法找到get方法", columnArray[i]);
                            throw new SQLException("column = {}无法找到get方法");
                        }
                        parameterIndex++;
                    } else {
                        logger.error("column = {}使用的是默认值 value = {}", columnArray[i], valueArray[i]);
                    }

                }
            });
        } else {
            logger.error("******向数据库执行操作的columnLength = {}, valueLength = {},列名和占位符不匹配", columnLength, valueLength);
            throw new Exception("向数据库执行操作的sql = {" + sql + "},列名和占位符不匹配");
        }

    }

    /**
     * 获取类实体对象的所有字段，包含父类中声明的字段
     * @param cs    实体类的Class对象
     * @param fieldArray    字段数组
     * @return  所有字段的字段数组
     */
    private Field[] getBeanFieldArray(Class<?> cs, Field[] fieldArray) {
        // 获取当前类中声明的字段属性，不包含父类中定义的属性
        fieldArray = ArrayUtils.addAll(fieldArray, cs.getDeclaredFields());
        if (null != cs.getSuperclass()) {
            Class<?> superclass = cs.getSuperclass();
            fieldArray = getBeanFieldArray(superclass, fieldArray);
        }
        return fieldArray;
    }
}
