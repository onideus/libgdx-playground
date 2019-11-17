package com.onideus.starfish.v2;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.onideus.game.GameBeta;
import com.onideus.starfish.v2.actors.Rock;
import com.onideus.starfish.v2.actors.Starfish;
import com.onideus.starfish.v2.actors.Turtle;
import com.onideus.starfish.v2.actors.Whirlpool;
import com.onideus.starfish.v2.models.BaseActor;

public class StarfishCollector extends GameBeta {
    private Turtle turtle;
    private Starfish starfish;
    private BaseActor ocean;
    private Rock rock;

    @Override
    public void initialize() {
        ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("water.jpg");
        ocean.setSize(800, 600);

        starfish = new Starfish(380, 380, mainStage);

        turtle = new Turtle(20, 20, mainStage);
        rock = new Rock(200, 200, mainStage);
    }

    @Override
    public void update(float dt) {
        if(turtle.overlaps(starfish) && !starfish.isCollected()) {
            starfish.collect();

            Whirlpool whirlpool = new Whirlpool(0, 0, mainStage);
            whirlpool.centerAtActor(starfish);
            whirlpool.setOpacity(0.25f);

            BaseActor youWinMessage = new BaseActor(0, 0, mainStage);
            youWinMessage.loadTexture("you-win.png");
            youWinMessage.centerAtPosition(400, 300);
            youWinMessage.setOpacity(0);
            youWinMessage.addAction(Actions.delay(1));
            youWinMessage.addAction(Actions.after(Actions.fadeIn(1)));
        }

        turtle.preventOverlap(rock);
    }
}
