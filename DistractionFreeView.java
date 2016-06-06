import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

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

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Date;
import java.util.Calendar;

import java.text.SimpleDateFormat;
import java.text.DateFormat;

import java.io.File;

public class DistractionFreeView extends Pappel{

        @Override
        public void start(Stage dFVMainWindow){
                // export class
                Export export = new Export()  ;

                //TextArea noteContent = new TextArea();
                TextField noteTitle = new TextField();

                // setting componets are gloval vars to to have acees to them in 
                // distraction free view

                // content container
                VBox contentContainer = new VBox();

                // button container
                HBox titleContainerBtns = new HBox();

                // title container 
                HBox titleContainer = new HBox();


                // htmlView Container
                HBox htmlViewContaier = new HBox();

                // contentWrapper
                HBox contentWrapper = new HBox();


                // immage buttons 
                ImageView htmlViewImgBtn = new ImageView();
                htmlViewImgBtn.getStyleClass().add("html-view-icon");
                htmlViewImgBtn.setFitHeight(25);
                htmlViewImgBtn.setFitWidth(25);

                ImageView exportNoteImgBtn = new ImageView();
                exportNoteImgBtn.getStyleClass().add("export-note-icon");
                exportNoteImgBtn.setFitHeight(25);
                exportNoteImgBtn.setFitWidth(25);

                ImageView deleteNoteImgBtn = new ImageView();
                deleteNoteImgBtn.getStyleClass().add("delete-icon");
                deleteNoteImgBtn.setFitHeight(25);
                deleteNoteImgBtn.setFitWidth(25);

                ImageView distractionFreeImgBtn = new ImageView();
                distractionFreeImgBtn.getStyleClass().add("distraction-free-icon");
                distractionFreeImgBtn.setFitHeight(25);
                distractionFreeImgBtn.setFitWidth(25);


                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();

                Label dayLabel = new Label(dateFormat.format(date).toString());


                noteContent.setWrapText(true);

                AnchorPane root = new AnchorPane();

                // adding contentContainer elements

                //content container
                contentContainer.setSpacing(10.0);
                contentContainer.setAlignment(Pos.CENTER);

                //button container
                titleContainerBtns.setAlignment(Pos.CENTER);
                titleContainerBtns.setSpacing(10.0);

                // tittle container 
                titleContainer.setAlignment(Pos.CENTER);

                // html container 
                htmlViewContaier.setAlignment(Pos.CENTER);

                // content wraper
                contentWrapper.setAlignment(Pos.CENTER);
                contentWrapper.setSpacing(100.0);
                contentWrapper.setPadding(new Insets(0, 20, 10, 20)); 

                // note title
                noteTitle.setFont(Font.font(null,20.0));
                noteTitle.setAlignment(Pos.CENTER);
                noteTitle.setPromptText("Title");
                noteTitle.setMaxSize(300.0, 50.0);

                // note taking area
                noteContent.setWrapText(true);
                noteContent.setPromptText("Let's create something!");
                contentContainer.setVgrow(noteContent, Priority.ALWAYS);

                // containers
                htmlViewContaier.getChildren().addAll(htmlViewImgBtn);
                titleContainer.getChildren().addAll(noteTitle, dayLabel);
                titleContainerBtns.getChildren().addAll(exportNoteImgBtn, deleteNoteImgBtn, distractionFreeImgBtn);

                contentWrapper.getChildren().addAll(htmlViewContaier, titleContainer, titleContainerBtns);
                contentContainer.getChildren().addAll(contentWrapper, noteContent);

                // setting anchor points
                root.setTopAnchor(contentContainer, 5.0);
                root.setBottomAnchor(contentContainer, 5.0);
                root.setRightAnchor(contentContainer, 5.0);
                root.setLeftAnchor(contentContainer, 5.0);

                root.getChildren().addAll(contentContainer);

                Scene dFVMainScene = new Scene(root);

                dFVMainScene.getStylesheets().add(defaultTheme);
                dFVMainWindow.setScene(dFVMainScene);

                // export menu
                ContextMenu exportMenu = new ContextMenu();

                //adding elemts to context menu
                MenuItem plainText = new MenuItem("Plain Text");
                MenuItem pdf = new MenuItem("PDF");
                MenuItem html = new MenuItem("HTML");

                exportMenu.getItems().addAll(plainText,pdf,html);

                try {

                        noteTitle.setText(note.getTitle());
                        noteContent.setText(note.getContent(note.getTitle(), notebook.getLocation()));
                }
                catch (Exception e){
                }


                // getting note title
                noteTitle.textProperty().addListener(new ChangeListener
                                <String>() {

                                        /**
                                         * changed - checking for value chnaged
                                         * @param: obserVablevalue - valued to
                                         * check for change
                                         * @param: oldTitle - old title  
                                         * @param: newTitle - updated title
                                         * @return: void
                                         */
                                        @Override
                                        public void changed(ObservableValue<? extends String> 
                                                        observableValue, String oldTitle, String newTitle) {
                                                note.setTitle(newTitle);

                                                // handling the change of a 
                                                // tiitle if the case empty 
                                                if (noteTitle.getText().isEmpty()){
                                                        note.setTitle("Unnamed");
                                                }
                                                        }
                                });


                // listening for chnages made to the note filed
                noteContent.textProperty().addListener(new ChangeListener
                                <String>() {

                                        /**
                                         * changed - checking for value chnaged
                                         * @param: obserVablevalue - valued to
                                         * check for change
                                         * @param: oldContent - old note content  
                                         * @param: newContent - updated note content 
                                         * @return: void
                                         */
                                        @Override
                                        public void changed(ObservableValue<? extends String> 
                                                        observableValue, String oldContent, String newContent) {

                                                note.setContent(newContent);

                                                        }
                                });

                
                // webview for markdown preview
                WebView browser = new WebView();
                WebEngine webEngine = browser.getEngine();
                webEngine.setJavaScriptEnabled(true);
                contentContainer.setVgrow(browser, Priority.ALWAYS);

                // markdown preview
                htmlViewImgBtn.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                new EventHandler<MouseEvent>() {

                                        @Override
                                        public void handle(MouseEvent event){

                                                if (counter == 0){
                                                        contentContainer.getChildren().addAll(browser);
                                                        webEngine.loadContent(export.getHTML(note, notebook));
                                                        contentContainer.getChildren().remove(noteContent);
                                                        counter = 1;
                                                }

                                                else {
                                                        contentContainer.getChildren().remove(browser);
                                                        contentContainer.getChildren().addAll(noteContent);
                                                        counter = 0;
                                                }
                                        }
                                });

                // delete note
                deleteNoteImgBtn.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                new EventHandler<MouseEvent>() {

                                        /**
                                         * handle - overriding method for handling events
                                         * @param: event - mouse event to handle
                                         * @return: void
                                         */

                                        @Override
                                        public void handle(MouseEvent event){

                                                if (!(noteContent.getText().equals("") && noteTitle.getText().equals(""))){

                                                        // alerting the user of deleting a note
                                                        Alert alert = new Alert(AlertType.CONFIRMATION);
                                                        alert.setTitle("Delete");
                                                        alert.setHeaderText("Are you sure that you want to delete " + "'" + note.getTitle() + "'" + "?");
                                                        alert.setContentText(null);


                                                        Optional<ButtonType> result = alert.showAndWait();
                                                        if (result.get() == ButtonType.OK){
                                                                note.delete();
                                                                // deleting note from list
                                                                notesContainer.getItems().remove(note.getTitle());
                                                                // clearing the note contents
                                                                noteTitle.setText(null);
                                                                noteContent.setText(null);
                                                        }
                                                        else {
                                                                alert.close();
                                                        }
                                                }
                                        }
                                });

                //export a note
                exportNoteImgBtn.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                new EventHandler<MouseEvent>() {

                                        @Override
                                        public void handle(MouseEvent event){

                                                if (!(noteContent.getText().equals("") && noteTitle.getText().equals(""))){
                                                        exportMenu.show(dFVMainWindow, event.getScreenX(), event.getScreenY());
                                                }
                                        }
                                });



                // export as plaintext 
                plainText.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent e) {

                                File tempFile;
                                File outputFile;

                                FileChooser choseOutputFile = new FileChooser ();
                                // setting extension filters
                                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter
                                        ("TXT files (*.txt)", "*.txt");
                                choseOutputFile.getExtensionFilters().add(extFilter);
                                tempFile = choseOutputFile.showSaveDialog(dFVMainWindow);

                                String out = tempFile.toString().substring(Math.max(0, tempFile.
                                                        toString().length() - 4));

                                // dealing with adding file extensions if not already present
                                if (!(out.equals(".txt"))){
                                        out = tempFile.toString() + ".txt";
                                        outputFile = new File (out);
                                }
                                else{ 
                                        outputFile = new File (tempFile.toString());
                                }

                                export.asPlainText(note, notebook, outputFile);
                        }
                });

                // export as html 
                html.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent e) {

                                try {

                                        File tempFile;
                                        File outputFile;

                                        FileChooser choseOutputFile = new FileChooser ();
                                        // setting extension filters
                                        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter
                                                ("HTML files (*.html)", "*.html");
                                        choseOutputFile.getExtensionFilters().add(extFilter);
                                        tempFile = choseOutputFile.showSaveDialog(dFVMainWindow);

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

                                        export.asHTML(note, notebook, outputFile);
                                }
                                catch (Exception ex){
                                }
                        }
                });

                // export as pdf 
                pdf.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent e) {

                                try {

                                        File tempFile;
                                        File outputFile;

                                        FileChooser choseOutputFile = new FileChooser ();
                                        // setting extension filters
                                        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter
                                                ("PDF files (*.pdf)", "*.pdf");
                                        choseOutputFile.getExtensionFilters().add(extFilter);
                                        tempFile = choseOutputFile.showSaveDialog(dFVMainWindow);

                                        String out = tempFile.toString().substring(Math.max(0, tempFile.
                                                                toString().length() - 4));

                                        // dealing with adding file extensions if not already present
                                        if (!(out.equals(".pdf"))){
                                                out = tempFile.toString() + ".pdf";
                                                outputFile = new File (out);
                                        }
                                        else{ 
                                                outputFile = new File (tempFile.toString());
                                        }

                                        export.asPDF(note, notebook, outputFile);
                                }
                                catch (Exception ex){
                                }
                        }
                });

                dFVMainScene.setOnKeyReleased(new EventHandler<KeyEvent>(){
                        @Override
                        public void handle(KeyEvent ke)
                        {
                                if (ke.getCode().equals(KeyCode.ESCAPE))
                                {
                                        note.write(notebook.getLocation());
                                        dFVMainWindow.close();
                                }
                        }
                });

                distractionFreeImgBtn.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent event){
                                                dFVMainWindow.close();
                                        note.write(notebook.getLocation());
                                        }
                                });

                dFVMainWindow.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        public void handle(WindowEvent we) {

                                try{
                                        // writing note to system
                                        note.write(notebook.getLocation());
                                        //note.setContent(note.getContent(note.getTitle(), notebook.getLocation()));
                                        noteContent.setText(note.getContent(note.getTitle(), notebook.getLocation()));
                                }
                                catch(Exception ex){
                                }
                        }
                });


                dFVMainWindow.setFullScreenExitHint("Press ESC to exit distraction free mode");
                dFVMainWindow.show();
                dFVMainWindow.setFullScreen(true);
        }
}
