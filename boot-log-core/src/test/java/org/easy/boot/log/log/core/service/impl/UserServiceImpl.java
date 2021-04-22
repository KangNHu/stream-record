package org.easy.boot.log.log.core.service.impl;

import org.easy.boot.log.log.core.annotation.Log;
import org.easy.boot.log.log.core.annotation.LogService;
import org.easy.boot.log.log.core.annotation.Search;
import org.easy.boot.log.log.core.model.User;
import org.easy.boot.log.log.core.service.UserService;

/**
*   
* @author : KangNing Hu
*/
@LogService
public class UserServiceImpl implements UserService {




	@Log("'这是一个简单的日志记录，用户的名称是：' + #user.name")
	@Override
	public void simple(@Search User user) {

	}
}
