package com.luckj.mapper;

import com.luckj.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户映射器
 *
 * @author lj
 * @date 2024/08/05
 */
public interface UserMapper {

    /**
     * 查找全部
     *
     * @return 列表<用户>
     */
    @Select("select * from user")
    List<User> selectAll();

    /**
     * 添加
     *
     * @param user 用户
     */
    @Insert("insert into user(id, name) values(#{id}, #{name})")
    void insert(User user);

    /**
     * 按 ID 查询
     *
     * @param id 同上
     * @return 用户表
     */
    @Select("select * from user where id = #{id}")
    User selectById(Long id);
}
