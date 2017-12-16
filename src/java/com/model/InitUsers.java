
package com.model;

import com.model.errores.MailNoValid;
import com.model.errores.NickNoValid;
import com.model.errores.PassNoValid;
import java.util.ArrayList;

/**
 * 
 * @author Adrian Garcia Garro
 */
public final class InitUsers {

      private ArrayList<User> users;
      
      //Aqui inicio los usuarios que voy a cargar en el contexto de la aplicacion.
      public InitUsers(){
            
            users = new ArrayList<>();

            try {
                  addUser(new User("Adrian", "Hola.1234", "adrian@gmail.com"));
                  addUser(new User("admin", "Hola.1234", "admin@admin.com", true));
                  addUser(new User("invitado", "Hola.1234", "invitado@gmail.com"));
            } catch (MailNoValid | NickNoValid | PassNoValid | IllegalArgumentException e) {
                  e.getMessage();
            }

      }
      
      //añador usuarios a la lista de users
      public void addUser(User user){
            users.add(user);
      }

      //Devuelvo la lista de usuarios sin modificar
      public ArrayList<User> getUsers() {
            return users;
      }
      
      
}
