package org.codingeasy.streamrecord.core;

import org.codingeasy.streamrecord.core.matedata.RecordInfoWrapper;
import org.codingeasy.streamrecord.core.matedata.RecordInfo;
import org.codingeasy.streamrecord.core.matedata.DefaultRecordInfo;

import java.util.UUID;

/**
 * 默认的记录生成器
 *
 * @author : KangNing Hu
 */
public class DefaultRecordProducer implements RecordProducer {


  @Override
  public RecordInfoWrapper doProduce(CurrentContext currentContext) {
    //获取记录消息
    AttributeAccess attributeAccess = currentContext.getAttributeAccess();
    String message = (String) attributeAccess.get(RecordInfo.MASSAGE_ATTR);
    //创建记录结构对象
    DefaultRecordInfo defaultRecordInfo = new DefaultRecordInfo();
    defaultRecordInfo.setId(UUID.randomUUID());
    defaultRecordInfo.setMessage(message);
    defaultRecordInfo.setOperationTime(System.currentTimeMillis());
    return RecordInfoWrapper.ofOne(defaultRecordInfo);
  }


}
