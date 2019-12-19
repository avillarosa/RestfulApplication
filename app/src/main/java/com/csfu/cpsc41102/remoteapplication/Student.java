package com.csfu.cpsc41102.remoteapplication;

import org.json.JSONObject;

public class Student {
    private String firstName;
    private String lastName;
    private String cwid;

    public Student() {
    }

    public Student(String firstName, String lastName, String cwid) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cwid = cwid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCwid() {
        return cwid;
    }

    public void setCwid(String cwid) {
        this.cwid = cwid;
    }

    public JSONObject toJsonObject() throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("First Name", firstName);
        obj.put("Last Name", lastName);
        obj.put("CWID", cwid);
        return obj;
    }

    public void fromJSON(JSONObject jObj) throws Exception {
        firstName = jObj.getString("First Name");
        lastName = jObj.getString("Last Name");
        cwid = jObj.getString("CWID");
    }
}
