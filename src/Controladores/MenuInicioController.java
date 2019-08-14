package Controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
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
    private TextArea MensajeTA;
    @FXML
    private StackPane Escena;
    @FXML
    private AnchorPane AnchoPane;
    @FXML
    private Button SeguirUsuario;
    @FXML
    private Button MensajeDirecto;
    @FXML
    private Label Contador;
    @FXML
    private Button TwittearBoton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Bot = new BotTwitter();
        
    }    
    @FXML
    private void PublicarPresionar() throws TwitterException {       
        String texto = MensajeTA.getText();
        MensajeTA.clear();
        MensajeTA.setPromptText("¿Qué está pansando?");
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
        Contador.setText(0+" / "+280);
        
    }
    /**
     * Validacion 280 caracteres
     * @param event 
     */
    @FXML
    private void ReleasedMensajeTA() {
        int letras = MensajeTA.getText().length();
        int limite = 280;
        if(letras > limite){
             //cambiar color
             Contador.textFillProperty().setValue(Paint.valueOf("Red"));
             TwittearBoton.setDisable(true);

        }else{
            //cambiar color
            Contador.textFillProperty().setValue(Paint.valueOf("Black"));
            TwittearBoton.setDisable(false);
        }
        Contador.setText(letras+" / "+limite);
        
        
    }



    
    
}
