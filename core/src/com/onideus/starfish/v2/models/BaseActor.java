package com.onideus.starfish.v2.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseActor extends Actor {
    private Animation<TextureRegion> animation;
    private float elapsedTime;
    private boolean animationPaused;
    private Vector2 velocityVec;
    private Vector2 accelerationVec;
    private float acceleration;
    private float maxSpeed;
    private float deceleration;

    public BaseActor(float x, float y, Stage stage) {
        super();
        setPosition(x, y);
        stage.addActor(this);
        animation = null;
        elapsedTime = 0;
        animationPaused = false;
        velocityVec = new Vector2(0, 0);
        accelerationVec = new Vector2(0, 0);
        acceleration = 0;
        maxSpeed = 1000;
        deceleration = 0;
    }

    public void setAnimation(Animation<TextureRegion> textureRegionAnimation) {
        animation = textureRegionAnimation;
        TextureRegion textureRegion = animation.getKeyFrame(0);
        float width = textureRegion.getRegionWidth();
        float height = textureRegion.getRegionHeight();
        setSize(width, height);
        setOrigin(width / 2, height / 2);
    }

    public void setAnimationPaused(boolean paused) {
        animationPaused = paused;
    }

    @Override
    public void act(float dt) {
        super.act(dt);

        if (!animationPaused) {
            elapsedTime += dt;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.setColor(getColor());

        if (animation != null && isVisible()) {
            batch.draw(animation.getKeyFrame(elapsedTime),
                    getX(),
                    getY(),
                    getOriginX(),
                    getOriginY(),
                    getWidth(),
                    getHeight(),
                    getScaleX(),
                    getScaleY(),
                    getRotation()
            );
        }
    }

    public Animation<TextureRegion> loadAnimationFromFiles(List<String> fileNames, float frameDuration, boolean loop) {
        Animation<TextureRegion> textureRegionAnimation = new Animation<>(
                frameDuration,
                fileNames.stream()
                        .map(fileName -> new Texture(Gdx.files.internal(
                                MessageFormat.format("starfish-game/v2/{0}", fileName))))
                        .map(texture -> {
                            texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
                            return new TextureRegion(texture);
                        }).toArray(TextureRegion[]::new)
        );

        setAnimationAndPlayMode(textureRegionAnimation, loop);

        return textureRegionAnimation;
    }

    public Animation<TextureRegion> loadAnimationFromSheet(String fileName, int rows, int columns,
                                                           float frameDuration, boolean loop) {
        Texture texture = new Texture(Gdx.files.internal(fileName), true);
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        int frameWidth = texture.getWidth() / columns;
        int frameHeight = texture.getHeight() / rows;

        TextureRegion[][] splitRegion = TextureRegion.split(texture, frameWidth, frameHeight);

        List<TextureRegion> textureRegions = new ArrayList<>();

        for (int r = 0; r < rows; r++) {
            textureRegions.addAll(Arrays.asList(splitRegion[r]).subList(0, columns));
        }

        Animation<TextureRegion> textureRegionAnimation = new Animation<>(frameDuration,
                textureRegions.toArray(new TextureRegion[0]));

        setAnimationAndPlayMode(textureRegionAnimation, loop);

        return textureRegionAnimation;
    }

    public Animation<TextureRegion> loadTexture(String fileName) {
        return loadAnimationFromFiles(List.of(fileName), 1, true);
    }

    public void applyPhysics(float dt) {
        velocityVec.add(accelerationVec.x * dt, accelerationVec.y * dt);

        float speed = getSpeed();

        if (accelerationVec.len() == 0) {
            speed -= deceleration * dt;
        }

        speed = MathUtils.clamp(speed, 0, maxSpeed);

        setSpeed(speed);

        moveBy(velocityVec.x * dt, velocityVec.y * dt);

        accelerationVec.set(0, 0);
    }

    public boolean isAnimationFinished() {
        return animation.isAnimationFinished(elapsedTime);
    }

    public void setSpeed(float speed) {
        if (velocityVec.len() == 0) {
            velocityVec.set(speed, 0);
        } else {
            velocityVec.setLength(speed);
        }
    }

    public float getSpeed() {
        return velocityVec.len();
    }

    public void setMotionAngle(float angle) {
        velocityVec.setAngle(angle);
    }

    public float getMotionAngle() {
        return velocityVec.angle();
    }

    public boolean isMoving() {
        return getSpeed() > 0;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public void accelerateAtAngle(float angle) {
        accelerationVec.add(new Vector2(acceleration, 0).setAngle(angle));
    }

    public void accelerateForward() {
        accelerateAtAngle(getRotation());
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setDeceleration(float deceleration) {
        this.deceleration = deceleration;
    }

    private void setAnimationAndPlayMode(Animation<TextureRegion> textureRegionAnimation, boolean loop) {
        if (loop) {
            textureRegionAnimation.setPlayMode(Animation.PlayMode.LOOP);
        } else {
            textureRegionAnimation.setPlayMode(Animation.PlayMode.NORMAL);
        }

        if (animation == null) {
            setAnimation(textureRegionAnimation);
        }
    }
}
