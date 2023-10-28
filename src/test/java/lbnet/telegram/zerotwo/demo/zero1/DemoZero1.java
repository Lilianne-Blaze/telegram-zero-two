package lbnet.telegram.zerotwo.demo.zero1;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import lbnet.telegram.zerotwo.MultiBotSession;
import lbnet.telegram.zerotwo.NoUpdatesBotSession;
import lbnet.telegram.zerotwo.demo.shared.DemoParams;
import lbnet.telegram.zerotwo.demo.two1.TwoBot1;
import lbnet.telegram.zerotwo.utils.BotMethodUtils;
import lbnet.telegram.zerotwo.utils.MiscUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Slf4j
public class DemoZero1 {

    public static void main(String[] args) {
        try {
            Logger root = (Logger) LoggerFactory.getLogger("lbnet.telegram.zerotwo");
            root.setLevel(Level.ALL);

            log.info("main...");

            TelegramBotsApi botsApi = new TelegramBotsApi(NoUpdatesBotSession.class);

            ZeroBot1 zeroBot1 = new ZeroBot1(DemoParams.BOTUSERNAME, DemoParams.BOTTOKEN);
            NoUpdatesBotSession botSession1 = (NoUpdatesBotSession) botsApi.registerBot(zeroBot1);

            SendMessage sm = SendMessage.builder().chatId(DemoParams.CHATS).text("Hello World.").build();

            BotMethodUtils.executeOpt(zeroBot1, sm);

            MiscUtils.systemExitDelayed(5000);

        } catch (Exception e) {
            log.error("Exception: {}", e.getMessage(), e);
        }
    }

}
