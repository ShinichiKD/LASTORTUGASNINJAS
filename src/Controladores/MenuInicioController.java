package Controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import twitter4j.Status;
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
    private Label AvisosLabel;
    @FXML
    private ScrollPane ScrollPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Bot = new BotTwitter();
        Animacion= new Animaciones();
        AvisosLabel.setVisible(false);
        try {
            ActualizarEstados();
        } catch (TwitterException ex) {
            Logger.getLogger(MenuInicioController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MenuInicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    @FXML
    private void PublicarPresionar() throws TwitterException, IOException {     
        try{
            
            String texto = MensajeTA.getText();
            Contador.setText(0+" / "+280);
            MensajeTA.clear();
            MensajeTA.setPromptText("¿Qué está pasando?");
            Bot.actualizarEstado(texto); 
            
            
            AvisosLabel.setText("Publicación exitosa.");
            Animacion.MostrarAvisos(AvisosLabel);
            try {
            ActualizarEstados();
        } catch (TwitterException ex) {
            Logger.getLogger(MenuInicioController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MenuInicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
        }catch(TwitterException e){
            System.out.println(e.getErrorCode());
            System.out.println(e.getErrorMessage());
            switch (e.getErrorCode()) {
                case 170:
                    AvisosLabel.setText("Mensaje en blanco.");
                    Animacion.MostrarAvisos(AvisosLabel);
                    break;
                case 187:
                    AvisosLabel.setText("Publicación duplicada.");
                    Animacion.MostrarAvisos(AvisosLabel);
                    break;
                case 186:
                    AvisosLabel.setText("Publicación muy larga (muchos emojis).");
                    Animacion.MostrarAvisos(AvisosLabel);
                    break;
                default:
                    break;
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
    void ActualizarEstados() throws TwitterException, IOException{
        int i=0;
        GridPane grid = new GridPane();
        for(Status e : Bot.obtenerTweets()){

            Parent root = FXMLLoader.load(getClass().getResource("/Vistas/Publicacion.fxml"));
            
            ((Label)root.getChildrenUnmodifiable().get(0)).setText(e.getUser().getName() );
            ((TextArea)root.getChildrenUnmodifiable().get(1)).setText(e.getText());
            ((ImageView)root.getChildrenUnmodifiable().get(2)).setImage(new Image( e.getUser().get400x400ProfileImageURL() ));
            
            grid.add(root, 0, i);
            i++;
        }
        
        
        ScrollPane.setContent(grid);
    }
}
