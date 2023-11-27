package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.film.FilmServiceApi;
import com.stylefeng.guns.api.film.vo.BannerVO;
import com.stylefeng.guns.rest.modular.film.vo.FilmIndexVO;
import com.stylefeng.guns.rest.modular.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
            filmIndexVO.setHotFilms(filmServiceApi.getHotFilms(true,8));
            //即将上映的电影
            filmIndexVO.setSoonFilms(filmServiceApi.getUpcomingFilms(true,8));
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
}
