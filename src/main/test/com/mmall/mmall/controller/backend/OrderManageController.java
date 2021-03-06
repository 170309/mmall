package com.mmall.mmall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.mmall.mmall.common.Const;
import com.mmall.mmall.common.ResponseCode;
import com.mmall.mmall.common.ServerResponse;
import com.mmall.mmall.pojo.User;
import com.mmall.mmall.service.IOrderService;
import com.mmall.mmall.service.IUserService;
import com.mmall.mmall.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by lucifer on 17-7-14.
 */
@Controller
@RequestMapping("/manage/order/")
public class OrderManageController {


    @Autowired
    private IOrderService iOrderService;

    @Autowired
    private IUserService iUserService;

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> orderList(HttpSession session,
                                              @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                              @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请使用管理员登录");
        }
        if(iUserService.checkAdmin(user).isSuccess()){
            return iOrderService.manageList(pageNum,pageSize);
        }else{
            return ServerResponse.createByError("没有权限操作，请使用管理员登录");
        }
    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<OrderVo> orderList(HttpSession session, Long orderNo){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请使用管理员登录");
        }
        if(iUserService.checkAdmin(user).isSuccess()){
            return iOrderService.manageDetail(orderNo);
        }else{
            return ServerResponse.createByError("没有权限操作，请使用管理员登录");
        }
    }

    @RequestMapping("order_search.do")
    @ResponseBody
    public ServerResponse<PageInfo> searchByOrderNo(HttpSession session, Long orderNo,
                                                    @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                                                    @RequestParam(value = "pageSize",defaultValue = "10") int pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请使用管理员登录");
        }
        if(iUserService.checkAdmin(user).isSuccess()){
            return iOrderService.manageSearch(orderNo,pageNum,pageSize);
        }else{
            return ServerResponse.createByError("没有权限操作，请使用管理员登录");
        }
    }

    @RequestMapping("send_goods.do")
    @ResponseBody
    public ServerResponse<String> sendGoods(HttpSession session, Long orderNo){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请使用管理员登录");
        }
        if(iUserService.checkAdmin(user).isSuccess()){
            return iOrderService.manageSendGoods(orderNo);
        }else{
            return ServerResponse.createByError("没有权限操作，请使用管理员登录");
        }
    }
}
