package com.codecool.snake.entities.enemies;

import com.codecool.snake.Globals;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.snakes.SnakeHead;
import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

import java.security.SecureRandom;

public class SamuraiRat extends GameEntity implements Animatable, Interactable {

    private static final int damage = 20;
    private final int RIGHT_MARGIN = 30, BOTTOM_MARGIN = 30;
    int speed;
    SecureRandom rnd;
    private Point2D heading;
    private double direction;

    public SamuraiRat(Pane pane) {
        super(pane);
        this.speed = 1;
        this.rnd = new SecureRandom();
        setImage(Globals.samuraiRatEnemy);
        pane.getChildren().add(this);
        setX(rnd.nextDouble() * (Globals.WINDOW_WIDTH - RIGHT_MARGIN));
        setY(rnd.nextDouble() * (Globals.WINDOW_HEIGHT - BOTTOM_MARGIN));
        this.direction = rnd.nextDouble() * 360;
        setRotate(direction);
        this.heading = Utils.directionToVector(direction, speed);

    }

    @Override
    public void step() {
        int chanceForAction = rnd.nextInt(100);

        // Check for collision with margin
        if (isOutOfBounds(0, RIGHT_MARGIN, 0, BOTTOM_MARGIN)) {
            direction += 180;
            heading = Utils.directionToVector(direction, speed);
        }
        // Random chance to change direction
        else if (chanceForAction == 0) {
            direction = Utils.randomizeDirection(direction, 60);
            heading = Utils.directionToVector(direction, speed);
        }
        // Chance for strike to snake head
        else if (chanceForAction == 50){
        }
        setX(getX() + heading.getX());
        setY(getY() + heading.getY());
    }

    @Override
    public void apply(SnakeHead player) {
        player.changeHealth(-damage);
        destroy();
    }

    @Override
    public String getMessage() {
        return damage + " damage from " + SamuraiRat.class.getSimpleName();
    }
}