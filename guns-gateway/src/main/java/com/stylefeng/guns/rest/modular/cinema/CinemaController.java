package com.stylefeng.guns.rest.modular.cinema;

import com.stylefeng.guns.api.cinema.vo.CinemaQueryVO;
import com.stylefeng.guns.rest.modular.vo.ResponseVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cinema")
public class CinemaController {



    @RequestMapping(value = "/getCinemas",method = RequestMethod.GET)
    public ResponseVo getCienemas(CinemaQueryVO cinemaQueryVO){

        return null;
    }

    @RequestMapping(value = "/getCondition",method = RequestMethod.GET)
    public ResponseVo getCondition(CinemaQueryVO cinemaQueryVO){

        return null;
    }

    @RequestMapping(value = "/getFields",method = RequestMethod.GET)
    public ResponseVo getFields(Integer cinemaId){

        return null;
    }


    @RequestMapping(value = "/getFieldInfo",method = RequestMethod.POST)
    public ResponseVo getFieldInfo(Integer cinemaId,Integer fieldId){

        return null;
    }





}
