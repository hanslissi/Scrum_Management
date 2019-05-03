/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BL;

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

    private DataBase () throws SQLException{
        this.conn = DriverManager.getConnection("jdbc:postgresql://localhost:1521/scrum-management", "postgres", "Postgresql4me");
    }
    
    public static synchronized DataBase getDbInstance() throws SQLException{
        if(dbInstance == null){
            dbInstance = new DataBase();
        }
        return dbInstance;
    }

    public Connection getConn() {
        return conn;
    }
    
    
    
}
