package com.stylefeng.guns.rest.modular.vo;

import lombok.Data;

/**
 * 返回值专用类
 */
@Data
public class ResponseVo<T> {

    //状态 0成功，1失败，999系统异常
    private int status;
    //信息
    private String msg;

    //返回的数据实体
    private T data;

    //图片前缀
    private String imgPre;


    private int nowPage;

    private int totalPage;

    //单例模式，不允许外面创建实体
    public ResponseVo(){}



    public static<T> ResponseVo success(T data){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setStatus(0);
        responseVo.setData(data);
        return responseVo;
    }

    public static<T> ResponseVo success(String msg){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setStatus(0);
        responseVo.setMsg(msg);
        return responseVo;
    }



    public static<T> ResponseVo success(String imgPre,T data,int totalPage,int nowPage){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setStatus(0);
        responseVo.setData(data);
        responseVo.setImgPre(imgPre);
        responseVo.setNowPage(nowPage);
        responseVo.setTotalPage(totalPage);
        return responseVo;
    }

    public static<T> ResponseVo success(String imgPre,T data){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setStatus(0);
        responseVo.setData(data);
        responseVo.setImgPre(imgPre);
        return responseVo;
    }
    public static<T> ResponseVo serviceFail(String msg){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setStatus(1);
        responseVo.setMsg(msg);
        return responseVo;
    }

    public static<T> ResponseVo appFail(String msg){
        ResponseVo responseVo = new ResponseVo();
        responseVo.setStatus(999);
        responseVo.setMsg(msg);
        return responseVo;
    }

}
