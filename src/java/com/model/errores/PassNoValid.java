
package com.model.errores;

/**
 * 
 * @author Adrian Garcia Garro
 */
public class PassNoValid extends Exception{

      public PassNoValid(String message) {
            super(message);
      }

      @Override
      public String getMessage() {
            return super.getMessage(); //To change body of generated methods, choose Tools | Templates.
      }
   
}
