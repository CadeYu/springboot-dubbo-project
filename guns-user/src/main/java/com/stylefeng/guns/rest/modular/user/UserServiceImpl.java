package com.stylefeng.guns.rest.modular.user;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.stylefeng.guns.api.user.UserAPI;
import com.stylefeng.guns.api.user.UserInfoModel;
import com.stylefeng.guns.api.user.UserModel;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.rest.common.persistence.dao.MoocUserTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MoocUserT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
@Service(interfaceClass = UserAPI.class)
public class UserServiceImpl implements UserAPI {

    @Autowired
    private MoocUserTMapper moocUserTMapper;

    @Override
    public int login(String username, String password) {
        MoocUserT moocUserT = new MoocUserT();
        moocUserT.setUserName(username);
        MoocUserT moocUserT1 = moocUserTMapper.selectOne(moocUserT);

        if (moocUserT1 != null && moocUserT1.getUuid() > 0) {
            String encrypted = MD5Util.encrypt(password);
            if (moocUserT1.getUserPwd().equals(encrypted)) {
                return moocUserT1.getUuid();
            }
        }

        return 0;
    }

    @Override
    public boolean register(UserModel userModel) {
        //将注册信息实体转换成数据实体
        MoocUserT moocUserT = new MoocUserT();
        moocUserT.setUserName(userModel.getUsername());
        moocUserT.setAddress(userModel.getAddress());
        moocUserT.setUserPhone(userModel.getPhone());
        moocUserT.setEmail(userModel.getEmail());

        //密码用md5加密
        String password = userModel.getPassword();
        String encryptedPassWord = MD5Util.encrypt(password);
        moocUserT.setUserPwd(encryptedPassWord);

        Integer insert = moocUserTMapper.insert(moocUserT);

        //

        if (insert > 0) {
            return true;
        }else {
            return false;
        }

    }

    @Override
    public boolean checkUserName(String userName) {
        EntityWrapper<MoocUserT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("user_name",userName);
        Integer i = moocUserTMapper.selectCount(entityWrapper);
        if (i != null && i > 0) {
            return false;
        }
        return true;
    }

    private UserInfoModel transfer2UserInfoModel(MoocUserT moocUserT){
        UserInfoModel userInfoModel = new UserInfoModel();
        userInfoModel.setAddress(moocUserT.getAddress());
        userInfoModel.setBiography(moocUserT.getBiography());
        userInfoModel.setBirthday(moocUserT.getBirthday());
        userInfoModel.setEmail(moocUserT.getEmail());
        userInfoModel.setUsername(moocUserT.getUserName());
        userInfoModel.setHeadAddress(moocUserT.getHeadUrl());
        userInfoModel.setLifeState(String.valueOf(moocUserT.getLifeState()));
        userInfoModel.setNickname(moocUserT.getNickName());
        userInfoModel.setPhone(moocUserT.getUserPhone());
        userInfoModel.setSex(moocUserT.getUserSex());

        return userInfoModel;
    }


    private MoocUserT transfer2MoocUserT(UserInfoModel userInfoModel){
        MoocUserT moocUserT = new MoocUserT();
        moocUserT.setUserName(userInfoModel.getUsername());
        moocUserT.setUserSex(userInfoModel.getSex());
        moocUserT.setAddress(userInfoModel.getAddress());
        moocUserT.setBiography(userInfoModel.getBiography());
        moocUserT.setBirthday(userInfoModel.getBirthday());
        moocUserT.setEmail(userInfoModel.getEmail());
        moocUserT.setUserPhone(userInfoModel.getPhone());
        moocUserT.setHeadUrl(userInfoModel.getHeadAddress());
        moocUserT.setLifeState(Integer.valueOf(userInfoModel.getLifeState()));
        moocUserT.setNickName(userInfoModel.getNickname());
        moocUserT.setBeginTime(userInfoModel.getCreateTime());
        moocUserT.setUpdateTime(transfer2Date(System.currentTimeMillis()));
        return moocUserT;
    }




    @Override
    public UserInfoModel getUserInfo(int uuid) {
        MoocUserT moocUserT = moocUserTMapper.selectById(uuid);
        UserInfoModel userInfoModel = transfer2UserInfoModel(moocUserT);
        return userInfoModel;
    }

    private Date transfer2Date(long time) {
        Instant epochMilli = Instant.ofEpochMilli(time);
        Date date = Date.from(epochMilli);
        return date;
    }

    @Override
    public UserInfoModel updateUserInfo(UserInfoModel userInfo) {
        MoocUserT transferredMoocUserT = transfer2MoocUserT(userInfo);
        Integer i = moocUserTMapper.updateById(transferredMoocUserT);
        if (i != null && i > 0) {
           return getUserInfo(transferredMoocUserT.getUuid());
        }
        return userInfo;
    }
}
