package com.thanple.little.boy.web.mapper;

import com.thanple.little.boy.web.entity.repo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Thanple on 2018/5/11.
 */
@Mapper
public interface UserMapperXml {
    int insert(@Param("pojo") User user);
    List<User> find();
}
