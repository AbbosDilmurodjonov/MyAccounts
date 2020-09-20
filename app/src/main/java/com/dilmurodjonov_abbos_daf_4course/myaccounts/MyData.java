package com.dilmurodjonov_abbos_daf_4course.myaccounts;

public class MyData {
    String name;
    String login;
    String password;
    String comment;

    public  MyData(){}

    public MyData(String name, String login, String password, String comment) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
