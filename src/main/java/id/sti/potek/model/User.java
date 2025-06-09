// model/User.java
package id.sti.potek.model;

public class User {
    private String name;
    private String birthDate;
    private String contact;
    private String password;

    public User(String name, String birthDate, String contact, String password) {
        this.name = name;
        this.birthDate = birthDate;
        this.contact = contact;
        this.password = password;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
