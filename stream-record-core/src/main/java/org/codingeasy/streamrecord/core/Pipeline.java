package org.codingeasy.streamrecord.core;

import org.codingeasy.streamrecord.core.matedata.RecordInfoWrapper;

/**
*   
* @author : KangNing Hu
*/
@FunctionalInterface
public interface Pipeline {

	void doConsume(RecordInfoWrapper recordInfoWrapper);
}
