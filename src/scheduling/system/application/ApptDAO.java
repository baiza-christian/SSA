/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduling.system.application;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Mariel
 */
public class ApptDAO implements DAO {
    private Appointment createAppointment(ResultSet rs) {
      Appointment p = new Appointment();
      try {
         p.setTitle(rs.getString("title"));
         p.setDate(rs.getString("date"));
         p.setLocation(rs.getString("location"));
      } catch (SQLException ex) {
      }
      return p;
   }
   public List<Appointment> getAppts() {
      String sql = "Select * from appointments order by title";
      List<Appointment> list = new ArrayList<>();
      try {
         Class.forName(DRIVER);
         Connection con = DriverManager.getConnection
            (DB_URL);
         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(sql);
         while (rs.next()) {
            Appointment p = createAppointment(rs);
            list.add(p);
         }
         rs.close();
         con.close();
      } catch (ClassNotFoundException | SQLException ex) {
      }
      return list;
   }

   public List<Appointment> getAppt(String title, String date) {
      String sql = "Select * from appointments where title like '%" +
         title + "%' and date like '%" + date + "%'";
      List<Appointment> list = new ArrayList<>();
      try {
         Class.forName(DRIVER);
         Connection con = DriverManager.getConnection
            (DB_URL);
         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(sql);
         while (rs.next()) {
            Appointment p = createAppointment(rs);
            list.add(p);
         }
         rs.close();
         con.close();
      } catch (ClassNotFoundException | SQLException ex) {
      }
      return list;
   }
   
      public void deleteAppt(String title, String date) {
        PreparedStatement pstmt = null;
        try {
         Class.forName(DRIVER);
         Connection con = DriverManager.getConnection(DB_URL);
         
        String sql = "delete from appointments " +
        "where title = ? and adate = ?";
        pstmt = con.prepareStatement(sql);
        pstmt.setString(1, title);
        pstmt.setString(2, date);

   
        pstmt.executeUpdate();
            
        pstmt.close();
        con.close();
      } catch (ClassNotFoundException | SQLException ex) {
      }
   }
      
      public String createAppt(Appointment a)
      {/*
        PreparedStatement pstmt = null;
        try {
         Class.forName(DRIVER);
         Connection con = DriverManager.getConnection(DB_URL);
         
        String sql = "insert into appointments values(?,?,?)";
        pstmt = con.prepareStatement(sql);
        pstmt.setString(1, a.getTitle());
        pstmt.setString(2, a.getDate());
        pstmt.setString(3, a.getLocation());
   
        pstmt.executeUpdate();
            
        pstmt.close();
        con.close();
      } catch (ClassNotFoundException | SQLException ex) {
      }*/
        Connection conn = null; //initialize the connection
        Statement stmt = null;  //initialize the statement that we're using
        try {

            Class.forName("org.apache.derby.jdbc.ClientDriver");

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);

            String insertstmt = "insert into appointments values(?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertstmt);
            
            pstmt.setString(1, a.getTitle());
            pstmt.setString(2, a.getDate());
            pstmt.setString(3, a.getLocation());
       
            int recordsUpdated = pstmt.executeUpdate();
            
            conn.close();
            if (recordsUpdated > 0)
            {
                return "Appointment created.";
            }
         
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
            
        }
        return "Error: cannot create appointment";
      }
      
      public void modifyAppt(String apptTitle, String oldDate, String newDate, String newLoc)
      {
          PreparedStatement pstmt = null;
          try {
           Class.forName(DRIVER);
           Connection con = DriverManager.getConnection(DB_URL);

          String sql = "update appointments set adate = ?, location = ? where title = ? and oldDate = ?";
          pstmt = con.prepareStatement(sql);
          pstmt.setString(1, newDate);
          pstmt.setString(2, newLoc);
          pstmt.setString(3, apptTitle);
          pstmt.setString(3, oldDate);

          pstmt.executeUpdate();

          pstmt.close();
          con.close();
        } catch (ClassNotFoundException | SQLException ex) {
        }
      }
   
   
}
