package com.ledo.alpha.web.mapper;

import com.ledo.alpha.web.entity.repo.User;
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
