package com.mmall.mmall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mmall.mmall.common.ServerResponse;
import com.mmall.mmall.dao.ShippingMapper;
import com.mmall.mmall.pojo.Shipping;
import com.mmall.mmall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by lucifer on 17-6-18.
 */
@Service("iShippingService")
public class IShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;

    public ServerResponse add(Integer userId, Shipping shipping){
        shipping.setUserId(userId);
        int rowCount = shippingMapper.insert(shipping);
        if (rowCount > 0) {
            Map result = Maps.newHashMap();
            result.put("shippingId",shipping.getId());
            return ServerResponse.createBySuccess("新建地址成功",result);
        }
        return ServerResponse.createByError("新建地址失败");
    }

    public ServerResponse<String> del(Integer userId, Integer shippingId){

        int resultCount = shippingMapper.deleteByShippingIdUserId(shippingId,userId);
        if (resultCount > 0) {
            return ServerResponse.createBySuccess("删除地址成功");
        }
        return ServerResponse.createByError("删除地址失败");
    }

    public ServerResponse update(Integer userId, Shipping shipping){
        //防止横向越权的发生，userId 可能被伪造
        shipping.setUserId(userId);
        int rowCount = shippingMapper.updateByShipping(shipping);
        if (rowCount > 0) {
            return ServerResponse.createBySuccess("更改地址成功");
        }
        return ServerResponse.createByError("更改地址失败");
    }

    public ServerResponse<Shipping> select(Integer userId, Integer shippingId){
        Shipping shipping = shippingMapper.selectByShippingIdUserId(userId,shippingId);
        if (shipping == null) {
            return ServerResponse.createByError("无法查询到地址");
        }
        return ServerResponse.createBySuccess("查询到地址",shipping);
    }

    public ServerResponse<PageInfo > list(Integer userId, int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Shipping> shippingList = shippingMapper.selectByUserId(userId);
        PageInfo pageInfo = new PageInfo(shippingList);
        return ServerResponse.createBySuccess(pageInfo);
    }

}
