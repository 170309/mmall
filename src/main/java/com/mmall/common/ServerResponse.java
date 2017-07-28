package com.mmall.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 *  通用响应对象
 *  保证序列化JSON对象时，如果是null的对象 key也会消失
 */

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T> implements Serializable{

    /**
     * status : 0 成功 1 失败
     */
    private int status;

    private String msg;

    private T data;

    private ServerResponse(int status){
        this.status = status;
    }

    private ServerResponse(int status , String msg){
        this.status = status;
        this.msg = msg;
    }

    private ServerResponse(int status , T data){
        this.status = status;
        this.data = data;
    }

    private ServerResponse(int status , String msg , T data){
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 判断是否是成功的返回
     * JsonIgnore 序列化时无视此方法
     * @return 返回响应状态
     */
    @JsonIgnore
    public boolean isSuccess(){
        return this.status==ResponseCode.SUCCESS.getCode();
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    /**
     * 响应成功
     * @param <T> 泛型的参数
     * @return 泛型ServerResponse对象
     */
    public static <T> ServerResponse<T> createBySuccess(){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    /**
     * 响应成功，并返回数据给前端
     * @param data 需要返回的数据
     * @param <T> 泛型参数
     * @return 成功响应 带数据
     */
    public static <T> ServerResponse<T> createBySuccess(T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),data);
    }



    /**
     * 响应成功，并返回一个消息给前端
     * @param message 需要返回给前端的消息
     * @param <T> 泛型的参数
     * @return 成功响应 带消息
     */
    public static <T> ServerResponse<T> createBySuccess(String message){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),message);
    }

    /**
     * 响应成功 ，返回数据 和消息给 前端
     * @param message 需要返回的消息
     * @param data 需要返回的数据
     * @param <T> 泛型参数
     * @return 成功响应 返回消息 返回数据
     */
    public static <T> ServerResponse<T> createBySuccess(String message,T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),message,data);
    }

    /**
     * 响应失败
     * @param <T> 泛型参数
     * @return 响应失败
     */
    public static <T> ServerResponse<T> createByError(){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }

    /**
     * 响应失败
     * @param message 返回的失败消息
     * @param <T> 泛型参数
     * @return 响应失败
     */
    public static <T> ServerResponse<T> createByError(String errorMessage){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),errorMessage);
    }

    /**
     * 自定义可以响应自定义错误代码的响应类型
     * @param errorCode 错误码
     * @param errorMessage 错误消息
     * @param <T> 泛型参数
     * @return 响应失败 返回错误码 返回错误消息
     */
    public static <T> ServerResponse<T> createByError(int errorCode,String errorMessage){
        return new ServerResponse<T>(errorCode,errorMessage);
    }
}
