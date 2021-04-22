package org.easy.boot.log.log.core;

import org.easy.boot.log.log.core.matedata.LogInfoWrapper;

/**
*   
* @author : KangNing Hu
*/
@FunctionalInterface
public interface Pipeline {

	void doConsume(LogInfoWrapper logInfoWrapper);
}
