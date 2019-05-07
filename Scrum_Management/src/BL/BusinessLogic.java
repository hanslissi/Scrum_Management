/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

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

    public void add(User user) {
        users.add(user);
        fireTableRowsInserted(users.size()-1, users.size()-1);
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public ArrayList<User> getUsers() {
        return users;
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
