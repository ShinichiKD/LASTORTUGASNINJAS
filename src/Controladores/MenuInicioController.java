package Controladores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
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
import javax.swing.SwingUtilities;
import twitter4j.DirectMessage;
import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.UserStreamListener;
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
    private Button TwittearBoton;
    @FXML
    private JFXButton AvisosLabel;
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
    private JFXButton botonMensaje;
    @FXML
    private Pane MenuMensaje;
    @FXML
    private ListView ListaUsuarios;
    @FXML
    private JFXTextArea TextoMensaje;
    @FXML
    private Button BotonEnviar;
    
    @FXML
    private ImageView img_inicio;
    @FXML
    private JFXButton BTNseguir;
    
    private final int EstadoTimeLine=0;
    private User PersonaMensaje;
    private GridPane MensajesBien;
    private int max;
    private final String ColorHastag="black";
    ObservableList<String> items =FXCollections.observableArrayList();
    public User PersonaBuscada;
    BotTwitter Bot;  
    //Animaciones Animacion;
    
    UserStreamListener listener = new UserStreamListener() {
             
        @Override
        public void onUnfavorite(User user, User user1, Status status) {
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
                            if((Bot.sigueA(status.getUser().getScreenName()) || status.getUser().getId()==Bot.getId()) && status.getId()!= Bot.lastStatusId){
                                Bot.timeLine(TimeLineInicio,1,status,"red",AvisosLabel);
                            }
                            
                        
                    } catch (TwitterException | IOException ex) {
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
        }
    }; 
    @FXML
    private ScrollPane Chat;
    @FXML
    private Label contadorPublicacion;
    @FXML
    private Label contadorChat;
    @FXML
    private JFXButton MDBusqueda;
    @FXML
    private JFXTextArea TAmensaje2;
    @FXML
    private JFXButton BotonEnviarMD;
    @FXML
    private Label contadorChat1;
    
    
    
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Bot = new BotTwitter();
       
        
        try {
            initStream();
        } catch (TwitterException ex) {
            Logger.getLogger(MenuInicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        AvisosLabel.setVisible(false);
        botonInicio.setStyle("-fx-background-color: #4fb4cb;");
        botonInicio.ripplerFillProperty().setValue(Paint.valueOf("white"));
        
        try {
            img_inicio.setImage( new Image(Bot.getUser(Bot.getId()).get400x400ProfileImageURL()));
        } catch (TwitterException ex) {
            Logger.getLogger(MenuInicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Bot.leerArchivo();
            ActualizarEstados(0);
            
        } catch (TwitterException ex) {
            if(ex.getErrorCode() == 88){
                AvisosLabel.setText("Error con limites de la API : Cierre el programa y espere");
                try {
                    Animaciones.MostrarAvisos(AvisosLabel,"orange");
                } catch (IOException ex1) {
                    System.out.println(ex1.getCause()); 
                }
            }
            System.out.println();
        } catch (IOException ex) {
            System.out.println(ex.getCause());
        }
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                new Thread( ()->{
                    Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                      try {
                        CargaVentanaChat();
                        AvisosLabel.setText("Mensajes Actualizados");
                        Animaciones.MostrarAvisos(AvisosLabel,"orange");
                        System.out.println("proceso....");
                        
                        } catch (TwitterException ex) {
                            Logger.getLogger(MenuInicioController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(MenuInicioController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        }
                    });
                }).start();

            }
        }, 0, 65*1000);
    } 
    
    @FXML
    private void publicarPresionar() throws TwitterException, IOException {     
        try{
            
            String texto = MensajeTA.getText();
            contadorPublicacion.setText(0+" / "+280);
            MensajeTA.clear();
            MensajeTA.setPromptText("¿Qué está pasando?");
            
            
            if (Bot.actualizarEstado(texto)){
                AvisosLabel.setText("Publicación exitosa.");
                Animaciones.MostrarAvisos(AvisosLabel,"orange");
            }else{
                AvisosLabel.setText("Tu publicacion tenia *spam/malas palabras* y no se publico");
                Animaciones.MostrarAvisos(AvisosLabel,"red");
                return;
            }
            try {
                ActualizarEstados(0);
            
        } catch (TwitterException | IOException ex) {
            Logger.getLogger(MenuInicioController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
        }catch(TwitterException e){
            System.out.println(e.getErrorCode());
            System.out.println(e.getErrorMessage());
            switch (e.getErrorCode()) {
                case 170:
                    AvisosLabel.setText("No se ha podido publicar: Mensaje en blanco.");
                    Animaciones.MostrarAvisos(AvisosLabel,"orange");
                    break;
                case 187:
                    AvisosLabel.setText("No se ha podido publicar: Publicación duplicada.");
                    Animaciones.MostrarAvisos(AvisosLabel,"orange");
                    break;
                case 186:
                    AvisosLabel.setText("No se ha podido publicar: Publicación muy larga ");
                    Animaciones.MostrarAvisos(AvisosLabel,"orange");
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
    private void releasedMensajeTA() {
        int letras = MensajeTA.getText().length();
        int limite = 280;
        if(letras > limite){
             //cambiar color
             contadorPublicacion.textFillProperty().setValue(Paint.valueOf("Red"));
             TwittearBoton.setDisable(true);

        }else{
            //cambiar color
            contadorPublicacion.textFillProperty().setValue(Paint.valueOf("White"));
            TwittearBoton.setDisable(false);
        }
        contadorPublicacion.setText(letras+" / "+limite);
        
    }
    void ActualizarEstados(int volverACargar) throws TwitterException, IOException{
            
            Bot.timeLine(TimeLineInicio,volverACargar,null,"red",AvisosLabel);
            
    }

    @FXML
    private void salir(ActionEvent event) {
        System.exit(0);
    }
    
    @FXML
    private void seleccionarItem() throws TwitterException {
        if (BuscarListView.getSelectionModel().getSelectedItem()!=null) {
            System.out.println("usuario seleccionado");
            MDBusqueda.setVisible(true);
            BTNseguir.setVisible(true);
            Nombre.setVisible(true);
            Identificador.setVisible(true);
            ImagenPerfil.setVisible(true);
            
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
            System.out.println(PersonaBuscada);
             BotonEnviarMD.setOnAction((event) -> {
               try{

                Bot.enviarMensajeDirecto(PersonaBuscada.getScreenName(), TAmensaje2.getText()); 
                TAmensaje2.clear();
                TAmensaje2.setPromptText("Escribir Mensaje");
               }
               catch(TwitterException e){
                   System.out.println(e.getErrorCode());
                   System.out.println(e.getErrorMessage());
                   switch (e.getErrorCode()) {
                       case 50:
                           AvisosLabel.setText("Usuario no encontrado: No se pudo enviar mensaje.");
                   {
                       try {
                           Animaciones.MostrarAvisos(AvisosLabel,"orange");
                       } catch (IOException ex) {
                           Logger.getLogger(MenuInicioController.class.getName()).log(Level.SEVERE, null, ex);
                       }
                   }
                           break;
                       case 151:
                           AvisosLabel.setText("Mensaje en blanco: No se puede enviar.");
                   {
                       try {
                           Animaciones.MostrarAvisos(AvisosLabel,"orange");
                       } catch (IOException ex) {
                           Logger.getLogger(MenuInicioController.class.getName()).log(Level.SEVERE, null, ex);
                       }
                   }
                           break;
                       case 349:
                           AvisosLabel.setText("No puedes enviar mensajes a este usuario.");
                   {
                       try {
                           Animaciones.MostrarAvisos(AvisosLabel,"orange");
                       } catch (IOException ex) {
                           Logger.getLogger(MenuInicioController.class.getName()).log(Level.SEVERE, null, ex);
                       }
                   }
                           break;
                       default:
                           break;
                   }
               }
                 
             });
            
            Bot.timeLineBuscado(PersonaBuscada, TimeLinePersona);
            
        }
        
    }

    @FXML
    private void buscarPersona() throws IOException, TwitterException {
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
            
        }catch(TwitterException e){
            AvisosLabel.setText("Usuario no encontrado");
            Animaciones.MostrarAvisos(AvisosLabel,"orange");
        }
        
        
    }

    @FXML
    private void subirArchivo() throws TwitterException, IOException {
        try{
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter fileExtensions = 
                new FileChooser.ExtensionFilter(
                  "Imagenes", "*.jpg", "*.PNG", "*.GIF");
            fileChooser.getExtensionFilters().add(fileExtensions);
            File selectedFile = fileChooser.showOpenDialog(new Stage());
            Bot.agregarArchivo(selectedFile);
            AvisosLabel.setText("Archivo subido correctamente");
            Animaciones.MostrarAvisos(AvisosLabel,"orange");
        }catch(IOException | TwitterException e){
            AvisosLabel.setText("Archivo subido no subido, formato incorrecto");
            Animaciones.MostrarAvisos(AvisosLabel,"orange");
        }
        
    }


    @FXML
    private void volverInicio(ActionEvent event) {
        
            
            if(MenuBuscar.isVisible()){
                botonBuscar.setStyle("-fx-background-color: #26292f;");
                botonBuscar.textFillProperty().setValue(Paint.valueOf("white"));
                MenuBuscar.setVisible(false);
            }
            
            if(MenuMensaje.isVisible()){
                botonMensaje.setStyle("-fx-background-color: #26292f;");
                botonMensaje.textFillProperty().setValue(Paint.valueOf("white"));
                MenuMensaje.setVisible(false);
            }
            MenuInicio.setVisible(true);
            botonInicio.setStyle("-fx-background-color:  #4fb4cb;");
            botonInicio.textFillProperty().setValue(Paint.valueOf("white"));
            botonInicio.ripplerFillProperty().setValue(Paint.valueOf("white"));
            BuscarListView.setVisible(false);
        
    }

    @FXML
    private void seguirUsuario() throws TwitterException, IOException {
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
                botonInicio.setStyle("-fx-background-color: #26292f;");
                botonInicio.textFillProperty().set(Paint.valueOf("white"));
                MenuInicio.setVisible(false);
            }
            
            if(MenuMensaje.isVisible()){
                botonMensaje.setStyle("-fx-background-color: #26292f;");
                botonMensaje.textFillProperty().set(Paint.valueOf("white")); 
                MenuMensaje.setVisible(false);
            }
            BuscarListView.setVisible(true);
            MenuBuscar.setVisible(true);
            botonBuscar.setStyle("-fx-background-color:  #4fb4cb;");
            botonBuscar.textFillProperty().setValue(Paint.valueOf("white"));
            botonBuscar.ripplerFillProperty().setValue(Paint.valueOf("white"));
    }

    @FXML
    private void menuMensaje(ActionEvent event) {
        
            if(MenuInicio.isVisible()){
                botonInicio.setStyle("-fx-background-color: #26292f;");
                botonInicio.textFillProperty().setValue(Paint.valueOf("white"));
                MenuInicio.setVisible(false);
            }
            
            if(MenuBuscar.isVisible()){
                botonBuscar.setStyle("-fx-background-color: #26292f;");
                botonBuscar.textFillProperty().setValue(Paint.valueOf("white"));
                MenuBuscar.setVisible(false);
            }
            
            MenuMensaje.setVisible(true);
            botonMensaje.setStyle("-fx-background-color:  #4fb4cb;");
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
        
        filtre.follow(Bot.getIdSeguidos());
        twitterStream.filter(filtre);
        
        
    }
    long idMensaje;
    ArrayList<GridPane> mensajesUsuario;
    ArrayList<User> nombreUsuario;
    int ultimoMensaje;
    int ventanaPresionada=-1;
    private void CargaVentanaChat() throws TwitterException, IOException {
        mensajesUsuario = new ArrayList();
        nombreUsuario = new ArrayList();
        ListaUsuarios.getItems().clear();
        
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
            
            usuario.setPrefWidth(235);
            usuario.setAlignment(Pos.TOP_LEFT);
            
            ListaUsuarios.getItems().add(usuario);
            
            
            int i=0;
            GridPane mensajes = new GridPane();
            for(DirectMessage dm : lista){
                    GridPane gridAux = new GridPane();
                    gridAux.setPrefWidth(650);
                    gridAux.setPadding(new Insets(5, 5, 5, 5)); 
                    if (user.getId()==dm.getRecipientId()) {
                        
                        
                        JFXButton miMensaje = new JFXButton(dm.getText());
                        miMensaje.wrapTextProperty().set(true);
                        miMensaje.setStyle("-fx-font-size: 15px; -fx-background-color:  #4fb4cb; -fx-font-family: Microsoft YaHei Light;");
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
                        
                        suMensaje.setStyle("-fx-font-size: 15px; -fx-background-color: #4191a3 ; -fx-font-family: Microsoft YaHei Light;");
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
                
                mensajesUsuario.add(MensajesBien);
                nombreUsuario.add(user);
                
                usuario.setOnAction((ActionEvent events)->{
                    PersonaMensaje = user;
                   
                    GridPane misMensajes= new GridPane();
                    for (int j = 0; j < nombreUsuario.size(); j++) {
                        User usuarioAux = (User) nombreUsuario.get(j);
                        if (usuario.getText().equals(usuarioAux.getName())) {
                            misMensajes = (GridPane) mensajesUsuario.get(j);
                            ultimoMensaje = j;
                        }
                    }
                   
                   Chat.setContent(misMensajes);
                
                });
                
                
        }
        Chat.setContent(new GridPane());
    }

    @FXML
    private void enviarMensaje(ActionEvent event) throws IOException {
        try{
                
                Bot.enviarMensajeDirecto(PersonaMensaje.getScreenName(), TextoMensaje.getText()); 
                //Crear Mensaje
                GridPane gridAux = new GridPane();
                gridAux.setPrefWidth(650);
                gridAux.setPadding(new Insets(5, 5, 5, 5)); 
                JFXButton miMensaje = new JFXButton(TextoMensaje.getText());
                miMensaje.wrapTextProperty().set(true);
                miMensaje.setStyle("-fx-font-size: 15px; -fx-background-color: #4fb4cb; -fx-font-family: Microsoft YaHei Light;");
                miMensaje.textFillProperty().setValue(Paint.valueOf("white"));
                miMensaje.ripplerFillProperty().setValue(Paint.valueOf("white"));
                Label espacio = new Label();
                espacio.setPrefWidth(348);
                gridAux.add(espacio,0,0);
                gridAux.add(miMensaje,1,0);
                gridAux.alignmentProperty().set(Pos.TOP_RIGHT);
                max++;
                mensajesUsuario.get(ultimoMensaje).add(gridAux, 0, max);
                
                TextoMensaje.clear();
                TextoMensaje.setPromptText("Escribir Mensaje");
            
        }
        catch(TwitterException e){
            System.out.println(e.getErrorCode());
            System.out.println(e.getErrorMessage());
            switch (e.getErrorCode()) {
                case 50:
                    AvisosLabel.setText("Usuario no encontrado: No se pudo enviar mensaje.");
                    Animaciones.MostrarAvisos(AvisosLabel,"orange");
                    break;
                case 151:
                    AvisosLabel.setText("Mensaje en blanco: No se puede enviar.");
                    Animaciones.MostrarAvisos(AvisosLabel,"orange");
                    break;
                case 349:
                    AvisosLabel.setText("No puedes enviar mensajes a este usuario.");
                    Animaciones.MostrarAvisos(AvisosLabel,"orange");
                    break;
                default:
                    break;
            }
        }
        
    }

    @FXML
    private void releasedTextoMensaje(KeyEvent event) {
        int letras = TextoMensaje.getText().length();
        int limite = 10000;
        if(letras > limite){
            //cambiar color
            contadorChat.textFillProperty().setValue(Paint.valueOf("Red"));
            
            BotonEnviar.setDisable(true);

        }else{
            //cambiar color
            contadorChat.textFillProperty().setValue(Paint.valueOf("white"));
            BotonEnviar.setDisable(false);
        }
        contadorChat.setText(letras+" / "+limite);
    }
    @FXML
    private void releasedTextoMensaje2(KeyEvent event) {
        int letras = TAmensaje2.getText().length();
        int limite = 10000;
        if(letras > limite){
            //cambiar color
            contadorChat1.textFillProperty().setValue(Paint.valueOf("Red"));
            
            BotonEnviarMD.setDisable(true);

        }else{
            //cambiar color
            contadorChat1.textFillProperty().setValue(Paint.valueOf("white"));
            BotonEnviarMD.setDisable(false);
        }
        contadorChat1.setText(letras+" / "+limite);
    }
    
    private void recargarInicio(ActionEvent event) throws TwitterException, IOException, IOException {
        
        ActualizarEstados(0);
    }

    @FXML
    private void mostrarMinichat(ActionEvent event) {
        
        if (TAmensaje2.isVisible()) {
            TAmensaje2.setVisible(false);
            BotonEnviarMD.setVisible(false);
            contadorChat1.setVisible(false);
        }
        else{
            TAmensaje2.setVisible(true);
            BotonEnviarMD.setVisible(true);
            contadorChat1.setVisible(true);
        }
        
        
        
        
    }

   
}
