package com.stylefeng.guns.api.film;

import com.stylefeng.guns.api.film.vo.*;

import java.util.List;

public interface FilmServiceApi {

    //获取Banners
    public List<BannerVO> getBanners();

    //获取热门影片

    /**
     *
     * @param isLimited：是否有数量限制
     * @param number：限制多少
     * @return
     */
    public FilmVO getHotFilms(boolean isLimited,int number);

    //获取即将上映的电影
    public FilmVO getUpcomingFilms(boolean isLimited,int number);

    //获取票房排名
    public List<FilmInfo> getBoxRanking();
    //获取人气排名
    public List<FilmInfo> getExpectRanking ();

    //top 100影片
    public List<FilmInfo> getTop();

    //获取影片条件

    //分类条件
    List<CatVO> getCat();

    //片源条件
    List<SourceVO> getSource();

    //年份
    List<YearVO> getYear();


}
