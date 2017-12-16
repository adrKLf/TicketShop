package com.model;

import java.io.Serializable;

/**
 * 
 * @author Adrian Garcia Garro
 */

//Clase para gestionar los datos de pago
public final class DataPay implements Serializable{

      public enum PayMethod {
            DebitCard, CreditCard, TransferBank
      }
      public enum CardType {
            VISA, MASTERCARD, DISCOVER
      }
      
      private String regExCards = 
            "^(?:4\\d([\\-])?\\d{6}\\1\\d{5}|(?:4\\d{3}|5[1-5]\\d{2}|6011)([\\- ])?\\d{4}\\2\\d{4}\\2\\d{4})$";
      
      private String regExDNI = 
            "\\d{8}[A-HJ-NP-TV-Z]"
            ;
      
      private String name;
      private String surname;
      private String address;
      private String DNI;
      private PayMethod paymethod;
      private String DebitCardNumber;
      private String CreditCardNumber;
      private String NumberAccountBank;

      public DataPay(String name, String surname, String address, String DNI, PayMethod paymethod) 
      throws RuntimeException, IllegalArgumentException, NullPointerException {
            
            if(name.length() <= 3 || name.length()>= 12)
                  throw new IllegalArgumentException("El nombre tiene que estar entre 2 y 12 caracteres");
            this.name = name;
            
            if(surname.length() <= 3 || surname.length()>= 35)
                  throw new IllegalArgumentException("Apellidos no válidos");
            this.surname = surname;
            
            if(address.length() <= 5 || address.length()>= 35)
                  throw new IllegalArgumentException("Direccion no válida");
            this.address = address;
            
            if(!DNI.matches(regExDNI))
                throw new RuntimeException("DNI no válido");
            this.DNI = DNI;
            
            if(paymethod == null)
                  throw new NullPointerException("No has introducido un metodo de pago"
                        + "válido");
            this.paymethod = paymethod;
      }

      public void setDebitCardNumber(String DebitCardNumber, CardType cardType) throws RuntimeException, IllegalArgumentException {
            if(paymethod != PayMethod.DebitCard)
                  throw new RuntimeException("El metodo de pago no es con tarjeta de debito");
            if(!(DebitCardNumber.matches(regExCards)))
                  throw new IllegalArgumentException("Ese numero de Tarjeta de Débito no es valido");
            this.DebitCardNumber = DebitCardNumber;
      }

      public void setCreditCardNumber(String CreditCardNumber, CardType cardType) throws RuntimeException, IllegalArgumentException {
            if(paymethod != PayMethod.CreditCard)
                  throw new RuntimeException("El metodo de pago no es con tarjeta de credito");
            if(!(DebitCardNumber.matches(regExCards)))
                  throw new IllegalArgumentException("Ese numero de Tarjeta de Crédito no es valido"); 
            this.CreditCardNumber = CreditCardNumber;
      }

      public void setNumberAccountBank(String NumberAccountBank) throws RuntimeException, IllegalArgumentException {
            if(paymethod != PayMethod.TransferBank)
                  throw new RuntimeException("El metodo de pago no es con transferencia bancaria");
            if (NumberAccountBank.length() != 20 && NumberAccountBank.matches("[0-9]*"))
                throw new IllegalArgumentException("El número de cuenta bancaria no el formato adecuado");

            this.NumberAccountBank = NumberAccountBank;
      }

      public String getName() {
            return name;
      }

      public String getSurname() {
            return surname;
      }

      public String getAddress() {
            return address;
      }

      public String getDNI() {
            return DNI;
      }

      public PayMethod getPaymethod() {
            return paymethod;
      }

      public String getDebitCardNumber() {
            return DebitCardNumber;
      }

      public String getCreditCardNumber() {
            return CreditCardNumber;
      }

      public String getNumberAccountBank() {
            return NumberAccountBank;
      }    

      @Override
      public String toString() {
            return "DataPay{" + "name=" + name + ", surname=" + surname + ", address=" + address + ", DNI=" + DNI + ", paymethod=" + paymethod + ", DebitCardNumber=" + DebitCardNumber + ", CreditCardNumber=" + CreditCardNumber + ", NumberAccountBank=" + NumberAccountBank + '}';
      }
      
}
