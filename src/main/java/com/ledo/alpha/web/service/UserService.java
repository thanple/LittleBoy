package com.ledo.alpha.web.service;

import com.ledo.alpha.web.entity.repo.User;
import com.ledo.alpha.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Thanple on 2018/5/24.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 测试这个事务将导致事务回滚，如果把@Transaction去了则user1插入成功。
     * Spring的事务管理默认只对未检查异常(java.lang.RuntimeException及其子类)进行回滚，如果一个方法抛出Checked异常，Spring事务管理默认不进行回滚。
     * 如果checked异常需要回滚，请先捕获，再抛出RuntimeException即可。
     */
    @Transactional
    public void insertDatas(){
        User user1 = new User(1L, "Tom", "Tom@qq.com",new Date());
        User user2 = new User(2L, "Lucy", "Lucy@qq.com",new Date());

        userRepository.save(user1);

        if(true){
            throw new RuntimeException("Accident Exception");
        }
        userRepository.save(user2);
    }
}
