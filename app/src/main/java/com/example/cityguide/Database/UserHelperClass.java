package com.example.cityguide.Database;

public class UserHelperClass {
    String fullname, username, email, phone_number, password;

    public UserHelperClass(){};

    public UserHelperClass(String fullname, String username, String email, String phone_number, String password) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.phone_number = phone_number;
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
