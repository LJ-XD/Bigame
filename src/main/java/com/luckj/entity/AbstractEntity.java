package com.luckj.entity;

import java.sql.ResultSet;

public abstract class AbstractEntity {
    public abstract AbstractEntity generateAbstractEntity(ResultSet rs);
}
