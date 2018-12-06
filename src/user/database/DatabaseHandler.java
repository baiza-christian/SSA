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
	
	private static final String DB_URL = "jdbc:derby:/Users/christianbaiza/NetBeansProjects/Scheduling-System-Application/ssaDB";
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
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
		}
	}
	
	private void setupUserTable() {
		String TABLE_NAME = "users";
		try {
			stmt = conn.createStatement();
			
			DatabaseMetaData dbm = conn.getMetaData();
			ResultSet tables = dbm.getTables(null, null, TABLE_NAME, null);
			
			if (tables.next()) {
				System.out.println("Table " + TABLE_NAME + "already exists. Ready to blast!");
			} else {
				stmt.execute("CREATE TABLE " + TABLE_NAME + "("
						+ "		username varchar(200) primary key,\n"
						+ "		password varchar(200),\n"
						+ "		email varchar(200) primary key,\n"
						+ "		firstname varchar(200),\n"
						+ "		lastname varchar(200)"
						+ "  )");
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage() + " ... setupDatabase");
		}
	}
        
        private void setupApptTable() {
		String TABLE_NAME = "appointments";
		try {
			stmt = conn.createStatement();
			
			DatabaseMetaData dbm = conn.getMetaData();
			ResultSet tables = dbm.getTables(null, null, TABLE_NAME, null);
			
			if (tables.next()) {
				System.out.println("Table " + TABLE_NAME + "already exists. Ready to blast!");
			} else {
				stmt.execute("CREATE TABLE " + TABLE_NAME + "("
						+ "		name varchar(200) primary key,\n"
						+ "		apptDate date primaryKey, \n"
                                                + "             location varchar(200)"
						+ "  )");
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage() + " ... setupDatabase");
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
