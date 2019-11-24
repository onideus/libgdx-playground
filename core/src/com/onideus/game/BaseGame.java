package com.onideus.game;

import com.badlogic.gdx.Game;
import com.onideus.screen.BaseScreen;

public class BaseGame extends Game {
    private static BaseGame game;

    public BaseGame() {
        game = this;
    }

    public static void setActiveScreen(BaseScreen baseScreen) {
        game.setScreen(baseScreen);
    }

    @Override
    public void create() {

    }
}
