package org.codingeasy.streamrecord.core;

import org.codingeasy.streamrecord.core.matedata.RecordInfoWrapper;

/**
 * 记录生成器
 *
 * @author : KangNing Hu
 */
@FunctionalInterface
public interface RecordProducer {


  RecordInfoWrapper doProduce(CurrentContext currentContext);


}
