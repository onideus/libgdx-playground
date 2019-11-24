package com.onideus.starfish.v2.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

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
    private Polygon boundaryPolygon;
    private static Rectangle worldBounds;

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
        if (boundaryPolygon == null) {
            setBoundaryRectangle();
        }
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
        Texture texture = new Texture(Gdx.files.internal(MessageFormat.format("starfish-game/v2/{0}", fileName)),
                true);
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

    public void setBoundaryRectangle() {
        float width = getWidth();
        float height = getHeight();
        float[] vertices = {0, 0, width, 0, width, height, 0, height};
        boundaryPolygon = new Polygon(vertices);
    }

    public void setBoundaryPolygon(int numSides) {
        float width = getWidth();
        float height = getHeight();

        float[] vertices = new float[2 * numSides];
        for (int i = 0; i < numSides; i++) {
            float angle = i * 6.28f / numSides;
            vertices[2 * i] = width / 2 * MathUtils.cos(angle) + width / 2;
            vertices[2 * i + 1] = height / 2 * MathUtils.sin(angle) + height / 2;
        }

        boundaryPolygon = new Polygon(vertices);
    }

    public Polygon getBoundaryPolygon() {
        boundaryPolygon.setPosition(getX(), getY());
        boundaryPolygon.setOrigin(getOriginX(), getOriginY());
        boundaryPolygon.setRotation(getRotation());
        boundaryPolygon.setScale(getScaleX(), getScaleY());
        return boundaryPolygon;
    }

    public boolean overlaps(BaseActor other) {
        Polygon ourPolygon = this.getBoundaryPolygon();
        Polygon theirPolygon = other.getBoundaryPolygon();

        if (!ourPolygon.getBoundingRectangle().overlaps(theirPolygon.getBoundingRectangle())) {
            return false;
        }

        return Intersector.overlapConvexPolygons(ourPolygon, theirPolygon);
    }

    public Vector2 preventOverlap(BaseActor other) {
        Polygon ourPolygon = this.getBoundaryPolygon();
        Polygon theirPolygon = other.getBoundaryPolygon();

        if (!ourPolygon.getBoundingRectangle().overlaps(theirPolygon.getBoundingRectangle())) {
            return null;
        }

        MinimumTranslationVector minimumTranslationVector = new MinimumTranslationVector();
        boolean polygonOverlap = Intersector.overlapConvexPolygons(ourPolygon, theirPolygon, minimumTranslationVector);

        if (!polygonOverlap) {
            return null;
        }

        this.moveBy(minimumTranslationVector.normal.x * minimumTranslationVector.depth,
                minimumTranslationVector.normal.y * minimumTranslationVector.depth);

        return minimumTranslationVector.normal;
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

    public void centerAtPosition(float x, float y) {
        setPosition(x - getWidth() / 2, y - getHeight() / 2);
    }

    public void centerAtActor(BaseActor baseActor) {
        centerAtPosition(baseActor.getX() + baseActor.getWidth() / 2, baseActor.getY() + baseActor.getHeight() / 2);
    }

    public void setOpacity(float opacity) {
        this.getColor().a = opacity;
    }

    public static ArrayList<BaseActor> getList(Stage stage, Class actorClass) {
        ArrayList<BaseActor> baseActors = new ArrayList<>();

        for(Actor actor : stage.getActors()) {
            if(actorClass.isInstance(actor)) {
                baseActors.add((BaseActor) actor);
            }
        }

        return baseActors;
    }

    public static int actorCount(Stage stage, Class actorClass) {
        return getList(stage, actorClass).size();
    }

    public static void setWorldBounds(float width, float height) {
        worldBounds = new Rectangle(0, 0, width, height);
    }

    public static void setWorldBounds(BaseActor baseActor) {
        setWorldBounds(baseActor.getWidth(), baseActor.getHeight());
    }

    public void boundToWorld() {
        if(getX() < 0) {
            setX(0);
        }

        if(getX() + getWidth() > worldBounds.width) {
            setX(worldBounds.width - getWidth());
        }

        if(getY() < 0) {
            setY(0);
        }

        if(getY() + getHeight() > worldBounds.height) {
            setY(worldBounds.height - getHeight());
        }
    }

    public void alignCamera() {
        Camera camera = this.getStage().getCamera();
        Viewport viewport = this.getStage().getViewport();

        camera.position.set(this.getX() + this.getOriginX(), this.getY() + this.getOriginY(), 0);

        camera.position.x = MathUtils.clamp(camera.position.x, camera.viewportWidth / 2, worldBounds.width - camera.viewportWidth / 2);
        camera.position.y = MathUtils.clamp(camera.position.y, camera.viewportHeight / 2, worldBounds.height - camera.viewportHeight / 2);
        camera.update();
    }
}
