import java.io.File;

public class Notebook{

  // location so save notebooks
  final private String PAPPELDIR = System.getProperty("user.home") +
    "/Documents/Pappel/";

  public static String title = "";
  File notebook;

  /**
   * Notebook contructor
   */
  public Notebook(){
    // default notebook name;
    this.title = "Unnamed"; 
    System.out.println("default notebook");
  }

  /**
   * make - make a new notebook
   * @return: void
   */
  public void make (){

    // setting notebook name
    notebook = new File (PAPPELDIR + "/" + this.title);

    //creating a new notebook if the notebook does not already exist
    if (!notebook.exists()) {
      boolean result = false;

      try{
        // making a new notebook
        notebook.mkdir();
        result = true;
      } 
      catch(SecurityException se){
        //handle it
      }        
      if(result) {    
        // letting use know that the notebook was created
        System.out.println("DIR created");  
      }
    }
  }

  /**
   * setTitle - set the title of a notebook
   * @param: title - the tile of the note to set
   * @return: void
   */
  public void setTitle(String title){
    // setting the title of the notebook
    this.title = title;
  }

  /**
   * getTitle - Get the title of  a notebook
   * @return: String - the title of the notebook
   */
  public String getTitle(){
    // getting the title of the notebook
    String notebookTitle = this.title;
    return notebookTitle;
  }

  /**
   * getTitle - Get the location of a notebook
   * @return: String - the location of the notebook
   */

  public String getLocation(){
          // getting location of current note
          String Location = (PAPPELDIR + this.title + "/"); 
          return Location;
  }
}
