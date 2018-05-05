package com.github.kright.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.github.kright.MainGame$;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Simplicivy";
        config.width = 1920 / 2;
        config.height = 1080 / 2;
        new LwjglApplication(MainGame$.MODULE$, config);
    }
}
