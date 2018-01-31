package com.xust.wtc.Entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by Spirit on 2017/12/4.
 */
public class Person implements Serializable {

    public interface Login{}

    public interface Register{}

    public interface Other{}

    @NotNull(groups = Other.class)
    private int id;
    @NotBlank(groups = {Other.class, Register.class})
    private String name;
    @NotBlank(groups = {Login.class, Register.class})
    private String loginName;
    @NotBlank(groups = {Login.class, Register.class})
    private String loginPasswd;
    private int status;
    private int balance;
    @Range(max = 1, min = 0, groups = {Other.class, Register.class})
    private int gender;
    @Size(max = 100, groups = {Other.class, Register.class})
    private String autograph;

    @Email(groups = {Register.class})
    private String email;

    private String portrait;

    public Person() {}

    public Person(String name, String loginName, String loginPasswd , int gender) {
        this.name = name;
        this.loginName = loginName;
        this.loginPasswd = loginPasswd;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPasswd() {
        return loginPasswd;
    }

    public void setLoginPasswd(String loginPasswd) {
        this.loginPasswd = loginPasswd;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }


    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAutograph() {
        return autograph;
    }

    public void setAutograph(String autograph) {
        this.autograph = autograph;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", loginName='" + loginName + '\'' +
                ", loginPasswd='" + loginPasswd + '\'' +
                ", status=" + status +
                ", balance=" + balance +
                ", gender=" + gender +
                ", autograph='" + autograph + '\'' +
                '}';
    }
}
