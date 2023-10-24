package DBClientApp.DAO;

import DBClientApp.helper.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class PracticeQuery {

//    public static int insert(String practiceName, int practiceID) throws SQLException {
//        String sql = "INSERT INTO CUSTOMERS (Column_Name, Column2_Name) VALUES(?, ?)";
//        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
//        ps.setString(1, practiceName);
//        ps.setInt(2, practiceID);
//        int rowsAffected = ps.executeUpdate();
//        return rowsAffected;
//    }
//
//    public static int update(int practiceID, String practiceName) throws SQLException {
//        String sql = "UPDATE CUSTOMERS SET Column_Name = ? WHERE Column_Name2(ID) = ?";
//        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
//        ps.setString(1, practiceName);
//        ps.setInt(2, practiceID);
//        int rowsAffected = ps.executeUpdate();
//        return rowsAffected;
//    }
//
//    public static int delete(int practiceID) throws SQLException {
//        String sql = "DELETE FROM TABLE WHERE Column_ID = ?";
//        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
//        ps.setInt(1, practiceID);
//        int rowsAffected = ps.executeUpdate();
//        return rowsAffected;
//    }
//
//    public static void select() throws SQLException {
//        String sql = "SELECT * FROM TABLE";
//        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
//        ResultSet rs = ps.executeQuery();
//        while(rs.next()){
//            int practiceID = rs.getInt("Column_ID"); // replace all "table" and "column" with real tables and columns
//            String practiceName = rs.getString("Practice_Name");
//            System.out.print(practiceID + " | ");
//            System.out.print(practiceName + "\n");
//        }
//    }
//
//    public static void select(int nuColumn) throws SQLException {
//        String sql = "SELECT * FROM TABLE WHERE nuColumn = ?";
//        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
//        ps.setInt(1, nuColumn);
//        ResultSet rs = ps.executeQuery();
//        while(rs.next()){
//            int practiceID = rs.getInt("Column_ID"); // replace all "table" and "column" with real tables and columns
//            String practiceName = rs.getString("Practice_Name");
//            int nuColumnFK = rs.getInt("Nu_Column");
//            System.out.print(practiceID + " | ");
//            System.out.print(practiceName + " | ");
//            System.out.print(nuColumnFK + "\n");
//        }
//    }

    //    ***If needed, implement this for create and updated by etc...

//    public static void checkDataConversion() {
//        System.out.println("CREATE DATE TEST");
//        try {
//            PreparedStatement ps = JDBC.getConnection().preparedStatement(sql);
//            ResultSet rs = ps.executeQuery();
//            while(rs.next()) {
//                Timestamp ts = rs.getTimeStamp("Create_Date");
//                System.out.println("CD: " + ts.toLocalDateTime().toString());
//            }
//        }
//        catch (SQLException throwable) {
//            throwable.printStackTrace();
//        }
//    }

    // USE THIS FOR THE DAO AND ERROR MESSAGE STUFF \/

//        int rowsAffected = PracticeQuery.insert(PARAMETERS);
//        if(rowsAffected > 0) {
//            System.out.println("Insert Successful!");
//        }
//        else {
//            System.out.println("Insert failed!");
//        }
//
//        int rowsAffected = PracticeQuery.update(PARAMETERS);
//        if(rowsAffected > 0) {
//            System.out.println("Update Successful!");
//        }
//        else {
//            System.out.println("Update failed!");
//        }
//
//        int rowsAffected = PracticeQuery.delete(PARAMETERS);
//        if(rowsAffected > 0) {
//            System.out.println("Delete Successful!");
//        }
//        else {
//            System.out.println("Delete failed!");
//        }

    // PracticeQuery.select();
    //  PracticeQuery.select(3);

}
