package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import database.ConnectDB;
import entity.LeaveHistory;
import entity.LeaveRequests;
//import subFrame.HistoryRequest;
import entity.Role;

public class LeaveRequestDao {


	public List<LeaveRequests> getLeaveRequests(int id) {
		var call = "call selectLeaveRequest(?)";
		List<LeaveRequests> list = new ArrayList<>();
		try (

				var conection = ConnectDB.connect();
				var cs = CreateCallableStmt.createCS(conection, id, call);
				var result = cs.executeQuery();) {

			while (result.next()) {
				var lr = new LeaveRequests();
				lr.setLeaveRequestId(result.getInt("LeaveRequestID"));
				lr.setEmployeeId(result.getInt("EmployeeID"));
				lr.setLeaveTypeId(result.getInt("LeaveTypeID"));
				lr.setStartDate(result.getDate("StartDate"));
				lr.setEndDate(result.getDate("EndDate"));
				lr.setReason(result.getString("Reason"));
				lr.setStatusLR(result.getString("StatusLR"));
				lr.setStatus(result.getBoolean("Status"));
				lr.setApproverId(result.getInt("ApproverID"));
				lr.setSubmissionDate(result.getDate("SubmissionDate"));
//				System.out.println("Employee Name: " + result.getString("Name"));
				list.add(lr);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}
	public List<LeaveRequests> getLeaveRequestForAdmin(int id, Role r) {
		
		var call = "Admin".equals(r.getRoleName()) 
                ? "call selLRequestForAdmin(?)" 
                : "call selLRequestForLeader(?)";
		List<LeaveRequests> list = new ArrayList<>();
		try (

				var conection = ConnectDB.connect();
				var cs = CreateCallableStmt.createCS(conection, id, call);
				var result = cs.executeQuery();) {

			while (result.next()) {
				var lr = new LeaveRequests();
				lr.setLeaveRequestId(result.getInt("LeaveRequestID"));
				lr.setEmployeeId(result.getInt("EmployeeID"));
				lr.setLeaveTypeId(result.getInt("LeaveTypeID"));
				lr.setStartDate(result.getDate("StartDate"));
				lr.setEndDate(result.getDate("EndDate"));
				lr.setReason(result.getString("Reason"));
				lr.setStatusLR(result.getString("StatusLR"));
				lr.setStatus(result.getBoolean("Status"));
				lr.setApproverId(result.getInt("ApproverID"));
				lr.setSubmissionDate(result.getDate("SubmissionDate"));
//				System.out.println("Employee Name: " + result.getString("Name"));
				list.add(lr);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}
	

	public void UpdateStatusLR(String status, int idHR, int id) {
		try (

				var conection = ConnectDB.connect();
				var cs = conection.prepareCall("{call updateStatusLR(?,?,?)}");) {
			cs.setString(1, status);
			cs.setInt(2, id);
			cs.setInt(3, idHR);
			var result = cs.executeUpdate();
			JOptionPane.showMessageDialog(null, "Application Approved");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public Boolean UpdateLeaveRequestById(LeaveRequests lr) {
		try (

				var conection = ConnectDB.connect();
				var cs = conection.prepareCall("{call updateLeaveRequest(?,?,?,?,?)}");
			) {
			
			
			cs.setInt(1, lr.getLeaveTypeId());
			cs.setDate(2, new java.sql.Date(lr.getStartDate().getTime()));
			cs.setDate(3, new java.sql.Date(lr.getEndDate().getTime()));
			cs.setString(4, lr.getReason());
			cs.setInt(5, lr.getLeaveRequestId());
			var result = cs.executeUpdate();
			JOptionPane.showMessageDialog(null, "Updated successfully");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		
	}
	

	public Integer countLRForLeader(int idUser, Role r) {
		int count = 0;
		
		var call = r.getRoleName().equals("Admin") ? "call countLRforAdmin(?)" : "call countLRforLeader(?)";
		try (
				var con = new ConnectDB().connect();
				var cs = CreateCallableStmt.createCS(con, idUser, call);
				var result = cs.executeQuery();		
			){
			while (result.next()) {
				count = result.getInt("total");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

		return count;
	}

	public List<LeaveRequests> selLRequestForLeader( int idUser, Role r) {
		var call = r.getRoleName().equals("Admin") ? "call selLRequestForAdmin(?,?,?)" : "call selLRequestForLeader(?,?,?)";
			
		
//		var call = "call selLRequestForLeader(?,?,?)";
		List<LeaveRequests> list = new ArrayList<>();
		try (var con = new ConnectDB().connect();
				var cs = CreateCallableStmt.createCS(con,  idUser, call);
				var result = cs.executeQuery();) {
			while (result.next()) {
				LeaveRequests leaveRequest = new LeaveRequests();
				var lr = new LeaveRequests();
				lr.setLeaveRequestId(result.getInt("LeaveRequestID"));
				lr.setEmployeeId(result.getInt("EmployeeID"));
				lr.setLeaveTypeId(result.getInt("LeaveTypeID"));
				lr.setStartDate(result.getDate("StartDate"));
				lr.setEndDate(result.getDate("EndDate"));
				lr.setReason(result.getString("Reason"));
				lr.setStatusLR(result.getString("StatusLR"));
				lr.setStatus(result.getBoolean("Status"));
				lr.setApproverId(result.getInt("ApproverID"));
				lr.setSubmissionDate(result.getDate("SubmissionDate"));

				list.add(lr);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;

	}
	
	public List<LeaveRequests> selAllLeave(String str) {
		var call = "";
		switch (str) {
		case "day" -> call = "call selEmpDay";
		
		case "week" -> call = "call selEmpWeek";
		
		case "month" -> call = "call selEmpMonth";
		}
		List<LeaveRequests> list = new ArrayList<>();
		try (	
				var conection = ConnectDB.connect();
				var cs = conection.prepareCall("{" + call +"}");
				var result = cs.executeQuery();
		) {
			while (result.next()) {
				LeaveRequests leaveRequest = new LeaveRequests();
				var lr = new LeaveRequests();
				lr.setEmployeeId(result.getInt("EmployeeID"));
				lr.setLeaveTypeId(result.getInt("LeaveTypeID"));
				lr.setStartDate(result.getDate("StartDate"));
				lr.setEndDate(result.getDate("EndDate"));
				lr.setReason(result.getString("Reason"));
			
				list.add(lr);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return list;
	}
	
	public Integer countAllLeave(String str) {
		var call = "";
		switch (str) {
		case "day" -> call = "call countEmpDay";
		
		case "week" -> call = "call countEmpWeek";
		
		case "month" -> call = "call countEmpMonth";
		
		}
		int count = 0;
		try (	
				var conection = ConnectDB.connect();
				var cs = conection.prepareCall("{" + call + "}");
				var result = cs.executeQuery();
				
		) {
			while (result.next()) {
				count = result.getInt("total");
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return count;
	}
	
	public List<LeaveRequests> selAllLeaveByDate(Date date) {
		List<LeaveRequests> list = new ArrayList<>();
		var call = "call GetEmployeesOnLeaveByDate(?)";
		try (	
				var conection = ConnectDB.connect();
				var cs = CreateCallableStmt.createCS(conection, date, call);
				var result = cs.executeQuery();
		) {
			while (result.next()) {
				LeaveRequests leaveRequest = new LeaveRequests();
				var lr = new LeaveRequests();
				lr.setEmployeeId(result.getInt("EmployeeID"));
				lr.setLeaveTypeId(result.getInt("LeaveTypeID"));
				lr.setStartDate(result.getDate("StartDate"));
				lr.setEndDate(result.getDate("EndDate"));
				lr.setReason(result.getString("Reason"));
			
				list.add(lr);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return list;
	}
	
	public int insertLR(LeaveRequests lr, String statusLR) { 
		try (
				var con = ConnectDB.connect();
				var cs = con.prepareCall("{call InsertLeaveRequest(?,?,?,?,?,?)}");
			){
			cs.setInt(1, lr.getEmployeeId());
			cs.setInt(2, lr.getLeaveTypeId());
			cs.setDate(3, new java.sql.Date(lr.getStartDate().getTime()));
			cs.setDate(4, new java.sql.Date(lr.getEndDate().getTime()));
			cs.setString(5, lr.getReason());
			cs.setString(6, statusLR);
			var rowsAffected = cs.executeUpdate();
			
			
			
			 if (rowsAffected > 0) {
	                try (ResultSet generatedKeys = cs.getGeneratedKeys()) {
	                    if (generatedKeys.next()) {
	                    	JOptionPane.showMessageDialog(null, "Submit a successful application");
	                        return generatedKeys.getInt(1); // Trả về ID của yêu cầu nghỉ phép mới
	                    }
	                }
	            }
	        
			
			 return -1;
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		
		
	}
	
	public List<LeaveRequests> getAllLeaveRequestNextDay(int idUser) {
		List<LeaveRequests> list = new ArrayList<>();
		var call = "call selAllLeaveRequestNextDay(?)";
		try (	
				var conection = ConnectDB.connect();
				var cs = CreateCallableStmt.createCS(conection, idUser, call);
				var result = cs.executeQuery();
		) {
			while (result.next()) {
				LeaveRequests leaveRequest = new LeaveRequests();
				var lr = new LeaveRequests();
				lr.setStartDate(result.getDate("StartDate"));
				lr.setEndDate(result.getDate("EndDate"));
				list.add(lr);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return list;
	}
	
	


	
	public List<LeaveRequests> selLHistoryForLeader(int pageNumber, int rowPage, int idUser, Role r) {
		var call = r.getRoleName().equals("Admin") ? "call selLHistoryForAdmin(?,?,?)" : "call selLHistoryForLeader(?,?,?)";
		
			List<LeaveRequests> list = new ArrayList<>();
			try
			(
				var con = new ConnectDB().connect();
				var cs = CreateCallableStmt.createCS(con, pageNumber, rowPage, idUser, call);
				var result = cs.executeQuery();
			) {
				while(result.next()) {
					var lh = new LeaveRequests();
					lh.setLeaveRequestId(result.getInt("LeaveRequestID"));
					lh.setEmployeeId(result.getInt("EmployeeID"));
					lh.setLeaveTypeId(result.getInt("LeaveTypeID"));
					lh.setStatusLR(result.getString("StatusLR"));
					lh.setStartDate(result.getDate("StartDate"));
					lh.setEndDate(result.getDate("EndDate"));
					lh.setSubmissionDate(result.getDate("SubmissionDate"));
					lh.setApproverId(result.getInt("ApproverID"));
					lh.setApprovalDate(result.getDate("ApprovalDate"));
					lh.setReason(result.getString("Reason"));
					lh.setStatus(result.getBoolean("Status"));
					
					list.add(lh);
				}
		
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return list;
		
	}
	
public Integer countLHForLeader(int idUser,  Role r) {
		
		var call =  r.getRoleName().equals("Admin") ? "call countLHforAdmin(?)" : "call countLHforLeader(?)";
		int count = 0;
		
		
		try (
				var con = new ConnectDB().connect();
				var cs = CreateCallableStmt.createCS(con, idUser, call);
				var result = cs.executeQuery();
		){
			while(result.next()){
				count = result.getInt("total");
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		return count;
	}

}
