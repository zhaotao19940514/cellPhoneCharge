package com.yxx.integral_exchange.vo;

import com.alibaba.fastjson.JSON;
import com.yxx.integral_exchange.util.Utils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@ApiModel
public class Ret<T> implements Serializable {

    @ApiModelProperty(value = "状态码：20000标示正常，非20000标示异常")
    private Integer code;
    @ApiModelProperty(value = "描述信息（如果有）")
    private String msg;
    @ApiModelProperty(value = "数据")
    private T data;
    @ApiModelProperty(value = "与状态码对应，只有20000才为true")
    private boolean success;
    @ApiModelProperty(value = "接口查询耗时(毫秒)")
    private String took;
    @ApiModelProperty(value = "数据集datas的记录条数")
    private int total;  //不代表真实的标签数，因为维度标签会默认加一个结果

    public Ret(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.success = ResultCode.SUCCESS.getCode().intValue() == code.intValue();
    }

    /** 用于对外服务接口，需要设置耗时、记录数*/
    public Ret(Integer code, String took, int total, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.took = took;
        this.total = total;
        this.data = data;
        this.success = ResultCode.SUCCESS.getCode().equals(code);
    }

    /** 用于对外服务接口，需要设置耗时、记录数*/
    public static <T> Ret<T> success(String took, int total, String msg, T data){
        //code默认设置为200，兼容前期的接口；因此未使用20000的编码ResultCode.SUCCESS.getCode()
        return new Ret<>(ResultCode.SUCCESS.getCode(), took, total, msg, data);
    }

    /** 用于对外服务接口，需要设置耗时、记录数*/
    public static <T> Ret<T> success(String took, int total, T data){
        return success(took,total, ResultCode.SUCCESS.getMessage(),data);
    }
    /**
     * 操作成功（无数据）
     * @return
     */
    public static <T> Ret<T> success() {
        return new Ret<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(),null);
    }

    /**
     * 成功返回结果
     * @param data 获取的数据
     */
    public static <T> Ret<T> success(T data) {
        return new Ret<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T> Ret<T> success(T data, String message) {
        return new Ret<T>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回结果
     */
    public static <T> Ret<T> failed() {
        return failed(ResultCode.FAILED);
    }

    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <T> Ret<T> failed(String message) {
        return new Ret<T>(ResultCode.FAILED.getCode(), message, null);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     */
    public static <T> Ret<T> failed(ResultCode errorCode) {
        return new Ret<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    /**
     * 返回失败结果（带异常信息）
     * @param errorCode
     * @param message
     * @param t
     * @param <T>
     * @return
     */
    public static <T> Ret<T> failed(ResultCode errorCode,String message,Throwable t) {
        if(t!=null) {
            return new Ret<T>(errorCode.getCode(), message, (T) Utils.fmtThrowable(t));
        }else{
            return failed(errorCode,message);
        }
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     * @param message 错误信息
     */
    public static <T> Ret<T> failed(ResultCode errorCode, String message) {
        return new Ret<T>(errorCode.getCode(), message, null);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
