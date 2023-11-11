package com.stylefeng.guns.rest.modular.user;


import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.user.UserAPI;

@Service(interfaceClass = UserAPI.class)
public class UserApiImpl implements UserAPI {
    @Override
    public boolean isAdmin() {
        return true;
    }
}
