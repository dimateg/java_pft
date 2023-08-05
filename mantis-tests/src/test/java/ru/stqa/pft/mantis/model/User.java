package ru.stqa.pft.mantis.model;

import javax.persistence.*;

@Entity
@Table(name = "mantis_user_table")
public class User {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "realname")
    private String name;

    @Column(name = "username ")
    private String login;

    @Column(name = "email")
    private String email;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }
}