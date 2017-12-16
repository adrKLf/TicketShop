<%@page import="java.util.ArrayList"%>
<%@page import="com.model.User"%>
<%@page import="com.model.Error"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
      String nombre = null;
      if(request.getSession(false) == null){
            response.sendRedirect("index.jsp");
            return;
      }
      else{
            /*
            Para entrar a esta pagina hay que estar logeado. Si el usuario no está
      logeado, te redirecciona para que te logees.
      */    HttpSession sesion;
            sesion = request.getSession();
            if (sesion.getAttribute("usuario") != null) {
                        User u = (User) sesion.getAttribute("usuario");
                        nombre = u.getNick();
                        if(u.isRoot()){
                              response.sendRedirect("MenuAdmin.jsp");
                              return;
                        }
            } else {
                  if(sesion.getAttribute("errores") == null){
                        response.sendRedirect("index.jsp");
                  }else{
                        ArrayList<Error> errores = (ArrayList<Error>)sesion.getAttribute("errores");
                        errores.add(new Error(325, "Debes logearte primero."));
                        response.sendRedirect("ErrorServlet");
                        return; 
                  }
                  
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
            <h1>Menu de Usuario</h1>
            <h3>Hola <% out.println(nombre); %></h3>
            <div>
                  <h4>Menu de opciones</h4>
                  <ul>
                        <li><a href="Reserve.jsp"> Reservar </a></li>
                        <li><a href="LogOutServlet">Cerrar Sesión</a></li>
                  </ul>
            </div>
      </body>
</html>
