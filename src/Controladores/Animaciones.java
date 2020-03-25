package Controladores;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 *
 * @author Rodrigo
 */
public class Animaciones {
    
    public void CambiarVentanta(StackPane Escena,String ruta) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource(ruta));
        
        root.translateXProperty().set(1020);
        
        Escena.getChildren().add(root);
        
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(), 0);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.5), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
        
        });
        timeline.play();
    }
    public static void MostrarAvisos(JFXButton Aviso, String color) throws IOException{
        
        Aviso.setVisible(true);
        Aviso.setStyle("-fx-background-color: "+color+";");
        Aviso.translateYProperty().set(600);
        
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(Aviso.translateYProperty(), 0);
        KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);
        
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
            
        });
        timeline.play();
        
        CerrarAviso(Aviso);
        
    }
    
    private static void CerrarAviso(JFXButton Aviso){
        
        Timeline timeline = new Timeline();
        KeyFrame kf = new KeyFrame(Duration.seconds(5));
        
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(t -> {
            Aviso.setVisible(false);
        });
        timeline.play();
        
    }

    

}
