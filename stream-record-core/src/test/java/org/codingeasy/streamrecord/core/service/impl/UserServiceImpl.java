package org.codingeasy.streamrecord.core.service.impl;

import org.codingeasy.streamrecord.core.annotation.Record;
import org.codingeasy.streamrecord.core.annotation.RecordService;
import org.codingeasy.streamrecord.core.annotation.Search;
import org.codingeasy.streamrecord.core.model.User;
import org.codingeasy.streamrecord.core.service.UserService;

/**
 * @author : KangNing Hu
 */
@RecordService
public class UserServiceImpl implements UserService {


  @Record("'这是一个简单的日志记录，用户的名称是：' + #user.name")
  @Override
  public void simple(@Search User user) {

  }
}
