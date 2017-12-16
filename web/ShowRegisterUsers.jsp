
<%@page import="com.model.*"%>
<%@page import="com.model.Error"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.model.InitUsers"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
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
            else{
                  //Hay un objeto usuario en la sesion.
                  User usuario = (User)sesion.getAttribute("usuario");
                  
                  //No tiene permisos
                  if(!usuario.isRoot()){
                        errores.add(new Error(388, "No tienes suficientes permisos."));
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
            <h1>Usuarios Registrados</h1>
            <br>
            <% InitUsers iu = (InitUsers) getServletContext().getAttribute("usuarios");
                  ArrayList<User> users = (ArrayList<User>) iu.getUsers();
                  int i = 1;
                  for(User u : users){
                        out.println("<br>Usuario " + (i++) + " :<br>Nick: " + u.getNick() + 
                              " <br>Mail: " + u.getEmail() + "<br>-------------------------"
                                    + "---------------------------");
                  }
                  out.println("<ul><li><a href='MenuAdmin.jsp'>Volver</a></li>");
                  out.println("<li><a href='LogOutServlet'>Cerrar Sesion</a></li></ul>");
            %>
            
      </body>
</html>
