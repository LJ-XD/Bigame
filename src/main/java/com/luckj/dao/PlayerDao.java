package com.luckj.dao;

import com.luckj.entity.Player;

import java.util.List;

public interface PlayerDao {
    /**
     * 创建一个新的用户。
     * @param player 用户对象
     * @return 返回插入用户的ID
     */
    int createPlayer(Player player);

    /**
     * 根据ID查找用户。
     */
    Player findPlayerById(int id);

    /**
     * 查询所有用户。
     */
    List<Player> findAllPlayers();

    /**
     * 更新用户信息。
     */
    boolean updatePlayer(Player player);

    /**
     * 删除用户。
     */
    boolean deletePlayer(int id);

    /**
     * 根据提供的用户信息选择性地更新用户。
     */
    boolean updatePlayerSelective(Player player);
}