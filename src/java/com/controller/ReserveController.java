
package com.controller;

import com.model.DAO;
import com.model.*;
import com.model.Reserve;
import com.model.User;
import com.model.Error;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "ReserveController", urlPatterns = {"/ReserveController"})
public class ReserveController extends HttpServlet {

     
      @Override
      protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
            DAO dao = new DAO();
            
            InitUsers iu = (InitUsers) getServletContext().getAttribute("usuarios");
            ArrayList<User> users = (ArrayList<User>) iu.getUsers();
            
            //Los inicializo a null para evitar futuros errores
            ArrayList<Error> errores = null;
            User u = null;
            Event evento = null;
            HttpSession sesion;
            
            if(request.getSession(false) == null){
                  response.sendRedirect("index.jsp");
                  return;
            }else{
                  sesion = request.getSession();

                  //Comprobamos que hay un evento en la sesion
                  
                  if(sesion.getAttribute("evento") == null){
                        response.sendRedirect("index.jsp");
                        return;
                  }
                  else{
                        evento = (Event) request.getSession().getAttribute("evento");
                  }
                   
                  //Comprobamos que el objeto ArrayList<Error> de la sesion no sea nulo.
                  if (sesion.getAttribute("errores") != null) {
                        //Si no es nulo accedo a el y le asigno una referencia. 
                        errores = (ArrayList<Error>) sesion.getAttribute("errores");
                  } else {
                        //Si no hay lista de errores es que se está saltando algún paso.
                        response.sendRedirect("index.jsp");
                        return;
                  }
                  
                  //Comprobamos que haya un ususario en la sesion
                  if(sesion.getAttribute("usuario") == null){
                        errores.add(new Error(400, "Necesita logearse para acceder aqui."));
                        response.sendRedirect("ErrorServlet");
                        return;
                  
                  } else {
                        u = (User) sesion.getAttribute("usuario");
                        if (u.isRoot()) {
                              //Si el usuario es root lo mando a su menú.
                              response.sendRedirect("MenuAdmin.jsp");
                              return;
                        } 
                              
                  //Acceso a la página autorizado.
                        if(sesion.getAttribute("reserva") != null){
                              sesion.removeAttribute("reserva");
                        }
                  }   
            }
            
            
            Reserve reserve = new Reserve(u, evento);
            if (request.getParameterValues("tickets") != null) {
                  for (String s : request.getParameterValues("tickets")) {
                        reserve.addTicket(Integer.parseInt(s));
                  }
                  sesion.setAttribute("reserva", reserve);
                  response.sendRedirect("ConfirmReserve.jsp");
                  
            }
            else{
                  u.addError(new Error(399, "No hay tickets seleccionados."));
                  response.sendRedirect("Reserve.jsp");
            }
      }

      
}
