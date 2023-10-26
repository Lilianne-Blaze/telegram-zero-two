package lbnet.telegram.zerotwo.demo.zero1;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DemoZero1 {

    public static void main(String[] args) {
        try {
            log.info("main...");

        } catch (Exception e) {
            log.error("Exception: {}", e.getMessage(), e);
        }
    }

}
