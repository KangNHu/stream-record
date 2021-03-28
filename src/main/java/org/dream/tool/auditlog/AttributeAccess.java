package org.dream.tool.auditlog;

import java.util.HashMap;

/**
* 参数属性  
* @author : KangNing Hu
*/
public class AttributeAccess extends HashMap<String , Object> {

	/**
	 * 解决类型转换 object -> 实际类型
	 * @param key
	 * @param <T>
	 * @return
	 */
	public <T> T get(String key){
		return this.get(key);
	}
}
