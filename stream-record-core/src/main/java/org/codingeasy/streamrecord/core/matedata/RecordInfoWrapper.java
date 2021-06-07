package org.codingeasy.streamrecord.core.matedata;



/**
* 审计信息包装类  
* @author : KangNing Hu
*/
public class RecordInfoWrapper {

	// 单个审计信息
	private RecordInfo recordInfo;

	//多个审计信息
	private RecordInfo[] recordInfos;

	private RecordInfoWrapper(RecordInfo recordInfo){
		this.recordInfo = recordInfo;
	}


	private RecordInfoWrapper(RecordInfo... recordInfos){
		this.recordInfos = recordInfos;
	}

	/**
	 * 构建单个数据
	 * @param recordInfo 审计信息对象
	 * @return
	 */
	public static RecordInfoWrapper ofOne(RecordInfo recordInfo){
		return new RecordInfoWrapper(recordInfo);
	}


	/**
	 * 构建多个数据
	 * @param recordInfos 审计记录信息
	 * @return
	 */
	public static RecordInfoWrapper ofMultiple(RecordInfo... recordInfos){
		return new RecordInfoWrapper(recordInfos);
	}

	/**
	 * 是否为多个数据
	 * @return
	 */
	public boolean isMultiple(){
		return this.recordInfos != null;
	}


	public RecordInfo getRecordInfo(){
		return this.recordInfo;
	}


	public RecordInfo[] getRecordInfos(){
		return this.recordInfos;
	}
}
