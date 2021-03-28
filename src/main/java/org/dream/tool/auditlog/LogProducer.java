package org.dream.tool.auditlog;

import org.dream.tool.auditlog.matedata.AuditLogInfo;
import org.dream.tool.auditlog.matedata.AuditLogInfoWrapper;

import java.util.List;

/**
* 日志生成器  
* @author : KangNing Hu
*/
@FunctionalInterface
public interface LogProducer {


	AuditLogInfoWrapper doProduce(CurrentContext currentContext);


}
