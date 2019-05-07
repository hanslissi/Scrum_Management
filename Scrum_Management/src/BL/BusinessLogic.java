/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author johannesriedmueller
 */
public class BusinessLogic extends AbstractListModel<User>{
    private ArrayList<Task> tasks = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    
    public void add(User user){
        users.add(user);
        fireIntervalAdded(user, users.size()-1, users.size()-1);
    }
    
    public void add(Task task){
        tasks.add(task);
    }
    
    @Override
    public int getSize() {
        return users.size();
    }

    @Override
    public User getElementAt(int index) {
        return users.get(index);
    }

    public ArrayList<User> getUsers() {
        return users;
    }
    
    
}
