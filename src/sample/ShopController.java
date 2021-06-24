package sample;

import com.sun.xml.internal.bind.v2.model.core.ID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShopController implements Initializable {

    @FXML
    private Button CancelButton;
    @FXML
    private AnchorPane query_pane;
    @FXML
    private Button queryButton;
    @FXML
    private Button buttonBack;
    @FXML
    private Button questionmarkButton;
    @FXML
    private AnchorPane AboutProjPane;
    @FXML
    private Button Goback;
    @FXML
    private AnchorPane MenPane;
    @FXML
    private Button MenButton;
    @FXML
    private GridPane ClotheContainer;
    @FXML
    private Button WomenButton;
    @FXML
    private Button KidsButton;
    @FXML
    private Button HomeLivingButton;
    @FXML
    //WOMENS PORTION:
    private AnchorPane WomensPane;
    @FXML
    private VBox WoMenVbox;
    @FXML
    private GridPane HoodieGridW;
    @FXML
    private GridPane TeesGridW;
    @FXML
    private GridPane JacketGridW;
    @FXML
    private GridPane BottomsGridW;
    @FXML
    private GridPane ShoesGridW;
    @FXML
    private GridPane WatchGridW;
    @FXML
    private GridPane BagGridW;
    @FXML
    private AnchorPane MainPane;
    @FXML
    private Button Sale;
    @FXML
    private Button AccesoriesButton;
    @FXML
    private HBox OfferLayout;
    @FXML
    //KIDS
    private AnchorPane kidsPane;
    @FXML
    private VBox KidsVbox;
    @FXML
    private GridPane HoodieGridkids;
    @FXML
    private GridPane TeesGridkids;
    @FXML
    private GridPane JacketGridkids;
    @FXML
    private GridPane JeansGridkids;
    @FXML
    private GridPane ShoesGridkids;
    @FXML
    private GridPane WatchGridkids;
    @FXML
    //HOME LIVING:
    private AnchorPane homeLivingPane;
    @FXML
    private VBox HomeLivingVbox;
    @FXML
    private GridPane chairGrid;
    @FXML
    private GridPane SofasGrid;
    @FXML
    private GridPane CarpetGrid;
    @FXML
    private GridPane DinnerTableGrid;
    @FXML
    private GridPane LampsGrid;
    @FXML
    private GridPane SideTableGrid;
    //ACCESSORIES
    @FXML
    private AnchorPane AccessoriePane;
    @FXML
    private VBox AccessoriesVbox;
    @FXML
    private GridPane MakeupGrid;
    @FXML
    private GridPane SkincareGrid;
    @FXML
    private GridPane HaircareGrid;
    @FXML
    private GridPane SmartWearablesGrid;
    @FXML
    private GridPane Fragrances;
    @FXML
    private GridPane Appliances;
    @FXML
    //MEN
    private VBox MenVbox;
    @FXML
    private GridPane TeesGridM;
    @FXML
    private GridPane JacketsGridM;
    @FXML
    private GridPane BottomGridM;
    @FXML
    private GridPane ShoesGridM;
    @FXML
    private GridPane WatchGridM;
    @FXML
    private AnchorPane AddtoCartPane;

    @FXML
    private Button BackButton;

    @FXML
    private Button PlaceorderButton;

    @FXML
    private Button ContinuShoppingButton;

    @FXML
    private Button ShoppingCartButton;

    @FXML
    private Label placeorderLabel;

    @FXML
    private TableView<CartTable> tablecart;

    @FXML
    private TableColumn<CartTable, Integer> id;

    @FXML
    private TableColumn<CartTable, String> prodDetails;

    @FXML
    private TableColumn<CartTable, String> ProductBrand;

    @FXML
    private TableColumn<CartTable, Integer> productPrice;

    @FXML
    private Button deleteitemButton;
    @FXML
    private Button refreshCart;
    @FXML
    private Label ConfirmationLabel;


    String query = null;
    Connection connectDB= null;
    PreparedStatement preparedStatement=null;
    ResultSet resultSet = null;


    @FXML
    private ObservableList<CartTable> Cartdata(){
        query = "SELECT * FROM shoppingcart";
        ObservableList<CartTable> Cartlist = FXCollections.observableArrayList();
        try {
            preparedStatement=connectDB.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){

                Cartlist.add(new CartTable(resultSet.getInt("idCart"), resultSet.getString("ProdName")
                        , resultSet.getString("ProdBy"), resultSet.getInt("Price")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Cartlist;
    }

    @FXML
    private void RefreshButtonAction(ActionEvent event) {
        loadData();
    }

    public void setlabelConfirm(){
        ConfirmationLabel.setVisible(true);
        ConfirmationLabel.setText("Item added to cart");
    }

    @FXML
    private void deletebuttonAction(ActionEvent event){
        deleteItem();
        ObservableList<CartTable> allproducts,singleproduct;
        allproducts=tablecart.getItems();
        singleproduct=tablecart.getSelectionModel().getSelectedItems();
        singleproduct.forEach(allproducts::remove);
    }

    public void deleteItem(){
        DatabaseConnection connectNow = new DatabaseConnection();
        connectDB = connectNow.getConnection();

        query="DELETE FROM shoppingcart WHERE idCart =?";
        try{

            preparedStatement = connectDB.prepareStatement(query);
            preparedStatement.setString(1,String.valueOf(CartTable.getIdCart()));
            preparedStatement.execute();
            loadData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }



    public void loadData(){
        DatabaseConnection connectNow = new DatabaseConnection();
        connectDB = connectNow.getConnection();

        id.setCellValueFactory(new PropertyValueFactory<>("idCart"));
        prodDetails.setCellValueFactory(new PropertyValueFactory<>("ProdName"));
        ProductBrand.setCellValueFactory(new PropertyValueFactory<>("ProdBy"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        tablecart.setItems(Cartdata());

    }




    @FXML
    private void handleButtonAction(ActionEvent event){

        if(event.getSource() == queryButton){
            if(MenPane.visibleProperty().getValue()== true || WomensPane.visibleProperty().getValue()==true||MainPane.visibleProperty().getValue()==true
                || kidsPane.visibleProperty().getValue()==true || homeLivingPane.visibleProperty().getValue()==true
                    || AccessoriePane.visibleProperty().getValue()==true){
                MenPane.setVisible(false);
                WomensPane.setVisible(false);
                MainPane.setVisible(false);
                homeLivingPane.setVisible(false);
                AccessoriePane.setVisible(false);
                kidsPane.setVisible(false);
            }
            query_pane.setVisible(true);
        }
        else if(event.getSource()==buttonBack){
            query_pane.setVisible(false);
            MainPane.setVisible(true);

        }
    }

    @FXML
    private void addtocartButtonAction(ActionEvent event){
        if(event.getSource() == ShoppingCartButton){
            if(MenPane.visibleProperty().getValue()== true || WomensPane.visibleProperty().getValue()==true||MainPane.visibleProperty().getValue()==true
                    || kidsPane.visibleProperty().getValue()==true || homeLivingPane.visibleProperty().getValue()==true
                    || AccessoriePane.visibleProperty().getValue()==true){
                MenPane.setVisible(false);
                WomensPane.setVisible(false);
                MainPane.setVisible(false);
                homeLivingPane.setVisible(false);
                AccessoriePane.setVisible(false);
                kidsPane.setVisible(false);
            }
            AddtoCartPane.setVisible(true);
        }
        else if(event.getSource()==BackButton){
            AddtoCartPane.setVisible(false);
            MainPane.setVisible(true);

        }
        else if(event.getSource()==ContinuShoppingButton){
            AddtoCartPane.setVisible(false);
            MainPane.setVisible(true);
        }
    }



    @FXML
    private void QuestionButtonAction(ActionEvent event){

        if(event.getSource() == questionmarkButton){
            AboutProjPane.setVisible(true);

        }
        else if(event.getSource()==Goback){
            AboutProjPane.setVisible(false);
        }
    }


    @FXML
    private void PaneAction(ActionEvent event){

        // This method is for Switching between panes within the same Scene.

        if(event.getSource() == MenButton){
            MenPane.setVisible(true);
            WomensPane.setVisible(false);
            MainPane.setVisible(false);
            homeLivingPane.setVisible(false);
            AccessoriePane.setVisible(false);
            kidsPane.setVisible(false);

            //new Menpage().run();
        }
        else if(event.getSource()==WomenButton){
            MainPane.setVisible(false);
            MenPane.setVisible(false);
            homeLivingPane.setVisible(false);
            AccessoriePane.setVisible(false);
            kidsPane.setVisible(false);
            WomensPane.setVisible(true);

//            new Women().run();
        }
        else if(event.getSource()==Sale){
            MenPane.setVisible(false);
            WomensPane.setVisible(false);
            homeLivingPane.setVisible(false);
            AccessoriePane.setVisible(false);
            kidsPane.setVisible(false);
            MainPane.setVisible(true);

        }
        else if(event.getSource()==KidsButton){
            MenPane.setVisible(false);
            WomensPane.setVisible(false);
            MainPane.setVisible(false);
            homeLivingPane.setVisible(false);
            AccessoriePane.setVisible(false);
            kidsPane.setVisible(true);


        }
        else if(event.getSource()==AccesoriesButton){
            MenPane.setVisible(false);
            WomensPane.setVisible(false);
            MainPane.setVisible(false);
            homeLivingPane.setVisible(false);
            kidsPane.setVisible(false);
            AccessoriePane.setVisible(true);


        }
        else if(event.getSource()==HomeLivingButton){
            MenPane.setVisible(false);
            WomensPane.setVisible(false);
            MainPane.setVisible(false);
            kidsPane.setVisible(false);
            AccessoriePane.setVisible(false);
            homeLivingPane.setVisible(true);

            new HomeLiving().run();

        }

    }
////////////////////////////////////////////////// MEN Threads /////////////////////////////////////////////////////////

//    class hoodiemen extends Thread{
//        @Override
//        public void run()
//        {
//
//        }
//    }
//
//    class teeM extends Thread{
//        @Override
//        public void run(){
//
//        }
//    }
//
//    class jMen extends Thread{
//        @Override
//        public void run(){
//
//        }
//    }
//
//    class shoemen extends Thread{
//        @Override
//        public void run(){
//
//        }
//    }
//
//    class bottomM extends Thread{
//        @Override
//        public void run(){
//
//        }
//    }
//
//    class watchM extends Thread{
//        @Override
//        public void run(){
//
//        }
//    }
//
//    class Menpage extends Thread{
//        @Override
//        public void run() {
//            try{
//                new shoemen().run();
//                new teeM().run();
//                new jMen().run();
//                new watchM().run();
//                new bottomM().run();
//                new hoodiemen().run();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//    }

////////////////////////////////////////////////////// WOMEN PAGE //////////////////////////////////////////////////////
//    class womenHoodie extends Thread{
//        @Override
//        public void run(){
//
//        }
//    }
//
//    class BagWomen extends Thread{
//        @Override
//        public void run(){
//
//        }
//    }
//    class BottomWomen extends Thread{
//        @Override
//        public void run(){
//
//        }
//    }
//    class teeWomen extends Thread{
//        @Override
//        public void run(){
//
//        }
//    }
//    class shoeWomen extends Thread{
//        @Override
//        public void run(){
//
//        }
//    }
//    class watchWomen extends Thread{
//        @Override
//        public void run(){
//
//        }
//    }
//
//    class jacketWomen extends Thread{
//        @Override
//        public void run(){
//
//        }
//    }


    class Women implements Runnable {
        @Override
        public void run(){
            try {
//                new womenHoodie().run();
//                new watchWomen().run();
//                new shoeWomen().run();
//                new jacketWomen().run();
//                new teeWomen().run();
//                new BottomWomen().run();
//                new BagWomen().run();

                HoodiesWomen = new ArrayList<>(WomenHoodies());
                int WR4 = 1, WC4 = 0;
                try{
                    for (WomenClothe HoodiesW : HoodiesWomen) {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("WomensPageController.fxml"));
                        WoMenVbox = fxmlLoader.load();
                        WomensPageController clotheContainer = fxmlLoader.getController();
                        clotheContainer.setData(HoodiesW);
                        if (WC4 == 4) {
                            WC4 = 0;
                            ++WR4;
                        }
                        HoodieGridW.add(WoMenVbox, WC4++, WR4);
                        GridPane.setMargin(WoMenVbox, new Insets(10));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("error in Hoodie women");
                }

                BagsWomen = new ArrayList<>(WomenBag());
                int WR1 = 1, WC1 = 0;
                try{


                    for (WomenClothe BagW : BagsWomen) {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("WomensPageController.fxml"));
                        WoMenVbox = fxmlLoader.load();
                        WomensPageController clotheContainer = fxmlLoader.getController();
                        clotheContainer.setData(BagW);
                        if (WC1 == 4) {
                            WC1 = 0;
                            ++WR1;
                        }
                        BagGridW.add(WoMenVbox, WC1++, WR1);
                        GridPane.setMargin(WoMenVbox, new Insets(10));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println();
                    System.out.println("error in bags women");
                }



            BottomWomen = new ArrayList<>(WomenBottom());
            int WR2 = 1, WC2 = 0;
            try{
                for (WomenClothe BottomW : BottomWomen) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("WomensPageController.fxml"));
                    WoMenVbox = fxmlLoader.load();
                    WomensPageController clotheContainer = fxmlLoader.getController();
                    clotheContainer.setData(BottomW);
                    if (WC2 == 4) {
                        WC2 = 0;
                        ++WR2;
                    }
                    BottomsGridW.add(WoMenVbox, WC2++, WR2);
                    GridPane.setMargin(WoMenVbox, new Insets(10));
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error in bottom women");
            }

            TeesWomen = new ArrayList<>(WomenTshirt());
            int WR3 = 1, WC3 = 0;
            try{
                for (WomenClothe TeesW : TeesWomen) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("WomensPageController.fxml"));
                    WoMenVbox = fxmlLoader.load();
                    WomensPageController clotheContainer = fxmlLoader.getController();
                    clotheContainer.setData(TeesW);
                    if (WC3 == 4) {
                        WC3 = 0;
                        ++WR3;
                    }
                    TeesGridW.add(WoMenVbox, WC3++, WR3);
                    GridPane.setMargin(WoMenVbox, new Insets(10));
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error in tees women");
            }

            ShoesWomen = new ArrayList<>(WomenShoes());
            int WR6 = 1, WC6 = 0;
            try{
                for (WomenClothe ShoesW : ShoesWomen) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("WomensPageController.fxml"));
                    WoMenVbox = fxmlLoader.load();
                    WomensPageController clotheContainer = fxmlLoader.getController();
                    clotheContainer.setData(ShoesW);
                    if (WC6 == 4) {
                        WC6 = 0;
                        ++WR6;
                    }
                    ShoesGridW.add(WoMenVbox, WC6++, WR6);
                    GridPane.setMargin(WoMenVbox, new Insets(10));
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error in shoes women");
            }

            WatchesWomen = new ArrayList<>(WomenWatchesW());
            int WR5=1,WC5=0;

            try{
                for (WomenClothe WatchesW : WatchesWomen) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("WomensPageController.fxml"));
                    WoMenVbox = fxmlLoader.load();
                    WomensPageController clotheContainer = fxmlLoader.getController();
                    clotheContainer.setData(WatchesW);
                    if (WC5 == 4) {
                        WC5 = 0;
                        ++WR5;
                    }
                    WatchGridW.add(WoMenVbox, WC5++, WR5);
                    GridPane.setMargin(WoMenVbox, new Insets(10));
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("watches women error");
            }

            int WR8 = 1, WC8 = 0;
            JacketWomen = new ArrayList<>(WomenJackets());
            try{
                for (WomenClothe JacketsW : JacketWomen) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("WomensPageController.fxml"));
                    WoMenVbox = fxmlLoader.load();
                    WomensPageController clotheContainer = fxmlLoader.getController();
                    clotheContainer.setData(JacketsW);
                    if (WC8 == 4) {
                        WC8 = 0;
                        ++WR8;
                    }
                    JacketGridW.add(WoMenVbox, WC8++, WR8);
                    GridPane.setMargin(WoMenVbox, new Insets(10));
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("error in jacket women");
            }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    class Kids extends Thread{
        @Override
        public void run(){
            Hoodiekids = new ArrayList<>(Hoodiekid());
            Teeskids = new ArrayList<>(TeeKid());
            Jacketkids = new ArrayList<>(Jacketkid());
            Shoeskids = new ArrayList<>(Shoeskid());
            Bottomkids = new ArrayList<>(Bottomkid());
            Watchkids = new ArrayList<>(Watchkid());

            int KR1 = 1, KC1 = 0, KR2 = 1, KC2 = 0, KR3 = 1, KC3 = 0, KR4 = 1, KC4 = 0, KR5 = 1, KC5 = 0, KR6 = 1, KC6 = 0;

            try {

                for (WomenClothe Hoodiek : Hoodiekids) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("WomensPageController.fxml"));
                    KidsVbox = fxmlLoader.load();
                    WomensPageController clotheContainer = fxmlLoader.getController();
                    clotheContainer.setData(Hoodiek);
                    if (KC1 == 4) {
                        KC1 = 0;
                        ++KR1;
                    }
                    HoodieGridkids.add(KidsVbox, KC1++, KR1);
                    GridPane.setMargin(KidsVbox, new Insets(10));
                }

                for (WomenClothe Teesk : Teeskids) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("WomensPageController.fxml"));
                    KidsVbox = fxmlLoader.load();
                    WomensPageController clotheContainer1 = fxmlLoader.getController();
                    clotheContainer1.setData(Teesk);
                    if (KC2 == 4) {
                        KC2 = 0;
                        ++KR2;
                    }
                    TeesGridkids.add(KidsVbox, KC2++, KR2);
                    GridPane.setMargin(KidsVbox, new Insets(10));
                }

                for (WomenClothe Jacketk : Jacketkids) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("WomensPageController.fxml"));
                    KidsVbox = fxmlLoader.load();
                    WomensPageController clotheContainer2 = fxmlLoader.getController();
                    clotheContainer2.setData(Jacketk);
                    if (KC3 == 4) {
                        KC3 = 0;
                        ++KR3;
                    }
                    JacketGridkids.add(KidsVbox, KC3++, KR3);
                    GridPane.setMargin(KidsVbox, new Insets(10));
                }

                for (WomenClothe Shoek : Shoeskids) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("WomensPageController.fxml"));
                    KidsVbox = fxmlLoader.load();
                    WomensPageController clotheContainer2 = fxmlLoader.getController();
                    clotheContainer2.setData(Shoek);
                    if (KC4 == 4) {
                        KC4 = 0;
                        ++KR4;
                    }
                    ShoesGridkids.add(KidsVbox, KC4++, KR4);
                    GridPane.setMargin(KidsVbox, new Insets(10));
                }

                for (WomenClothe Watchesk : Watchkids) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("WomensPageController.fxml"));
                    KidsVbox = fxmlLoader.load();
                    WomensPageController clotheContainer2 = fxmlLoader.getController();
                    clotheContainer2.setData(Watchesk);
                    if (KC5 == 4) {
                        KC5 = 0;
                        ++KR5;
                    }
                    WatchGridkids.add(KidsVbox, KC5++, KR5);
                    GridPane.setMargin(KidsVbox, new Insets(10));
                }

                for (WomenClothe Bottomk : Bottomkids) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("WomensPageController.fxml"));
                    KidsVbox = fxmlLoader.load();
                    WomensPageController clotheContainer2 = fxmlLoader.getController();
                    clotheContainer2.setData(Bottomk);
                    if (KC6 == 4) {
                        KC6 = 0;
                        ++KR6;
                    }
                    JeansGridkids.add(KidsVbox, KC6++, KR6);
                    GridPane.setMargin(KidsVbox, new Insets(10));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }



    class HomeLiving implements Runnable{
        @Override
        public void run(){
            Chairs = new ArrayList<>(Chair());
            Sofas = new ArrayList<>(Sofa());
            Dining = new ArrayList<>(Dine());
            Lamps = new ArrayList<>(Lamp());
            Side_Tables = new ArrayList<>(SidTable());
            Carpets = new ArrayList<>(Carpet());


            int AR1 = 1, AC1 = 0, AR2 = 1, AC2 = 0, AR3 = 1, AC3 = 0, AR4 = 1, AC4 = 0, AR5 = 1, AC5 = 0, AR6 = 1, AC6 = 0;

            try {

                for (Clothes chair : Chairs) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("AddToCart.fxml"));
                    HomeLivingVbox = fxmlLoader.load();
                    AddToCart clotheContainer = fxmlLoader.getController();
                    clotheContainer.setData(chair);
                    if (AC1 == 4) {
                        AC1 = 0;
                        ++AR1;
                    }
                    chairGrid.add(HomeLivingVbox, AC1++, AR1);
                    GridPane.setMargin(HomeLivingVbox, new Insets(10));
                }

                for (Clothes sofa : Sofas) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("AddToCart.fxml"));
                    HomeLivingVbox = fxmlLoader.load();
                    AddToCart clotheContainer1 = fxmlLoader.getController();
                    clotheContainer1.setData(sofa);
                    if (AC2 == 4) {
                        AC2 = 0;
                        ++AR2;
                    }
                    SofasGrid.add(HomeLivingVbox, AC2++, AR2);
                    GridPane.setMargin(HomeLivingVbox, new Insets(10));
                }

                for (Clothes Dine : Dining) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("AddToCart.fxml"));
                    HomeLivingVbox = fxmlLoader.load();
                    AddToCart clotheContainer2 = fxmlLoader.getController();
                    clotheContainer2.setData(Dine);
                    if (AC3 == 4) {
                        AC3 = 0;
                        ++AR3;
                    }
                    DinnerTableGrid.add(HomeLivingVbox, AC3++, AR3);
                    GridPane.setMargin(HomeLivingVbox, new Insets(10));
                }

                for (Clothes lamp : Lamps) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("AddToCart.fxml"));
                    HomeLivingVbox = fxmlLoader.load();
                    AddToCart clotheContainer2 = fxmlLoader.getController();
                    clotheContainer2.setData(lamp);
                    if (AC4 == 4) {
                        AC4 = 0;
                        ++AR4;
                    }
                    LampsGrid.add(HomeLivingVbox, AC4++, AR4);
                    GridPane.setMargin(HomeLivingVbox, new Insets(10));
                }

                for (Clothes SideTable : Side_Tables) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("AddToCart.fxml"));
                    HomeLivingVbox = fxmlLoader.load();
                    AddToCart clotheContainer2 = fxmlLoader.getController();
                    clotheContainer2.setData(SideTable);
                    if (AC5 == 4) {
                        AC5 = 0;
                        ++AR5;
                    }
                    SideTableGrid.add(HomeLivingVbox, AC5++, AR5);
                    GridPane.setMargin(HomeLivingVbox, new Insets(10));
                }

                for (Clothes Carpet : Carpets) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("AddToCart.fxml"));
                    HomeLivingVbox = fxmlLoader.load();
                    AddToCart clotheContainer2 = fxmlLoader.getController();
                    clotheContainer2.setData(Carpet);
                    if (AC6 == 4) {
                        AC6 = 0;
                        ++AR6;
                    }
                    CarpetGrid.add(HomeLivingVbox, AC6++, AR6);
                    GridPane.setMargin(HomeLivingVbox, new Insets(10));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class Accessories extends Thread{
        @Override
        public void run(){
            Makeup = new ArrayList<>(makeup());
            SkinCare = new ArrayList<>(skincare());
            HairCare = new ArrayList<>(haircare());
            Fragrance = new ArrayList<>(fragrance());
            SmartWearables = new ArrayList<>(smartwearables());
            Appliance = new ArrayList<>(appliance());

            int HR1 = 1, HC1 = 0, HR2 = 1, HC2 = 0, HR3 = 1, HC3 = 0, HR4 = 1, HC4 = 0, HR5 = 1, HC5 = 0, HR6 = 1, HC6 = 0;


            try {

                for (Clothes makeup : Makeup) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("AddToCart.fxml"));
                    AccessoriesVbox = fxmlLoader.load();
                    AddToCart clotheContainer = fxmlLoader.getController();
                    clotheContainer.setData(makeup);
                    if (HC1 == 4) {
                        HC1 = 0;
                        ++HR1;
                    }
                    MakeupGrid.add(AccessoriesVbox, HC1++, HR1);
                    GridPane.setMargin(AccessoriesVbox, new Insets(10));
                }

                for (Clothes skin : SkinCare) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("AddToCart.fxml"));
                    AccessoriesVbox = fxmlLoader.load();
                    AddToCart clotheContainer1 = fxmlLoader.getController();
                    clotheContainer1.setData(skin);
                    if (HC2 == 4) {
                        HC2 = 0;
                        ++HR2;
                    }
                    SkincareGrid.add(AccessoriesVbox, HC2++, HR2);
                    GridPane.setMargin(AccessoriesVbox, new Insets(10));
                }

                for (Clothes hair : HairCare) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("AddToCart.fxml"));
                    AccessoriesVbox = fxmlLoader.load();
                    AddToCart clotheContainer2 = fxmlLoader.getController();
                    clotheContainer2.setData(hair);
                    if (HC3 == 4) {
                        HC3 = 0;
                        ++HR3;
                    }
                    HaircareGrid.add(AccessoriesVbox, HC3++, HR3);
                    GridPane.setMargin(AccessoriesVbox, new Insets(10));
                }

                for (Clothes fragrance : Fragrance) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("AddToCart.fxml"));
                    AccessoriesVbox = fxmlLoader.load();
                    AddToCart clotheContainer2 = fxmlLoader.getController();
                    clotheContainer2.setData(fragrance);
                    if (HC4 == 4) {
                        HC4 = 0;
                        ++HR4;
                    }
                    Fragrances.add(AccessoriesVbox, HC4++, HR4);
                    GridPane.setMargin(AccessoriesVbox, new Insets(10));
                }

                for (Clothes Watch : SmartWearables) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("AddToCart.fxml"));
                    AccessoriesVbox = fxmlLoader.load();
                    AddToCart clotheContainer2 = fxmlLoader.getController();
                    clotheContainer2.setData(Watch);
                    if (HC5 == 4) {
                        HC5 = 0;
                        ++HR5;
                    }
                    SmartWearablesGrid.add(AccessoriesVbox, HC5++, HR5);
                    GridPane.setMargin(AccessoriesVbox, new Insets(10));
                }

                for (Clothes appliance : Appliance) {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("AddToCart.fxml"));
                    AccessoriesVbox = fxmlLoader.load();
                    AddToCart clotheContainer2 = fxmlLoader.getController();
                    clotheContainer2.setData(appliance);
                    if (HC6 == 4) {
                        HC6 = 0;
                        ++HR6;
                    }
                    Appliances.add(AccessoriesVbox, HC6++, HR6);
                    GridPane.setMargin(AccessoriesVbox, new Insets(10));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


//    class mainPage extends Thread{
//        @Override
//        public void run(){
//
//
//
//        }
//    }





    @FXML
    private AnchorPane ShopPane;

    public void CancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
        try{
            //On closing shop Screen it will open up the Login Page

            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Stage registerStage = new Stage();
            registerStage.setTitle("OCA");
            registerStage.setScene(new Scene(root, 760, 519));
            registerStage.resizableProperty().setValue(false);
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.show();
            ShopPane.getScene().getWindow().hide();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    @FXML
    private WebView webView;


    @FXML
    private GridPane HoodieGridM;


    @FXML
    private WebEngine engine;


    private ArrayList<WomenClothe> HoodieMen;
    private ArrayList<WomenClothe> TeesMen;
    private ArrayList<WomenClothe> JacketM;
    private ArrayList<WomenClothe> ShoesM;
    private ArrayList<WomenClothe> BottomsM;
    private ArrayList<WomenClothe> WatchesM;

    private ArrayList<WomenClothe> BagsWomen;
    private ArrayList<WomenClothe> BottomWomen;
    private ArrayList<WomenClothe> TeesWomen;
    private ArrayList<WomenClothe> HoodiesWomen;
    private ArrayList<WomenClothe> ShoesWomen;
    private ArrayList<WomenClothe> WatchesWomen;
    private ArrayList<WomenClothe> JacketWomen;

    private List<WomenClothe> Hoodiekids;
    private List<WomenClothe> Teeskids;
    private List<WomenClothe> Jacketkids;
    private List<WomenClothe> Shoeskids;
    private List<WomenClothe> Bottomkids;
    private List<WomenClothe> Watchkids;

    private List<Clothes> Chairs;
    private List<Clothes> Carpets;
    private List<Clothes> Dining;
    private List<Clothes> Lamps;
    private List<Clothes> Side_Tables;
    private List<Clothes> Sofas;

    private List<Clothes> Makeup;
    private List<Clothes> SkinCare;
    private List<Clothes> HairCare;
    private List<Clothes> Fragrance;
    private List<Clothes> SmartWearables;
    private List<Clothes> Appliance;




    @Override
    public synchronized void  initialize(URL location, ResourceBundle resources) {

//        new mainPage().run();


        List<Clothes> offer = new ArrayList<>(offer());
        List<Clothes> recommended = new ArrayList<>(recom());


        System.out.println("recommended size " + recommended.size());

        int col = 0;
        int row = 1;
        try {
            for (Clothes value : offer) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("OfferCard.fxml"));
                HBox Offerbox = fxmlLoader.load();
                OfferCardController offerCardController = fxmlLoader.getController();
                offerCardController.setData(value);
                // Adding items to the Hbox
                OfferLayout.getChildren().add(Offerbox);

            }
            for (Clothes clothes : recommended) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("ClotheCont.fxml"));
                VBox Clothebox = fxmlLoader.load();
                ClotheContController clotheContainercontroller = fxmlLoader.getController();
                clotheContainercontroller.setData(clothes);
                // Adding items to the Vbox
                //OfferLayout.getChildren().add(Clothebox);
                if (col == 6) {
                    col = 0;
                    ++row;
                }
                ClotheContainer.add(Clothebox, col++, row);
                GridPane.setMargin(Clothebox, new Insets(10));

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        HoodieMen = new ArrayList<>(Hoodie_Mens());
        int R1 = 1, C1 = 0;
        try{
            for (WomenClothe HoodieM : HoodieMen) {
                FXMLLoader fxml = new FXMLLoader();
                fxml.setLocation(getClass().getResource("WomensPageController.fxml"));
                MenVbox = fxml.load();
                WomensPageController clotheContainer = fxml.getController();
                clotheContainer.setData(HoodieM);
                if (C1 == 4) {
                    C1 = 0;
                    ++R1;
                }
                HoodieGridM.add(MenVbox, C1++, R1);
                GridPane.setMargin(MenVbox, new Insets(10));
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in HoodieM");
        }

        TeesMen = new ArrayList<>(TeesMen());
        int R2 = 1, C2 = 0;
        try{
            for (WomenClothe TeesM : TeesMen) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("WomensPageController.fxml"));
                MenVbox = fxmlLoader.load();
                WomensPageController clotheContainer1 = fxmlLoader.getController();
                clotheContainer1.setData(TeesM);
                if (C2 == 4) {
                    C2 = 0;
                    ++R2;
                }
                TeesGridM.add(MenVbox, C2++, R2);
                GridPane.setMargin(MenVbox, new Insets(10));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in TeesMen");
        }

        int  RM3 = 1, CM3 = 0;
        JacketM = new ArrayList<>(JacketMen());
        try{
            for (WomenClothe Jacket : JacketM) {
                FXMLLoader fxmljacket = new FXMLLoader();
                fxmljacket.setLocation(getClass().getResource("WomensPageController.fxml"));
                MenVbox = fxmljacket.load();
                WomensPageController clotheContJacket = fxmljacket.getController();
                clotheContJacket.setData(Jacket);
                if (CM3 == 4) {
                    CM3 = 0;
                    ++RM3;
                }
                JacketsGridM.add(MenVbox, CM3++, RM3);
                GridPane.setMargin(MenVbox, new Insets(10));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in jacketmen");
        }

        int  R4 = 1, C4 = 0;
        ShoesM = new ArrayList<>(ShoesMen());
        try{
            for (WomenClothe Shoes : ShoesM) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("WomensPageController.fxml"));
                MenVbox = fxmlLoader.load();
                WomensPageController clotheContainer2 = fxmlLoader.getController();
                clotheContainer2.setData(Shoes);
                if (C4 == 4) {
                    C4 = 0;
                    ++R4;
                }
                ShoesGridM.add(MenVbox, C4++, R4);
                GridPane.setMargin(MenVbox, new Insets(10));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in shoesM");
        }

        int  R6 = 1, C6 = 0;
        BottomsM = new ArrayList<>(BottomMen());
        try{
            for (WomenClothe BottomMen : BottomsM) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("WomensPageController.fxml"));
                MenVbox = fxmlLoader.load();
                WomensPageController clotheContainer2 = fxmlLoader.getController();
                clotheContainer2.setData(BottomMen);
                if (C6 == 4) {
                    C6 = 0;
                    ++R6;
                }
                BottomGridM.add(MenVbox, C6++, R6);
                GridPane.setMargin(MenVbox, new Insets(10));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in bottomM");
        }

        int  R5 = 1, C5 = 0;
        WatchesM = new ArrayList<>(WatchesMen());
        try{
            for (WomenClothe WatchesMen : WatchesM) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("WomensPageController.fxml"));
                MenVbox = fxmlLoader.load();
                WomensPageController clotheContainer2 = fxmlLoader.getController();
                clotheContainer2.setData(WatchesMen);
                if (C5 == 4) {
                    C5 = 0;
                    ++R5;
                }
                WatchGridM.add(MenVbox, C5++, R5);
                GridPane.setMargin(MenVbox, new Insets(10));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error in watch men");
        }


        new Accessories().run();

        new Women().run();

        new Kids().run();

        loadData();


    }



    public void loadpage(){
        engine.load("https://docs.google.com/forms/d/e/1FAIpQLScENfzb7bTGUfPO0jLOR_gdncKqIFOSHK5fBlCkGNgdxZMvog/viewform?usp=sf_link");
    }
    public void FeedBackButtonAction(ActionEvent event){
        engine = webView.getEngine();
        loadpage();
    }


    private List<Clothes> offer(){

        List<Clothes> ls = new ArrayList<>();

        Clothes clothe = new Clothes();
        clothe.setName(" Grey Woolen OverCoat");
        clothe.setImageSrc("/image2/MenGray.png");
        clothe.setBrand(" By H&M");
        ls.add(clothe);

        clothe = new Clothes();
        clothe.setName(" Checked Jacket");
        clothe.setImageSrc("/image2/WomenChecked_Jacket.png");
        clothe.setBrand(" By H&M");
        ls.add(clothe);


        clothe = new Clothes();
        clothe.setName(" Nike Jump-Suit");
        clothe.setImageSrc("/image2/nikejumpsuitpurple.png");
        clothe.setBrand(" By Nike");
        ls.add(clothe);


        //System.out.println("list of clothe size is: "+ ls.size());

        return ls;

    }

    private List<Clothes> recom(){
        List<Clothes> ls = new ArrayList<>();

        Clothes clothe = new Clothes();
        clothe.setName("Converse Creme Sneakers");
        clothe.setImageSrc("/image2/Converse1.jpg");
        clothe.setBrand("By Converse");
        ls.add(clothe);

        clothe = new Clothes();
        clothe.setName("Tissot Watch");
        clothe.setImageSrc("/image2/TissotWatch.jpg");
        clothe.setBrand("By Tissot");
        ls.add(clothe);

        clothe = new Clothes();
        clothe.setName("Solid color Tshirt");
        clothe.setImageSrc("/image2/BlackTshirt.jpg");
        clothe.setBrand("By H&M");
        ls.add(clothe);


        clothe = new Clothes();
        clothe.setName(" Jute Bag");
        clothe.setImageSrc("/image2/DarkblueJacket.jpg");
        clothe.setBrand(" Christian Dior");
        ls.add(clothe);



        clothe = new Clothes();
        clothe.setName(" Dark blue Jacket");
        clothe.setImageSrc("/image2/PurpleJacketWomens.jpg");
        clothe.setBrand(" By H&M");
        ls.add(clothe);


        clothe = new Clothes();
        clothe.setName(" Grey Woolen OverCoat");
        clothe.setImageSrc("/image2/MenGray.png");
        clothe.setBrand(" By H&M");
        ls.add(clothe);

        clothe = new Clothes();
        clothe.setName(" Checked Jacket");
        clothe.setImageSrc("/image2/WomenChecked_Jacket.png");
        clothe.setBrand(" By H&M");
        ls.add(clothe);

        clothe = new Clothes();
        clothe.setName(" Grey Woolen OverCoat");
        clothe.setImageSrc("/image2/MenGray.png");
        clothe.setBrand(" By H&M");
        ls.add(clothe);

        //System.out.println("list of clothe size is: "+ ls.size());

        return ls;
    }

    private List<WomenClothe> Hoodie_Mens(){
        List<WomenClothe> ls = new ArrayList<>();

        WomenClothe clothe = new WomenClothe();

        clothe.setNamewomen("Classic Black");
        clothe.setImageSrcWomen("/image2/Men/Hoodies/ClassicBlack_ZARA.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Zara");
        clothe.setPrice(1599);
        ls.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Creme Color");
        clothe.setImageSrcWomen("/image2/Men/Hoodies/CremeColor.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Levis");
        clothe.setPrice(20000);
        ls.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Light Orange");
        clothe.setImageSrcWomen("/image2/Men/Hoodies/LightOrange_By_OFFWHITE.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By OFFWHITE");
        clothe.setPrice(22699);
        ls.add(clothe);


        clothe = new WomenClothe();
        clothe.setNamewomen("Vans Original Black");
        clothe.setImageSrcWomen("/image2/Men/Hoodies/VansOriginalBlack.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Vans");
        clothe.setPrice(699);
        ls.add(clothe);



        clothe = new WomenClothe();
        clothe.setNamewomen("White SUEDE");
        clothe.setImageSrcWomen("/image2/Men/Hoodies/WhiteSUEDE.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By H&M");
        clothe.setPrice(699);
        ls.add(clothe);

        //System.out.println("list of clothe size is: "+ ls.size());

        return ls;
    }

    private List<WomenClothe> TeesMen(){
        List<WomenClothe> lstm = new ArrayList<>();

        WomenClothe clothe = new WomenClothe();

        clothe.setNamewomen("audere");
        clothe.setImageSrcWomen("/image2/Men/Tees/audere.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By H&M");
        clothe.setPrice(2699);
        lstm.add(clothe);


        clothe = new WomenClothe();
        clothe.setNamewomen("Solid White Awesome");
        clothe.setImageSrcWomen("/image2/Men/Tees/AwesomeSolidWhiteColor.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Off-White");
        clothe.setPrice(1899);
        lstm.add(clothe);



        clothe = new WomenClothe();
        clothe.setNamewomen("Black Graphic Tee");
        clothe.setImageSrcWomen("/image2/Men/Tees/BlackGraphicTee.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By H&M");
        clothe.setPrice(599);
        lstm.add(clothe);


        clothe = new WomenClothe();
        clothe.setNamewomen("Black Scripted Tee");
        clothe.setImageSrcWomen("/image2/Men/Tees/BlackScripted.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand(" By H&M");
        clothe.setPrice(1200);
        lstm.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Inscription");
        clothe.setImageSrcWomen("/image2/Men/Tees/EnscriptionbyChampion.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand(" By H&M");
        clothe.setPrice(699);
        lstm.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Solid Flame Orange");
        clothe.setImageSrcWomen("/image2/Men/Tees/SolidFlameOrange.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand(" By H&M");
        clothe.setPrice(699);
        lstm.add(clothe);

        //System.out.println("list of clothe size is: "+ lstm.size());

        return lstm;
    }

    private List<WomenClothe> JacketMen(){
        List<WomenClothe> lssm = new ArrayList<>();

        WomenClothe clothe = new WomenClothe();

        clothe.setNamewomen("Black Leather");
        clothe.setImageSrcWomen("/image2/Men/Jackets/BlackLeatherJacket.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Addidas");
        clothe.setPrice(699);
        lssm.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Grey OverCoat");
        clothe.setImageSrcWomen("/image2/Men/Jackets/GreyOver.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Louis Vuitton");
        clothe.setPrice(20000);
        lssm.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Solid color Tshirt");
        clothe.setImageSrcWomen("/image2/Men/Jackets/Lightgrey.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By H&M");
        clothe.setPrice(11699);
        lssm.add(clothe);


        clothe = new WomenClothe();
        clothe.setNamewomen("Navy Blue");
        clothe.setImageSrcWomen("/image2/Men/Jackets/NavyBlueJ.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Christian Dior");
        clothe.setPrice(2699);
        lssm.add(clothe);



        clothe = new WomenClothe();
        clothe.setNamewomen("Olive Green Cargo");
        clothe.setImageSrcWomen("/image2/Men/Jackets/OliveGreen.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By H&M");
        clothe.setPrice(5999);
        lssm.add(clothe);


        clothe = new WomenClothe();
        clothe.setNamewomen("Turquoise");
        clothe.setImageSrcWomen("/image2/Men/Jackets/Turquoise.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By H&M");
        clothe.setPrice(5999);
        lssm.add(clothe);

        //System.out.println("list of clothe size is: "+ lssm.size());

        return lssm;
    }

    private List<WomenClothe> BottomMen(){
        List<WomenClothe> lssm = new ArrayList<>();

        WomenClothe clothe = new WomenClothe();

        clothe.setNamewomen("Grey Checked  Chinos");
        clothe.setImageSrcWomen("/image2/Men/Bottoms/CheckGreyChinos.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Addidas");
        clothe.setPrice(1699);
        lssm.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Grey chinos");
        clothe.setImageSrcWomen("/image2/Men/Bottoms/greyChinos.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Louis Vuitton");
        clothe.setPrice(20000);
        lssm.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Blue Denim ");
        clothe.setImageSrcWomen("/image2/Men/Bottoms/Jeans.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By H&M");
        clothe.setPrice(11699);
        lssm.add(clothe);


        clothe = new WomenClothe();
        clothe.setNamewomen("Navy Blue Trousers");
        clothe.setImageSrcWomen("/image2/Men/Bottoms/NavyBlueFormalTrousers.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Christian Dior");
        clothe.setPrice(22699);
        lssm.add(clothe);



        clothe = new WomenClothe();
        clothe.setNamewomen("Ripped Jeans");
        clothe.setImageSrcWomen("/image2/Men/Bottoms/rippedjeans_Vans.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By H&M");
        clothe.setPrice(5999);
        lssm.add(clothe);


        clothe = new WomenClothe();
        clothe.setNamewomen("White Formals");
        clothe.setImageSrcWomen("/image2/Men/Bottoms/WhiteChinos.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By ZARA");
        clothe.setPrice(8999);
        lssm.add(clothe);

        //System.out.println("list of clothe size is: "+ lssm.size());

        return lssm;
    }

    private List<WomenClothe> ShoesMen(){
        List<WomenClothe> lssm = new ArrayList<>();

        WomenClothe clothe = new WomenClothe();

        clothe.setNamewomen("Brown Leather");
        clothe.setImageSrcWomen("/image2/Men/Shoes/BrownLEATHERFormals.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Van Huessen");
        clothe.setPrice(11699);
        lssm.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Nike AirForce ONE");
        clothe.setImageSrcWomen("/image2/Men/Shoes/NikeAirForce1.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Nike");
        clothe.setPrice(20000);
        lssm.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Nike Air Max Red");
        clothe.setImageSrcWomen("/image2/Men/Shoes/NikeAirMaxProductRed.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Nike");
        clothe.setPrice(11699);
        lssm.add(clothe);


        clothe = new WomenClothe();
        clothe.setNamewomen("Nike Color Block");
        clothe.setImageSrcWomen("/image2/Men/Shoes/NikeColorBlock.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Nike");
        clothe.setPrice(22699);
        lssm.add(clothe);



        clothe = new WomenClothe();
        clothe.setNamewomen("Classic Black Converse");
        clothe.setImageSrcWomen("/image2/Men/Shoes/UnisexBlackConverse.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Converse");
        clothe.setPrice(5999);
        lssm.add(clothe);


        clothe = new WomenClothe();
        clothe.setNamewomen("Classic Vans");
        clothe.setImageSrcWomen("/image2/Men/Shoes/Vans.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Vans");
        clothe.setPrice(8999);
        lssm.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Yeezy");
        clothe.setImageSrcWomen("/image2/Men/Shoes/YeezyAdidas.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Adidas");
        clothe.setPrice(8999);
        lssm.add(clothe);

        //System.out.println("list of clothe size is: "+ lssm.size());

        return lssm;
    }

    private List<WomenClothe> WatchesMen(){
        List<WomenClothe> lssm = new ArrayList<>();

        WomenClothe clothe = new WomenClothe();

        clothe.setNamewomen("Apple Watch Series 6");
        clothe.setImageSrcWomen("/image2/Men/AppleWatchseries6.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Apple");
        clothe.setPrice(11699);
        lssm.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Limited Edition Tissot");
        clothe.setImageSrcWomen("/image2/Men/BrownTissot.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Tissot");
        clothe.setPrice(20000);
        lssm.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Classic Fossil");
        clothe.setImageSrcWomen("/image2/Men/FossilClassic.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Fossil");
        clothe.setPrice(11699);
        lssm.add(clothe);


        clothe = new WomenClothe();
        clothe.setNamewomen("Metallic Strap Fossil");
        clothe.setImageSrcWomen("/image2/Men/FOSSILgoldchain.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Fossil");
        clothe.setPrice(22699);
        lssm.add(clothe);



        clothe = new WomenClothe();
        clothe.setNamewomen("MVMT RoseGold edition");
        clothe.setImageSrcWomen("/image2/Men/MVMTrosegolddial.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By MVMT");
        clothe.setPrice(5999);
        lssm.add(clothe);


        clothe = new WomenClothe();
        clothe.setNamewomen("Nautical Brown Belt");
        clothe.setImageSrcWomen("/image2/Men/NauticaBrown.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Nautical");
        clothe.setPrice(8999);
        lssm.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Timex Brown Belt");
        clothe.setImageSrcWomen("/image2/Men/TimexBrownBelt.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Timex");
        clothe.setPrice(8999);
        lssm.add(clothe);

        //System.out.println("list of clothe size is: "+ lssm.size());

        return lssm;
    }


    private List<WomenClothe> WomenBag(){
        List<WomenClothe> WB = new ArrayList<>();

        WomenClothe woman = new WomenClothe();

        woman.setNamewomen("Solid Black Bag");
        woman.setImageSrcWomen("/Women/BlackBag1.png");
        woman.setImageSrc2("/image2/rupeeSymbol.jpg");
        woman.setBrand("By Aldo");
        woman.setPrice(13299);
        WB.add(woman);

        woman = new WomenClothe();
        woman.setNamewomen("Shoulder Bag");
        woman.setImageSrcWomen("/Women/gabrielle_henderson_ShoulderBag.png");
        woman.setImageSrc2("/image2/rupeeSymbol.jpg");
        woman.setBrand("By Gabrielle Henderson");
        woman.setPrice(15000);
        WB.add(woman);

        woman = new WomenClothe();
        woman.setNamewomen("Croc Style SlingBag");
        woman.setImageSrcWomen("/Women/irene_kredenets_CrocStyleSlingBag.png");
        woman.setImageSrc2("/image2/rupeeSymbol.jpg");
        woman.setBrand("By irene kredenets");
        woman.setPrice(11999);
        WB.add(woman);


        woman = new WomenClothe();
        woman.setNamewomen("Orange Bag");
        woman.setImageSrcWomen("/Women/OrangeBag2.png");
        woman.setImageSrc2("/image2/rupeeSymbol.jpg");
        woman.setBrand("By Christian Dior");
        woman.setPrice(17999);
        WB.add(woman);



        woman = new WomenClothe();
        woman.setNamewomen("OWL Color Blocked");
        woman.setImageSrcWomen("/Women/OWL_colorblocked_ALDO.png");
        woman.setImageSrc2("/image2/rupeeSymbol.jpg");
        woman.setBrand(" By H&M");
        woman.setPrice(5999);
        WB.add(woman);


        woman = new WomenClothe();
        woman.setNamewomen("Metallic Shoulder Bag");
        woman.setImageSrcWomen("/Women/Solid_MetallicShoulderBag.png");
        woman.setImageSrc2("/image2/rupeeSymbol.jpg");
        woman.setBrand(" By PRADA");
        woman.setPrice(29999);
        WB.add(woman);

        woman = new WomenClothe();
        woman.setNamewomen("Solid Peach color");
        woman.setImageSrcWomen("/Women/SolidPeachcolor_LEISARA.png");
        woman.setImageSrc2("/image2/rupeeSymbol.jpg");
        woman.setBrand(" By LEISARA");
        woman.setPrice(19999);
        WB.add(woman);

        woman = new WomenClothe();
        woman.setNamewomen("Solid White");
        woman.setImageSrcWomen("/Women/SolidWhiteLouisVuitton.png");
        woman.setImageSrc2("/image2/rupeeSymbol.jpg");
        woman.setBrand("LouisVuitton");
        woman.setPrice(25599);
        WB.add(woman);


        //System.out.println("list of clothe size is: "+ lssm.size());

        return WB;
    }

    private List<WomenClothe> WomenBottom(){
        List<WomenClothe> WB = new ArrayList<>();

        WomenClothe clothe = new WomenClothe();

        clothe.setNamewomen("Army Cargo Pants");
        clothe.setImageSrcWomen("/Women/bottom/ArmyCargoPants.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Zara");
        clothe.setPrice(13299);
        WB.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Black Joggers");
        clothe.setImageSrcWomen("/Women/bottom/BlackJoggers.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Superdry");
        clothe.setPrice(15000);
        WB.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Blue Denim Jeans");
        clothe.setImageSrcWomen("/Women/bottom/Bluedenim.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Levis");
        clothe.setPrice(11999);
        WB.add(clothe);


        clothe = new WomenClothe();
        clothe.setNamewomen("Floral Bottoms");
        clothe.setImageSrcWomen("/Women/bottom/FloralBottoms.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Christian Dior");
        clothe.setPrice(17999);
        WB.add(clothe);



        clothe = new WomenClothe();
        clothe.setNamewomen("Black Joggers ");
        clothe.setImageSrcWomen("/Women/bottom/Joggers_34ths_Forever21.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Forever21");
        clothe.setPrice(5999);
        WB.add(clothe);


        clothe = new WomenClothe();
        clothe.setNamewomen("Palazzo grey");
        clothe.setImageSrcWomen("/Women/bottom/Palazo_grey.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand(" By H&M");
        clothe.setPrice(10999);
        WB.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Ripped Jeans");
        clothe.setImageSrcWomen("/Women/bottom/RippedJeans_MARKNSPENCER.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand(" By MARK & SPENCER");
        clothe.setPrice(19999);
        WB.add(clothe);

//        clothe = new WomenClothe();
//        clothe.setNamewomen("White Palazzo");
//        clothe.setImageSrcWomen("/Women/bottom/WhitePalazo.png");
//        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
//        clothe.setBrand("Verssace");
//        clothe.setPrice(25599);
//        WB.add(clothe);

        //System.out.println("list of clothe size is: "+ lssm.size());

        return WB;
    }


    private List<WomenClothe> WomenTshirt(){
        List<WomenClothe> WT = new ArrayList<>();

        WomenClothe clothe = new WomenClothe();

        clothe.setNamewomen("Broken Saints");
        clothe.setImageSrcWomen("/Women/Tees/BrokenSaints.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Adidas");
        clothe.setPrice(13299);
        WT.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Dark Grey");
        clothe.setImageSrcWomen("/Women/Tees/DarkGrey.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Addidas");
        clothe.setPrice(15000);
        WT.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Floral Gucci Logo");
        clothe.setImageSrcWomen("/Women/Tees/FloralGucciLogo.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Gucci");
        clothe.setPrice(11999);
        WT.add(clothe);


        clothe = new WomenClothe();
        clothe.setNamewomen("Orange Tee");
        clothe.setImageSrcWomen("/Women/Tees/OrangeTee.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Christian Dior");
        clothe.setPrice(17999);
        WT.add(clothe);



        clothe = new WomenClothe();
        clothe.setNamewomen("Saudade");
        clothe.setImageSrcWomen("/Women/Tees/Saudade.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Forever21");
        clothe.setPrice(5999);
        WT.add(clothe);


//        clothe = new WomenClothe();
//        clothe.setNamewomen("Solid Black");
//        clothe.setImageSrcWomen("/Women/Tees/solidBlacktee.jpg");
//        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
//        clothe.setBrand(" By H&M");
//        clothe.setPrice(10999);
//        WT.add(clothe);
//
//        clothe = new WomenClothe();
//        clothe.setNamewomen("stripped Shirt");
//        clothe.setImageSrcWomen("/Women/Tees/strippedShirt.jpg");
//        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
//        clothe.setBrand(" By MARK & SPENCER");
//        clothe.setPrice(19999);
//        WT.add(clothe);
//
//        clothe = new WomenClothe();
//        clothe.setNamewomen("Vogue White TEE");
//        clothe.setImageSrcWomen("/Women/Tees/VogueWhiteTEE.jpg");
//        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
//        clothe.setBrand("Verssace");
//        clothe.setPrice(25599);
//        WT.add(clothe);

        //System.out.println("list of clothe size is: "+ lssm.size());

        return WT;
    }

    private List<WomenClothe> WomenWatchesW(){
        List<WomenClothe> WW = new ArrayList<>();

        WomenClothe clothe = new WomenClothe();
        clothe.setNamewomen("Infamous 2021");
        clothe.setImageSrcWomen("/Women/Watches/jaelynn_castillo.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Jaelynn Castillo");
        clothe.setPrice(13299);
        WW.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Metallic");
        clothe.setImageSrcWomen("/Women/Watches/Metallic.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Jaguar");
        clothe.setPrice(15000);
        WW.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Beige Rolex");
        clothe.setImageSrcWomen("/Women/Watches/Rolex.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Rolex");
        clothe.setPrice(11999);
        WW.add(clothe);


        clothe = new WomenClothe();
        clothe.setNamewomen("Rose Gold");
        clothe.setImageSrcWomen("/Women/Watches/RoseGold.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Christian Dior");
        clothe.setPrice(17999);
        WW.add(clothe);



        clothe = new WomenClothe();
        clothe.setNamewomen("wood watch");
        clothe.setImageSrcWomen("/Women/Watches/woodwatch.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Forever21");
        clothe.setPrice(5999);
        WW.add(clothe);

        //System.out.println("list of clothe size is: "+ lssm.size());

        return WW;
    }
    private List<WomenClothe> WomenHoodies(){
        List<WomenClothe> WH = new ArrayList<>();

        WomenClothe clothe = new WomenClothe();

        clothe.setNamewomen("Color Block");
        clothe.setImageSrcWomen("/Women/Hoodies/AdidasColorBlock.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Adidas");
        clothe.setPrice(13299);
        WH.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Grey Hoodie");
        clothe.setImageSrcWomen("/Women/Hoodies/grey.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Addidas");
        clothe.setPrice(15000);
        WH.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Beige Solid Color");
        clothe.setImageSrcWomen("/Women/Hoodies/Beige.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Levis");
        clothe.setPrice(11999);
        WH.add(clothe);


        clothe = new WomenClothe();
        clothe.setNamewomen("Dog Lover Yellow");
        clothe.setImageSrcWomen("/Women/Hoodies/DogLover.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Christian Dior");
        clothe.setPrice(17999);
        WH.add(clothe);



        clothe = new WomenClothe();
        clothe.setNamewomen("Graphic");
        clothe.setImageSrcWomen("/Women/Hoodies/Graphic.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Forever21");
        clothe.setPrice(5999);
        WH.add(clothe);


        clothe = new WomenClothe();
        clothe.setNamewomen("Solid Black");
        clothe.setImageSrcWomen("/Women/Hoodies/SBlack.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand(" By H&M");
        clothe.setPrice(10999);
        WH.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Solid Purple Haze");
        clothe.setImageSrcWomen("/Women/Hoodies/SolidPurpleHaze.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand(" By MARK & SPENCER");
        clothe.setPrice(19999);
        WH.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Violet");
        clothe.setImageSrcWomen("/Women/Hoodies/Violet.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("Verssace");
        clothe.setPrice(25599);
        WH.add(clothe);

        //System.out.println("list of clothe size is: "+ lssm.size());

        return WH;
    }
    private List<WomenClothe> WomenJackets(){
        List<WomenClothe> WJ = new ArrayList<>();

        WomenClothe clothe = new WomenClothe();

        clothe.setNamewomen("Army Green Cargo");
        clothe.setImageSrcWomen("/Women/jackets/ArmyGreenCargo.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Adidas");
        clothe.setPrice(13299);
        WJ.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Black Double Button");
        clothe.setImageSrcWomen("/Women/jackets/BlackDoubleButton.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Addidas");
        clothe.setPrice(15000);
        WJ.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Checked Jacket");
        clothe.setImageSrcWomen("/Women/jackets/Checked_Jacket.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Levis");
        clothe.setPrice(11999);
        WJ.add(clothe);


        clothe = new WomenClothe();
        clothe.setNamewomen("Dark Blue ");
        clothe.setImageSrcWomen("/Women/jackets/DarkBlue.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Christian Dior");
        clothe.setPrice(17999);
        WJ.add(clothe);



        clothe = new WomenClothe();
        clothe.setNamewomen("Dark Brown Leather");
        clothe.setImageSrcWomen("/Women/jackets/DarkBrownLeather.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Forever21");
        clothe.setPrice(5999);
        WJ.add(clothe);


        clothe = new WomenClothe();
        clothe.setNamewomen("Red Leather");
        clothe.setImageSrcWomen("/Women/jackets/RedLeather.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand(" By H&M");
        clothe.setPrice(10999);
        WJ.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("turquoise");
        clothe.setImageSrcWomen("/Women/jackets/turquoise.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand(" By MARK & SPENCER");
        clothe.setPrice(19999);
        WJ.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("White Puffed.png");
        clothe.setImageSrcWomen("/Women/jackets/WhitePuffed.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("Verssace");
        clothe.setPrice(25599);
        WJ.add(clothe);

        //System.out.println("list of clothe size is: "+ lssm.size());

        return WJ;
    }
    private List<WomenClothe> WomenShoes(){
        List<WomenClothe> WS = new ArrayList<>();

        WomenClothe clothe = new WomenClothe();

        clothe.setNamewomen("Classic Black Converse");
        clothe.setImageSrcWomen("/Women/Shoes/ClassicBlackConverse.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Adidas");
        clothe.setPrice(13299);
        WS.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Floral Heels");
        clothe.setImageSrcWomen("/Women/Shoes/FloralHeels.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Addidas");
        clothe.setPrice(15000);
        WS.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Light Pink");
        clothe.setImageSrcWomen("/Women/Shoes/LightPink_SaintLauren.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Saint Lauren");
        clothe.setPrice(11999);
        WS.add(clothe);


        clothe = new WomenClothe();
        clothe.setNamewomen("Transparent Sparkle Heels");
        clothe.setImageSrcWomen("/Women/Shoes/marcus_lewissparkly.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Marcus Lewis");
        clothe.setPrice(17999);
        WS.add(clothe);



        clothe = new WomenClothe();
        clothe.setNamewomen("Color Block");
        clothe.setImageSrcWomen("/Women/Shoes/NikeColorBlock.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Nike");
        clothe.setPrice(5999);
        WS.add(clothe);


        clothe = new WomenClothe();
        clothe.setNamewomen("Bi color Peace-Black");
        clothe.setImageSrcWomen("/Women/Shoes/Peace_BlackChanel.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Chanel");
        clothe.setPrice(10999);
        WS.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Sparkle Heels");
        clothe.setImageSrcWomen("/Women/Shoes/SparklyHeels_JimmyCHOOLONDON.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand(" By Jimmy Choo LONDON");
        clothe.setPrice(19999);
        WS.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("unisex Creme color");
        clothe.setImageSrcWomen("/Women/Shoes/unisexConverse.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Converse");
        clothe.setPrice(25599);
        WS.add(clothe);

        //System.out.println("list of clothe size is: "+ lssm.size());

        return WS;
    }

    private List<WomenClothe> Shoeskid(){
        List<WomenClothe> lsSK = new ArrayList<>();

        WomenClothe clothe = new WomenClothe();

        clothe.setNamewomen("Brown Girl Boots");
        clothe.setImageSrcWomen("/Kids/BrownGirlBoots.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By J&J");
        clothe.setPrice(11699);
        lsSK.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Cloth Canvas Shoe");
        clothe.setImageSrcWomen("/Kids/ClothCanvas.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By TinyTots");
        clothe.setPrice(20000);
        lsSK.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Red Converse");
        clothe.setImageSrcWomen("/Kids/Converse.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Converse");
        clothe.setPrice(11699);
        lsSK.add(clothe);


        clothe = new WomenClothe();
        clothe.setNamewomen("Silver Shoes");
        clothe.setImageSrcWomen("/Kids/Silvershoes.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By J&J");
        clothe.setPrice(22699);
        lsSK.add(clothe);



        clothe = new WomenClothe();
        clothe.setNamewomen("White Princess Shoes");
        clothe.setImageSrcWomen("/Kids/WhiteGirl.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By TinyTots");
        clothe.setPrice(5999);
        lsSK.add(clothe);

        //System.out.println("list of clothe size is: "+ lssm.size());

        return lsSK;
    }

    private List<WomenClothe> Hoodiekid(){
        List<WomenClothe> lsHK = new ArrayList<>();

        WomenClothe clothe = new WomenClothe();

        clothe.setNamewomen("creme Brown");
        clothe.setImageSrcWomen("/Kids/Hoodie/CremeBrown.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By J&J");
        clothe.setPrice(11699);
        lsHK.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Grey Knit");
        clothe.setImageSrcWomen("/Kids/Hoodie/GreyKnit.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By TinyTots");
        clothe.setPrice(20000);
        lsHK.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Sky Blue");
        clothe.setImageSrcWomen("/Kids/Hoodie/SkyBlue.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Converse");
        clothe.setPrice(11699);
        lsHK.add(clothe);


        clothe = new WomenClothe();
        clothe.setNamewomen("White Fur Winter");
        clothe.setImageSrcWomen("/Kids/Hoodie/WhiteFur.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By J&J");
        clothe.setPrice(22699);
        lsHK.add(clothe);



        clothe = new WomenClothe();
        clothe.setNamewomen("Yellow Woolen Hoodie");
        clothe.setImageSrcWomen("/Kids/Hoodie/YelloWollen.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By TinyTots");
        clothe.setPrice(5999);
        lsHK.add(clothe);

        //System.out.println("list of clothe size is: "+ lssm.size());

        return lsHK;
    }

    private List<WomenClothe> Jacketkid(){
        List<WomenClothe> lsJK = new ArrayList<>();

        WomenClothe clothe = new WomenClothe();

        clothe.setNamewomen("Boys Blue Denim");
        clothe.setImageSrcWomen("/Kids/Jacket/BoyesBlueDenim.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By J&J");
        clothe.setPrice(11699);
        lsJK.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Boys Black Denim");
        clothe.setImageSrcWomen("/Kids/Jacket/BoysBlackDenim.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By TinyTots");
        clothe.setPrice(20000);
        lsJK.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Girls Denim");
        clothe.setImageSrcWomen("/Kids/Jacket/Girls_Denim.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Converse");
        clothe.setPrice(11699);
        lsJK.add(clothe);


        clothe = new WomenClothe();
        clothe.setNamewomen("Girls Over Coat");
        clothe.setImageSrcWomen("/Kids/Jacket/GirlsOvercoat.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By J&J");
        clothe.setPrice(22699);
        lsJK.add(clothe);



        clothe = new WomenClothe();
        clothe.setNamewomen("Woolen Jacket");
        clothe.setImageSrcWomen("/Kids/Jacket/WoolenJacket.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By TinyTots");
        clothe.setPrice(5999);
        lsJK.add(clothe);

        //System.out.println("list of clothe size is: "+ lssm.size());

        return lsJK;
    }

    private List<WomenClothe> Bottomkid(){
        List<WomenClothe> lsBK = new ArrayList<>();

        WomenClothe clothe = new WomenClothe();

        clothe.setNamewomen("Cargo denims");
        clothe.setImageSrcWomen("/Kids/Jeans/CargoDenims.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By J&J");
        clothe.setPrice(11699);
        lsBK.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Dark Blue Denim");
        clothe.setImageSrcWomen("/Kids/Jeans/DarkBlueDenims.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By TinyTots");
        clothe.setPrice(20000);
        lsBK.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Denim Chinos");
        clothe.setImageSrcWomen("/Kids/Jeans/DenimChinos.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Converse");
        clothe.setPrice(11699);
        lsBK.add(clothe);


        clothe = new WomenClothe();
        clothe.setNamewomen("Girl BasketBall Jeans");
        clothe.setImageSrcWomen("/Kids/Jeans/GirlsBascketballJeans.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By J&J");
        clothe.setPrice(22699);
        lsBK.add(clothe);



        clothe = new WomenClothe();
        clothe.setNamewomen("Girls Ripped Jeans");
        clothe.setImageSrcWomen("/Kids/Jeans/GirlsRippedJeans.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By TinyTots");
        clothe.setPrice(5999);
        lsBK.add(clothe);

        //System.out.println("list of clothe size is: "+ lssm.size());

        return lsBK;
    }

    private List<WomenClothe> TeeKid(){
        List<WomenClothe> lsKT = new ArrayList<>();

        WomenClothe clothe = new WomenClothe();

        clothe.setNamewomen("Color Block");
        clothe.setImageSrcWomen("/Kids/Tees/ColorBlock.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By J&J");
        clothe.setPrice(11699);
        lsKT.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Girls Abstract");
        clothe.setImageSrcWomen("/Kids/Tees/GirlsAbstract.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By TinyTots");
        clothe.setPrice(20000);
        lsKT.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("White Solid");
        clothe.setImageSrcWomen("/Kids/Tees/WhiteSolid.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Converse");
        clothe.setPrice(11699);
        lsKT.add(clothe);


        clothe = new WomenClothe();
        clothe.setNamewomen("Woolen Checked");
        clothe.setImageSrcWomen("/Kids/Tees/WoolenChecked.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By J&J");
        clothe.setPrice(22699);
        lsKT.add(clothe);



        clothe = new WomenClothe();
        clothe.setNamewomen("Yellow");
        clothe.setImageSrcWomen("/Kids/Tees/ColorBlock.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By TinyTots");
        clothe.setPrice(5999);
        lsKT.add(clothe);

        //System.out.println("list of clothe size is: "+ lssm.size());

        return lsKT;
    }

    private List<WomenClothe> Watchkid(){
        List<WomenClothe> lssm = new ArrayList<>();

        WomenClothe clothe = new WomenClothe();

        clothe.setNamewomen("Blue Design");
        clothe.setImageSrcWomen("/Kids/Watch/Bluedesign.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By J&J");
        clothe.setPrice(11699);
        lssm.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Blue");
        clothe.setImageSrcWomen("/Kids/Watch/Bluewatch.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By TinyTots");
        clothe.setPrice(20000);
        lssm.add(clothe);

        clothe = new WomenClothe();
        clothe.setNamewomen("Girls Watch Pink");
        clothe.setImageSrcWomen("/Kids/Watch/Girlswatchpink.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Converse");
        clothe.setPrice(11699);
        lssm.add(clothe);


        clothe = new WomenClothe();
        clothe.setNamewomen("Orange");
        clothe.setImageSrcWomen("/Kids/Watch/Orange.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By J&J");
        clothe.setPrice(22699);
        lssm.add(clothe);



        clothe = new WomenClothe();
        clothe.setNamewomen("White Strap");
        clothe.setImageSrcWomen("/Kids/Watch/Whitestrap.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By TinyTots");
        clothe.setPrice(5999);
        lssm.add(clothe);

        //System.out.println("list of clothe size is: "+ lssm.size());

        return lssm;
    }

    private List<Clothes> Chair(){
        List<Clothes> lsc = new ArrayList<>();

        Clothes clothe = new Clothes();

        clothe.setName("Beach Chair");
        clothe.setImageSrc("/HomeNliving/BeachChair.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By OCA");
        clothe.setPrice(11699);
        lsc.add(clothe);

        clothe = new Clothes();
        clothe.setName("Funky Color Block");
        clothe.setImageSrc("/HomeNliving/Funky_ColorBlock.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By OCA");
        clothe.setPrice(20000);
        lsc.add(clothe);

        clothe = new Clothes();
        clothe.setName("Grey Suede");
        clothe.setImageSrc("/HomeNliving/GreaySuede.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By OCA");
        clothe.setPrice(11699);
        lsc.add(clothe);


        clothe = new Clothes();
        clothe.setName("Kitchen Bar Chair");
        clothe.setImageSrc("/HomeNliving/KitchenBar_Chair.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By OCA");
        clothe.setPrice(22699);
        lsc.add(clothe);


        clothe = new Clothes();
        clothe.setName("Leather Lounge Chair");
        clothe.setImageSrc("/HomeNliving/Leather-LoungeChair.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By OCA");
        clothe.setPrice(5999);
        lsc.add(clothe);

        clothe = new Clothes();
        clothe.setName("Office Chair");
        clothe.setImageSrc("/HomeNliving/office_Chair.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By OCA");
        clothe.setPrice(5999);
        lsc.add(clothe);

        clothe = new Clothes();
        clothe.setName("Wooden Chairs");
        clothe.setImageSrc("/HomeNliving/WoodenChairs.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By OCA");
        clothe.setPrice(5999);
        lsc.add(clothe);

        //System.out.println("list of clothe size is: "+ lssm.size());

        return lsc;
    }

    private List<Clothes> Sofa(){
        List<Clothes> lsS = new ArrayList<>();

        Clothes clothe = new Clothes();

        clothe.setName("Black Leather");
        clothe.setImageSrc("/HomeNliving/Sofas/BlackLeatherLshaped.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By OCA");
        clothe.setPrice(11699);
        lsS.add(clothe);

        clothe = new Clothes();
        clothe.setName("Brown Leather");
        clothe.setImageSrc("/HomeNliving/Sofas/BrownLeather.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By OCA");
        clothe.setPrice(20000);
        lsS.add(clothe);

        clothe = new Clothes();
        clothe.setName("Classic White 6Seater");
        clothe.setImageSrc("/HomeNliving/Sofas/ClassicWhite6Seater.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By OCA");
        clothe.setPrice(20000);
        lsS.add(clothe);

        clothe = new Clothes();
        clothe.setName("Grey Blue");
        clothe.setImageSrc("/HomeNliving/Sofas/GreyBlue.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By OCA");
        clothe.setPrice(20000);
        lsS.add(clothe);

        clothe = new Clothes();
        clothe.setName("U-shaped");
        clothe.setImageSrc("/HomeNliving/Sofas/UshapedLightGrey.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By OCA");
        clothe.setPrice(20000);
        lsS.add(clothe);

        //System.out.println("list of clothe size is: "+ lssm.size());

        return lsS;
    }


    private List<Clothes> SidTable() {
        List<Clothes> lsst = new ArrayList<>();

        Clothes clothe = new Clothes();

        clothe.setName("Circle Table");
        clothe.setImageSrc("/HomeNliving/SideTable/CircleTable.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By OCA");
        clothe.setPrice(11699);
        lsst.add(clothe);

        clothe = new Clothes();
        clothe.setName("LightBrown MultiDrawer table");
        clothe.setImageSrc("/HomeNliving/SideTable/LightBrown_MultiDrawertable.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By OCA");
        clothe.setPrice(11699);
        lsst.add(clothe);

        clothe = new Clothes();
        clothe.setName("Oak Wooden Tables");
        clothe.setImageSrc("/HomeNliving/SideTable/OakWoodenTables.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By OCA");
        clothe.setPrice(11699);
        lsst.add(clothe);

        clothe = new Clothes();
        clothe.setName("Offwhite Modern Table");
        clothe.setImageSrc("/HomeNliving/SideTable/OffwhiteModernSideTable.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By OCA");
        clothe.setPrice(11699);
        lsst.add(clothe);

        clothe = new Clothes();
        clothe.setName("White Bedroom Table");
        clothe.setImageSrc("/HomeNliving/SideTable/WhiteSmallBedroomTable.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By OCA");
        clothe.setPrice(11699);
        lsst.add(clothe);

        return lsst;
    }

    private List<Clothes> Lamp() {
        List<Clothes> lssl = new ArrayList<>();

        Clothes Lamp = new Clothes();

        Lamp.setName("Custom Neon Lights");
        Lamp.setImageSrc("/HomeNliving/Lamp/CustomNeonLights.png");
        Lamp.setImageSrc2("/image2/rupeeSymbol.jpg");
        Lamp.setBrand("By OCA");
        Lamp.setPrice(11699);
        lssl.add(Lamp);

        Lamp = new Clothes();
        Lamp.setName("Kitchen Lights");
        Lamp.setImageSrc("/HomeNliving/Lamp/KitchenLights.png");
        Lamp.setImageSrc2("/image2/rupeeSymbol.jpg");
        Lamp.setBrand("By OCA");
        Lamp.setPrice(11699);
        lssl.add(Lamp);

        Lamp = new Clothes();
        Lamp.setName("Lounge Lights");
        Lamp.setImageSrc("/HomeNliving/Lamp/LoungeLights.png");
        Lamp.setImageSrc2("/image2/rupeeSymbol.jpg");
        Lamp.setBrand("By OCA");
        Lamp.setPrice(11699);
        lssl.add(Lamp);

        Lamp = new Clothes();
        Lamp.setName("Bedroom Lights");
        Lamp.setImageSrc("/HomeNliving/Lamp/ModernBedroomLights.png");
        Lamp.setImageSrc2("/image2/rupeeSymbol.jpg");
        Lamp.setBrand("By OCA");
        Lamp.setPrice(11699);
        lssl.add(Lamp);

        Lamp = new Clothes();
        Lamp.setName("Study Room Lights");
        Lamp.setImageSrc("/HomeNliving/Lamp/StudyRoomLight.png");
        Lamp.setImageSrc2("/image2/rupeeSymbol.jpg");
        Lamp.setBrand("By OCA");
        Lamp.setPrice(11699);
        lssl.add(Lamp);

        return lssl;
    }


    private List<Clothes> Dine() {
        List<Clothes> lssd = new ArrayList<>();

        Clothes Dine = new Clothes();

        Dine.setName("8 Seater");
        Dine.setImageSrc("/HomeNliving/Dining/8_Seater.png");
        Dine.setImageSrc2("/image2/rupeeSymbol.jpg");
        Dine.setBrand("By OCA");
        Dine.setPrice(11699);
        lssd.add(Dine);

        Dine = new Clothes();
        Dine.setName("Marble White");
        Dine.setImageSrc("/HomeNliving/Dining/marblewhite6seater.png");
        Dine.setImageSrc2("/image2/rupeeSymbol.jpg");
        Dine.setBrand("By OCA");
        Dine.setPrice(11699);
        lssd.add(Dine);

        Dine = new Clothes();
        Dine.setName("Round Table DarkOAK");
        Dine.setImageSrc("/HomeNliving/Dining/RoundTableDarkGreySuede.png");
        Dine.setImageSrc2("/image2/rupeeSymbol.jpg");
        Dine.setBrand("By OCA");
        Dine.setPrice(11699);
        lssd.add(Dine);

        Dine = new Clothes();
        Dine.setName("Teak Wood");
        Dine.setImageSrc("/HomeNliving/Dining/Table_2_Wooden.png");
        Dine.setImageSrc2("/image2/rupeeSymbol.jpg");
        Dine.setBrand("By OCA");
        Dine.setPrice(11699);
        lssd.add(Dine);

        Dine = new Clothes();
        Dine.setName("Peach White");
        Dine.setImageSrc("/HomeNliving/Dining/Table_4_PeacheWhite.png");
        Dine.setImageSrc2("/image2/rupeeSymbol.jpg");
        Dine.setBrand("By OCA");
        Dine.setPrice(11699);
        lssd.add(Dine);


        return lssd;
    }


    private List<Clothes> Carpet() {
        List<Clothes> lscarpet = new ArrayList<>();

        Clothes carpet = new Clothes();


        carpet.setName("Abstract");
        carpet.setImageSrc("/HomeNliving/Carpet/Abstract.png");
        carpet.setImageSrc2("/image2/rupeeSymbol.jpg");
        carpet.setBrand("By OCA");
        carpet.setPrice(11699);
        lscarpet.add(carpet);

        carpet = new Clothes();
        carpet.setName("Black Gold Design");
        carpet.setImageSrc("/HomeNliving/Carpet/BlackGoldStripes.png");
        carpet.setImageSrc2("/image2/rupeeSymbol.jpg");
        carpet.setBrand("By OCA");
        carpet.setPrice(11699);
        lscarpet.add(carpet);

        carpet = new Clothes();
        carpet.setName("Faded Modern Blocks");
        carpet.setImageSrc("/HomeNliving/Carpet/FadedModernBlocks.png");
        carpet.setImageSrc2("/image2/rupeeSymbol.jpg");
        carpet.setBrand("By OCA");
        carpet.setPrice(11699);
        lscarpet.add(carpet);

        carpet = new Clothes();
        carpet.setName("Navy Blue Design");
        carpet.setImageSrc("/HomeNliving/Carpet/NavyBlueDesign.png");
        carpet.setImageSrc2("/image2/rupeeSymbol.jpg");
        carpet.setBrand("By OCA");
        carpet.setPrice(11699);
        lscarpet.add(carpet);

        carpet = new Clothes();
        carpet.setName("Off White fur");
        carpet.setImageSrc("/HomeNliving/Carpet/OffWhiteFur.png");
        carpet.setImageSrc2("/image2/rupeeSymbol.jpg");
        carpet.setBrand("By OCA");
        carpet.setPrice(11699);
        lscarpet.add(carpet);


        return lscarpet;
    }


    private List<Clothes> makeup() {
        List<Clothes> lsa6 = new ArrayList<>();

        Clothes clothe = new Clothes();

        clothe.setName("4 shades of Red");
        clothe.setImageSrc("/Accessories/lipstick.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By OCA");
        clothe.setPrice(890);
        lsa6.add(clothe);

        clothe = new Clothes();
        clothe.setName("Blush Brush");
        clothe.setImageSrc("/Accessories/BlushBrush2.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By OCA");
        clothe.setPrice(659);
        lsa6.add(clothe);

        clothe = new Clothes();
        clothe.setName("Lip Gloss");
        clothe.setImageSrc("/Accessories/Lipgloss.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By OCA");
        clothe.setPrice(699);
        lsa6.add(clothe);

        clothe = new Clothes();
        clothe.setName("Concealer");
        clothe.setImageSrc("/Accessories/Concealer.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By OCA");
        clothe.setPrice(1320);
        lsa6.add(clothe);

        clothe = new Clothes();
        clothe.setName("Nail Polish");
        clothe.setImageSrc("/Accessories/NailPolishPackof3.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By OCA");
        clothe.setPrice(799);
        lsa6.add(clothe);

        return lsa6;
    }

    private List<Clothes> smartwearables() {
        List<Clothes> lsa5 = new ArrayList<>();

        Clothes clothe = new Clothes();

        clothe.setName("Apple Nike Watch");
        clothe.setImageSrc("/Accessories/SmartWearables/Aplle_Nike_Watch.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Apple");
        clothe.setPrice(11699);
        lsa5.add(clothe);

        clothe = new Clothes();
        clothe.setName("Fitness Neck Band");
        clothe.setImageSrc("/Accessories/SmartWearables/FitnessNeckBand.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By OCA");
        clothe.setPrice(11699);
        lsa5.add(clothe);

        clothe = new Clothes();
        clothe.setName("French Connection");
        clothe.setImageSrc("/Accessories/SmartWearables/FrenchConnectionFitness.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By French Connection");
        clothe.setPrice(11699);
        lsa5.add(clothe);

        clothe = new Clothes();
        clothe.setName("Sennheiser HD350");
        clothe.setImageSrc("/Accessories/SmartWearables/SennheiserBlackHD350.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Sennheiser");
        clothe.setPrice(11699);
        lsa5.add(clothe);

        clothe = new Clothes();
        clothe.setName("Wireless Airpods");
        clothe.setImageSrc("/Accessories/SmartWearables/WirelessAirpods.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Boat");
        clothe.setPrice(11699);
        lsa5.add(clothe);

        return lsa5;
    }


    private List<Clothes> skincare() {
        List<Clothes> lsa4 = new ArrayList<>();

        Clothes clothe = new Clothes();

        clothe.setName("Body Lotion Avocado");
        clothe.setImageSrc("/Accessories/SkinCare/BodyLotion.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Bath & Body");
        clothe.setPrice(11699);
        lsa4.add(clothe);

        clothe = new Clothes();
        clothe.setName("Brownie Lip Balm");
        clothe.setImageSrc("/Accessories/SkinCare/BrownieLipBalm.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Bath & Body");
        clothe.setPrice(11699);
        lsa4.add(clothe);

        clothe = new Clothes();
        clothe.setName("Face mask");
        clothe.setImageSrc("/Accessories/SkinCare/FaceMask.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Bath & Body");
        clothe.setPrice(11699);
        lsa4.add(clothe);

        clothe = new Clothes();
        clothe.setName("Face Moisturisers");
        clothe.setImageSrc("/Accessories/SkinCare/FaceMoisturisers.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Bath & Body");
        clothe.setPrice(11699);
        lsa4.add(clothe);

        clothe = new Clothes();
        clothe.setName("Sun Screen");
        clothe.setImageSrc("/Accessories/SkinCare/SunScreen.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Bath & Body");
        clothe.setPrice(11699);
        lsa4.add(clothe);

        return lsa4;

    }

    private List<Clothes> haircare() {
        List<Clothes> lsa3 = new ArrayList<>();

        Clothes clothe = new Clothes();

        clothe.setName("Hair serum");
        clothe.setImageSrc("/Accessories/HairCare/Bath&BodyWORKS.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Bath & Body");
        clothe.setPrice(11699);
        lsa3.add(clothe);

        clothe = new Clothes();
        clothe.setName("Hair Cream");
        clothe.setImageSrc("/Accessories/HairCare/HairCream.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Bath & Body");
        clothe.setPrice(11699);
        lsa3.add(clothe);

        clothe = new Clothes();
        clothe.setName("Hair Conditioner");
        clothe.setImageSrc("/Accessories/HairCare/HairConditioner.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Bath & Body");
        clothe.setPrice(11699);
        lsa3.add(clothe);

        clothe = new Clothes();
        clothe.setName("Hair serum");
        clothe.setImageSrc("/Accessories/HairCare/HairSerum.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Bath & Body");
        clothe.setPrice(11699);
        lsa3.add(clothe);

        clothe = new Clothes();
        clothe.setName("Hair spray");
        clothe.setImageSrc("/Accessories/HairCare/HairSpray.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Bath & Body");
        clothe.setPrice(11699);
        lsa3.add(clothe);


        return lsa3;
    }


    private List<Clothes> fragrance() {
        List<Clothes> lsa2 = new ArrayList<>();

        Clothes clothe = new Clothes();

        //Image img = new Image("/Accessories/Fragrance/AQUA_ALGORIA_GODESS.png");
        clothe.setName("AQUA ALGORIA GODESS");
        clothe.setImageSrc("/Accessories/Fragrance/AQUA_ALGORIA_GODESS.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By GODESS");
        clothe.setPrice(11699);
        lsa2.add(clothe);

        clothe = new Clothes();
        clothe.setName("Ether");
        clothe.setImageSrc("/Accessories/Fragrance/CHANEL_ETHER.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Chanel");
        clothe.setPrice(11699);
        lsa2.add(clothe);

        clothe = new Clothes();
        clothe.setName("Daisy");
        clothe.setImageSrc("/Accessories/Fragrance/DAISY_MARKJACOBS.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Mark Jacobs");
        clothe.setPrice(11699);
        lsa2.add(clothe);

        clothe = new Clothes();
        clothe.setName("Theone");
        clothe.setImageSrc("/Accessories/Fragrance/DOLCE_GABANA_THEONE.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Dolce Gabana");
        clothe.setPrice(11699);
        lsa2.add(clothe);

        clothe = new Clothes();
        clothe.setName("The SIN");
        clothe.setImageSrc("/Accessories/Fragrance/JEANE_LANVIN_THESIN.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By Jeane Lanvin");
        clothe.setPrice(11699);
        lsa2.add(clothe);



        return lsa2;
    }



    private List<Clothes> appliance() {
        List<Clothes> lsa1 = new ArrayList<>();

        Clothes clothe = new Clothes();
        clothe.setName("Electric Hair Brush");
        clothe.setImageSrc("/Accessories/Appliances/ElectricHairBrush.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By OCA");
        clothe.setPrice(11699);
        lsa1.add(clothe);

        clothe = new Clothes();
        clothe.setName("Epilator");
        clothe.setImageSrc("/Accessories/Appliances/epilator.jpg");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By OCA");
        clothe.setPrice(11699);
        lsa1.add(clothe);

        clothe = new Clothes();
        clothe.setName("Hair Straightener");
        clothe.setImageSrc("/Accessories/Appliances/Hair_Straigthner.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By OCA");
        clothe.setPrice(11699);
        lsa1.add(clothe);

        clothe = new Clothes();
        clothe.setName("Hair Dryer");
        clothe.setImageSrc("/Accessories/Appliances/HairDryer.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By OCA");
        clothe.setPrice(11699);
        lsa1.add(clothe);

        clothe = new Clothes();
        clothe.setName("Hair Trimmer");
        clothe.setImageSrc("/Accessories/Appliances/HairTimmer.png");
        clothe.setImageSrc2("/image2/rupeeSymbol.jpg");
        clothe.setBrand("By OCA");
        clothe.setPrice(11699);
        lsa1.add(clothe);


        return lsa1;
    }



}
