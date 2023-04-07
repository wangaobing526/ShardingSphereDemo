package com.bus.businessdiaryapp;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.mybatis.spring.annotation.MapperScan;


@EnableDiscoveryClient
@SpringBootApplication
@EnableApolloConfig
@MapperScan("com.sharding.mapper")
public class BusinessDiaryAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessDiaryAppApplication.class, args);
    }

}
