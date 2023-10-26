package lbnet.telegram.zerotwo.utils;

public class MiscUtils {

    public static void systemExitDelayed(int millis) {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(millis);
            } catch (Exception e) {
            }
            System.exit(0);

        });
        t.setDaemon(true);
        t.start();
    }

}
