
package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.model.Error;
import com.model.*;
import com.model.errores.MailNoValid;
import com.model.errores.NickNoValid;
import com.model.errores.PassNoValid;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

public class RegisterValidateServlet extends HttpServlet {

     @Override
      protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
            HttpSession sesion = request.getSession(false);
            
            if(sesion == null){
                  sesion = request.getSession(true);
            }
            else{
                  if(request.getSession().getAttribute("usuario") != null){
                        
                        response.sendRedirect("Main.jsp");
                        return;
                  }
            }
            
            String nick, pass, repass, email;
            nick = request.getParameter("nick");
            pass = request.getParameter("pass");
            repass = request.getParameter("repass");
            email = request.getParameter("email");
            
            
            
            if(sesion == null){
                  response.sendRedirect("index.jsp");
                  return;
            }
            
            InitUsers initu = (InitUsers) getServletContext().getAttribute("usuarios");
            
            ArrayList<User> users = (ArrayList<User>) initu.getUsers();
            try{
                  DAO dao = new DAO();
                  dao.RegisterUser(users, nick, pass, repass, email);
                  response.sendRedirect("LogIn.jsp");
            }
            catch(Exception e){
                  ArrayList<Error> errores = (ArrayList <Error>) sesion.getAttribute("errores");
                  String er = e.getMessage();
                  errores.add(new Error(200, er));
                  response.sendRedirect("ErrorServlet");
                  return;
            }
            
            
      }
}
