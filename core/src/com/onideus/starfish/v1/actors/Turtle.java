package com.onideus.starfish.v1.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.onideus.starfish.v1.models.ActorBeta;

public class Turtle extends ActorBeta {
    public Turtle() {
        super();
    }

    public Turtle(Texture texture, float x, float y) {
        super(texture, x, y);
    }

    public void act(float dt) {
        super.act(dt);

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.moveBy(-1, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.moveBy(1, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.moveBy(0, 1);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            this.moveBy(0, -1);
        }
    }
}
