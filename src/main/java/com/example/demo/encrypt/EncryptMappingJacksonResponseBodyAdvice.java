package com.example.demo.encrypt;

import com.example.demo.encrypt.annotation.Encrypt;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

/**
 * 描述
 *
 * @author 王俊
 */
@ControllerAdvice
public class EncryptMappingJacksonResponseBodyAdvice extends AbstractMappingJacksonResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {

        return super.supports(returnType, converterType) &&
                (AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), Encrypt.class) ||
                        returnType.hasMethodAnnotation(Encrypt.class));
    }


    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType, MethodParameter returnType,
                                           ServerHttpRequest request, ServerHttpResponse response) {

        Object object = bodyContainer.getValue();
        ReflectionUtils.doWithFields(object.getClass(),
                field -> {
                    ReflectionUtils.makeAccessible(field);
                    field.set(object, "1");
                },
                field -> field.isAnnotationPresent(Encrypt.class)
        );
    }
}
