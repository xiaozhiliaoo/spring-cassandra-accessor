package org.lili.accessor;

import com.datastax.driver.core.Session;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * 用于生成Session
 *
 * @author lili
 * @date 2022/11/12 16:27
 */
public class CqlSessionFactoryBean implements FactoryBean<Session>,
        InitializingBean, ApplicationListener<ApplicationEvent> {



    private Session session;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public Session getObject() throws Exception {

        return session;
    }

    @Override
    public Class<?> getObjectType() {
        return this.session == null ? Session.class : this.session.getClass();
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {

    }
}
