package com.thanple.little.boy.web.entity.repo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Create by Thanple at 2019/2/13 下午8:57
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class People {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;//id
    @Column(name = "name", nullable = true, length = 20)
    private String name;//姓名
    @Column(name = "sex", nullable = true, length = 1)
    private String sex;//性别

    @OneToOne(cascade=CascadeType.ALL)//People是关系的维护端，当删除 people，会级联删除 address
    @JoinColumn(name = "address_id", referencedColumnName = "id")//people中的address_id字段参考address表中的id字段
    private Address address;//地址
}
