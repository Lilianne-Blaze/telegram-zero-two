package lbnet.telegram.zerotwo.demo.zero1;

import lbnet.telegram.zerotwo.utils.BotMethodUtils;
import lbnet.telegram.zerotwo.utils.MiscUtils;
import lbnet.telegram.zerotwo.utils.UpdateUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
public class ZeroBot1 extends TelegramLongPollingBot {

    @Getter
    protected String botUsername;

    protected String prefix = "[" + hashCode() + "] ";

    public ZeroBot1(String botUserame, String botToken) {
        this(botUserame, botToken, null);
    }

    public ZeroBot1(String botUserame, String botToken, DefaultBotOptions botOptions) {
        super(botOptions != null ? botOptions : new DefaultBotOptions(), botToken);
        this.botUsername = botUserame;
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.warn("WTF? There should be no updates here.");
    }
}
