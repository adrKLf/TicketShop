package com.model;

import com.model.errores.MailNoValid;
import com.model.errores.NickNoValid;
import com.model.errores.PassNoValid;
import java.util.ArrayList;

/**
 *
 * @author Adrian Garcia Garro
 */

//Clase para acceder a datos de la aplicacion
public final class DAO {

      //Registra un usuario en la lista de usuarios del contexto de la aplicacion(InitUsers).
      public void RegisterUser(ArrayList<User> users, String nick, String pass, String repass, String email) throws MailNoValid, NickNoValid, PassNoValid, RuntimeException{
            try{
                  if(!pass.equals(repass)){//Si la contraseña y la confirmacion no son iguales
                        throw new RuntimeException("Las contraseñas no coinciden.");
                  }
                  User u = new User(nick, pass, email);//Creo un usuario para comparar con los de la lista.
                  
                  for (User i : users) {
                        if (i.getNick().equalsIgnoreCase(nick) || i.getEmail().equalsIgnoreCase(email)) {
                            //nick o email son iguales
                            throw new RuntimeException("Usuario ya registrado");
                        }    
                  }
                  //Si sale del bucle no hay coincidencias. Usuario está disponible
                  users.add(u);
                  //Añado usuario
            }
            catch(IllegalArgumentException e){
                  e.getMessage();
            }
      }
      
      //Metodos para validar el usuario
      
      public boolean ValidateUser(ArrayList<User> users, User u) throws NickNoValid, MailNoValid, PassNoValid {
            
            
            for (User i : users) {
                  /*El equals lo sobreescribí. El atributo que compara es la contraseña. 
                  Tuve que sacar los otros atributos(nick y mail) porque no distinguía
                  mayúsculas de minúsculas.*/
                  if (i.equals(u) 
                        && i.getNick().equalsIgnoreCase(u.getNick())
                        && i.getEmail().equalsIgnoreCase(u.getEmail())) {
                        //Usuario válido
                        return true;      
                  } else {
                        if (!i.getNick().equalsIgnoreCase(u.getNick())) {
                              u.addError(new Error(101, "Nick no coincide"));
                        }

                        if (!i.getEmail().equalsIgnoreCase(u.getEmail())) {
                              u.addError(new Error(101, "Mail no coincide"));
                        } else {
                              u.addError(new Error(102, "Password no coincide")); 
                        }
                  }
            }
            return false; //Nunca va a entrar aqui 
      }
      
      
      //Devuelve false si no hay coincidencias
      public boolean ValidateUser(ArrayList<User> users, String nick, String pass, String email) throws NickNoValid, MailNoValid, PassNoValid {
            
            //Capturo las posibles excepciones de la instancia de User.
            try{ 
                 User u = new User(nick, pass, email);
            
            
                  for (User i : users ) {
                        if (i.equals(u) && i.getNick().equalsIgnoreCase(u.getNick())
                        && i.getEmail().equalsIgnoreCase(u.getEmail())) {
                              return true;      
                              //usuario válido
                        
                        } else {
                              
                              //usuario no válido
                              if (!i.getNick().equalsIgnoreCase(nick)) {
                                    throw new NickNoValid("Nick no coincide");
                              }

                              if (!i.getEmail().equalsIgnoreCase(email)) {
                                    throw new MailNoValid("Mail no coincide");
                              } else {
                                    throw new PassNoValid("Contraseña no coincide");
                              }
                        }
                  }
            }
            catch(IllegalArgumentException e){
                  e.getMessage();
            }
            return false; //No hay coincidencias. No es usuario válido.
      }
      
      
      //Encontrar al usuario cuyo nick sea coincidente
      public User searchUserbyNick (ArrayList<User> users, String nick){
            for(User i : users){
                  if(i.getNick().equalsIgnoreCase(nick))
                        return i;
            }
            return null;
      }
      
      
      //Encontrar al usuario cuyo mail sea coincidente
      public User searchUserbyMail(ArrayList<User> users, String mail){
            for(User i : users){
                  if(i.getEmail().equalsIgnoreCase(mail))
                        return i;
            }
            return null;
      }

}
