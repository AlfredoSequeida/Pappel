import javafx.stage.Stage;

import javafx.scene.Scene;

import javafx.scene.text.Font;

import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.event.EventHandler;

import javafx.geometry.Pos;

import java.util.Arrays;

import java.net.URL;

import org.controlsfx.control.NotificationPane;

public class WebAction extends Pappel{

        private String loadAction = null;
        private String searchEngine = "https://www.google.com/search?q=";
        private String searchTerm = null;
        private int actionNum = 0;

        // search engine
        public WebAction(int actionNum,  String searchTerm){
                this.actionNum = actionNum;
                this.searchTerm = searchTerm;
                String webAdress = null;

                switch (actionNum){
                        case 0:
                                System.out.println("search");
                                String searchEngineAction = this.searchEngine + searchTerm;
                                this.loadAction = searchEngineAction;
                                break;

                        case 1: 
                                System.out.println("address");
                                if ( !searchTerm.contains("https://") && !searchTerm.contains("http://")){
                                        webAdress = "http://" + searchTerm; 
                                }

                                else{
                                        webAdress = searchTerm; 
                                }

                                this.loadAction = webAdress;
                                System.out.println(this.loadAction);
                                break;
                }
        }

        @Override
        public void start(Stage webActionMainWindow){

                String defaultTheme = "./resources/css/DefaultTheme.css";

                // AnchorPane
                AnchorPane root = new AnchorPane();

                WebView browser = new WebView();
                WebEngine webEngine = browser.getEngine();
                webEngine.setJavaScriptEnabled(true);


                webEngine.load(this.loadAction);


                HBox contentContainer = new HBox(); 
                VBox sidePannel = new VBox(); 

                ImageView webPinImgBtn = new ImageView();
                webPinImgBtn.getStyleClass().add("web-pin-icon");
                webPinImgBtn.setFitHeight(30);
                webPinImgBtn.setFitWidth(30);

                sidePannel.getChildren().addAll(webPinImgBtn);

                contentContainer.getChildren().addAll(sidePannel, browser);
                contentContainer.setHgrow(browser, Priority.ALWAYS);
                root.getChildren().addAll(contentContainer);

                root.setTopAnchor(contentContainer, 5.0);
                root.setBottomAnchor(contentContainer, 5.0);
                root.setRightAnchor(contentContainer, 5.0);
                root.setLeftAnchor(contentContainer, 5.0);

                Scene webActionMainScene = new Scene(root, 950 ,600);              
                webActionMainScene.getStylesheets().add(defaultTheme);             

                /*---------------------GUI Actions---------------------------*/
                webPinImgBtn.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent event){
                                                String pageToPin = webEngine.getLocation();
                                                String markDFormat = "[" + searchTerm + "]" + "(" + pageToPin + ")";
                                                note.appendAndSave(markDFormat);
                                                NotificationPane notificationPane = new NotificationPane(browser);
                                                notificationPane.setText("hello");
                                                notificationPane.show();
                                        }
                                });
                /*-----------------------------------------------------------*/


                // setting the stage                                            
                webActionMainWindow.setScene(webActionMainScene); 

                webActionMainWindow.show();
        }
}
