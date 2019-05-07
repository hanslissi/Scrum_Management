/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BL.DataBase;
import BL.Project;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author johannesriedmueller
 */
public class ProjectCreationGUI extends javax.swing.JFrame {

    private final String placeholderName = "Name of Project";
    private final String placeholderDescription = "Short Description";
    private Project project;
    private int projectId = 1;

    public ProjectCreationGUI() {
        initComponents();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        tfName.setText(placeholderName);
        tfDescription.setText(placeholderDescription);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btCreate = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        tfName = new javax.swing.JTextField();
        tfDescription = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(51, 51, 51));

        jLabel1.setFont(new java.awt.Font("Open Sans", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Project Creation");
        getContentPane().add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jPanel1.setLayout(new java.awt.BorderLayout());

        btCreate.setBackground(new java.awt.Color(222, 222, 222));
        btCreate.setFont(new java.awt.Font("Open Sans", 0, 18)); // NOI18N
        btCreate.setText("Create");
        btCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCreateActionPerformed(evt);
            }
        });
        jPanel1.add(btCreate, java.awt.BorderLayout.PAGE_END);

        jPanel2.setLayout(new java.awt.GridLayout(2, 1));

        tfName.setForeground(new java.awt.Color(170, 170, 170));
        tfName.setText("Name of Project");
        tfName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfNameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfNameFocusLost(evt);
            }
        });
        jPanel2.add(tfName);

        tfDescription.setForeground(new java.awt.Color(170, 170, 170));
        tfDescription.setText("Short Description");
        tfDescription.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tfDescriptionFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfDescriptionFocusLost(evt);
            }
        });
        jPanel2.add(tfDescription);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void updatePlaceholder(boolean gained, JTextField field, String toSet) {
        if (gained) {
            if (field.getText().equals(toSet)) {
                field.setText("");
                field.setForeground(new java.awt.Color(0, 0, 0));
            }
        } else {
            if (field.getText().equals("")) {
                field.setText(toSet);
                field.setForeground(new java.awt.Color(170, 170, 170));
            }
        }
    }

    private void addProjectToDataBase(Project project) throws SQLException {
        Statement stat = DataBase.getDbInstance().getConn().createStatement();
        String sqlString = String.format("INSERT INTO public.\"Project\"(\"ProjID\", \"Name\", \"Description\") VALUES (%s, \'%s\', \'%s\');", project.getProjectId(), project.getName(), project.getDescription());
        stat.executeQuery(sqlString);
        stat.close();
    }

    private void tfNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfNameFocusGained
        updatePlaceholder(true, tfName, placeholderName);
    }//GEN-LAST:event_tfNameFocusGained

    private void tfNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfNameFocusLost
        updatePlaceholder(false, tfName, placeholderName);
    }//GEN-LAST:event_tfNameFocusLost

    private void tfDescriptionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfDescriptionFocusGained
        updatePlaceholder(true, tfDescription, placeholderDescription);
    }//GEN-LAST:event_tfDescriptionFocusGained

    private void tfDescriptionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfDescriptionFocusLost
        updatePlaceholder(false, tfDescription, placeholderDescription);
    }//GEN-LAST:event_tfDescriptionFocusLost

    private void btCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCreateActionPerformed
        if (tfName.getText().equals(placeholderName)
                || tfDescription.getText().equals(placeholderDescription)) {
            int res = JOptionPane.showOptionDialog(null, "Do you really want to create a Project with some defaults?", "Caution!", JOptionPane.YES_NO_OPTION, 1, null, null, null);
            if (res == 0) {
                this.project = new Project(projectId, tfName.getText(), tfDescription.getText());
                try {
                    addProjectToDataBase(project);
                } catch (SQLException ex) {
                    Logger.getLogger(ProjectCreationGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            new MainScreenGUI(project).setVisible(true);
            this.dispose();
            }
        } else {
            try {
                this.project = new Project(projectId, tfName.getText(), tfDescription.getText());
                addProjectToDataBase(project);
            } catch (SQLException ex) {
                Logger.getLogger(ProjectCreationGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            new MainScreenGUI(project).setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_btCreateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCreate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField tfDescription;
    private javax.swing.JTextField tfName;
    // End of variables declaration//GEN-END:variables
}
