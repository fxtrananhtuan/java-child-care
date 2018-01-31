/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Nanny;

/**
 *
 * @author CristianoT
 */
public class Entity {
    private int code;
    private String name;
    private int yearofbirth ;
    private String address;
    private String mail;
    private String phone;
    private double charge;
    private int workinghours;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public int getChildassignedtoher() {
        return childassignedtoher;
    }

    public void setChildassignedtoher(int childassignedtoher) {
        this.childassignedtoher = childassignedtoher;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getWorkinghours() {
        return workinghours;
    }

    public void setWorkinghours(int workinghours) {
        this.workinghours = workinghours;
    }

    public int getYearofbirth() {
        return yearofbirth;
    }

    public void setYearofbirth(int yearofbirth) {
        this.yearofbirth = yearofbirth;
    }
    private int childassignedtoher;

    public Entity(int code, String name, int yearofbirth, String address, String phone, String mail, double charge, int workinghours, int childassignedtoher) {
        this.code = code;
        this.name = name;
        this.yearofbirth = yearofbirth;
        this.address = address;
        this.mail = mail;
        this.phone = phone;
        this.charge = charge;
        this.workinghours = workinghours;
        this.childassignedtoher = childassignedtoher;
    }

    public Entity(String name, int yearofbirth, String address, String phone, String mail, double charge, int workinghours, int childassignedtoher) {
        this.name = name;
        this.yearofbirth = yearofbirth;
        this.address = address;
        this.mail = mail;
        this.phone = phone;
        this.charge = charge;
        this.workinghours = workinghours;
        this.childassignedtoher = childassignedtoher;
    }
}
