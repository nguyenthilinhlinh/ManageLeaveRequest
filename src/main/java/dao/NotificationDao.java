package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import database.ConnectDB;
import entity.LeaveRequests;
import entity.Notification;

public class NotificationDao {
    
    public static CallableStatement createCS(Connection con, int employeeId) throws Exception {
        var cs = con.prepareCall("{call GetNotificationsByEmployee(?)}");
        cs.setInt(1, employeeId);
        return cs;
    }

    public List<Notification> getNotificationsByEmployee(int employeeId) {
        var notifications = new ArrayList<Notification>();
        
        try (
            var con = ConnectDB.connect();
            var cs = createCS(con, employeeId);
            var result = cs.executeQuery();
        ) {
            while (result.next()) {
                var notification = new Notification();
                notification.setNotificationID(result.getInt("NotificationID"));
                notification.setLeaveRequestID(result.getInt("LeaveRequestID"));
                notification.setNotificationDate(result.getDate("NotificationDate"));
                notification.setReceiverID(result.getInt("ReceiverID"));
                notification.setMessage(result.getString("Message"));
                notification.setStatus(result.getBoolean("Status"));
                notifications.add(notification);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return notifications;
    }
    public int insertNotification(Notification noti) { 
        try (
            var con = ConnectDB.connect();
            var cs = con.prepareCall("{call InsertNotification(?,?,?)}");
        ) {
            cs.setInt(1, noti.getLeaveRequestID());
            cs.setInt(2, noti.getReceiverID());
            cs.setString(3, noti.getMessage());

            var rowsAffected = cs.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Notification successfully inserted");
                return 1; // Trả về 1 để biểu thị rằng thông báo đã được chèn thành công
            }
            
            return -1; // Trả về -1 nếu không có hàng nào bị ảnh hưởng

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

}

