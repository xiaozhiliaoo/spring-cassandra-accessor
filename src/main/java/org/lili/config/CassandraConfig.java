package org.lili.config;

import com.datastax.driver.core.*;
import com.datastax.driver.core.policies.DefaultRetryPolicy;
import com.datastax.driver.mapping.MappingManager;
import com.youdao.ke.courseop.common.cassandra.accessor.AccessorScannerConfigurer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

/**
 * @author lili
 * @date 2022/11/01 14:41
 */
@Slf4j
@Configuration
public class CassandraConfig {

    @Bean
    public Cluster cluster() {
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
    public AccessorScannerConfigurer configurer() {
        log.info("AccessorScannerConfigurer start,{}", Objects.isNull(session()));
        AccessorScannerConfigurer configurer = new AccessorScannerConfigurer();
        configurer.setSession(session());
        configurer.setBasePackage("org.lili.cassandra.accessor");
        return configurer;
    }
}