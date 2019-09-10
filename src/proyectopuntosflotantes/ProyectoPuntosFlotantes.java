/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectopuntosflotantes;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader; 
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Emilio
 */
public class ProyectoPuntosFlotantes extends Application {
    
    private double xOffset = 0; 
    private double yOffset = 0;
    
    @Override
    public void start(Stage stage) throws Exception {
        
        stage.initStyle(StageStyle.UNDECORATED);
        
        Parent root = FXMLLoader.load(getClass().getResource("/Vistas/MenuInicio.fxml"));
        
        Scene scene = new Scene(root);
 
        stage.setScene(scene);
        stage.show();
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //aa
        launch(args);
    }
    
}
