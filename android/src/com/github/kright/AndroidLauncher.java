package com.github.kright;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        config.useCompass = false;
        config.useAccelerometer = false;
        config.r = config.g = config.b = config.a = 8;

        config.useImmersiveMode = true;
        config.hideStatusBar = true;

        initialize(new Main(), config);
    }
}
