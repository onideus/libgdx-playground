package com.onideus.starfish.v2.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.onideus.starfish.v2.models.BaseActor;

import java.util.ArrayList;
import java.util.List;

public class Turtle extends BaseActor {
    public Turtle(float x, float y, Stage stage) {
        super(x, y, stage);

        List<String> fileNames = new ArrayList<>(List.of(
                "turtle-1.png",
                "turtle-2.png",
                "turtle-3.png",
                "turtle-4.png",
                "turtle-5.png",
                "turtle-6.png"
        ));

        loadAnimationFromFiles(fileNames, 0.1f, true);

        setAcceleration(400);
        setMaxSpeed(100);
        setDeceleration(400);

        setBoundaryPolygon(8);
    }

    public void act(float dt) {
        super.act(dt);

        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            setMotionAngle(getMotionAngle() + 3);
        }
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            setMotionAngle(getMotionAngle() - 3);
        }
        if (Gdx.input.isKeyPressed(Keys.UP)) {
            accelerateAtAngle(getRotation());
        }

        applyPhysics(dt);

        setAnimationPaused(!isMoving());

        setRotation(getMotionAngle() == 0 ? getRotation() : getMotionAngle());
    }
}
