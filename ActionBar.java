import javafx.application.Application;

import javafx.stage.Stage;
import javafx.stage.FileChooser;

import javafx.scene.Scene;

import javafx.scene.text.Font;

import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import javafx.event.EventHandler;

import javafx.geometry.Pos;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import java.io.File;

import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.AutoCompletionBinding.AutoCompletionEvent;

public class ActionBar extends Pappel{

        private int counter = 0;
        private String initiatedCommand = null;


        public ActionBar(){
        }

        public ActionBar(String command){
                counter = 1;
                this.initiatedCommand = command;
                System.out.println(initiatedCommand);
        }

        @Override
        public void start(Stage actionBarMainWindow){

                // AnchorPane
                AnchorPane root = new AnchorPane();

                TextField actionText = new TextField();
                actionText.setPromptText("command filter");
                actionText.setFont(Font.font(null,20.0));                        
                actionText.setAlignment(Pos.CENTER);                             
                actionText.setPromptText("Filter Commands");                               
                actionText.setMaxSize(300.0, 50.0);

                List <String> suggestions = new ArrayList <String>();

                suggestions.add("web-search:");
                suggestions.add("web-address:");

                //AutoCompletionBinding<String> autoCompletionBinding = TextFields.bindAutoCompletion(actionText, suggestions);

                //autoCompletionBinding.bindAutoCompletion(actionText, suggestions);

                //horizontal separator
                Separator lineSeparator = new Separator();

                ListView results = new ListView();

                //container
                VBox container = new VBox();
                container.setAlignment(Pos.CENTER);

                // setting anchors
                root.setRightAnchor(container, 5.0);
                root.setLeftAnchor(container, 5.0);
                root.setTopAnchor(container, 5.0);
                root.setBottomAnchor(container, 5.0);


                container.getChildren().addAll(actionText, lineSeparator, results);

                container.setVgrow(results, Priority.ALWAYS);

                root.getChildren().addAll(container);

                // scene
                Scene actionBarScene = new Scene(root, 500, 250);
                actionBarScene.getStylesheets().add(defaultTheme);

                actionBarMainWindow.setScene(actionBarScene);

                if(counter == 1){
                        actionText.setText(initiatedCommand);
                        actionBarMainWindow.show();
                        counter = 0;
                        System.out.println(counter);
                }


                actionBarScene.setOnKeyReleased(new EventHandler<KeyEvent>(){
                        @Override
                        public void handle(KeyEvent ke)
                        {
                                if (ke.getCode().equals(KeyCode.ESCAPE))
                                {
                                        actionBarMainWindow.close();
                                }
                        }
                });

                actionBarScene.setOnKeyReleased(new EventHandler<KeyEvent>(){
                        @Override
                        public void handle(KeyEvent ke)
                        {
                                if (ke.getCode().equals(KeyCode.ENTER))
                                {
                                        runCommand(actionText.getText());
                                        actionBarMainWindow.close();
                                }
                        }
                });

                /*
                autoCompletionBinding.setOnAutoCompleted(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent ke) {
                                if (ke.getCode().equals(KeyCode.ENTER)){
                                actionText.setText(autoCompletionBinding);
                                }
                        }
                });
                */



                // showing the ActionBar
                actionBarMainWindow.show();
        }

        private void runCommand(String command){

                String commandType = parseText(command);
                command = command.substring(command.indexOf(" ")+1);

                switch (commandType.toLowerCase()){
                        case "web-search:":
                                         runWebSearch(command);
                                         break;
                        case "web-address:":
                                          runWebAddress(command);
                                          break;
                        case "find:": 
                                   runFind(command);
                                   break;
                        case "add-image:":           
                                        runAddFile(command, commandType);
                                        break;
                        case "add-note:":                
                                       runAddNote(command);
                                       break;
                        case "add-notebook:":
                                           runAddNotebook(command);
                                           break;
                        case "tutorial:":
                                       runTutorial();
                                       break;
                }
        }

        private void runWebSearch(String webCommand){
                WebAction webAction = new WebAction(0, webCommand);
                Stage webViewWindow = new Stage();
                webAction.start(webViewWindow);
        }

        private void runWebAddress(String webCommand){
                WebAction webAction = new WebAction(1, webCommand);
                Stage webViewWindow = new Stage();
                webAction.start(webViewWindow);
        }

        private void runFind(String findCommand){
        }

        private void runTutorial(){
        }

        private void runAddFile(String command, String commandType){
                String imageToAdd = null;

                if (command.equals(commandType)){

                        Stage fileMan = new Stage();

                        File tempFile;
                        File outputFile;

                        FileChooser choseOutputFile = new FileChooser ();
                        // setting extension filters
                        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter
                                ("All Images", "*.*");
                        choseOutputFile.getExtensionFilters().add(extFilter);
                        tempFile = choseOutputFile.showOpenDialog(fileMan);

                        /*
                           String out = tempFile.toString().substring(Math.max(0, tempFile.
                           toString().length() - 5));

                        // dealing with adding file extensions if not already present
                        if (!(out.equals(".html"))){
                        out = tempFile.toString() + ".html";
                        outputFile = new File (out);
                        }
                        else{ 
                        outputFile = new File (tempFile.toString());
                        }
                        */

                        imageToAdd = tempFile.toString();  
                }

                else{
                        imageToAdd = command;
                }

                String markDFormat = "![Alt text]" + "(" + imageToAdd + ")";
                note.appendAndSave(markDFormat);

        }

        private void runAddNote(String command){
        }

        private void runAddNotebook(String command){
                notebook.setTitle(command);
                notebook.make();
        }

        private void ExportAs(String exportCommand){
        }

        private void ShareAS(String shareCommand){
        }

        private String parseText(String commandToParse){
                // handling the case of multiple words
                if (commandToParse.indexOf(" ") > -1){
                        return commandToParse.substring(0, commandToParse.indexOf(" "));
                }
                // case where there is only one word
                else {
                        return commandToParse;
                }
        }
}
