package com.mmall.dao;

import com.mmall.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    Cart selectCartByUserIDProductId(@Param("userId") Integer userId, @Param("productId") Integer productId);

    List<Cart> selectCartByUserId(Integer userId);

    int selectCartProductCheckStatusByUserId(Integer userId);

    int deleteByUserIdProductIds(@Param("userId")Integer userId , @Param("productList")List<String> productList);

    int selectOrUnselectProduct(@Param("userId")Integer userId,@Param("productId") Integer productId,@Param("checked")Integer checked);

    int getCartProductCount(Integer userId);

    List<Cart> selectCheckedCartByUserId(Integer userId);

}