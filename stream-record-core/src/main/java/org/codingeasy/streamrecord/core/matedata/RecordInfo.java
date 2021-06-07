package org.codingeasy.streamrecord.core.matedata;

import java.io.Serializable;

/**
* 记录信息
* @author : KangNing Hu
*/
public interface RecordInfo extends Serializable {

	// 记录信息属性名称
	String MASSAGE_ATTR = "massage";


	/**
	 * 获取唯一标标识别
	 * @return
	 */
	Object getId();

	/**
	 * 设置唯一标识
	 * @param id
	 */
	void setId(Object id);

	/**
	 * 获取操作时间 ，记录的生成时间
	 * @return
	 */
	Long getOperationTime();

	/**
	 * 设置操作时间 ，记录的生成时间
	 * @param time
	 */
	void setOperationTime(Long time);

	/**
	 * 获取记录消息
	 * @return
	 */
	String getMessage();

	/**
	 * 设置记录信息
	 * @param message
	 */
	void setMessage(String message);
}
