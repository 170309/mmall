package com.mmall.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.mmall.common.ServerResponse;
import com.mmall.mmall.vo.OrderVo;

import java.util.Map;

/**
 * 订单管理
 */
public interface IOrderService {

    /**
     * 付款<br/>
     * @param orderNo 订单号<br/>
     * @param userId 用户ID<br/>
     * @param path 二维码上传目录<br/>
     * @return
     */
    ServerResponse pay(Long orderNo, Integer userId, String path);

    /**
     * 阿里云回调验证<br/>
     * @param params 验证参数<br/>
     * @return
     */
    ServerResponse aliCallback(Map<String, String> params);

    /**
     * 查询订单状态<br/>
     * @param userId 用户ID<br/>
     * @param orderNo 订单ID<br/>
     * @return
     */
    ServerResponse queryOrderPayStatus(Integer userId, Long orderNo);

    /**
     * 创建订单<br/>
     * @param userId 用户ID<br/>
     * @param shippingId 地址ID<br/>
     * @return
     */
    ServerResponse create(Integer userId, Integer shippingId);

    /**
     * 取消订单<br/>
     * @param userId 用户ID<br/>
     * @param orderNo 订单ID<br/>
     * @return
     */
    ServerResponse<String> cancel(Integer userId, Long orderNo);

    /**
     * 获取购物车中剩下商品信息
     * @param userId
     * @return
     */
    ServerResponse getOrderCartProduct(Integer userId);

    /**
     * 获取订单详情
     * @param userId
     * @param orderNo
     * @return
     */
    ServerResponse<OrderVo> getOrderDetail(Integer userId, Long orderNo);

    /**
     * 获取所有的订单
     * @param userId
     * @param pageNum
     * @param pageSize
     * @return
     */
    ServerResponse<PageInfo> getOrderList(Integer userId, int pageNum, int pageSize);

    /**
     * 后台订单管理
     * @param pageNum
     * @param pageSize
     * @return
     */
    ServerResponse<PageInfo> manageList(int pageNum, int pageSize);

    /**
     * 后台订单详情
     * @param orderNo
     * @return
     */
    ServerResponse<OrderVo> manageDetail(Long orderNo);

    /**
     * 后台订单查找
     * @param orderNo
     * @param pageNum
     * @param pageSize
     * @return
     */
    ServerResponse<PageInfo> manageSearch(Long orderNo, int pageNum, int pageSize);

    /**
     * 后台订单发货
     * @param orderNo
     * @return
     */
    ServerResponse<String> manageSendGoods(Long orderNo);
}
