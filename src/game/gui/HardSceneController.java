package game.gui;


import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import game.engine.Battle;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.Titan;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.paint.*;


public class HardSceneController {
    

    public static Group root = new Group();
    public static AnimationTimer animationTimer;
    public static ImageView wallTrap = new ImageView();
    public static int frame = 0;
    public static long time = 0;
    public static TranslateTransition translate;
    public static boolean playerTurnDone = false;
    public static boolean playerSkipTurn = false;
    public static boolean playerPurchaseWeapons = false;

    public static Battle battle;
    public static Label round;
    public static Label phase;
    public static Label score;
    public static Label resources;
    public static boolean purchaseOpened = false;
    public static int weaponToBuyCode = 0;
    public static Button[] laneRects = new Button[5];
    public static Lane[] lanes = new Lane[5];
    public static HashMap<Titan, TitanIcon> titanImages = new HashMap<>();
    public static Rectangle dim;
    public static ImageView[] laneLost0 = new ImageView[6];
    public static ImageView[] laneLost1 = new ImageView[6];
    public static ImageView[] laneLost2 = new ImageView[6];
    public static ImageView[] laneLost3 = new ImageView[6];
    public static ImageView[] laneLost4 = new ImageView[6];

    public void startHard() throws IOException{

        battle = new Battle(0, 0, 100, 5, 125);
        
        wallTrap.setFitHeight(100);
        wallTrap.setFitHeight(100);
        wallTrap.setLayoutX(200);
        wallTrap.setLayoutY(100);
        wallTrap.setRotate(270);
        wallTrap.setImage(new Image(getClass().getResource("assets/AnimationFrames/WallTrap/0.png").toString()));


        int counter = 0;
        for (Lane lane : battle.getLanes()) {
            lanes[counter] = lane;
            counter++;
        }

        Rectangle greenBackground = new Rectangle(960,540);
        greenBackground.setFill(Color.rgb(00, 255, 0));
        root.getChildren().add(greenBackground);

        //960 x 540
        //16:9
        
        for(int i = 0; i < 16; i++){
            for (int j = 0; j < 24; j++) {
                
                ImageView image = new ImageView();
                image.setImage(new Image(getClass().getResource("assets/Images/Tiles/1.png").toString()));
                image.setFitWidth(960/24);
                image.setFitHeight(540/16);
                image.setLayoutX(j * 960/24); 
                image.setLayoutY(i * 540/16); 
                root.getChildren().add(image);     

            }
        }

        for(int i = 0; i < 24; i++){
            ImageView topImage = new ImageView(getClass().getResource("assets/Images/Tiles/3.png").toString());
            topImage.setFitWidth(960/24);
            topImage.setFitHeight(540/16); 
            topImage.setLayoutY(0);
            topImage.setLayoutX(i * 960/24);
            root.getChildren().add(topImage);
            
            ImageView bottomImage = new ImageView(getClass().getResource("assets/Images/Tiles/3.png").toString());
            bottomImage.setFitWidth(960/24);
            bottomImage.setFitHeight(540/16); 
            bottomImage.setLayoutY(540 - 540/16);
            bottomImage.setLayoutX(i * 960/24);
            bottomImage.setRotate(180);
            root.getChildren().add(bottomImage);

        }

        for(int i = 0; i < 24; i++){

            
            ImageView sand0 = new ImageView(getClass().getResource("assets/Images/Tiles/4.png").toString());
            ImageView sand1 = new ImageView(getClass().getResource("assets/Images/Tiles/4.png").toString());
            ImageView sand2 = new ImageView(getClass().getResource("assets/Images/Tiles/4.png").toString());
            ImageView sand3 = new ImageView(getClass().getResource("assets/Images/Tiles/4.png").toString());
            

            sand0.setFitWidth(960/24);
            sand0.setFitHeight(540/16); 
            sand0.setLayoutY(3 * 540/16);
            sand0.setLayoutX(i * 960/24);
            root.getChildren().add(sand0);
            

            sand1.setFitWidth(960/24);
            sand1.setFitHeight(540/16); 
            sand1.setLayoutY(6 * 540/16);
            sand1.setLayoutX(i * 960/24);
            root.getChildren().add(sand1);

            sand2.setFitWidth(960/24);
            sand2.setFitHeight(540/16); 
            sand2.setLayoutY(9 * 540/16);
            sand2.setLayoutX(i * 960/24);
            root.getChildren().add(sand2);

            sand3.setFitWidth(960/24);
            sand3.setFitHeight(540/16); 
            sand3.setLayoutY(12 * 540/16);
            sand3.setLayoutX(i * 960/24);
            root.getChildren().add(sand3);

        }

        for (int i = 0; i < 15; i++) {
            if(i!=0 && i!=3 && i!=6 && i!=9 && i!=12 && i!=15){

                ImageView tower = new ImageView(getClass().getResource("assets/Images/Tower/0.png").toString());
                tower.setFitWidth(960/24);
                tower.setFitHeight(540/16); 
                tower.setLayoutY(i * 540/16);
                tower.setLayoutX(0);
                root.getChildren().add(tower);

            }
        }

        for (int i = 0; i < 15; i++) {
            if(i!=0 && i!=3 && i!=6 && i!=9 && i!= 12 && i != 15){

                ImageView wall = new ImageView(getClass().getResource("assets/Images/Wall/0.png").toString());
                
                
                wall.setFitWidth(960/15);
                wall.setFitHeight(2*540/16); 
                wall.setRotate(-90);
                wall.setLayoutY(i * 540/16 -12);
                wall.setLayoutX(4 * 960/24 - 30);
                root.getChildren().add(wall);

            }
        }

        Button passTurn = new Button("Pass Turn");
        passTurn.setId("passTurn");
        passTurn.setLayoutX(650);
        passTurn.setLayoutY(360);
        passTurn.setPrefHeight(300);
        passTurn.setPrefWidth(275);
        root.getChildren().add(passTurn);
        passTurn.setVisible(true);

        Button purchaseWeapon = new Button("Purchase Weapon");
        purchaseWeapon.setId("purchaseWeapon");
        purchaseWeapon.setLayoutX(300);
        purchaseWeapon.setLayoutY(360);
        purchaseWeapon.setPrefHeight(300);
        purchaseWeapon.setPrefWidth(400);
        root.getChildren().add(purchaseWeapon);
        purchaseWeapon.setVisible(true);

        score = new Label("Score: " + battle.getScore());
        score.setId("score");
        score.setLayoutX(20);
        score.setLayoutY(-5);
        score.setPrefHeight(30);
        score.setPrefWidth(800);
        root.getChildren().add(score);
        score.setVisible(true);

        resources = new Label("Resources: " + battle.getResourcesGathered());
        resources.setId("resources");
        resources.setLayoutX(250);
        resources.setLayoutY(-5);
        resources.setPrefHeight(30);
        resources.setPrefWidth(800);
        root.getChildren().add(resources);
        resources.setVisible(true);

        round = new Label("Round: " + battle.getNumberOfTurns());
        round.setId("round");
        round.setLayoutX(600);
        round.setLayoutY(-5);
        round.setPrefHeight(30);
        round.setPrefWidth(800);
        root.getChildren().add(round);
        round.setVisible(true);

        phase = new Label("Phase: " + battle.getBattlePhase());
        phase.setId("phase");
        phase.setLayoutX(10);
        phase.setLayoutY(480);
        phase.setPrefHeight(30);
        phase.setPrefWidth(300);
        root.getChildren().add(phase);
        phase.setVisible(true);

        dim = new Rectangle(1000, 550);
        dim.setFill(Color.BLACK);
        dim.setOpacity(0.6);
        root.getChildren().add(dim);
        dim.setVisible(false);
        dim.toFront();

        Rectangle purchase = new Rectangle();
        purchase.setWidth(500);
        purchase.setHeight(300);
        purchase.setLayoutX(960/2 - 250);
        purchase.setLayoutY(540/2-150);
        purchase.setVisible(false);
        purchase.setFill(Color.DARKGRAY);
        purchase.toFront();
        root.getChildren().add(purchase);

        Button closePurchase = new Button("X");
        closePurchase.setPrefWidth(20);
        closePurchase.setPrefHeight(20);
        closePurchase.setLayoutX(707);
        closePurchase.setLayoutY(120);
        closePurchase.setVisible(false);
        closePurchase.toFront();
        root.getChildren().add(closePurchase);

        Button sniperCannon = new Button("Buy Sniper Cannon: 25 Resources");
        sniperCannon.setId("menuButton");
        sniperCannon.setPrefWidth(450);
        sniperCannon.setPrefHeight(60);
        sniperCannon.setLayoutX(960/2 - 250 + 25);
        sniperCannon.setLayoutY(540/2-150 + 30);
        sniperCannon.setVisible(false);
        sniperCannon.toFront();
        root.getChildren().add(sniperCannon);

        Button piercingCannon = new Button("Buy Piecring Cannon: 25 Resources");
        piercingCannon.setId("menuButton");
        piercingCannon.setPrefWidth(450);
        piercingCannon.setPrefHeight(60);
        piercingCannon.setLayoutX(960/2 - 250 + 25);
        piercingCannon.setLayoutY(540/2-150 + 90);
        piercingCannon.setVisible(false);
        piercingCannon.toFront();
        root.getChildren().add(piercingCannon);

        Button volleySpread = new Button("Buy Volley Spread Cannon: 100 Resources");
        volleySpread.setId("menuButton");
        volleySpread.setPrefWidth(450);
        volleySpread.setPrefHeight(60);
        volleySpread.setLayoutX(960/2 - 250 + 25);
        volleySpread.setLayoutY(540/2-150 + 150);
        volleySpread.setVisible(false);
        volleySpread.toFront();
        root.getChildren().add(volleySpread);

        Button wallTrap = new Button("Buy Wall Trap: 75 Resources");
        wallTrap.setId("menuButton");
        wallTrap.setPrefWidth(450);
        wallTrap.setPrefHeight(60);
        wallTrap.setLayoutX(960/2 - 250 + 25);
        wallTrap.setLayoutY(540/2-150 + 210);
        wallTrap.setVisible(false);
        wallTrap.toFront();
        root.getChildren().add(wallTrap);
       
        laneRects[0] = new Button();
        laneRects[0].setId("laneButton");
        laneRects[0].setPrefWidth(3*960/24);
        laneRects[0].setPrefHeight(2*540/16);
        laneRects[0].setLayoutX(960/24);
        laneRects[0].setLayoutY(540/16);
        laneRects[0].setVisible(false);
        laneRects[0].setOpacity(0.5);
        root.getChildren().add(laneRects[0]);

        laneRects[1] = new Button();
        laneRects[1].setId("laneButton");
        laneRects[1].setPrefWidth(3*960/24);
        laneRects[1].setPrefHeight(2*540/16);
        laneRects[1].setLayoutX(960/24);
        laneRects[1].setLayoutY(4*540/16);
        laneRects[1].setVisible(false);
        laneRects[1].setOpacity(0.5);
        root.getChildren().add(laneRects[1]);

        laneRects[2] = new Button();
        laneRects[2].setId("laneButton");
        laneRects[2].setPrefWidth(3*960/24);
        laneRects[2].setPrefHeight(2*540/16);
        laneRects[2].setLayoutX(960/24);
        laneRects[2].setLayoutY(7*540/16);
        laneRects[2].setVisible(false);
        laneRects[2].setOpacity(0.5);
        root.getChildren().add(laneRects[2]);

        laneRects[3] = new Button();
        laneRects[3].setId("laneButton");
        laneRects[3].setPrefWidth(3*960/24);
        laneRects[3].setPrefHeight(2*540/16);
        laneRects[3].setLayoutX(960/24);
        laneRects[3].setLayoutY(10*540/16);
        laneRects[3].setVisible(false);
        laneRects[3].setOpacity(0.5);
        root.getChildren().add(laneRects[3]);

        laneRects[4] = new Button();
        laneRects[4].setId("laneButton");
        laneRects[4].setPrefWidth(3*960/24);
        laneRects[4].setPrefHeight(2*540/16);
        laneRects[4].setLayoutX(960/24);
        laneRects[4].setLayoutY(13*540/16);
        laneRects[4].setVisible(false);
        laneRects[4].setOpacity(0.5);
        root.getChildren().add(laneRects[4]);

        //root.getChildren().add(wallTrap);
        
        Main.stage.getScene().setRoot(root);

        //Game Loop


       
        
        passTurn.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                battle.passTurn();
                updateTitanImages();
                playRound();
                updateGUI();
            }

            
            
        });

        purchaseWeapon.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                // try {
                //     battle.purchaseWeapon(1, battle.getLanes().peek());
                // } catch (InsufficientResourcesException e) {
                //     System.out.println("NO RESOURCES");
                // } catch (InvalidLaneException e){
                //     System.out.println("NO LANE!!");
                // }
                // playRound();
                purchase.setVisible(true);
                closePurchase.setVisible(true);
                sniperCannon.setVisible(true);
                volleySpread.setVisible(true);
                wallTrap.setVisible(true);
                piercingCannon.setVisible(true);
                dim.setVisible(true);

            }
            
            

            
            
        });

        closePurchase.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                purchase.setVisible(false);
                closePurchase.setVisible(false);
                sniperCannon.setVisible(false);
                volleySpread.setVisible(false);
                wallTrap.setVisible(false);
                piercingCannon.setVisible(false);
                dim.setVisible(false);
            } 

            
        });

        


        piercingCannon.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                weaponToBuyCode = 1;
                for (Button button : laneRects)  button.setVisible(true);
                purchase.setVisible(false);
                closePurchase.setVisible(false);
                sniperCannon.setVisible(false);
                volleySpread.setVisible(false);
                wallTrap.setVisible(false);
                piercingCannon.setVisible(false);
                dim.setVisible(false);
            }
            
        });

        sniperCannon.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                weaponToBuyCode = 2;
                for (Button button : laneRects)  button.setVisible(true);
                purchase.setVisible(false);
                closePurchase.setVisible(false);
                sniperCannon.setVisible(false);
                volleySpread.setVisible(false);
                wallTrap.setVisible(false);
                piercingCannon.setVisible(false);
                dim.setVisible(false);
            }
            
        });

        volleySpread.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                weaponToBuyCode = 3;
                for (Button button : laneRects)  button.setVisible(true);
                purchase.setVisible(false);
                closePurchase.setVisible(false);
                sniperCannon.setVisible(false);
                volleySpread.setVisible(false);
                wallTrap.setVisible(false);
                piercingCannon.setVisible(false);
                dim.setVisible(false);
            }
            
        });

        wallTrap.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                weaponToBuyCode = 4;
                for (Button button : laneRects)  button.setVisible(true);
                purchase.setVisible(false);
                closePurchase.setVisible(false);
                sniperCannon.setVisible(false);
                volleySpread.setVisible(false);
                wallTrap.setVisible(false);
                piercingCannon.setVisible(false);
                dim.setVisible(false);
            }
            
        });

        Main.stage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ESCAPE){
                    weaponToBuyCode = 0;
                    for (Button button : laneRects)  button.setVisible(false);
                    
                    purchase.setVisible(false);
                closePurchase.setVisible(false);
                sniperCannon.setVisible(false);
                volleySpread.setVisible(false);
                wallTrap.setVisible(false);
                piercingCannon.setVisible(false);

                }
            }
            
        });

        laneRects[0].setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
               try {
                battle.purchaseWeapon(weaponToBuyCode, lanes[0]);
                for (Button button : laneRects) button.setVisible(false);
                updateGUI();
            } catch (InsufficientResourcesException e) {
                dim.setVisible(true);
                Rectangle resourcesWarning = new Rectangle();
                resourcesWarning.setWidth(500);
                resourcesWarning.setHeight(300);
                resourcesWarning.setLayoutX(960/2 - 250);
                resourcesWarning.setLayoutY(540/2-150);
                resourcesWarning.setVisible(true);
                resourcesWarning.setFill(Color.DARKGRAY);
                resourcesWarning.toFront();
                root.getChildren().add(resourcesWarning);

                Label resourcesWarningText = new Label("Insufficient Resources");
                resourcesWarningText.setId("gameOver");
                resourcesWarningText.setPrefWidth(450);
                resourcesWarningText.setPrefHeight(60);
                resourcesWarningText.setLayoutX(960/2 - 200);
                resourcesWarningText.setLayoutY(540/2-125);
                resourcesWarningText.setVisible(true);
                resourcesWarningText.toFront();
                root.getChildren().add(resourcesWarningText);

                Button back = new Button("Back");
                back.setId("menuButton");
                back.setPrefWidth(450);
                back.setPrefHeight(60);
                back.setLayoutX(960/2 - 250 + 25);
                back.setLayoutY(540/2-150 + 150);
                back.setVisible(true);
                back.toFront();
                root.getChildren().add(back);

                back.setOnMouseClicked(new EventHandler<Event>() {

                    @Override
                    public void handle(Event event) {
                        dim.setVisible(false);
                        resourcesWarning.setVisible(false);
                        back.setVisible(false);
                        resourcesWarningText.setVisible(false);
                    }
                    
                });

            } catch (InvalidLaneException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            }
            
        });
        
        laneRects[1].setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
               try {
                battle.purchaseWeapon(weaponToBuyCode, lanes[1]);
                for (Button button : laneRects) button.setVisible(false);
                updateGUI();
            } catch (InsufficientResourcesException e) {
                dim.setVisible(true);
                Rectangle resourcesWarning = new Rectangle();
                resourcesWarning.setWidth(500);
                resourcesWarning.setHeight(300);
                resourcesWarning.setLayoutX(960/2 - 250);
                resourcesWarning.setLayoutY(540/2-150);
                resourcesWarning.setVisible(true);
                resourcesWarning.setFill(Color.DARKGRAY);
                resourcesWarning.toFront();
                root.getChildren().add(resourcesWarning);

                Label resourcesWarningText = new Label("Insufficient Resources");
                resourcesWarningText.setId("gameOver");
                resourcesWarningText.setPrefWidth(450);
                resourcesWarningText.setPrefHeight(60);
                resourcesWarningText.setLayoutX(960/2 - 200);
                resourcesWarningText.setLayoutY(540/2-125);
                resourcesWarningText.setVisible(true);
                resourcesWarningText.toFront();
                root.getChildren().add(resourcesWarningText);

                Button back = new Button("Back");
                back.setId("menuButton");
                back.setPrefWidth(450);
                back.setPrefHeight(60);
                back.setLayoutX(960/2 - 250 + 25);
                back.setLayoutY(540/2-150 + 150);
                back.setVisible(true);
                back.toFront();
                root.getChildren().add(back);

                back.setOnMouseClicked(new EventHandler<Event>() {

                    @Override
                    public void handle(Event event) {
                        dim.setVisible(false);
                        resourcesWarning.setVisible(false);
                        back.setVisible(false);
                        resourcesWarningText.setVisible(false);
                    }
                    
                });

            } catch (InvalidLaneException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            }
            
        });

        laneRects[2].setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
               try {
                battle.purchaseWeapon(weaponToBuyCode, lanes[2]);
                for (Button button : laneRects) button.setVisible(false);
                updateGUI();
            } catch (InsufficientResourcesException e) {
                
                dim.setVisible(true);
                Rectangle resourcesWarning = new Rectangle();
                resourcesWarning.setWidth(500);
                resourcesWarning.setHeight(300);
                resourcesWarning.setLayoutX(960/2 - 250);
                resourcesWarning.setLayoutY(540/2-150);
                resourcesWarning.setVisible(true);
                resourcesWarning.setFill(Color.DARKGRAY);
                resourcesWarning.toFront();
                root.getChildren().add(resourcesWarning);

                Label resourcesWarningText = new Label("Insufficient Resources");
                resourcesWarningText.setId("gameOver");
                resourcesWarningText.setPrefWidth(450);
                resourcesWarningText.setPrefHeight(60);
                resourcesWarningText.setLayoutX(960/2 - 200);
                resourcesWarningText.setLayoutY(540/2-125);
                resourcesWarningText.setVisible(true);
                resourcesWarningText.toFront();
                root.getChildren().add(resourcesWarningText);

                Button back = new Button("Back");
                back.setId("menuButton");
                back.setPrefWidth(450);
                back.setPrefHeight(60);
                back.setLayoutX(960/2 - 250 + 25);
                back.setLayoutY(540/2-150 + 150);
                back.setVisible(true);
                back.toFront();
                root.getChildren().add(back);

                back.setOnMouseClicked(new EventHandler<Event>() {

                    @Override
                    public void handle(Event event) {
                        dim.setVisible(false);
                        resourcesWarning.setVisible(false);
                        back.setVisible(false);
                        resourcesWarningText.setVisible(false);
                    }
                    
                });

                
            } catch (InvalidLaneException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            }
            
        });

        laneRects[3].setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
               try {
                battle.purchaseWeapon(weaponToBuyCode, lanes[3]);
                for (Button button : laneRects) button.setVisible(false);
                updateGUI();
            } catch (InsufficientResourcesException e) {
                
                dim.setVisible(true);
                Rectangle resourcesWarning = new Rectangle();
                resourcesWarning.setWidth(500);
                resourcesWarning.setHeight(300);
                resourcesWarning.setLayoutX(960/2 - 250);
                resourcesWarning.setLayoutY(540/2-150);
                resourcesWarning.setVisible(true);
                resourcesWarning.setFill(Color.DARKGRAY);
                resourcesWarning.toFront();
                root.getChildren().add(resourcesWarning);

                Label resourcesWarningText = new Label("Insufficient Resources");
                resourcesWarningText.setId("gameOver");
                resourcesWarningText.setPrefWidth(450);
                resourcesWarningText.setPrefHeight(60);
                resourcesWarningText.setLayoutX(960/2 - 200);
                resourcesWarningText.setLayoutY(540/2-125);
                resourcesWarningText.setVisible(true);
                resourcesWarningText.toFront();
                root.getChildren().add(resourcesWarningText);

                Button back = new Button("Back");
                back.setId("menuButton");
                back.setPrefWidth(450);
                back.setPrefHeight(60);
                back.setLayoutX(960/2 - 250 + 25);
                back.setLayoutY(540/2-150 + 150);
                back.setVisible(true);
                back.toFront();
                root.getChildren().add(back);

                back.setOnMouseClicked(new EventHandler<Event>() {

                    @Override
                    public void handle(Event event) {
                        dim.setVisible(false);
                        resourcesWarning.setVisible(false);
                        back.setVisible(false);
                        resourcesWarningText.setVisible(false);
                    }
                    
                });

                
            } catch (InvalidLaneException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            }
            
        });

        laneRects[4].setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
               try {
                battle.purchaseWeapon(weaponToBuyCode, lanes[4]);
                for (Button button : laneRects) button.setVisible(false);
                updateGUI();
            } catch (InsufficientResourcesException e) {
                
                dim.setVisible(true);
                Rectangle resourcesWarning = new Rectangle();
                resourcesWarning.setWidth(500);
                resourcesWarning.setHeight(300);
                resourcesWarning.setLayoutX(960/2 - 250);
                resourcesWarning.setLayoutY(540/2-150);
                resourcesWarning.setVisible(true);
                resourcesWarning.setFill(Color.DARKGRAY);
                resourcesWarning.toFront();
                root.getChildren().add(resourcesWarning);

                Label resourcesWarningText = new Label("Insufficient Resources");
                resourcesWarningText.setId("gameOver");
                resourcesWarningText.setPrefWidth(450);
                resourcesWarningText.setPrefHeight(60);
                resourcesWarningText.setLayoutX(960/2 - 200);
                resourcesWarningText.setLayoutY(540/2-125);
                resourcesWarningText.setVisible(true);
                resourcesWarningText.toFront();
                root.getChildren().add(resourcesWarningText);

                Button back = new Button("Back");
                back.setId("menuButton");
                back.setPrefWidth(450);
                back.setPrefHeight(60);
                back.setLayoutX(960/2 - 250 + 25);
                back.setLayoutY(540/2-150 + 150);
                back.setVisible(true);
                back.toFront();
                root.getChildren().add(back);
                back.setOnMouseClicked(new EventHandler<Event>() {

                    @Override
                    public void handle(Event event) {
                        dim.setVisible(false);
                        resourcesWarning.setVisible(false);
                        back.setVisible(false);
                        resourcesWarningText.setVisible(false);
                    }
                    
                });

                
            } catch (InvalidLaneException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            }
            
        });

        
        for (int i = 0; i < laneLost0.length; i++) {
            ImageView laneLost = new ImageView(getClass().getResource("assets/Images/laneLost.png").toString());
            laneLost.setFitWidth(960/6);
            laneLost.setFitHeight(540/8);
            laneLost.setLayoutY(540/16);
            laneLost.setLayoutX(i * 960/6);
            laneLost0[i] = laneLost;
            laneLost.setVisible(false);
            root.getChildren().add(laneLost0[i]);
        }
       
        for (int i = 0; i < laneLost1.length; i++) {
            ImageView laneLost = new ImageView(getClass().getResource("assets/Images/laneLost.png").toString());
            laneLost.setFitWidth(960/6);
            laneLost.setFitHeight(540/8);
            laneLost.setLayoutY(4*540/16);
            laneLost.setLayoutX(i * 960/6);
            laneLost1[i] = laneLost;
            laneLost.setVisible(false);
            root.getChildren().add(laneLost1[i]);
        }

        for (int i = 0; i < laneLost2.length; i++) {
            ImageView laneLost = new ImageView(getClass().getResource("assets/Images/laneLost.png").toString());
            laneLost.setFitWidth(960/6);
            laneLost.setFitHeight(540/8);
            
            laneLost.setLayoutY(7*540/16);
            laneLost.setLayoutX(i * 960/6);
            laneLost2[i] = laneLost;
            laneLost.setVisible(false);
            root.getChildren().add(laneLost2[i]);
        }

        for (int i = 0; i < laneLost3.length; i++) {
            ImageView laneLost = new ImageView(getClass().getResource("assets/Images/laneLost.png").toString());
            laneLost.setFitWidth(960/6);
            laneLost.setFitHeight(540/8);
            
            laneLost.setLayoutY(10*540/16);
            laneLost.setLayoutX(i * 960/6);
            laneLost3[i] = laneLost;
            laneLost.setVisible(false);
            root.getChildren().add(laneLost3[i]);
        }

        for (int i = 0; i < laneLost4.length; i++) {
            ImageView laneLost = new ImageView(getClass().getResource("assets/Images/laneLost.png").toString());
            laneLost.setFitWidth(960/6);
            laneLost.setFitHeight(540/8);
            
            laneLost.setLayoutY(13*540/16);
            laneLost.setLayoutX(i * 960/6);
            laneLost4[i] = laneLost;
            laneLost.setVisible(false);
            root.getChildren().add(laneLost4[i]);
        }

        


    }

    private static void updateGUI(){
        round.setText("Round: " + battle.getNumberOfTurns());
        phase.setText("Phase: " + battle.getBattlePhase());
        score.setText("Score: " + battle.getScore());
        resources.setText("Resources: " + battle.getResourcesGathered());
        updateTitanImages();
        updateLaneLost();
    }

    private static void updateLaneLost(){

        if(lanes[0].isLaneLost()){
            
            for (ImageView imageView : laneLost0) {
                imageView.setVisible(true);
            }

            for (Titan t : lanes[0].getTitans()) {
                titanImages.get(t).getTitanImage().setVisible(false);
                titanImages.get(t).getTitanHealth().setVisible(false);
            }

        }
    
        if(lanes[1].isLaneLost()){
            
            for (ImageView imageView : laneLost1) {
                imageView.setVisible(true);
            }
            for (Titan t : lanes[1].getTitans()) {
                titanImages.get(t).getTitanImage().setVisible(false);
                titanImages.get(t).getTitanHealth().setVisible(false);
            }
        }

        if(lanes[2].isLaneLost()){
            
            for (ImageView imageView : laneLost2) {
                imageView.setVisible(true);
            }
            for (Titan t : lanes[2].getTitans()) {
                titanImages.get(t).getTitanImage().setVisible(false);
                titanImages.get(t).getTitanHealth().setVisible(false);
            }
        }
        if(lanes[3].isLaneLost()){
            
            for (ImageView imageView : laneLost3) {
                imageView.setVisible(true);
            }
            for (Titan t : lanes[3].getTitans()) {
                titanImages.get(t).getTitanImage().setVisible(false);
                titanImages.get(t).getTitanHealth().setVisible(false);
            }
        }
        if(lanes[4].isLaneLost()){
            
            for (ImageView imageView : laneLost4) {
                imageView.setVisible(true);
            }
            for (Titan t : lanes[4].getTitans()) {
                titanImages.get(t).getTitanImage().setVisible(false);
                titanImages.get(t).getTitanHealth().setVisible(false);
            }
        }
    }



    private static void playRound() {
                updateTitanImages();
                updateGUI();
                round.setText("Round: " + battle.getNumberOfTurns());
                phase.setText("Phase: " + battle.getBattlePhase());
                score.setText("Score: " + battle.getScore());
                resources.setText("Resources: " + battle.getResourcesGathered());
                
                if(battle.isGameOver()) endGame();
    }
    private static void endGame() {
        dim.setVisible(true);
        dim.toFront();

        Rectangle gameOver = new Rectangle();
        gameOver.setWidth(500);
        gameOver.setHeight(300);
        gameOver.setLayoutX(960/2 - 250);
        gameOver.setLayoutY(540/2-150);
        gameOver.setVisible(true);
        gameOver.setFill(Color.DARKGRAY);
        gameOver.toFront();
        root.getChildren().add(gameOver);

        Button backToMainMenu = new Button("Back To Main Menu");
        backToMainMenu.setId("menuButton");
        backToMainMenu.setPrefWidth(450);
        backToMainMenu.setPrefHeight(60);
        backToMainMenu.setLayoutX(960/2 - 250 + 25);
        backToMainMenu.setLayoutY(540/2-150 + 150);
        backToMainMenu.setVisible(true);
        backToMainMenu.toFront();
        root.getChildren().add(backToMainMenu);

        backToMainMenu.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
               Main.stage.getScene().setRoot(Main.mainMenuRoot);
            }
            
        });

        Label gameOverText = new Label("Game Over!");
        gameOverText.setId("gameOver");
        gameOverText.setPrefWidth(450);
        gameOverText.setPrefHeight(60);
        gameOverText.setLayoutX(960/2 - 100);
        gameOverText.setLayoutY(540/2-125);
        gameOverText.setVisible(true);
        gameOverText.toFront();
        root.getChildren().add(gameOverText);

    }

    private static void updateTitanImages(){

        for (Titan t : titanImages.keySet()) {
                if(t.isDefeated()){
                    ImageView image = titanImages.get(t).getTitanImage();
                    image.setY(1000);
                    image.setVisible(false);
                    titanImages.get(t).getTitanHealth().setLayoutY(1000);
                    titanImages.get(t).getTitanImage().setLayoutY(1000);
                }
        }


        for (int i = 0; i < lanes.length; i++) {
            Lane lane = lanes[i];
            for (Titan titan : lane.getTitans()) {
                


                if(!titanImages.containsKey(titan)){
                    ImageView image = new ImageView(new Image(new EasySceneController().getClass().getResource("assets/AnimationFrames/Abnormal/Idle/0.png").toString()));
                    image.setRotationAxis(Rotate.Y_AXIS);
                    image.setRotate(180);
                    image.setFitWidth(70);
                    image.setFitHeight(70);
                    image.setLayoutX(titan.getDistance()* 8 + 100);
                    // image.setLayoutY(100);
                    
                    switch (i) {
                        case 0:
                        if(titan.getHeightInMeters() == 60){
                            image.setLayoutY(540/16);
                            
                        }
                        else
                            image.setLayoutY(0);
                            break;
                        case 1:
                        if(titan.getHeightInMeters() == 60){
                            image.setLayoutY(4*540/16);
                            
                        }
                        else
                            image.setLayoutY(3*540/16);
                            break;
                        case 2:
                        if(titan.getHeightInMeters() == 60){
                            image.setLayoutY(7*540/16);
                            
                        }
                        else
                            image.setLayoutY(6*540/16);
                            break;
                        case 3:
                        if(titan.getHeightInMeters() == 60)
                            image.setLayoutY(10*540/16);
                        else
                            image.setLayoutY(9*540/16);
                            
                            break;
                        case 4:
                        if(titan.getHeightInMeters() == 60)
                            image.setLayoutY(13*540/16);
                        else
                            image.setLayoutY(12*540/16);
                        break;
                        
                        
                        default:
                            break;
                    }
                    root.getChildren().add(image);
                    ProgressBar titanHealth = new ProgressBar(1);
                    root.getChildren().add(titanHealth);
                    titanHealth.setLayoutX(image.getLayoutX());
                    titanHealth.setLayoutY(image.getLayoutY()-5);
                    TitanIcon titanIcon = new TitanIcon(titan, image, titanHealth);
                    titanImages.put(titan, titanIcon);
                }else{
                    
                    for (Titan t : titanImages.keySet()) {
                       
                        ImageView image = titanImages.get(t).getTitanImage();
                        image.setLayoutX(t.getDistance()*8 + 145);
                        titanImages.get(t).getTitanHealth().setProgress(t.getCurrentHealth()/(float)t.getBaseHealth());
                        titanImages.get(t).getTitanHealth().setLayoutX(image.getLayoutX());
                        
                    }
                }

            }

        }
    }


}
