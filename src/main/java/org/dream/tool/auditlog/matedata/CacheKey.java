package org.dream.tool.auditlog.matedata;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 缓存key
 * 方法全名称:参数类型名称
 *
 * @author hukangning
 */
public class CacheKey {

	//全限定名称
	protected String className;
	//方法名称
	protected String methodName;
	//参数类型名称
	protected String paramTypes;

	public CacheKey(Method method , Class clazz) {
		parseCode(method , clazz);
	}

	/**
	 * 解析为code
	 */
	protected void parseCode(Method method , Class clazz){
		this.methodName = method.toGenericString();
		this.paramTypes = Arrays.asList(method.getParameterTypes()).stream().map(Class::getTypeName).collect(Collectors.joining());
		this.className = clazz.getTypeName();
	}


	@Override
	public int hashCode() {
		return Objects.hash(this.className , methodName , paramTypes);
	}
}