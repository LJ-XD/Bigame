package com.luckj.entity;

import java.io.Serializable;


/**
 * 用户表
 *
 * @author lj
 * @date 2024/08/05
 */
public class User implements Serializable {

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public User() {
    }

    /**
     * qq号
     */
    private Long id;
    /**
     * 昵称
     */
    private String name;

    /**
     * qq号
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 昵称
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * qq号
     */
    public Long getId() {
        return this.id;
    }

    /**
     * 昵称
     */
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
