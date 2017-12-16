
<%@page import="com.model.User"%>
<%@page import="com.model.Event"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
      
      Event evento = null;
      if(request.getSession(false) == null){
            response.sendRedirect("index.jsp");
            return;
      }
      else{
            //Compruebo que la sesion tiene un atributo llamado evento
            if (request.getSession().getAttribute("evento") != null) {
                  //Lo tiene, pues seguimos en la pagina
                  
                  evento = (Event) request.getSession().getAttribute("evento");
                  
            } else {
                  //No lo tiene. Pues redirecciono a index.jsp para que seleccione uno.
                  
                  response.sendRedirect("index.jsp");
                  return;
            }

            HttpSession sesion = request.getSession();
            //Aqui compruebo si el usuario esta logeado (atributo "usuario" en el ámbito de la sesion")
            if (sesion.getAttribute("usuario") != null) {
                  //Esta logeado
                  User u = (User) sesion.getAttribute("usuario");
                  if (u.isRoot()) {
                        //Es admin
                        response.sendRedirect("MenuAdmin.jsp");
                        return;
                  } else {
                        //No es admin
                        response.sendRedirect("Main.jsp");
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
            <h1>Inicio de Sesion</h1>
            <% 
                  out.println("El evento es: " + evento.getName());
            %>
            <form action="LogInServlet" method="post">  
                  Nick:<input type="text" name="nick" required="required"><br>
                  Mail: <input type="email" name="email" required="required"><br/>
                  Password:<input type="password" name="pass" required="required"><br>  
                  <input type="submit" value="Entrar">  
            </form>
            <p>Si no tiene cuenta puede registrarse <a href="Register.jsp">aqui</a></p>
            <p><label>Pulse <a href="index.jsp">aqui</a> para volver a elegir un Evento.</label></p>
            
      </body>
</html>
