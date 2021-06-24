package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Objects;

public class WomensPageController {


    @FXML
    private ImageView ProdImage;

    @FXML
    private Label ProdDetails;

    @FXML
    private Label ProdBrand;

    @FXML
    private Label priceLabel;

    @FXML
    private ImageView RupeeSymbol;

    @FXML
    private Button AddToCartButton;



    public void setData( WomenClothe woman){
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(woman.getImageSrcWomen())));
        ProdImage.setImage(image);

        Image image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream(woman.getImageSrc2())));
        RupeeSymbol.setImage(image1);

        ProdDetails.setText(woman.getNamewomen());
        ProdBrand.setText(woman.getBrand());
        priceLabel.setText(String.valueOf(woman.getPrice()));
    }

    ShopController sh=new ShopController();
    @FXML
    private void AddtocartAction(ActionEvent event){

        if(event.getSource() == AddToCartButton){

            addToTable();
            JOptionPane.showMessageDialog(null,"Item added to cart");
            System.out.println(priceLabel.getText());
            System.out.println(ProdDetails.getText());
        }
    }

    Connection connectDB= null;

    public void addToTable(){


        DatabaseConnection connectNow = new DatabaseConnection();
        connectDB = connectNow.getConnection();

        String name = ProdDetails.getText();
        String By = ProdBrand.getText();
        int price = Integer.parseInt(priceLabel.getText());

        String insertFields="INSERT INTO shoppingcart(ProdName, ProdBy,Price) VALUES ('";
        String insertValues= name + "','" + By + "','" + price + "')";
        String insertToTable= insertFields+insertValues;

        // inside the try , the actual updating / writing process is being executed.

        try{
            //Creating an SQL statement
            Statement statement = connectDB.createStatement();

            //Executes the given SQL statement, which may be an INSERT, UPDATE, or DELETE statement
            // Passing the string containing The data I want to insert into my DB
            statement.executeUpdate(insertToTable);
            System.out.println("Item added to cart Successfully");
        }
        catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

}
