/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import com.jfoenix.controls.JFXButton;

/**
 *
 * @author Emilio
 */
public class ModeloBotonId {
    JFXButton boton;
    long id;

    public ModeloBotonId(JFXButton boton, long id) {
        this.boton = boton;
        this.id = id;
    }

    public JFXButton getBoton() {
        return boton;
    }

    public long getId() {
        return id;
    }

    
    
    
}

