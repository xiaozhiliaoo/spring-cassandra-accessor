package org.lili.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author lili
 * @date 2022/11/11 9:51
 */
@Configuration
@MapperScan(basePackages = Config.PACKAGE)
@Slf4j
public class Config {
    static final String PACKAGE = "org.lili.mapper";
    @Value("${db.mysql.url}")
    private String dbUrl;
    @Value("${db.mysql.username}")
    private String dbUser;
    @Value("${db.mysql.password}")
    private String dbPassword;

    @Bean(name = "mysqlDataSource")
    public DataSource mysqlDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver"); //mysql 8
        dataSource.setUrl(dbUrl);
        log.debug("dburl: {}", dbUrl);
        dataSource.setUsername(dbUser);
        dataSource.setPassword(dbPassword);
        return dataSource;
    }

    @Bean(name = "mysqlTransactionManager")
    public DataSourceTransactionManager mysqlTransactionManager() {
        return new DataSourceTransactionManager(mysqlDataSource());
    }

    @Bean(name = "mySqlSessionFactory")
    public SqlSessionFactory mySqlSessionFactory(@Qualifier("mysqlDataSource") DataSource mysqlDataSource)
            throws Exception {
        Connection conn = mysqlDataSource.getConnection();
        log.info("isClosed: {}", conn.isClosed());
        log.info("isValid: {}", conn.isValid(5));
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(mysqlDataSource);
        return sessionFactory.getObject();
    }
}