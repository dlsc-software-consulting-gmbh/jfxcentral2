package com.dlsc.jfxcentral2.mobile.util;

import com.dlsc.jfxcentral2.mobile.events.MobileLinkEvent;

public class MobileLinkUtil {

    private static final EventBus EVENT_BUS = EventBusUtil.getEventBus();

    private MobileLinkUtil() {
    }

    public static void getToPage(String link) {
        EVENT_BUS.post(new MobileLinkEvent(link));
    }
}
