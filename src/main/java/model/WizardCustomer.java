/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Мария
 */
import java.time.LocalDate;

public class WizardCustomer {
    private int id;
    private String firstName;
    private String lastName;
    private String school; 
    private String contactInfo;

    public WizardCustomer() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
   
    public String getSchool() { return school; }
    public void setSchool(String school) { this.school = school; }
    
    public String getContactInfo() { return contactInfo; }
    public void setContactInfo(String contactInfo) { this.contactInfo = contactInfo; }

    @Override
    public String toString() {
        return String.valueOf(id) + " " + firstName + " " + lastName;
    }
}
