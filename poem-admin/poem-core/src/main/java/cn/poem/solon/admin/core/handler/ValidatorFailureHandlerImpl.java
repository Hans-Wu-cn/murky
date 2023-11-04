package cn.poem.solon.admin.core.handler;

import org.noear.solon.Utils;
import org.noear.solon.annotation.Component;
import org.noear.solon.core.handle.Context;
import org.noear.solon.core.handle.Result;
import org.noear.solon.validation.ValidatorException;
import org.noear.solon.validation.ValidatorFailureHandler;

import java.lang.annotation.Annotation;

@Component
public class ValidatorFailureHandlerImpl implements ValidatorFailureHandler {
    @Override
    public boolean onFailure(Context ctx, Annotation ano, Result result, String message) throws Throwable {
        if (Utils.isEmpty(message)) {
            if (Utils.isEmpty(result.getDescription())) {
                message = new StringBuilder(100)
                        .append("@")
                        .append(ano.annotationType().getSimpleName())
                        .append(" verification failed")
                        .toString();
            } else {
                message = new StringBuilder(100)
                        .append("@")
                        .append(ano.annotationType().getSimpleName())
                        .append(" verification failed: ")
                        .append(result.getDescription())
                        .toString();
            }
        }
        //这里也可以直接做输出，不过用异常更好
        throw new ValidatorException(result.getCode(), message, ano, result);
    }
}
