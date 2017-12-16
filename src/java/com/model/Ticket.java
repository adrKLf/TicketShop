

package com.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * 
 * @author Adrian Garcia Garro
 */
public final class Ticket implements Serializable, Cloneable{

      private int number;
      private User user;
      private boolean free;
      private Event evento;

      public Ticket(int number) {
            this.number = number;
            free = true;
      }
      
      public synchronized void setUser(User user) {
            this.user = user;
            free = false;
      }
      
      public void setEvent(Event e) {
            this.evento = e;
            free = false;
      }
      
      public void removeUser(){
            user = null;
            free = true;
      }
      
      public void removeEvent(){
            evento = null;
            free = true;
      }
      
      public int getNumber() {
            return number;
      }

      public boolean isFree() {
            return free;
      }

      public User getUser() {
            return user;
      }

      public Event getEvento() {
            return evento;
      }

      @Override
      public String toString() {
            return "Ticket{" + "number=" + number + ", user=" + user + ", free=" + free + ", evento=" + evento + '}';
      }

      @Override
      public int hashCode() {
            int hash = 7;
            hash = 79 * hash + this.number;
            hash = 79 * hash + Objects.hashCode(this.user);
            return hash;
      }

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
            final Ticket other = (Ticket) obj;
            if (this.number != other.number) {
                  return false;
            }
            if (!Objects.equals(this.user, other.user)) {
                  return false;
            }
            return true;
      }
      
      
      
}
