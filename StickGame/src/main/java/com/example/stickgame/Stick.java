package com.example.stickgame;

import javafx.animation.AnimationTimer;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Arrays;

interface stickbuilder {
    void spawnstick(int x, int y, int pillarwidth, int randgap, ImageView player);
}

public class Stick implements stickbuilder {

    private AnchorPane gameroot;
    private Line stick;
    private ScaleTransition stretchingAnimation;
    private double initialStickLength = 5;
    private boolean isStretching = false;
    private int angle = 0;
    private int stickStartY;
    private int stickStartX;
    private boolean stopstick = false;

    public void setGameroot(AnchorPane root) {
        gameroot = root;
    }

    private ArrayList<String> images_paths = new ArrayList<>(Arrays.asList("file:images/p1.png", "file:images/p2.png", "file:images/p3.png", "file:images/p5.png", "file:images/p6.png"));
    private ArrayList<Integer> pillarsize = new ArrayList<>(Arrays.asList(121, 92, 27, 47, 69));

    @Override
    public void spawnstick(int x, int y, int pillarwidth, int randgap, ImageView player) {
//        stickStartY = y;
//        stickStartX = x;
//
//        stick = new Line(x, y, x, y - initialStickLength);
//        stick.setStrokeWidth(5);
//        gameroot.getChildren().add(stick);
//
//        stretchingAnimation = new ScaleTransition(Duration.seconds(2), stick);
//        stretchingAnimation.setFromY(1);
//        stretchingAnimation.setToY(1);
//        stretchingAnimation.setAutoReverse(false);
//        stretchingAnimation.setCycleCount(1);
//
//        Scene scene = Game.getGamescene();
//        scene.setOnMousePressed(event -> {
//            if (event.isPrimaryButtonDown()) {
//                if (!stopstick) {
//                    System.out.println("Pressed");
//                    isStretching = true;
//                    startStretchingAnimation();
//                }
//            }
//        });
//
//        scene.setOnMouseReleased(e -> {
//            if (e.getButton() == MouseButton.PRIMARY) {
//                if (!stopstick) {
//                    System.out.println("Released");
//                    isStretching = false;
//                    stopstick = true;
//                    System.out.println("Stick length is " + (y - stick.getEndY()));
//                    stretchingAnimation.stop();
//                    try {
//                        rotateAndMovePlayer(stick, stickStartX, pillarwidth, randgap, player);
//                    } catch (Exception e2) {
//                        e2.printStackTrace();
//                    }
//                }
//            }
//        });
    }

    private void startStretchingAnimation() {
        double maxLength = 600;
        new Thread(() -> {
            while (isStretching) {
                try {
                    double initialLength = stick.getStartY() - stick.getEndY();
                    if (initialLength >= maxLength) {
                        isStretching = false;
                        break;
                    }
                    if (initialLength < maxLength) {
                        double endY = stick.getEndY() - 1;
                        if (endY >= 0) {
                            stick.setEndY(endY);
                        }
                    }
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void rotateAndMovePlayer(Line stick, int pivotx, int pillarwidth, int randgap, ImageView player) {
        Rotate rotate = new Rotate();
        rotate.setPivotX(pivotx);
        rotate.setPivotY(stickStartY);
        stick.getTransforms().setAll(rotate);

        AnimationTimer rotationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (angle < 91) {
                    rotate.setAngle(angle);
                    angle++;
                } else {
                    this.stop();
//                    movePlayerAfterRotation(pillarwidth, randgap, player);
                }
            }
        };
        rotationTimer.start();
    }

    private void movePlayerAfterRotation(int pillarwidth, int randgap, ImageView player) {
        if ((stickStartY - stick.getEndY() >= randgap - 2) && (stickStartY - stick.getEndY() <= (randgap + pillarwidth))) {
            int newx = randgap + pillarwidth;
            TranslateTransition ani = new TranslateTransition(Duration.seconds(0.5), player);
            ani.setToX(newx);
            ani.play();

            if ((stickStartY - stick.getEndY() <= randgap + (pillarwidth / 2) + 2) && (stickStartY - stick.getEndY() >= randgap + (pillarwidth / 2) - 2)) {
                System.out.println("Lies in red region");
            }
        } else {
            System.out.println("Stick does not fit");
        }
    }
}
