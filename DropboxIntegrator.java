import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

import javafx.stage.Stage;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;
import com.dropbox.core.DbxWriteMode;


/**
 * @author: Alfredo Sequeida
 * @PID: A91111755
 * 
 */
public class DropboxIntegrator 
{
  String accessToken = "";
  // setting dropbox app and secret keys
  final String DROP_BOX_APP_KEY = "ccmgjawkdhac8wk";   
  final String DROP_BOX_APP_SECRET = "7slqdhins7lsbv6";
  // making new DbxRequestConfig
  DbxRequestConfig reqConfig;

  /**
   * DropboxIntegrator constructor
   *
   */
  public DropboxIntegrator(){
  }

  /**
   * authenticate method - authenticate with dropbox
   * @return: void
   */
  public void authenticate() throws IOException, DbxException{
    // using keys for app information
    DbxAppInfo dbxAppInfo = new DbxAppInfo(DROP_BOX_APP_KEY,
        DROP_BOX_APP_SECRET);

    this.reqConfig = new DbxRequestConfig("Pappel",
        Locale.getDefault().toString());
    DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(reqConfig,
        dbxAppInfo);



    // instructing the user how to connect with dropbox
    String authorizeUrl = webAuth.start();
    WebAction webAction = new WebAction(1, authorizeUrl);
    Stage stage = new Stage();
    webAction.start(stage);

    System.out.println("webAction finished");

    PappelSettings pappelSettings = new PappelSettings();
    String code = pappelSettings.loadPreference("dropBoxAuthCode");

    System.out.println("code done");

    // authenticating with dropbox sever
    DbxAuthFinish authFinish = webAuth.finish(code);
    System.out.println("authenticated");
    this.accessToken = authFinish.accessToken;
    System.out.println("done");
  }

  /**
   * push - push files to dropbox
   * @param : fileToPush - file to be uploaded
   * @return : void
   */
  public void push (String fileToPush) throws IOException, DbxException
  {

    System.out.println("pushing" + fileToPush);      
    PappelSettings pappelSettings = new PappelSettings();
    this.accessToken = pappelSettings.loadPreference("dropBoxAuthCode");

    System.out.println(this.accessToken);

    DbxClient client = new DbxClient(this.reqConfig, this.accessToken);

    // account name information
    System.out.println("account name is : " + client.getAccountInfo().
        displayName);

    // upload ing file to dropbox
    File inputFile = new File(fileToPush);
    FileInputStream inputStream = new FileInputStream(inputFile);
    try {

      DbxEntry.File uploadedFile = client.uploadFile(fileToPush,
          DbxWriteMode.add(), inputFile.length(), inputStream);
      String sharedUrl = client.createShareableUrl(fileToPush);
      System.out.println("Uploaded: " + uploadedFile.toString() + " URL " +
          sharedUrl);
    } finally {
      // closing input stream
      inputStream.close();
    }
  }
}
