package com.mmall.mmall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mmall.mmall.common.ServerResponse;
import com.mmall.mmall.dao.CategoryMapper;
import com.mmall.mmall.pojo.Category;
import com.mmall.mmall.service.ICategoryService;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * Created by lucifer on 17-6-12.
 */
@Service("iCategoryService")
public class ICategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    private Logger logger = LoggerFactory.getLogger(ICategoryServiceImpl.class);
    /*
        添加品类
        1.校验传递过来的categoryName 和 parentId 是否合法
        2.创建一个新的品类对象
        3.插入数据库， 返回状态提示
     */
    @Test
    public ServerResponse addCategory(String categoryName , Integer parentId){
        if(categoryName == null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByError("添加品类参数传递错误");
        }

        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);//分类可用

        int rowCount = categoryMapper.insert(category);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("添加品类成功");
        }

        return ServerResponse.createByError("添加品类失败");
    }

    /*
        更新品类名
        1.校验品类名和ID 是否存在
        2.创建新对象 更新数据库
     */
    public ServerResponse updateCategory(String categoryName, Integer categoryId){
        if(categoryId == null || StringUtils.isBlank(categoryName)){
            return ServerResponse.createByError("品类参数错误");
        }

        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);

        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);
        if(rowCount > 0){
            return ServerResponse.createBySuccess("更新品类名字成功");
        }
        return ServerResponse.createByError("更新品类名字失败");

    }

    /*
        查找当前分类的子分类
        1.查询数据库 得到List集合
        2.如果集合为空 打印日志
     */
    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId){
        List<Category> categoryList = categoryMapper.selectCategoryParallelByCategoryId(categoryId);
        if(CollectionUtils.isEmpty(categoryList)){
            logger.info("未找到当前分类的子分类");
        }
        return ServerResponse.createBySuccess(categoryList);
    }


    /*
        递归无限级菜单
        1.初始化一个Set 使用 Guawa Sets 初始化
        2.调用递归方法
        3.初始化List 同为 Guawa Lists 初始化
        4.在categoryId 不为null的情况下 for categorySet add到categoryIdList 中返回给前端
     */
    public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId){
        Set<Category> categorySet = Sets.newHashSet();
        findChildCategory(categorySet,categoryId);
        List<Integer> categoryIdList = Lists.newArrayList();
        if(categoryId != null){
            for(Category categoryItem: categorySet){
                categoryIdList.add(categoryItem.getId());
            }
        }
        return ServerResponse.createBySuccess(categoryIdList);

    }

    /*
        递归方法的使用，
        1.使用Set来做递归
        2.在参数中加入Set集合，和查询条件
        3.只要查询到的结果不为空 则把该结果放入Set 集合
        4.使用LIst 存储该Id 下的所有子节点
        5.遍历IdList 使用id递归调用该方法，只要没有到最后就一直执行查询
        6.返回该Set 集合
     */
    public Set<Category> findChildCategory(Set<Category> categorySet, Integer categoryId){
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if(category != null){
            categorySet.add(category);
        }
        List<Category> categoryList = categoryMapper.selectCategoryParallelByCategoryId(categoryId);
        for(Category categoryItem : categoryList){
            findChildCategory(categorySet,categoryItem.getId());
        }
        return categorySet;
    }

}
