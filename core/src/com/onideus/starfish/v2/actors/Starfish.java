package com.onideus.starfish.v2.actors;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.onideus.starfish.v2.models.BaseActor;

public class Starfish extends BaseActor {
    private boolean collected;

    public Starfish(float x, float y, Stage stage) {
        super(x, y, stage);

        collected = false;

        loadTexture("starfish.png");

        Action spin = Actions.rotateBy(30, 1);
        this.addAction(Actions.forever(spin));

        setBoundaryPolygon(8);
    }

    public boolean isCollected() {
        return collected;
    }

    public void collect() {
        collected = true;
        clearActions();
        addAction(Actions.fadeOut(1));
        addAction(Actions.after(Actions.removeActor()));
    }
}
