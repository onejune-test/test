package com.example.demo.encrypt.annotation;

import java.lang.annotation.*;

/**
 * 表示此方法返回的内容会进行加密处理
 *
 * @author 王俊
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Encrypt {
}
