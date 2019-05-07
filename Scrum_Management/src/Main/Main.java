/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import BL.DataBase;
import BL.Project;
import GUI.MainScreenGUI;
import GUI.ProjectCreationGUI;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author johannesriedmueller
 */
public class Main {
    
    public boolean projectIsCreated() throws SQLException{
        Statement stat = DataBase.getDbInstance().getConn().createStatement();
        String sqlString = "SELECT COUNT(*) FROM public.\"Project\"";
        ResultSet set = stat.executeQuery(sqlString);
        while(set.next()){
            if(Integer.parseInt(set.getString(1)) >= 1){
                stat.close();
                return true;
            }
        }
        stat.close();
        return false;
    }
    
    private Project getProject() throws SQLException {
        Statement stat = DataBase.getDbInstance().getConn().createStatement();
        String sqlString = "SELECT \"ProjID\",\"Name\",\"Description\" FROM public.\"Project\"";
        ResultSet set = stat.executeQuery(sqlString);
        if(set.next()){
            return new Project(Integer.parseInt(set.getString("ProjID")), set.getString("Name"), set.getString("Description"));
        }
        return null;
    }
    
    public static void main(String[] args) {
        Main main = new Main();
        try {
            if(main.projectIsCreated()){
                Project project = main.getProject();
                if (project != null) {
                    new MainScreenGUI(project).setVisible(true);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Something went wrong...");
                }
            }
            else{
                new ProjectCreationGUI().setVisible(true);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
}
