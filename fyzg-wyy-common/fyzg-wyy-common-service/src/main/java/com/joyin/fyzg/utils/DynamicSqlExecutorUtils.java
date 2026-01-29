package com.joyin.fyzg.utils;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.google.common.collect.Maps;
import com.joyin.fyzg.common.PageDataMap;
import com.joyin.fyzg.common.exception.SqlExecutionException;
import com.joyin.fyzg.enums.SqlExecutionExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * TpAppServiceImpl(功能列表解析器应用)
 * <br/>
 *
 * @author pengzhen
 * @date 2019/10/29 0029 下午 3:55
 */
@Component
@Slf4j
public class DynamicSqlExecutorUtils {

	@Autowired
	SqlSession sqlSession;

	public Map findOneData(String sql, Map<String, Object> parameterMap) {
		try {
			String method = "hash" + sql.hashCode();
			String namespace = MappedStatement.NAMESPACE + ".findOneData";
			String statementId = namespace + "." + method;
			log.info("SQL执行语句： ========》" + sql + "【"+ JSON.toJSONString(parameterMap)+"】");
			MappedStatement statement = new MappedStatement(sql, SqlCommandType.SELECT, method, namespace, null,false);
			statement.inject(new MapperBuilderAssistant(sqlSession.getConfiguration(), String.class.getName()), String.class, String.class, TableInfoHelper.getTableInfo(HashMap.class));
			Map oneMap = sqlSession.selectOne(statementId, parameterMap);
			if(oneMap == null ){
				return oneMap;
			}
			Map upperKeyMap = Maps.newHashMap();
			oneMap.forEach((k, v) -> {
				upperKeyMap.put(StringUtils.upperCase(k + ""), v);
			});
			return upperKeyMap;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SqlExecutionException(SqlExecutionExceptionEnum.SQL_EXECUTE_FAIL.format(sql, e), e);
		}
	}

	public <K> K findOneData(String sql, Map<String, Object> parameterMap,String resultMap,Class<K> resultClass) {
		try {
			String method = "hash" + sql.hashCode();
			String namespace = MappedStatement.NAMESPACE + ".findOneDataWithParse";
			String statementId = namespace + "." + method;
			MappedStatement statement = new MappedStatement(sql, SqlCommandType.SELECT, method, namespace, resultMap,false);
			statement.inject(new MapperBuilderAssistant(sqlSession.getConfiguration(), String.class.getName()), String.class, resultClass, TableInfoHelper.getTableInfo(HashMap.class));

			return sqlSession.selectOne(statementId, parameterMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SqlExecutionException(SqlExecutionExceptionEnum.SQL_EXECUTE_FAIL.format(sql, e), e);
		}
	}

	public Map findOneDataWithParse(String sql, Map<String, Object> parameterMap) {
		try {
			String method = "hash" + sql.hashCode();
			String namespace = MappedStatement.NAMESPACE + ".findOneDataWithParse";
			String statementId = namespace + "." + method;
			MappedStatement statement = new MappedStatement(sql, SqlCommandType.SELECT, method, namespace, null,true);
			statement.inject(new MapperBuilderAssistant(sqlSession.getConfiguration(), String.class.getName()), String.class, String.class, TableInfoHelper.getTableInfo(HashMap.class));
			Map oneMap = sqlSession.selectOne(statementId, parameterMap);
			if(oneMap == null ){
				return oneMap;
			}
			Map upperKeyMap = Maps.newHashMap();
			oneMap.forEach((k, v) -> {
				upperKeyMap.put(StringUtils.upperCase(k + ""), v);
			});
			return upperKeyMap;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SqlExecutionException(SqlExecutionExceptionEnum.SQL_EXECUTE_FAIL.format(sql, e), e);
		}
	}

	public <K> K findOneDataWithParse(String sql, Map<String, Object> parameterMap,String resultMap,Class<K> resultClass) {
		try {
			String method = "hash" + sql.hashCode();
			String namespace = MappedStatement.NAMESPACE + ".findOneDataWithParse";
			String statementId = namespace + "." + method;
			MappedStatement statement = new MappedStatement(sql, SqlCommandType.SELECT, method, namespace, resultMap,true);
			statement.inject(new MapperBuilderAssistant(sqlSession.getConfiguration(), String.class.getName()), String.class, resultClass, TableInfoHelper.getTableInfo(HashMap.class));

			return sqlSession.selectOne(statementId, parameterMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SqlExecutionException(SqlExecutionExceptionEnum.SQL_EXECUTE_FAIL.format(sql, e), e);
		}
	}

	public List<Map> findListData(String sql, Map<String, Object> parameterMap) {
		try {
			String method = "hash" + sql.hashCode();
			String namespace = MappedStatement.NAMESPACE + ".findListData";
			String statementId = namespace + "." + method;
			MappedStatement statement = new MappedStatement(sql, SqlCommandType.SELECT, method, namespace, null,false);
			statement.inject(new MapperBuilderAssistant(sqlSession.getConfiguration(), String.class.getName()), String.class, String.class, TableInfoHelper.getTableInfo(HashMap.class));
			List<Map> mapList = sqlSession.selectList(statementId, parameterMap);
			return mapList.stream().map(map -> {
				Map upperKeyMap = Maps.newHashMap();
				map = Optional.ofNullable(map).orElse(Maps.newHashMap());
				map.forEach((k, v) -> {
					upperKeyMap.put(StringUtils.upperCase(k + ""), v);
				});
				return upperKeyMap;
			}).collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
			throw new SqlExecutionException(SqlExecutionExceptionEnum.SQL_EXECUTE_FAIL.format(sql, e), e);
		}
	}

	public <K> List<K> findListData(String sql, Map<String, Object> parameterMap,String resultMap,Class<K> resultClass) {
		try {
			String method = "hash" + sql.hashCode();
			String namespace = MappedStatement.NAMESPACE + ".findListDataWithParse";
			String statementId = namespace + "." + method;
			MappedStatement statement = new MappedStatement(sql, SqlCommandType.SELECT, method, namespace, resultMap,false);
			statement.inject(new MapperBuilderAssistant(sqlSession.getConfiguration(), String.class.getName()), String.class, resultClass, TableInfoHelper.getTableInfo(HashMap.class));

			return sqlSession.selectList(statementId, parameterMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SqlExecutionException(SqlExecutionExceptionEnum.SQL_EXECUTE_FAIL.format(sql, e), e);
		}
	}

	public List<Map> findListDataWithParse(String sql, Map<String, Object> parameterMap) {
		try {
			String method = "hash" + sql.hashCode();
			String namespace = MappedStatement.NAMESPACE + ".findListDataWithParse";
			String statementId = namespace + "." + method;
			MappedStatement statement = new MappedStatement(sql, SqlCommandType.SELECT, method, namespace, null,true);
			statement.inject(new MapperBuilderAssistant(sqlSession.getConfiguration(), String.class.getName()), String.class, String.class, TableInfoHelper.getTableInfo(HashMap.class));
			log.info("SQL执行语句： ========》" + sql + "【"+ JSON.toJSONString(parameterMap)+"】");

			List<Map> mapList = sqlSession.selectList(statementId, parameterMap);
			log.info("结果返回： {}", JSON.toJSONString(mapList));
			if (mapList == null){
				return new ArrayList<>();
			}
			return mapList.stream().filter(Objects::nonNull).map(map -> {
				Map upperKeyMap = Maps.newHashMap();
				map.forEach((k, v) -> {
					upperKeyMap.put(StringUtils.upperCase(k + ""), v);
				});
				return upperKeyMap;
			}).collect(Collectors.toList());
		} catch (Exception e) {
			log.error("error", e);
			throw new SqlExecutionException(SqlExecutionExceptionEnum.SQL_EXECUTE_FAIL.format(sql, e), e);
		}
	}

	public <K> List<K> findListDataWithParse(String sql, Map<String, Object> parameterMap,String resultMap,Class<K> resultClass) {
		try {
			String method = "hash" + sql.hashCode();
			String namespace = MappedStatement.NAMESPACE + ".findListDataWithParse";
			String statementId = namespace + "." + method;
			MappedStatement statement = new MappedStatement(sql, SqlCommandType.SELECT, method, namespace, resultMap,true);
			statement.inject(new MapperBuilderAssistant(sqlSession.getConfiguration(), String.class.getName()), String.class, resultClass, TableInfoHelper.getTableInfo(HashMap.class));

			return sqlSession.selectList(statementId, parameterMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SqlExecutionException(SqlExecutionExceptionEnum.SQL_EXECUTE_FAIL.format(sql, e), e);
		}
	}

	public Integer insert(String sql, Map<String, Object> parameterMap) {
		int count = 0;
		try {
			String method = "hash" + sql.hashCode();
			String namespace = MappedStatement.NAMESPACE + ".insert";
			String statementId = namespace + "." + method;
			MappedStatement statement = new MappedStatement(sql, SqlCommandType.INSERT, method, namespace, null,false);
			statement.inject(new MapperBuilderAssistant(sqlSession.getConfiguration(), String.class.getName()), String.class, String.class, TableInfoHelper.getTableInfo(HashMap.class));
			count =sqlSession.insert(statementId, parameterMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SqlExecutionException(SqlExecutionExceptionEnum.SQL_EXECUTE_FAIL.format(sql, e), e);
		}
		return count;
	}

	public int update(String sql, Map<String, Object> parameterMap) {
		int num = 0;
		try {
			String method = "hash" + sql.hashCode();
			String namespace = MappedStatement.NAMESPACE + ".update";
			String statementId = namespace + "." + method;
			MappedStatement statement = new MappedStatement(sql, SqlCommandType.UPDATE, method, namespace, null, true);
			statement.inject(new MapperBuilderAssistant(sqlSession.getConfiguration(), String.class.getName()), String.class, String.class, TableInfoHelper.getTableInfo(HashMap.class));
			log.info("SQL执行语句update： ========》" + sql + "【" + JSON.toJSONString(parameterMap) + "】");
			num = sqlSession.update(statementId, parameterMap);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new SqlExecutionException(SqlExecutionExceptionEnum.SQL_EXECUTE_FAIL.format(sql, e), e);
		}
		return num;
	}


	public void delete(String sql, Map<String, Object> parameterMap) {
		try {
			String method = "hash" + sql.hashCode();
			String namespace = MappedStatement.NAMESPACE + ".delete";
			String statementId = namespace + "." + method;
			MappedStatement statement = new MappedStatement(sql, SqlCommandType.DELETE, method, namespace, null,false);
			statement.inject(new MapperBuilderAssistant(sqlSession.getConfiguration(), String.class.getName()), String.class, String.class, TableInfoHelper.getTableInfo(HashMap.class));
			sqlSession.delete(statementId, parameterMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SqlExecutionException(SqlExecutionExceptionEnum.SQL_EXECUTE_FAIL.format(sql, e), e);
		}
	}


	public void deleteWithParse(String sql, Map<String, Object> parameterMap) {
		try {
			String method = "hash" + sql.hashCode();
			String namespace = MappedStatement.NAMESPACE + ".deleteWithParse";
			String statementId = namespace + "." + method;
			MappedStatement statement = new MappedStatement(sql, SqlCommandType.DELETE, method, namespace, null,true);
			statement.inject(new MapperBuilderAssistant(sqlSession.getConfiguration(), String.class.getName()), String.class, String.class, TableInfoHelper.getTableInfo(HashMap.class));
			sqlSession.delete(statementId, parameterMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SqlExecutionException(SqlExecutionExceptionEnum.SQL_EXECUTE_FAIL.format(sql, e), e);
		}
	}

	/**
	 * 删除全部
	 *
	 * @author K 2019-7-9
	 */
	public static class MappedStatement extends AbstractMethod {
		public static final String ID_PREFIX = "MlExecute";
		public static final String NAMESPACE = "MlNamespace";
		private String sql;
		private String id;
		private String namespace;
		private String resultMap=null;
		private SqlCommandType sqlCommandType;
		private Boolean parse;

		public MappedStatement(String sql, SqlCommandType sqlCommandType, String id, String namespace, String resultMap, Boolean parse) {
			this.sql = sql;
			this.sqlCommandType = sqlCommandType;
			this.id = id;
			this.namespace = namespace;
			this.resultMap = resultMap;
			this.parse = parse;
		}

		@Override
		public org.apache.ibatis.mapping.MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {

			try {
				Field mappedStatementsField = Configuration.class.getDeclaredField("mappedStatements");
				mappedStatementsField.setAccessible(true);
				Map<String, org.apache.ibatis.mapping.MappedStatement> mappedStatements = (Map<String, org.apache.ibatis.mapping.MappedStatement>) mappedStatementsField.get(super.configuration);
				if(!mappedStatements.containsKey(this.namespace+"."+id)) {
					super.builderAssistant.setCurrentNamespace(this.namespace);
					SqlSource sqlSource = getSqlSource();
					org.apache.ibatis.mapping.MappedStatement mappedStatement = null;
					switch (this.sqlCommandType) {
					case SELECT:
						mappedStatement = addMappedStatement(mapperClass, id, sqlSource, SqlCommandType.SELECT, null,
								resultMap, HashMap.class, new NoKeyGenerator(), null, null);
						break;
					case INSERT:
						mappedStatement = addMappedStatement(mapperClass, id, sqlSource, SqlCommandType.INSERT, PageDataMap.class, null,
								Integer.class, new NoKeyGenerator(), null, null);
						break;
					case UPDATE:
						mappedStatement = addMappedStatement(mapperClass, id, sqlSource, SqlCommandType.UPDATE, PageDataMap.class, null,
								Integer.class, new NoKeyGenerator(), null, null);
						break;
					case DELETE:
						mappedStatement = addMappedStatement(mapperClass, id, sqlSource, SqlCommandType.DELETE, PageDataMap.class,
								null, Integer.class, new NoKeyGenerator(), null, null);
						break;
					}
					mappedStatements.remove(mappedStatement.getId());
					mappedStatements.put(mappedStatement.getId(), mappedStatement);
				}
			}
			catch (Exception e) {
                log.error("DynamicSqlExecutorUtils.injectMappedStatement error", e);
                throw new SqlExecutionException(SqlExecutionExceptionEnum.SQL_BUILD_FAIL.format(sql, e), e);
			}
			return null;
		}

		private SqlSource getSqlSource() {
			if (this.parse) {
				this.sql = "<script>" + this.sql + "</script>";
				XPathParser parser = new XPathParser(sql, false, super.configuration.getVariables(), new XMLMapperEntityResolver());
				return super.languageDriver.createSqlSource(super.configuration, parser.evalNode("/script"), HashMap.class);
			}
			else {
				return super.languageDriver.createSqlSource(super.configuration, this.sql, PageDataMap.class);
			}
		}
	}
}
