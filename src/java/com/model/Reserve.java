
package com.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * 
 * @author Adrian Garcia Garro
 */

//
public final class Reserve implements Serializable {

      private User user;
      private LocalDate date;
      private Event evento;
      private ArrayList<Ticket> ticketsReservados;
      

      public Reserve(User user,Event evento) {
            
            date = LocalDate.now();
            
            if(evento == null|| !(evento instanceof Event))
                  throw new NullPointerException("Referencia Event no válida");
            
            if(user == null|| !(user instanceof User))
                  throw new NullPointerException("Referencia User no válida");
            
            this.evento = evento;
            this.user = user;
            ticketsReservados = new ArrayList<>();
      }
      
      public synchronized void addTicket(int numeroTicket){
            
            ticketsReservados.add(new Ticket(numeroTicket));         

      }

      @Override
      public String toString() {
            return "Reserve{" + "user=" + user + ", date=" + date + ", evento=" + evento + ", ticketsReservados=" + ticketsReservados + '}';
      }

      public ArrayList<Ticket> getTicketsReservados() {
            return ticketsReservados;
      }

      public Event getEvent(){
            return evento;
      }


      public User getUser() {
            return user;
      }

      public LocalDate getDate() {
            return date;
      }

}
