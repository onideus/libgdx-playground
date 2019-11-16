package com.onideus.starfish.v2;

import com.onideus.game.GameBeta;
import com.onideus.starfish.v2.actors.Starfish;
import com.onideus.starfish.v2.actors.Turtle;
import com.onideus.starfish.v2.models.BaseActor;

public class StarfishCollector extends GameBeta {
    private Turtle turtle;
    private Starfish starfish;
    private BaseActor ocean;

    @Override
    public void initialize() {
        ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("water.jpg");
        ocean.setSize(800, 600);

        starfish = new Starfish(380, 380, mainStage);

        turtle = new Turtle(20, 20, mainStage);
    }

    @Override
    public void update(float dt) {
        // to be defined
    }
}
