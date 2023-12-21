    package com.example.stickgame;

    import javafx.scene.Scene;

    import javafx.stage.Stage;


    public class Scenecontroller {

        private static Scenecontroller instance;
        private final Stage stage;

        private Scenecontroller(Stage stage){this.stage = stage;}

        public void show(){stage.show();}

//        will switch scene
        public void switchScene(Scene scene){
            stage.setScene(scene);
        }

//        creating instance
        public static Scenecontroller getInstance(Stage primarystage){
            if(instance == null){
                instance = new Scenecontroller(primarystage);
            }
            return instance;
        }


    }






