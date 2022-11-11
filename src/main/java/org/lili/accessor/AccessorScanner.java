package org.lili.accessor;

import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.annotations.Accessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

/**
 * 扫描带有Accessor注解的接口
 *
 * @author lili
 * @date 2022/11/10 19:38
 */
@Slf4j
public class AccessorScanner extends ClassPathBeanDefinitionScanner {

    private Class<? extends AccessorFactoryBean> accessorFactoryBeanClass = AccessorFactoryBean.class;

    private Class<? extends Annotation> annotationClass;

    private Session session;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setAccessorFactoryBeanClass(Class<? extends AccessorFactoryBean> AccessorFactoryBeanClass) {
        this.accessorFactoryBeanClass = AccessorFactoryBeanClass == null ? AccessorFactoryBean.class : AccessorFactoryBeanClass;
    }

    public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }


    public AccessorScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        log.info("doScan in package:{}", Arrays.toString(basePackages));
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        processBeanDefinitions(beanDefinitions);
        return beanDefinitions;
    }


    /**
     * 这句话非常重要，影响是否能扫描到包
     */
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }

    /**
     * 处理扫描出来的Bean，将bean转换成AccessorFactoryBean
     *
     * @param beanDefinitionHolders
     */
    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitionHolders) {
        log.info("processBeanDefinitions start");
        if (CollectionUtils.isEmpty(beanDefinitionHolders)) {
            log.info("beanDefinitionHolders is empty,check package");
            return;
        }
        AbstractBeanDefinition definition;
        for (BeanDefinitionHolder holder : beanDefinitionHolders) {
            definition = (AbstractBeanDefinition) holder.getBeanDefinition();
            definition.setScope("singleton");
            String beanClassName = definition.getBeanClassName();
            definition.getConstructorArgumentValues().addGenericArgumentValue(beanClassName);
            try {
                definition.getPropertyValues().add("accessorInterface", Resources.classForName(beanClassName));
            } catch (ClassNotFoundException e) {
                // ignore
            }

            //将Bean改造成 MapperFactoryBean类型
            definition.setBeanClass(this.accessorFactoryBeanClass);
            //构造MapperFactoryBean属性
            if (Objects.nonNull(session)) {
                logger.info("config bean property, session");
                definition.getPropertyValues().add("session", session);
                definition.getPropertyValues().add("mappingManager", new MappingManager(session));
            }
            log.info("process every bean:{},{}", beanClassName, this.accessorFactoryBeanClass.getClass());
        }
    }

    public void registerFilters() {
        log.info("registerFilters start. {},{}", annotationClass, accessorFactoryBeanClass);

        boolean acceptAllInterfaces = true;

        if (this.annotationClass != null) {
            addIncludeFilter(new AnnotationTypeFilter(this.annotationClass));
            acceptAllInterfaces = false;
        }

        addIncludeFilter((metadataReader, metadataReaderFactory) -> true);
        addIncludeFilter(new AnnotationTypeFilter(Accessor.class));

        if (acceptAllInterfaces) {
            addIncludeFilter((metadataReader, metadataReaderFactory) -> true);
        }

        addExcludeFilter((metadataReader, metadataReaderFactory) -> {
            String className = metadataReader.getClassMetadata().getClassName();
            return className.endsWith("package-info");
        });
        log.info("registerFilters end. acceptAllInterfaces:{}", acceptAllInterfaces);

    }

}
