package org.dream.tool.auditlog;

/**
*   
* @author : KangNing Hu
*/
@FunctionalInterface
public interface Pipeline {

	void doConsume(Object log);
}
