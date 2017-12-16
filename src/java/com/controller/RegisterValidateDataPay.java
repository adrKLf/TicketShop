
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

public class RegisterValidateDataPay extends HttpServlet {

     
      @Override
      protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
            User user = null;
            Reserve reserve = null;

            //Si no hay sesion, no se crea, se redirecciona al index.
            if (request.getSession(false) == null) {
                  response.sendRedirect("index.jsp");
                  return;
            }

            HttpSession sesion = request.getSession();
            ArrayList<Error> errores;

            //Comprobamos que el objeto ArrayList<Error> de la sesion no sea nulo.
            if (sesion.getAttribute("errores") != null) {
                  //Si no es nulo accedo a el y le asigno una referencia. 
                  errores = (ArrayList<Error>) sesion.getAttribute("errores");
            } else {
                  //Si no hay lista de errores es que se está saltando algún paso.
                  response.sendRedirect("index.jsp");
                  return;
            }

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

                        if (u.getDatapay() != null) {
                              //Si esto es null es que no ha introducido sus datos
                              response.sendRedirect("ReserveServlet");
                              return;
                        }
                        if (sesion.getAttribute("reserva") == null) {
                              errores.add(new Error(3334, "No hay tickets en tu reserva"));
                              response.sendRedirect("ErrorServlet");
                        }

                        //Acceso a la página autorizado.
                        
                        String name = request.getParameter("name");
                        String surname = request.getParameter("surname");
                        String address = request.getParameter("address");
                        String dni = request.getParameter("dni");
                        String dataPay = request.getParameter("dataPay");
                        DataPay dp = null;
                        try{
                              switch(dataPay){
                                    case "CreditCard":
                                          dp = new DataPay(name, surname, 
                                                address, dni, DataPay.PayMethod.CreditCard);
                                          break;
                                    case "DebitCard":
                                          dp = new DataPay(name, surname, 
                                                address, dni, DataPay.PayMethod.DebitCard);
                                          break;
                                    case "TransferBank":
                                          dp = new DataPay(name, surname, 
                                                address, dni, DataPay.PayMethod.TransferBank);
                                          break;
                              }
                              
                              User usuario = (User) request.getSession().getAttribute("usuario");
                              usuario.setDataPay(dp);
                              response.sendRedirect("TicketBuy.jsp");
                              
                        }
                        catch(IOException | RuntimeException e){
                              errores.add(new Error(453, e.getMessage()));
                              response.sendRedirect("ErrorServlet");
                        }
                        
                  }
            }
      }
}
