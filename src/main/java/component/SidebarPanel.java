package component;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.Objects;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

import constants.UIConstants;
import context.AuthenticationContextManager;
import context.CardController;

public class SidebarPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel contentPaneCard;
	private JButton btnStatistics;
	private JButton btnApproval;
	private JButton btnVacation;
	private JButton btnLeavetypes;
	private JButton btnLogout;
	private JButton btnProfileEmployee;
	private JButton btnUserData;
	private final transient CardController cardController;
	private final transient CardController frameCardController;

	public SidebarPanel(CardController cardController, CardController frameCardController) {
		setBackground(new Color(104, 109, 118));

		this.cardController = cardController;
		this.frameCardController = frameCardController;
		
		Color colorbutton = Color.decode("#88D66C");
		btnStatistics = new JButton("Statistics");
		btnStatistics.setFont(new Font(UIConstants.FONT_FAMILY, Font.BOLD, 16));
		btnStatistics.setMnemonic('S');
		btnStatistics.setBackground(colorbutton);
		btnStatistics.addActionListener(this::handleActionListener);
		ImageIcon iconstatistics = new ImageIcon(
				Objects.requireNonNull(getClass().getResource("/asset/image/statistics.png")));
		Image resizedImagestatistics = iconstatistics.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon resizedIconstatistics = new ImageIcon(resizedImagestatistics);
		btnStatistics.setIcon(resizedIconstatistics);

		btnApproval = new JButton("Approval");
		btnApproval.setVisible(false);
		btnApproval.setFont(new Font(UIConstants.FONT_FAMILY, Font.BOLD, 16));
		btnApproval.setMnemonic('A');
		btnApproval.setBackground(colorbutton);
		btnApproval.addActionListener(this::handleActionListener);
		ImageIcon iconcheck = new ImageIcon(Objects.requireNonNull(getClass().getResource("/asset/image/check.png")));
		Image resizedImagecheck = iconcheck.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon resizedIconcheck = new ImageIcon(resizedImagecheck);
		btnApproval.setIcon(resizedIconcheck);

		btnVacation = new JButton("Vacation");
		btnVacation.setFont(new Font(UIConstants.FONT_FAMILY, Font.BOLD, 16));
		btnVacation.setMnemonic('V');
		btnVacation.setBackground(colorbutton);
		btnVacation.addActionListener(this::handleActionListener);
		ImageIcon iconvacation = new ImageIcon(
				Objects.requireNonNull(getClass().getResource("/asset/image/vacation.png")));
		Image resizedImagevacation = iconvacation.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon resizedIconvacation = new ImageIcon(resizedImagevacation);
		btnVacation.setIcon(resizedIconvacation);

		btnLeavetypes = new JButton("LeaveTypes");
		btnLeavetypes.setVisible(false);
		btnLeavetypes.setFont(new Font(UIConstants.FONT_FAMILY, Font.BOLD, 16));
		btnLeavetypes.setMnemonic('L');
		btnLeavetypes.setBackground(colorbutton);
		btnLeavetypes.addActionListener(this::handleActionListener);
		ImageIcon iconleave = new ImageIcon(Objects.requireNonNull(getClass().getResource("/asset/image/leave.png")));
		Image resizedImageleave = iconleave.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon resizedIconleave = new ImageIcon(resizedImageleave);
		btnLeavetypes.setIcon(resizedIconleave);

		btnLogout = new JButton("Logout");
		ImageIcon iconcheckout = new ImageIcon(
				Objects.requireNonNull(getClass().getResource("/asset/image/check-out.png")));
		Image resizedImage = iconcheckout.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon resizedIcon = new ImageIcon(resizedImage);
		btnLogout.setIcon(resizedIcon);
		btnLogout.setFont(new Font(UIConstants.FONT_FAMILY, Font.BOLD, 16));
		btnLogout.setMnemonic('L');
		btnLogout.setBackground(colorbutton);
		btnLogout.addActionListener(this::handleActionListener);

		btnProfileEmployee = new JButton("Profile");
		btnProfileEmployee.setFont(new Font(UIConstants.FONT_FAMILY, Font.BOLD, 16));
		btnProfileEmployee.setMnemonic('P');
		btnProfileEmployee.setBackground(colorbutton);
		btnProfileEmployee.addActionListener(this::handleActionListener);
		ImageIcon iconprofile = new ImageIcon(
				Objects.requireNonNull(getClass().getResource("/asset/image/profile.png")));
		Image resizedImageprofile = iconprofile.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon resizedIconprofile = new ImageIcon(resizedImageprofile);
		btnProfileEmployee.setIcon(resizedIconprofile);

		btnUserData = new JButton("User Data");
		btnUserData.setMnemonic('U');
		btnUserData.setFont(new Font(UIConstants.FONT_FAMILY, Font.BOLD, 16));
		btnUserData.setBackground(new Color(136, 214, 108));
		ImageIcon iconpersonal = new ImageIcon(
				Objects.requireNonNull(getClass().getResource("/asset/image/client.png")));
		Image resizedImageper = iconpersonal.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon resizedIconper = new ImageIcon(resizedImageper);
		btnUserData.setIcon(resizedIconper);

		GroupLayout gl_penalSiledbar = new GroupLayout(this);
		gl_penalSiledbar.setHorizontalGroup(gl_penalSiledbar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_penalSiledbar.createSequentialGroup().addGroup(gl_penalSiledbar
						.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_penalSiledbar.createSequentialGroup().addContainerGap().addGroup(gl_penalSiledbar
								.createParallelGroup(Alignment.LEADING)
								.addComponent(btnVacation, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
								.addComponent(btnLogout, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 163,
										Short.MAX_VALUE)
								.addComponent(btnProfileEmployee, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
								.addComponent(btnStatistics, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
								.addComponent(btnApproval, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
								.addComponent(btnLeavetypes, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)))
						.addGroup(gl_penalSiledbar.createSequentialGroup().addContainerGap().addComponent(btnUserData,
								GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));
		gl_penalSiledbar.setVerticalGroup(gl_penalSiledbar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_penalSiledbar.createSequentialGroup().addContainerGap().addComponent(btnProfileEmployee)
						.addGap(11).addComponent(btnStatistics).addGap(11)
						.addComponent(btnApproval, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(btnLeavetypes, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(btnVacation, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
						.addGap(18).addComponent(btnUserData).addGap(266)
						.addPreferredGap(ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
						.addComponent(btnLogout, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addGap(27)));
		setLayout(gl_penalSiledbar);
	}

	private void handleActionListener(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		switch (actionCommand) {
		case "Statistics" -> showCard("Statistics", btnStatistics);
		case "Approval" -> {
			showCard("Approval", btnApproval);
		}
		case "Vacation" -> {
			showCard("Vacation", btnVacation);
		}
		case "LeaveTypes" -> showCard("LeaveTypes", btnLeavetypes);
		case "Profile" -> showCard("ProfileEmployee", btnProfileEmployee);
		case "User Data" -> showCard("ProfileEmployee", btnProfileEmployee);
		case "Logout" -> logout();
		}
	}
	
	public void updatePermissions() {
		switch (AuthenticationContextManager.getInstance().getAuthz().getRoleName()) {

		case "Admin" -> {
			btnApproval.setVisible(true);
			btnLeavetypes.setVisible(true);
		}

		case "Leader" -> {
			btnApproval.setVisible(true);
		}

		}
	}

	private void showCard(String cardName, JButton button) {
		highlightButton(button);
		cardController.showCard(cardName);
	}

	private void highlightButton(JButton button) {
		resetButtonColors();
		button.setBackground(Color.decode("#6cd685"));
	}

	private void resetButtonColors() {
		Color defaultColor = Color.decode("#88D66C");
		btnStatistics.setBackground(defaultColor);
		btnApproval.setBackground(defaultColor);
		btnVacation.setBackground(defaultColor);
		btnLeavetypes.setBackground(defaultColor);
		btnLogout.setBackground(defaultColor);
		btnProfileEmployee.setBackground(defaultColor);
	}

	private void logout() {
		resetButtonColors();

		frameCardController.showCard("Login");

		btnApproval.setVisible(false);
		btnLeavetypes.setVisible(false);
	}
}
