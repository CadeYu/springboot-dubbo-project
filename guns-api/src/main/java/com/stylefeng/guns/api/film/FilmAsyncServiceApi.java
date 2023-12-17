package com.stylefeng.guns.api.film;

import com.stylefeng.guns.api.film.vo.*;

import java.util.List;

public interface FilmAsyncServiceApi {

    //获取影片相关的其他信息
    FilmDescVO getFilmDesc(String filmId);

    ActorVO getDectInfo(String filmId);

    ImgVO getImgs(String filmId);

    List<ActorVO> getActor(String filmId);


}
