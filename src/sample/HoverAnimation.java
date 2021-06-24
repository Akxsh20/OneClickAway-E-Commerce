package sample;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class HoverAnimation {

    private TranslateTransition hover;

    public HoverAnimation(Node node){
        hover = new TranslateTransition(Duration.millis(40));
        hover.setFromX(0);
        hover.setFromY(0);
        hover.setByX(5f);
        hover.setByY(5f);
        hover.setCycleCount(3);
        hover.setAutoReverse(true);
    }

    public void Hover(){
        hover.playFromStart();
    }
}
