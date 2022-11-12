package org.lili.config;

import com.datastax.driver.core.*;
import com.datastax.driver.core.policies.DefaultRetryPolicy;
import com.datastax.driver.mapping.MappingManager;
import com.youdao.ke.courseop.common.cassandra.CassandraProperties;
import com.youdao.ke.courseop.common.cassandra.accessor.AccessorScannerConfigurer;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * @author lili
 * @date 2022/11/01 14:41
 */
@Slf4j
@Configuration
@NoArgsConstructor
public class CassandraConfig {

    /**
     * 外部包里面的类，如何让外部配置优于内部配置被注入呢？这个值总是空的，但是项目启动后这个值有。
     */
    @Autowired
    private CassandraProperties cassandraProperties;

    @Bean
    public Cluster cluster() {
        log.info("init cqlSessionFactory:{}", cassandraProperties);
        QueryOptions queryOption = new QueryOptions()
                .setFetchSize(1000)
                .setConsistencyLevel(ConsistencyLevel.ONE);

        SocketOptions socketOptions = new SocketOptions()
                .setConnectTimeoutMillis(10000)
                .setReadTimeoutMillis(3000);
        Cluster.Builder builder = Cluster.builder().withQueryOptions(queryOption)
                .withSocketOptions(socketOptions)
                .withRetryPolicy(DefaultRetryPolicy.INSTANCE);
        //cassandraProperties.getContactPoints().get(0)
        Cluster cluster = builder.addContactPoint("10.108.160.30").build();
        return cluster;
    }

    @Bean
    public Session session() {
        return cluster().connect("adaplearn_tiku_test");
    }


    @Bean
    public MappingManager mappingManager() {
        return new MappingManager(session());
    }

    @Bean
    public AccessorScannerConfigurer configurer(Session session) {
        log.info("AccessorScannerConfigurer start,{}", Objects.isNull(session));
        AccessorScannerConfigurer configurer = new AccessorScannerConfigurer();
        configurer.setSession(session);
        configurer.setBasePackage("org.lili.cassandra.accessor");
        return configurer;
    }
}