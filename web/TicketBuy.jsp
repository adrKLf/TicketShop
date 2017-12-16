
<%@page import="com.model.*"%>
<%@page import="com.model.Error"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%//Si no hay sesion, no se crea, se redirecciona al index.
      if (request.getSession(false) == null) {
            response.sendRedirect("index.jsp");
            return;
      } else {

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

      InitUsers iu = (InitUsers) getServletContext().getAttribute("usuarios");
      ArrayList<User> users = iu.getUsers();

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
                        response.sendRedirect("Pay.jsp");
                  }

                  //Acceso a la página autorizado.
            }
      }%>
<!DOCTYPE html>
<html>
      <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>JSP Page</title>
      </head>
      <body>
            <h1>Ticket de compra</h1>
            <%
                 
                  Reserve reserve = (Reserve) request.getSession().getAttribute("reserva");
                  User usuarioReserva = (User) reserve.getUser();
                  ArrayList<Ticket> ticketsReserva = reserve.getTicketsReservados();
                  DataPay dp = (DataPay) usuarioReserva.getDatapay();
                  
                  double precioFactura;
                  
                  double precioEvento = reserve.getEvent().getPrize();
                  
                  precioFactura = precioEvento * ticketsReserva.size();
                  
                  String nombre = dp.getName();
                  String apellido = dp.getSurname();
                  String direccion = dp.getAddress();
                  String dni = dp.getDNI();
                  String modoPago = dp.getPaymethod().name();
                  
                  
            %> 
            
            <ul>
                  <li>Nombre: <%= nombre %></li>
                  <li>Apellido: <%= apellido %></li>
                  <li>Direccion: <%= direccion %></li>
                  <li>DNI: <%= dni %></li>
                  <li>Método de Pago: <%= modoPago %></li>
                  <li>Precio total: <%= precioFactura %> €</li>
                  
            </ul>
            
            <br>
            <ul>
                  <li><a href="ReserveServlet">Comprar</a></li>
                  <li><a href="Reserve.jsp">Cancelar Reserva</a></li>
            </ul>
      </body>
</html>
