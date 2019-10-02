/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import com.jfoenix.controls.JFXButton;
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
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
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

                
            GridPane gridAux = new GridPane();
            
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
            
            gridAux.add(FotoPerfil,0,0);
            gridAux.add(NombreUsuario, 1, 0);
            gridAux.add(Tweet, 1, 1);
            gridAux.add(BotonLike, 0, 2);
            gridAux.add(BotonRetweet, 1, 2);
            gridAux.add(FotoPublicacion, 2, 1);
            
            
            BotonLike.setOnAction((ActionEvent events)->{ 
                try {
                    if(Bot.darLikeTweet(e.getId())){
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
                    if(Bot.darRetweet(e.getId())){
                        
                        BotonRetweet.setStyle("-fx-background-color: #23E868;");
                    }else{
                        BotonRetweet.setStyle("-fx-background-color: #9e9e9e;");
                    }
                } catch (TwitterException ex) {
                    System.out.println(ex.getMessage());
                }
            });
            
//            ((Label)root.getChildrenUnmodifiable().get(3)).setText(e.getCreatedAt().toString() );

            
            grid.add(gridAux, 0, i);
            i++;
        }
        
        
        ScrollPane.setContent(grid);
    }
    
}
