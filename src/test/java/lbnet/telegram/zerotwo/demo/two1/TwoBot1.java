package lbnet.telegram.zerotwo.demo.two1;

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
public class TwoBot1 extends TelegramLongPollingBot {

    @Getter
    protected String botUsername;

    protected String prefix = "[" + hashCode() + "] ";

    public TwoBot1(String botUserame, String botToken) {
        this(botUserame, botToken, null);
    }

    public TwoBot1(String botUserame, String botToken, DefaultBotOptions botOptions) {
        super(botOptions != null ? botOptions : new DefaultBotOptions(), botToken);
        this.botUsername = botUserame;
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.info("updateReceived: {}", UpdateUtils.updateToString(update));

        SendMessage sm = UpdateUtils.newReplyWithQuote(update,
                prefix + "You wrote: " + UpdateUtils.getTextOpt(update).orElse("???"));
        BotMethodUtils.executeOpt(this, sm);

        if (UpdateUtils.textContainsNoEx(update, "exit")) {
            SendMessage sm2 = UpdateUtils.newReplyWithQuote(update, prefix + "Exiting in a sec.");
            BotMethodUtils.executeOpt(this, sm2);
            MiscUtils.systemExitDelayed(3000);
        }

        if (UpdateUtils.textContainsNoEx(update, "id")) {
            String t = prefix + "chatId = " + update.getMessage().getChatId();
            t = t + "\nuserChat = " + UpdateUtils.isUserChatNoEx(update);
            t = t + "\ngroupChat = " + UpdateUtils.isAnyGroupChatNoEx(update);
            SendMessage sm2 = UpdateUtils.newReplyWithQuote(update, t);
            BotMethodUtils.executeOpt(this, sm2);
        }

    }

}
