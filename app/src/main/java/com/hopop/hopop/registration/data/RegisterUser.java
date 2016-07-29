package com.hopop.hopop.registration.data;

public class RegisterUser {
    private String mobile_number;
    private String first_name;
    private String last_name;
    private String mail_id;
    private String password;

    private String dob;
    private String gender;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

  /*  public String getFemaleSex() {
        return femaleSex;
    }

    public void setFemaleSex(String femaleSex) {
        this.femaleSex = femaleSex;
    }

    public String getMaleSex() {
        return maleSex;
    }

    public void setMaleSex(String maleSex) {
        this.maleSex = maleSex;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }*/
    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMail_id() {
        return mail_id;
    }

    public void setMail_id(String mail_id) {
        this.mail_id = mail_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}