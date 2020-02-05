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
import twitter4j.UserStreamListener;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.conf.ConfigurationBuilder;

/**
 * FXML Controller class
 *
 * @author Emilio
 */
public  class MenuInicioController implements Initializable {
    
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
    public ScrollPane TimeLinePersona;
    @FXML
    private JFXButton botonBuscar;
    @FXML
    private Pane informacion;
    @FXML
    private JFXButton botonMensaje;
    @FXML
    private Pane MenuMensaje;
    @FXML
    private ListView ListaUsuarios;
    @FXML
    private ScrollPane Chat;
    @FXML
    private JFXTextArea TextoMensaje;
    @FXML
    private JFXButton BotonEnviar;
    @FXML
    private ImageView img_inicio;
    @FXML
    private JFXButton BTNseguir;
    
    private User PersonaMensaje;
    private GridPane MensajesBien;
    private int max;
    ObservableList<String> items =FXCollections.observableArrayList();
    public User PersonaBuscada;
    BotTwitter Bot;  
    Animaciones Animacion;
    
    UserStreamListener listener = new UserStreamListener() {
             
        @Override
        public void onUnfavorite(User user, User user1, Status status) {
            System.out.println("hola");
        }

        @Override
        public void onUnfollow(User user, User user1) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onUserListMemberAddition(User user, User user1, UserList ul) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onUserListMemberDeletion(User user, User user1, UserList ul) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onUserListSubscription(User user, User user1, UserList ul) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onUserListUnsubscription(User user, User user1, UserList ul) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onUserListCreation(User user, UserList ul) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onUserListUpdate(User user, UserList ul) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onUserListDeletion(User user, UserList ul) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onUserSuspension(long l) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onUserDeletion(long l) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onRetweetedRetweet(User user, User user1, Status status) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onFavoritedRetweet(User user, User user1, Status status) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onQuotedTweet(User user, User user1, Status status) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

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
        public void onDeletionNotice(StatusDeletionNotice sdn) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onStallWarning(StallWarning sw) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onDeletionNotice(long l, long l1) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onFriendList(long[] longs) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onFavorite(User user, User user1, Status status) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onFollow(User user, User user1) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onDirectMessage(DirectMessage dm) {
            System.out.println(dm.getText());
        }

        @Override
        public void onUserProfileUpdate(User user) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onBlock(User user, User user1) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onUnblock(User user, User user1) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onTrackLimitationNotice(int i) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onScrubGeo(long l, long l1) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onException(Exception excptn) {
            System.out.println("gg");
        }
    }; 
    
    
    
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Bot = new BotTwitter();
        try {
            initStream();
        } catch (TwitterException ex) {
            Logger.getLogger(MenuInicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Animacion= new Animaciones();
        AvisosLabel.setVisible(false);
        botonInicio.setStyle("-fx-background-color: #9d6da5;");
        botonInicio.textFillProperty().setValue(Paint.valueOf("white"));
        botonInicio.ripplerFillProperty().setValue(Paint.valueOf("white"));
        
        try {
            System.out.println("se intento");
            img_inicio.setImage( new Image(Bot.getUser(Bot.getId()).get400x400ProfileImageURL()));
            System.out.println("no se pudo");
        } catch (TwitterException ex) {
            Logger.getLogger(MenuInicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        informacion.setVisible(false);
       
        try {
            CargaVentanaChat();
            System.out.println("Cargando chats...");
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
            
            if (Bot.sigueA(PersonaBuscada.getScreenName())){
                BTNseguir.setText("No seguir");
                BTNseguir.setStyle("-fx-background-color: #F6502E;");
            }else{
                BTNseguir.setText("Seguir");
                BTNseguir.setStyle("-fx-background-color: #00ffbb;");
            }
            
            Nombre.setText(PersonaBuscada.getName());
            Identificador.setText("@"+PersonaBuscada.getScreenName());
            ImagenPerfil.setImage(new Image(PersonaBuscada.get400x400ProfileImageURL()));
            Bot.timeLineBuscado(PersonaBuscada, TimeLinePersona);
            
        }
        
    }

    @FXML
    private void BuscarPersona() throws IOException, TwitterException {
        if(BuscarTF.getText().isEmpty()) return;
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
            BuscarListView.setVisible(false);
        
    }

    @FXML
    private void SeguirUsuario() throws TwitterException, IOException {
        System.out.println("Executando");
        Bot.seguirUsuario(PersonaBuscada.getScreenName());
        if(Bot.sigueA(PersonaBuscada.getScreenName())){
            BTNseguir.setText("No seguir");
            BTNseguir.setStyle("-fx-background-color: #F6502E;");
        }else{
            BTNseguir.setText("Seguir");
            BTNseguir.setStyle("-fx-background-color: #00ffbb;");
        }
                
                
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
            BuscarListView.setVisible(true);
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
            BuscarListView.setVisible(false);
            
    }
    private void initStream() throws TwitterException {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey(BotTwitter.CK)
          .setOAuthConsumerSecret(BotTwitter.CS)
          .setOAuthAccessToken(BotTwitter.AT)
          .setOAuthAccessTokenSecret(BotTwitter.TS);
        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        
        twitterStream.addListener(listener);
        FilterQuery filtre = new FilterQuery();
        String[] keywordsArray = { "Test36467081",
                Bot.getId()+""
        };
        
        filtre.track(keywordsArray);
        twitterStream.filter(filtre);
    }
    long idMensaje;
    private void CargaVentanaChat() throws TwitterException {
        for(ArrayList<DirectMessage> lista : Bot.obtenerMensajesDirectos()){
            User user;
            long senderId = lista.get(0).getSenderId();
            if(senderId != Bot.getId()){
                user = Bot.getUser(senderId);
            }else{
                user = Bot.getUser(lista.get(0).getRecipientId());
            }
            JFXButton usuario = new JFXButton(user.getName());
            ImageView foto = new ImageView(new Image(user.get400x400ProfileImageURL()));
            foto.setFitWidth(40);
            foto.setFitHeight(40);
            usuario.graphicProperty().set(foto);
            
            usuario.setPrefWidth(280);
            usuario.setAlignment(Pos.TOP_LEFT);
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
                        miMensaje.setStyle("-fx-font-size: 15px; -fx-background-color: #9D6DA5; -fx-font-family: Microsoft YaHei Light;");
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
                        
                        suMensaje.setStyle("-fx-font-size: 15px; -fx-background-color: #826DB3; -fx-font-family: Microsoft YaHei Light;");
                        suMensaje.textFillProperty().setValue(Paint.valueOf("white"));
                        suMensaje.ripplerFillProperty().setValue(Paint.valueOf("white"));
                        
                        gridAux.add(suMensaje,0,0);
                        gridAux.add(new Label(),1,0);
                    }

                    i++;
                    mensajes.add(gridAux, 0, i);
                }
                MensajesBien = new GridPane();
                
                max= i-1;
                for (int j = 0; j < max; j++) {
                    MensajesBien.add(mensajes.getChildren().get((max-j)),0,j);
                    
                }
                MensajesBien.add(mensajes.getChildren().get(0),0,max);
                
                Chat.setContent(MensajesBien);
                
                
            });
            
        }
    }

    @FXML
    private void EnviarMensaje(ActionEvent event) throws IOException {
        try{
                
                Bot.enviarMensajeDirecto(PersonaMensaje.getScreenName(), TextoMensaje.getText()); 
                //Crear Mensaje
                GridPane gridAux = new GridPane();
                gridAux.setPrefWidth(690);
                gridAux.setPadding(new Insets(5, 5, 5, 5)); 
                JFXButton miMensaje = new JFXButton(TextoMensaje.getText());
                miMensaje.wrapTextProperty().set(true);
                miMensaje.setStyle("-fx-font-size: 15px; -fx-background-color: #9D6DA5; -fx-font-family: Microsoft YaHei Light;");
                miMensaje.textFillProperty().setValue(Paint.valueOf("white"));
                miMensaje.ripplerFillProperty().setValue(Paint.valueOf("white"));
                Label espacio = new Label();
                espacio.setPrefWidth(348);
                gridAux.add(espacio,0,0);
                gridAux.add(miMensaje,1,0);
                gridAux.alignmentProperty().set(Pos.TOP_RIGHT);
                max++;
                MensajesBien.add(gridAux, 0, max);
                
                TextoMensaje.clear();
                TextoMensaje.setPromptText("Escribir Mensaje");
            
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
