
package com.controller;

import com.model.Room;
import com.model.User;
import java.util.ArrayList;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.model.*;
import java.time.LocalDate;
import java.time.Month;


//Inicio el contexto
public class InitListener implements ServletContextListener {

      @Override
      public void contextInitialized(ServletContextEvent sce) {
            
            Room sala = new Room("Thwod", 50);
            
            //Los eventos no deben tener el mismo nombre ni la misma fecha. 
            //Los añado a la sala
            sala.addEvent(new Event("Concierto Estopa", LocalDate.of(2018, Month.MARCH, 15), 20.50));
            sala.addEvent(new Event("Concierto Kase-O", LocalDate.of(2018, Month.APRIL, 20), 12.00));
            sala.addEvent(new Event("Concierto Extremoduro", LocalDate.of(2018, Month.JANUARY, 9), 11.75));
            
            //Aqui añado los atributos sala y usuarios a el contexto de la aplicacion
            sce.getServletContext().setAttribute("sala", sala);
            sce.getServletContext().setAttribute("usuarios", new InitUsers());
   
      }

      @Override
      public void contextDestroyed(ServletContextEvent sce) {
      }
}
