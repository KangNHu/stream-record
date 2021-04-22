package org.easy.boot.log.log.core;

import org.easy.boot.log.log.core.matedata.LogInfoWrapper;

/**
* 日志生成器  
* @author : KangNing Hu
*/
@FunctionalInterface
public interface LogProducer {


	LogInfoWrapper doProduce(CurrentContext currentContext);


}
