package com.yuen.rank;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author: yuanchengyan
 * @description:
 * @since 16:29 2021/4/26
 */
public class Start {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    }
}

