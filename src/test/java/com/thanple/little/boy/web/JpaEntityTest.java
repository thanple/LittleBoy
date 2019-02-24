package com.thanple.little.boy.web;

import com.thanple.little.boy.web.entity.repo.Address;
import com.thanple.little.boy.web.entity.repo.Article;
import com.thanple.little.boy.web.entity.repo.Author;
import com.thanple.little.boy.web.entity.repo.People;
import com.thanple.little.boy.web.repository.AuthorRepository;
import com.thanple.little.boy.web.repository.PeopleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by Thanple at 2019/2/13 下午8:52
 * jpa的一对一，一对多，多对多
 */

@RunWith(SpringRunner.class)
@org.springframework.boot.test.context.SpringBootTest
public class JpaEntityTest {

    @Autowired
    private PeopleRepository peopleRepository;

    @Autowired
    private AuthorRepository authorRepository;


    @Test
    public void oneToOne(){
        People people = new People();
        people.setName("张三");
        people.setSex("男");

        Address address = new Address();
        address.setAddress("北京海淀");
        address.setPhone("12331415");
        address.setZipcode("124563");
        people.setAddress(address);

        peopleRepository.save(people);

        People firstPerson = peopleRepository.findById(1L);
        System.out.println(firstPerson);
    }

    @Test
    public void oneToMany(){
        Author author = new Author();
        author.setName("Thanple");

        List<Article> articles = new ArrayList<>();
        Article article1 = new Article();
        article1.setAuthor(author);
        article1.setContent("111");
        article1.setTitle("111");
        Article article2 = new Article();
        article2.setAuthor(author);
        article2.setContent("222");
        article2.setTitle("222");

        articles.add(article1);
        articles.add(article2);
        author.setArticleList(articles);

        authorRepository.save(author);
    }

}
