package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.ConnectDB;
import entity.ProfileEmp;

public class ProfileEmpDao {
	public static ProfileEmp getProfileById(int employeeID) {
		var profile = new ProfileEmp();
        String sql = "SELECT * FROM EmployeeDetails WHERE EmployeeID = ?";
        try (Connection con = ConnectDB.connect();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            
            pstmt.setInt(1, employeeID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                	profile.setEmployeeID(rs.getInt("EmployeeID"));
                	profile.setDateOfBirth(rs.getDate("DateOfBirth"));
                	profile.setPhoneNumber(rs.getString("PhoneNumber"));
                	profile.setAddress(rs.getString("Address"));
                	profile.setGender(rs.getString("Gender"));
                	profile.setImage(rs.getString("Image"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profile;
    }
}
