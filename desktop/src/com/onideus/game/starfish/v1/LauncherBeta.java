package com.onideus.game.starfish.v1;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.onideus.starfish.v1.StarfishCollectorBeta;

public class LauncherBeta {
    public static void main(String[] args) {
        Game myGame = new StarfishCollectorBeta();
        LwjglApplication launcher =
                new LwjglApplication(myGame, "Starfish Collector", 800, 600);
    }
}
