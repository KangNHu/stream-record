package org.easy.boot.log.log.core.utils;

import org.easy.boot.log.log.core.ReadWriteLock;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * spring el 工具类
 * @author hukangning
 */
public class SpringELUtils {

    private static final Logger logger = Logger.getLogger(StringUtils.class.getName());


    private static ReadWriteLock readWriteLock = new ReadWriteLock();


    private static Map<String , Expression> expressions = new HashMap<>(16);

    /**
     * 使用方法菜蔬解析spel
     * @param spel
     * @param method
     * @param args
     * @return
     */
    public static String parse(String spel, Method method, Object[] args) {
        Expression expression = getExpressionByTemplate(spel);
        if (expression == null){
            return null;
        }
        String[] paraNameArr = MethodUtils.getParamNames(method);
        //SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        //把方法参数放入SPEL上下文中
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }
        try {
            return expression.getValue(context, String.class);
        }catch (EvaluationException e){
            logger.log(Level.WARNING , "可能是错误的表达式：" + spel);
            return spel;
        }
    }


    /**
     * 获取spel表达式对象
     * @param template
     * @return
     */
    private static Expression getExpressionByTemplate(String template){
        if (StringUtils.isEmpty(template)){
            return null;
        }
        Expression expression = readWriteLock.readExecute(() -> expressions.get(template));
        if (expression == null){
            expression =  readWriteLock.writeExecute(() -> {
                /**
                 * 创建spel表达式对象并缓存
                 */
                ExpressionParser expressionParser = new SpelExpressionParser();
                Expression newExpression = expressionParser.parseExpression(template);
                expressions.putIfAbsent(template , newExpression);
                return newExpression;
            });
        }
        return expression;
    }
}
