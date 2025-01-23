package com.luckj.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @TableName b_user
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class Player extends AbstractEntity implements Serializable {


    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 等级
     */
    private Integer level;
    /**
     * 当前经验
     */
    private Integer experience;
    /**
     * 力
     */
    private Integer strength;
    /**
     * 敏
     */
    private Integer agility;
    /**
     * 体
     */
    private Integer constitution;
    /**
     * 智
     */
    private Integer intelligence;

    private Integer maxHp;

    private Integer maxMp;

    @Override
    public Player generateAbstractEntity(ResultSet rs) {
        Player player = new Player();
        try {
            player.setId(rs.getInt("id"));
            player.setName(rs.getString("name"));
            player.setLevel(rs.getInt("level"));
            player.setExperience(rs.getInt("experience"));
            player.setStrength(rs.getInt("strength"));
            player.setAgility(rs.getInt("agility"));
            player.setConstitution(rs.getInt("constitution"));
            player.setIntelligence(rs.getInt("intelligence"));
            player.setMaxHp(rs.getInt("max_hp"));
            player.setMaxMp(rs.getInt("max_mp"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return player;
    }
}
