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
    private boolean win;

    @Override
    public void initialize() {
        BaseActor ocean = new BaseActor(0, 0, mainStage);
        ocean.loadTexture("water.jpg");
        ocean.setSize(800, 600);

        new Starfish(400,400, mainStage);
        new Starfish(500,100, mainStage);
        new Starfish(100,450, mainStage);
        new Starfish(200,250, mainStage);

        new Rock(200,150, mainStage);
        new Rock(100,300, mainStage);
        new Rock(300,350, mainStage);
        new Rock(450,200, mainStage);

        turtle = new Turtle(20, 20, mainStage);

        win = false;
    }

    @Override
    public void update(float dt) {
        for(BaseActor rock : BaseActor.getList(mainStage, Rock.class)) {
            turtle.preventOverlap(rock);
        }

        for(BaseActor starfishActor : BaseActor.getList(mainStage, Starfish.class)) {
            Starfish starfish = (Starfish) starfishActor;
            if(turtle.overlaps(starfish) && !starfish.isCollected()) {
                starfish.collect();
                starfish.clearActions();
                starfish.addAction(Actions.fadeOut(1));
                starfish.addAction(Actions.after(Actions.removeActor()));

                Whirlpool whirlpool = new Whirlpool(0, 0, mainStage);
                whirlpool.centerAtActor(starfish);
                whirlpool.setOpacity(0.25f);
            }
        }

        if(BaseActor.actorCount(mainStage, Starfish.class) == 0 && !win) {
            win = true;
            BaseActor youWinMessage = new BaseActor(0, 0, mainStage);
            youWinMessage.loadTexture("you-win.png");
            youWinMessage.centerAtPosition(400, 300);
            youWinMessage.setOpacity(0);
            youWinMessage.addAction(Actions.delay(1));
            youWinMessage.addAction(Actions.after(Actions.fadeIn(1)));
        }
    }
}
