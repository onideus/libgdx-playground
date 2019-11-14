package com.onideus.starfish;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.onideus.game.GameBeta;
import com.onideus.starfish.actors.Turtle;
import com.onideus.starfish.models.ActorBeta;

public class StarfishCollectorBeta extends GameBeta {
    private Turtle turtle;
    private ActorBeta starfish;
    private ActorBeta ocean;
    private ActorBeta winMessage;

    private boolean win;

    @Override
    public void initialize() {
        ocean = new ActorBeta(new Texture(Gdx.files.internal("starfish-game/water.jpg")));
        starfish = new ActorBeta(new Texture(Gdx.files.internal("starfish-game/starfish.png")),
                380,
                380
        );
        turtle = new Turtle(new Texture(Gdx.files.internal("starfish-game/turtle-1.png")),
                20,
                20
        );
        winMessage = new ActorBeta(new Texture(Gdx.files.internal("starfish-game/you-win.png")),
                180,
                180
        );
        winMessage.setVisible(false);

        mainStage.addActor(ocean);
        mainStage.addActor(starfish);
        mainStage.addActor(turtle);
        mainStage.addActor(winMessage);

        win = false;
    }

    public void update(float dt) {
        if(turtle.overlaps(starfish)) {
            starfish.remove();
            winMessage.setVisible(true);
        }
    }
}
