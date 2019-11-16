package com.onideus.starfish.v2.actors;

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
    }
}
