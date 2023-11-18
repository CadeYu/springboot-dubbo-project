package com.stylefeng.guns.rest.modular.user;


import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.user.UserAPI;
import com.stylefeng.guns.api.user.UserInfoModel;
import com.stylefeng.guns.api.user.UserModel;
import com.stylefeng.guns.rest.common.CurrentUser;
import com.stylefeng.guns.rest.modular.vo.ResponseVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Reference(interfaceClass = UserAPI.class)
    private UserAPI userAPI;


    @RequestMapping(value = "/register",method = RequestMethod.POST)
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

    @RequestMapping(value = "/check",method = RequestMethod.POST)
    public ResponseVo check(String userName){
       
        if (userName != null && userName.trim().length() > 0){
            boolean notExists = userAPI.checkUserName(userName);
            if (notExists) {
                return ResponseVo.success("用户名不存在");
            }else {
                return ResponseVo.serviceFail("用户名已存在");
            }
        }
        return ResponseVo.serviceFail("用户名不能为空");
    }


    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public ResponseVo logout(String userName){
        return ResponseVo.success("用户退出成功");
    }

    @RequestMapping(value = "/getUserInfo",method = RequestMethod.GET)
    public ResponseVo getUserInfo(){
        String userId = CurrentUser.get();
        if (userId != null && userId.trim().length() > 0){
            int parseInt = Integer.parseInt(userId);
            UserInfoModel userInfo = userAPI.getUserInfo(parseInt);
            if (userInfo != null){
                return ResponseVo.success(userInfo);
            }
            else {
                return ResponseVo.appFail("获取用户信息失败");
            }
        }else {
            return ResponseVo.serviceFail("用户未登陆");
        }
    }


    @RequestMapping(value = "/updateUserInfo",method = RequestMethod.POST)
    public ResponseVo updateUserInfo(UserInfoModel userInfoModel){
        String userId = CurrentUser.get();
        if (userId != null && userId.trim().length() > 0){
            int parseInt = Integer.parseInt(userId);
            if (userInfoModel.getUuid() != parseInt){
                return ResponseVo.serviceFail("当前用户不匹配");
            }
            UserInfoModel updateUserInfo = userAPI.updateUserInfo(userInfoModel);
            if (updateUserInfo != null){
                return ResponseVo.success("修改用户信息成功");
            }else {
                return ResponseVo.serviceFail("修改用户信息失败");
            }
        }else {
            return ResponseVo.serviceFail("用户未登陆");
        }
    }
}
