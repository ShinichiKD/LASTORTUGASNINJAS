package Controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */
public class MensajeDirectoController implements Initializable {

    BotTwitter Bot;  
    
    @FXML
    private StackPane Escena;
    @FXML
    private AnchorPane AnchoPane;
    @FXML
    private TextArea PersonaBuscada;
    @FXML
    private Button BotonCerrar;
    @FXML
    private TextArea TextoMensaje;
    @FXML
    private Button BotonEnviar;
    @FXML
    private Label Contador;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       Bot = new BotTwitter();
    }    
    /**
     * Cierra la ventana actual y vuelve al Inicio
     * @param event
     * @throws IOException 
     */
    @FXML
    private void CerrarVentana() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/Vistas/MenuInicio.fxml"));
        Scene scene = BotonCerrar.getScene();
        root.translateYProperty().set(scene.getHeight());
        
        
        Escena.getChildren().add(root);
        
        Escena.getChildren().remove(AnchoPane);
        Escena.setVisible(false);
    }

    @FXML
    private void EnviarMensaje() throws IOException {
        // pasar el texto del mensaje a la funcion de enviar mensaje del bot
        try{
           Bot.enviarMensajeDirecto(PersonaBuscada.getText(), TextoMensaje.getText()); 
           TextoMensaje.clear();
           TextoMensaje.setPromptText("Escribir Mensaje");
           // Mensaje enviado con exito(3)
            CerrarVentana();
        }
        catch(Exception e){
            // popup de error aqui se despliegan las interfaces de error, (1)mensaje en blanco, (2)destinatario no nos sigue
            System.out.println(e.getMessage());
            
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
            Contador.textFillProperty().setValue(Paint.valueOf("Black"));
            BotonEnviar.setDisable(false);
        }
        Contador.setText(letras+" / "+limite);
        
        
    }
    
    
    
}
