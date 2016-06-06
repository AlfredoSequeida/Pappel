import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;

import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.markdown4j.Markdown4jProcessor;
import org.pegdown.PegDownProcessor;

public class Note{

        private String title;
        private String content;
        // directory to save notes
        final private String PAPPELDIR = System.getProperty("user.home") +      
                "/Documents/Pappel/"; 
        // note file
        File note = new File ("");
        DropboxIntegrator dropboxIntegrator;
        // note file name counter
        public int noteFileNameCounter = -1;

        /**
         * Note constructor
         */
        public Note(){
                // setting default note title
                this.title = "Unnamed";
                //note = new File(PAPPELDIR + );
        }

        /**
         * write - save a note note
         * @param: content - The content to be written
         * @return: void
         */
        public void write(String notebookLoc){ 

                try {

                        System.out.println("writing");

                        File newFile = new File(notebookLoc + this.title);     
                        newFile.createNewFile();
                        note = newFile;
                        //if(checkForExistance(note)){

                        // making a buffered writer to speed up the note saving
                        // process
                        BufferedWriter out = new BufferedWriter(new FileWriter(note)); 


                        out.write(this.content);
                        // closing the file
                        out.close();

                        DropboxIntegrator dropBoxIntegrator = new DropboxIntegrator(); 

                        dropBoxIntegrator.push(note.toString());

                        }

                catch (Exception e)
                {
                        System.out.println("Exception ");       
                }
                }

                /**
                 * appendAndSave - add content to excisting note 
                 * @param: appendContent - content to ne added 
                 * @return: void
                 */

                public void appendAndSave(String appendContent){ 

                        try {

                                // making a buffered writer to speed up the note saving
                                // process
                                BufferedWriter out = new BufferedWriter(new FileWriter(note, true)); 


                                out.write(appendContent);
                                // closing the file
                                out.close();

                                //dropboxIntegrator.push(PAPPELDIR + "/" + this.title);

                                //}
                }

                catch (Exception e)
                {
                        System.out.println("Exception ");       
                }
        }

        /**
         * checkForExistance - check if note / file excists 
         * @param: filetocheck - note to check for 
         * @return: boolean - excistance of a note 
         */

        public boolean checkForExistance(File fileToCheck){
                System.out.println(fileToCheck.getParent());
                // get parent folder/notebook
                File folder = new File(note.getParent().toString());
                // list files in folder/notebook
                File[] listOfFiles = folder.listFiles();

                try {

                        // chekcing for excistance
                        if (!Arrays.asList(listOfFiles).contains(fileToCheck)){
                                fileToCheck.createNewFile();
                        }
                        else{
                                System.out.println("***normal write");
                                System.out.println(fileToCheck.getParent());
                                write(fileToCheck.getParent());
                        }
                }
                catch(Exception ex){
                }

                return false;
        }

        /**
         * updateTitle - update note title 
         * @param: notebookLoc - location of notebook 
         * @return: void 
         */

        // look for better way of renaming
        public void updateTitle(String notebookLoc){
                // updating file the file name
                File renamedFile = new File(notebookLoc + this.title);
                System.out.println(renamedFile.toString());
                // renaming file
                note.renameTo(renamedFile);
        }

        /**
         * delete - delete note 
         * @return: void 
         */

        public void delete(){
                // deleting note
                note.delete();       
        }

        /*
           public String updateFileNameCounter(){
           this.noteFileNameCounter ++;
           this.title = this.title + this.noteFileNameCounter;
           return this.title;
           }
           */

        /**
         * setTitle - set the title of the note
         * @param: title - the title to set for the note
         * @return: void
         */
        public void setTitle(String title){

                /*
                // catching the empty title case
                if (title == null){
                title = "Unnamed";
                }
               */

                // setting the title for the note
                this.title = title;
        }

        /**
         * getTitle - get the title of the note
         * @return: String - get the title of the note 
         */

        public String getTitle(){
                return this.title;
        }

        /**
         * getNotePreview - get the first line of a note as preview 
         * @return: String - preview of note 
         */

        public String getNotePreview(){

                String notePreview = "";

                try{
                        BufferedReader bfr = new BufferedReader(new FileReader(note));

                        // read the first line, that's what you need
                        notePreview = bfr.readLine();
                }
                catch(Exception e) {
                }
                return notePreview; 
        }

        /**
         * getContent - get the content of a note 
         * @param: noteName - The name of the note to get
         * @return: String - The content of the note
         */
        public String getContent(String noteName, String notebookLoc) throws IOException{
                try(BufferedReader br = new BufferedReader(new FileReader
                                        (notebookLoc +  noteName))){

                        // building string
                        StringBuilder sb = new StringBuilder();
                        String line = br.readLine();

                        // reading file line by line until there are no more
                        // lines
                        while (line != null) {
                                sb.append(line);
                                sb.append(System.lineSeparator());
                                line = br.readLine();
                        }
                        // getting everything in file
                        String everything = sb.toString();
                        return everything;
                                        }
        }

        /**
         * getNoteCreationDate - get the creation date of a note 
         * @return: String - creation date of note 
         */

        public String getNoteCreationDate(){

                BasicFileAttributes attr = null;

                // get file attributes
                try{
                        Path noteFilePath = Paths.get(note.toString());
                        attr = Files.readAttributes(noteFilePath, BasicFileAttributes.class);
                }
                catch (Exception ex) {
                }

                // get time of note creation date
                return attr.creationTime().toString();
        }

        /**
         * setContent - set note content 
         * @param: passedcontent - content to set to note
         * @return: void 
         */

        public void setContent(String passedContent){
                //setting note content
                this.content = passedContent;
        }

}
