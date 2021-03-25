package org.dream.tool.auditlog.annotation;

import java.lang.annotation.*;

/**
 * 参数搜索器
 * @author hukangning
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER , ElementType.FIELD})
@Documented
public @interface Search {
}
