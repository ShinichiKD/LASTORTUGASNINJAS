/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import twitter4j.IDs;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author Emilio
 */
public class BotTwitter {
    
    private Twitter Bot;

    public BotTwitter() {
        //inicializar
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
        Bot.updateStatus(texto);
        
        
    }
    public void enviarMensajeDirecto(String id,String texto) throws TwitterException{
        Bot.sendDirectMessage(id,texto);
    }
    public void seguirUsuario(String nombreDeUsuario) throws TwitterException{
        Bot.createFriendship(nombreDeUsuario);
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
    public void BuscarUsuario(String NombreUsuario) throws TwitterException{
        ResponseList<User> users;
         users = Bot.searchUsers(NombreUsuario, 1);
         for(User user:users){
             if(user.getStatus()!=null){
                 System.out.println(user);
             }
             
         }
        
                
     }
    
    
    
}
