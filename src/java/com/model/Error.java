/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.model;

/**
 * 
 * @author Adrian Garcia Garro
 */
import java.io.Serializable;

//Clase para gestionar mis propios errores y excepciones
public class Error implements Serializable{
    private int code;
    private String description;

    public Error(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
