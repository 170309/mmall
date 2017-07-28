package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;

/**
 * 品类管理service
 * @author 1.01
 *
 */
public interface ICategoryService {

    /**
     * 添加品类<br/>
     * @param categoryName 品类名称<br/>
     * @param parentId 品类父级ID<br/>
     * @return
     */
    ServerResponse addCategory(String categoryName , Integer parentId);

    /**
     * 更新品类信息<br/>
     * @param categoryName 新品了名称<br/>
     * @param categoryId 需要更新的品类ID
     * @return
     */
    ServerResponse updateCategory(String categoryName,Integer categoryId);

    /**
     * 获取品类子节点<br/>
     * @param categoryId 需要获取的品类ID<br/>
     * @return
     */
    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);

    /**
     * 无限级递归获取子节点<br/>
     * @param categoryId 需要获取的品类ID<br/>
     * @return 一个品类ID的List
     */
    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);

}
