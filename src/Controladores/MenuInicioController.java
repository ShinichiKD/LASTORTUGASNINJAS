package Controladores;

import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */
public  class MenuInicioController implements Initializable {
    
 
    BotTwitter Bot;  
    
    Animaciones Animacion;
    
    @FXML
    private TextArea MensajeTA;
    @FXML
    private StackPane Escena;
    @FXML
    private AnchorPane AnchoPane;
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
    @FXML
    private JFXTextField BuscarTF;
    @FXML
    private ListView<String> BuscarListView = new ListView<String>();
    
    ObservableList<String> items =FXCollections.observableArrayList();
    
    public static User PersonaBuscada;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Bot = new BotTwitter();
        Animacion= new Animaciones();
        AvisosLabel.setVisible(false);
        BuscarListView.setVisible(false);

        
        try {
            if(proyectopuntosflotantes.ProyectoPuntosFlotantes.CARGADO_INICIO){
                ActualizarEstados();
            }
            proyectopuntosflotantes.ProyectoPuntosFlotantes.CARGADO_INICIO=false;
            ScrollPane.setContent(proyectopuntosflotantes.ProyectoPuntosFlotantes.GRID);
            
        } catch (TwitterException ex) {
            System.out.println(ex.getMessage());
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
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
            
        } catch (TwitterException | IOException ex) {
            Logger.getLogger(MenuInicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
        }catch(TwitterException e){
            System.out.println(e.getErrorCode());
            System.out.println(e.getErrorMessage());
            switch (e.getErrorCode()) {
                case 170:
                    AvisosLabel.setText("No se ha podido publicar: Mensaje en blanco.");
                    Animacion.MostrarAvisos(AvisosLabel);
                    break;
                case 187:
                    AvisosLabel.setText("No se ha podido publicar: Publicación duplicada.");
                    Animacion.MostrarAvisos(AvisosLabel);
                    break;
                case 186:
                    AvisosLabel.setText("No se ha podido publicar: Publicación muy larga ");
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

    /**
     * Esta Accion nos permite cambiar de escena y mostrar la interfaz de 
     * Mensaje Directo
     * @param event
     * @throws IOException 
     */
    
    @FXML
    private void MenuMensajeDirecto() throws IOException {
        
        Animacion.CambiarVentanta(Escena,"/Vistas/MensajeDirecto.fxml");
        
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
            Contador.textFillProperty().setValue(Paint.valueOf("White"));
            TwittearBoton.setDisable(false);
        }
        Contador.setText(letras+" / "+limite);
        
    }
    void ActualizarEstados() throws TwitterException, IOException{
        int i=0;
        GridPane grid = new GridPane();
        if(Bot.obtenerTweets()==null){
            return;
        }
        for(Status e : Bot.obtenerTweets()){

            Parent root = FXMLLoader.load(getClass().getResource("/Vistas/Publicacion.fxml"));
            
            
            
            ((Label)root.getChildrenUnmodifiable().get(0)).setText(e.getUser().getName() );
            
            ((TextArea)root.getChildrenUnmodifiable().get(1)).setText(e.getText());
            
            ((ImageView)root.getChildrenUnmodifiable().get(2)).setImage(new Image( e.getUser().get400x400ProfileImageURL() ));
            
            ((Label)root.getChildrenUnmodifiable().get(3)).setText(e.getCreatedAt().toString() );
            
            ((Button)root.getChildrenUnmodifiable().get(4)).setOnAction((ActionEvent events)->{ 
                try {
                    if(Bot.darLikeTweet(e.getId())){
                        AvisosLabel.setText("Te ha gustado esta publicación");
                        Animacion.MostrarAvisos(AvisosLabel);
                    }else{
                        AvisosLabel.setText("Ya no te gusta esta publicación");
                        Animacion.MostrarAvisos(AvisosLabel);
                    }
                
                
                } catch (TwitterException | IOException ex) {
                    System.out.println(ex.getMessage());
                }
            });
            
            
            ((Button)root.getChildrenUnmodifiable().get(5)).setOnAction((ActionEvent events)->{ 
                try {
                    if(Bot.darRetweet(e.getId())){
                        AvisosLabel.setText("Has retweeteado esta publicación");
                        Animacion.MostrarAvisos(AvisosLabel);
                    }else{
                        AvisosLabel.setText("Ya no retweeteas esta publicación");
                        Animacion.MostrarAvisos(AvisosLabel);
                    }
                } catch (TwitterException | IOException ex) {
                    System.out.println(ex.getMessage());
                }
            });
            
            try{
                ((ImageView)root.getChildrenUnmodifiable().get(6)).setImage(new Image( e.getMediaEntities()[0].getMediaURL() ));
            }catch(Exception ex){}
            
           
            
            grid.add(root, 0, i);
            i++;
        }
        
        
        ScrollPane.setContent(grid);
        proyectopuntosflotantes.ProyectoPuntosFlotantes.GRID = grid;
    }

    @FXML
    private void Salir(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void BuscarAC() throws TwitterException {
        ResponseList <User> ListaUsuarios ;
        BuscarListView.getItems().clear();
        BuscarListView.setVisible(true);

        if (!BuscarTF.getText().isEmpty()) {
            ArrayList<String> Aux=Bot.BuscarEnTwitter(BuscarTF.getText());
            items.addAll(Aux);
            BuscarListView.setItems(items);
        } 

         else{
            BuscarListView.setVisible(false);
        }
    }

    @FXML
    private void SeleccionarItem() {
        if (BuscarListView.getSelectionModel().getSelectedItem()!=null) {
            System.out.println("usuario seleccionado");
            
            BuscarTF.setText(BuscarListView.getSelectionModel().getSelectedItem());
            
            BuscarListView.setVisible(false);
        }
        
    }

    @FXML
    private void BuscarPersona() throws IOException, TwitterException {
        try{
            PersonaBuscada= Bot.BuscarUsuario(BuscarTF.getText());
            Animacion.CambiarVentanta(Escena, "/Vistas/PersonaBuscada.fxml"); 
        }catch(Exception e){
            AvisosLabel.setText("Usuario no encontrado");
            Animacion.MostrarAvisos(AvisosLabel);
        }
        
        
    }

    @FXML
    private void SubirArchivo() throws TwitterException {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        Bot.agregarArchivo(selectedFile);
    }



    

    
   
}
