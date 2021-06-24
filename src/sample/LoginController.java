package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController {

    @FXML
    private TextField UsernameTF;
    @FXML
    private PasswordField PasswordTF;
    @FXML
    private Button LoginButton;
    @FXML
    Button SignupButton;
    @FXML
    private Button CancelButton;
    @FXML
    private Label LoginMessageLabel;
    @FXML
    private AnchorPane LoginPane;



    public void LoginButtonOnAction(ActionEvent event){

        if (UsernameTF.getText().isBlank() == false && PasswordTF.getText().isBlank() == false) {

            HoverAnimation Loginhover= new HoverAnimation(LoginButton);
            Loginhover.Hover();

            validateLogin();

        } else {
            LoginMessageLabel.setText("Please enter Username & Password");
        }
    }


    // Method for exiting the application
    public void CancelButtonOnAction(javafx.event.ActionEvent event) {

        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
    }


    public void validateLogin(){

        // creating obj of class DatabaseConnection to use its methods.
        DatabaseConnection connectNow = new DatabaseConnection();

        // The DBlink is now in ConnectDB, since return value of getconnection() is return DBlink;
        Connection connectDB = connectNow.getConnection();

        // storing the MySQL statement that we need to verify later
        String verifyLogin = "select count(1) FROM account_login WHERE ussername='" + UsernameTF.getText() + "' and password='" + PasswordTF.getText() + "'";

        try {
            /*Creates a Statement object for sending SQL statements to the database.
             SQL statements without parameters are normally executed using Statement objects
             */
            Statement statement = connectDB.createStatement();

            //Executes the given SQL statement, which returns a single ResultSet object
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()) { // .next() helps us visit every row of the Resultset objrct
                if (queryResult.getInt(1) == 1) {
                    LoginMessageLabel.setText("Welcome!!");
                    MainPage();

                } else {
                    animations usernameShaker = new animations(UsernameTF);
                    animations PasswordShaker = new animations(PasswordTF);
                    usernameShaker.shake();
                    PasswordShaker.shake();

                    LoginMessageLabel.setText("Invalid login Please try again");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }

    private void MainPage() {
        try{
            // Connecting login controller class to Main Page

            Parent root = FXMLLoader.load(getClass().getResource("Shop.fxml"));
            Stage mainStage = new Stage();
            mainStage.setTitle("OCA");
            mainStage.setScene(new Scene(root, 1235, 670));
            mainStage.resizableProperty().setValue(false);
            mainStage.initStyle(StageStyle.UNDECORATED);
            mainStage.show();
            LoginPane.getScene().getWindow().hide();


        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }


    public void SignupButtonOnAction(ActionEvent event){
        createAccount();
    }
    public void createAccount(){
        try{
            //Connection of Login controller to Signup controller

            Parent root = FXMLLoader.load(getClass().getResource("Signup.fxml"));
            Stage registerStage = new Stage();
            registerStage.setTitle("OCA");
            registerStage.setScene(new Scene(root, 904, 604));
            registerStage.resizableProperty().setValue(false);
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.show();
            LoginPane.getScene().getWindow().hide();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }


}
