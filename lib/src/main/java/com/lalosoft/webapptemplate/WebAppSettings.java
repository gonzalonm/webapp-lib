package com.lalosoft.webapptemplate;

import android.content.Context;

/**
 * Created by gonzalo on 10/19/17
 */

public final class WebAppSettings {

    private NetworkAvailabilityListener listener;

    private static WebAppSettings INSTANCE;

    public static WebAppSettings getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WebAppSettings();
        }
        return INSTANCE;
    }

    public void register(Context context) {

    }

}
