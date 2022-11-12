package org.lili.config;

import com.datastax.driver.core.*;
import com.datastax.driver.core.policies.DefaultRetryPolicy;
import lombok.extern.slf4j.Slf4j;
import org.lili.accessor.AccessorScan;
import org.lili.accessor.CqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lili
 * @date 2022/11/01 14:41
 */
@Configuration
@Slf4j
@AccessorScan(basePackages = "org.lili.cassandra.accessor",
        cqlSessionRef = "fb")

public class CassandraConfig {

    @Bean(name = "cqlSession")
    public Session session() throws Exception {
        log.info("init cqlSessionFactory");
        QueryOptions queryOption = new QueryOptions()
                .setFetchSize(1000)
                .setConsistencyLevel(ConsistencyLevel.ONE);

        SocketOptions socketOptions = new SocketOptions()
                .setConnectTimeoutMillis(10000)
                .setReadTimeoutMillis(3000);
        Cluster.Builder builder = Cluster.builder().withQueryOptions(queryOption)
                .withSocketOptions(socketOptions)
                .withRetryPolicy(DefaultRetryPolicy.INSTANCE);
        Cluster cluster = builder.addContactPoint("10.108.160.30").build();

        return cluster.connect("adaplearn_tiku_test");
    }

//    @Bean
//    public AccessorScannerConfigurer configurer(@Qualifier("cqlSession") Session session) {
//        log.info("AccessorScannerConfigurer start");
//        AccessorScannerConfigurer configurer = new AccessorScannerConfigurer();
//        configurer.setSession(session);
//        configurer.setBasePackage("org.lili.cassandra.accessor");
//        return configurer;
//    }

    @Bean(name = "fb")
    public CqlSessionFactoryBean factoryBean(@Qualifier("cqlSession") Session session) {
        CqlSessionFactoryBean bean = new CqlSessionFactoryBean();
        bean.setSession(session);
        return bean;
    }
}