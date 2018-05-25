package com.thanple.little.boy.web.entity.msg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Thanple on 2018/5/24.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedisPubMsg {
    private Long id;
    private RedisPubBean pubBean;
}
