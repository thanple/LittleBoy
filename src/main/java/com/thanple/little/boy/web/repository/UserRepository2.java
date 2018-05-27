package com.thanple.little.boy.web.repository;

import com.thanple.little.boy.web.entity.repo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Create by Thanple at 2018/5/27 下午7:45
 * 这张方式需要写一个实现类来自定义对JPA的操作，详见UserRepository2Impl
 */
@Repository
public interface UserRepository2 {
    List<User> searchNames(String lastName);
}
