package com.ledo.alpha.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Thanple on 2017/2/24.
 */
@Controller
public class ErrorPageController {

    @RequestMapping(value="/404")
    public String handleNotFound(){
        return "errors/404";
    }
    @RequestMapping(value="/500")
    public String handleServerError(){
        return "errors/500";
    }
}