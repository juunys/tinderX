package com.juny.tinderx;

public class User {

    private String Name;
    private String Email;
    private String Password;
    private String Age;


    public User(){}

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public User(String name, String email, String age, String password) {

        Name = name;
        Email = email;
        Age = age;
        Password = password;

    }
}
