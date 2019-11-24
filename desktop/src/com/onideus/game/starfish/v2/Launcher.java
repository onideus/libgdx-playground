package com.onideus.game.starfish.v2;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.onideus.game.StarfishGame;

public class Launcher {
    public static void main(String[] args) {
        Game myGame = new StarfishGame();
        LwjglApplication lwjglApplication = new LwjglApplication(myGame, "Starfish Collector", 800, 600);
    }
}
