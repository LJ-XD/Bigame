package com.luckj.service.impl;

import com.luckj.service.GameService;

public class GameServiceImpl implements GameService {
    public static final GameService INSTANCE = new GameServiceImpl();
    private GameServiceImpl() {
    }
}
