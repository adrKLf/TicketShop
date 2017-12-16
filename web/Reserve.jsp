
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.model.Error"%>
<%@page import="com.model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
      User u = null;
      Event event = null;
      ArrayList<Error> errores = null;
      
      //Controlar sesiones invalidas
      if(request.getSession(false) == null){
            response.sendRedirect("index.jsp");
      }
      else{
            //Primero compruebo que haya lista de errores en la sesion.
            if (request.getSession().getAttribute("errores") != null) {
                        errores = (ArrayList<Error>) request.getSession().getAttribute("errores");
                  } else {
                        //Si no hay lista de errores es porque se ha saltado algun paso
                        response.sendRedirect("index.jsp");
                  }
            
            
            //Controlar que haya un evento
            if(request.getSession().getAttribute("evento") != null)
                  event = (Event)request.getSession().getAttribute("evento");
            else{
                 response.sendRedirect("index.jsp");
                 return;
            }
            
            //Controlar que haya un usuario
            if(request.getSession().getAttribute("usuario") != null){
                  u = (User)request.getSession().getAttribute("usuario");
                  
                  //Si el usuario es root lo quiero redireccionar al menu de admin(MenuAdmin.jsp)
                  if(u.isRoot()){
                        response.sendRedirect("MenuAdmin.jsp");
                        return;
                  }
                  
                  //Acceso autorizado
                  if(request.getSession().getAttribute("reserva") != null){
                        request.getSession().removeAttribute("reserva");
                  }
            }
            else{
                  //No hay usuario en la sesion
                  
                  //Como no hay usuario lo mando a la lista de errores.
                  errores.add(new Error(1004, "Debes logearte para reservar."));
                  response.sendRedirect("ErrorServlet");
            }
      }
      
      Room room = (Room) getServletContext().getAttribute("sala");
%>

<!DOCTYPE html>
<html>
      <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>JSP Page</title>
      </head>
      <body>
            <h1>¡Reserva tus tickets ya!</h1>
            <div>
                  Hola <% out.println(u.getNick()); %> has seleccionado el evento: <%
                  out.println("<ul><li>Nombre: " + event.getName() + "</li><li>Fecha: " + event.getDate()
                  + "</li><li>Precio: " + event.getPrize());
                  %>
                  <br>
                  <h3>Tickets Disponibles: </h3>
                  <form action="ReserveController" method="post">
                        <%
                             for(Ticket t : room.getTotalTickets()){
                                   if(t.isFree())
                                          out.println("<label><input type='checkbox' name='tickets'"
                                                + " value='" + t.getNumber() + "'/> Ticket "
                                                      + t.getNumber() + " Estado: Libre</label><br/>");
                                   else{
                                         out.println("<label><input type='checkbox' name='tickets'"
                                                + " value='" + t.getNumber() + "'/> Ticket "
                                                      + t.getNumber() + " Estado: Ocupado</label><br/>");
                                   }
                             } 
                        %>
                        <br><input type="submit" value="Reservar"/>
                  </form>
                  <br>
                  Pulse <a href="index.jsp">aqui</a> para volver atras y cambiar el evento
                  <br><br><a href="LogOutServlet">Cerrar Sesión</a>
            </div>
      </body>
</html>
