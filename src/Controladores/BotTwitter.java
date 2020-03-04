package Controladores;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import twitter4j.DirectMessage;
import twitter4j.DirectMessageList;
import twitter4j.IDs;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.UploadedMedia;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;


/**
 *
 * @author Emilio
 */
public class BotTwitter {
    private int Bandera=0;
    private Twitter Bot;
    private ArrayList<Long> medias;
    private long LastId = -1;
    private GridPane grid;
    private ArrayList<GridPane> gridsAux;
    private ArrayList<ModeloBotonId> listaBotonesIdGustar;
    private ArrayList<ModeloBotonId> listaBotonesIdRetweetear;
    /*
    //Bot de reserva
    public static String CK = "rjBybNH66nPfhNKZUPL2Wd2qc";
    public static String CS = "CRIcPF8RHfOXSiVTdht44ShcT4XcCMydM3ihFIVmQhKWVz5rP2";
    public static String AT = "2344321298-xDKjy1GNh9CmzOwNAQFWylObrDlRmdCR3wlDxy0";
    public static String TS = "hEdJxavmWpIyMJDxOoBFexSRXDbiNzN1GLmvSXkNt2dw4";
    */
    
    public static String CK = "zkQze8k75ponlo27agP7GL0mX";
    public static String CS = "TIMwroq3H4CwYP4ZQOVlIawx0PYyC89R68mYNIcnqZy5dJCDLu";
    public static String AT = "2344321298-XPuCsygYXnT0HjbkG0p3wE84ST0tFKupikcnNNH";
    public static String TS = "hfmNcTIU1XO1ItqOqRUyse5Ou7hXAPEQ0bYtUbM7ZhZb7";
    
    public BotTwitter() {
        //inicializar
        listaBotonesIdGustar = new ArrayList<>();
        listaBotonesIdRetweetear = new ArrayList<>();
        gridsAux = new ArrayList<>();
        grid = new GridPane();
        medias = new ArrayList<>();
        Bot = init();
    }
    
    /**
     * Crea una configuracion con los datos de la API de nuestro bot de Twitter
     * y lo conecta con la cuenta
     * 
     * @return retorna el objeto con los metodos de la libreria 
     * 
     */
    private Twitter init(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey(CK)
          .setOAuthConsumerSecret(CS)
          .setOAuthAccessToken(AT)
          .setOAuthAccessTokenSecret(TS);
        TwitterFactory tf = new TwitterFactory(cb.build());
        return tf.getInstance();
    }
    
    /**
     * Actualiza el estado de la cuenta
     * @param texto
     * @throws TwitterException 
     */
   
    public void actualizarEstado(String texto) throws TwitterException{
        
        StatusUpdate statusUpdate = new StatusUpdate(texto);
        if(medias.size()>0){
            statusUpdate.setMediaIds(IdToLong());
        }
        Bot.updateStatus(statusUpdate);
        medias = new ArrayList<>();
    }
    
    private ArrayList<DirectMessage> buscarIdMensaje(long id,ArrayList<ArrayList<DirectMessage>> list){
        for(ArrayList<DirectMessage> arr : list){
            for(DirectMessage dm : arr){
                if(dm.getRecipientId() == id || dm.getSenderId()== id){
                    return arr;
                }
            }
        }
        return null;
    }
    
    public long getId() throws TwitterException{
        return Bot.getId();
    }
    
    public User getUser(long id) throws TwitterException{
        return Bot.users().showUser(id);
    }
    public long[] getIdSeguidos() throws TwitterException{
        return Bot.getFriendsIDs(-1).getIDs();
    }
    
    public ArrayList<ArrayList<DirectMessage>> obtenerMensajesDirectos() throws TwitterException{
        ArrayList<ArrayList<DirectMessage>> amigos = new ArrayList<>();
        
        DirectMessageList Lista = Bot.getDirectMessages(10);
        
        for(DirectMessage m : Lista){
            if(m.getRecipientId() == Bot.getId()){
                ArrayList<DirectMessage> mensajes = buscarIdMensaje(m.getSenderId(), amigos);
                if(mensajes!=null){
                mensajes.add(m);
                }else{
                    mensajes = new ArrayList<>();
                    mensajes.add(m);
                    amigos.add(mensajes);
                }
            }else{
                ArrayList<DirectMessage> mensajes = buscarIdMensaje(m.getRecipientId(), amigos);
                if(mensajes!=null){
                mensajes.add(m);
                }else{
                    mensajes = new ArrayList<>();
                    mensajes.add(m);
                    amigos.add(mensajes);
                }
            }
        }
        return amigos;
    }
    
    private long[] IdToLong(){
        long[] mediaIds = new long[medias.size()];
        int i = 0;
        for(long id : medias){
            mediaIds[i] = id;
            i++;
        }
        return mediaIds;
    }
    
    public void enviarMensajeDirecto(String id,String texto) throws TwitterException{
//        List<DirectMessage> mensajes = Bot.getDirectMessages(0);
//        int i=0;
//        while(i< mensajes.size()){
//            
//            System.out.println(mensajes.get(i).getText());
//            i++;        
//        }
        Bot.sendDirectMessage(id,texto);
    }
    
    public void seguirUsuario(String nombreDeUsuario) throws TwitterException{
        try {
            if(!Bot.showFriendship(Bot.getScreenName(), nombreDeUsuario).isSourceFollowingTarget()){
                Bot.createFriendship(nombreDeUsuario);
                return;
            }
            Bot.destroyFriendship(nombreDeUsuario);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public boolean sigueA(String nombreDeUsuario) throws TwitterException{
        return Bot.showFriendship(Bot.getScreenName(), nombreDeUsuario).isSourceFollowingTarget();
    }
    
    public ArrayList<Status> buscarTweets(String nombre) throws TwitterException{
        ArrayList<Status> Tweets= new ArrayList<>();
        
        Query query = new Query("source:"+nombre);
        QueryResult result = Bot.search(query);
        for (Status status : result.getTweets()) {
            Tweets.add(status);
        }
        return Tweets;
    }
    
    public ArrayList<Status> obtenerTweets() throws TwitterException {
            // cantidad tweets
            int cantidad = 10;
            Paging paging;
            if(LastId > 0){
                paging = new Paging(LastId);
            }else{
                paging = new Paging(1, cantidad);
            }
            
            return (ArrayList<Status>) Bot.getHomeTimeline(paging);
            
    }
    
    public boolean darLikeTweet(Long id) throws TwitterException{
        
        Status st = Bot.showStatus(id);
        if(!st.isFavorited()){
            Bot.createFavorite(id);
            return true;
        }else{
            Bot.destroyFavorite(id);
            return false;
        }
        
    }
    public boolean darLikeHastag(Long id) throws TwitterException{
        
        Status st = Bot.showStatus(id);
        if(!st.isFavorited()){
            Bot.createFavorite(id);
            return true;
        }else{
            
            return false;
        }
        
    }
    public boolean darRetweet(Long id) throws TwitterException{
        
        Status st = Bot.showStatus(id);
        if(!st.isRetweetedByMe()){
            Bot.retweetStatus(id);
            return true;
        }else{
            Bot.unRetweetStatus(id);
            return false;
        }
    }
     public boolean darRetweetHastag(Long id) throws TwitterException{
        
        Status st = Bot.showStatus(id);
        if(!st.isRetweetedByMe()){
            Bot.retweetStatus(id);
            return true;
        }else{
            
            return false;
        }
    }
    
    public ObservableList ListFriends(String nombreUsuario) throws TwitterException{
        
        ObservableList<String> List = FXCollections.observableArrayList();
        
        long lCursor = -1;
        IDs friendsIDs = Bot.getFriendsIDs(nombreUsuario, lCursor);
        do
        {
          for (long i : friendsIDs.getIDs())
           {
               List.add(Bot.showUser(i).getScreenName());
           }
        }while(friendsIDs.hasNext());
     
        return List;
    }
    
    public ArrayList<String> BuscarEnTwitter(String NombreUsuario) throws TwitterException{
        ResponseList<User> users = null;

        ArrayList<String> AUX = new ArrayList();

        for (int i = 0; i < 1; i++) {
            users = Bot.searchUsers(NombreUsuario, i);

            for (User aux:users) {
                if (aux.getScreenName()!=null) {
                    AUX.add(aux.getScreenName()) ;
                    //System.out.println(aux.getScreenName());
                }
            }
        }

        return AUX;
        
     }
    
    public User BuscarUsuario(String Nombre) throws TwitterException{
        try{
            User usuario = Bot.users().showUser(Nombre);
            System.out.println(usuario.getScreenName());
            return usuario;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public  ArrayList<Status> TweetBuscado(String Nombre) throws TwitterException{
        
        ArrayList<Status> Tweets= new ArrayList<>();
        for (Status status : Bot.getUserTimeline(Nombre, new Paging(1,20))) {
            
            Tweets.add(status);
            
        }
        return Tweets;
    }
    
    public void agregarArchivo(File media) throws TwitterException{
        UploadedMedia mediaAux = Bot.uploadMedia(media);
        this.medias.add(mediaAux.getMediaId());
    }
    
    public void timeLineBuscado(User usuario,ScrollPane timeLine) throws TwitterException{
        int i=0,max;
        GridPane grid = new GridPane();
        ArrayList<Status> status = TweetBuscado(usuario.getScreenName());
        max = status.size();
        timeLine.setStyle("-fx-background: white");
        
        for(Status e : status){
            
            System.out.println("Cargando tweet "+i+" de "+max);
            
            GridPane gridAux = new GridPane();
            gridAux.setStyle("-fx-background-color: white;");
            gridAux.setPadding(new Insets(10, 10, 10, 10)); 
            
            Label NombreUsuario = new Label(e.getUser().getName());
            NombreUsuario.setPrefHeight(27);
            NombreUsuario.setStyle("-fx-font: Microsoft YaHei Light;");
            NombreUsuario.setStyle("-fx-font-size: 19px;");
            
            
            ImageView FotoPerfil = new ImageView(new Image(e.getUser().get400x400ProfileImageURL()));
            FotoPerfil.setFitHeight(50);
            FotoPerfil.setFitWidth(50);
            
            TextArea Tweet = new TextArea(e.getText());
            Tweet.setStyle("-fx-font: Microsoft YaHei Light;");
            Tweet.setStyle("-fx-font-size: 18px;");
            Tweet.editableProperty().set(false);
            Tweet.wrapTextProperty().set(true);
            
            
            
            Tweet.setPrefHeight(135);
            Tweet.setPrefWidth(480);
            
            
            JFXButton BotonLike = new JFXButton();
            JFXButton BotonRetweet = new JFXButton();
            
            
            
            BotonLike.graphicProperty().set(new ImageView(new Image("/Vistas/Imagenes/corazon.png")));
           
            BotonRetweet.graphicProperty().set(new ImageView(new Image("/Vistas/Imagenes/retuit.png")));
                
            if (e.isRetweeted()) {
                BotonRetweet.setStyle("-fx-background-color: #23E868;");
            }else{
                BotonRetweet.setStyle("-fx-background-color: white;");
            }
            
            if (e.isFavorited()) {
                BotonLike.setStyle("-fx-background-color: #ff0000;");
            }else{
                BotonLike.setStyle("-fx-background-color: white;");
            }
            
            ImageView FotoPublicacion = new ImageView();
            FotoPublicacion.setFitHeight(150);
            FotoPublicacion.setFitWidth(150);
            try {
                FotoPublicacion.setImage(new Image(e.getMediaEntities()[0].getMediaURL()));
                
            }catch (Exception ex) {
                
            }
            Label Fecha = new Label(e.getCreatedAt().getDate()+"/"+(e.getCreatedAt().getMonth()+1)+"/"+(e.getCreatedAt().getYear()-100));
            Fecha.setStyle("-fx-font: Microsoft YaHei Light;");
            Fecha.setStyle("-fx-font-size: 15px;");
            gridAux.add(FotoPerfil,0,0);
            gridAux.add(NombreUsuario, 1, 0);
            gridAux.add(Tweet, 1, 1);
            gridAux.add(BotonLike, 0, 2);
            gridAux.add(BotonRetweet, 1, 2);
            gridAux.add(FotoPublicacion, 2, 1);
            gridAux.add(Fecha,2,0);
            
            BotonLike.setOnAction((ActionEvent events)->{ 
                try {
                    if(darLikeTweet(e.getId())){
                        BotonLike.setStyle("-fx-background-color: #ff0000;");
                    }else{
                        BotonLike.setStyle("-fx-background-color: white;");
                    }
                
                } catch (TwitterException ex) {
                    System.out.println(ex.getMessage());
                }
            });
            BotonRetweet.setOnAction((ActionEvent events)->{ 
                try {
                    if(darRetweet(e.getId())){
                        
                        BotonRetweet.setStyle("-fx-background-color: #23E868;");
                    }else{
                        BotonRetweet.setStyle("-fx-background-color: white;");
                    }
                } catch (TwitterException ex) {
                    System.out.println(ex.getMessage());
                }
            });    
            grid.add(gridAux, 0, i);
            i++;
        }
        timeLine.setContent(grid);
    }
    public TextFlow cambiarColorHastag(String colorHastag,String Tweet,ArrayList<String> hastagsTweet){
        TextFlow tf = new TextFlow();
        String parseo = new String("");
        ArrayList<String> s = new ArrayList<>();
            if (hastagsTweet.size()!=0) {
                 for (int i = 0; i < hastagsTweet.size(); i++) {
                    String [] t = Tweet.split(hastagsTweet.get(i),2);
                    if (t[0]!="") {
                        Text text= new Text(t[0]);                  
                        Text text2= new Text(hastagsTweet.get(i));
                        text.setStyle("-fx-font-family: Microsoft YaHei Light");
                        text2.setStyle("-fx-font-family: Microsoft YaHei Light");
                        text2.setFill(Paint.valueOf(colorHastag));
                        tf.getChildren().add(text);
                        tf.getChildren().add(text2);              
                        Tweet=t[1];                     
                    }    
                 }
                Text text= new Text(Tweet);
                text.setStyle("-fx-font-family: Microsoft YaHei Light");
                tf.getChildren().add(text);

            }else{
                Text text= new Text(Tweet);
                text.setStyle("-fx-font-family: Microsoft YaHei Light");
                tf.getChildren().add(text);
            }
            
            
        return tf;
    }
    
    public void eliminarTweet(GridPane gp) throws TwitterException, IOException{
        gridsAux.remove(gp);
        grid.getChildren().remove(gp);
        Animaciones.MostrarAvisos(new JFXButton("aa") );
        
    }
    public void timeLine(ScrollPane timeLine,int evento,Status statusEvent,String Colors) throws TwitterException{
        int i=0,max;
        
        ArrayList<Status> status;
        ArrayList<String> HastagsTweet;
        
        if(evento == 0){
            status =  obtenerTweets();
            max = status.size();
        }else{
            status = new ArrayList<>();
            status.add(statusEvent);
            
            max = 1;
        }
        LastId = status.get(0).getId();
        for (Status e : status) {
            System.out.println(e.getText());
            TextFlow t = new TextFlow();
            t.setLayoutX(700);
            t.setLayoutY(100);
            Bandera=0;
            
            HastagsTweet=hastag(e.getText(),e.getId());
            t=cambiarColorHastag(Colors, e.getText(), HastagsTweet);
           
            GridPane gridAux = new GridPane();
            
            t.setStyle("-fx-border-color: #cfcfcf;-fx-font-family: Arial;  -fx-font-size: 18px; -fx-padding: 10px;");
            t.setPrefWidth(480);
            t.setPrefHeight(100);
            gridAux.setStyle("-fx-background-color: white;");
            gridAux.setPadding(new Insets(10, 10, 10, 10));
            Label NombreUsuario = new Label(e.getUser().getName());
            NombreUsuario.setPrefHeight(27);
            NombreUsuario.setStyle("-fx-font: Microsoft YaHei Light;");
            NombreUsuario.setStyle("-fx-font-size: 19px;");
            ImageView FotoPerfil = new ImageView(new Image(e.getUser().get400x400ProfileImageURL()));
            FotoPerfil.setFitHeight(50);
            FotoPerfil.setFitWidth(50);
            TextArea Tweet = new TextArea(e.getText());
            Tweet.setStyle("-fx-font: Microsoft YaHei Light;");
            Tweet.setStyle("-fx-font-size: 18px;");
            Tweet.editableProperty().set(false);
            Tweet.wrapTextProperty().set(true);
            Tweet.setPrefHeight(135);
            Tweet.setPrefWidth(730);
            JFXButton BotonLike = new JFXButton();
            JFXButton BotonRetweet = new JFXButton();
            BotonLike.graphicProperty().set(new ImageView(new Image("/Vistas/Imagenes/corazon.png")));
            BotonRetweet.graphicProperty().set(new ImageView(new Image("/Vistas/Imagenes/retuit.png")));
            if (e.isRetweeted()) {
                BotonRetweet.setStyle("-fx-background-color: #23E868;");
            }else{
                BotonRetweet.setStyle("-fx-background-color: white;");
            }
            if (e.isFavorited()) {
                BotonLike.setStyle("-fx-background-color: #ff0000;");
            }else{
                BotonLike.setStyle("-fx-background-color: white;");
            }
            if (Bandera==1) {
                BotonLike.setStyle("-fx-background-color: #ff0000;");
            }
            
            ImageView FotoPublicacion = new ImageView();
            FotoPublicacion.setFitHeight(150);
            FotoPublicacion.setFitWidth(150);
            try {
                FotoPublicacion.setImage(new Image(e.getMediaEntities()[0].getMediaURL()));
            }catch (Exception ex) {
                
            }
            Label Fecha = new Label(e.getCreatedAt().getDate()+"/"+(e.getCreatedAt().getMonth()+1)+"/"+(e.getCreatedAt().getYear()-100));
            Fecha.setStyle("-fx-font: Microsoft YaHei Light;");
            Fecha.setStyle("-fx-font-size: 15px;");
            gridAux.setStyle("-fx-border-color: #cfcfcf;");
            gridAux.add(FotoPerfil,0,0);
            gridAux.add(NombreUsuario, 1, 0);
            gridAux.add(t, 1, 1);
            gridAux.add(BotonLike, 0, 2);
            gridAux.add(BotonRetweet, 1, 2);
            gridAux.add(FotoPublicacion, 2, 1);
            gridAux.add(Fecha,2,0);
            
            if(e.getUser().getId() == Bot.getId()){
                JFXButton eliminar = new JFXButton("Eliminar");
                eliminar.graphicProperty().set(new ImageView(new Image("/Vistas/Imagenes/eliminar.png")));
                gridAux.add(eliminar, 2, 2);
                eliminar.setOnAction((ActionEvent events)->{ 
                    
                    try {
                        Bot.destroyStatus(e.getId());
                        eliminarTweet( gridAux);
                    } catch (TwitterException ex) {
                        System.out.println(ex.getMessage());
                    } catch (IOException ex) {
                        Logger.getLogger(BotTwitter.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
            BotonLike.setOnAction((ActionEvent events)->{ 
                try {
                    if(darLikeTweet(e.getId())){
                        BotonLike.setStyle("-fx-background-color: #ff0000;");
                    }else{
                        BotonLike.setStyle("-fx-background-color: white;");
                    }
                
                } catch (TwitterException ex) {
                    System.out.println(ex.getMessage());
                }
            });
           
            BotonRetweet.setOnAction((ActionEvent events)->{ 
                try {
                    if(darRetweet(e.getId())){
                        
                        BotonRetweet.setStyle("-fx-background-color: #23E868;");
                    }else{
                        BotonRetweet.setStyle("-fx-background-color: white;");
                    }
                } catch (TwitterException ex) {
                    System.out.println(ex.getMessage());
                }
            });
            listaBotonesIdGustar.add(new ModeloBotonId(BotonLike, e.getId()));
            listaBotonesIdRetweetear.add(new ModeloBotonId(BotonRetweet, e.getId()));
            if(status.size()==1){
                gridsAux.add(0,gridAux);
            }else{
                gridsAux.add(gridAux);
            }
            
            i++;
        }
        
        anadirGrid();
        timeLine.setContent(grid);
        
    }
    
    private void anadirGrid(){
        int i = 0;
        grid = new GridPane();
        for(GridPane gp : gridsAux){
            grid.add(gp, 0, i);
            i++;
        }
    }
    void volverACargarGustar(long id){
        for(ModeloBotonId modelo : listaBotonesIdGustar){
            if(modelo.getId()==id){
                modelo.getBoton().setStyle("-fx-background-color: #ad0352;");
            }
        }
    }
    void volverACargarRetweetear(long id){
        for(ModeloBotonId modelo : listaBotonesIdRetweetear){
            if(modelo.getId()==id){
                modelo.getBoton().setStyle("-fx-background-color: #23E868;");
            }
        }
    }
    public ArrayList<String> hastag(String texto,Long idAux) throws TwitterException{
        TextFlow text = new TextFlow();
      
        ArrayList<String> hastags = new ArrayList<String>();
        String [] a = texto.split("#");
        if (a.length>1) {
            for (int i = 1; i <a.length; i++) { 
                String [] b =a[i].split(" ");               
                hastags.add("#"+b[0]);
                if (b[0].equals("seguir") && b.length>=2) {
                    Bot.createFriendship(b[1]);
                }else if (b[0].equals("gustar") && (b.length>=2 || b.length==1)) {
                    try{
                        Long id = Long.parseLong(b[1]);
                        darLikeHastag(id);
                        volverACargarGustar(id);
                    }catch(Exception e){
                        Bandera=1;    
                        darLikeHastag(idAux); 
                    }
                } else if (b[0].equals("difundir") && (b.length>=2 || b.length==1)) {
                    try{
                        Long id = Long.parseLong(b[1]);
                        darRetweetHastag(id);   
                        volverACargarRetweetear(id);
                    }catch(Exception e){
                        darRetweetHastag(idAux);  
                    }
                }else{
                     
                }       
            }
        }
            return hastags;
    }
    
}
    

