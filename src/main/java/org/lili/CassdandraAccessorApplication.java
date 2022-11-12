package org.lili;

import com.youdao.ke.courseop.common.cassandra.CommonCassandraConfiguration;
import com.youdao.ke.courseop.common.cassandra.accessor.AccessorScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(CommonCassandraConfiguration.class)
public class CassdandraAccessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CassdandraAccessorApplication.class, args);
    }
}
