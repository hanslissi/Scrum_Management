/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

import java.awt.Color;
import java.time.LocalDate;

/**
 *
 * @author johannesriedmueller
 */
public class Task {
    private String taskName;
    private User user;
    private LocalDate startDate;
    private LocalDate endDate;
    private Color color;

    public Task(String taskName, User user) {
        this.taskName = taskName;
        this.user = user;
    }

    public Task(String taskName, User user, LocalDate startDate, LocalDate endDate) {
        this.taskName = taskName;
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.color = user.getColor();
    }

    public String getTaskName() {
        return taskName;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return String.format("%s: %s - %s", taskName, startDate, endDate);
    }
    
    
    
    
}
