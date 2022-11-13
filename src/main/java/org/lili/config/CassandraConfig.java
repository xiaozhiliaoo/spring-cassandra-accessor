package org.lili.config;

import com.datastax.driver.core.*;
import com.datastax.driver.core.policies.DefaultRetryPolicy;
import com.datastax.driver.mapping.MappingManager;
import com.youdao.ke.courseop.common.cassandra.Cassandra;
import com.youdao.ke.courseop.common.cassandra.CassandraProperties;
import com.youdao.ke.courseop.common.cassandra.accessor.AccessorScan;
import com.youdao.ke.courseop.common.cassandra.accessor.CqlSessionFactoryBean;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.lili.config.CassandraConfig.PACKAGE_DIR;


@Slf4j
@Configuration
@NoArgsConstructor
@AccessorScan(basePackages = PACKAGE_DIR,
        cqlSessionRef = "cqlSessionRef")
public class CassandraConfig {

    static final String PACKAGE_DIR = "org.lili.cassandra.accessor";


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
    public Cassandra cassandra() {
        return new Cassandra(cluster(), "adaplearn_tiku_test");
    }

    @Bean
    public MappingManager MappingManager() {
        return new MappingManager(session());
    }

    @Bean(name = "cqlSessionRef")
    public CqlSessionFactoryBean factoryBean() {
        CqlSessionFactoryBean bean = new CqlSessionFactoryBean();
        bean.setSession(session());
        return bean;
    }
}
