import org.markdown4j.Markdown4jProcessor;
import org.pegdown.PegDownProcessor;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader;

import java.lang.Process;
import java.lang.Runtime;


public class Export{

        // Syntax hilighting
        String prismJS = "./resources/javaScript/prism.js";
        String prismCSS = "./resources/css/prism.css";

        /**
         * asPlainText - gets the pain text of a note 
         * @param: note - note to get plain text from 
         * @param: notebook - notebook   
         * @param: saveLocation - location to save to 
         * @return: void
         */
        public void asPlainText(Note note, Notebook notebook, File saveLocation){

                try  {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(saveLocation));
                        // writing out the file
                        bw.write(note.getContent(note.getTitle(), notebook.getLocation()));
                        //closing the file
                        bw.close();
                }

                catch (IOException e) {
                        e.printStackTrace();
                }
        }

        /**
         * asHTML - gets the html of a note 
         * @param: note - note to get plain text from 
         * @param: notebook - notebook   
         * @param: saveLocation - location to save to 
         * @return: void
         */

        public void asHTML(Note note, Notebook notebook, File saveLocation){

                String html = getHTML(note, notebook);

                try  {
                        BufferedWriter bw = new BufferedWriter(new FileWriter(saveLocation));
                        // writing out the file
                        bw.write(html);
                        //closing the file
                        bw.close();
                }

                catch (IOException e) {
                        e.printStackTrace();
                }
        }

        /**
         * asHTML - gets the html of a note and converts it into pdf format 
         * @param: note - note to get plain text from 
         * @param: notebook - notebook   
         * @param: saveLocation - location to save to 
         * @return: void
         */
        public void asPDF(Note note, Notebook notebook, File saveLocation) throws IOException{
                String html = getHTML(note, notebook);
                // using bash command to inteface with phantomjs
                runBash("echo " + "\"" + html + "\"" + " | phantomjs ./resources/javaScript/PdfMaker.js > " + saveLocation.toString());
        }

        /**
         * getHTML - converts markdown to HTML 
         * @param: note - note to get plain text from 
         * @param: notebook - notebook   
         * @return: String - HTML content 
         */

        public String getHTML(Note note, Notebook notebook){

                String html = null;

                // gettign HTML from note
                try{
                        html =  getRequiredDependencies() +
                                new Markdown4jProcessor().process(note.getContent(note.getTitle(), notebook.getLocation()));  
                }
                catch (Exception e){
                }
                return html;
        }

        /**
         * getRequiredDependencies - adding required HMTL dependencies
         * for correct display  
         * @return: String - formated HTML content 
         */

        private String getRequiredDependencies(){

                // addding the required dependencies to the html
                String prismDependencies = "<link href=\"" + getClass().getResource(prismCSS) + "\"" + " rel=\"stylesheet\"" + " type=\"text/css\"" +  " />\n" + 
                        "<script src=\"" + getClass().getResource(prismJS) + "\"" + " type=\"text/javascript\"" + "></script>\n"; 

                // retuning the html dependencies
                return prismDependencies;

        }

        /**
         * runBash - run bash command 
         * @param: runCommand - command to be ran 
         * @return: void 
         */

        public void runBash(String runCommand){
                String result = null;
                try {
                        Runtime r = Runtime.getRuntime();                    

                        // executing bash command
                        String[] command = {"bash","-c",runCommand};
                        Process p = r.exec(command);

                        BufferedReader in =
                                new BufferedReader(new InputStreamReader(p.getInputStream()));
                        String inputLine;

                        // gettign output
                        while ((inputLine = in.readLine()) != null) {
                                System.out.println(inputLine);
                                result += inputLine;
                        }
                        in.close();

                } catch (IOException e) {
                        System.out.println(e);
                }

        }
}
