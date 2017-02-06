package me.takimitsuha.tomatoalarmclock.library.timer;

import com.squareup.otto.Bus;

public class OttoAppConfig {
    private static Bus sBus;

    public static Bus getInstance() {
        if (sBus == null) {
            sBus = new Bus();
        }
        return sBus;
    }
}
