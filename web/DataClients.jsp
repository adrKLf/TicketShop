
<%@page import="java.util.ArrayList"%>
<%@page import="com.model.Error"%>
<%@page import="com.model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
      //Compruebo sesion no nula
      if (request.getSession(false) == null) {
                  response.sendRedirect("index.jsp");
                  return;
            } else {
                  HttpSession sesion;
                  sesion = request.getSession();

                  ArrayList<Error> errores = null;
                  if (sesion.getAttribute("errores") != null) {
                        errores = (ArrayList<Error>) sesion.getAttribute("errores");
                  } else {
                        //Lo mando a Start para que cree de nuevo el atributo.
                        response.sendRedirect("StartServlet");
                        return;
                  }

                  
                  if (sesion.getAttribute("usuario") == null) {

                        errores.add(new Error(344, "Debes Iniciar sesión."));
                        response.sendRedirect("ErrorServlet");

                  } else {//Hay un objeto usuario en la sesion.
                        User usuario = (User) sesion.getAttribute("usuario");

                        //No tiene permisos
                        if (!usuario.isRoot()) {
                              errores.add(new Error(388, "No tienes suficientes permisos."));
                              response.sendRedirect("ErrorServlet");
                              return;
                        }
                        //Acceso permitido
                  }

            }
      
%>
<!DOCTYPE html>
<html>
      <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>JSP Page</title>
      </head>
      <body>
            <h1>Datos de los compradores</h1>
            
            <%
            InitUsers iu = (InitUsers) getServletContext().getAttribute("usuarios");
            ArrayList<User> users = (ArrayList<User>) iu.getUsers();
            Room room = (Room) getServletContext().getAttribute("sala");
            ArrayList<Ticket> ticketsSala = room.getTotalTickets();
            
            ArrayList<Ticket> ticketsUsuario = new ArrayList();

            for (User u : users) {
                  if(!ticketsUsuario.isEmpty()){
                        ticketsUsuario.removeAll(ticketsUsuario);
                  }
                  if (u.getDatapay() != null) {

                        for (Ticket t : ticketsSala) { 
                              
                              if(t.getUser() != null){
                                 if (u.getNick().equalsIgnoreCase(t.getUser().getNick())) {
                                          ticketsUsuario.add(t);
                                       }  
                              }
                        }

                        out.println("<br>------------------------------------------------------------"
                              + "<ul><li>Nombre: " + u.getDatapay().getName() + "</li>"
                              + "<li>Apellidos: " + u.getDatapay().getSurname() + "</li>"
                              + "<li>DNI: " + u.getDatapay().getDNI() + "</li>"
                              + "<li>Direccion: " + u.getDatapay().getAddress() + "</li>"
                              + "<li>Metodo de pago: " + u.getDatapay().getPaymethod().name() + "</li>"
                              + "</ul>----------------------------------------------------------------"
                              + "<br><br>");

                        if (!ticketsUsuario.isEmpty()) {
                              out.println("<lu>");
                              double prize = 0;
                              for (Ticket b : ticketsUsuario) {
                                    out.println("<li>Numero del Ticket: " + b.getNumber() + "</li>"
                                          + "<li>Evento: " + b.getEvento().getName() + "</li>"
                                          + "<li>Precio: " + b.getEvento().getPrize() + " €</li>"
                                          + "--------------------------------------------------------------"
                                    );
                                    prize += b.getEvento().getPrize();
                              }
                              out.println("<li>Precio total de las compras del cliente: "
                                    + prize + " €</li>");
                              out.println("</lu>");
                              out.println("<br>------------------------------------------------------------");
                        } else {
                              out.println("<br>Este usuario no ha reservado ningun ticket");
                              
                        }
                  }
                  
            }
      
            %>
            <br>
            <ul>
                  <li><a href="MenuAdmin.jsp">Volver</a></li>
                  <li><a href="LogOutServlet">Cerrar sesión</a></li>
            </ul>
      </body>
</html>
