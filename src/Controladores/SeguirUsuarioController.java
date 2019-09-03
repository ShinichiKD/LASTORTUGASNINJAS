package Controladores;

import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import twitter4j.ResponseList;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */
public class SeguirUsuarioController implements Initializable {

    BotTwitter Bot;
    Animaciones Animacion;
    
    @FXML
    private StackPane Escena;
    @FXML
    private AnchorPane AnchoPane;
    private Button BotonCerrar;
    @FXML
    private Label AvisosLabel;
    @FXML
    private Button SeguirBoton;
    @FXML
    private JFXTextField SeguirTF;
    
//    ObservableList<String> items =FXCollections.observableArrayList();
//    
//    @FXML
//    private ListView<String> Prueba = new ListView();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Bot = new BotTwitter();
//        Prueba.setEditable(true);
        Animacion = new Animaciones();
        AvisosLabel.setVisible(false);
        
        
    }    
    
    
    
    @FXML
    private void SeguirPresionar() throws TwitterException, IOException {
       try{
           
            Bot.seguirUsuario(SeguirTF.getText());
            SeguirTF.clear();
            SeguirTF.setPromptText("Ingresar ID");
            //Aviso sergui
            AvisosLabel.setText("Siguiendo al Usuario Correctamente.");
            Animacion.MostrarAvisos(AvisosLabel);
            
            
       }catch(TwitterException e){
            System.out.println(e.getErrorCode());
            System.out.println(e.getErrorMessage());
            if (e.getErrorCode() == 108) {
                AvisosLabel.setText("Busqueda no encontrada: No se puedo seguir.");
                Animacion.MostrarAvisos(AvisosLabel);
               
           }
       }
       
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
    
}
