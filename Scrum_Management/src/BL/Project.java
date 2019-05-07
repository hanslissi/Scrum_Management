/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

/**
 *
 * @author johannesriedmueller
 */
public class Project {
    private String name;
    private String description;
    private User projectLeader;
    private final String projectId;
    
    public Project(int projectId, String name, String description) {
        this.name = name;
        this.description = description;
        this.projectId = Integer.toString(projectId);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public User getProjectLeader() {
        return projectLeader;
    }

    public String getProjectId() {
        return projectId;
    }
    
    

    public void setProjectLeader(User projectLeader) {
        this.projectLeader = projectLeader;
    }
    
    
    
}
