package lbnet.telegram.zerotwo;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.generics.BotOptions;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * Mostly meant for easier subclassing.
 */
@Slf4j
public class ExposingDefaultBotSession extends DefaultBotSession {

    @Getter
    private LongPollingBot callback;

    @Getter
    private DefaultBotOptions options;

    @Override
    public void setCallback(LongPollingBot callback) {
        super.setCallback(callback);
        this.callback = callback;
    }

    public TelegramLongPollingBot getBot() {
        return (TelegramLongPollingBot) getCallback();
    }

    @Override
    public void setOptions(BotOptions options) {
        super.setOptions(options);
        this.options = (DefaultBotOptions) options;
    }

}
