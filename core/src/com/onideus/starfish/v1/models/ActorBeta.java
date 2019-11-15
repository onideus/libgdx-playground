package com.onideus.starfish.v1.models;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;



public class ActorBeta extends Actor {
    private TextureRegion textureRegion;
    private Rectangle rectangle;

    public ActorBeta() {
        super();
        textureRegion = new TextureRegion();
        rectangle = new Rectangle();
    }

    public ActorBeta(Texture texture) {
        this();
        setTexture(texture);
    }

    public ActorBeta(Texture texture, float xPosition, float yPosition) {
        this(texture);
        setPosition(xPosition, yPosition);
    }

    public void setTexture(Texture texture) {
        textureRegion.setRegion(texture);
        setSize(texture.getWidth(), texture.getHeight());
        rectangle.setSize(texture.getWidth(), texture.getHeight());
    }

    public Rectangle getRectangle() {
        rectangle.setPosition(getX(), getY());
        return rectangle;
    }

    public boolean overlaps(ActorBeta other) {
        return this.getRectangle().overlaps(other.getRectangle());
    }

    public void act(float dt) {
        super.act(dt);
    }

    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        Color c = getColor();

        batch.setColor(c.r, c.g, c.b, c.a);

        if(isVisible()) {
            batch.draw(textureRegion, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }
}
