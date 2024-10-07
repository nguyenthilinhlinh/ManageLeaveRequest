package component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import constants.UIConstants;
import context.AuthenticationContextManager;
import gui.JFrameMain;

public class NavbarPanel extends JPanel {

	private static final long serialVersionUID = 1222195228216508966L;

	private final JLabel lblNameEmployeeLogin;
	private final JLabel lblbell;
	private final JLabel lblUser;

	private NotificationPanel notificationPanel;
	private JPanel panel;

	public NavbarPanel() {
		setPreferredSize(new Dimension(908, 60));
		
		ImageIcon iconbell = new ImageIcon(
				Objects.requireNonNull(JFrameMain.class.getResource("/asset/image/bell.png")));
		Image resizedImage = iconbell.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon resizedIcon = new ImageIcon(resizedImage);
		// Create a JLabel bell
		lblbell = new JLabel();
		lblbell.setBounds(846, 0, 50, 60);
		lblbell.setIcon(resizedIcon);

		lblbell.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				notificationPanel.setVisible(true);
			}
		});
		lblbell.setBackground(new Color(255, 255, 0));
		ImageIcon iconuser = new ImageIcon(
				Objects.requireNonNull(JFrameMain.class.getResource("/asset/image/user.png")));
		Image resizedImageuser = iconuser.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon resizedIconuser = new ImageIcon(resizedImageuser);
		setLayout(null);
		add(lblbell);

		panel = new JPanel();
		panel.setBounds(0, 0, 379, 60);
		add(panel);

		lblUser = new JLabel();
		lblUser.setBackground(Color.YELLOW);
		lblUser.setIcon(resizedIconuser);

		lblNameEmployeeLogin = new JLabel("");
		lblNameEmployeeLogin.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 19));
		lblNameEmployeeLogin.setHorizontalAlignment(SwingConstants.LEFT);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblUser)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNameEmployeeLogin, GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblNameEmployeeLogin, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
							.addGap(10))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblUser, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(11))))
		);
		panel.setLayout(gl_panel);
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				lblbell.setBounds(getSize().width - lblbell.getWidth() - 10, 0, 50, 60);
			}
		});
	}

	public void updatePermissions() {
		var emp = AuthenticationContextManager.getInstance().getAuthn();
		var role = AuthenticationContextManager.getInstance().getAuthz();
		lblNameEmployeeLogin.setText("Welcome, " + emp.getEmployeeName() + " (" + role.getRoleName() + ")");
		notificationPanel = new NotificationPanel(emp.getEmployeeID());
	}
}
