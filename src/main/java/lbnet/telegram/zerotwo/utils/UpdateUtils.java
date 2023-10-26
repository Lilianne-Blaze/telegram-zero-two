package lbnet.telegram.zerotwo.utils;

import java.time.Instant;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Methods ending with "NoEx" or "Opt" never throw exceptions under any circumstances.
 */
@Slf4j
public class UpdateUtils {

    public static String updateToString(Update update) {
        if (update == null) {
            return "null";
        }

        return removeNullAssignments(update.toString());
    }

    private static String removeNullAssignments(String input) {
        if (input == null) {
            return null;
        }

        // Regular expression to match ", xxx=null" pattern
        String regex = ",\\s+([a-zA-Z_][a-zA-Z0-9_]*)=null";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(input);

        return matcher.replaceAll("");
    }

    public static boolean isMessageWithTextNoEx(Update update) {
        return update != null && update.hasMessage() && update.getMessage().hasText();
    }

    public static Optional<String> getTextOpt(Update update) {
        try {
            String s = update.getMessage().getText();
            return Optional.of(s);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static boolean textContainsNoEx(Update update, String text) {
        try {
            String s = update.getMessage().getText();
            return s.contains(text);
        } catch (Exception e) {
            return false;
        }
    }

    public static SendMessage newReply(Update update, String text) {
        SendMessage sm = new SendMessage();
        sm.setChatId(update.getMessage().getChatId());
        sm.setText(text);
        return sm;
    }

    public static SendMessage newReplyWithQuote(Update update, String text) {
        SendMessage sm = newReply(update, text);
        sm.setReplyToMessageId(update.getMessage().getMessageId());
        return sm;
    }

    public static boolean isUserChatNoEx(Update update) {
        try {
            return update.getMessage().getChat().isUserChat();
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isAnyGroupChatNoEx(Update update) {
        try {
            Chat chat = update.getMessage().getChat();
            return chat.isGroupChat() || chat.isSuperGroupChat();
        } catch (Exception e) {
            return false;
        }
    }

    public static Optional<Instant> instantOfUpdateOpt(Update update) {
        try {
            Instant in = Instant.ofEpochSecond(update.getMessage().getDate());
            return Optional.of(in);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
