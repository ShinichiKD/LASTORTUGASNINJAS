/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import java.io.File;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import twitter4j.IDs;
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
    
    public BotTwitter() {
        //inicializar
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
    
    public ArrayList<Status> obtenerTweets() throws TwitterException{
       
        ArrayList<Status> Tweets= new ArrayList<>();
         for (Status status : Bot.getHomeTimeline()) {
             Tweets.add(status);
         }
         return Tweets; 
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
        for (Status status : Bot.getUserTimeline(Nombre)) {
            
            Tweets.add(status);
            
        }
        return Tweets;
    }
    
    public void agregarArchivo(File media) throws TwitterException{
        UploadedMedia mediaAux = Bot.uploadMedia(media);
        this.medias.add(mediaAux.getMediaId());
    }
    
    
    
}
