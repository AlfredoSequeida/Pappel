import javafx.application.Application;

import javafx.scene.Scene;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

import javafx.stage.Stage;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.Optional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.IOException;

import java.lang.Process;
import java.lang.Runtime;

public class LinuxDependencyCheck{

        public boolean isPhantomInstalled(){
                String terminalOut = null;

                terminalOut = runBash("./resources/bash/pacapt -Qqe phantomjs");

                if (terminalOut.equals("phantomjs")){
                        return true;
                }
                return false;
        }

        public String runBash(String runCommand){
                String result = null;
                String returnVal = null;
                try {
                        Runtime r = Runtime.getRuntime();                    

                        Process p = r.exec(runCommand);

                        BufferedReader in =
                                new BufferedReader(new InputStreamReader(p.getInputStream()));
                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                                System.out.println(inputLine);
                                result += inputLine;
                                returnVal = inputLine;
                        }
                        in.close();

                } catch (IOException e) {
                        System.out.println(e);
                }

                return returnVal;
        }
}
