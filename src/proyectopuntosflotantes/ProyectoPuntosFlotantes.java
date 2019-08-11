/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectopuntosflotantes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Emilio
 */
public class ProyectoPuntosFlotantes extends Application {
    
    
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
<<<<<<< Updated upstream
    public static void main(String[] args) throws TwitterException {
        
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
        .setOAuthConsumerKey("hdRHBpZk8bgGkERmuNc78vmli")
        .setOAuthConsumerSecret("viguyMV46wSk2jroiZjttgtbihTizYcOwosOsPbfnv61gjiBYN")
        .setOAuthAccessToken("2344321298-oSPBpnAYRp0YBZ6qW6hr1JKXmOO7butDGnCmNRd")
        .setOAuthAccessTokenSecret("ZAsWCrbotgTBcWQzJC82ixRgFagnWFS9tUJFElNoD35eQ");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        
        twitter.updateStatus("a");
=======
    public static void main(String[] args) {
>>>>>>> Stashed changes
    
        launch(args);
    }
    
}
