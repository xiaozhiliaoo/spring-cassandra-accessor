package org.lili.accessor;

import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;

import static org.springframework.util.Assert.notNull;

/**
 * 生成Accessor对象的工厂Bean
 *
 * @author lili
 * @date 2022/11/01 20:29
 */
@Slf4j
public class AccessorFactoryBean<T> implements FactoryBean<T> {

    private Session session;

    private MappingManager mappingManager;
    private Class<T> accessorInterface;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public MappingManager getMappingManager() {
        return mappingManager;
    }

    public void setMappingManager(MappingManager mappingManager) {
        this.mappingManager = mappingManager;
    }

    public void setAccessorInterface(Class<T> accessorInterface) {
        this.accessorInterface = accessorInterface;
    }

    public AccessorFactoryBean() {
        // intentionally empty
    }


    public AccessorFactoryBean(Class<T> accessorInterface) {
        this.accessorInterface = accessorInterface;
    }


    @Override
    public T getObject() throws Exception {
        notNull(this.accessorInterface, "Property 'accessorInterface' is required");
        Session session = getSession();
        notNull(session, "session can not be null");
        MappingManager mappingManager = new MappingManager(session);
        return mappingManager.createAccessor(accessorInterface);
    }

    @Override
    public Class<?> getObjectType() {
        return this.accessorInterface;
    }

    public Class<T> getAccessorInterface() {
        return accessorInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
