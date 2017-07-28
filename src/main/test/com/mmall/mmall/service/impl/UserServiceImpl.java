package com.mmall.mmall.service.impl;

import com.mmall.mmall.common.Const;
import com.mmall.mmall.common.ServerResponse;
import com.mmall.mmall.common.TokenCache;
import com.mmall.mmall.dao.UserMapper;
import com.mmall.mmall.pojo.User;
import com.mmall.mmall.service.IUserService;
import com.mmall.mmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    /*
     * 1.校验用户名是否存在
     * 2.校验密码是否正确 使用MD5加密后的值校验
     * 3.将user对象中的密码直空 返回给前端
     */
    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);
        if(resultCount == 0){
            return ServerResponse.createByError("用户名不存在");
        }
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectLogin(username,md5Password);
        if(user == null){
            return ServerResponse.createByError("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功",user);
    }

    /*
    用户注册
    1.校验用户名是否存在
    2.校验email是否存在
    3.插入数据到数据库
     */
    @Override
    public ServerResponse<String> register(User user){
        ServerResponse validResponse = this.checkVaild(user.getUsername(), Const.USERNAME);
        if(!validResponse.isSuccess()){
            return validResponse;
        }
        validResponse = this.checkVaild(user.getEmail(), Const.EMAIL);
        if(!validResponse.isSuccess()){
            return validResponse;
        }
        user.setRole(Const.Role.ROLE_CUSTOMER);
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCount = userMapper.insert(user);
        if(resultCount==0){
            return ServerResponse.createByError("注册失败");
        }
        return ServerResponse.createBySuccess("注册成功");
    }

    /**
     * 检查用户名或者email是否有效，并自动的校验用户名或者email是否有效
     * @param str username 或者email
     * @param type 类型判断 为userna 还是email
     * @return
     */
    public ServerResponse<String> checkVaild(String str, String type){
        if(StringUtils.isNotBlank(type)){
            if(Const.USERNAME.equals(type)){
                int resultCount = userMapper.checkUsername(str);
                if(resultCount > 0){
                    return ServerResponse.createByError("用户名已存在");
                }
            }
            if(Const.EMAIL.equals(type)){
                int resultCount = userMapper.checkEmail(str);
                if(resultCount>0){
                    return ServerResponse.createByError("email已存在");
                }
            }
        }else{
            return ServerResponse.createByError("参数错误");
        }
        return ServerResponse.createBySuccess("校验成功");
    }


    /*
    获取用户密保问题
    1.校验用户是否存在
    2.如果用户存在则获取该用户的密保问题
    3.校验问题是否为空
    4.为空则返回错误，不为空返回该问题
     */
    public ServerResponse<String> selectQuestion(String username){
        ServerResponse validResponse = this.checkVaild(username, Const.USERNAME);
        if(validResponse.isSuccess()){
            return ServerResponse.createByError("用户不存在");
        }

        String quetion = userMapper.selectQuetionByUsername(username);

        if(StringUtils.isNotBlank(quetion)){
            return ServerResponse.createBySuccess(quetion);
        }
        return ServerResponse.createByError("找回密码的问题为空");
    }

    /*
    验证密保问题的正确性，
    1.验证用户的密保问题答案是否 正确
    2.如果答案正确，则使用UUID生成一个随机值，拼接“token_” + username + forgettoken 并设置本地缓存
    3.返回该token给前端
    4.否则返回错误信息给前端
     */
    public ServerResponse<String> checkAnswer(String username, String question, String answer){
        int resultCount = userMapper.checkAnswer(username,question,answer);
        if(resultCount > 0){
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PREFIX+username,forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByError("问题答案错误");
    }

    /*
    忘记密码时 使用密保问题重置密码
    1.验证token时候为空
    2.验证用户是否存在
    3.获取本地缓存中的token
    4.验证token是否为空或者没有token
    5.通过dao层更新用户数据
     */
    public ServerResponse<String> forgetResetPassword(String username , String passwordNew, String tokenforget){
        if(StringUtils.isBlank(tokenforget)){
            return ServerResponse.createByError("参数错误，token需要传递");
        }
        ServerResponse validResponse = this.checkVaild(username, Const.USERNAME);
        if(validResponse.isSuccess()){
            return ServerResponse.createByError("用户不存在");
        }
        //获取本地缓存中的token
        String token  = TokenCache.getKey(TokenCache.TOKEN_PREFIX+username);
        if(StringUtils.isBlank(token)){
            return ServerResponse.createByError("token 不存在或者过期");
        }
        if(StringUtils.equals(tokenforget,token)){
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            int resultCount = userMapper.updatePasswordByUsername(username,md5Password);
            if(resultCount > 0){
                return ServerResponse.createBySuccess("修改成功");
            }
        }else{
            return ServerResponse.createByError("token 错误，请重新获取重置密码的token");
        }
        return ServerResponse.createByError("修改密码失败");
    }


    /*
    登录状态下更新密码
    1.校验旧密码 防止横向越权的发生
    2.更新密码
     */
    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew , User user){
        int resultCount = userMapper.checkPassword(MD5Util.MD5EncodeUtf8(passwordOld),user.getId());
        if(resultCount == 0){
            return ServerResponse.createByError("旧密码错误");
        }
        user.setPassword(MD5Util.MD5EncodeUtf8(passwordNew));
        resultCount = userMapper.updateByPrimaryKeySelective(user);
        if(resultCount>0){
            return ServerResponse.createBySuccess("密码更新成功");
        }
        return ServerResponse.createBySuccess("密码更新失败");
    }

    /*
    更新个人用户信息
    1.校验待更细的email是否被使用
    2.new 一个新的User 对象 用来更新
     */
    public ServerResponse<User> updateInfomation(User user){
        int resultCount = userMapper.checkEmailByUserId(user.getEmail(),user.getId());
        if(resultCount > 0){
            return ServerResponse.createByError("email已经存在 ,请更换email");
        }

        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());

        resultCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if(resultCount> 0){
            return ServerResponse.createBySuccess("更新个人信息成功");
        }
        return ServerResponse.createByError("更新个人信息失败");
    }

    /*
    获取当前用户信息
    1.查询数据库 查找当前id的用户信息
     */
    public ServerResponse<User> getInfomation(Integer id){
        User user = userMapper.selectByPrimaryKey(id);
        if(user == null){
            return ServerResponse.createByError("找不到当前用户");
        }

        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }

    /*
    backend
    校验是否是管理员
     */
    public ServerResponse checkAdmin(User user){
        if(user != null && user.getRole().intValue()== Const.Role.ROLE_ADMIN){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();



    }
}
