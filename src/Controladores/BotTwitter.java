package Controladores;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.util.ArrayList;
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
    
    private Twitter Bot;
    private ArrayList<Long> medias;
    private long LastId = -1;
    GridPane grid;
    
    public BotTwitter() {
        //inicializar
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
          .setOAuthConsumerKey("rjBybNH66nPfhNKZUPL2Wd2qc")
          .setOAuthConsumerSecret("CRIcPF8RHfOXSiVTdht44ShcT4XcCMydM3ihFIVmQhKWVz5rP2")
          .setOAuthAccessToken("2344321298-xDKjy1GNh9CmzOwNAQFWylObrDlRmdCR3wlDxy0")
          .setOAuthAccessTokenSecret("hEdJxavmWpIyMJDxOoBFexSRXDbiNzN1GLmvSXkNt2dw4");
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
        //User usuario = Bot.users().showUser(nombreDeUsuario);
        //Bot.destroyFriendship(nombreDeUsuario);
        try {
            Bot.createFriendship(nombreDeUsuario);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        
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
        
            Paging paging;
            if(LastId > 0){
                paging = new Paging(LastId);
            }else{
                paging = new Paging(1, 3);
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
        for(Status e : status){
            
            System.out.println("Cargando tweet "+i+" de "+max);
            
            GridPane gridAux = new GridPane();
            gridAux.setStyle("-fx-background-color: #e4e4e4;");
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
                BotonRetweet.setStyle("-fx-background-color: #9e9e9e;");
            }
            
            if (e.isFavorited()) {
                BotonLike.setStyle("-fx-background-color: #ad0352;");
            }else{
                BotonLike.setStyle("-fx-background-color: #9e9e9e;");
            }
            
            
            ImageView FotoPublicacion = new ImageView(new Image("/Vistas/Imagenes/MenuInicio.png"));
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
                        BotonLike.setStyle("-fx-background-color: #ad0352;");
                    }else{
                        BotonLike.setStyle("-fx-background-color: #9e9e9e;");
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
                        BotonRetweet.setStyle("-fx-background-color: #9e9e9e;");
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
    public void timeLine(ScrollPane timeLine) throws TwitterException{
        int i=0,max;
        
        ArrayList<Status> status =  obtenerTweets();
        max = status.size();
        LastId = status.get(0).getId();
                
        for (Status e : status) {
            System.out.println("Cargando tweet "+(i+1)+" de "+max);
            GridPane gridAux = new GridPane();
            gridAux.setStyle("-fx-background-color: #e4e4e4;");
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
                BotonRetweet.setStyle("-fx-background-color: #9e9e9e;");
            }
            if (e.isFavorited()) {
                BotonLike.setStyle("-fx-background-color: #ad0352;");
            }else{
                BotonLike.setStyle("-fx-background-color: #9e9e9e;");
            }
            ImageView FotoPublicacion = new ImageView(new Image("/Vistas/Imagenes/MenuInicio.png"));
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
                        BotonLike.setStyle("-fx-background-color: #ad0352;");
                    }else{
                        BotonLike.setStyle("-fx-background-color: #9e9e9e;");
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
                        BotonRetweet.setStyle("-fx-background-color: #9e9e9e;");
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
    
    
}
