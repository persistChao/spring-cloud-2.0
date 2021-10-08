package com.answer.mapper;

import com.answer.pojo.Order;
import org.apache.ibatis.annotations.Select;

public interface OrderMapper {

    @Select("select id id , user_id userId,name name,price price,num num from tb_order where id = #{id}")
    Order findById(Long id);
}
