package com.luckj.service;

import com.luckj.entity.User;
import com.luckj.service.impl.UserServiceImpl;
import org.junit.Test;

import java.util.List;

public class UserServiceTest {
    private final UserService userService;
    public UserServiceTest(){
        this.userService=new UserServiceImpl();
    }
    @Test
    public void testQueryAll() {
        List<User> users = userService.queryAll();
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void name() {

        User user=new User(2763267932L,"luckj");
        System.out.println(userService.registerUser(user));
    }
}