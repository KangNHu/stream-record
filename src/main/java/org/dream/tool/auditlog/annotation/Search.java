package org.dream.tool.auditlog.annotation;

import java.lang.annotation.*;

/**
 * 参数搜索器
 * 和{@link Param}有且只有一个生效
 * @see Search
 * @author hukangning
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER , ElementType.FIELD})
@Documented
public @interface Search {
}
