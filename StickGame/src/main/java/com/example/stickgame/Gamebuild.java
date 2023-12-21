package com.example.stickgame;

import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class Gamebuild {


    private int min_gap = 5;
    private int max_gap = 175;
    private AnchorPane gameroot;
    private Stick stick;
    public void setGameroot(AnchorPane root) {
        gameroot = root;
    }

    private ArrayList<String> images_paths = new ArrayList<>(Arrays.asList("file:images/p1.png","file:images/p2.png","file:images/p3.png","file:images/p5.png","file:images/p6.png"));
    private ArrayList<Integer> pillarsize = new ArrayList<>(Arrays.asList(121,92,27,47,69));

    public int randomGap(){
        Random rand = new Random();
        int randgap = rand.nextInt(max_gap - min_gap + 1) + min_gap;
        return randgap;
    }

    public void firstspawn() {
        try {
            Random rand = new Random();
            int randpillar = rand.nextInt(5);
            int pillarwidth = pillarsize.get(randpillar);

            Image image = new Image(images_paths.get(randpillar));
            Rectangle rectangle = new Rectangle(0, 0, 150, 240); // Initialize rectangle off-screen
            Image image2 = new Image("file:images/char.png");
            ImageView pillar = new ImageView(image);
            ImageView player = new ImageView(image2);
            stick = new Stick();
            stick.setGameroot(gameroot);
            rectangle.setLayoutY(-rectangle.getHeight()); // Move rectangle off-screen
            int randgap= randomGap();
            pillar.setLayoutX(150 + randgap);
            player.setLayoutX(115);

            gameroot.getChildren().add(rectangle);


            // Animate the rectangle
            TranslateTransition rectangleTransition = new TranslateTransition(Duration.seconds(0.5), rectangle);
            rectangleTransition.setToY(700); // Set final Y position
            rectangleTransition.play();

            rectangleTransition.setOnFinished(event -> {
                // Animate the player after the rectangle animation finishes
                gameroot.getChildren().add(player);

                TranslateTransition playerTransition = new TranslateTransition(Duration.seconds(0.5), player);
                playerTransition.setToY(425); // Set final Y position
                playerTransition.play();

                playerTransition.setOnFinished(event1 -> {
                    gameroot.getChildren().add(pillar);
                    // Animate the pillar after the player animation finishes
                    TranslateTransition pillarTransition = new TranslateTransition(Duration.seconds(0.5), pillar);
                    pillarTransition.setToY(460); // Set final Y position
                    pillarTransition.play();
                    pillarTransition.setOnFinished(event2 -> {
                        try
                        {
                            stick.spawnstick(150,460 , pillarwidth, randgap , player);
                            System.out.println("whis");

                        }
                        catch (Exception e){
                            System.out.println("Error in catching spawnstick");
                        }
                    });
                });
            });
        } catch (Exception e) {
            System.out.println("Error in firstspawn ");
//            System.out.println(e);
        }
    }

    public void spawn() {
//        int numb = (int) (Math.random() * (max - min + 1) + min);
    }
}
