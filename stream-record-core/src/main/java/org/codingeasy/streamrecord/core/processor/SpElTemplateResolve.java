package org.codingeasy.streamrecord.core.processor;

import org.codingeasy.streamrecord.core.utils.SpringELUtils;
import org.codingeasy.streamrecord.core.CurrentContext;
import org.codingeasy.streamrecord.core.InterceptMethodWrapper;

/**
* Spel表达式实现的解析器
 *
* @author : KangNing Hu
*/
public class SpElTemplateResolve implements TemplateResolve{



	@Override
	public String doResolve(CurrentContext currentContext) {
		String temple = currentContext.getRecordDefinition().getTemple();
		InterceptMethodWrapper interceptMethodWrapper = currentContext.getInterceptMethodWrapper();
		return SpringELUtils.parse(temple, interceptMethodWrapper.getMethod(), interceptMethodWrapper.toArray(new Object[]{}));
	}
}
