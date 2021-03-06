package com.onideus.starfish.v1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.onideus.game.GameBeta;
import com.onideus.starfish.v1.actors.Turtle;
import com.onideus.starfish.v1.models.ActorBeta;

public class StarfishCollectorBeta extends GameBeta {
    private Turtle turtle;
    private ActorBeta starfish;
    private ActorBeta shark;
    private ActorBeta ocean;
    private ActorBeta winMessage;
    private ActorBeta loseMessage;

    @Override
    public void initialize() {
        ocean = new ActorBeta(new Texture(Gdx.files.internal("starfish-game/v1/water.jpg")));
        starfish = new ActorBeta(new Texture(Gdx.files.internal("starfish-game/v1/starfish.png")),
                380,
                380
        );
        turtle = new Turtle(new Texture(Gdx.files.internal("starfish-game/v1/turtle-1.png")),
                20,
                20
        );
        shark = new ActorBeta(new Texture(Gdx.files.internal("starfish-game/v1/sharky.png")),
                170,
                170
        );

        winMessage = new ActorBeta(new Texture(Gdx.files.internal("starfish-game/v1/you-win.png")),
                180,
                180
        );
        winMessage.setVisible(false);

        loseMessage = new ActorBeta(new Texture(Gdx.files.internal("starfish-game/v1/game-over.png")),
                180,
                180
        );
        loseMessage.setVisible(false);

        mainStage.addActor(ocean);
        mainStage.addActor(starfish);
        mainStage.addActor(turtle);
        mainStage.addActor(shark);
        mainStage.addActor(winMessage);
        mainStage.addActor(loseMessage);
    }

    public void update(float dt) {
        if(turtle.overlaps(starfish)) {
            starfish.remove();
            winMessage.setVisible(true);
        }

        if(turtle.overlaps(shark)) {
            turtle.remove();
            loseMessage.setVisible(true);
        }
    }
}
