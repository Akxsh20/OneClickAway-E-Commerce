package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;
import java.util.Objects;

public class ClotheContController {

    @FXML
    private ImageView ProductImage;

    @FXML
    private  Label productName;

    @FXML
    private Label ProductBrand;


    private List<Clothes> recommended;

    public void setData(Clothes clothe){
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(clothe.getImageSrc())));
        ProductImage.setImage(image);
        productName.setText(clothe.getName());
        ProductBrand.setText(clothe.getBrand());

    }
}
