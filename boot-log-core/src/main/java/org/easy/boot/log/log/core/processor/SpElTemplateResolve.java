package org.easy.boot.log.log.core.processor;

import org.easy.boot.log.log.core.utils.SpringELUtils;
import org.easy.boot.log.log.core.CurrentContext;
import org.easy.boot.log.log.core.InterceptMethodWrapper;

/**
* Spel表达式实现的解析器
 *
* @author : KangNing Hu
*/
public class SpElTemplateResolve implements TemplateResolve{



	@Override
	public String doResolve(CurrentContext currentContext) {
		String temple = currentContext.getLogDefinition().getTemple();
		InterceptMethodWrapper interceptMethodWrapper = currentContext.getInterceptMethodWrapper();
		return SpringELUtils.parse(temple, interceptMethodWrapper.getMethod(), interceptMethodWrapper.toArray(new Object[]{}));
	}
}
