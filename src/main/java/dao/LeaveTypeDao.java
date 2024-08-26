package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import component.LeaveTypes;
import database.ConnectDB;

import entity.LeaveType;
import entity.Role;

public class LeaveTypeDao {
	public LeaveType selectLeaveTypeByIdLr(int idLR) {
		var lt = new LeaveType();
		var call = "call selectLeaveTypeByLr(?)";
		try (
				var con = ConnectDB.connect();
				var cs = CreateCallableStmt.createCS(con, idLR, call);
				var result = cs.executeQuery();
		){
			
			while (result.next()) {		
				lt.setLeaveTypeName(result.getString("LeaveTypeName"));
				lt.setLeaveTypeID(result.getInt("LeaveTypeID"));
				lt.setLeaveTypeDescription(result.getString("LeaveTypeDescription"));
				lt.setLeaveDaysPerYear(result.getInt("LeaveDaysPerYear"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return lt;
	}
	
	public LeaveType selectLeaveTypeById(int idLt) {
		var lt = new LeaveType();
		var call = "call selectLeaveType(?)";
		try (
				var con = ConnectDB.connect();
				var cs = CreateCallableStmt.createCS(con, idLt, call);
				var result = cs.executeQuery();
		){
			
			while (result.next()) {		
				lt.setLeaveTypeName(result.getString("LeaveTypeName"));
				lt.setLeaveTypeID(result.getInt("LeaveTypeID"));
				lt.setLeaveTypeDescription(result.getString("LeaveTypeDescription"));
				lt.setLeaveDaysPerYear(result.getInt("LeaveDaysPerYear"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return lt;
	}
	
	public List<LeaveType> selectAllLeaveTypes() {
        var leaveTypes = new ArrayList<LeaveType>();
        var query = "SELECT * FROM LeaveTypes"; // Adjust query as necessary
        try (
                var con = ConnectDB.connect();
                var stmt = con.createStatement();
                var result = stmt.executeQuery(query);
        ) {
            while (result.next()) {
                var lt = new LeaveType();
                lt.setLeaveTypeID(result.getInt("LeaveTypeID"));
                lt.setLeaveTypeName(result.getString("LeaveTypeName"));
                lt.setLeaveTypeDescription(result.getString("LeaveTypeDescription"));
                lt.setLeaveDaysPerYear(result.getInt("LeaveDaysPerYear"));
                leaveTypes.add(lt);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Replace with proper logging
        }
        return leaveTypes;
    }
	
	
	public boolean insertLeaveType(LeaveType leaveType) {
        String call = "call insertleaveType(?, ?, ?)";
        try (
        	var con = ConnectDB.connect();
             var cs = con.prepareCall("{" +call+ "}");
        	
            ) {
            
            // Set parameters
           
            cs.setString(1, leaveType.getLeaveTypeName());
            cs.setString(2, leaveType.getLeaveTypeDescription());
            cs.setInt(3, leaveType.getLeaveDaysPerYear());

            // Execute the stored procedure
            int result = cs.executeUpdate();
            
            // Return true if one or more rows were affected
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
	
	public List<LeaveType> getAll() {
		List<LeaveType> list = new ArrayList<>();  
		try (
	        	var con = ConnectDB.connect();
	            var cs  = con.prepareCall("{call getAllLeaveType}");
	        	var result = cs.executeQuery();
	            ) {
	            
	            // Set parameters
	           
			while (result.next()) {		
				list.add(new LeaveType(
						result.getInt("LeaveTypeID"),
						result.getString("LeaveTypeName"),
						result.getString("LeaveTypeDescription"),
						result.getInt("LeaveDaysPerYear"),
						result.getBoolean("Status")));
			}
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		return list;
	}

	public boolean updateLeaveType(LeaveType leaveType) {
		String call = "call UpdateLeaveType(?, ?, ?, ?)";
		try (
			var con = ConnectDB.connect();
			 var cs = con.prepareCall("{" +call+ "}");
			
			) {
			
			// Set parameters
			cs.setString(1, leaveType.getLeaveTypeName());
			cs.setString(2, leaveType.getLeaveTypeDescription());
			cs.setInt(3, leaveType.getLeaveDaysPerYear());
			cs.setInt(4, leaveType.getLeaveTypeID());
			// Execute the stored procedure
			int result = cs.executeUpdate();
			
			// Return true if one or more rows were affected
			return result > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	   public boolean leaveTypeNameExists(String leaveTypeName) {
	        String query = "SELECT COUNT(*) FROM LeaveTypes WHERE LeaveTypeName = ?";
	        try (
	            Connection con = ConnectDB.connect();
	            PreparedStatement ps = con.prepareStatement(query);
	        ) {
	            ps.setString(1, leaveTypeName);
	            try (ResultSet rs = ps.executeQuery()) {
	                if (rs.next() && rs.getInt(1) > 0) {
	                    return true;
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
	
	   public Integer countLRByIdLTVsEmp(int idUser, int idLt) {
			int count = 0;
			
			var call = "call selAllLeaveRequestByIdLT(?,?)";
			try (
					var con = new ConnectDB().connect();
					var cs = CreateCallableStmt.createCS(con, idUser, idLt, call);
					var result = cs.executeQuery();		
				){
				while (result.next()) {
					count = result.getInt("TotalUsedDays");
				}
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}

			return count;
		}

}
