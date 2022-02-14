package org.codingeasy.streamrecord.core.matedata;

/**
* 通知类型  
* @author : KangNing Hu
*/
public enum  Advice {

	//前置
	BEFORE,
	//后置
	AFTER,
	//后置异常处理
	AFTER_EXCEPTION,
	//异常
	EXCEPTION,
	//无意义
	NONE;
}
