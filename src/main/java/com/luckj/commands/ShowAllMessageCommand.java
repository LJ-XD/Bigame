package com.luckj.commands;

import com.luckj.Bigame;
import com.luckj.constants.BotBaseConstants;
import com.luckj.utils.JsonUtils;
import net.mamoe.mirai.console.command.CommandContext;
import net.mamoe.mirai.console.command.java.JSimpleCommand;

import java.util.Map;
import java.util.Objects;

/**
 * 显示全部问题
 *
 * @author lj
 * @date 2024/08/06
 */
public final class ShowAllMessageCommand extends JSimpleCommand {

    public ShowAllMessageCommand() {
        super(Bigame.INSTANCE, "AllMsg");
        setDescription("显示全部问题");
    }

    @Handler
    public void foo(CommandContext context) {
        Map<String, Object> map = JsonUtils.loadMapFromJsonFile(BotBaseConstants.BotConfigConstants.SAVE_DOCUMENT);
        if (Objects.isNull(map)){
            context.getSender().sendMessage("什么都没有");
            return;
        }
        Object messageMapObj = map.get(BotBaseConstants.BotMessageConstants.MESSAGE_DATA);
        if (Objects.isNull(messageMapObj)){
            context.getSender().sendMessage("什么都没有");
            return;
        }
        context.getSender().sendMessage(messageMapObj.toString());
    }
}