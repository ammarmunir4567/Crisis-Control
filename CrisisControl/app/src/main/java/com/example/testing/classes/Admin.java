package com.example.testing.classes;


import androidx.room.Entity;
import androidx.room.Ignore;

@Entity
public class Admin extends Account {
   private String CNIC;
   private String name;

   public Admin(int accID, String userName, String Pass, String CNIC, String name) {
      super(accID, userName, Pass);
      this.CNIC = CNIC;
      this.name = name;
   }

   public String getCNIC() {
      return CNIC;
   }

   public void setCNIC(String CNIC) {
      this.CNIC = CNIC;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }
}
