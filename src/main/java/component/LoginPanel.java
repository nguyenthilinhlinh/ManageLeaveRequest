package component;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.prefs.Preferences;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import constants.UIConstants;
import context.AuthenticationContextManager;
import context.MediatorCardController;
import context.MediatorColleague;
import dao.EmployeeDao;
import gui.JFrameMain;

public class LoginPanel extends JPanel implements MediatorColleague {

	private static final long serialVersionUID = 4695178691451235011L;

	private final JTextField txtEmail;
	private final JPasswordField txtPassword;
	private final JCheckBox chkRememberMe;
	private final MediatorCardController frameCardController;
	private final JLabel lblCopyright;
	private final JPanel panel;

	public LoginPanel(MediatorCardController frameCardController) {
		setBorder(null);
		this.frameCardController = frameCardController;
		var imageIcon = new ImageIcon(new ImageIcon(JFrameMain.class.getResource("/asset/image/leave-management.png"))
				.getImage().getScaledInstance(600, -1, java.awt.Image.SCALE_SMOOTH));
		setLayout(null);
		this.setBackground(new Color(122, 186, 120));

		lblCopyright = new JLabel("Copyright @ 2024");
		lblCopyright.setFont(new Font(UIConstants.FONT_FAMILY, Font.BOLD, 16));
		lblCopyright.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblCopyright);

		panel = new JPanel();
		panel.setBounds(0, 0, 1141, 517);

		add(panel);
		panel.setBorder(null);
		panel.setBackground(new Color(122, 186, 120));

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font(UIConstants.FONT_FAMILY, Font.BOLD, 18));

		txtEmail = new JTextField();
		txtEmail.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 18));
		txtEmail.setBounds(321, 127, 344, 39);
		txtEmail.setMargin(new Insets(10, 10, 10, 10));
		panel.add(txtEmail);
		txtEmail.setColumns(10);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font(UIConstants.FONT_FAMILY, Font.BOLD, 18));

		txtPassword = new JPasswordField();
		txtPassword.setBounds(321, 205, 344, 39);
		txtPassword.setMargin(new Insets(10, 10, 10, 10));
		panel.add(txtPassword);

		txtPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					handleLogin();
				}
			}
		});

		chkRememberMe = new JCheckBox("Remember Me");
		chkRememberMe.setBackground(new Color(115, 187, 163));
		chkRememberMe.setFont(new Font(UIConstants.FONT_FAMILY, Font.BOLD, 11));
		chkRememberMe.setBounds(341, 288, 129, 25);
		panel.add(chkRememberMe);

		JSeparator separator = new JSeparator();

		JButton btnLogin = new JButton("Login");
		btnLogin.setBackground(new Color(0, 255, 0));
		btnLogin.setForeground(Color.BLACK);
		btnLogin.setFont(new Font(UIConstants.FONT_FAMILY, Font.BOLD, 18));
		btnLogin.addActionListener(e -> handleLogin());

		JButton btnReset = new JButton("Reset");
		btnReset.setBackground(new Color(76, 175, 80));
		btnReset.setForeground(Color.BLACK);
		btnReset.setFont(new Font(UIConstants.FONT_FAMILY, Font.BOLD, 18));
		btnReset.addActionListener(e -> reset());

		JLabel lblLoginTitle = new JLabel("Welcome back");
		lblLoginTitle.setFont(new Font(UIConstants.FONT_FAMILY, Font.BOLD, 42));
		lblLoginTitle.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblNewLabel = new JLabel(imageIcon);
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup().addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup().addContainerGap()
								.addComponent(lblLoginTitle, GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE).addGap(46))
						.addGroup(gl_panel.createSequentialGroup().addGroup(gl_panel
								.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
										.createSequentialGroup().addGap(11)
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 112,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(lblEmail, GroupLayout.PREFERRED_SIZE, 88,
														GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
												.addComponent(txtPassword)
												.addComponent(separator, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE,
														412, GroupLayout.PREFERRED_SIZE)
												.addComponent(txtEmail, GroupLayout.DEFAULT_SIZE, 421, Short.MAX_VALUE))
										.addPreferredGap(ComponentPlacement.RELATED)).addGroup(
												gl_panel.createSequentialGroup().addGap(135).addComponent(chkRememberMe,
														GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_panel.createSequentialGroup().addContainerGap()
										.addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 128,
												GroupLayout.PREFERRED_SIZE)
										.addGap(59)
										.addComponent(btnReset, GroupLayout.PREFERRED_SIZE, 128,
												GroupLayout.PREFERRED_SIZE)
										.addGap(72)))
								.addGap(34)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 524, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup().addGap(20)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup()
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE).addContainerGap())
						.addGroup(gl_panel.createSequentialGroup()
								.addComponent(lblLoginTitle, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
								.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_panel.createSequentialGroup()
												.addComponent(txtEmail, GroupLayout.PREFERRED_SIZE, 60,
														GroupLayout.PREFERRED_SIZE)
												.addGap(18))
										.addGroup(gl_panel.createSequentialGroup()
												.addComponent(lblEmail, GroupLayout.PREFERRED_SIZE, 35,
														GroupLayout.PREFERRED_SIZE)
												.addGap(30)))
								.addGroup(
										gl_panel.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_panel.createSequentialGroup()
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(txtPassword, GroupLayout.PREFERRED_SIZE, 39,
																GroupLayout.PREFERRED_SIZE)
														.addGap(28)
														.addComponent(separator, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addGap(18)
														.addComponent(chkRememberMe, GroupLayout.PREFERRED_SIZE, 25,
																GroupLayout.PREFERRED_SIZE)
														.addGap(18)
														.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
																.addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 35,
																		GroupLayout.PREFERRED_SIZE)
																.addComponent(btnReset, GroupLayout.PREFERRED_SIZE, 35,
																		GroupLayout.PREFERRED_SIZE)))
												.addGroup(gl_panel.createSequentialGroup().addGap(13).addComponent(
														lblPassword, GroupLayout.PREFERRED_SIZE, 35,
														GroupLayout.PREFERRED_SIZE)))
								.addGap(91)))));
		gl_panel.linkSize(SwingConstants.VERTICAL, new Component[] { txtEmail, txtPassword, btnLogin, btnReset });
		panel.setLayout(gl_panel);
		this.loadSavedLoginInfo();
	}

	public void handleLogin() {
		String email = txtEmail.getText();
		String password = new String(txtPassword.getPassword());

		StringBuilder sb = new StringBuilder();

		// validate

		if (email.equals("")) {
			sb.append("Email cannot be blank!\n");
		} else if (!isValidEmail(email)) {
			sb.append("Invalid email format! domains are allowed.\n");
		}
		if (password.equals("")) {
			sb.append("Password cannot be blank!");
		}

		if (sb.length() > 0) {
			JOptionPane.showMessageDialog(this, sb.toString(), "Invalidation", JOptionPane.ERROR_MESSAGE);
			return;
		}

		var dao = new EmployeeDao();
		var emp = dao.checkLogin(email, password);
		if (emp != null) {
			var role = dao.selectRole(emp.getEmployeeID());

			// store authentication to authmanager
			AuthenticationContextManager.getInstance().setAuthn(emp).setAuthz(role);

			// Save login details if "Remember Me" is selected
			Preferences prefs = Preferences.userNodeForPackage(JFrameMain.class);
			prefs.put("email", email);
			if (chkRememberMe.isSelected()) {
				prefs.put("password", password);
			} else {
				prefs.remove("password");
			}

			frameCardController.showCard("Main");

		} else {
			JOptionPane.showMessageDialog(null, "The account does not exist, please re-enter");
			reset();
		}
	}

	private void logout() {
		this.loadSavedLoginInfo();
		AuthenticationContextManager.getInstance().clearContext();
	}

	private boolean isValidEmail(String email) {
		// Regex pattern to validate email addresses ending with .com
		String emailRegex = "^[a-zA-Z0-9._%+-]+@(gmail\\.com|outlook\\.com|icloud\\.com|zoho\\.com|yahoo\\.com|aol\\.com|gmx\\.com|example\\.com)$";
		return email.matches(emailRegex);
	}

	private void loadSavedLoginInfo() {
		Preferences prefs = Preferences.userNodeForPackage(JFrameMain.class);
		String savedEmail = prefs.get("email", "");
		String savedPassword = prefs.get("password", "");

		txtEmail.setText(savedEmail);
		txtPassword.setText(savedPassword);

		if (!savedEmail.isEmpty() && !savedPassword.isEmpty()) {
			chkRememberMe.setSelected(true);
		}
	}

	private void reset() {
		txtEmail.setText("");
		txtPassword.setText("");
		chkRememberMe.setSelected(false);

		Preferences prefs = Preferences.userNodeForPackage(JFrameMain.class);
		prefs.remove("email");
		prefs.remove("password");
	}

	public void setFrameSize(int width, int height) {

		// Calculate the center position
		int panelWidth = panel.getWidth();
		int panelHeight = panel.getHeight();

		int x = (width - panelWidth) / 2;
		int y = (height - panelHeight) / 2;

		// Set the JPanel bounds to center it
		panel.setBounds(x, y, panelWidth, panelHeight);

		lblCopyright.setBounds(0, height - 100, width, 30);
	}

	@Override
	public void onNotify() {
		logout();
	}
}
