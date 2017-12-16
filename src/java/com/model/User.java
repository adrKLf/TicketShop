
package com.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.model.errores.*;
import java.util.List;

/**
 * 
 * @author Adrian Garcia Garro
 */
public final class User implements Serializable{
      
      private DataPay datapay; //Datos de pago del Usuario. 
      
      //Los 3 atributos de logeo
      private String nick;
      private String email;
      private String pass;
      
      //Lista de errores del usuario. 
      //La uso para controlar los errores durante la sesion.
      private List<Error> errors;
      
      //root = true --> El usuario es administrador.
      private boolean root;
      
      //Expresion regular para el email
      private String patronMail = 
            "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

      //Expresion regular para la contraseña
      private String patronPass = 
            "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,15}$";
      
      //Constructor por defecto. Siempre se usará este para registrar usuarios estándar.
      public User(String nick, String pass, String email) throws IllegalArgumentException, MailNoValid, NickNoValid, PassNoValid {
          this(nick, pass, email, false);  
      }
      
      //Constructor para registrar admins.
      public User(String nick, String pass, String email, boolean root) throws IllegalArgumentException, MailNoValid, NickNoValid, PassNoValid {
            
            if(nick.length() <= 2 || nick.length() >= 15)
                  //Excepcion creada en com.model.errores
                  throw new NickNoValid("El nick introducido "
                        + "no cumple con los requisitos para "
                        + "su validación. Debe estar comprendido"
                        + " entre 3 y 15 carácteres.");
            this.nick = nick;
            
            //Metodo que evalua expresion regular de contraseña
            if(!passValidate(pass))
                  //Excepcion creada en com.model.errores
                  throw new PassNoValid("La contraseña introducida "
                        + "no cumple con los requisitos para "
                        + "su validación. Debe estar comprendida entre "
                        + "6 y 15 caracteres. Con mayúsculas, números y"
                        + " algún signo de puntuación.");
            this.pass = pass;
            
            //Metodo que evalua expresion regular de contraseña
            if(!mailValidate(email))
                  //Excepcion creada en com.model.errores
                  throw new MailNoValid("El mail introducido "
                        + "no cumple con los requisitos para "
                        + "su validación.");
            
            this.email = email;
            this.root = root;
            
            errors = new ArrayList<>();
      }
      
      private boolean passValidate(String pass){
            Pattern pattern = Pattern.compile(patronPass);
            Matcher matcher = pattern.matcher(pass);
            return matcher.matches();
      }
       
      private boolean mailValidate(String email){
            
            Pattern pattern = Pattern.compile(patronMail);
 
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
      }

      public void setDataPay(DataPay dp){
            this.datapay = dp;
      }
      
      public DataPay getDatapay() {
            return datapay;
      }

      @Override
      public int hashCode() {
            int hash = 5;
            hash = 61 * hash + Objects.hashCode(this.nick);
            hash = 61 * hash + Objects.hashCode(this.email);
            hash = 61 * hash + Objects.hashCode(this.pass);
            return hash;
      }

      //Son usuarios iguales cuando tienen la misma Contraseña, el mismo nick y el mismo mail.
      @Override
      public boolean equals(Object obj) {
            if (this == obj) {
                  return true;
            }
            if (obj == null) {
                  return false;
            }
            if (getClass() != obj.getClass()) {
                  return false;
            }
            final User other = (User) obj;
            
            return Objects.equals(this.pass, other.pass);
      }

      public void addError(Error error){
        errors.add(error);
      }

      public boolean errorExist(){
          return !errors.isEmpty();
      }

      public List<Error> getErrors() {
          return errors;
      }
      
      public boolean isRoot() {
            return root;
      }

      public String getNick() {
            return nick;
      }

      public String getEmail() {
            return email;
      }

      @Override
      public String toString() {
            return "User{" + "datapay=" + datapay + 
                  ", nick=" + nick + ", email=" + email + 
                  ", root=" + root + '}';
      }


}
