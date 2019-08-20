package Controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import twitter4j.TwitterException;

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */
public class MenuInicioController implements Initializable {
    
 
    BotTwitter Bot;  
    
    Animaciones Animacion;
    
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
    @FXML
    private Pane AvisosPanel;
    @FXML
    private Label AvisosLabel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Bot = new BotTwitter();
        Animacion= new Animaciones();
        
    }    
    @FXML
    private void PublicarPresionar() throws TwitterException {     
        try{
            
            String texto = MensajeTA.getText();
            MensajeTA.clear();
            MensajeTA.setPromptText("¿Qué está pasando?");
            Bot.actualizarEstado(texto); 
            Contador.setText(0+" / "+280);
            
        }catch(TwitterException e){
            System.out.println(e.getErrorCode());
            System.out.println(e.getErrorMessage());
            if (e.getErrorCode()==170) {
                AvisosLabel.setText("Mensaje en blanco.");
                AvisosPanel.setVisible(true);
            }
            else if (e.getErrorCode()==187) {
                AvisosLabel.setText("Publicación duplicada.");
                AvisosPanel.setVisible(true);
            }
        }
        
                
    }
    /**
     * Esta Accion nos permite cambiar de escena y mostrar la interfaz de 
     * Seguir Usuario
     * @param event
     * @throws IOException 
     */
    @FXML
    private void MenuSeguirUsuario() throws IOException {
        
        Animacion.CambiarVentanta(Escena, TwittearBoton, AnchoPane, "/Vistas/SeguirUsuario.fxml");
        
    }
    /**
     * Esta Accion nos permite cambiar de escena y mostrar la interfaz de 
     * Mensaje Directo
     * @param event
     * @throws IOException 
     */
    @FXML
    private void MenuMensajeDirecto() throws IOException {
        
        Animacion.CambiarVentanta(Escena, TwittearBoton, AnchoPane, "/Vistas/MensajeDirecto.fxml");
        
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
