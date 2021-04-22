package org.easy.boot.log.log.core;

import org.junit.Test;

/**
*   
* @author : KangNing Hu
*/
public class AttributeAccessTest {

	/**
	 * 测试属性存储器的存和取
	 */
	 @Test
	public void getAndSet(){

	 	AttributeAccess attributeAccess = new AttributeAccess();
	 	attributeAccess.setAttribute("1" , "123" );

	 	assert attributeAccess.getAttribute("1").equals("123");
	 }


}
