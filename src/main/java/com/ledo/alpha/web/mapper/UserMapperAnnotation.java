package com.ledo.alpha.web.mapper;

import com.ledo.alpha.web.entity.repo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * Created by Thanple on 2017/2/25.
 */

@Mapper
public interface UserMapperAnnotation {

    @Select("select * from user")
    List<User> find();

    @Insert("insert into user(name,email,create_time) "+
            "values(#{name,jdbcType=VARCHAR},#{email,jdbcType=VARCHAR},#{createTime,jdbcType=DATE})")
    int insert(@Param("name") String name, @Param("email") String email, @Param("createTime") Date createTime);
}
