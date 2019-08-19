package Controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import twitter4j.TwitterException;

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */
public class SeguirUsuarioController implements Initializable {

    BotTwitter Bot;
    Animaciones Animacion;
    
    @FXML
    private TextArea SeguirTA;
    @FXML
    private Button SeguirBoton;
    @FXML
    private StackPane Escena;
    @FXML
    private AnchorPane AnchoPane;
    @FXML
    private Button BotonCerrar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Bot = new BotTwitter();
        Animacion = new Animaciones();
    }    
    
    
    
    @FXML
    private void SeguirPresionar() throws TwitterException {
       try{
           Bot.seguirUsuario(SeguirTA.getText());
            SeguirTA.clear();
            SeguirTA.setPromptText("Ingresar ID");
            CerrarVentana();
       }catch(Exception e){
           System.out.println(e.getMessage());
       }
       
       
    }
    
    /**
     * Cierra la ventana actual y vuelve al Inicio
     * @param event
     * @throws IOException 
     */
    @FXML
    private void CerrarVentana() throws IOException {
        Animacion.CerrarVentana(Escena, SeguirBoton, AnchoPane, "/Vistas/MenuInicio.fxml");
    }
    
}
