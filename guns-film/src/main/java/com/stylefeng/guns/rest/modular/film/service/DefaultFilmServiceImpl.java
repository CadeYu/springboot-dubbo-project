package com.stylefeng.guns.rest.modular.film.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.film.FilmServiceApi;
import com.stylefeng.guns.api.film.vo.BannerVO;
import com.stylefeng.guns.api.film.vo.FilmInfo;
import com.stylefeng.guns.api.film.vo.FilmVO;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.rest.common.persistence.dao.MoocBannerTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MoocFilmInfoTMapper;
import com.stylefeng.guns.rest.common.persistence.dao.MoocFilmTMapper;
import com.stylefeng.guns.rest.common.persistence.model.MoocBannerT;
import com.stylefeng.guns.rest.common.persistence.model.MoocFilmT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@Service(interfaceClass = FilmServiceApi.class)
public class DefaultFilmServiceImpl implements FilmServiceApi {
    @Autowired
    private  MoocBannerTMapper moocBannerTMapper;
    @Autowired
    private  MoocFilmTMapper moocFilmTMapper;


    @Override
    public List<BannerVO> getBanners() {
        List<BannerVO> result = new ArrayList<BannerVO>();
        List<MoocBannerT> moocBannerTS = moocBannerTMapper.selectList(null);
        for (MoocBannerT moocBannerT : moocBannerTS) {
            BannerVO bannerVO = new BannerVO();
            bannerVO.setBannerId(moocBannerT.getUuid()+"");
            bannerVO.setBannerAddress(moocBannerT.getBannerAddress());
            bannerVO.setBannerUrl(moocBannerT.getBannerUrl());
            result.add(bannerVO);
        }
        return result;
    }

    private List<FilmInfo> getFilmInfos(List<MoocFilmT> moocFilmT) {
        List<FilmInfo> res = new ArrayList<FilmInfo>();
        for (MoocFilmT filmT : moocFilmT) {
            FilmInfo filmInfo = new FilmInfo();
            filmInfo.setFilmName(filmT.getFilmName());
            filmInfo.setFilmType(filmT.getFilmType());
            filmInfo.setFilmScore(filmT.getFilmScore());
            filmInfo.setFilmId(filmT.getUuid()+"");
            filmInfo.setImgAddress(filmT.getImgAddress());
            filmInfo.setExpectNum(String.valueOf(filmT.getFilmPresalenum()));
            filmInfo.setBoxNum(filmT.getFilmBoxOffice());
            filmInfo.setScore(filmT.getFilmScore());
            filmInfo.setShowTime(DateUtil.getDay(filmT.getFilmTime()));
            res.add(filmInfo);
        }
        return res;
    }

    @Override
    public FilmVO getHotFilms(boolean isLimited, int number) {
        FilmVO filmVO = new FilmVO();
        List<FilmInfo> filmInfos = new ArrayList<FilmInfo>();

        //限制影片为热映状态
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status",1);
        if (isLimited){
            //判断是否为首页需要的内容
            //1.是，则限制展示的条数
            Page<MoocFilmT> page = new Page<>(1,number);
            List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, entityWrapper);
            //封装FilmInfo
            List<FilmInfo> filmInfoList = getFilmInfos(moocFilmTS);
            filmVO.setFilmInfos(filmInfoList);
            filmVO.setFilmNum(moocFilmTS.size());
        }else {
            //2.不是，则是列表页
        }
        return filmVO;

    }

    @Override
    public FilmVO getUpcomingFilms(boolean isLimited, int number) {
        FilmVO filmVO = new FilmVO();
        List<FilmInfo> filmInfos = new ArrayList<FilmInfo>();

        //限制影片为即将上映状态
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status",2);
        if (isLimited){
            //判断是否为首页需要的内容
            //1.是，则限制展示的条数
            Page<MoocFilmT> page = new Page<>(1,number);
            List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, entityWrapper);
            //封装FilmInfo
            List<FilmInfo> filmInfoList = getFilmInfos(moocFilmTS);
            filmVO.setFilmInfos(filmInfoList);
            filmVO.setFilmNum(moocFilmTS.size());
        }else {
            //2.不是，则是列表页
        }
        return filmVO;
    }

    @Override
    public List<FilmInfo> getBoxRanking() {
        //票房排名前10，正在上映的
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status",1);
        Page<MoocFilmT> page = new Page<>(1,10,"film_box_office");
        List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, entityWrapper);
        List<FilmInfo> filmInfoList = getFilmInfos(moocFilmTS);
        return filmInfoList;
    }

    @Override
    public List<FilmInfo> getExpectRanking() {
        //预售票房排名前10，即将上映的

        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status",2);
        Page<MoocFilmT> page = new Page<>(1,10,"film_preSaleNum");
        List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, entityWrapper);
        List<FilmInfo> filmInfoList = getFilmInfos(moocFilmTS);
        return filmInfoList;
    }

    @Override
    public List<FilmInfo> getTop() {
        //正在上映的，评分前10
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status",1);
        Page<MoocFilmT> page = new Page<>(1,10,"film_score");
        List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, entityWrapper);
        List<FilmInfo> filmInfoList = getFilmInfos(moocFilmTS);
        return filmInfoList;
    }
}
