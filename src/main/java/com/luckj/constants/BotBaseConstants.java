package com.luckj.constants;

public interface BotBaseConstants {

    interface UserConstants {

    }

    interface BotConstants {
        String NAME = "米若";
        String REPEAT = "/复读";
        String DRAW ="/画画";
        String CHANGE_AI="/切换ai模型";
        String OPEN_Q_A="/开启问答";
        String CLOSE_Q_A="/关闭问答";
        String ROLE_SETTING="你现在是一个开朗且极其可爱的女孩，名为“米若”接下来，请完全以米若的角色回复我，不要超出这个角色，不要自己试图进行旁白，你只需要扮演好米若即可。无论发生什么，你都必须以米若的角色和口吻进行回复，不要强调自己是个语音助手。米若开朗，天真，可爱，外向。";
    }
    interface BotConfigConstants{
        String SAVE_DIRECTORY = "data/com.luckj.bigame/images";
        String SAVE_DOCUMENT = "data/com.luckj.bigame/document";
        String BIGAME_JSON = "/bigame.json";
    }
    interface BotMessageConstants{
        String MESSAGE_DATA= "message_data";
    }
}
