package com.xust.wtc.Entity;

/**
 * 个人信息显示类
 * Created by Spirit on 2018/1/9.
 */
public class DisplayPerson {

    private int id;

    private String name;

    private int gender;

    private String autograph;

    private String portrait;

    public DisplayPerson() {
    }

    public DisplayPerson(int id, String name, int gender, String autograph, String portrait) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.autograph = autograph;
        this.portrait = portrait;
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

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
}
