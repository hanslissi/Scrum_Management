/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author johannesriedmueller
 */
public class BusinessLogic extends AbstractTableModel {

    private ArrayList<Task> tasks = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private final String[] userColNames = {"Username", "Color"};
    private static DateTimeFormatter dtf;
    private static DateTimeFormatter dtfFromDataBase;

    static {
        dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        dtfFromDataBase = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    /**
     * Adds the user
     * @param user User which should be added
     * @param projID Project where the user should be added to (foreign key)
     * @param addToDataBase when its false it will only be added locally. ProjID can be null when this is false
     * @throws SQLException 
     */
    public void add(User user, String projID, boolean addToDataBase) throws SQLException, IOException {
        if (addToDataBase) {
            addUserToDataBase(user, projID);
            String userID = checkUserId(user.getName());
            if (!userID.equals("")) {
                user.setUserid(userID);
            }
        }
        users.add(user);
        fireTableRowsInserted(users.size() - 1, users.size() - 1);
    }

    public void add(Task task, String projID, boolean addToDataBase) throws SQLException, IOException {
        if (addToDataBase) {
            addTaskToDataBase(task, projID);
            String taskID = getLastAddedTaskID();
            if (!taskID.equals("")) {
                task.setTaskid(taskID);
            }

        }
        tasks.add(task);
    }

    /**
     * Adds the given user to the Database with the foreign key to the given project
     * @param user User which should be added
     * @param projID Project where the user should be added to (foreign key)
     * @throws SQLException 
     */
    public void addUserToDataBase(User user, String projID) throws SQLException, IOException {
        Statement stat = DataBase.getDbInstance().getConn().createStatement();
        String sqlString = String.format("INSERT INTO public.\"User\"(\"UserID\", \"fk_ProjID\", \"Username\") VALUES (DEFAULT, \'%s\', \'%s\');", projID, user.getName());
        stat.executeUpdate(sqlString);
        stat.close();
    }

    /**
     * Adds the given task to the Database with the foreign key to the given project
     * @param task Task which should be added
     * @param projID Project where the task should be added to (foreign key)
     * @throws SQLException 
     */
    public void addTaskToDataBase(Task task, String projID) throws SQLException, IOException {
        Statement stat = DataBase.getDbInstance().getConn().createStatement();
        String sqlString = String.format("INSERT INTO public.\"Task\"(\"StartDate\", \"EndDate\", \"fk_ProjID\", \"fk_UserID\", \"TaskName\") VALUES (TO_DATE(\'%s\','DD.MM.YYYY'), TO_DATE(\'%s\','DD.MM.YYYY'), \'%s\', \'%s\', \'%s\');", dtf.format(task.getStartDate()), dtf.format(task.getEndDate()), projID, task.getUser().getUserid(), task.getTaskName());
        stat.executeUpdate(sqlString);
        stat.close();
    }

    /**
     * Returns the TaskID of the last added Task in the Database
     * @return Returns the TaskID as a String of the last added Task in the Database.
     * @throws SQLException 
     */
    public String getLastAddedTaskID() throws SQLException, IOException {
        Statement stat = DataBase.getDbInstance().getConn().createStatement();
        String sqlString = String.format("SELECT MAX(\"TaskID\") \"Max\" FROM public.\"Task\";");
        ResultSet set = stat.executeQuery(sqlString);
        while (set.next()) {
            return set.getString("Max");
        }
        return "";
    }

    /**
     * Returns the UserID from the Database according to the username
     * @param username The username of the user
     * @return Returns UserID if username is found. When its not found it will return an empty String
     * @throws SQLException
     */
    public String checkUserId(String username) throws SQLException, IOException {
        Statement stat = DataBase.getDbInstance().getConn().createStatement();
        String sqlString = String.format("SELECT * FROM public.\"User\" WHERE \"Username\" = \'%s\'", username);
        ResultSet rs = stat.executeQuery(sqlString);
        while (rs.next()) {
            return rs.getString("UserID");
        }
        return "";
    }

    /**
     * Loads every User and every Task of DataBase into BL (so its locally
     * saved)
     *
     * @param projID
     * @throws SQLException
     * @throws Exception
     */
    public void updateEverythingBL(String projID) throws SQLException, Exception {
        Statement statUsers = DataBase.getDbInstance().getConn().createStatement();
        String sqlString = String.format("SELECT \"Username\", \"UserID\" FROM public.\"User\" WHERE \"fk_ProjID\" = %s;", projID);
        ResultSet rsUsers = statUsers.executeQuery(sqlString);
        while (rsUsers.next()) {
            add(new User(rsUsers.getString("Username"), rsUsers.getString("UserID")), projID, false);
        }
        statUsers.close();
        Statement statTasks = DataBase.getDbInstance().getConn().createStatement();
        String sqlString2 = String.format("SELECT \"TaskName\", \"fk_UserID\", \"StartDate\", \"EndDate\", \"TaskID\" FROM public.\"Task\" WHERE \"fk_ProjID\" = %s;", projID);
        ResultSet rsTasks = statTasks.executeQuery(sqlString2);
        while (rsTasks.next()) {
            User userOfTask = null;
            for (User user : users) {
                if (user.getUserid().equals(rsTasks.getString("fk_UserID"))) {
                    userOfTask = user;
                }
            }
            if (userOfTask == null) {
                throw new Exception("User for task not found!");
            }
            Task task = new Task(
                    rsTasks.getString("TaskName"),
                    userOfTask,
                    LocalDate.parse(rsTasks.getString("StartDate"), dtfFromDataBase),
                    LocalDate.parse(rsTasks.getString("EndDate"), dtfFromDataBase));
            task.setTaskid(rsTasks.getString("TaskID"));
            add(task, projID, false);
        }
        statTasks.close();

    }

    /**
     * Draws every task of the current week to the timeline (Graphics 2D)
     * @param currentWeek current week which is displayed
     * @param g2 Graphics of image
     * @param width width of image
     * @param height height of image
     */
    public void drawTasks(LocalDate currentWeek, Graphics2D g2, int width, int height) {
        int i = 1;
        for (Task task : tasks) {
            if (task.getStartDate().isAfter(currentWeek.minusDays(1)) && task.getStartDate().isBefore(currentWeek.plusDays(7))) {
                Color colorOfText;
                double y = (299 * task.getColor().getRed() + 587 * task.getColor().getGreen() + 114 * task.getColor().getBlue()) / 1000;
                if (y >= 128) {
                    colorOfText = Color.black;
                } else {
                    colorOfText = Color.white;
                }
                g2.setColor(task.getColor());
                if (task.getEndDate().isBefore(currentWeek.plusDays(7))) {
                    int days = (int) DAYS.between(task.getStartDate(), task.getEndDate());
                    g2.fillRect(width / 7 * task.getStartDate().getDayOfWeek().getValue() - width / 15, 25 * i, width / 7 * days, 20);
                    g2.setColor(colorOfText);
                    g2.drawString(task.getTaskName(), width / 7 * task.getStartDate().getDayOfWeek().getValue() - width / 15 + 10, 25 * i + 15);
                } else {
                    g2.fillRect(width / 7 * task.getStartDate().getDayOfWeek().getValue() - width / 15, 25 * i, width, 20);
                    g2.setColor(colorOfText);
                    g2.drawString(task.getTaskName(), width / 7 * task.getStartDate().getDayOfWeek().getValue() - width / 15 + 10, 25 * i + 15);
                }
                i++;
            } else if (task.getEndDate().isAfter(currentWeek.minusDays(1)) && task.getEndDate().isBefore(currentWeek.plusDays(7))) {
                Color colorOfText;
                double y = (299 * task.getColor().getRed() + 587 * task.getColor().getGreen() + 114 * task.getColor().getBlue()) / 1000;
                if (y >= 128) {
                    colorOfText = Color.black;
                } else {
                    colorOfText = Color.white;
                }

                g2.setColor(task.getColor());
                if (task.getEndDate().equals(currentWeek)) {
                    g2.fillRect(0, 25 * i, width / 7 - width / 15, 20);
                } else {
                    g2.fillRect(0, 25 * i, width / 7 * task.getEndDate().getDayOfWeek().getValue() - width / 15, 20);
                }
                g2.setColor(colorOfText);
                g2.drawString(task.getTaskName(), 10, 25 * i + 15);
                i++;
            } else if (task.getStartDate().isBefore(currentWeek.minusDays(1)) && task.getEndDate().isAfter(currentWeek.plusDays(6))) {
                Color colorOfText;
                double y = (299 * task.getColor().getRed() + 587 * task.getColor().getGreen() + 114 * task.getColor().getBlue()) / 1000;
                if (y >= 128) {
                    colorOfText = Color.black;
                } else {
                    colorOfText = Color.white;
                }
                g2.setColor(task.getColor());
                g2.fillRect(0, 25 * i, width, 20);
                g2.setColor(colorOfText);
                g2.drawString(task.getTaskName(), 10, 25 * i + 15);
                i++;
            }
        }
        g2.setColor(Color.BLACK);
    }

    /**
     * Deletes the task with the taskID from the database.
     * @param taskid taskID of task which should be deleted.
     * @throws SQLException 
     */
    public void deleteTaskFromDatabase(String taskid) throws SQLException, IOException {
        Statement stat = DataBase.getDbInstance().getConn().createStatement();
        String sqlString = String.format("DELETE FROM public.\"Task\" WHERE \"TaskID\" = %s;", taskid);
        stat.executeUpdate(sqlString);
        stat.close();
    }
    
    /**
     * Deletes the user with the taskID from the database.
     * @param userid userID of task which should be deleted.
     * @throws SQLException 
     */
    public void deleteUserFromDatabase(String userid) throws SQLException, IOException {
        Statement stat = DataBase.getDbInstance().getConn().createStatement();
        String sqlString = String.format("DELETE FROM public.\"User\" WHERE \"UserID\" = %s;", userid);
        stat.executeUpdate(sqlString);
        stat.close();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Deletes Task from Database and deletes it locally
     * @param idx Index of Task which should be deleted
     * @throws SQLException 
     */
    public void deleteTask(int idx) throws SQLException, IOException {
        deleteTaskFromDatabase(tasks.get(idx).getTaskid());
        tasks.remove(idx);
    }
    
    /**
     * Deletes User and User related tasks from database and locally
     * @param idx Index of User which should be deleted
     * @throws SQLException 
     */
    public void deleteUser(int idx) throws SQLException, IOException {
        for (int i = 0; i < tasks.size(); i++) {
            if(tasks.get(i).getUser() == users.get(idx)){
                deleteTask(i);
                i--;
            }
        }
        deleteUserFromDatabase(users.get(idx).getUserid());
        users.remove(idx);
        fireTableRowsDeleted(0, users.size()-1);
    }

    @Override
    public String getColumnName(int column) {
        return userColNames[column];
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return userColNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return users.get(rowIndex);
    }

}
