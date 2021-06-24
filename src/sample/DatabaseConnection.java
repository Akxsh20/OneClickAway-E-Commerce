package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// creating a class to link database with my sql and vice-versa.

public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection() {

        String databaseName = "oca";
        String databaseUser = "root";
        String databasePassword = "kumar";
        String url = "jdbc:mysql://localhost/" + databaseName;

        try {
            /* 1st step to connect DB, you have to specify which database driver I'm going to use to connect the DB
            I'm doing it by directly sending the driver class name to the forname() method.
             */

            Class.forName("com.mysql.cj.jdbc.Driver");

            /* Connecting to DB, Will need 3 things:
            1.Url of DB
            2. Username of DB
            3. Password of DB
             */
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);


        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        return databaseLink;

    }

//    public ObservableList<ShopController.cart> getDataCart(){
//
//        DatabaseConnection connectNow = new DatabaseConnection();
//        Connection connectDB = connectNow.getConnection();
//        ObservableList<ShopController.cart> list= FXCollections.observableArrayList();
//
//        try{
//            PreparedStatement ps = connectDB.prepareStatement("Select * from shoppingcart");
//            ResultSet rs = ps.executeQuery();
//
//            while(rs.next()){
//                list.add(new ShopController.cart(Integer.parseInt(rs.getString("id")),Integer.parseInt(rs.getString("price")),rs.getString("prodDetails"),rs.getString("ProdBrand")));
//            }
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        return list;
//    }

}
