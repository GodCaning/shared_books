package com.xust.wtc.Entity;

/**
 * 个人信息显示类
 * Created by Spirit on 2018/1/9.
 */
public class DisplayPerson {

    private int id;

    private String name;

    private String birthdate;

    private int gender;

    private String autograph;

    public DisplayPerson() {
    }

    public DisplayPerson(int id, String name, String birthdate, int gender, String autograph) {
        this.id = id;
        this.name = name;
        this.birthdate = birthdate;
        this.gender = gender;
        this.autograph = autograph;
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

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
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
}
