package com.stylefeng.guns.rest.modular.film.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.film.FilmServiceApi;
import com.stylefeng.guns.api.film.vo.*;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.*;
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
    @Autowired
    private MoocCatDictTMapper moocCatDictTMapper;
    @Autowired
    private MoocSourceDictTMapper moocSourceDictTMapper;
    @Autowired
    private MoocYearDictTMapper moocYearDictTMapper;
    @Autowired
    private MoocActorTMapper moocActorTMapper;
    @Autowired
    private MoocFilmInfoTMapper moocFilmInfoTMapper;



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
    public FilmVO getHotFilms(boolean isLimited, int number,int nowPage,int yearId,int sourceId,int catId,int sortId) {
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
            Page<MoocFilmT> page = null;
            // 根据sortId的不同，来组织不同的Page对象
            // 1-按热门搜索，2-按时间搜索，3-按评价搜索
            switch (sortId){
                case 1 :
                    page = new Page<>(nowPage,number,"film_box_office");
                    break;
                case 2 :
                    page = new Page<>(nowPage,number,"film_time");
                    break;
                case 3 :
                    page = new Page<>(nowPage,number,"film_score");
                    break;
                default:
                    page = new Page<>(nowPage,number,"film_box_office");
                    break;
            }

            if (sourceId != 99){
                entityWrapper.eq("film_source",sourceId);
            }
            if (catId != 99){
                String catStr = "%#"+catId+"#%";
                entityWrapper.like("film_cats",catStr);
            }
            if (yearId != 99){
                entityWrapper.eq("film_date",yearId);
            }


            List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, entityWrapper);
            Integer counts = moocFilmTMapper.selectCount(entityWrapper);
            int totalPages = (counts/number)+1;



            //封装FilmInfo
            List<FilmInfo> filmInfoList = getFilmInfos(moocFilmTS);
            filmVO.setFilmInfos(filmInfoList);
            filmVO.setFilmNum(moocFilmTS.size());
            filmVO.setTotalPage(totalPages);
            filmVO.setNowPage(nowPage);
        }
        return filmVO;

    }

    @Override
    public FilmVO getUpcomingFilms(boolean isLimited, int number,int nowPage,int yearId,int sourceId,int catId,int sortId) {
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
            Page<MoocFilmT> page = null;
            // 根据sortId的不同，来组织不同的Page对象
            // 1-按热门搜索，2-按时间搜索，3-按评价搜索
            switch (sortId){
                case 1 :
                    page = new Page<>(nowPage,number,"film_preSaleNum");
                    break;
                case 2 :
                    page = new Page<>(nowPage,number,"film_time");
                    break;
                case 3 :
                    page = new Page<>(nowPage,number,"film_preSaleNum");
                    break;
                default:
                    page = new Page<>(nowPage,number,"film_preSaleNum");
                    break;
            }
            if (sourceId != 99){
                entityWrapper.eq("film_source",sourceId);
            }
            if (catId != 99){
                String catStr = "%#"+catId+"#%";
                entityWrapper.like("film_cats",catStr);
            }
            if (yearId != 99){
                entityWrapper.eq("film_date",yearId);
            }


            List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, entityWrapper);
            Integer counts = moocFilmTMapper.selectCount(entityWrapper);
            int totalPages = (counts/number)+1;


            //封装FilmInfo
            List<FilmInfo> filmInfoList = getFilmInfos(moocFilmTS);
            filmVO.setFilmInfos(filmInfoList);
            filmVO.setFilmNum(moocFilmTS.size());
            filmVO.setTotalPage(totalPages);
            filmVO.setNowPage(nowPage);

        }
        return filmVO;
    }

    @Override
    public FilmVO getClassicialFilms(int number, int nowPage, int yearId, int sourceId, int catId,int sortId) {

        FilmVO filmVO = new FilmVO();
        List<FilmInfo> filmInfos = new ArrayList<FilmInfo>();

        //限制影片为即将上映状态
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status",3);
        Page<MoocFilmT> page = null;
        // 根据sortId的不同，来组织不同的Page对象
        // 1-按热门搜索，2-按时间搜索，3-按评价搜索
        switch (sortId){
            case 1 :
                page = new Page<>(nowPage,number,"film_box_office");
                break;
            case 2 :
                page = new Page<>(nowPage,number,"film_time");
                break;
            case 3 :
                page = new Page<>(nowPage,number,"film_score");
                break;
            default:
                page = new Page<>(nowPage,number,"film_box_office");
                break;
        }
        if (sourceId != 99){
            entityWrapper.eq("film_source",sourceId);
        }
        if (catId != 99){
            String catStr = "%#"+catId+"#%";
            entityWrapper.like("film_cats",catStr);
        }
        if (yearId != 99){
            entityWrapper.eq("film_date",yearId);
        }


        List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page, entityWrapper);
        Integer counts = moocFilmTMapper.selectCount(entityWrapper);
        int totalPages = (counts/number)+1;


        //封装FilmInfo
        List<FilmInfo> filmInfoList = getFilmInfos(moocFilmTS);
        filmVO.setFilmInfos(filmInfoList);
        filmVO.setFilmNum(moocFilmTS.size());
        filmVO.setTotalPage(totalPages);
        filmVO.setNowPage(nowPage);

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

    @Override
    public List<CatVO> getCat() {
        List<CatVO> res = new ArrayList<>();
        List<MoocCatDictT> moocCatDictTS = moocCatDictTMapper.selectList(null);
        for (MoocCatDictT moocCatDictT : moocCatDictTS) {
            CatVO catVO = new CatVO();
            catVO.setCatName(moocCatDictT.getShowName());
            catVO.setCatId(moocCatDictT.getUuid()+"");
            res.add(catVO);
        }
        return res;
    }

    @Override
    public List<SourceVO> getSource() {
        List<SourceVO> res = new ArrayList<>();
        List<MoocSourceDictT> moocSourceDictTS = moocSourceDictTMapper.selectList(null);
        for (MoocSourceDictT moocSourceDictT : moocSourceDictTS) {
            SourceVO sourceVO = new SourceVO();
            sourceVO.setSourceId(moocSourceDictT.getUuid()+"");
            sourceVO.setSourceName(moocSourceDictT.getShowName());
            res.add(sourceVO);
        }
        return res;
    }

    @Override
    public List<YearVO> getYear() {
        List<YearVO> res = new ArrayList<>();
        List<MoocYearDictT> moocYearDictTS = moocYearDictTMapper.selectList(null);
        for (MoocYearDictT moocYearDictT : moocYearDictTS) {
            YearVO year = new YearVO();
            year.setYearName(moocYearDictT.getShowName());
            year.setYearId(moocYearDictT.getUuid()+"");
            res.add(year);
        }

        return res;
    }

    @Override
    public FilmDetailVO getFilmDetail(int searchType, String searchParam) {
        //searchType：1，按名称，2，按ID

        FilmDetailVO filmDetailVO = null;
        if (searchType == 1) {
            filmDetailVO = moocFilmTMapper.getFilmDetailById(searchParam);
        }else {
            filmDetailVO = moocFilmTMapper.getFilmDetailByName(searchParam);
        }
        return filmDetailVO;
    }


    private MoocFilmInfoT getMoocFilmInfo(String filmId){
        MoocFilmInfoT moocFilmInfoT = new MoocFilmInfoT();
        moocFilmInfoT.setFilmId(filmId);
        moocFilmInfoT = moocFilmInfoTMapper.selectOne(moocFilmInfoT);
        return moocFilmInfoT;
    }


    @Override
    public FilmDescVO getFilmDesc(String filmId) {
        MoocFilmInfoT moocFilmInfo = getMoocFilmInfo(filmId);
        FilmDescVO filmDescVO = new FilmDescVO();
        filmDescVO.setBiography(moocFilmInfo.getBiography());
        filmDescVO.setFilmId(moocFilmInfo.getFilmId());
        return filmDescVO;
    }

    @Override
    public ActorVO getDectInfo(String filmId) {
        MoocFilmInfoT moocFilmInfo = getMoocFilmInfo(filmId);

        Integer directorId = moocFilmInfo.getDirectorId();
        MoocActorT director = moocActorTMapper.selectById(directorId);
        ActorVO actorVO = new ActorVO();
        actorVO.setDirectorName(director.getActorName());
        actorVO.setImgAddress(director.getActorImg());
        return actorVO;
    }

    @Override
    public ImgVO getImgs(String filmId) {
        MoocFilmInfoT moocFilmInfo = getMoocFilmInfo(filmId);
        ImgVO img = new ImgVO();
        String filmImgs = moocFilmInfo.getFilmImgs();
        String[] split = filmImgs.split(",");
        img.setMainImg(split[0]);
        img.setImg01(split[1]);
        img.setImg02(split[2]);
        img.setImg03(split[3]);
        img.setImg04(split[4]);
        return img;
    }

    @Override
    public List<ActorVO> getActor(String filmId) {
        List<ActorVO> actors = moocActorTMapper.getActors(filmId);
        return actors;
    }
}
