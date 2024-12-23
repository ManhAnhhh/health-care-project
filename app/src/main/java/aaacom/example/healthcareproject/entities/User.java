package aaacom.example.healthcareproject.entities;

public class User {
    private String email;
    private String password;
    private String fullName;
    private String phone;

    public User() {
    }

    public User(String email, String fullName, String password, String phone) {
        this.email = email;
        this.fullName = fullName;
        this.password = password;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
