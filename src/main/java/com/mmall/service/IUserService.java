package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

/**
 * 用户管理
 */
public interface IUserService {
    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    ServerResponse<User> login(String username , String password);

    /**
     * 注册
     * @param user
     * @return
     */
    ServerResponse<String> register(User user);

    /**
     * 验证用户的有效性
     * @param str
     * @param type
     * @return
     */
    ServerResponse<String> checkVaild(String str,String type);

    /**
     * 获取密保问题
     * @param username
     * @return
     */
    ServerResponse<String> selectQuestion(String username);

    /**
     * 验证密保答案
     * @param username
     * @param quetion
     * @param answer
     * @return
     */
    ServerResponse<String> checkAnswer(String username,String quetion,String answer);

    /**
     * 忘记密码时，以密保问题重置密码
     * @param username
     * @param passwordNew
     * @param tokenforget
     * @return
     */
    ServerResponse<String> forgetResetPassword(String username ,String passwordNew,String tokenforget);

    /**
     * 登录状态下，重置密码
     * @param passwordOld
     * @param passwordNew
     * @param user
     * @return
     */
    ServerResponse<String> resetPassword(String passwordOld, String passwordNew ,User user);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    ServerResponse<User> updateInfomation(User user);

    /**
     * 获取当前用户信息
     * @param id
     * @return
     */
    ServerResponse<User> getInfomation(Integer id);

    /**
     * 验证用户是否为管理员
     * @param user
     * @return
     */
    ServerResponse checkAdmin(User user);
}
