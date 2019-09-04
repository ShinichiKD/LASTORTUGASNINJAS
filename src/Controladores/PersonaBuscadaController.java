/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */
public class PersonaBuscadaController implements Initializable {

    BotTwitter Bot;
    User Usuario;
    ArrayList<Status> LineaTiempo;
    
    
    @FXML
    private ImageView ImagenPerfil;
    @FXML
    private Label Nombre;
    @FXML
    private Label Identificador;
    
    Animaciones Animacion;
    
    @FXML
    private StackPane Escena;
    @FXML
    private ScrollPane ScrollPane;
    @FXML
    private Label LabelAviso;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Bot = new BotTwitter();
        Animacion = new Animaciones();
        LabelAviso.setVisible(false);
        
        Usuario = MenuInicioController.PersonaBuscada;
        
        Nombre.setText(Usuario.getName());
        Identificador.setText("@"+Usuario.getScreenName());
        ImagenPerfil.setImage(new Image(Usuario.get400x400ProfileImageURL()));
        
        try {
            ActualizarEstados();
        } catch (TwitterException | IOException ex) {
            System.out.println(ex.getMessage());
        }
        
        
    }    

    @FXML
    private void SeguirUsuario() throws TwitterException, IOException {
        Bot.seguirUsuario(Usuario.getScreenName());
        LabelAviso.setText("Siguiendo al Usuario Correctamente.");
        Animacion.MostrarAvisos(LabelAviso);
                
    }

    @FXML
    private void CerrarVentana() throws IOException {
        Animacion.CerrarVentana(Escena, "/Vistas/MenuInicio.fxml");
        
    }
    void ActualizarEstados() throws TwitterException, IOException{
        int i=0;
        GridPane grid = new GridPane();
        for(Status e : Bot.TweetBuscado(Usuario.getScreenName())){

            Parent root = FXMLLoader.load(getClass().getResource("/Vistas/Publicacion.fxml"));
            
            ((Label)root.getChildrenUnmodifiable().get(0)).setText(e.getUser().getName() );
            
            ((TextArea)root.getChildrenUnmodifiable().get(1)).setText(e.getText());
            
            ((ImageView)root.getChildrenUnmodifiable().get(2)).setImage(new Image( e.getUser().get400x400ProfileImageURL() ));
            
            ((Label)root.getChildrenUnmodifiable().get(3)).setText(e.getCreatedAt().toString() );
            
            ((Button)root.getChildrenUnmodifiable().get(4)).setOnAction((ActionEvent events)->{ 
                try {
                    if(Bot.darLikeTweet(e.getId())){
                        LabelAviso.setText("Te ha gustado esta publicación");
                        Animacion.MostrarAvisos(LabelAviso);
                    }else{
                        LabelAviso.setText("Ya no te gusta esta publicación");
                        Animacion.MostrarAvisos(LabelAviso);
                    }
                
                
                } catch (TwitterException | IOException ex) {
                    System.out.println(ex.getMessage());
                }
            });
            
            
            ((Button)root.getChildrenUnmodifiable().get(5)).setOnAction((ActionEvent events)->{ 
                try {
                    if(Bot.darRetweet(e.getId())){
                        LabelAviso.setText("Has retweeteado esta publicación");
                        Animacion.MostrarAvisos(LabelAviso);
                    }else{
                        LabelAviso.setText("Ya no retweeteas esta publicación");
                        Animacion.MostrarAvisos(LabelAviso);
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
    }
    
}
