package component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import dao.NotificationDao;
import entity.Notification;

public class NotificationPanel extends JDialog {

	private static final long serialVersionUID = 3286010694999697945L;

	private NotificationDao notificationDao;
	private JButton close;
	private JScrollPane scrollPane;
	private JList<Notification> notificationJList;
	private DefaultListModel<Notification> listModel;

	private void initComponent() {

		// setPreferredSize(new Dimension(300, 200));
	}

	public NotificationPanel(int employeeId) {
		setTitle("Thông báo ");
//    	getContentPane().setSize(new Dimension(300, 200));
		this.setSize(new Dimension(400, 480));
		this.setResizable(false);

		close = new JButton("close");
		getContentPane().add(close, BorderLayout.SOUTH);

		scrollPane = new JScrollPane(notificationJList);
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		listModel = new DefaultListModel<>();
		notificationJList = new JList<>(listModel);
		scrollPane.setViewportView(notificationJList);
		// Sử dụng Custom ListCellRenderer đơn giản để hỗ trợ word wrap
		notificationJList.setCellRenderer(new MyCellRenderer(290, 11));

		initComponent();
//    	notifications = new ArrayList<>();
		notificationDao = new NotificationDao();

		try {
			List<Notification> notificationList = notificationDao.getNotificationsByEmployee(employeeId);
			for (Notification notification : notificationList) {
				listModel.addElement(notification);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static class MyCellRenderer extends DefaultListCellRenderer {
		public static final String HTML_1 = "<html><body style='width: ";
		public static final String HTML_2 = "px; overflow-wrap: break-word; ";
		public static final String HTML_3 = "</body></html>";
		private int width;
		private final Border bottomBorder;
		private final int fontSize; // Thêm thuộc tính kích thước font chữ

		public MyCellRenderer(int width, int fontSize) {
			this.width = width;
			this.fontSize = fontSize;
			this.bottomBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY);
		}

		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			// value là một đối tượng Notification, do đó cần phải ép kiểu
			Notification notification = (Notification) value;

			// Đặt kiểu chữ đậm nếu thông báo chưa đọc
			String fontStyle = notification.isStatus() ? "" : "font-weight: bold; ";
			String text = HTML_1 + width + HTML_2 + "font-size: " + fontSize + "px; " + fontStyle + "'>"
					+ notification.getMessage() + HTML_3;

			// Sử dụng phương thức của lớp cha để lấy component
			Component component = super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);

			// Đảm bảo component là JComponent để thiết lập đường viền
			if (component instanceof JComponent) {
				JComponent jComponent = (JComponent) component;
				jComponent.setBorder(bottomBorder);
			}

			// Cập nhật màu nền và chữ của mục khi được chọn
			if (isSelected) {
				component.setBackground(list.getSelectionBackground());
				component.setForeground(list.getSelectionForeground());
			} else {
				component.setBackground(list.getBackground());
				component.setForeground(list.getForeground());
			}

			return component;
		}
	}

	private void addNotification(String message) {
		JPanel notificationItem = new JPanel(new BorderLayout());
		notificationItem.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
		notificationItem.setBackground(Color.WHITE);
//        JLabel notificationLabel = new JLabel(notificationText);
//        notificationLabel.setFont(new Font("Arial", Font.PLAIN, 14));
//        notificationLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JLabel notificationLabel = new JLabel(message);
		notificationLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		getContentPane().add(notificationLabel);

		notificationItem.add(notificationLabel, BorderLayout.CENTER);
//        notificationListPanel.add(notificationItem);
//        notificationList.add(notificationLabel)
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Notifications");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Tạo notification panel
		NotificationPanel notificationPanel = new NotificationPanel(1);
		notificationPanel.setVisible(true);

		// Thêm panel vào frame
//        frame.getContentPane().add(notificationPanel);
//        frame.pack();
//        frame.setLocationRelativeTo(null);
//        frame.setVisible(true);
	}

}
