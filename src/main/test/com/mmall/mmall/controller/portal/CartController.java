package com.mmall.mmall.controller.portal;

import com.mmall.mmall.common.Const;
import com.mmall.mmall.common.ResponseCode;
import com.mmall.mmall.common.ServerResponse;
import com.mmall.mmall.pojo.User;
import com.mmall.mmall.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by lucifer on 17-6-15.
 */
@Controller
@RequestMapping("/cart/")
public class CartController {

    @Autowired
    private ICartService iCartService;

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse list(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user ==null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.list(user.getId());
    }

    @RequestMapping("add.do")
    @ResponseBody
    public ServerResponse add(HttpSession session , Integer count, Integer productId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user ==null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.add(user.getId(),productId,count);
    }

    @RequestMapping("update.do")
    @ResponseBody
    public ServerResponse update(HttpSession session , Integer count, Integer productId){
        //参数检测
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user ==null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.update(user.getId(),productId,count);
    }


    @RequestMapping("delete_product.do")
    @ResponseBody
    public ServerResponse deleteProduct(HttpSession session , String productIds){
        //参数检测
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user ==null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.deleteProduct(user.getId(),productIds);
    }

    @RequestMapping("select_all.do")
    @ResponseBody
    public ServerResponse selectAll(HttpSession session){
        //参数检测
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user ==null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelectProduct(user.getId(),null, Const.Cart.CHECKED);
    }

    @RequestMapping("un_select_all.do")
    @ResponseBody
    public ServerResponse unSelectAll(HttpSession session){
        //参数检测
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user ==null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelectProduct(user.getId(),null, Const.Cart.UN_CHECKED);
    }

    @RequestMapping("select.do")
    @ResponseBody
    public ServerResponse select(HttpSession session , Integer productId){
        //参数检测
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user ==null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelectProduct(user.getId(),productId, Const.Cart.CHECKED);
    }

    @RequestMapping("un_select.do")
    @ResponseBody
    public ServerResponse unSelect(HttpSession session , Integer productId){
        //参数检测
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user ==null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.selectOrUnSelectProduct(user.getId(),productId, Const.Cart.UN_CHECKED);
    }

    @RequestMapping("get_cart_product_count.do")
    @ResponseBody
    public ServerResponse getCartProductCount(HttpSession session){
        //参数检测
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user ==null) {
            return ServerResponse.createByError(ResponseCode.NEED_LOGIN.getCode(), ResponseCode.NEED_LOGIN.getDesc());
        }
        return iCartService.getCartProductCount(user.getId());
    }




}
