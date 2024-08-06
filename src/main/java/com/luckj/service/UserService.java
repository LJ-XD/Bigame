package com.luckj.service;

import com.luckj.entity.User;

import java.util.List;

/**
 * 用户服务
 *
 * @author lj
 * @date 2024/08/05
 */
public interface UserService {

    /**
     * 欢迎
     */
    void welcomeUser();

    /**
     * 注册用户
     *
     * @param user 用户
     * @return 字符串
     */
    String registerUser(User user);


    /**
     * 查询全部
     *
     * @return 列表<用户>
     */
    List<User> queryAll();
}
