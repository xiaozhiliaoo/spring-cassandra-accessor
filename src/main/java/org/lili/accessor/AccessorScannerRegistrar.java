package org.lili.accessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lili
 * @date 2022/11/01 19:46
 */
@Slf4j
public class AccessorScannerRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        log.info("registerBeanDefinitions:{}", importingClassMetadata);
        AnnotationAttributes mapperScanAttrs = AnnotationAttributes
                .fromMap(importingClassMetadata.getAnnotationAttributes(AccessorScan.class.getName()));
        if (mapperScanAttrs != null) {
            registerBeanDefinitions(importingClassMetadata, mapperScanAttrs, registry,
                    generateBaseBeanName(importingClassMetadata, 0));
        }
    }

    private void registerBeanDefinitions(AnnotationMetadata annoMeta, AnnotationAttributes annoAttrs,
                                         BeanDefinitionRegistry registry, String beanName) {
        log.info("registerBeanDefinitions inner start beanName:{},annoAttrs:{}", beanName, annoAttrs);

        //实例化自定义的扫描器，然后写入属性
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(AccessorScannerConfigurer.class);

        Class<? extends Annotation> annotationClass = annoAttrs.getClass("annotationClass");
        if (!Annotation.class.equals(annotationClass)) {
            builder.addPropertyValue("annotationClass", annotationClass);
        }

        Class<? extends AccessorFactoryBean> accessorFactoryBeanClass = annoAttrs.getClass("factoryBean");
        if (!AccessorFactoryBean.class.equals(accessorFactoryBeanClass)) {
            builder.addPropertyValue("accessorFactoryBeanClass", accessorFactoryBeanClass);
        }

        String sqlSessionFactoryRef = annoAttrs.getString("cqlSessionRef");
        if (StringUtils.hasText(sqlSessionFactoryRef)) {
            builder.addPropertyValue("cqlSessionFactoryBeanName", annoAttrs.getString("cqlSessionRef"));
        }

        List<String> basePackages = new ArrayList<>();


        basePackages.addAll(Arrays.stream(annoAttrs.getStringArray("basePackages")).filter(StringUtils::hasText)
                .collect(Collectors.toList()));

        if (basePackages.isEmpty()) {
            basePackages.add(getDefaultBasePackage(annoMeta));
        }

        builder.addPropertyValue("basePackage", StringUtils.collectionToCommaDelimitedString(basePackages));

        // for spring-native
        builder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);

        registry.registerBeanDefinition(beanName, builder.getBeanDefinition());

        log.info("registerBeanDefinitions inner end beanName:{},annoAttrs:{},beanClassName:{}",
                beanName, annoAttrs, builder.getBeanDefinition().getBeanClassName());

    }

    private static String getDefaultBasePackage(AnnotationMetadata importingClassMetadata) {
        return ClassUtils.getPackageName(importingClassMetadata.getClassName());
    }

    private static String generateBaseBeanName(AnnotationMetadata importingClassMetadata, int index) {
        return importingClassMetadata.getClassName() + "#" +
                AccessorScannerRegistrar.class.getSimpleName() + "#" + index;
    }
}
