package org.dream.tool.auditlog.core;

import org.dream.tool.auditlog.core.matedata.AuditLogInfoWrapper;

/**
*   
* @author : KangNing Hu
*/
@FunctionalInterface
public interface Pipeline {

	void doConsume(AuditLogInfoWrapper auditLogInfoWrapper);
}
