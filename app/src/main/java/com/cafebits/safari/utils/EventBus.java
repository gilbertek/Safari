package com.cafebits.safari.utils;

import com.squareup.otto.Bus;

/**
 * Created by Gilbert on 10/28/15.
 */
public class EventBus extends Bus {
    private static final EventBus bus = new EventBus();

    public static Bus getInstance() { return bus; }

    private EventBus() {}
}
