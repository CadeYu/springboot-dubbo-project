package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.film.FilmServiceApi;
import com.stylefeng.guns.api.film.vo.*;
import com.stylefeng.guns.rest.modular.film.vo.FilmConditionVO;
import com.stylefeng.guns.rest.modular.film.vo.FilmIndexVO;
import com.stylefeng.guns.rest.modular.film.vo.FilmRequestVO;
import com.stylefeng.guns.rest.modular.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.management.BufferPoolMXBean;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/film")
public class FilmController {

    @Reference(interfaceClass = FilmServiceApi.class,check = false)
    private FilmServiceApi filmServiceApi;

    private final static String IMG_PRE = "http://maoyan.com/";


    //获取首页信息

    /**
     * api网关功能聚合
     * 六个接口，一次请求
     * @return
     */
    @RequestMapping(value = "/getIndex",method = RequestMethod.GET)
    public ResponseVo getIndexInfo(){
        try{
            FilmIndexVO filmIndexVO = new FilmIndexVO();
            //获取banner信息
            filmIndexVO.setBanners(filmServiceApi.getBanners());
            //获取hot电影信息
            filmIndexVO.setHotFilms(filmServiceApi.getHotFilms(true,8,1,99,99,99,1));
            //即将上映的电影
            filmIndexVO.setSoonFilms(filmServiceApi.getUpcomingFilms(true,8,1,99,99,99,1));
            //票房排行榜
            filmIndexVO.setBoxRanking(filmServiceApi.getBoxRanking());
            //受欢迎的榜单
            filmIndexVO.setExpectRanking(filmServiceApi.getExpectRanking());
            //获取前一百
            filmIndexVO.setTop100(filmServiceApi.getTop());
            return ResponseVo.success(IMG_PRE,filmIndexVO);
        }catch (Exception e){
            return ResponseVo.serviceFail(e.getMessage());
        }
    }



    @RequestMapping(value = "/getConditionList",method = RequestMethod.GET)
    public ResponseVo getConditionList(@RequestParam(name = "catId",required = false,defaultValue = "99") String catId,
                                       @RequestParam(name = "sourceId",required = false,defaultValue = "99") String sourceId,
                                       @RequestParam(name = "yearId",required = false,defaultValue = "99") String yearId
                                       )
    {
        FilmConditionVO filmConditionVO = new FilmConditionVO();

        // 标识位
        boolean flag = false;
        // 类型集合
        List<CatVO> cats = filmServiceApi.getCat();
        List<CatVO> catResult = new ArrayList<>();
        CatVO cat = null;
        for(CatVO catVO : cats){
            // 判断集合是否存在catId，如果存在，则将对应的实体变成active状态
            // 6
            // 1,2,3,99,4,5 ->

            if(catVO.getCatId().equals("99")){
                cat = catVO;
                continue;
            }
            if(catVO.getCatId().equals(catId)){
                flag = true;
                catVO.setActive(true);
            }else{
                catVO.setActive(false);
            }
            catResult.add(catVO);
        }
        // 如果不存在，则默认将全部变为Active状态
        if(!flag){
            cat.setActive(true);
            catResult.add(cat);
        }else{
            cat.setActive(false);
            catResult.add(cat);
        }


        // 片源集合
        flag=false;
        List<SourceVO> sources = filmServiceApi.getSource();
        List<SourceVO> sourceResult = new ArrayList<>();
        SourceVO sourceVO = null;
        for(SourceVO source : sources){
            if(source.getSourceId().equals("99")){
                sourceVO = source;
                continue;
            }
            if(source.getSourceId().equals(catId)){
                flag = true;
                source.setActive(true);
            }else{
                source.setActive(false);
            }
            sourceResult.add(source);
        }
        // 如果不存在，则默认将全部变为Active状态
        if(!flag){
            sourceVO.setActive(true);
            sourceResult.add(sourceVO);
        }else{
            sourceVO.setActive(false);
            sourceResult.add(sourceVO);
        }

        // 年代集合
        flag=false;
        List<YearVO> years = filmServiceApi.getYear();
        List<YearVO> yearResult = new ArrayList<>();
        YearVO yearVO = null;
        for(YearVO year : years){
            if(year.getYearId().equals("99")){
                yearVO = year;
                continue;
            }
            if(year.getYearId().equals(catId)){
                flag = true;
                year.setActive(true);
            }else{
                year.setActive(false);
            }
            yearResult.add(year);
        }
        // 如果不存在，则默认将全部变为Active状态
        if(!flag){
            yearVO.setActive(true);
            yearResult.add(yearVO);
        }else{
            yearVO.setActive(false);
            yearResult.add(yearVO);
        }

        filmConditionVO.setCatInfo(catResult);
        filmConditionVO.setSourceInfo(sourceResult);
        filmConditionVO.setYearInfo(yearResult);

        return ResponseVo.success(filmConditionVO);
    }

    @RequestMapping(value = "/getFilms",method = RequestMethod.GET)
    public ResponseVo getFilms(FilmRequestVO requestVO){
        FilmVO filmVO =null;

        switch (requestVO.getShowType()) {
            case 1:
                filmVO = filmServiceApi.getHotFilms(
                        false, requestVO.getPageSize(), requestVO.getNowPage(), requestVO.getYearId(), requestVO.getSourceId(),
                        requestVO.getCatId(), requestVO.getSortId());

                break;
            case 2:
                filmVO = filmServiceApi.getUpcomingFilms(
                        false, requestVO.getPageSize(), requestVO.getNowPage(), requestVO.getYearId(), requestVO.getSourceId(),
                        requestVO.getCatId(), requestVO.getSortId());
                break;
            case 3:
                filmVO = filmServiceApi.getClassicialFilms(
                         requestVO.getPageSize(), requestVO.getNowPage(), requestVO.getYearId(), requestVO.getSourceId(),
                        requestVO.getCatId(), requestVO.getSortId());
                break;
            default:
                filmVO = filmServiceApi.getHotFilms(
                        false, requestVO.getPageSize(), requestVO.getNowPage(), requestVO.getYearId(), requestVO.getSourceId(),
                        requestVO.getCatId(), requestVO.getSortId());
                break;
        }
        return ResponseVo.success(IMG_PRE, filmVO.getFilmInfos(),filmVO.getTotalPage(), filmVO.getNowPage());
    }


    @RequestMapping(value = "/film/{searchParam}",method = RequestMethod.GET)
    public ResponseVo films(@PathVariable("searchParam") String searchParam,int requestType){

        return null;
    }
}