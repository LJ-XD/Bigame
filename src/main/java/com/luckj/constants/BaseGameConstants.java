package com.luckj.constants;

import com.luckj.config.BigameConfig;

public interface BaseGameConstants {

    interface UserConstants {
        String BIGGER = "你猜大了!";
        String SMALLER = "你猜小了!";
        String WIN = "恭喜你猜对了!";
    }

    interface BotConstants {
        String NAME = "米若";
        String BIGGER = "米若猜大了!";
        String SMALLER = "米若猜小了!";
        String WIN = "恭喜米若猜对了!";
        String AT_BOT_QQ = "@" + BigameConfig.getInstance().getBotQQ();
        String REPEAT = "/复读";
        String DRAW ="/画画";
        String MO_YU="/摸鱼";
    }
}
