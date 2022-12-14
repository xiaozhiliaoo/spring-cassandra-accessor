package org.lili.accessor;

import com.datastax.driver.core.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;

import static org.springframework.util.Assert.notNull;

/**
 * 配置AccessorScanner的属性，配置完开始扫描
 *
 * @author lili
 * @date 2022/11/01 18:25
 */
@Slf4j
public class AccessorScannerConfigurer implements BeanDefinitionRegistryPostProcessor,
        InitializingBean, ApplicationContextAware, BeanNameAware {

    private String basePackage;

    private Class<? extends Annotation> annotationClass;

    private Class<? extends AccessorFactoryBean> accessorFactoryBeanClass;

    private ApplicationContext applicationContext;

    private Session session;

    private String beanName;

    private String cqlSessionFactoryBeanName;

    public String getCqlSessionFactoryBeanName() {
        return cqlSessionFactoryBeanName;
    }

    public void setCqlSessionFactoryBeanName(String cqlSessionFactoryBeanName) {
        this.cqlSessionFactoryBeanName = cqlSessionFactoryBeanName;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public void setAccessorFactoryBean(Class<? extends AccessorFactoryBean> accessorFactoryBeanClass) {
        this.accessorFactoryBeanClass = accessorFactoryBeanClass;
    }

    public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
        // left intentionally blank
        log.debug("postProcessBeanFactory:{},{},{}", basePackage, annotationClass, accessorFactoryBeanClass);
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        log.debug("postProcessBeanDefinitionRegistry start:{},{},{}", basePackage, annotationClass, accessorFactoryBeanClass);

        AccessorScanner scanner = new AccessorScanner(registry);
        scanner.setSession(this.session);
        scanner.setAccessorFactoryBeanClass(this.accessorFactoryBeanClass);
        scanner.setAnnotationClass(this.annotationClass);
        scanner.setCqlSessionFactoryBeanName(this.cqlSessionFactoryBeanName);
        //注册过滤器
        scanner.registerFilters();
        //扫描包下面的bean
        String[] basePackages = StringUtils.tokenizeToStringArray(this.basePackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
        scanner.scan(basePackages);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        notNull(this.basePackage, "Property 'basePackage' is required");
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }
}
