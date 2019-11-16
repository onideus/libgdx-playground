package com.onideus.starfish.v2.actors;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.onideus.starfish.v2.models.BaseActor;

public class Starfish extends BaseActor {
    public Starfish(float x, float y, Stage stage) {
        super(x, y, stage);

        loadTexture("starfish.png");

        Action spin = Actions.rotateBy(30, 1);
        this.addAction(Actions.forever(spin));
    }
}
