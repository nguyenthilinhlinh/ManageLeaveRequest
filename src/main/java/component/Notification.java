package component;

import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.*;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class Notification extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private DefaultListModel<String> notificationModel;
	private JList<String> notificationList;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Notification dialog = new Notification();
			dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Notification() {
		setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        
        
		
		
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		contentPanel.setLayout(new BorderLayout(0, 0));
		 JLabel headerLabel = new JLabel("Thông báo", JLabel.LEFT);
	        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
	        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	        
	        notificationModel = new DefaultListModel<>();
	        notificationList = new JList<>(notificationModel);
	        notificationList.setCellRenderer(new NotificationCellRenderer());

	        JScrollPane scrollPane = new JScrollPane(notificationList);
	        scrollPane.setBorder(BorderFactory.createEmptyBorder());

	        add(headerLabel, BorderLayout.NORTH);
	        add(scrollPane, BorderLayout.CENTER);
	    }

	    // Thêm thông báo vào danh sách
	    public void addNotification(String notification) {
	        notificationModel.addElement(notification);
	    }
	    
	    // Renderer tùy chỉnh cho các mục thông báo
	    private static class NotificationCellRenderer extends JLabel implements ListCellRenderer<String> {

	        private static final long serialVersionUID = 1L;

	        @Override
	        public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
	                boolean isSelected, boolean cellHasFocus) {
	            setText(value);
	            setFont(new Font("Arial", Font.PLAIN, 14));
	            setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	            if (isSelected) {
	                setBackground(new Color(204, 229, 255));  // Màu nền khi được chọn
	            } else {
	                setBackground(Color.WHITE);
	            }
	            setOpaque(true);
	            return this;
	        }
	    
	
}
}
