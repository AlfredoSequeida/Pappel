import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ComboBox;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.GridPane;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.web.WebEngine;                                              
import javafx.scene.web.WebView;    

import javafx.geometry.Pos;
import javafx.geometry.Orientation;
import javafx.geometry.Insets;
;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableArray;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Date;
import java.util.Calendar;
import java.util.Properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;


public class PappelSettings extends Pappel{

        //File settingsFile = new File("./PappelSettings.properties");
        String settingsFile = "./PappelSettings.xml";

        TextField authCode = new TextField();

        private String prefWindow = "";

        @Override
        public void start(Stage PSMainWindow){

                DropboxIntegrator dropboxIntegrator = new DropboxIntegrator();

                AnchorPane root = new AnchorPane();

                GridPane grid = new GridPane();

                HBox settingsContainer = new HBox();

                ListView settingsNav = new ListView();
                settingsNav.getItems().addAll("Apperance", 
                                "Cloud Integration", "General");

                VBox settingsManager = new VBox();

                ObservableList <String> options = FXCollections.observableArrayList(
                                "Light",
                                "Dark"
                                ); 


                ComboBox themeChooser = new ComboBox(options);

                Label themeLabel = new Label("Theme:");

                Separator separator = new Separator();
                separator.setOrientation(Orientation.VERTICAL);

                Button apply = new Button("Apply");
                apply.setAlignment(Pos.CENTER_LEFT);


                settingsContainer.getChildren().addAll(settingsNav, separator, settingsManager);

                settingsContainer.setHgrow(settingsManager, Priority.ALWAYS);

                Label cloudLabel = new Label("Take your notes with you.");
                Label dropBox = new Label("DropBox");
                Label moreCommigSoon = new Label("Others coming soon");

                ImageView dropBoxImgBtn = new ImageView();
                dropBoxImgBtn.getStyleClass().add("dropBox-icon");
                dropBoxImgBtn.setFitHeight(200);
                dropBoxImgBtn.setFitWidth(200);

                authCode.setFont(Font.font(null,15.0));                                                                                                             
                authCode.setAlignment(Pos.CENTER);                             
                authCode.setPromptText("Paste authentication code here");                               
                authCode.setMaxSize(300.0, 50.0);
                authCode.setText(loadPreference("dropBoxAuthCode"));


                grid.add(settingsContainer, 0, 0);
                grid.add(apply, 1, 1);

                grid.setHgrow(settingsContainer, Priority.ALWAYS);


                root.getChildren().addAll(grid);

                root.setTopAnchor(grid, 10.0);
                root.setRightAnchor(grid, 10.0);
                root.setLeftAnchor(grid, 10.0);
                root.setBottomAnchor(grid, 10.0);

                Scene PSMainScene = new Scene(root, 640, 400);
                PSMainScene.getStylesheets().add(defaultTheme);  

                /*---------------------------------------------------------*/
                VBox apperanceSett = new VBox(themeLabel, themeChooser);
                VBox cloudIntegrationSett = new VBox(cloudLabel,dropBox, dropBoxImgBtn, authCode, moreCommigSoon);

                settingsNav.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event){

                                try{

                                        String currentPref = settingsNav.getSelectionModel().
                                                getSelectedItem().toString();

                                        System.out.println(currentPref);

                                        settingsManager.getChildren().remove(0, settingsManager.getChildren().size()); 

                                        switch (currentPref){
                                                case "Apperance":
                                                        System.out.println("here");
                                                        settingsManager.getChildren().addAll(apperanceSett);
                                                        prefWindow = "Apperance";
                                                        break;
                                                case "Cloud Integration":
                                                        settingsManager.getChildren().addAll(cloudIntegrationSett);
                                                        prefWindow = "Cloud Integration";
                                                        break;
                                                case "General":
                                                        prefWindow = "General";
                                                        break;
                                        }

                                }
                                catch(Exception ex) {
                                }

                        }
                });

                dropBoxImgBtn.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                new EventHandler<MouseEvent>() {

                                        @Override
                                        public void handle(MouseEvent event){
                                                try{
                                                        dropboxIntegrator.authenticate();
                                                }
                                                catch(Exception ex){
                                                        // IO Exception
                                                }
                                        }
                                });

                apply.setOnAction(new EventHandler<ActionEvent>() {
                        @Override public void handle(ActionEvent e) {
                                switch (prefWindow){
                                        case "Cloud Integration":
                                                System.out.println("savedrop");
                                                savePreference("dropBoxAuthCode", authCode.getText());
                                                break;

                                }
                        }
                });


                PSMainWindow.setScene(PSMainScene);
                PSMainWindow.show();
        }

        /**
         * loadPreference - loads specified preference  
         * @param: preference - preferece to load 
         * @return: String - preference data 
         */

        public String loadPreference(String preference){

                String preferenceData = "";

                // load data from config file
                try {

                        Properties prop = new Properties();

                        prop.loadFromXML(new FileInputStream(settingsFile));

                        preferenceData = prop.getProperty(preference);

                } 

                catch (Exception ex) {
                        // I/O error
                }

                return preferenceData;

        }

        /**
         * savePreference - saves specified preference  
         * @param: preference - preferece to save 
         * @param: preferenceData - preferece data to save 
         * @return: void 
         */

        public void savePreference(String preference, String preferenceData){
                try{
                        System.out.println("saving pref");
                        System.out.println(preference + preferenceData);

                        // load perefence file
                        FileInputStream in = new FileInputStream(settingsFile);
                        Properties props = new Properties();
                        props.loadFromXML(in);
                        in.close();

                        // save to preference file
                        FileOutputStream out = new FileOutputStream(settingsFile);
                        props.setProperty(preference, preferenceData);
                        props.storeToXML(out, null);
                        out.close();

                }
                catch(Exception ex){
                }
        }

}
