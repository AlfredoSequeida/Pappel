import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class Filewalker {

        /**
         * Filewalker contructor
         */
        public Filewalker(){
        }

        /**
         * walk - walk through the files in a specified directory
         * @param : path - The file path to walk through
         * @return : List <String> - list of files found in the specified 
         * directory 
         */
        public List <String> walk( String path ) {

                // array to save files
                List <String> files = new ArrayList <String>();

                // root directory to use
                File root = new File( path );
                File[] list = root.listFiles();

                if (list == null) return files;

                // going through all directories
                for ( File f : list ) {
                        if ( f.isDirectory() ) {
                                walk( f.getAbsolutePath() );
                                files.add (f.getName());
                        }
                        else {
                                files.add(f.getName());
                        }
                }
                // returning array of files found
                return files;
        }

        /**
         * walkFolders - walk through the folders in a specified directory
         * @param : path - The file path to walk through
         * @return : List <String> - list of folders found in the specified 
         * directory 
         */

        public List <String> walkFolders (String path){

                // array to save files
                List <String> files = new ArrayList <String>();

                // root directory to use
                File root = new File( path );
                File[] list = root.listFiles();

                if (list == null) return files;

                // going through all directories
                for ( File f : list ) {
                        if ( f.isDirectory() ) {
                                files.add (f.getName());
                        }
                }
                // returning array of files found
                return files;

        }
}
