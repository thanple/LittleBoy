package com.thanple.little.boy.web.entity.repo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Thanple on 2017/2/20.
 */
@Entity // This tells Hibernate to make a table out of this class
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = {@Index(name = "createTime_key",columnList = "createTime"),
        @Index(name = "name_key",columnList = "name")})   //为createTime,name增加索引
public class User implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Column(length = 50)
    private String name;
    @Column(length = 50)
    private String email;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
