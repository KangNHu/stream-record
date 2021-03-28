package org.dream.tool.auditlog.matedata;



/**
* 审计信息包装类  
* @author : KangNing Hu
*/
public class AuditLogInfoWrapper {

	// 单个审计信息
	private AuditLogInfo auditLogInfo;

	//多个审计信息
	private AuditLogInfo[] auditLogInfos;

	private AuditLogInfoWrapper(AuditLogInfo auditLogInfo){
		this.auditLogInfo = auditLogInfo;
	}


	private AuditLogInfoWrapper(AuditLogInfo... auditLogInfos){
		this.auditLogInfos = auditLogInfos;
	}

	/**
	 * 构建单个数据
	 * @param auditLogInfo 审计信息对象
	 * @return
	 */
	public static AuditLogInfoWrapper ofOne(AuditLogInfo auditLogInfo){
		return new AuditLogInfoWrapper(auditLogInfo);
	}


	/**
	 * 构建多个数据
	 * @param auditLogInfos 审计日志信息
	 * @return
	 */
	public static AuditLogInfoWrapper ofMultiple(AuditLogInfo... auditLogInfos){
		return new AuditLogInfoWrapper(auditLogInfos);
	}

	/**
	 * 是否为多个数据
	 * @return
	 */
	public boolean isMultiple(){
		if (this.auditLogInfo == null){
			return true;
		}
		return false;
	}


	public AuditLogInfo getAuditLogInfo(){
		return this.auditLogInfo;
	}


	public AuditLogInfo[] getAuditLogInfos(){
		return this.auditLogInfos;
	}
}
