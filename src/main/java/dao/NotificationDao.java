package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import database.ConnectDB;
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
}
