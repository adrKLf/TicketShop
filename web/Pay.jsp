
<%@page import="com.model.*"%>
<%@page import="com.model.InitUsers"%>
<%@page import="com.model.Ticket"%>
<%@page import="com.model.Room"%>
<%@page import="com.model.Event"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.model.User"%>
<%@page import="com.model.Error"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
      //Si no hay sesion, no se crea, se redirecciona al index.
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
                        response.sendRedirect("ReserveServlet");
                  }

                  //Acceso a la página autorizado.
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
            <h1>Datos de pago</h1>
            
            <form action="RegisterValidateDataPay" method="post">
                  <div>
                        <table border="1px">
                              <tr>
                                    <td>Nombre: </td> 
                                    <td><input type="text" name="name" required="required"/></td>
                              </tr>
                              <tr>
                                    <td>Apellido: </td> 
                                    <td><input type="text" name="surname" required="required"
                                               /></td>
                              </tr>
                              <tr>
                                    <td>Dirección: </td> 
                                    <td><input type="text" name="address" required="required"/></td>
                              </tr>
                              <tr>
                                    <td>DNI: </td> 
                                    <td><input type="text" name="dni" required="required"/></td>
                              </tr>
                              <tr>
                                    <td>Metodo de pago: </td> 
                                    <td><select name="dataPay">
                                                <option value="DebitCard">Tarjeta de Débito</option>
                                                <option value="CreditCard">Tarjeta de Crédito</option>
                                                <option value="TransferBank">Transferencia Bancaria</option>
                                    </select></td>
                              </tr>
                              
                        </table>
                  </div>
                  <br><input type="submit" value="Registrate" style="margin-left: 5%">
            </form>
      </body>
</html>
