package com.stylefeng.guns.api.user;
public interface UserAPI {
    int login(String username, String password);

    boolean register(UserModel userModel);

    boolean checkUserName(String userName);

    UserInfoModel getUserInfo(int uuid);

    UserInfoModel updateUserInfo(UserInfoModel userInfo);;

}