package gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import component.LoginPanel;
import component.MainPanel;
import component.NotificationPanel;
import constants.UIConstants;

public class JFrameMain extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPaneCard;
	private LoginPanel panelLogin;
	private JPanel panelMain;
	private JButton btnStatistics;
	private JButton btnApproval;
	private JButton btnVacation;
	private JPanel panelNarbar;
	private JLabel lblNameEmployeeLogin;
	private JButton btnLeavetypes;
	private JLabel lblbell;
	private JButton btnLogout;
	private JButton btnProfileEmployee;
	private NotificationPanel notificationPanel;
	
	private JLabel lblUser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(() -> {
			try {
				JFrameMain frame = new JFrameMain();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JFrameMain() {
		setBackground(new Color(243, 202, 82));
//		setResizable(false);
		setTitle("Leave Management Application");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;

		// Tính toán kích thước hợp lý, có thể là 70% chiều rộng và 70% chiều cao của
		// màn hình
		int frameWidth = (int) (screenWidth * 0.8);
		int frameHeight = (int) (screenHeight * 0.8);

		// Thiết lập kích thước và vị trí cho JFrame
		setBounds((screenWidth - frameWidth) / 2, (screenHeight - frameHeight) / 2, frameWidth, frameHeight);

		contentPaneCard = new JPanel();
		contentPaneCard.setBackground(new Color(243, 202, 82));
		contentPaneCard.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPaneCard.setPreferredSize(new Dimension(800, 600));
		setContentPane(contentPaneCard);
		contentPaneCard.setLayout(new CardLayout(0, 0));
		initLoginPanel();
		initMainPanel();
		initNavbar();
		ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/asset/image/a.png")));
		setIconImage(icon.getImage());
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				panelLogin.setFrameSize(e.getComponent().getWidth(), e.getComponent().getHeight());
			}
		});
	}

	private void initLoginPanel() {
		panelLogin = new LoginPanel(contentPaneCard, "Main");
		contentPaneCard.add(panelLogin, "Login");
	}

	private void initMainPanel() {
		panelMain = new MainPanel();
		contentPaneCard.add(panelMain, "Main");


		panelNarbar = new JPanel();
		panelMain.add(panelNarbar, BorderLayout.NORTH);
		panelNarbar.setPreferredSize(new Dimension(300, 60));
		panelNarbar.setMinimumSize(new Dimension(300, 60)); // Kích thước nhỏ nhất
		panelNarbar.setMaximumSize(new Dimension(300, 60)); // Kích thước lớn nhất
	}

	private void initNavbar() {
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
		lblUser.setBackground(Color.YELLOW);
		ImageIcon iconuser = new ImageIcon(
				Objects.requireNonNull(JFrameMain.class.getResource("/asset/image/user.png")));
		Image resizedImageuser = iconuser.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon resizedIconuser = new ImageIcon(resizedImageuser);
		lblUser.setIcon(resizedIconuser);

		GroupLayout gl_panelNarbar = new GroupLayout(panelNarbar);
		gl_panelNarbar.setHorizontalGroup(gl_panelNarbar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelNarbar.createSequentialGroup().addGap(60)
						.addComponent(lblUser, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblNameEmployeeLogin, GroupLayout.PREFERRED_SIZE, 471, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 486, Short.MAX_VALUE)
						.addComponent(lblbell, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
						.addGap(389)));
		gl_panelNarbar.setVerticalGroup(gl_panelNarbar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelNarbar.createSequentialGroup()
						.addComponent(lblbell, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE).addGap(20))
				.addGroup(gl_panelNarbar.createSequentialGroup().addContainerGap()
						.addComponent(lblNameEmployeeLogin, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
						.addContainerGap())
				.addGroup(gl_panelNarbar.createSequentialGroup().addGap(21)
						.addComponent(lblUser, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(29, Short.MAX_VALUE)));
		panelNarbar.setLayout(gl_panelNarbar);
	}

//	private void initSubPanels() {
//		panelStatistics = new JPanel();
//		panelStatistics.setBackground(new Color(243, 202, 82));
//		panelSubCard.add(panelStatistics, "Statistics");
//		panelStatistics.setLayout(null);
//
//		static1 = new Static();
//		static1.setBounds(10, 11, 1043, 649);
//		panelStatistics.add(static1);
//
//		panelApproval = new JPanel();
//		panelApproval.setBackground(new Color(243, 202, 82));
//		panelSubCard.add(panelApproval, "Approval");
//		panelApproval.setLayout(null);
//
//		tabbedPaneApproval = new JTabbedPane(JTabbedPane.TOP);
//		tabbedPaneApproval.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 16));
//		tabbedPaneApproval.setBounds(32, 11, 1001, 625);
//		panelApproval.add(tabbedPaneApproval);
//
//		panelProcessed = new JPanel();
//		panelProcessed.setBackground(new Color(191, 246, 195));
//		tabbedPaneApproval.addTab("Processed", null, panelProcessed, null);
//		panelProcessed.setLayout(null);
//
//		panelPending = new JPanel();
//		panelPending.setBackground(new Color(191, 246, 195));
//		tabbedPaneApproval.addTab("Pending Approval", null, panelPending, null);
//		panelPending.setLayout(new CardLayout(0, 0));
//
//		tabbedPaneApproval.addChangeListener(e -> {
//			int selectedIndex = tabbedPaneApproval.getSelectedIndex();
//			if (selectedIndex == 0) {
//				loadProcesedData();
//			} else if (selectedIndex == 1) {
//				loadPendingData();
//			}
//		});
//
//		panelVacation = new JPanel();
//		panelVacation.setBackground(new Color(243, 202, 82));
//		panelSubCard.add(panelVacation, "Vacation");
//		panelVacation.setLayout(null);
//
//		panelVactionRequest = new JPanel();
//		panelVactionRequest.setLayout(null);
//		panelVactionRequest.setBackground(new Color(191, 246, 195));
//		panelVactionRequest.setBounds(0, 0, 1055, 671);
//		panelVacation.add(panelVactionRequest);
//
//		panelLeaveTypes = new JPanel();
//		panelLeaveTypes.setBackground(new Color(243, 202, 82));
//		panelSubCard.add(panelLeaveTypes, "LeaveTypes");
//		panelLeaveTypes.setLayout(null);
//
//		leaveTypes = new LeaveTypes();
//		leaveTypes.setBounds(10, 11, 1043, 649);
//		panelLeaveTypes.add(leaveTypes);
//
//		panelProfileEmployee = new JPanel();
//		panelProfileEmployee.setBackground(new Color(243, 202, 82));
//		panelSubCard.add(panelProfileEmployee, "ProfileEmployee");
//		panelProfileEmployee.setLayout(null);
//	}

//	private void checkEventButton(ActionEvent e) {
//		String actionCommand = e.getActionCommand();
//		switch (actionCommand) {
//		case "Statistics" -> showCard("Statistics", btnStatistics);
//		case "Approval" -> {
//			showCard("Approval", btnApproval);
//			loadProcesedData();
//			loadPendingData();
//		}
//		case "Vacation" -> {
//			showCard("Vacation", btnVacation);
//			loadVacationRequestsData();
//		}
//		case "LeaveTypes" -> showCard("LeaveTypes", btnLeavetypes);
//		case "ProfileEmployee" -> showCard("ProfileEmployee", btnProfileEmployee);
//		case "Logout" -> logout();
////		case "Login" -> checkLogin();
////		case "Reset" -> reset();
//		}
//	}

//	private void showCard(String cardName, JButton button) {
//		highlightButton(button);
//		CardLayout cardLayout = (CardLayout) panelSubCard.getLayout();
//		cardLayout.show(panelSubCard, cardName);
//	}
//
//	private void highlightButton(JButton button) {
//		resetButtonColors();
//		button.setBackground(Color.decode("#6cd685"));
//	}

	private void resetButtonColors() {
		Color defaultColor = Color.decode("#88D66C");
		btnStatistics.setBackground(defaultColor);
		btnApproval.setBackground(defaultColor);
		btnVacation.setBackground(defaultColor);
		btnLeavetypes.setBackground(defaultColor);
		btnLogout.setBackground(defaultColor);
		btnProfileEmployee.setBackground(defaultColor);
	}

}
