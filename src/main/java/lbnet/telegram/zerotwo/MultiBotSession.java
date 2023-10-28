package lbnet.telegram.zerotwo;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import lbnet.telegram.zerotwo.utils.UpdateUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.updates.GetUpdates;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Bot Session that does not mark updates as seen on the servers, instead tracking them locally so multiple instances
 * can see them. Note it will generate unusual and unintended traffic at Telegram, so it's recommended to use longer
 * delays to avoid excessive requests and getting blocked. Note it's intended for a set small number of instances, not
 * an arbitrarily large / increasing number.
 */
@Slf4j
public class MultiBotSession extends ExposingDefaultBotSession {

    public static class StartPaused extends MultiBotSession {

        public StartPaused() {
            super();
            setPaused(true);
        }
    }

    /**
     * Recommendation - leave as TRACE for production, change to DEBUG for testing if needed.
     */
    @Getter
    @Setter
    private static Level logLevel = Level.TRACE;

    protected int newestUpdateId = 0;

    /**
     * By default ignore updates generated earlier than 15 minutes before session was created. If you want to change it
     * you'll probably need to do it either for a StartPaused or your own subclasss.
     */
    @Getter
    @Setter
    protected Instant ignoreEarlierThan = Instant.now().minusSeconds(15 * 60);

    /**
     * Extra delay in millis between update requests, randomized +/- 50%. Increase according to how many instances you
     * plan to run. Suggestion: 2000 per instance is a good starting point. TODO: is it really? Needs more testing.
     */
    @Getter
    @Setter
    protected int averageExtraDelay = 2000;

    /**
     * How many updates to keep, 1-100. Any more will be permanently discarded.
     */
    @Setter
    @Getter
    protected int maxUpdatesKept = 100;

    /**
     * Set to true to temporarily disable updates, bot will not try to contact server. Ignored updates will not be
     * discarded. Note any updates after maxUpdatesKept will be discarded at next request after setting back to false.
     */
    @Setter
    @Getter
    protected boolean paused = false;

    public MultiBotSession() {
        setUpdatesSupplier(this::updatesSupplier);
        log.atLevel(logLevel).log("Constructed.");
    }

    /**
     * Ignores paused state and delay settings.
     */
    public int checkUpdatesNowNoEx() {
        try {
            return checkUpdatesNow();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Ignores paused state and delay settings.
     */
    public int checkUpdatesNow() throws TelegramApiException {
        List<Update> updates = updatesSupplierInner();
        getBot().onUpdatesReceived(updates);
        return updates.size();
    }

    protected Object getLock() {
        return this;
    }

    protected void extraDelay() {
        synchronized (getLock()) {
            try {
                int delay = (int) (Math.random() * averageExtraDelay);
                log.atLevel(logLevel).log("extraDelay for {} millis...", delay);
                Thread.sleep(delay);
            } catch (Exception e) {
            }
        }
    }

    protected List<Update> updatesSupplier() throws TelegramApiException {
        log.atLevel(logLevel).log("updateSupplier called...");
        extraDelay();
        if (paused) {
            log.atLevel(logLevel).log("Delayed. Ignoring updates, not calling server.");
            return Collections.EMPTY_LIST;
        }
        log.atLevel(logLevel).log("Delayed, calling server for updates.");

        try {
            List<Update> updateList = updatesSupplierInner();
            return updateList;
        } catch (Exception e) {
            log.warn("Exception in updatesSupplier: " + e.getMessage(), e);
            throw e;
        }
    }

    protected List<Update> updatesSupplierInner() throws TelegramApiException {
        log.atLevel(logLevel).log("updateSupplierInner called...");
        TelegramLongPollingBot bot = getBot();

        // note we are NOT marking any updates as seen so other instances see them too
        GetUpdates getUpdatesCmd = GetUpdates.builder().offset(-maxUpdatesKept).limit(maxUpdatesKept).timeout(0)
                .build();
        List<Update> updateList = bot.execute(getUpdatesCmd);
        int updatesAll = updateList.size();
        log.atLevel(logLevel).log("Server returned {} updates.", updatesAll);

        if (updateList.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        // discard old updates
        updateList.removeIf((t) -> UpdateUtils.instantOfUpdateOpt(t).orElse(Instant.MAX).isBefore(ignoreEarlierThan));
        int updatesExpired = updatesAll - updateList.size();

        // discard already seen updates
        updateList.removeIf((t) -> t.getUpdateId() <= newestUpdateId);
        int updatesAlreadySeen = updatesAll - updatesExpired - updateList.size();
        int updatesNew = updatesAll - updatesExpired - updatesAlreadySeen;

        // remember last current id
        updateList.forEach((t) -> {
            int currentUpdateId = t.getUpdateId();
            if (currentUpdateId > newestUpdateId) {
                newestUpdateId = currentUpdateId;
            }
        });

        log.atLevel(logLevel).log("Updates expired {}, already seen {}, new {}, newest id {}.", updatesExpired,
                updatesAlreadySeen, updatesNew, newestUpdateId);

        return updateList;
    }

}
