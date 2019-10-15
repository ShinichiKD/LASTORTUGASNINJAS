package Controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import twitter4j.DirectMessage;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

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
    private Label Contador;
    @FXML
    private Button TwittearBoton;
    @FXML
    private Label AvisosLabel;
    @FXML
    private JFXTextField BuscarTF;
    
    @FXML 
    private ListView<String> BuscarListView;
    
    ObservableList<String> items =FXCollections.observableArrayList();
    public User PersonaBuscada;
    
    @FXML
    private JFXButton botonInicio;
    @FXML
    private Pane MenuInicio;
    @FXML
    private ScrollPane TimeLineInicio;
    @FXML
    
    private Pane MenuBuscar;
    @FXML
    private ImageView ImagenPerfil;
    @FXML
    private Label Nombre;
    @FXML
    private Label Identificador;
    @FXML
    private ScrollPane TimeLinePersona;
    @FXML
    private JFXButton botonBuscar;
    @FXML
    private Pane informacion;
    @FXML
    private JFXButton botonMensaje;
    @FXML
    private Pane MenuMensaje;
    
    private User PersonaMensaje;
    
     StatusListener listener = new StatusListener() {
            @Override
            public void onStatus(Status status) {
                Platform.runLater(
                () -> {
                    try {
                        Bot.timeLine(TimeLineInicio,1,status);
                        AvisosLabel.setText("Nuevo tweet!, de: "+status.getUser().getName());
                        Animacion.MostrarAvisos(AvisosLabel);
                    } catch (TwitterException ex) {
                        Logger.getLogger(MenuInicioController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(MenuInicioController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
              );
                
            }       

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
                System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            @Override
            public void onStallWarning(StallWarning warning) {
                System.out.println("Got stall warning:" + warning);
            }

            @Override
            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        };
    @FXML
    private ListView ListaUsuarios;
    @FXML
    private ScrollPane Chat;
    @FXML
    private JFXTextArea TextoMensaje;
    @FXML
    private JFXButton BotonEnviar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Bot = new BotTwitter();
        initStream();
        Animacion= new Animaciones();
        AvisosLabel.setVisible(false);
        BuscarListView.setVisible(false);
        BuscarListView = new ListView<String>();
        botonInicio.setStyle("-fx-background-color: #9d6da5;");
        botonInicio.textFillProperty().setValue(Paint.valueOf("white"));
        botonInicio.ripplerFillProperty().setValue(Paint.valueOf("white"));
        
        informacion.setVisible(false);
       
        try {
            CargaVentanaChat(Bot.obtenerMensajesDirectos());
        } catch (TwitterException ex) {
            Logger.getLogger(MenuInicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            ActualizarEstados();
            
        } catch (TwitterException ex) {
            if(ex.getErrorCode() == 88){
                AvisosLabel.setText("Error con limites de la API : Cierre el programa y espere");
                try {
                    Animacion.MostrarAvisos(AvisosLabel);
                } catch (IOException ex1) {
                    System.out.println(ex1.getCause());
                }
            }
            System.out.println();
        } catch (IOException ex) {
            System.out.println(ex.getCause());
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
        
        Bot.timeLine(TimeLineInicio,0,null);
        
    }

    @FXML
    private void Salir(ActionEvent event) {
        System.exit(0);
    }
    
    @FXML 
    private void SeleccionarItem() throws TwitterException {
        if (BuscarListView.getSelectionModel().getSelectedItem()!=null) {
            System.out.println("usuario seleccionado");
            
            informacion.setVisible(true);
            
            BuscarTF.setText(BuscarListView.getSelectionModel().getSelectedItem());
            PersonaBuscada= Bot.BuscarUsuario(BuscarTF.getText());
            
            Nombre.setText(PersonaBuscada.getName());
            Identificador.setText("@"+PersonaBuscada.getScreenName());
            ImagenPerfil.setImage(new Image(PersonaBuscada.get400x400ProfileImageURL()));
            Bot.timeLineBuscado(PersonaBuscada, TimeLinePersona);
            
            BuscarListView.setVisible(false);
        }
        
    }

    @FXML
    private void BuscarPersona() throws IOException, TwitterException {
        try{
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
            
        }catch(Exception e){
            AvisosLabel.setText("Usuario no encontrado");
            Animacion.MostrarAvisos(AvisosLabel);
        }
        
        
    }

    @FXML
    private void SubirArchivo() throws TwitterException, IOException {
        try{
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter fileExtensions = 
                new FileChooser.ExtensionFilter(
                  "Imagenes", "*.jpg", "*.PNG", "*.GIF");
            fileChooser.getExtensionFilters().add(fileExtensions);
            File selectedFile = fileChooser.showOpenDialog(new Stage());
            Bot.agregarArchivo(selectedFile);
            AvisosLabel.setText("Archivo subido correctamente");
            Animacion.MostrarAvisos(AvisosLabel);
        }catch(Exception e){
            AvisosLabel.setText("Archivo subido no subido, formato incorrecto");
            Animacion.MostrarAvisos(AvisosLabel);
        }
        
    }


    @FXML
    private void volverInicio(ActionEvent event) {
        
            
            if(MenuBuscar.isVisible()){
                botonBuscar.setStyle("-fx-background-color: #ededed;");
                botonBuscar.textFillProperty().setValue(Paint.valueOf("#545454"));
                botonBuscar.ripplerFillProperty().setValue(Paint.valueOf("#9d6da5"));
                MenuBuscar.setVisible(false);
            }
            
            if(MenuMensaje.isVisible()){
                botonMensaje.setStyle("-fx-background-color: #ededed;");
                botonMensaje.textFillProperty().setValue(Paint.valueOf("#545454"));
                botonMensaje.ripplerFillProperty().setValue(Paint.valueOf("#9d6da5"));
                MenuMensaje.setVisible(false);
            }
            MenuInicio.setVisible(true);
            botonInicio.setStyle("-fx-background-color: #9d6da5;");
            botonInicio.textFillProperty().setValue(Paint.valueOf("white"));
            botonInicio.ripplerFillProperty().setValue(Paint.valueOf("white"));
        
    }

    @FXML
    private void SeguirUsuario(ActionEvent event) throws TwitterException, IOException {
        Bot.seguirUsuario(PersonaBuscada.getScreenName());
        AvisosLabel.setText("Siguiendo al Usuario Correctamente.");
        Animacion.MostrarAvisos(AvisosLabel);
    }

    @FXML
    private void menuBuscar(ActionEvent event) {
        
            
            
            if(MenuInicio.isVisible()){
                botonInicio.setStyle("-fx-background-color: #ededed;");
                botonInicio.textFillProperty().setValue(Paint.valueOf("#545454"));
                botonInicio.ripplerFillProperty().setValue(Paint.valueOf("#9d6da5"));
                MenuInicio.setVisible(false);
            }
            
            if(MenuMensaje.isVisible()){
                botonMensaje.setStyle("-fx-background-color: #ededed;");
                botonMensaje.textFillProperty().setValue(Paint.valueOf("#545454"));
                botonMensaje.ripplerFillProperty().setValue(Paint.valueOf("#9d6da5")); 
                MenuMensaje.setVisible(false);
            }
            MenuBuscar.setVisible(true);
            botonBuscar.setStyle("-fx-background-color: #9d6da5;");
            botonBuscar.textFillProperty().setValue(Paint.valueOf("white"));
            botonBuscar.ripplerFillProperty().setValue(Paint.valueOf("white"));
    }

    @FXML
    private void menuMensaje(ActionEvent event) {
        
            if(MenuInicio.isVisible()){
                botonInicio.setStyle("-fx-background-color: #ededed;");
                botonInicio.textFillProperty().setValue(Paint.valueOf("#545454"));
                botonInicio.ripplerFillProperty().setValue(Paint.valueOf("#9d6da5"));
                MenuInicio.setVisible(false);
            }
            
            if(MenuBuscar.isVisible()){
                botonBuscar.setStyle("-fx-background-color: #ededed;");
                botonBuscar.textFillProperty().setValue(Paint.valueOf("#545454"));
                botonBuscar.ripplerFillProperty().setValue(Paint.valueOf("#9d6da5"));
                MenuBuscar.setVisible(false);
            }
            
            MenuMensaje.setVisible(true);
            botonMensaje.setStyle("-fx-background-color: #9d6da5;");
            botonMensaje.textFillProperty().setValue(Paint.valueOf("white"));
            botonMensaje.ripplerFillProperty().setValue(Paint.valueOf("white"));
            
    }
    private void initStream() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey(BotTwitter.CK)
          .setOAuthConsumerSecret(BotTwitter.CS)
          .setOAuthAccessToken(BotTwitter.AT)
          .setOAuthAccessTokenSecret(BotTwitter.TS);
        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        
        twitterStream.addListener(listener);
        FilterQuery filtre = new FilterQuery();
        String[] keywordsArray = { "AlmostHumanBot" };
        filtre.track(keywordsArray);
        twitterStream.filter(filtre);
    }
    long idMensaje;
    private void CargaVentanaChat(ArrayList<ArrayList<DirectMessage>> mensajesDirectos) throws TwitterException {
        for(ArrayList<DirectMessage> lista : mensajesDirectos){
            User user;
            long senderId = lista.get(0).getSenderId();
            if(senderId != Bot.getId()){
                user = Bot.getUser(senderId);
            }else{
                user = Bot.getUser(lista.get(0).getRecipientId());
            }
            JFXButton usuario = new JFXButton(user.getName());
            //boton
            ListaUsuarios.getItems().add(usuario);
            
            usuario.setOnAction((ActionEvent events)->{
                int i=0;
                PersonaMensaje = user;
                GridPane mensajes = new GridPane();
                
                for(DirectMessage dm : lista){
                    GridPane gridAux = new GridPane();
                    gridAux.setPrefWidth(690);
                    gridAux.setPadding(new Insets(5, 5, 5, 5)); 
                    if (user.getId()==dm.getRecipientId()) {
                        JFXButton miMensaje = new JFXButton(dm.getText());
                        miMensaje.wrapTextProperty().set(true);
                        miMensaje.setStyle("-fx-font-size: 15px; -fx-background-color: #9D6DA5; -fx-font: Microsoft YaHei Light;");
                        miMensaje.textFillProperty().setValue(Paint.valueOf("white"));
                        miMensaje.ripplerFillProperty().setValue(Paint.valueOf("white"));
                        Label espacio = new Label();
                        espacio.setPrefWidth(348);
                        gridAux.add(espacio,0,0);
                        gridAux.add(miMensaje,1,0);
                        gridAux.alignmentProperty().set(Pos.TOP_RIGHT);
                    }
                    else{
                        JFXButton suMensaje = new JFXButton(dm.getText());
                        suMensaje.wrapTextProperty().set(true);
                        
                        suMensaje.setStyle("-fx-font-size: 15px; -fx-background-color: #826DB3; -fx-font: Microsoft YaHei Light;");
                        suMensaje.textFillProperty().setValue(Paint.valueOf("white"));
                        suMensaje.ripplerFillProperty().setValue(Paint.valueOf("white"));
                        
                        gridAux.add(suMensaje,0,0);
                        gridAux.add(new Label(),1,0);
                        
                    }

                    i++;
                    mensajes.add(gridAux, 0, i);
                }
                
                GridPane MensajesBien = new GridPane();
                int max= i-1;
                for (int j = 0; j < max; j++) {
                    MensajesBien.add(mensajes.getChildren().get((max-j)),0,j);
                    
                }
                MensajesBien.add(mensajes.getChildren().get(0),0,max);
                
                Chat.setContent(MensajesBien);
                Chat.setHvalue(1000000);
                
            });
            
        }
    }

    @FXML
    private void EnviarMensaje(ActionEvent event) throws IOException {
        try{
                
                Bot.enviarMensajeDirecto(PersonaMensaje.getScreenName(), TextoMensaje.getText()); 
                TextoMensaje.clear();
                TextoMensaje.setPromptText("Escribir Mensaje");
                // Mensaje enviado con exito(3)
                AvisosLabel.setText("Mensaje Enviado Correctamente.");
                Animacion.MostrarAvisos(AvisosLabel);
            
        }
        catch(TwitterException e){
            System.out.println(e.getErrorCode());
            System.out.println(e.getErrorMessage());
            switch (e.getErrorCode()) {
                case 50:
                    AvisosLabel.setText("Usuario no encontrado: No se pudo enviar mensaje.");
                    Animacion.MostrarAvisos(AvisosLabel);
                    break;
                case 151:
                    AvisosLabel.setText("Mensaje en blanco: No se puede enviar.");
                    Animacion.MostrarAvisos(AvisosLabel);
                    break;
                case 349:
                    AvisosLabel.setText("No puedes enviar mensajes a este usuario.");
                    Animacion.MostrarAvisos(AvisosLabel);
                    break;
                default:
                    break;
            }
        }
        
    }

    @FXML
    private void ReleasedTextoMensaje(KeyEvent event) {
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
