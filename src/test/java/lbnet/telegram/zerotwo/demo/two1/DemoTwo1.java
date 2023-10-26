package lbnet.telegram.zerotwo.demo.two1;

import ch.qos.logback.classic.Level;
import lbnet.telegram.zerotwo.MultiBotSession;
import ch.qos.logback.classic.Logger;
import lbnet.telegram.zerotwo.demo.shared.DemoParams;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.generics.BotSession;

@Slf4j
public class DemoTwo1 {

    public static void main(String[] args) {
        try {

            Logger root = (Logger) LoggerFactory.getLogger("lbnet.telegram.zerotwo");
            root.setLevel(Level.ALL);

            log.info("main...");

            TelegramBotsApi botsApi = new TelegramBotsApi(MultiBotSession.class);

            // first instance
            TwoBot1 twoBot1 = new TwoBot1(DemoParams.BOTUSERNAME, DemoParams.BOTTOKEN);
            BotSession botSession1 = botsApi.registerBot(twoBot1);

            // second instance
            TwoBot1 twoBot2 = new TwoBot1(DemoParams.BOTUSERNAME, DemoParams.BOTTOKEN);
            BotSession botSession2 = botsApi.registerBot(twoBot2);

            // TwoBot twoBot3 = new TwoBot(DemoParams.BOTUSERNAME, DemoParams.BOTTOKEN);
            // BotSession botSession3 = botsApi.registerBot(twoBot3);
            //
            // TwoBot twoBot4 = new TwoBot(DemoParams.BOTUSERNAME, DemoParams.BOTTOKEN);
            // BotSession botSession4 = botsApi.registerBot(twoBot4);

        } catch (Exception e) {
            log.error("Exception: {}", e.getMessage(), e);
        }
    }

}
