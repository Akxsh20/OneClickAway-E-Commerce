package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OfferCardController {

    @FXML
    private HBox box;

    @FXML
    private ImageView Clotheimage;

    @FXML
    private Label ClotheType;

    @FXML
    private Label Brand;

    private List<Clothes> recentlyAdded;


    private String[] colors = {"B9E5FF","BDB2FE","FB9AA8","FF5056"};

    public void setData(Clothes clothe){

        Image image = new Image(getClass().getResourceAsStream(clothe.getImageSrc()));
        Clotheimage.setImage(image);

        ClotheType.setText(clothe.getName());

        Brand.setText(clothe.getBrand());

        box.setStyle("-fx-background-color: #" + colors[(int)(Math.random()*colors.length)] + ";"
                + " -fx-background-radius: 15;"
                + " -fx-effect:dropShadow(three-pass-box,rgba(0,0,0,0.2), 10, 0, 0, 10)");

    }


}

