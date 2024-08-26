package dao;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import database.ConnectDB;
import entity.LeaveDocument;
import entity.LeaveType;

public class LeaveDocumentDao {
	

	public void insertLeaveDocument(int idLr, String file) {
        String call = "call InsertLeaveDocument(?, ?)";
        try (
        	var con = ConnectDB.connect();
             var cs = con.prepareCall("{" +call+ "}");
        	
            ) {
            
            // Set parameters
           
            cs.setInt(1, idLr);
            cs.setString(2, file);
           

            // Execute the stored procedure
            int result = cs.executeUpdate();
            
            // Return true if one or more rows were affected
            JOptionPane.showMessageDialog(null, "Thành công");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }
	
	public void updateLeaveDocument(int idLr, String file) {
        String call = "call updateLeaveDocument(?, ?)";
        try (
        	var con = ConnectDB.connect();
             var cs = con.prepareCall("{" +call+ "}");
        	
            ) {
            
            // Set parameters
           cs.setString(1, file);
            cs.setInt(2, idLr);
            
           

            // Execute the stored procedure
            cs.executeUpdate();
            
            // Return true if one or more rows were affected
            JOptionPane.showMessageDialog(null, "Updated successfully leave doc");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return;
    }

	public LeaveDocument selectLeaveDocument(int idLR) {
		var ld = new LeaveDocument();
		var call = "call selLeaveDocument(?)";
		try (
				var con = ConnectDB.connect();
				var cs = CreateCallableStmt.createCS(con, idLR, call);
				var result = cs.executeQuery();
		){
			
			while (result.next()) {		
				ld.setDocumentId(result.getInt("DocumentID"));
				ld.setDocumentPath(result.getString("DocumentPath"));
				ld.setLeaveRequestID(result.getInt("LeaveRequestID"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return ld;
	}
}
