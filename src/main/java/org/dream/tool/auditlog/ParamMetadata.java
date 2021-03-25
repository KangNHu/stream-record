package org.dream.tool.auditlog;


import java.lang.reflect.Field;

/**
* 参数元信息
* @author : KangNing Hu
*/
public class ParamMetadata {

	/**
	 * 参数索引
	 * 如果不是 get(String name , String id) 形式的参数则为-1
	 */
	private int index = -1;

	/**
	 * 参数字段对象
	 * 如果不是 <code>
	 *     class A{
	 *         private id;
	 *         private name
	 *     }
	 * </code>形式的参数则为空
	 */
	private Field field;


}
