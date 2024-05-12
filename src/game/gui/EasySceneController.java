package game.gui;

import java.awt.Color;
import javafx.scene.control.Label;
import java.io.IOException;

import game.engine.Battle;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import javafx.scene.paint.*;


public class EasySceneController {
    

    public static Group root = new Group();
    public static AnimationTimer animationTimer;
    public static ImageView enemy = new ImageView(); 
    public static ImageView wallTrap = new ImageView();
    public static int frame = 0;
    public static long time = 0;
    public static TranslateTransition translate;
    public static boolean playerTurnDone = false;
    public static boolean playerSkipTurn = false;
    public static boolean playerPurchaseWeapons = false;

    public void startEasy() throws IOException{

        translate = new TranslateTransition();

        animationTimer = new AnimationTimer() {
        
            @Override
            public void handle(long now) {

                if(now - time > 80_000_000){
                    enemy.setImage(new Image(getClass().getResource("assets/AnimationFrames/Abnormal/Walk/" + frame + ".png").toString()));
                    frame++;
                    if(frame == 8) frame = 0;
                    time = now;
                }
            }
            
        };
        



        enemy.setFitHeight(960/8);
        enemy.setFitHeight(540/5);
        enemy.setRotationAxis(Rotate.Y_AXIS);
        enemy.setRotate(180);
        enemy.setLayoutX(960 - 960/16);
        enemy.setLayoutY(-20);

        translate.setNode(enemy);
        translate.setDuration(new Duration(20000));
        translate.setByX(-900);
        translate.play();


        wallTrap.setFitHeight(100);
        wallTrap.setFitHeight(100);
        wallTrap.setLayoutX(200);
        wallTrap.setLayoutY(100);
        wallTrap.setRotate(270);
        wallTrap.setImage(new Image(getClass().getResource("assets/AnimationFrames/WallTrap/0.png").toString()));


        //960 x 540
        //16:9
        
        for(int i = 0; i < 10; i++){
            for (int j = 0; j < 16; j++) {
                
                ImageView image = new ImageView();
                image.setImage(new Image(getClass().getResource("assets/Images/Tiles/1.png").toString()));
                image.setFitWidth(960/16);
                image.setFitHeight(540/10);
                image.setLayoutX(j * 960/16); 
                image.setLayoutY(i * 540/10); 
                root.getChildren().add(image);     

            }
        }

        for(int i = 0; i < 16; i++){
            ImageView topImage = new ImageView(getClass().getResource("assets/Images/Tiles/3.png").toString());
            topImage.setFitWidth(960/16);
            topImage.setFitHeight(540/10); 
            topImage.setLayoutY(0);
            topImage.setLayoutX(i * 960/16);
            root.getChildren().add(topImage);
            
            ImageView bottomImage = new ImageView(getClass().getResource("assets/Images/Tiles/3.png").toString());
            bottomImage.setFitWidth(960/16);
            bottomImage.setFitHeight(540/10); 
            bottomImage.setLayoutY(540 - 540/10);
            bottomImage.setLayoutX(i * 960/16);
            bottomImage.setRotate(180);
            root.getChildren().add(bottomImage);

        }

        for(int i = 0; i < 16; i++){

            ImageView sandTop = new ImageView(getClass().getResource("assets/Images/Tiles/4.png").toString());
            ImageView sandBottom = new ImageView(getClass().getResource("assets/Images/Tiles/4.png").toString());

            sandTop.setFitWidth(960/16);
            sandTop.setFitHeight(540/10); 
            sandTop.setLayoutY(3 * 540/10);
            sandTop.setLayoutX(i * 960/16);
            root.getChildren().add(sandTop);
            

            sandBottom.setFitWidth(960/16);
            sandBottom.setFitHeight(540/10); 
            sandBottom.setLayoutY(6 * 540/10);
            sandBottom.setLayoutX(i * 960/16);
            root.getChildren().add(sandBottom);

        }

        for (int i = 0; i < 9; i++) {
            if(i!=0 && i!=3 && i!=6 && i!=9){

                ImageView tower = new ImageView(getClass().getResource("assets/Images/Tower/0.png").toString());
                tower.setFitWidth(960/16);
                tower.setFitHeight(540/10); 
                tower.setLayoutY(i * 540/10);
                tower.setLayoutX(0);
                root.getChildren().add(tower);

            }
        }

        for (int i = 0; i < 9; i++) {
            if(i!=0 && i!=3 && i!=6 && i!=9){

                ImageView wall = new ImageView(getClass().getResource("assets/Images/Wall/0.png").toString());
                
                
                wall.setFitWidth(960/10);
                wall.setFitHeight(540/8); 
                wall.setRotate(-90);
                wall.setLayoutY(i * 540/10 - 10);
                wall.setLayoutX(4 * 960/16 - 30);
                root.getChildren().add(wall);

            }
        }

        Button passTurn = new Button("Pass Turn");
        passTurn.setId("quit");
        passTurn.setLayoutX(650);
        passTurn.setLayoutY(350);
        passTurn.setPrefHeight(300);
        passTurn.setPrefWidth(275);
        root.getChildren().add(passTurn);
        passTurn.setVisible(true);

        Button purchaseWeapon = new Button("Purchase Weapon");
        purchaseWeapon.setId("quit");
        purchaseWeapon.setLayoutX(300);
        purchaseWeapon.setLayoutY(350);
        purchaseWeapon.setPrefHeight(300);
        purchaseWeapon.setPrefWidth(400);
        root.getChildren().add(purchaseWeapon);
        purchaseWeapon.setVisible(true);

        Label score = new Label("Score:");
        score.setId("score");
        score.setLayoutX(20);
        score.setLayoutY(-5);
        score.setPrefHeight(30);
        score.setPrefWidth(800);
        root.getChildren().add(score);
        score.setVisible(true);

        Label resources = new Label("Resources");
        resources.setId("resources");
        resources.setLayoutX(250);
        resources.setLayoutY(-5);
        resources.setPrefHeight(30);
        resources.setPrefWidth(800);
        root.getChildren().add(resources);
        resources.setVisible(true);

        Label round = new Label();
        round.setId("round");
        round.setLayoutX(600);
        round.setLayoutY(-5);
        round.setPrefHeight(30);
        round.setPrefWidth(800);
        root.getChildren().add(round);
        round.setVisible(true);

        Label phase = new Label();
        phase.setId("phase");
        phase.setLayoutX(10);
        phase.setLayoutY(480);
        phase.setPrefHeight(30);
        phase.setPrefWidth(300);
        root.getChildren().add(phase);
        phase.setVisible(true);


       

        
        






        


        root.getChildren().add(enemy);
        //root.getChildren().add(wallTrap);
        animationTimer.start();
        Main.stage.getScene().setRoot(root);

        //Game Loop


        Battle battle = new Battle(0, 0, 500, 3, 250);
        
        passTurn.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                battle.passTurn();
                round.setText("Round: " + battle.getNumberOfTurns());
                phase.setText("Phase: " + battle.getBattlePhase());
            }
            
        });
                    

        Platform.runLater(new Runnable() {
            
            @Override
            public void run() {
                if(!battle.isGameOver()){
                //    System.out.println(battle.getNumberOfTurns());
                   
                   
                    score.setText("Score: " + battle.getScore());
                    resources.setText("Resources: " + battle.getResourcesGathered());
                    round.setText("Round: " + battle.getNumberOfTurns());
                    phase.setText("Phase: " + battle.getBattlePhase());

                    if(playerPurchaseWeapons){
                        battle.passTurn();
                        playerPurchaseWeapons = false;
                    }
                    if(playerSkipTurn){
                        battle.passTurn();
                        playerSkipTurn = false;
                    }


                }
            }
            
        });

       



    }


}
