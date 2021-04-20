package org.dream.tool.auditlog.core;

import org.dream.tool.auditlog.core.matedata.AuditLogInfoWrapper;
import org.dream.tool.auditlog.core.matedata.AuditLogInfo;
import org.dream.tool.auditlog.core.matedata.DefaultAuditLogInfo;

import java.util.UUID;

/**
* 默认的日志生成器  
* @author : KangNing Hu
*/
public class DefaultLogProducer implements LogProducer {


	@Override
	public AuditLogInfoWrapper doProduce(CurrentContext currentContext) {
		//获取日志消息
		AttributeAccess attributeAccess = currentContext.getAttributeAccess();
		String message = (String) attributeAccess.get(AuditLogInfo.MASSAGE_ATTR);
		//创建日志结构对象
		DefaultAuditLogInfo defaultAuditLogInfo = new DefaultAuditLogInfo();
		defaultAuditLogInfo.setId(UUID.randomUUID());
		defaultAuditLogInfo.setMessage(message);
		defaultAuditLogInfo.setOperationTime(System.currentTimeMillis());
		return AuditLogInfoWrapper.ofOne(defaultAuditLogInfo);
	}


}
