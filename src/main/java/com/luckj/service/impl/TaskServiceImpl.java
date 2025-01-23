package com.luckj.service.impl;

import com.luckj.service.TaskService;

public class TaskServiceImpl implements TaskService {
    public static final TaskService INSTANCE = new TaskServiceImpl();
    private TaskServiceImpl() {
    }
}
