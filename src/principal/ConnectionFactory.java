/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    
    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection("jdbc:mysql://localhost/bdsistema","root","");
        
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            ConnectionFactory obj = new ConnectionFactory();
            Connection con = obj.getConnection();
            System.out.println("CONECTADO!!!");
        } catch (SQLException ex) {
            System.out.println("Ocorreu uma exceção: "+
                    ex.getMessage());
        }
       
    }
    
}
