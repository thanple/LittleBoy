package com.thanple.little.boy.web.repository;


import com.thanple.little.boy.web.entity.repo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by Thanple on 2017/2/20
 * 只需要定义接口就行了，如果需要自定义，可以写注解
 */

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("select a from User a where a.id = ?1")
    User findById(Long id);
}
