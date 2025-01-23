package com.luckj.commands;

import com.alibaba.fastjson.JSON;
import com.luckj.Bigame;
import com.luckj.constants.BotBaseConstants;
import com.luckj.utils.JsonUtils;
import net.mamoe.mirai.console.command.CommandContext;
import net.mamoe.mirai.console.command.java.JSimpleCommand;

import java.util.*;

/**
 * 添加消息指令
 *
 * @author lj
 * @date 2024/08/06
 */
public final class AddMessageCommand extends JSimpleCommand {

    public AddMessageCommand() {
        super(Bigame.INSTANCE, "addMsg");
        setDescription("添加消息");
    }

    @Handler
    public void foo(CommandContext context, @Name("问题") String question, @Name("答案") String answer) {
        if (question.length() > 50 || answer.length() > 50) {
            System.out.println("文字太长了,不要超过50哦。");
            return;
        }
        Map<String, Object> map = JsonUtils.loadMapFromJsonFile(BotBaseConstants.BotConfigConstants.SAVE_DOCUMENT);
        if (Objects.isNull(map)) {
            map = new HashMap<>();
        }
        Object messageMapObj = map.get(BotBaseConstants.BotMessageConstants.MESSAGE_DATA);
        Map<String, List<String>> messageMap = new HashMap<>();
        if (Objects.nonNull(messageMapObj)) {
            messageMap = JSON.parseObject(JSON.toJSONString(messageMapObj), Map.class);
        }
        List<String> strings = messageMap.get(question);
        if (Objects.nonNull(strings)) {
            if (!strings.contains(answer)) {
                strings.add(answer);
            }
        } else {
            strings = new ArrayList<>();
            strings.add(answer);
            messageMap.put(question, strings);
        }
        map.put(BotBaseConstants.BotMessageConstants.MESSAGE_DATA, messageMap);
        JsonUtils.saveMapToJsonFile(BotBaseConstants.BotConfigConstants.SAVE_DOCUMENT, map);
        context.getSender().sendMessage("添加成功。");
    }
}