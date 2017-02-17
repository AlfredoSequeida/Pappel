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

public class MinimalActionBar extends Pappel{

        private int counter = 0;
        private String initiatedCommand = null;
        String promptText = null;


        public MinimalActionBar(){
        }

        public MinimalActionBar(String promptText){
                this.promptText = promptText;
        }

        @Override
        public void start(Stage minimalActionBarMainWindow){

                // AnchorPane
                AnchorPane root = new AnchorPane();

                TextField actionText = new TextField();
                //actionText.setPromptText("command filter");
                actionText.setFont(Font.font(null,20.0));                        
                actionText.setAlignment(Pos.CENTER);                             
                actionText.setPromptText(promptText);                               
                actionText.setMaxSize(300.0, 50.0);

                //horizontal separator
                Separator lineSeparator = new Separator();

                //container
                VBox container = new VBox();
                container.setAlignment(Pos.CENTER);

                // setting anchors
                root.setRightAnchor(container, 5.0);
                root.setLeftAnchor(container, 5.0);
                root.setTopAnchor(container, 5.0);
                root.setBottomAnchor(container, 5.0);


                container.getChildren().addAll(actionText);

                container.setVgrow(actionText, Priority.ALWAYS);

                root.getChildren().addAll(container);

                // scene
                Scene minimalActionBarScene = new Scene(root, 500, 250);
                minimalActionBarScene.getStylesheets().add(defaultTheme);

                minimalActionBarMainWindow.setScene(minimalActionBarScene);

                if(counter == 1){
                        actionText.setText(initiatedCommand);
                        minimalActionBarMainWindow.show();
                        counter = 0;
                        System.out.println(counter);
                }

                minimalActionBarScene.setOnKeyReleased(new EventHandler<KeyEvent>(){
                        @Override
                        public void handle(KeyEvent ke)
                        {
                                if (ke.getCode().equals(KeyCode.ESCAPE))
                                {
                                        minimalActionBarMainWindow.close();
                                }
                        }
                });

                minimalActionBarScene.setOnKeyReleased(new EventHandler<KeyEvent>(){
                        @Override
                        public void handle(KeyEvent ke)
                        {
                                if (ke.getCode().equals(KeyCode.ENTER))
                                {
                                        minimalActionBarData = actionText.getText();
                                        minimalActionBarMainWindow.close();
                                }
                        }
                });


                // showing the ActionBar
                minimalActionBarMainWindow.show();
        }
}
