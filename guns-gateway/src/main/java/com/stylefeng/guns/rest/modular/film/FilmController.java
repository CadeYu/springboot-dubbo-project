package com.stylefeng.guns.rest.modular.film;

import com.stylefeng.guns.rest.modular.vo.ResponseVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/film")
public class FilmController {

    //获取首页信息

    /**
     * api网关功能聚合
     * 六个接口，一次请求
     * @return
     */
    @RequestMapping(value = "/getIndex",method = RequestMethod.GET)
    public ResponseVo getIndexInfo(){
        //获取banner信息

        //获取hot电影信息

        //即将上映的电影

        //票房排行榜

        //受欢迎的榜单

        //获取前一百

        return null;
    }
}
