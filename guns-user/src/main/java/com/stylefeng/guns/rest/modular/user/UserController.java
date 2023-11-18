package com.stylefeng.guns.rest.modular.user;


import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.user.UserAPI;
import com.stylefeng.guns.api.user.UserInfoModel;
import com.stylefeng.guns.api.user.UserModel;
import com.stylefeng.guns.rest.modular.vo.ResponseVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {




    @Reference(interfaceClass = UserAPI.class)
    private UserAPI userAPI;


    @RequestMapping("/register")
    public ResponseVo register(UserModel userModel){
        String username = userModel.getUsername();
        String password = userModel.getPassword();

        if (username == null ||
                username.trim().length() == 0 ||
                password == null ||
                password.trim().length() == 0){
            return ResponseVo.serviceFail("用户名或者密码不能为空");

        }
        boolean register = userAPI.register(userModel);
        if (register){
            return ResponseVo.success("注册成功");
        }else {
            return ResponseVo.serviceFail("注册失败");
        }

    }


}
