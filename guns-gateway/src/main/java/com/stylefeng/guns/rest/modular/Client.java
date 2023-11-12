package com.stylefeng.guns.rest.modular;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.user.UserAPI;
import org.springframework.stereotype.Component;

@Component
public class Client {

    @Reference(interfaceClass = UserAPI.class)
    private UserAPI userAPI;

    public boolean testClient() {
        boolean isAdmin = userAPI.isAdmin();
        return isAdmin;
    }
}
