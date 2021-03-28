package org.dream.tool.auditlog.matedata;


import org.dream.tool.auditlog.annotation.Param;
import org.dream.tool.auditlog.annotation.Search;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字段节点
 */
public class ParamNode {
	//当前字段对象
	private Field field;
	/**
	 * 参数名称
	 */
	private String paramName;

	/**
	 * 搜索的子级列表
	 */
	private List<ParamNode> searchParamNodes;


	public ParamNode(){

	}

	public ParamNode(String paramName ){
		this.paramName = paramName;
	}


	public void setField(Field field) {
		this.field = field;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public void setSearchParamNodes(List<ParamNode> searchParamNodes) {
		this.searchParamNodes = searchParamNodes;
	}

	/**
	 * 构建 字段节点
	 * @param field
	 * @return
	 */
	public static ParamNode build(Field field) {
		ParamNode paramNode = new ParamNode();
		Param param = AnnotationUtils.findAnnotation(field, Param.class);
		if (param != null){
			paramNode.setField(field);
			paramNode.setParamName(StringUtils.isEmpty(param.value()) ? field.getName() : param.value());
			paramNode.setSearchParamNodes(null);
		}else if (field.isAnnotationPresent(Search.class)){
			paramNode.setField(field);
			paramNode.setParamName(null);
			paramNode.setSearchParamNodes(findChildNodes(field));
		}
		return paramNode;
	}

	/**
	 * 查询下级节点列表
	 * @param field
	 * @return
	 */
	private static List<ParamNode> findChildNodes(Field field) {
		List<ParamNode> paramNodes = new ArrayList<>();
		Class<?> type = field.getType();
		do {
			ReflectionUtils.doWithFields(type, f -> {
				Param param = AnnotationUtils.findAnnotation(f, Param.class);
				ParamNode paramNode = new ParamNode();
				if (param != null) {
					paramNode.setField(f);
					paramNode.setParamName(StringUtils.isEmpty(param.value()) ? f.getName() : param.value());
					paramNode.setSearchParamNodes(null);
				} else {
					paramNode.setField(f);
					paramNode.setParamName(null);
					paramNode.setSearchParamNodes(findChildNodes(f));
				}
				paramNodes.add(paramNode);
			}, f -> f.isAnnotationPresent(Param.class) || f.isAnnotationPresent(Search.class));
			type = type.getSuperclass().isAssignableFrom(Object.class) ? null : type.getSuperclass();
		}while (type != null);
		return paramNodes;
	}


	/**
	 * 内省调用 get方法
	 *
	 * @param target 目标对象
	 */
	public Map<String , Object> parseParam(Object target) {

		//创建容器
		Map<String , Object> param = new HashMap<>();
		//解析参数
		this.field.setAccessible(true);
		//如果 没有搜索节点 则只需要处理当前对象
		Object value = ReflectionUtils.getField(this.field, target);
		if (CollectionUtils.isEmpty(this.searchParamNodes)){
			param.put(this.paramName , value);
		}
		// 如果需要进行搜索 则遍历下级节点
		else {
			for (ParamNode paramNode : this.searchParamNodes){
				param.putAll(paramNode.parseParam(value));
			}
		}
		return param;
	}


}