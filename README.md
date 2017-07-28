# mmall

**Pagehelper 分页插件的使用**<br>
``````
1.用PageHelper.start()
2.传入两个参数
    pageNum ：分页页码
    pageSize:每页有多少条数据
    
3.使用PageInfo 组织数据 new 一个PageInfo传入一个List数据源

``````

**Mybatis** 
``````
where 标签的使用
    <where>
          <if test="productName!=null">
            and name like #{productName}
          </if>
          <if test="productId!=null">
            and id = #{productId}
          </if>
    </where>
if子句的结果如果为空则不会拼接
如果不为空则会拼接这段sql语句，并且and | or 会自动转成 where

``````````
