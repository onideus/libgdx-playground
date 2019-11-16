package com.onideus.starfish.v2.actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.onideus.starfish.v2.models.BaseActor;

public class Whirlpool extends BaseActor {
    public Whirlpool(float x, float y, Stage stage) {
        super(x, y, stage);

        loadAnimationFromSheet("whirlpool.png", 2, 5, 0.1f, false);
    }

    @Override
    public void act(float dt) {
        super.act(dt);

        if(isAnimationFinished()) {
            remove();
        }
    }
}
