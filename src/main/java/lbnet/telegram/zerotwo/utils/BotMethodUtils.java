package lbnet.telegram.zerotwo.utils;

import java.io.Serializable;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Slf4j
public class BotMethodUtils {

    public static <T extends Serializable, Method extends BotApiMethod<T>> Optional<T> executeOpt(AbsSender bot,
            Method method) {
        try {
            return Optional.of(bot.execute(method));
        } catch (Exception e) {
            return Optional.empty();
        }

    }

}
