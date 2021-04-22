package org.easy.boot.log.log.core.matedata;



/**
* 审计信息包装类  
* @author : KangNing Hu
*/
public class LogInfoWrapper {

	// 单个审计信息
	private LogInfo logInfo;

	//多个审计信息
	private LogInfo[] logInfos;

	private LogInfoWrapper(LogInfo logInfo){
		this.logInfo = logInfo;
	}


	private LogInfoWrapper(LogInfo... logInfos){
		this.logInfos = logInfos;
	}

	/**
	 * 构建单个数据
	 * @param logInfo 审计信息对象
	 * @return
	 */
	public static LogInfoWrapper ofOne(LogInfo logInfo){
		return new LogInfoWrapper(logInfo);
	}


	/**
	 * 构建多个数据
	 * @param logInfos 审计日志信息
	 * @return
	 */
	public static LogInfoWrapper ofMultiple(LogInfo... logInfos){
		return new LogInfoWrapper(logInfos);
	}

	/**
	 * 是否为多个数据
	 * @return
	 */
	public boolean isMultiple(){
		return this.logInfos != null;
	}


	public LogInfo getLogInfo(){
		return this.logInfo;
	}


	public LogInfo[] getLogInfos(){
		return this.logInfos;
	}
}
