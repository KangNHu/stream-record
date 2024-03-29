package org.codingeasy.streamrecord.core.annotation;

import java.lang.annotation.*;

/**
 * 参数搜索器 和{@link Param}有且只有一个生效
 *
 * @author hukangning
 * @see Search
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Documented
public @interface Search {

}
