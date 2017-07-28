package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.vo.CartVo;

/**
 * @author 1.01
 * @version v0.1
 * 购物车service
 */
public interface ICartService {
    /**
     * 购物车列表<br/>
     * @param userId 用户ID<br/>
     * @return CartVo
     */
    ServerResponse<CartVo> list(Integer userId);

    /**
     * 添加商品到购物车<br/>
     * @param userId 用户ID<br/>
     * @param productId 商品ID<br/>
     * @param count 商品数量<br/>
     * @return CartVO
     */
    ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);

    /**
     * 更新购物车信息<br/>
     * @param userId 用户ID<br/>
     * @param productId 商品ID<br/>
     * @param count 商品数量<br/>
     * @return CartVo
     */
    ServerResponse<CartVo> update(Integer userId,Integer productId,Integer count);

    /**
     * 删除购物车中的商品<br/>
     * @param userId 用户ID<br/>
     * @param productIds 商品ID<br/>
     * @return CartVO
     */
    ServerResponse<CartVo> deleteProduct(Integer userId,String productIds);

    /**
     * 选中或者取消选中商品<br/>
     * @param userId 用户ID<br/>
     * @param productId 商品ID<br/>
     * @param checked 选中或者取消选中代码<br/>
     * @return CartVO
     */
    ServerResponse<CartVo> selectOrUnSelectProduct(Integer userId ,Integer productId, Integer checked);

    /**
     * 获取购物车中商品总数<br/>
     * @param userId 用户ID
     * @return 当前购物车中的商品数量
     */
    ServerResponse<Integer> getCartProductCount(Integer userId);
}
