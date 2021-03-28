package org.dream.tool.auditlog;

import org.dream.tool.auditlog.matedata.AuditLogInfo;
import org.dream.tool.auditlog.matedata.AuditLogInfoWrapper;

import java.util.List;

/**
*   
* @author : KangNing Hu
*/
@FunctionalInterface
public interface Pipeline {

	void doConsume(AuditLogInfoWrapper auditLogInfoWrapper);
}
