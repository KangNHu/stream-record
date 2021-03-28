package org.dream.tool.auditlog.matedata;

import java.io.Serializable;

/**
* 审计信息  
* @author : KangNing Hu
*/
public interface AuditLogInfo  extends Serializable {

	// 日志信息属性名称
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
	 * 获取操作时间 ，日志的生成时间
	 * @return
	 */
	Long getOperationTime();

	/**
	 * 设置操作时间 ，日志的生成时间
	 * @param time
	 */
	void setOperationTime(Long time);

	/**
	 * 获取日志消息
	 * @return
	 */
	String getMessage();

	/**
	 * 设置日志信息
	 * @param message
	 */
	void setMessage(String message);
}
