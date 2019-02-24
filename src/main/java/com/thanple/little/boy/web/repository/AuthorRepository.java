package com.thanple.little.boy.web.repository;

import com.thanple.little.boy.web.entity.repo.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Create by Thanple at 2019/2/16 下午2:09
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author,Long> {
}
