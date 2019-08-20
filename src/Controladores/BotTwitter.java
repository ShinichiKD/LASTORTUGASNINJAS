/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import java.util.ArrayList;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
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
          .setOAuthConsumerKey("hdRHBpZk8bgGkERmuNc78vmli")
          .setOAuthConsumerSecret("viguyMV46wSk2jroiZjttgtbihTizYcOwosOsPbfnv61gjiBYN")
          .setOAuthAccessToken("2344321298-oSPBpnAYRp0YBZ6qW6hr1JKXmOO7butDGnCmNRd")
          .setOAuthAccessTokenSecret("ZAsWCrbotgTBcWQzJC82ixRgFagnWFS9tUJFElNoD35eQ");
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
        System.out.println(Tweets);
        return Tweets;
    }
    
    
    
    
    
}
