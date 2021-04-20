package org.dream.tool.auditlog.core;

import org.dream.tool.auditlog.core.matedata.AuditLogInfoWrapper;

/**
* 日志生成器  
* @author : KangNing Hu
*/
@FunctionalInterface
public interface LogProducer {


	AuditLogInfoWrapper doProduce(CurrentContext currentContext);


}
