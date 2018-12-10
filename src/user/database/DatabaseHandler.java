/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Statement;

import javax.swing.JOptionPane;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseHandler {
	private static DatabaseHandler handler;
	
	private static final String DB_URL = "jdbc:derby:C:\\Users\\Mariel\\Documents\\NetBeansProjects\\scheduling-system\\SchedulingSys\\ssaDB";
	private static Connection conn = null;
	private static Statement stmt = null;
	
	public DatabaseHandler() {
		createConnection();
		//setupUserTable();
                //setupApptTable();
	}
	
	private void createConnection() {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			conn = DriverManager.getConnection(DB_URL);
		} catch (Exception e) {
                        System.err.println(e);
                       System.err.println(e.getMessage());
		}
	}
	
	public ResultSet execQuery(String query) {
		ResultSet result;
		try {
			stmt = conn.createStatement();
			result = stmt.executeQuery(query);
		} catch (SQLException ex) {
			System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
			return null;
		} finally {
	}
		return result;
	}
	
	public boolean execAction(String qu) {
		try {
			stmt = conn.createStatement();
			stmt.execute(qu);
			return true;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage(), "Error Occurred", JOptionPane.ERROR_MESSAGE);
			System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
			return false;
		} finally {
		}
	}

}
