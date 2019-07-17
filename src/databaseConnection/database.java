/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class database {
    Connection con=null; // we are using this code for connection to sql developer database for rasimmetindemiral
       PreparedStatement ps = null; 
       ResultSet rs = null;      
       Statement st=null;
       
     public static void connection() throws SQLException, ClassNotFoundException  {
    Connection con=null; // we are using this code for connection to sql developer database for rasimmetindemiral
    
         try {
           Class.forName("oracle.jdbc.driver.OracleDriver"); 
           con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/rasimmetindemiral", "hr", "123");
              
           } 
               catch(SQLException s) {
                           s.printStackTrace();
           }       
    }
    
    
    
}

    
    

