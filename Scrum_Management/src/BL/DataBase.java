/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author johannesriedmueller
 */
public class DataBase {
    
    private static DataBase dbInstance;
    
    private Connection conn;

    private DataBase () throws SQLException, FileNotFoundException, IOException{
        BufferedReader br = new BufferedReader(new FileReader(new File("./preferences.txt")));
        String serverURL = br.readLine().split("=")[1];
        String user = br.readLine().split("=")[1];
        String password = br.readLine().split("=")[1];
        this.conn = DriverManager.getConnection(serverURL, user, password);
    }
    
    public static synchronized DataBase getDbInstance() throws SQLException, FileNotFoundException, IOException{
        if(dbInstance == null){
            dbInstance = new DataBase();
        }
        return dbInstance;
    }

    public Connection getConn() {
        return conn;
    }
    
    
    
}
