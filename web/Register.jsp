
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
      <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>JSP Page</title>
            
      </head>
      <body>
            <h1>Regístrate</h1>
            <form action="RegisterValidateServlet" method="post">
                  <div>
                        <table border="1px">
                              <tr>
                                    <td>Nick:</td> 
                                    <td><input type="text" name="nick" required="required"/></td>
                              </tr>
                              <tr>
                                    <td>Contraseña:</td> 
                                    <td><input type="password" name="pass" required="required"
                                               /></td>
                              </tr>
                              <tr>
                                    <td>Confirma tu Contraseña:</td> 
                                    <td><input type="password" name="repass" required="required"/></td>
                              </tr>
                              <tr>
                                    <td>Email:</td> 
                                    <td><input type="email" name="email" required="required"/></td>
                              </tr>
                              
                        </table>
                  </div>
                  <br><input type="submit" value="Registrate" style="margin-left: 5%">
            </form>
            <br>
            <a href="LogIn.jsp">Volver</a>
      </body>
</html>
