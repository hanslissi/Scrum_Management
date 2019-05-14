/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BL.BusinessLogic;
import BL.Project;
import BL.Task;
import BL.User;
import BL.myTableCellRenderer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author johannesriedmueller
 */
public class MainScreenGUI extends javax.swing.JFrame {

    private BusinessLogic bl = new BusinessLogic();
    private Project project;
    private LocalDate currentWeek = LocalDate.now();
    private Image dbImage;
    private Graphics2D dbg;
    private static DateTimeFormatter dtf;

    static {
        dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    }

    public MainScreenGUI(Project project) {
        initComponents();
        this.project = project;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        currentWeek = currentWeek.plusDays(7).minusDays(currentWeek.getDayOfWeek().getValue() + 6);
        tableUsers.setModel(bl);
        tableUsers.setDefaultRenderer(Object.class, new myTableCellRenderer());
        try {
            updateEveryThing();
        } catch (SQLException ex) {
            Logger.getLogger(MainScreenGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateTimeline(Graphics g) {
        //doubleBuffering
        dbImage = createImage(paDraw.getWidth(), paDraw.getHeight());
        dbg = (Graphics2D) dbImage.getGraphics();
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        dbg.setRenderingHints(rh);
        paintComponent(dbg);
        g.drawImage(dbImage, 0, 0, this);
    }

    public void paintComponent(Graphics2D g2) {
        drawDate(g2);
        bl.drawTasks(currentWeek, g2, paDraw.getWidth(), paDraw.getHeight());
    }

    private void drawDate(Graphics2D g2) {
        for (int i = 1; i <= 7; i++) {
            g2.drawString(currentWeek.plusDays(i-1).format(dtf), paDraw.getWidth() / 7 * i - paDraw.getWidth() / 15 - 30, 17);
            g2.drawLine(paDraw.getWidth() / 7 * i - paDraw.getWidth() / 15, 20, paDraw.getWidth() / 7 * i - paDraw.getWidth() / 15, paDraw.getHeight());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btAddUser = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableUsers = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btAddTask = new javax.swing.JButton();
        btProductBacklog = new javax.swing.JButton();
        panel = new javax.swing.JPanel();
        laCurrentWeek = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btLeft = new javax.swing.JButton();
        btRight = new javax.swing.JButton();
        paDraw = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(200, 490));
        jPanel1.setLayout(new java.awt.BorderLayout());

        btAddUser.setText("Add User");
        btAddUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddUserActionPerformed(evt);
            }
        });
        jPanel1.add(btAddUser, java.awt.BorderLayout.PAGE_END);

        tableUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tableUsers);

        jPanel1.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.LINE_START);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setPreferredSize(new java.awt.Dimension(533, 25));
        jPanel3.setLayout(new java.awt.GridLayout(1, 2));

        btAddTask.setText("Add Task");
        btAddTask.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddTaskActionPerformed(evt);
            }
        });
        jPanel3.add(btAddTask);

        btProductBacklog.setText("Change to Product Backlog");
        btProductBacklog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btProductBacklogActionPerformed(evt);
            }
        });
        jPanel3.add(btProductBacklog);

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        panel.setLayout(new java.awt.BorderLayout());

        laCurrentWeek.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        laCurrentWeek.setText("Current Week");
        panel.add(laCurrentWeek, java.awt.BorderLayout.PAGE_START);

        jPanel4.setPreferredSize(new java.awt.Dimension(327, 25));
        jPanel4.setLayout(new java.awt.GridLayout(1, 2));

        btLeft.setText("<");
        btLeft.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLeftActionPerformed(evt);
            }
        });
        jPanel4.add(btLeft);

        btRight.setText(">");
        btRight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRightActionPerformed(evt);
            }
        });
        jPanel4.add(btRight);

        panel.add(jPanel4, java.awt.BorderLayout.PAGE_END);

        paDraw.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                paDrawComponentResized(evt);
            }
        });

        javax.swing.GroupLayout paDrawLayout = new javax.swing.GroupLayout(paDraw);
        paDraw.setLayout(paDrawLayout);
        paDrawLayout.setHorizontalGroup(
            paDrawLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 784, Short.MAX_VALUE)
        );
        paDrawLayout.setVerticalGroup(
            paDrawLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 493, Short.MAX_VALUE)
        );

        panel.add(paDraw, java.awt.BorderLayout.CENTER);

        jPanel2.add(panel, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void updateEveryThing() throws SQLException {
        this.setTitle(project.getName());
        try {
            bl.updateEverythingBL(project.getProjectId());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage());
        }
        updateTimeline(paDraw.getGraphics());
    }
    private void btAddUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddUserActionPerformed
        String name = JOptionPane.showInputDialog("Please enter a name:");
        User user = new User(name);
        String userid = "";
        try {
            userid = bl.checkUserId(name);
        } catch (SQLException ex) {
            Logger.getLogger(MainScreenGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!userid.equals("")) {
            try {
                bl.add(user.setUserid(userid), project.getProjectId(), true);
            } catch (SQLException ex) {
                Logger.getLogger(MainScreenGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btAddUserActionPerformed

    private void btAddTaskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddTaskActionPerformed
        TaskDialog dialog = new TaskDialog(this, true, bl.getUsers());
        dialog.setVisible(true);
        if (dialog.isOk()) {
            Task task = dialog.getTask();
            try {
                bl.add(task, project.getProjectId(), true);
            } catch (SQLException ex) {
                Logger.getLogger(MainScreenGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            updateTimeline(paDraw.getGraphics());
        }
    }//GEN-LAST:event_btAddTaskActionPerformed

    private void btProductBacklogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btProductBacklogActionPerformed

    }//GEN-LAST:event_btProductBacklogActionPerformed

    private void btLeftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLeftActionPerformed
        currentWeek = currentWeek.minusDays(7);
        updateTimeline(paDraw.getGraphics());
    }//GEN-LAST:event_btLeftActionPerformed

    private void btRightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRightActionPerformed
        currentWeek = currentWeek.plusDays(7);
        updateTimeline(paDraw.getGraphics());
    }//GEN-LAST:event_btRightActionPerformed

    private void paDrawComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_paDrawComponentResized
        updateTimeline(paDraw.getGraphics());
    }//GEN-LAST:event_paDrawComponentResized

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAddTask;
    private javax.swing.JButton btAddUser;
    private javax.swing.JButton btLeft;
    private javax.swing.JButton btProductBacklog;
    private javax.swing.JButton btRight;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel laCurrentWeek;
    private javax.swing.JPanel paDraw;
    private javax.swing.JPanel panel;
    private javax.swing.JTable tableUsers;
    // End of variables declaration//GEN-END:variables

}
