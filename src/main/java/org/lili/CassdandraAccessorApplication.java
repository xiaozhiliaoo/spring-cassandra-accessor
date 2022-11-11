package org.lili;

import org.lili.accessor.AccessorScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AccessorScan(basePackages = "org.lili.cassandra.accessor")
public class CassdandraAccessorApplication {

    public static void main(String[] args) {
        SpringApplication.run(CassdandraAccessorApplication.class, args);
    }
}
