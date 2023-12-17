package com.stylefeng.guns.rest.modular.film.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.film.FilmAsyncServiceApi;
import com.stylefeng.guns.api.film.FilmServiceApi;
import com.stylefeng.guns.api.film.vo.*;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Service(interfaceClass = FilmAsyncServiceApi.class)
public class DefaultAsyncFilmServiceImpl implements FilmAsyncServiceApi {

    @Autowired
    private MoocActorTMapper moocActorTMapper;
    @Autowired
    private MoocFilmInfoTMapper moocFilmInfoTMapper;



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
