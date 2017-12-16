/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.model.Error;
import com.model.*;
import java.util.ArrayList;

/**
 *
 * @author Adrian Garcia Garro
 */
public class ErrorServlet extends HttpServlet {

     
      protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
        
            HttpSession session = request.getSession();
            if(session.getAttribute("errores")==null){
                  response.sendRedirect("index.jsp");
                  return;
            }
            ArrayList<Error> errors = (ArrayList<Error>)session.getAttribute("errores");
        
        try (PrintWriter out = response.getWriter()){
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Error</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>¡Error!</h1>");
            
            for(Error e : errors){
                out.println("["+e.getCode()+"] "+e.getDescription() + "<br>");
            }
            //Imprimo los errores, y una vez que están imprimidos por pantalla se borran. 
            //No se lleva a cabo ninguna accion controlada por excepciones y la lista se va renovando cada vez que entremos.
            if(request.getSession().getAttribute("errores") != null){
                  request.getSession().removeAttribute("errores");
                  ArrayList<Error> errores = new ArrayList();
                  request.getSession().setAttribute("errores", errores);
            }
            
            out.println("<ul>"
                  + "<li><a href='index.jsp'>Inicio</a></li>"
                  + "<li><a href='LogIn.jsp'>Iniciar sesion</a></li>"
                  + "<li><a href='Register.jsp'>Registro</a></li>"
                  + "<li><a href='Reserve.jsp'>Reservas de tickets</a></li>"
                  + "<li><a href='Main.jsp'>Menu Usuario</a></li>"
                  + "<li><a href='LogOutServlet'>Cerrar sesion</a></li>"
                  + "</ul>");
            
            out.println("</body>");
            out.println("</html>");
        }
      }

      // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
      /**
       * Handles the HTTP <code>GET</code> method.
       *
       * @param request servlet request
       * @param response servlet response
       * @throws ServletException if a servlet-specific
       * error occurs
       * @throws IOException if an I/O error occurs
       */
      @Override
      protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            processRequest(request, response);
      }

      /**
       * Handles the HTTP <code>POST</code> method.
       *
       * @param request servlet request
       * @param response servlet response
       * @throws ServletException if a servlet-specific
       * error occurs
       * @throws IOException if an I/O error occurs
       */
      @Override
      protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            processRequest(request, response);
      }

      /**
       * Returns a short description of the servlet.
       *
       * @return a String containing servlet description
       */
      @Override
      public String getServletInfo() {
            return "Short description";
      }// </editor-fold>

}
