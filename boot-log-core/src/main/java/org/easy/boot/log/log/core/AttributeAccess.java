package org.easy.boot.log.log.core;

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
	public <T> T getAttribute(String key){
		return (T) this.get(key);
	}

	/**
	 * 设置属性
	 * @param name 属性名称
	 * @param value 属性值
	 */
	public void setAttribute(String name , Object value){
		this.put(name  , value);
	}
}
