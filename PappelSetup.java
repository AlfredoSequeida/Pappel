import java.io.File;

public class PappelSetup{

        final private String PAPPELDIR = System.getProperty("user.home") +            
                "/Documents/Pappel/";

        /**
         * setupPappelDir - setting up Pappel's directory 
         * @return: void
         */

        public void setupPappelDir(){

                File pappelDir = new File(PAPPELDIR);

                // if the directory does not exist, create it
                if (!pappelDir.exists()) {
                        boolean result = false;

                        try{
                                pappelDir.mkdir();
                                result = true;
                        } 
                        catch(SecurityException se){
                                //handle it
                        }        
                        if(result) {    
                                System.out.println("DIR created");  
                        }
                }

        }
}

