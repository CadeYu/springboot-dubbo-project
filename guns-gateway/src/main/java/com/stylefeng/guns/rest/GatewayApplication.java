package com.stylefeng.guns.rest;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.stylefeng.guns.rest.modular.Client;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = {"com.stylefeng.guns"})
@EnableDubboConfiguration
public class GatewayApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run =
                SpringApplication.run(GatewayApplication.class, args);
        Client client =
                (Client)run.getBean("client");
        boolean b = client.testClient();
        System.out.println(b);
    }
}
