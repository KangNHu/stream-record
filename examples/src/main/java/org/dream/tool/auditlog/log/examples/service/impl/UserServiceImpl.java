package org.dream.tool.auditlog.log.examples.service.impl;

import org.dream.tool.auditlog.configuration.AuditLogService;
import org.dream.tool.auditlog.core.annotation.AuditLog;
import org.dream.tool.auditlog.core.annotation.Search;
import org.dream.tool.auditlog.log.examples.pojo.User;
import org.dream.tool.auditlog.log.examples.service.UserService;

/**
*   
* @author : KangNing Hu
*/
@AuditLogService
public class UserServiceImpl implements UserService {




	@AuditLog("'这是一个简单的日志记录，用户的名称是：' + #user.name")
	@Override
	public void simple(@Search User user) {

	}
}
