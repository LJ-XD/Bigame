package com.luckj.commands;

import com.alibaba.fastjson.JSON;
import com.luckj.Bigame;
import com.luckj.constants.BotBaseConstants;
import com.luckj.utils.JsonUtils;
import net.mamoe.mirai.console.command.CommandContext;
import net.mamoe.mirai.console.command.java.JSimpleCommand;

import java.util.*;

/**
 * 回答问题
 *
 * @author lj
 * @date 2024/08/06
 */
public final class AsnMessageCommand extends JSimpleCommand {

    public AsnMessageCommand() {
        super(Bigame.INSTANCE, "q");
        setDescription("回答问题");
    }

    @Handler
    public void foo(CommandContext context, @Name("问题") String question) {
        Map<String, Object> map = JsonUtils.loadMapFromJsonFile(BotBaseConstants.BotConfigConstants.SAVE_DOCUMENT);
        Object messageMapObj = map.get(BotBaseConstants.BotMessageConstants.MESSAGE_DATA);
        if (Objects.nonNull(messageMapObj)) {
            try {
                Map<String, List<String>> messageMap = JSON.parseObject(JSON.toJSONString(messageMapObj), Map.class);
                List<String> messages = messageMap.get(question);
                if (Objects.nonNull(messages)) {
                    if (messages.size() > 1) {
                        context.getSender().sendMessage(messages.get(new Random().nextInt(messages.size())));

                    } else {
                        context.getSender().sendMessage(messages.get(0));
                    }
                }
            } catch (Exception e) {
                System.out.println("自定义问题回答异常:");
            }
        }
    }
}