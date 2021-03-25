package org.dream.tool.auditlog;

import java.lang.reflect.Method;

/**
*   
* @author : KangNing Hu
*/
public class LogDefinitionBuilder {

	private Method method;


	public LogDefinitionBuilder(Method method){
		this.method = method;
	}


	/**
	 * 获取一般的log定义
	 * @return
	 */
	public LogDefinition getGenericLogDefinition(){
		return null;
	}

}
