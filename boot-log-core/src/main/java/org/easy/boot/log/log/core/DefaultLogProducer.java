package org.easy.boot.log.log.core;

import org.easy.boot.log.log.core.matedata.LogInfoWrapper;
import org.easy.boot.log.log.core.matedata.LogInfo;
import org.easy.boot.log.log.core.matedata.DefaultLogInfo;

import java.util.UUID;

/**
* 默认的日志生成器  
* @author : KangNing Hu
*/
public class DefaultLogProducer implements LogProducer {


	@Override
	public LogInfoWrapper doProduce(CurrentContext currentContext) {
		//获取日志消息
		AttributeAccess attributeAccess = currentContext.getAttributeAccess();
		String message = (String) attributeAccess.get(LogInfo.MASSAGE_ATTR);
		//创建日志结构对象
		DefaultLogInfo defaultAuditLogInfo = new DefaultLogInfo();
		defaultAuditLogInfo.setId(UUID.randomUUID());
		defaultAuditLogInfo.setMessage(message);
		defaultAuditLogInfo.setOperationTime(System.currentTimeMillis());
		return LogInfoWrapper.ofOne(defaultAuditLogInfo);
	}


}
