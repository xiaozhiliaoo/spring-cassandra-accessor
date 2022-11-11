/*
 * Copyright 2010-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lili.accessor;

import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 注解用来扫描包下面的Accessor接口
 * // TODO: 2022/11/01 暂未实现自动装配
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(AccessorScannerRegistrar.class)
public @interface AccessorScan {

    @AliasFor("basePackages")
    String[] value() default {};


    @AliasFor("value")
    String[] basePackages() default {};


    Class<? extends AccessorFactoryBean> factoryBean() default AccessorFactoryBean.class;


    Class<? extends Annotation> annotationClass() default Annotation.class;


    String cqlSessionRef() default "";

}
