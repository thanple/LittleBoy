package com.thanple.little.boy.web.repository;

import com.thanple.little.boy.web.entity.repo.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Create by Thanple at 2018/5/27 下午7:46
 */
@Repository
public class UserRepository2Impl implements UserRepository2{

    //加上这个注解，就会注入一个对JPA操作的manager
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> searchNames(String lastName) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.where(builder.like(root.get("name"), "%" + lastName + "%"));

        return entityManager.createQuery(query.select(root)).getResultList();
    }
}
