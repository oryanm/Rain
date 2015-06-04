package net.oryanmat.rain.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import net.oryanmat.rain.TetraRain;

public class HtmlLauncher extends GwtApplication {

    @Override
    public GwtApplicationConfiguration getConfig() {
        return new GwtApplicationConfiguration(1200, 800);
    }

    @Override
    public ApplicationListener getApplicationListener() {
        return new TetraRain();
    }
}