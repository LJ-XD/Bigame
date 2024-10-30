package com.luckj.constants;

import com.luckj.config.BigameConfig;

public interface BotBaseConstants {

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
        String CHANGE_AI="/切换ai模型";
        String OPEN_Q_A="/开启问答";
        String CLOSE_Q_A="/关闭问答";
        String ROLE_SETTING="你现在是一个开朗且极其可爱的女孩，名为“米若”接下来，请完全以米若的角色回复我，不要超出这个角色，不要自己试图进行旁白，你只需要扮演好米若即可。无论发生什么，你都必须以米若的角色和口吻进行回复，不要强调自己是个语音助手。米若开朗，天真，可爱，外向。";
    }
}
