package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.model.*;
import com.model.Error;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

public class ReserveServlet extends HttpServlet {

      protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            
            User user;
            Reserve reserve;

            //Si no hay sesion, no se crea, se redirecciona al index.
            if (request.getSession(false) == null) {
                  response.sendRedirect("index.jsp");
                  return;
            }
            
            HttpSession sesion = request.getSession();
            ArrayList<Error> errores = null;

            //Comprobamos que el objeto ArrayList<Error> de la sesion no sea nulo.
            if (sesion.getAttribute("errores") != null) {
                  //Si no es nulo accedo a el y le asigno una referencia. 
                  errores = (ArrayList<Error>) sesion.getAttribute("errores");
            } else {
                  //Si no hay lista de errores es que se está saltando algún paso.
                  response.sendRedirect("index.jsp");
                  return;
            }

            Event evento = (Event) request.getSession().getAttribute("evento");

            Room room = (Room) getServletContext().getAttribute("sala");
            ArrayList<Ticket> ticketsSala = room.getTotalTickets();

            InitUsers uss = (InitUsers) getServletContext().getAttribute("usuarios");
            ArrayList<User> users = uss.getUsers();

            DAO dao = new DAO();
            User u;

            if (sesion.getAttribute("usuario") == null) {
                  errores.add(new Error(400, "Necesita logearse para acceder aqui."));
                  response.sendRedirect("ErrorServlet");
                  return;
            } else {
                  u = (User) sesion.getAttribute("usuario");
                  if (u.isRoot()) {
                        response.sendRedirect("MenuAdmin.jsp");
                        return;
                  } else {
                        //Tenemos que comprobar que el usuario ha introducido sus datos de pago.
                        if (u.getDatapay() == null) {
                              //Si esto es null es que no ha introducido sus datos
                              errores.add(new Error(1403, "Hay que pagar antes de Reservar"));
                              response.sendRedirect("Pay.jsp");
                              return;
                        }
                        if (sesion.getAttribute("reserva") == null) {
                              errores.add(new Error(3334, "No hay tickets en tu reserva"));
                              response.sendRedirect("ErrorServlet");
                        }

                        //Acceso a la página autorizado.
                  }
            }
            
            try (PrintWriter out = response.getWriter()) {
                  try {
                        user = dao.searchUserbyNick(users, u.getNick());
                        reserve = (Reserve) sesion.getAttribute("reserva");
                        if (request.getParameterValues("tickets") != null) {
                              for (String s : request.getParameterValues("tickets")) {
                                    reserve.addTicket(Integer.parseInt(s));
                              }
                        }
                        request.getSession().setAttribute("reserva", reserve);
//                        for(Ticket ti : reserve.getTicketsReservados()){
//                              if(!ti.isFree()){
//                                    errores.add(new Error(587, "Ese ticket ya está ocupado."));
//                                    response.sendRedirect("ErrorServlet");
//                                    return;
//                              }
//                                    
//                        }
                        room.reserve(reserve);
                        
                        
                  } catch (Exception e) {
                     errores.add(new Error(1000, e.getMessage()));
                     response.sendRedirect("ErrorServlet");
                     return;
                  }
                  
                  out.println("<!DOCTYPE html>");
                  out.println("<html>");
                  out.println("<head>");
                  out.println("<title>Servlet PruebaServlet</title>");
                  out.println("</head>");
                  out.println("<body>");
                  out.println("<h1> Reserva hecha con éxito </h1>");
                  out.println("<h3>Si quiere seguir reservando pulse <a href='Reserve.jsp'>aqui</a><h3><br>");
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
