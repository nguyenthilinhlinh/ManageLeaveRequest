package component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
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
	
	public NavbarPanel() {
		setPreferredSize(new Dimension(908, 60));
		
		lblNameEmployeeLogin = new JLabel("");
		lblNameEmployeeLogin.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 19));
		lblNameEmployeeLogin.setHorizontalAlignment(SwingConstants.LEFT);
		ImageIcon iconbell = new ImageIcon(
				Objects.requireNonNull(JFrameMain.class.getResource("/asset/image/bell.png")));
		Image resizedImage = iconbell.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
		ImageIcon resizedIcon = new ImageIcon(resizedImage);
		// Create a JLabel bell
		lblbell = new JLabel();
		lblbell.setIcon(resizedIcon);

		lblbell.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				notificationPanel.setVisible(true);
			}
		});
		lblbell.setBackground(new Color(255, 255, 0));

		lblUser = new JLabel();
		lblUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblUser.setBackground(Color.YELLOW);
		ImageIcon iconuser = new ImageIcon(
				Objects.requireNonNull(JFrameMain.class.getResource("/asset/image/user.png")));
		Image resizedImageuser = iconuser.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon resizedIconuser = new ImageIcon(resizedImageuser);
		lblUser.setIcon(resizedIconuser);

		GroupLayout gl_panelNarbar = new GroupLayout(this);
		gl_panelNarbar.setHorizontalGroup(
			gl_panelNarbar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelNarbar.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblUser, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblNameEmployeeLogin, GroupLayout.PREFERRED_SIZE, 471, GroupLayout.PREFERRED_SIZE)
					.addGap(93)
					.addComponent(lblbell, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(201, Short.MAX_VALUE))
		);
		gl_panelNarbar.setVerticalGroup(
			gl_panelNarbar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelNarbar.createSequentialGroup()
					.addGap(5)
					.addComponent(lblbell, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(15))
				.addGroup(gl_panelNarbar.createSequentialGroup()
					.addGap(4)
					.addGroup(gl_panelNarbar.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNameEmployeeLogin, GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
						.addComponent(lblUser, GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE))
					.addGap(13))
		);
		setLayout(gl_panelNarbar);
	}

	public void updatePermissions() {
		var emp = AuthenticationContextManager.getInstance().getAuthn();
		var role = AuthenticationContextManager.getInstance().getAuthz();
		lblNameEmployeeLogin.setText("Welcome, " + emp.getEmployeeName() + " (" + role.getRoleName() + ")");
		notificationPanel = new NotificationPanel(emp.getEmployeeID());
	}
}
