package Controladores;

import java.io.IOException;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 *
 * @author Rodrigo
 */
public class Animaciones {
    
    
    public void CerrarVentana(StackPane Escena,Button Boton,AnchorPane AnchoPane,String ruta) throws IOException{
        
        Parent root = FXMLLoader.load(getClass().getResource(ruta));
        Scene scene = Boton.getScene();
        root.translateYProperty().set(scene.getHeight());
        
        
        Escena.getChildren().add(root);
        
        Escena.getChildren().remove(AnchoPane);
        Escena.setVisible(false);
    }
    
    public void CambiarVentanta(StackPane Escena,Button Boton,AnchorPane AnchoPane,String ruta) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource(ruta));
        Scene scene= Boton.getScene();
        
        root.translateXProperty().set(scene.getHeight());
        
        Escena.getChildren().add(root);
        
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_OUT);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
        });
        timeline.play();
    }
            
}
