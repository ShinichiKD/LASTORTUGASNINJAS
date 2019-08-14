package Controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import twitter4j.TwitterException;

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */
public class MenuInicioController implements Initializable {
    
 
    BotTwitter Bot;  
    
    @FXML
    private TextField MensajeTF;
    @FXML
    private StackPane Escena;
    @FXML
    private AnchorPane AnchoPane;
    
    @FXML
    private Button SeguirUsuario;
    @FXML
    private Button MensajeDirecto;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Bot = new BotTwitter();
        
    }    
    @FXML
    private void PublicarPresionar() throws TwitterException {       
        String texto = MensajeTF.getText();
        MensajeTF.clear();
        MensajeTF.setPromptText("¿Qué está pansando?");
        Bot.actualizarEstado(texto); 
                
    }
    /**
     * Esta Accion nos permite cambiar de escena y mostrar la interfaz de 
     * Seguir Usuario
     * @param event
     * @throws IOException 
     */
    @FXML
    private void MenuSeguirUsuario() throws IOException {
        
        Parent root = FXMLLoader.load(getClass().getResource("/Vistas/SeguirUsuario.fxml"));
        Scene scene= SeguirUsuario.getScene();
        
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
    /**
     * Esta Accion nos permite cambiar de escena y mostrar la interfaz de 
     * Mensaje Directo
     * @param event
     * @throws IOException 
     */
    @FXML
    private void MenuMensajeDirecto() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Vistas/MensajeDirecto.fxml"));
        Scene scene= MensajeDirecto.getScene();
        
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

    @FXML
    private void TypedMensajeTF(KeyEvent event) {
        System.out.println(144-MensajeTF.getText().length()); 
    }


    
    
}
