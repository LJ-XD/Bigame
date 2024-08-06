package com.luckj.service.impl;

import com.luckj.Bigame;
import com.luckj.entity.User;
import com.luckj.mapper.UserMapper;
import com.luckj.mybatis.MyBatisLoader;
import com.luckj.service.UserService;
import net.mamoe.mirai.utils.MiraiLogger;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * 用户服务实现
 *
 * @author lj
 * @date 2024/08/05
 */
public class UserServiceImpl implements UserService {
    private final SqlSessionFactory sqlSessionFactory;

    public UserServiceImpl() {
        this.sqlSessionFactory = MyBatisLoader.getSqlSessionFactory();
    }

    private final MiraiLogger miraiLogger = MiraiLogger.Factory.INSTANCE.create(Bigame.class);

    @Override
    public void welcomeUser() {
        MiraiLogger.Factory.INSTANCE.create(UserServiceImpl.class).info("Welcome to use BIGAME\n" +
                "###########      #####       #########            #                #       #         #############     \n" +
                "#           #      #       #           #                                             #                 \n" +
                "#           #      #       #           #         # #                                 #                 \n" +
                "#           #      #       #           #                          # #     # #        #                 \n" +
                "#           #      #       #                    #   #                                #                 \n" +
                "#           #      #       #                                                         #                 \n" +
                "###########        #       #                   # # # #           #   #   #   #       #############     \n" +
                "#           #      #       #                                                         #                 \n" +
                "#           #      #       #                  #        #                             #                 \n" +
                "#           #      #       #                                    #     # #     #      #                 \n" +
                "#           #      #       #         ###     #          #                            #                 \n" +
                "#           #      #       #           #                                             #                 \n" +
                "############     #####       ###########    #            #     #       #       #     #############     ");


    }

    @Override
    public String registerUser(User user) {
        try {
            try (SqlSession session = sqlSessionFactory.openSession(true)) {
                UserMapper mapper = session.getMapper(UserMapper.class);
                mapper.insert(user);
            }
            miraiLogger.info("注册成功:" + user.toString());
            return "注册成功";
        } catch (Exception e) {
            miraiLogger.error("注册失败", e);
            User userDb = null;
            try (SqlSession session = sqlSessionFactory.openSession(true)) {
                UserMapper mapper = session.getMapper(UserMapper.class);
                userDb = mapper.selectById(user.getId());
            }catch (Exception err) {
                miraiLogger.error("查询失败", err);
            }
            if (userDb != null) {
                return "再重复注册打你的头哦!";
            }
            return "注册失败";
        }
    }


    @Override
    public List<User> queryAll() {
        try (SqlSession session = sqlSessionFactory.openSession(true)) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.selectAll();
        }
    }
}
