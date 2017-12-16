
package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.model.*;
import com.model.Error;
import java.util.*;
import com.model.Error;
import javax.servlet.http.HttpSession;

public class LogInServlet extends HttpServlet {

    
      @Override
      protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
            HttpSession sesion = request.getSession();
            if(sesion.getAttribute("usuario") != null){
                  User u = (User) sesion.getAttribute("usuario");
                  if(u.isRoot()){
                        response.sendRedirect("MenuAdmin.jsp");
                  }
                  else{
                        response.sendRedirect("Main.jsp");
                  }
            }
            
            try{
                  
            InitUsers iu = (InitUsers) getServletContext().getAttribute("usuarios");
            ArrayList<User> users = (ArrayList<User>) iu.getUsers();
            
            DAO dao = new DAO();
            
            String email = request.getParameter("email");
            String nick = request.getParameter("nick");
            String pass = request.getParameter("pass");
            
            
            
                  User nuevo = new User(nick, pass, email);
                  
                  if(email == null || nick == null || pass == null)
                        nuevo.addError(new Error(1023, "Un campo del formulario no se ha rellenado"));
                  
                  
                  if (dao.ValidateUser(users, nuevo)) {
                        //Ususario Validado(hay coincidencia)
                        
                        //Bucamos el usuario validado por su nick
                        nuevo = dao.searchUserbyNick(users, nick);
                        
                        //Vemos si ese usuario es admin o  no.
                        if (nuevo.isRoot()) {
                              //Si es admin lo mando a MenuAdmin.jsp
                              sesion.setAttribute("usuario", nuevo);
                              response.sendRedirect("MenuAdmin.jsp");
                              return;
                        } else {
                              //Si no es admin lo mando a Main.jsp
                              sesion.setAttribute("usuario", nuevo);
                              response.sendRedirect("Main.jsp");
                              return;
                        }
                  }
                  else{
                        nuevo.addError(new Error(1034, "Usuario no valido."));
                        response.sendRedirect("ErrorServlet");
                        return;
                  }
                  
            }
            catch(Exception e){
                 ArrayList<Error> errores = (ArrayList <Error>)sesion.getAttribute("errores");
                 errores.add(new Error(305, e.getMessage()));
                 response.sendRedirect("ErrorServlet");
                 return;
            }    
      }

}
