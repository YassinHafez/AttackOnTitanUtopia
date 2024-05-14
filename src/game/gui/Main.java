package game.gui;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.stage.Stage;



public class Main extends Application{

    public static Group mainMenuRoot = new Group(); // main menu root - static so it can be accessed from other classes

    public static Group easyRoot = new Group();
    public static Group openingRoot = new Group();
    public static MediaPlayer musicMediaPlayer;
    public static MediaPlayer videoMediaPlayer;
    public static Stage stage;
    public static ArrayList<Button> allButtons = new ArrayList<>();
    public static MediaView video;
    public static boolean isMuted = false;
    public static String keyPresses = "";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Main.stage = stage;
        //openingRoot = FXMLLoader.load(getClass().getResource("opening.fxml"));
       // mainMenuRoot = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));

        //Make Main Menu
        //----------------------------------------------------------------------------------

      
        Media menuVideo = new Media(getClass().getResource("assets/videos/menuVideo.mp4").toString());
        videoMediaPlayer = new MediaPlayer(menuVideo);
        
        MediaView mainMenuVideo = new MediaView(videoMediaPlayer);
        mainMenuVideo.setFitWidth(960);
        mainMenuVideo.setFitHeight(540);
        videoMediaPlayer.setAutoPlay(true);
       
        mainMenuRoot.getChildren().add(mainMenuVideo);


        Label gameName = new Label("Attack On Titan");
        gameName.setId("mainTitle");
        gameName.setLayoutX(50);
        gameName.setLayoutY(373);
        gameName.setPrefWidth(1536);
        gameName.setPrefHeight(176);
        mainMenuRoot.getChildren().add(gameName);



        Button play = new Button("New Game");
        play.setId("newGame");
        play.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                
            }
            
        });
        play.setLayoutX(602);
        play.setLayoutY(105);
        play.setPrefHeight(124);
        play.setPrefWidth(338);
        mainMenuRoot.getChildren().add(play);
        allButtons.add(play);


        Button mute = new Button("Mute");
        mute.setId("muteMusic");
        allButtons.add(mute);
        mute.setLayoutX(602);
        mute.setLayoutY(232);
        mute.setPrefHeight(124);
        mute.setPrefWidth(338);
        mainMenuRoot.getChildren().add(mute);


        Button quit =new Button("Quit");
        quit.setId("quit");
        quit.setLayoutX(602);
        quit.setLayoutY(399);
        quit.setPrefHeight(124);
        quit.setPrefWidth(338);
        mainMenuRoot.getChildren().add(quit);
        allButtons.add(quit);


        Button easy = new Button("Easy");
        easy.setId("easy");
        easy.setLayoutX(602);
        easy.setLayoutY(105);
        easy.setPrefHeight(124);
        easy.setPrefWidth(338);
        mainMenuRoot.getChildren().add(easy);
        easy.setVisible(false);
        allButtons.add(easy);

        Button hard = new Button("Hard");
        hard.setId("hard");
        hard.setLayoutX(602);
        hard.setLayoutY(229);
        hard.setPrefHeight(124);
        hard.setPrefWidth(338);
        mainMenuRoot.getChildren().add(hard);
        hard.setVisible(false);
        allButtons.add(hard);

        Button backToMain = new Button("Back");
        backToMain.setId("quit");
        backToMain.setLayoutX(602);
        backToMain.setLayoutY(399);
        backToMain.setPrefHeight(124);
        backToMain.setPrefWidth(338);
        mainMenuRoot.getChildren().add(backToMain);
        backToMain.setVisible(false);
        allButtons.add(backToMain);
        

        Runnable musicRunnable = new Runnable() {
            @Override
            public void run() {

                Media mainMenuMusic = new Media(getClass().getResource("assets/music/SymphonicSuite.mp3").toString());
                musicMediaPlayer = new MediaPlayer(mainMenuMusic);
                musicMediaPlayer.play();
                musicMediaPlayer.setAutoPlay(true);
                
            }
        };
        Thread musicThread = new Thread(musicRunnable);


            Media openingVideo = new Media(getClass().getResource("assets/videos/Opening.mp4").toString());
            videoMediaPlayer = new MediaPlayer(openingVideo);
            video = new MediaView(videoMediaPlayer);
            video.setFitWidth(960);
            video.setFitHeight(540);
            
            
            openingRoot.getChildren().add(video);
            EventHandler eHandler = new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    videoMediaPlayer.stop();
                    stage.getScene().setRoot(mainMenuRoot);
                    musicThread.start();

                }
            };

            Runnable onVideoEnd = new Runnable() {
                @Override
                public void run() {
                    videoMediaPlayer.stop();
                    stage.getScene().setRoot(mainMenuRoot);
                    musicThread.start();
            }
        };
            videoMediaPlayer.setOnEndOfMedia(onVideoEnd);
            video.setOnMouseClicked(eHandler);
            video.setOnKeyPressed(eHandler);
            
        //----------------------------------------------------------------------------------

        //Make On Click New Game Menu

     
        
        // File fontFile = new File("src\\game\\gui\\assets\\Salium.ttf");
        Font.loadFont(getClass().getResourceAsStream("assets/Fonts/Salium.ttf"), 40);
        Scene scene = new Scene(openingRoot, 960, 540);
        String cssFile = getClass().getResource("application.css").toExternalForm();
        scene.getStylesheets().add(cssFile);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResource("assets/logo.png").toString()));
        stage.show();
        videoMediaPlayer.play();
       

        //Button Functionalties

        
        play.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                disableAllButtons();
                easy.setVisible(true);
                hard.setVisible(true);
                backToMain.setVisible(true);
            }
            
        });

        backToMain.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
               
                disableAllButtons();
                play.setVisible(true);
                mute.setVisible(true);
                quit.setVisible(true);

            }
            
        });

        mute.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                if(isMuted){
                    isMuted = false;
                    mute.setText("Mute");
                    musicMediaPlayer.setMute(false);
                }else{
                    isMuted = true;
                    mute.setText("Unmute");
                    musicMediaPlayer.setMute(true);
                }
            }
            
        });
        
        quit.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {

                stage.getScene().getWindow();
                stage.close();
            }
            
        });
        
        easy.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event){
                //musicMediaPlayer.stop();
                try {
                    new EasySceneController().startEasy();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            
        });

        hard.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event event) {
                try {
                    new HardSceneController().startHard();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            
        });


        Label assem = new Label("ASSEM");
        assem.setPrefHeight(400);
        assem.setPrefWidth(400);
        assem.setLayoutX(200);
        assem.setLayoutY(200);
        assem.setId("assem");
        assem.setVisible(false);
        mainMenuRoot.getChildren().add(assem);

        MediaPlayer assemMediaPlayer = new MediaPlayer(new Media(getClass().getResource("assets/Assem.mp4").toString()));
        MediaView assemMediaView = new MediaView();
        assemMediaView.setFitWidth(assemMediaView.getFitWidth()*0.1);
        assemMediaView.setFitHeight(assemMediaView.getFitHeight()*0.1);
        assemMediaView.setLayoutX(300);
        assemMediaView.setLayoutY(0);
        assemMediaView.setMediaPlayer(assemMediaPlayer);
        assemMediaPlayer.setVolume(1000);
        assemMediaView.setVisible(false);
        
        mainMenuRoot.getChildren().add(assemMediaView);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent event) {
                keyPresses = keyPresses + event.getText();
                if(keyPresses.length() >= 5){
                    if(keyPresses.substring(keyPresses.length() - 5, keyPresses.length()).equals("assem")){
                        assemMediaView.setVisible(true);
                        assemMediaPlayer.play();
                    }
                }
                
            }
            
        });

        

  













    }

    private static void disableAllButtons(){
        for (Button button : allButtons) {
            button.setVisible(false);
        }
    }

  
}



/*CSS NOTES
    # is used to reference an element by its ID
    . is used to reference an element by its class/type 
 */