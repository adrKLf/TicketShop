
<%@page import="java.awt.AWTError"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.model.DAO"%>
<%@page import="com.model.User"%>
<%@page import="com.model.Error"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
      
      String nombre = null;
      HttpSession sesion = null;
      
      if(request.getSession(false) == null){
            response.sendRedirect("index.jsp");
            return;
      }
      else{
            sesion = request.getSession();
            ArrayList<Error> errores = (ArrayList<Error>)sesion.getAttribute("errores");
            
            if(sesion.getAttribute("usuario")==null){
               
                  errores.add(new Error(344, "Debes Iniciar sesión."));
                  response.sendRedirect("ErrorServlet");

            }
            else{//Hay un objeto usuario en la sesion.
                  User usuario = (User)sesion.getAttribute("usuario");
                  
                  //No tiene permisos
                  if(!usuario.isRoot()){
                        errores.add(new Error(388, "No tienes suficientes permisos."));
                        response.sendRedirect("ErrorServlet");
                        return;
                  }
                  nombre = usuario.getNick();
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
            <h1>Menu Admin</h1>
            <h3>Hola <% out.println(nombre); %></h3>
            <div>
                  <h4>Menu de opciones</h4>
                  <ul>
                        <li><a href="DataClients.jsp"> Consultar datos de Compradores </a></li>
                        <li><a href="ShowRegisterUsers.jsp">Ver lista de Usuarios</a></li>
                        <li><a href="LogOutServlet">Cerrar Sesión</a></li>
                  </ul>
            </div>
      </body>
</html>
