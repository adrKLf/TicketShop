
<%@page import="java.util.ArrayList"%>
<%@page import="com.model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
      if(request.getSession() != null){
            request.getSession().removeAttribute("evento");
      }

%>
<html>
      <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Eventos disponibles</title>
      </head>
      <body>
            <h1>Eventos Disponibles</h1>
            <p>Seleccione el evento al que quiere acudir:</p> 
            <form action="StartServlet" method="get">
                  <select name="evento">
                        <%Room room = (Room) getServletContext().getAttribute("sala");
                              ArrayList<Event> eventos = room.getEvents();
                              for(Event e : eventos){
                                    out.println("<option>"+e.getName()+"</option>");
                              } 
                        %>
                  </select>
                  <br/>
                  <input type="submit" value="Enviar">
            </form>
            
            
      </body>
</html>
