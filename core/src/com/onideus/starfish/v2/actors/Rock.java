package com.onideus.starfish.v2.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.onideus.starfish.v2.models.BaseActor;

public class Rock extends BaseActor {
    public Rock(float x, float y, Stage stage) {
        super(x, y, stage);
        loadTexture("rock.png");
        setBoundaryPolygon(8);
    }
}
