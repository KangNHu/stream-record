package org.codingeasy.streamrecord.core.matedata;


/**
 * 默认的记录信息
 *
 * @author : KangNing Hu
 */
public class DefaultRecordInfo implements RecordInfo {

  private Object id;


  private Long operationTime;


  private String message;


  @Override
  public Object getId() {
    return this.id;
  }

  @Override
  public void setId(Object id) {
    this.id = id;
  }

  @Override
  public Long getOperationTime() {
    return this.operationTime;
  }

  @Override
  public void setOperationTime(Long time) {
    this.operationTime = time;
  }

  @Override
  public String getMessage() {

    return this.message;
  }

  @Override
  public void setMessage(String message) {
    this.message = message;
  }
}
