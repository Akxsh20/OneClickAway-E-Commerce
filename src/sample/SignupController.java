package sample;

import javafx.application.Platform;
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
import java.sql.Statement;

public class SignupController {

    @FXML
    private TextField firstnameTF;

    @FXML
    private TextField lastnameTF;

    @FXML
    private TextField usernameTF;

    @FXML
    private PasswordField passwordTF;

    @FXML
    private PasswordField confirmPassTF;

    @FXML
    private Button signupFrameButton;

    @FXML
    Button GobackButton;

    @FXML
    private Label passwordMatchLabel;

    @FXML
    private Label signupmessageLabel;

    @FXML
    private Label passwordLabel;
    @FXML
    private AnchorPane SignupPane;

// cancel button
    public void GobackButtonOnAction(javafx.event.ActionEvent event) {

        Stage stage = (Stage) GobackButton.getScene().getWindow();
        stage.close();

        try{
            //on Cancel it will load the Login page back on

            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Stage registerStage = new Stage();
            registerStage.setTitle("OCA");
            registerStage.setScene(new Scene(root, 760, 519));
            registerStage.resizableProperty().setValue(false);
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.show();
            SignupPane.getScene().getWindow().hide();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

        //Platform.exit();

    }


    public void signupFrameButtonOnAction(ActionEvent event){

        if(firstnameTF.getText().isBlank()==false && lastnameTF.getText().isBlank()==false && usernameTF.getText().isBlank() == false && passwordTF.getText().isBlank() == false && confirmPassTF.getText().isBlank()==false) {
            if (passwordTF.getText().equals(confirmPassTF.getText())) {
                registerUser();
                //passwordLabel.setText("you are set");
            }
            else{
                animations passShaker = new animations(passwordTF);
                animations confirmpassShaker = new animations(confirmPassTF);
                passShaker.shake();
                confirmpassShaker.shake();
                passwordMatchLabel.setText("Password Doesn't Match");
            }
        }
        else{
            passwordMatchLabel.setText("Please fill all details");
        }

    }

    public void registerUser(){

        //linking java to database
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        //Retrieving signup values from the Signup Page
        String firstname= firstnameTF.getText();
        String lastname = lastnameTF.getText();
        String ussername=usernameTF.getText();
        String password=passwordTF.getText();

        // Inserting these values into DB
        String insertFields="INSERT INTO account_login(lastname,firstname,ussername,password) VALUES ('";
        String insertValues= firstname + "','" + lastname + "','" + ussername + "','" + password + "')";
        String insertToSignup= insertFields+insertValues;

        // inside the try , the actual updating / writing process is being executed.

        try{
            //Creating an SQL statement
            Statement statement = connectDB.createStatement();

            //Executes the given SQL statement, which may be an INSERT, UPDATE, or DELETE statement
            // Passing the string containing The data I want to insert into my DB
            statement.executeUpdate(insertToSignup);
            signupmessageLabel.setText("User has been signed up Successfully");
        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }
}
