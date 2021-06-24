package sample;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class animations {

    private TranslateTransition translateTransition;

    // created a constructor
    //i can pass any node as argument to apply the given animations
    //we create or describe the type of animation
    public animations(Node node){
        // to move back and front if an error occurs while login in
        translateTransition = new TranslateTransition(Duration.millis(60),node);
        translateTransition.setFromX(0f);
        translateTransition.setByX(15f);
        translateTransition.setCycleCount(2); // how many times the TF should move back and forth
        translateTransition.setAutoReverse(true); // made true so that it can move backwards

    }


    // this plays the animation
    public void shake(){
        translateTransition.playFromStart();
    }





}
