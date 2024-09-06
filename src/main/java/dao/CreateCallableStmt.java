package dao;

import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;

public class CreateCallableStmt {
//	Employees
	public static CallableStatement createCS(Connection con, int idEmp, String call) throws Exception {
		var cs = con.prepareCall("{" +call +"}");
		cs.setInt(1, idEmp);  
		return cs;
    }
	
	public static CallableStatement createCS(Connection con,java.util.Date date , String call) throws Exception {
		var cs = con.prepareCall("{" +call +"}");
		cs.setDate(1, new java.sql.Date(date.getTime()));
		return cs;
	}
	
	public static CallableStatement createCS(Connection con, int idEmp) throws Exception {
		var cs = con.prepareCall("{call selectEmpID(?)}");
		cs.setInt(1, idEmp);
		return cs;
	}
	
	public static CallableStatement createCS(Connection con, String str , String call) throws Exception {
		var cs = con.prepareCall("{" +call +"}");
		cs.setString(1, str);
		return cs;
	}
	
//	public static CallableStatement createCS(Connection con, String str1, String Str2, String call) throws Exception {
//		
//		var cs = con.prepareCall("{call countLR(?)}");
//		cs.setString(1, str1);
//		cs.setString(2, Str2);
//		return cs;
//	}
	
	public static CallableStatement createCS(Connection con, int id, String str , String call) throws Exception {
		var cs = con.prepareCall("{" +call +"}");
		cs.setInt(1, id);
		cs.setString(2, str);
		return cs;
	}
	
	
//	HistoryRequest
	//	Call Phân trang LH
	public static CallableStatement createCS(Connection con, int pageNumber, int rowPage,int idUser,String call) throws Exception {
		var cs = con.prepareCall("{" + call +"}");
		cs.setInt(1, idUser);
		cs.setInt(2, pageNumber);
		cs.setInt(3, rowPage);
		return cs;
	}
	
// checkLogin
	public static CallableStatement createCS(Connection con, String Email, String password, String call) throws Exception {
		var cs = con.prepareCall("{" + call +"}");
		cs.setString(1, Email);
		cs.setString(2, password);
		return cs;
	}

	public static CallableStatement createCS(Connection con, int idEmp, int idLt, String call) throws Exception {
		var cs = con.prepareCall("{" +call +"}");
		cs.setInt(1, idEmp);
		cs.setInt(2, idLt);
		return cs;
	}
	
}
