package com.example.stickgame;

import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;


interface gameMethods{
    public void gameEndTrans();
    public void addberries();
    public void spawnstick(int y , ImageView player);
    public ImageView spawnBerry();
    public ImageView spawnPillar();

}

public class SpawnPillars implements gameMethods{

    private static final double SPEED =0.2;
    private boolean isMoving = true;
    private RotateTransition rotateTransition;
    private int min_gap = 60 ;
    private int playerwidth = 30;
    private int max_gap = 175;
    private AnchorPane gameroot;
    private int berryx = 0 ;
    private int berryy = 0 ;
    private ArrayList<String> images_paths = new ArrayList<>(Arrays.asList("file:images/p1.png","file:images/p2.png","file:images/p3.png","file:images/p5.png","file:images/p6.png"));
    private ArrayList<Integer> pillarsize = new ArrayList<>(Arrays.asList(121,92,27,47,69));
    private ArrayList<String> berriespath = new ArrayList<>(Arrays.asList("file:images/bry.png"));
    private ScaleTransition stretchingAnimation;
    private double initialStickLength = 5;
    private boolean isStretching = false;
    private int angle = 0;
    private int stickStartY;
    private boolean isBerry = false;
    private int stickStartX = 121;
    private boolean stopstick = false;
    private Line stick;
    private int randgap;
    private int startPos = 121;
    private double length = 5;
    private ImageView player;
    private ImageView pillar1;
    private ImageView pillar2;
    private int levelY = 425;
    private int playerY = levelY - 9;
    private ImageView berry;
    private int pillar2width ;
    private int playerheight = 35;
    private Label currentscore;
    private boolean isAddberries = false;
    private boolean flipDirection = false;
    private boolean isUpsideDown = false;
    private boolean moveBackground = false;
    private boolean startflip = false;
    private int reviveberries = 10;
    private int totalberries ;
    private Label score;
    private int currentberries = 0;
    private boolean firstime = true;
    private Volume sound;

    private double WOBBLE_ANGLE = 5;
    private double WOBBLE_DURATION = 0.5;

    private Scenecontroller scenecontroller;
    public int randomGap(){
        Random rand = new Random();
        int randgap = rand.nextInt(max_gap - min_gap + 1) + min_gap;
        return randgap;
    }

    public SpawnPillars(Scenecontroller controller){
        this.scenecontroller = controller;
    }
    public void setGameroot(AnchorPane root  ) {
        gameroot = root;
    }

    public void gameEndTrans(){
        Pane gameEndPane = new Pane();

//        ----------RETRY BUTTON
        Button retry_button = new Button();
        retry_button.setShape(new Circle(10));
        retry_button.setPrefHeight(70);
        retry_button.setPrefWidth(70);
        retry_button.setLayoutX(12);
        retry_button.setLayoutY(40);
        retry_button.setCursor(Cursor.HAND);
        retry_button.setOnAction(e->{

        });

        retry_button.setGraphic(new ImageView(new Image("file:images/bb1.png")));
        retry_button.setPadding(Insets.EMPTY);


        retry_button.setLayoutX(60);
        retry_button.setLayoutY(300);
        retry_button.setOnAction(e->{
            User u1 = new User();
            u1.setHighestberries(totalberries);
            try {
                serialize(u1);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            Game newgame = new Game(scenecontroller);
            try {
                sound.stop();
                newgame.displaygame();
            } catch (UnsupportedAudioFileException ex) {
                throw new RuntimeException(ex);
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

//        ----REVIVE BUTTON
        Button revive = new Button();
        revive.setShape(new Circle(10));
        revive.setPrefHeight(70);
        revive.setPrefWidth(70);
        revive.setLayoutX(12);
        revive.setLayoutY(40);
        revive.setCursor(Cursor.HAND);


        revive.setGraphic(new ImageView(new Image("file:images/revivee.png")));
        revive.setPadding(Insets.EMPTY);
        revive.setLayoutX(130);
        revive.setLayoutY(190);

        revive.setOnAction(e->{
            gameroot.getChildren().remove(stick);
            System.out.println("here");
            try {
                sound.stop();
            } catch (UnsupportedAudioFileException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
            gameroot.getChildren().remove(player);
            player = new ImageView(new Image("file:images/character3.png"));
            player.setX(startPos - playerwidth);
            player.setY(playerY);
            gameroot.getChildren().add(player);
            if(totalberries - reviveberries >= 0){
                stopstick = false;
                isStretching = true;
                isBerry = true;
                isAddberries = true;
                totalberries -= reviveberries;
                gameroot.getChildren().remove(gameEndPane);
                gameroot.getChildren().remove(score);
                score = new Label(String.valueOf(totalberries));
                score.setStyle("-fx-font-weight: bold ; -fx-font-family : Roboto ; -fx-font-size:30px; -fx-letter-spacing:2em;");
                ImageView showberry = new ImageView(new Image("file:images/dfgdfg.png"));
                showberry.setX(380);
                score.setLayoutY(5);
                score.setLayoutX(420);
                gameroot.getChildren().addAll(score,showberry);
                isAddberries = false;
                sound.setFilePath("sounds/music1.wav");
                try {
                    sound.play();

                    spawnstick(460,player);
                } catch (UnsupportedAudioFileException ex) {
                    throw new RuntimeException(ex);
                } catch (LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else{
                System.out.println("i am in else");
                Pane revivepane = new Pane();
                revivepane.setPrefWidth(340);
                revivepane.setPrefHeight(200);

                ImageView dont = new ImageView(new Image("file:images/dontt.png"));
                dont.setLayoutX(20);
                dont.setLayoutY(50);
                Button okbutton = new Button();
                okbutton.setShape(new Circle(10));
                okbutton.setPrefHeight(70);
                okbutton.setPrefWidth(70);
                okbutton.setLayoutX(12);
                okbutton.setLayoutY(40);
                okbutton.setCursor(Cursor.HAND);
                okbutton.setOnAction(e2->{
                    System.out.println("hey ");
                });

                okbutton.setGraphic(new ImageView(new Image("file:images/okay.png")));
                okbutton.setPadding(Insets.EMPTY);


                okbutton.setLayoutX(130);
                okbutton.setLayoutY(100);
                revivepane.setStyle(
                        "-fx-background-color: rgba(255, 255, 255, 0.5); " +
                                "-fx-background-radius: 10;"
                                +"-fx-background-color: #ffffff; " );

                okbutton.setOnAction(e3->{
                    gameroot.getChildren().remove(revivepane);
                    gameEndTrans();
                });
                revivepane.getChildren().addAll(okbutton,dont);
                gameroot.getChildren().remove(gameEndPane);
                revivepane.setLayoutX(70);
                revivepane.setLayoutY(200);
                gameroot.getChildren().add(revivepane);

            }

        });

//        EXIT BUTTON
        Button exit_button = new Button();
        exit_button.setShape(new Circle(10));
        exit_button.setPrefHeight(70);
        exit_button.setPrefWidth(70);
        exit_button.setLayoutX(12);
        exit_button.setLayoutY(40);
        exit_button.setCursor(Cursor.HAND);
        exit_button.setOnAction(e->{
            try {
                gameEnd();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        exit_button.setGraphic(new ImageView(new Image("file:images/bb2.png")));
        exit_button.setPadding(Insets.EMPTY);
        exit_button.setLayoutX(200);
        exit_button.setLayoutY(300);


        gameEndPane.setLayoutX(57);
        gameEndPane.setLayoutY(150);
        gameEndPane.setPrefWidth(350);
        gameEndPane.setPrefHeight(450);



//      GAME OVER
        Image image1 = new Image("file:images/yoyo.png");
        ImageView gameover=  new ImageView(image1);
        gameover.setLayoutX(80);
        gameover.setLayoutY(30);

        gameEndPane.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.5); " +
                "-fx-background-radius: 10;"
        +"-fx-background-color: #ffffff; " );

        gameEndPane.getChildren().addAll(gameover,retry_button,revive,exit_button);
        gameroot.getChildren().add(gameEndPane);
    }

    public int getReviveberries() {
        return reviveberries;
    }

    public int getTotalberries() {
        return totalberries;
    }

//    will serialize
    public void serialize(User user) throws IOException {
        FileOutputStream file =null;
        ObjectOutputStream out = null;

        try {
            file = new FileOutputStream("save.txt");
            out = new ObjectOutputStream(file);
            out.writeObject(user);
        }
        catch (Exception e){
            System.out.println("Error in Serializing ");
        }
        finally {
            if(file != null){
                file.close();
            }
            if(out != null){
                out.close();
            }
        }
    }


//    will deserialize
    public void deserialize() throws IOException , ClassNotFoundException {
        User userdata = null;
        System.out.println("here123");
        FileInputStream filein = null;
        ObjectInputStream in = null;
        Map<String , Integer> mp = new HashMap<>();
        ArrayList<Integer> a = new ArrayList<>();

        try {
            filein = new FileInputStream("save.txt");
            in = new ObjectInputStream(filein);
            userdata = (User) in.readObject();
            totalberries = userdata.getHighestberries();
            RandomAccessFile file= new RandomAccessFile("output.txt", "rw");
            file.setLength(0);

        }
        catch (Exception e){
            System.out.println(e);
        }
        finally {
            if(userdata == null){
                totalberries = 0;
            }
            if(filein != null){
                filein.close();
            }
            if(in != null){
                in.close();
            }
            System.out.println("Getting info");

        }
    }


// will end the game
    public void gameEnd() throws IOException {
        User u = new User();
        u.setHighestberries(totalberries);
        serialize(u);
        System.exit(0);
    }
    private ImageView background ;
    private ImageView background2;

//    Spawn when first time game calls
    public void firstspawn() {

        try {
            deserialize();
            sound = new Volume();
            sound.setFilePath("sounds/music1.wav");
            sound.play();
            currentberries = 0;
            score = new Label(String.valueOf(totalberries));
            score.setStyle("-fx-font-weight: bold ; -fx-font-family : Roboto ; -fx-font-size:30px; -fx-letter-spacing:2em;");
            ImageView showberry = new ImageView(new Image("file:images/dfgdfg.png"));
            showberry.setX(380);
            score.setLayoutY(5);
            score.setLayoutX(420);
            Random rand = new Random();
            int randpillar1 = rand.nextInt(5);
            int randpillar2 = rand.nextInt(5);

            pillar2width = pillarsize.get(randpillar2);
            int randberry = rand.nextInt(berriespath.size());
            int pillar1width = pillarsize.get(randpillar1);
            Image image = new Image(images_paths.get(randpillar1));

            Image image2 = new Image("file:images/character3.png");
            Image image3 = new Image(berriespath.get(randberry));
            Image image4= new Image(images_paths.get(randpillar2));
            pillar2 = new ImageView(image4);
            pillar1 = new ImageView(image);
            player = new ImageView(image2);
            berry = new ImageView(image3);


            randgap= randomGap();
            pillar1.setX(startPos - pillar1width);
            pillar2.setX(startPos + randgap);


            Image backgroundImage = new Image("file:images/gggg.png.", 470, 700, false, true);
            background = new ImageView(backgroundImage);
            gameroot.getChildren().add(background);

            // Create a second image view for cycling effect
             background2 = new ImageView(backgroundImage);
            background2.setTranslateX(470);
            gameroot.getChildren().add(background2);
            gameroot.getChildren().addAll(score,showberry);

            showScore();

            gameroot.getChildren().add(pillar1);
            TranslateTransition pillar1Transition = new TranslateTransition(Duration.seconds(0.5), pillar1);
            pillar1Transition.setToY(460); // Set final Y position
            pillar1Transition.play();

            pillar1Transition.setOnFinished(event -> {
                pillar1.setTranslateY(0);
                pillar1.setY(460);
                // Animate the player after the rectangle animation finishes
                player.setX(startPos - playerwidth);
                gameroot.getChildren().add(player);
                TranslateTransition playerTransition = new TranslateTransition(Duration.seconds(0.5), player);
                playerTransition.setToY(playerY); // Set final Y position
                playerTransition.play();

                playerTransition.setOnFinished(event1 -> {
                    player.setTranslateY(0);
                    player.setY(playerY);
//                    wobblePlayer(player);
                    gameroot.getChildren().add(pillar2);
                    // Animate the pillar1 after the player animation finishes
                    TranslateTransition pillar2Transition = new TranslateTransition(Duration.seconds(0.5), pillar2);
                    pillar2Transition.setToY(460); // Set final Y position
                    pillar2Transition.play();
                    pillar2Transition.setOnFinished(event2 -> {
                        pillar2.setTranslateY(0);
                        pillar2.setY(460);
                        try
                        {
                            spawnstick(460,player);
                        }
                        catch (Exception e){
                            System.out.println("Error in catching spawnstick");
                        }
                    });
                });
            });
        } catch (Exception e) {
            System.out.println("Error in firstspawn ");
        }
    }

//      Will spawn the stick
        public void spawnstick( int y, ImageView player) {

            stickStartY = y;
            gameroot.getChildren().remove(stick);
            stick = new Line(stickStartX, y, stickStartX, y - initialStickLength);
            stick.setStrokeWidth(5);
            gameroot.getChildren().add(stick);

            stretchingAnimation = new ScaleTransition(Duration.seconds(0.5), stick);
            stretchingAnimation.setFromY(1);
            stretchingAnimation.setToY(1);
            stretchingAnimation.setAutoReverse(false);
            stretchingAnimation.setCycleCount(1);

            Scene scene = Game.getGamescene();
            scene.setOnMousePressed(event -> {
                if (event.isPrimaryButtonDown()) {
                    if (!stopstick) {
                        System.out.println("Pressed");
                        isStretching = true;
                        startStretchingAnimation();
                    }
                }

                if(startflip){
                    flipDirection = !flipDirection;

                    if (flipDirection) {
                        player.setY(playerY+playerheight + 10); // Move the character to the bottom
                    } else {
                        player.setY(playerY); // Move the character back to its original Y-coordinate
                    }

                    // Invert the character vertically (180-degree rotation)
                    isUpsideDown = !isUpsideDown;
                    player.setRotationAxis(new Point3D(1,0,0));
                    player.setRotate(isUpsideDown ? 180 : 0);
                }
            });

            scene.setOnMouseReleased(e -> {
                if (e.getButton() == MouseButton.PRIMARY) {
                    if (!stopstick) {
                        System.out.println("Released");
                        length = stickStartY - stick.getEndY();
                        isStretching = false;
                        stopstick = true;
                        System.out.println("Stick length is " + (y - stick.getEndY()));

                        stretchingAnimation.stop();
                        try {
                            rotateAndMovePlayer( stickStartX, player);
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    }
                }
            });

        }


    private void startStretchingAnimation() {
        double maxLength = 600;

//       todo:  USING MULTITHREADING
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
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

//    rotate stick
    private void rotateAndMovePlayer( int pivotx, ImageView player) {
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
                    angle = 0;

                    try {
                        movePlayerAfterRotation(  player);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };


        rotationTimer.start();
    }

//    will add berries
    public void addberries(){
        totalberries++;
        gameroot.getChildren().remove(score);
        score = new Label(String.valueOf(totalberries));
        score.setStyle("-fx-font-weight: bold ; -fx-font-family : Roboto ; -fx-font-size:30px; -fx-letter-spacing:2em;");
        ImageView showberry = new ImageView(new Image("file:images/dfgdfg.png"));
        showberry.setX(380);
        score.setLayoutY(5);
        score.setLayoutX(420);
        gameroot.getChildren().addAll(score,showberry);
        isAddberries = false;
    }

//    will show score
    public void showScore(){

        Label scoreplate = new Label("Score: ");
        scoreplate.setStyle("-fx-font-weight: bold ; -fx-font-family : Roboto ; -fx-font-size:30px; -fx-letter-spacing:2em; -fx-text-fill: black;");
        scoreplate.setLayoutX(20);
        scoreplate.setLayoutY(5);

        currentscore = new Label(String.valueOf(currentberries));
        currentscore.setStyle("-fx-font-weight: bold ; -fx-font-family : Roboto ; -fx-font-size:30px; -fx-letter-spacing:2em; -fx-text-fill: black;");
        currentscore.setLayoutX(110);
        currentscore.setLayoutY(5);

        gameroot.getChildren().addAll(currentscore , scoreplate);
    }

//    will move player after rotation
    private void movePlayerAfterRotation(  ImageView player) throws InterruptedException {

        if ((length >= randgap - 1) && (length <= (randgap + pillar2width))) {

            int newx = randgap + pillar2width ;
            moveBackground = true;
            TranslateTransition ani = new TranslateTransition(Duration.seconds(2), player);
            ani.setToX(newx);
            ani.play();

            startflip= true;


            AnimationTimer timer = new AnimationTimer() {
                @Override
                public void handle(long now) {


                    if(moveBackground ){
                        background.setTranslateX(background.getTranslateX() - SPEED);
                        background2.setTranslateX(background2.getTranslateX() - SPEED);

                        if (background.getTranslateX() <= -470) {
                            background.setTranslateX(470);
                        }
                        if (background2.getTranslateX() <= -470) {
                            background2.setTranslateX(470);
                        }
                    }

                    if(((player.getTranslateX() + player.getX()) >= startPos + randgap-playerwidth-1 )){
                        startflip = false;
                    }
                    if(flipDirection && ((player.getTranslateX() + player.getX()) >= startPos + randgap-playerwidth-1 ) && ((player.getTranslateX() + player.getX()) <= startPos + randgap-playerwidth+1 )){
                        this.stop();
                        ani.stop();
                        isMoving =false;
                        player.setTranslateX(0);
                        player.setX(startPos + randgap-playerwidth);
                        TranslateTransition falldown = new TranslateTransition(Duration.seconds(1),player);
                        falldown.setToY(800);
                        falldown.play();
                        falldown.setOnFinished(e->{
                            gameroot.getChildren().remove(player);
                            try {
                                sound.stop();
                                sound.setFilePath("sounds/gameover.wav");
                                sound.playGameover();
                            } catch (UnsupportedAudioFileException ex) {
                                throw new RuntimeException(ex);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            } catch (LineUnavailableException ex) {
                                throw new RuntimeException(ex);
                            }

                            gameEndTrans();

                        });
                    }



                    if(((int) (player.getTranslateX() + player.getX() )<= berryx + 4) && ((int) (player.getTranslateX() + player.getX() )>= berryx - 4) && (berryy == levelY  && !flipDirection) ){

                        if(isAddberries){
                            addberries();
                        }
                        isBerry = false;
                        gameroot.getChildren().remove(berry);
                    }

                    if(((int) (player.getTranslateX() + player.getX() )<= berryx +4) && ((int) (player.getTranslateX() + player.getX() )>= berryx - 4) && (berryy == levelY+30 && flipDirection) ){
                        if(isAddberries){
                            addberries();
                        }
                        isBerry = false;
                        gameroot.getChildren().remove(berry);
                    }


                }
            };

            timer.start();
            ani.setOnFinished(e->{
                startflip = false;
                moveBackground = false;
                isAddberries = true;
                gameroot.getChildren().remove(berry);
                berryx = 0;
                player.setTranslateX(0);

                player.setX(startPos+ randgap + pillar2width - playerwidth);

                if(isMoving){

                    moveScene();
                }
            });
        }
        else {

            stick.setEndX(stickStartY - stick.getEndY() + stickStartX);
            stick.setEndY(stickStartY);
            Rotate r2 = new Rotate();
            r2.setPivotX(stickStartX);
            r2.setPivotY(stickStartY);
            stick.getTransforms().setAll(r2);
            TranslateTransition ani = new TranslateTransition(Duration.seconds(2), player);
            ani.setToX(length+20);
            ani.play();
            ani.setOnFinished(e->{
                if(length > randgap + pillar2width){
                    TranslateTransition ani2 = new TranslateTransition(Duration.seconds(2), player);
                    if(length + stickStartX > 470){
                        ani2.setToX(500);
                    }
                    else{

                        ani2.setToY(700);
                    }
                    ani2.play();
                    ani2.setOnFinished(e2 -> {

//                      TODO: HERE THE GAME ENDS WRITE CONDITIONS
                        try {
                            sound.stop();
                            sound.setFilePath("sounds/gameover.wav");
                            sound.playGameover();
                        } catch (UnsupportedAudioFileException ex) {
                            throw new RuntimeException(ex);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        } catch (LineUnavailableException ex) {
                            throw new RuntimeException(ex);
                        }

                        gameroot.getChildren().remove(player);
                        gameEndTrans();

                    });
                }
                else {
                    AnimationTimer rotationTimer = new AnimationTimer() {
                        int angle2 = 0;

                        @Override
                        public void handle(long now) {
                            if (angle2 < 91) {
                                r2.setAngle(angle2);
                                angle2++;
                            } else {
                                this.stop();
                                TranslateTransition ani2 = new TranslateTransition(Duration.seconds(0.5), player);
                                ani2.setToY(700);
                                ani2.play();
                                ani2.setOnFinished(e -> {

//                      TODO: HERE THE GAME ENDS WRITE CONDITIONS
                                    try {
                                        sound.stop();
                                        sound.setFilePath("sounds/gameover.wav");
                                        sound.playGameover();
                                    } catch (UnsupportedAudioFileException ex) {
                                        throw new RuntimeException(ex);
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    } catch (LineUnavailableException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                    gameroot.getChildren().remove(player);

                                    gameEndTrans();
                                });

                            }
                        }
                    };
                rotationTimer.start();
                }
            });




        }

    }

//will move the whole scene
    public void moveScene( ){


            currentberries++;
            gameroot.getChildren().remove(currentscore);
            showScore();
            TranslateTransition movepillar1back = new TranslateTransition(Duration.seconds(0.5),pillar1);
            TranslateTransition movepillar2back = new TranslateTransition(Duration.seconds(0.5),pillar2);
            TranslateTransition moveplayerback = new TranslateTransition(Duration.seconds(0.5),player);
            TranslateTransition movestickback = new TranslateTransition(Duration.seconds(0.5),stick);
            isAddberries = true;
            movepillar1back.setToX(-randgap - pillar2width );
            moveplayerback.setToX(-randgap - pillar2width );
            movestickback.setToX(-randgap-pillar2width);
            movepillar2back.setToX(-randgap-pillar2width);

            ParallelTransition parallelTransition = new ParallelTransition(movepillar1back,movepillar2back,moveplayerback,movestickback);
            parallelTransition.play();
            parallelTransition.setOnFinished(e->{
                player.setTranslateX(0);
                player.setX(startPos  - playerwidth );
                pillar2.setTranslateX(0);
                pillar2.setX(startPos - pillar2width );
                gameroot.getChildren().remove(pillar1);
                pillar1 = pillar2;
                pillar2 = spawnPillar();
                TranslateTransition pillar2entry = new TranslateTransition(Duration.seconds(0.5),pillar2);
                pillar2entry.setToY(460);
                pillar2entry.play();
                pillar2entry.setOnFinished(e2->{
                    berry = spawnBerry();
                    isBerry = true;
                    TranslateTransition berytransition = new TranslateTransition(Duration.seconds(0.5),berry);
                    berytransition.setToY(berryy);
                    berytransition.play();
                    berytransition.setOnFinished(e3->{
                        pillar2.setTranslateY(0);
                        pillar2.setY(460);
                        spawnstick(460,player);
                        stopstick = false;
                        isStretching = true;
                    });

                });
            });



    }

//    will spawn new pillar
    public ImageView spawnPillar(){
        Random rand = new Random();
        int randpillar = rand.nextInt(pillarsize.size());
        randgap = randomGap();
        System.out.println("here");
        Image image = new Image(images_paths.get(randpillar));
        ImageView pillar2 = new ImageView(image);
        pillar2width = pillarsize.get(randpillar);

        pillar2.setX(startPos + randgap);
        pillar2.setY(0);

        gameroot.getChildren().add(pillar2);
        return pillar2;
    }

//    will spawn new berry
    public ImageView spawnBerry(){

        Random random = new Random();
        String berypath = "file:images/gamebery.png";
        ImageView bery =  null;
        try {
            Image image = new Image(berypath);
            bery = new ImageView(image);
            }
        catch (Exception e){
            System.out.println("Error in fetching berry image");
        }

        int[] pos_in_y = {levelY + 30 , levelY };
        int randomIndex = random.nextInt(pos_in_y.length);
        int randy = pos_in_y[randomIndex];
        int randx = random.nextInt(randgap - 20) ;



        berryx = randx + startPos;
        berryy = randy;
        assert bery != null;
        bery.setX( berryx );
        bery.setY(0);
        gameroot.getChildren().add(bery);
        System.out.println("berries x is " + berryx);
        return bery;
    }


}
