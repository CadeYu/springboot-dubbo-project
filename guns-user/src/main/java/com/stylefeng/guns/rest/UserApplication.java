package com.stylefeng.guns.rest;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.stylefeng.guns.rest.modular.user.UserApiImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(scanBasePackages = {"com.stylefeng.guns"})
@EnableDubboConfiguration
public class UserApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run =
                SpringApplication.run(UserApplication.class, args);
        UserApiImpl userApiImpl =
                (UserApiImpl)run.getBean("userApiImpl");
        userApiImpl.isAdmin();
    }
}
