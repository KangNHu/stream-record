package org.codingeasy.streamrecord.core.processor;

import org.codingeasy.streamrecord.core.CurrentContext;
import org.codingeasy.streamrecord.core.AttributeAccess;
import org.codingeasy.streamrecord.core.matedata.RecordInfo;

/**
* 基于表达式实现的处理器
 * @see
* @author : KangNing Hu
*/
public class ExpressionProcessor implements Processor {


	//模版解析器
	private TemplateResolve templateResolve;

	/**
	 * 设置模版解析器
	 * @param templateResolve 模版解析器
	 */
	public void setTemplateResolve(TemplateResolve templateResolve) {
		this.templateResolve = templateResolve;
	}

	@Override
	public void process(CurrentContext currentContext) {
		//获取上下文属性
		AttributeAccess attributeAccess = currentContext.getAttributeAccess();
		//设置记录消息
		attributeAccess.put(RecordInfo.MASSAGE_ATTR , templateResolve.doResolve(currentContext));
	}
}
