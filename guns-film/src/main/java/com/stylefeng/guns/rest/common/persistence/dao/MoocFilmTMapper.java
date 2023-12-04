package com.stylefeng.guns.rest.common.persistence.dao;

import com.stylefeng.guns.api.film.vo.FilmDetailVO;
import com.stylefeng.guns.rest.common.persistence.model.MoocFilmT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * 影片主表 Mapper 接口
 * </p>
 *
 * @author cade
 * @since 2023-11-26
 */
public interface MoocFilmTMapper extends BaseMapper<MoocFilmT> {
    FilmDetailVO getFilmDetailByName(@Param("filmName") String filmName);

    FilmDetailVO getFilmDetailById(@Param("uuId") String uuId);



}
