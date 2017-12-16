package com.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Adrian Garcia Garro
 */
public final class Room implements Serializable {

      private String id;
      private String name;
      private ArrayList<Event> events;
      private ArrayList<Ticket> totalTickets;

      public Room(String name, int numberTicketsCapacity) {
            
            
            this.name = name;

            if(numberTicketsCapacity <= 10 || numberTicketsCapacity >= 200)
                  throw new IllegalArgumentException("Numero tickets fuera de rango");
            
            
            //Numero aleatorio para identificar la sala.
            id = Integer.toString((int)
                  (Math.round(Math.random()*100000)+1));
            
            totalTickets = new ArrayList<>();
            events = new ArrayList<>();

            //Compruebo si la lista está vacia
            if (totalTickets.isEmpty()) {
                  for (int i = 1; i <= numberTicketsCapacity; i++) {
                        totalTickets.add(new Ticket(i));
                  }
            }

      }

      public void addEvent(Event e) {
            if (e == null || !(e instanceof Event)) {
                  throw new NullPointerException("Referencia "
                        + "no valida");
            }
            if (events.isEmpty()) {
                  events.add(e);
            } else {
                  for (Event a : events) {
                        if (a.equals(e)) {
                              //Compruebo que no se puedan almacenar dos eventos iguales
                              throw new RuntimeException("No "
                                    + "puede haber dos eventos"
                                    + " iguales");
                        }
                  }
                  events.add(e);
            }
      }
      
      
      //RESERVAR
      public synchronized void reserve(Reserve reserve) throws RuntimeException, IllegalArgumentException {

            //Aqui utilizo la referencia de reserva para sacar datos que necesito
            ArrayList<Ticket> ticketsreservados = reserve.getTicketsReservados();//Tickets de la reserva

            User usuarioReserva = reserve.getUser(); //Usuario que ha reservado
            Event eventoReserva = reserve.getEvent();//Evento que ha reservado

            if (ticketsreservados.isEmpty()) {
                  throw new IllegalArgumentException("Debe "
                        + "haber al menos "
                        + "un ticket seleccionado");
            }
            
            //Para comprobar cuando la sala está llena
            if (totalTickets.isEmpty())
                  throw new RuntimeException("No quedan tickets disponibles");
            
            ArrayList<Ticket> contador = new ArrayList();
            
            for (Ticket tr : ticketsreservados) {
                  for (Ticket total : totalTickets) {

                        if (tr.getNumber() == total.getNumber()) {
                              //Coinciden los tickets
                              if (total.isFree()) {
                                    //El ticket de la lista de la sala está libre
                                    total.setEvent(eventoReserva); //Le asigno al ticket un evento
                                    total.setUser(usuarioReserva); //Le asigno al ticket un usuario
                                    contador.add(total);
                              } else {
                                    //No está libre
                                    if(!contador.isEmpty()){
                                          for(Ticket t: contador){
                                                 t.removeEvent();
                                                 t.removeUser();
                                          }              
                                    }
                                    throw new RuntimeException(
                                          "El ticket seleccionado "
                                          + "no está disponible");
                              }
                        }

                  }
            }
      }
      
      //Metodo sin usar.
      private void resetTickets(){
            for(Ticket t: totalTickets) {
                  t.removeUser();
            }
      }

      //Devuelve la lista con los eventos de esta sala
      public ArrayList<Event> getEvents() {
            return events;
      }

      /*Devuelve lista original de tickets de la sala. En un 
futuro implementar la interfaz cloneable.*/
      public ArrayList<Ticket> getTotalTickets() {
            return totalTickets;
      }

      //Implementacion metodos hashcode y equals para comparar instancias de Room
      @Override
      public int hashCode() {
            int hash = 5;
            hash = 79 * hash + Objects.hashCode(this.id);
            hash = 79 * hash + Objects.hashCode(this.name);
            return hash;
      }

      //Atributos diferenciadores: id y name.
      @Override
      public boolean equals(Object obj) {
            if (this == obj) {
                  return true;
            }
            if (obj == null) {
                  return false;
            }
            if (getClass() != obj.getClass()) {
                  return false;
            }
            final Room other = (Room) obj;
            if (!Objects.equals(this.id, other.id)) {
                  return false;
            }
            if (!Objects.equals(this.name, other.name)) {
                  return false;
            }
            return true;
      }

      public String getId() {
            return id;
      }

      public String getName() {
            return name;
      }

}
