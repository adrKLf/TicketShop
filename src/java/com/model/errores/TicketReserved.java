/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.model.errores;

/**
 * 
 * @author Adrian Garcia Garro
 */
public class TicketReserved extends Exception{

      public TicketReserved(String message) {
            super(message);
      }

      @Override
      public String getMessage() {
            return super.getMessage(); //To change body of generated methods, choose Tools | Templates.
      }
      
}
