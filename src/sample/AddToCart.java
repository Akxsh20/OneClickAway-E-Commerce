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

public class AddToCart {

    @FXML
    private ImageView ProductImage;

    @FXML
    private Label ProductDetails;

    @FXML
    private Label ProductBrand;

    @FXML
    private Button AddToCartButton;

    @FXML
    private ImageView RupeeSymbol;

    @FXML
    private Label PriceTag;


    public void setData( Clothes clothe){
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(clothe.getImageSrc())));
        ProductImage.setImage(image);

        Image image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream(clothe.getImageSrc2())));
        RupeeSymbol.setImage(image1);


        ProductDetails.setText(clothe.getName());
        ProductBrand.setText(clothe.getBrand());
        PriceTag.setText(String.valueOf(clothe.getPrice()));
    }

    @FXML
    private void AddtocartAction(ActionEvent event){

        if(event.getSource() == AddToCartButton){
            AddToCart();
            JOptionPane.showMessageDialog(null,"Item added to cart");
            System.out.println(PriceTag.getText());
            System.out.println(ProductDetails.getText());


        }
    }

    Connection connectDB = null;

    public void AddToCart() {

        DatabaseConnection connectNow = new DatabaseConnection();
        connectDB = connectNow.getConnection();

        String name = ProductDetails.getText();
        String By = ProductBrand.getText();
        int price = Integer.parseInt(PriceTag.getText());

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
