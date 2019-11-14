package com.onideus.game.starfish;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.onideus.starfish.StarfishCollectorBeta;

public class LauncherBeta {
    public static void main(String[] args) {
        Game myGame = new StarfishCollectorBeta();
        LwjglApplication launcher =
                new LwjglApplication(myGame, "Starfish Collector", 800, 600);
    }
}
