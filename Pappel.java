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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;

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

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.stage.FileChooser;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javafx.css.*;
import javafx.scene.Node;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Date;
import java.util.Calendar;

import java.text.SimpleDateFormat;
import java.text.DateFormat;

import java.io.File;

import java.net.URL;

public class Pappel extends Application{

        String defaultTheme = "./resources/css/DefaultTheme.css";
        static Note note = new Note();
        static Notebook notebook = new Notebook();

        // listview
        static ListView notesContainer = new ListView();

        public TextArea noteContent = new TextArea();

        // counter for markdown preview
        int counter = 0;

        // Pappel default dir
        final private String PAPPELDIR = System.getProperty("user.home") +      
                "/Documents/Pappel/"; 

        public boolean newNoteButtonPushed = false;

        public static void main (String [] args){
                launch(args);
        }

        @Override
        public void start(Stage pappelMainWindow){
                // Setting up Pappel  
                PappelSetup pappelSetup = new PappelSetup();
                // setting up Pappel's directory
                pappelSetup.setupPappelDir();

                // export
                Export export = new Export();

                // webview for markdown preview
                WebView browser = new WebView();
                WebEngine webEngine = browser.getEngine();
                webEngine.setJavaScriptEnabled(true);

                // initializing program components
                //Note note = new Note();

                ActionBar actionBar = new ActionBar();
                DistractionFreeView distractionFreeView = new DistractionFreeView();

                // adding GridPane
                GridPane grid = new GridPane();
                grid.setHgap(10);

                // adding AnchorPane
                AnchorPane root = new AnchorPane();

                //setting up GUI components

                //String defaultTheme = "./DefaultTheme.css";
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


                // making image buttons
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

                ImageView settingsImgBtn = new ImageView();
                settingsImgBtn.getStyleClass().add("settings-icon");
                settingsImgBtn.setFitHeight(20);
                settingsImgBtn.setFitWidth(20);


                /*----------------- setting tooltips-------------------------*/
                //htmlView
                Tooltip htmlViewTooltip = new Tooltip("Markdown preview");
                Tooltip.install(htmlViewImgBtn, htmlViewTooltip);

                // exportNote
                Tooltip exportNoteTooltip = new Tooltip("Export options");
                Tooltip.install(exportNoteImgBtn, exportNoteTooltip);

                // deleteNote
                Tooltip deleteNoteTooltip = new Tooltip("Delete Note");
                Tooltip.install(deleteNoteImgBtn, deleteNoteTooltip);

                // distractionFree 
                Tooltip distractionFreeTooltip = new Tooltip("Distraction free mode");
                Tooltip.install(distractionFreeImgBtn, distractionFreeTooltip);
                /*-----------------------------------------------------------*/

                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();

                Label dayLabel = new Label(note.getNoteCreationDate());


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
                contentContainer.setVgrow(browser, Priority.ALWAYS);

                // containers
                htmlViewContaier.getChildren().addAll(htmlViewImgBtn);
                titleContainer.getChildren().addAll(noteTitle, dayLabel);
                titleContainerBtns.getChildren().addAll(exportNoteImgBtn, deleteNoteImgBtn, distractionFreeImgBtn);


                // adding contentContainer to the grid
                grid.add(contentContainer, 4, 0);
                grid.setVgrow(contentContainer,Priority.ALWAYS);
                grid.setHgrow(contentContainer,Priority.ALWAYS);

                // setting separator
                Separator contentContainerSeparator = new Separator(Orientation.VERTICAL);
                grid.add(contentContainerSeparator, 3, 0);

                // wrapping content
                contentWrapper.getChildren().addAll(htmlViewContaier, titleContainer, titleContainerBtns);


                // adding content to contentContainer
                contentContainer.getChildren().addAll(contentWrapper, noteContent);

                // note browser container
                VBox noteBrowserContainer = new VBox();
                noteBrowserContainer.setAlignment(Pos.CENTER);
                noteBrowserContainer.setSpacing(30.0);

                // note browser

                // label

                // noteTitleContainer
                HBox noteTitleContainer = new HBox();
                noteTitleContainer.setAlignment(Pos.CENTER);

                Label noteBrowserTitle = new Label("Notes");
                noteBrowserTitle.setFont(Font.font(null, FontWeight.BOLD, 15.0));



                // addNote
                ImageView addNoteImgButton = new ImageView();
                addNoteImgButton.getStyleClass().add("add-content-icon");
                addNoteImgButton.setFitHeight(25);
                addNoteImgButton.setFitWidth(25);

                noteTitleContainer.getChildren().addAll(noteBrowserTitle,addNoteImgButton);


                // adding to note browser container
                noteBrowserContainer.getChildren().addAll(noteTitleContainer, notesContainer);
                noteBrowserContainer.setVgrow(notesContainer,Priority.ALWAYS);

                grid.add(noteBrowserContainer, 2, 0);

                // notebook browser

                // notebookTitleContainer
                HBox notebookTitleContainer = new HBox();
                notebookTitleContainer.setAlignment(Pos.CENTER);

                // label
                Label notebookTitle = new Label("Notebooks");
                noteBrowserTitle.setFont(Font.font(null, FontWeight.BOLD, 15.0));

                //addNotebook
                ImageView addNotebookImgButton = new ImageView();
                addNotebookImgButton.getStyleClass().add("add-content-icon");
                addNotebookImgButton.setFitHeight(25);
                addNotebookImgButton.setFitWidth(25);

                notebookTitleContainer.getChildren().addAll(notebookTitle, addNotebookImgButton);


                // Listview
                VBox notebookBrowserContainer = new VBox();
                notebookBrowserContainer.setAlignment(Pos.CENTER);
                notebookBrowserContainer.setSpacing(30.0);

                ListView notebookBrowser = new ListView();

                // settings bar
                HBox settingsBar = new HBox();
                settingsBar.getChildren().addAll(settingsImgBtn);
                notebookBrowserContainer.getChildren().addAll(notebookTitleContainer, notebookBrowser, settingsBar);
                notebookBrowserContainer.setVgrow(notebookBrowser,Priority.ALWAYS);
                grid.add(notebookBrowserContainer,0,0);

                // setting separator
                Separator notebookContainerSeparator = new Separator(Orientation.VERTICAL);
                grid.add(notebookContainerSeparator, 1, 0);


                // adding components to Anchorpane
                root.getChildren().addAll(grid);

                // adding Anchorpane to scene

                // adding scene to stage
                Scene pappelMainScene = new Scene(root, 950 ,600);
                pappelMainScene.getStylesheets().add(defaultTheme);

                // style for noteContainer
                notesContainer.getStyleClass().add("note-browser");

                // setting the stage
                pappelMainWindow.setScene(pappelMainScene);

                // adding anchor points
                root.setTopAnchor(grid, 10.0);
                root.setBottomAnchor(grid, 10.0);
                root.setRightAnchor(grid, 10.0);
                root.setLeftAnchor(grid, 10.0);

                // export menu
                ContextMenu exportMenu = new ContextMenu();

                //adding elemts to context menu
                MenuItem plainText = new MenuItem("Plain Text");
                MenuItem pdf = new MenuItem("PDF");
                MenuItem html = new MenuItem("HTML");

                exportMenu.getItems().addAll(plainText,pdf,html);

                /*----------------------GUI functionality--------------------*/

                // going though directory with new filewalker object
                Filewalker filewalker = new Filewalker();
                List <String> notebooks = new ArrayList <String>();
                notebooks = filewalker.walkFolders(PAPPELDIR);     

                // loading notebooks container with items from directory
                int i = 0;
                while (i < notebooks.size()){
                        notebookBrowser.getItems().add(notebooks.get(i));
                        i ++;
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
                                                try {
                                                        note.setTitle(newTitle);

                                                        // handling the change of a 
                                                        // tiitle if the case empty 
                                                        if (noteTitle.getText().isEmpty()){
                                                                note.setTitle("Unnamed");
                                                        }
                                                        /*

                                                           note.updateTitle(newTitle, notebook);
                                                           System.out.println("wrote");
                                                           */
                                                }
                                                catch(Exception ex){
                                                }
                                                        }
                                });

                //not in foucus 
                noteTitle.focusedProperty().addListener(new ChangeListener<Boolean>()
                                {
                                        @Override
                                        public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean inFocus)
                                        {
                                                note.setTitle(noteTitle.getText());

                                                try{
                                                        // only write a new note if the user indicates that
                                                        // they are wrtiting a new note
                                                        if (!inFocus.booleanValue() && newNoteButtonPushed){ 
                                                                System.out.println(newNoteButtonPushed);
                                                                note.write(notebook.getLocation());
                                                                newNoteButtonPushed = false;
                                                        }
                                                        // rename the note
                                                        else{
                                                                //handle renaming
                                                                note.updateTitle(notebook.getLocation());
                                                        }
                                                        System.out.println(newNoteButtonPushed);

                                                }
                                                catch(Exception e){
                                                }
                                        }
                                });
                // not in focus 
                noteContent.focusedProperty().addListener(new ChangeListener<Boolean>()
                                {
                                        @Override
                                        public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean inFocus)
                                        {
                                                // save the file automatically once the
                                                // note content filed is no longer in
                                                // focus
                                                if (!inFocus.booleanValue()){ 
                                                        try{
                                                                // writing out the note
                                                                note.write(notebook.getLocation());
                                                        }
                                                        catch(Exception e){
                                                        }
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

                                                // list for checking note content
                                                List<String> contentList = new <String>ArrayList();

                                                // getting list content
                                                contentList = notesContainer.getItems();

                                                if (!(notesContainer.getItems().contains(note.getTitle()))){
                                                        notesContainer.getItems().add(note.getTitle());
                                                }
                                                        }
                                });

                notesContainer.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override                                               
                        public void handle(MouseEvent event){                  

                                try{

                                        String currentNoteTitle = notesContainer.getSelectionModel().
                                                getSelectedItem().toString();

                                        // setting note field title
                                        noteTitle.setText(currentNoteTitle);

                                        // setting note title
                                        note.setTitle(currentNoteTitle);

                                        String content = note.getContent(note.getTitle(), notebook.getLocation());


                                        // setting note content
                                        noteContent.setText(content);

                                        // updating web view
                                        webEngine.loadContent(export.getHTML(note, notebook));

                                }
                                catch(Exception ex) {
                                }

                        }                                                       
                });     

                notebookBrowser.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override                                               
                        public void handle(MouseEvent event){                  

                                notesContainer.getItems().remove(0, notesContainer.getItems().size());
 
                                try{

                                        String currentNotebook = notebookBrowser.getSelectionModel().
                                                getSelectedItem().toString();

                                        notebook.setTitle(currentNotebook);

                                        List <String> notes = new ArrayList <String>();
                                        notes = filewalker.walk(PAPPELDIR + currentNotebook);     


                                        // loading notes container with items from directory
                                        int i = 0;
                                        while (i < notes.size()){
                                                if (!(notesContainer.getItems().contains(notes.get(i)))){
                                                        notesContainer.getItems().add(notes.get(i));
                                                }
                                                i ++;
                                        }

                                }
                                catch(Exception ex) {
                                }

                        }                                                       
                }); 

                // add a new note
                addNoteImgButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                new EventHandler<MouseEvent>() {

                                        /**
                                         * handle - overriding method for handling events
                                         * @param: event - mouse event to handle
                                         * @return: void
                                         */

                                        @Override
                                        public void handle(MouseEvent event){
                                                //clearing note title
                                                noteTitle.clear();
                                                // cleaning note content
                                                noteContent.clear();
                                                newNoteButtonPushed = true;
                                        }
                                });

                Stage actionBarStage = new Stage();
                actionBarStage.initStyle(StageStyle.UNDECORATED);


                // add a new notebook
                addNotebookImgButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                new EventHandler<MouseEvent>() {

                                        /**
                                         * handle - overriding method for handling events
                                         * @param: event - mouse event to handle
                                         * @return: void
                                         */

                                        @Override
                                        public void handle(MouseEvent event){
                                                System.out.println("hello");
                                                ActionBar makeNotebook = new ActionBar("add-notebook:");
                                                makeNotebook.start(actionBarStage);
                                                notebookBrowser.getItems().add(notebook.getTitle());
                                        }
                                });


                // delete a note 
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
                                                                noteTitle.clear();
                                                                noteContent.clear();
                                                        }
                                                        else {
                                                                alert.close();
                                                        }
                                                }
                                        }
                                });

                // only export if note content excists

                //export a note
                exportNoteImgBtn.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                new EventHandler<MouseEvent>() {

                                        @Override
                                        public void handle(MouseEvent event){

                                                note.write(notebook.getLocation());

                                                if (!(noteContent.getText().equals("") && noteTitle.getText().equals(""))){
                                                        exportMenu.show(pappelMainWindow, event.getScreenX(), event.getScreenY());
                                                }
                                        }
                                });


                // markdown preview
                htmlViewImgBtn.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                new EventHandler<MouseEvent>() {

                                        @Override
                                        public void handle(MouseEvent event){

                                                note.write(notebook.getLocation());

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

                PappelSettings pappelSettings = new PappelSettings();
                Stage settingsWindow = new Stage();
                settingsWindow.initStyle(StageStyle.UTILITY);

                // settings 
                settingsImgBtn.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                new EventHandler<MouseEvent>() {

                                        @Override
                                        public void handle(MouseEvent event){

                                                note.write(notebook.getLocation());

                                                pappelSettings.start(settingsWindow);

                                        }
                                });


                // export as plaintext 
                plainText.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent e) {

                                note.write(notebook.getLocation());

                                File tempFile;
                                File outputFile;

                                FileChooser choseOutputFile = new FileChooser ();
                                // setting extension filters
                                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter
                                        ("TXT files (*.txt)", "*.txt");
                                choseOutputFile.getExtensionFilters().add(extFilter);
                                tempFile = choseOutputFile.showSaveDialog(pappelMainWindow);

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

                                note.write(notebook.getLocation());

                                try {

                                        File tempFile;
                                        File outputFile;

                                        FileChooser choseOutputFile = new FileChooser ();
                                        // setting extension filters
                                        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter
                                                ("HTML files (*.html)", "*.html");
                                        choseOutputFile.getExtensionFilters().add(extFilter);
                                        tempFile = choseOutputFile.showSaveDialog(pappelMainWindow);

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

                LinuxDependencyCheck check = new LinuxDependencyCheck();

                // export as pdf 
                pdf.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent e) {

                                note.write(notebook.getLocation());

                                if (check.isPhantomInstalled()){
                                        try {

                                                File tempFile;
                                                File outputFile;

                                                FileChooser choseOutputFile = new FileChooser ();
                                                // setting extension filters
                                                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter
                                                        ("PDF files (*.pdf)", "*.pdf");
                                                choseOutputFile.getExtensionFilters().add(extFilter);
                                                tempFile = choseOutputFile.showSaveDialog(pappelMainWindow);

                                                String out = tempFile.toString().substring(Math.max(0, tempFile.
                                                                        toString().length() - 4));

                                                // dealing with adding file extensions if not already present
                                                if (!(out.equals(".pdf"))){
                                                        out = tempFile.toString() + ".pdf";
                                                        outputFile = new File (out);
                                                } 

                                                else {
                                                        outputFile = new File (tempFile.toString());
                                                }

                                                export.asPDF(note, notebook, outputFile);
                                        }
                                        catch (Exception ex){
                                        }
                                }

                                else{
                                        Alert alert = new Alert(AlertType.ERROR);
                                        alert.setTitle("Missing Dependency");
                                        alert.setHeaderText("To export as a pdf, you need to install" + 
                                                        "phantomjs");
                                        alert.setContentText(null);

                                        alert.showAndWait();
                                }
                        }
                });


                pappelMainScene.setOnKeyReleased(new EventHandler<KeyEvent>(){
                        @Override
                        public void handle(KeyEvent ke){

                                if (ke.getCode().equals(KeyCode.F2))
                                {
                                        actionBar.start(actionBarStage);
                                }
                        }
                });

                Stage distractionFreeViewStage = new Stage();
                distractionFreeViewStage.initStyle(StageStyle.TRANSPARENT);
                distractionFreeViewStage.initStyle(StageStyle.UNDECORATED);

                distractionFreeImgBtn.addEventHandler(MouseEvent.MOUSE_CLICKED,
                                new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent event){
                                                try{
                                                        distractionFreeView.start(distractionFreeViewStage);
                                                        noteContent.setText(note.getContent(note.getTitle(), notebook.getLocation()));
                                                }
                                                catch(Exception ex){
                                                }
                                        }
                                });

                // on focus
                pappelMainWindow.focusedProperty().addListener(new ChangeListener<Boolean>()
                                {
                                        @Override
                                        public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean inFocus)
                                        {
                                                if (inFocus.booleanValue()){ 
                                                        try{
                                                                // updating note content and title on focus
                                                                noteContent.setText(note.getContent(note.getTitle(), notebook.getLocation()));
                                                                //noteTitle.setText(note.getTitle(), note.getTitle());
                                                        }
                                                        catch(Exception e){
                                                        }
                                                }
                                        }
                                });

                pappelMainWindow.setOnCloseRequest(new EventHandler<WindowEvent>() {
                        public void handle(WindowEvent we) {

                                try{
                                        // writing note to system
                                        note.write(notebook.getLocation());
                                }
                                catch(Exception ex){
                                }
                        }
                });
                /*-----------------------------------------------------------*/

                // setting up main window
                pappelMainWindow.setTitle("Pappel");

                // showing the stage
                pappelMainWindow.show();
        }

}
