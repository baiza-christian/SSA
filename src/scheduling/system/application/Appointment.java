/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.system.application;

import java.util.Date;

/**
 *
 * @author christianbaiza
 */
public class Appointment {
    private String title;
    private String date;
    private String location;

    public Appointment() {
        this.title = "";
        this.date = "";
        this.location = "";
    }
    public Appointment(String title, String date, String loc) {
        this.title = title;
        this.date = date;
        this.location = loc;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String l) {
        this.location = l;
    }
    
}
