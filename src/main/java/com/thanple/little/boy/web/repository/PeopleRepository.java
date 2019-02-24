package com.thanple.little.boy.web.repository;

import com.thanple.little.boy.web.entity.repo.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Create by Thanple at 2019/2/13 下午8:59
 */
@Repository
public interface PeopleRepository extends JpaRepository<People,Long> {

    @Query("select a from People a where a.id = ?1")
    People findById(Long id);

}
