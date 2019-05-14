/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

import java.awt.Color;
import java.util.Random;

/**
 *
 * @author johannesriedmueller
 */
public class User {

    private String name;
    private String userid;
    private Color color;

    public User(String name) {
        this.name = name;
        Random r = new Random();
        this.color = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
    }

    
    
    public User(String name, String userid) {
        this.name = name;
        this.userid = userid;
        Random r = new Random();
        this.color = new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public String getUserid() {
        return userid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
    
    @Override
    public String toString() {
        return name;
    }

}
