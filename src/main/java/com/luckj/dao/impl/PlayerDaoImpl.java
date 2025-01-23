package com.luckj.dao.impl;

import cn.hutool.json.JSONUtil;
import com.luckj.config.DatabaseConnectionConfig;
import com.luckj.dao.PlayerDao;
import com.luckj.entity.Player;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public enum PlayerDaoImpl implements PlayerDao {
    INSTANCE;

    @Override
    public int createPlayer(Player player) {
        String sql = "INSERT INTO b_user(id,name) VALUES(?, ?)";
        System.out.println(sql);
        System.out.println(JSONUtil.toJsonStr(player));
        try (Connection conn = DatabaseConnectionConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, player.getId());
            pstmt.setString(2, player.getName());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("新增用户失败");
            }
            return player.getId();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    @Override
    public Player findPlayerById(int id) {
        String sql = "SELECT * FROM b_user WHERE id = ?";
        System.out.println(sql);
        System.out.println(id);
        try (Connection conn = DatabaseConnectionConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Player().generateAbstractEntity(rs);
            }
        } catch (SQLException e) {
            System.err.println("数据库操作失败: " + e.getMessage());
        }
        return new Player();
    }

    @Override
    public List<Player> findAllPlayers() {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT * FROM b_user";
        try (Connection conn = DatabaseConnectionConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                players.add(new Player().generateAbstractEntity(rs));
            }
        } catch (SQLException e) {
            System.err.println("数据库操作失败: " + e.getMessage());
        }
        return players;
    }

    @Override
    public boolean updatePlayer(Player player) {
        String sql = "UPDATE b_user SET name = ? WHERE id = ?";
        System.out.println(sql);
        System.out.println(JSONUtil.toJsonStr(player));
        try (Connection conn = DatabaseConnectionConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, player.getName());
            pstmt.setInt(2, player.getId());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("数据库操作失败: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deletePlayer(int id) {
        String sql = "DELETE FROM b_user WHERE id = ?";
        System.out.println(sql);
        System.out.println(id);
        try (Connection conn = DatabaseConnectionConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("数据库操作失败: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updatePlayerSelective(Player player) {
        StringBuilder sqlBuilder = new StringBuilder("UPDATE b_user SET ");
        List<String> columnsToUpdate = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        if (player.getName() != null) {
            columnsToUpdate.add("name = ?");
            params.add(player.getName());
        }
        if (Objects.nonNull(player.getLevel()) && player.getLevel() >= 0) {
            columnsToUpdate.add("level = ?");
            params.add(player.getLevel());
        }
        if (Objects.nonNull(player.getExperience()) && player.getExperience() >= 0) {
            columnsToUpdate.add("experience = ?");
            params.add(player.getExperience());
        }
        if (Objects.nonNull(player.getStrength()) && player.getStrength() >= 0) {
            columnsToUpdate.add("strength = ?");
            params.add(player.getStrength());
        }
        if (Objects.nonNull(player.getAgility()) && player.getAgility() >= 0) {
            columnsToUpdate.add("agility = ?");
            params.add(player.getAgility());
        }
        if (Objects.nonNull(player.getConstitution()) && player.getConstitution() >= 0) {
            columnsToUpdate.add("constitution = ?");
            params.add(player.getConstitution());
        }
        if (Objects.nonNull(player.getIntelligence()) && player.getIntelligence() >= 0) {
            columnsToUpdate.add("intelligence = ?");
            params.add(player.getIntelligence());
        }
        if (Objects.nonNull(player.getMaxHp()) && player.getMaxHp() >= 0) {
            columnsToUpdate.add("max_hp = ?");
            params.add(player.getMaxHp());
        }
        if (Objects.nonNull(player.getMaxMp()) && player.getMaxMp() >= 0) {
            columnsToUpdate.add("max_mp = ?");
            params.add(player.getMaxMp());
        }
        if (columnsToUpdate.isEmpty()) {
            return false;
        }
        sqlBuilder.append(String.join(", ", columnsToUpdate));
        sqlBuilder.append(" WHERE id = ?");
        params.add(player.getId());
        String sql = sqlBuilder.toString();
        System.out.println(sql);
        System.out.println(params);
        try (Connection conn = DatabaseConnectionConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
            }
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("数据库操作失败: " + e.getMessage());
            return false;
        }
    }
}