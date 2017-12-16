
package com.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * 
 * @author Adrian Garcia Garro
 */

//Aqui gestiono los eventos
public final class Event implements Serializable{

      private String name;
      private LocalDate date;
      private double prize;

      public Event(String name, LocalDate date, double prize) {
            this.name = name;
            this.date = date;
            this.prize = prize;
      }
 
      @Override
      public int hashCode() {
            int hash = 7;
            hash = 53 * hash + Objects.hashCode(this.name);
            hash = 53 * hash + Objects.hashCode(this.date);
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
            final Event other = (Event) obj;
            if (!Objects.equals(this.name, other.name)) {
                  return false;
            }
            if (!Objects.equals(this.date, other.date)) {
                  return false;
            }
            return true;
      }

      public String getName() {
            return name;
      }

      public LocalDate getDate() {
            return date;
      }

      public double getPrize() {
            return prize;
      }

      @Override
      public String toString() {
            return "Nombre: " + name + "\tFecha: " + date + 
                  "\tPrecio: " + prize; 
      }
 
}
