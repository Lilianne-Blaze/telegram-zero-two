package lbnet.telegram.zerotwo;

import org.junit.jupiter.api.Test;

public class MultiBotSessionTest {
    @Test
    public void testNewInstance() throws Exception {
        MultiBotSession.class.newInstance();
        MultiBotSession.StartPaused.class.newInstance();
    }
}
