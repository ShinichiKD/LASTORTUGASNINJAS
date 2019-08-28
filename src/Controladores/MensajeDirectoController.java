package Controladores;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import twitter4j.TwitterException;

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */
public class MensajeDirectoController implements Initializable {

    BotTwitter Bot;  
    
    Animaciones Animacion;
    
    @FXML
    private StackPane Escena;
    @FXML
    private AnchorPane AnchoPane;
    @FXML
    private Button BotonCerrar;
    @FXML
    private TextArea TextoMensaje;
    @FXML
    private Button BotonEnviar;
    @FXML
    private Label Contador;
    @FXML
    private Label AvisosLabel;
    @FXML
    private JFXTextField PersonaBuscada;
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       Bot = new BotTwitter();
       Animacion = new Animaciones(); 
       AvisosLabel.setVisible(false);
      
       
    }    
    /**
     * Cierra la ventana actual y vuelve al Inicio
     * @param event
     * @throws IOException 
     */
    @FXML
    private void CerrarVentana() throws IOException {
        Animacion.CerrarVentana(Escena, BotonCerrar, AnchoPane, "/Vistas/MenuInicio.fxml");
    }

    @FXML
    private void EnviarMensaje() throws IOException {
        // pasar el texto del mensaje a la funcion de enviar mensaje del bot
        try{
                Bot.enviarMensajeDirecto(PersonaBuscada.getText(), TextoMensaje.getText()); 
                TextoMensaje.clear();
                TextoMensaje.setPromptText("Escribir Mensaje");
                // Mensaje enviado con exito(3)
                AvisosLabel.setText("Mensaje Enviado Correctamente.");
                Animacion.MostrarAvisos(AvisosLabel);
            
        }
        catch(TwitterException e){
            System.out.println(e.getErrorCode());
            System.out.println(e.getErrorMessage());
            if (e.getErrorCode()== 50) {
                AvisosLabel.setText("Usuario no encontrado: No se pudo enviar mensaje.");
                Animacion.MostrarAvisos(AvisosLabel);
                
            }else if (e.getErrorCode()==151) {
                AvisosLabel.setText("Mensaje en blanco: No se puede enviar.");
                Animacion.MostrarAvisos(AvisosLabel);
                
            }
        }
    }
    /**
     * Validacion 10000 caracteres
     * @param event 
     */
    @FXML
    private void ReleasedTextoMensaje() {
        int letras = TextoMensaje.getText().length();
        int limite = 10000;
        if(letras > limite){
            //cambiar color
            Contador.textFillProperty().setValue(Paint.valueOf("Red"));
            BotonEnviar.setDisable(true);

        }else{
            //cambiar color
            Contador.textFillProperty().setValue(Paint.valueOf("White"));
            BotonEnviar.setDisable(false);
        }
        Contador.setText(letras+" / "+limite);
        
        
    }


    
    
    
}
