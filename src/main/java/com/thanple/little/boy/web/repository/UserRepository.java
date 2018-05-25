package com.thanple.little.boy.web.repository;


import com.thanple.little.boy.web.entity.repo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Thanple on 2017/2/20.
 */

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
