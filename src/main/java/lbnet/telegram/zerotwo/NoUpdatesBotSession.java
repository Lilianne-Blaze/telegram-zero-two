package lbnet.telegram.zerotwo;

import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Bot Session for send-only bots. Send-only bots can run multiple instances, but can still get errors if multiple
 * instances send too many messages in a short time window.
 */
@Slf4j
public class NoUpdatesBotSession extends ExposingDefaultBotSession {

    public NoUpdatesBotSession() {
        setUpdatesSupplier(this::noOpUpdatesSupplier);
    }

    protected List<Update> noOpUpdatesSupplier() throws Exception {
        return Collections.EMPTY_LIST;
    }

}
