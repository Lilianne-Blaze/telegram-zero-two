package lbnet.telegram.zerotwo.demo.shared;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DemoParams {

    public static String BOTUSERNAME;

    public static String BOTTOKEN;

    public static String CHATS;

    static {
        staticInit();
    }

    private static void staticInit() {
        BOTUSERNAME = getParam("TELEGRAM_TEST_BOTUSERNAME");
        BOTTOKEN = getParam("TELEGRAM_TEST_BOTTOKEN");
        CHATS = getParam("TELEGRAM_TEST_CHATS");
    }

    private static String getParam(String envName) {
        String propName = envName.toLowerCase().replace("_", ".");
        return System.getProperty(propName, System.getenv(envName));
    }

}
