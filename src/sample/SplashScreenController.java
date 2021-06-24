package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SplashScreenController implements Initializable {

    @FXML
    private Label loadLabel;
    @FXML
    private ProgressBar loadingBar;
    @FXML
    private AnchorPane splashPane;

    public void initialize(URL url,ResourceBundle rb){

        new SplashScreen().start();
    }

    // creation of thread so that JVM understand that after Main.
    // java it has to run this class automatically
    class SplashScreen extends Thread{
        private static Label loadLabel;
        @Override
        // run() is a predefined method which allows Jvm to recognise that these statements have to be executed.
        public void run()
        {
            // try and catch they help in Exception Handling
            try{
                Thread.sleep(5000);
                //makes the splashscreen Sleep for 5 seconds

                Platform.runLater(new Runnable() {
    /*Platform.runLater: If you need to update a GUI component from a non-GUI thread,
    you can use that to put your update in a queue and it will be handled by the GUI thread as soon as possible.
                */
                    @Override
                    public void run() {

                        // Basic commands to switch between Scenes from line 54 to 70

                        Parent root = null;
                        try {
                            root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                        } catch (IOException e) {
                            Logger.getLogger(SplashScreenController.class.getName()).log(Level.SEVERE,null,e);
                        }
                        Stage  stage = new Stage();
                        Scene scene= new Scene(root);
                        stage.setScene(scene);

                        //to hide the maximize minimize & exit button
                        stage.resizableProperty().setValue(false);
                        stage.initStyle(StageStyle.UNDECORATED);

                        stage.show();
                        splashPane.getScene().getWindow().hide();
                    }
                });
            } catch (InterruptedException e) {
                Logger.getLogger(SplashScreenController.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
}
