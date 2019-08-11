/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import twitter4j.TwitterException;

/**
 * FXML Controller class
 *
 * @author Rodrigo
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Button Boton4;
    @FXML
    private Button Boton6;
    @FXML
    private Button Boton3;
    @FXML
    private Button Boton5;
 
    BotTwitter Bot;   
    @FXML
    private Pane Contenido;
    @FXML
    private TextField SeguirTF;
    @FXML
    private Button SeguirBoton;
    @FXML
    private Pane TwittearPanel;
    @FXML
    private Pane SeguirPanel;
    @FXML
    private TextField MensajeTF;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Bot = new BotTwitter();
        
        
    }    
    @FXML
    private void PublicarPresionar(ActionEvent event) throws TwitterException {       
        Bot.actualizarEstado(MensajeTF.getText()); 
        MensajeTF.clear();
        MensajeTF.setPromptText("¿Qué está pansando?");
               
                
                
    }

    @FXML
    private void SeguirPresionar(ActionEvent event) throws TwitterException {
       Bot.seguirUsuario(SeguirTF.getText());
       SeguirTF.clear();
       SeguirTF.setPromptText("Ingresar ID: @Fulanito");
    }

    
    
}
