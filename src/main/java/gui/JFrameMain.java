package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.awt.*;
import javax.swing.GroupLayout.Alignment;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.Objects;
import java.util.prefs.Preferences;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JCalendar;

import dao.EmployeeDao;
import entity.Employees;
import entity.Role;
import component.Processed;
import component.PendingApproval;
import component.AddLeaveRequest;
import component.VacationRequest;
import component.LeaveTypes;
import component.NotificationPanel;
import component.ProfileEmployee;
import component.Static;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;

public class JFrameMain extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPaneCard;
	private JPanel panelLogin;
	private JPanel panelMain;
	private JPanel penalSiledbar;
	private JButton btnStatistics;
	private JButton btnApproval;
	private JButton btnVacation;
	private JPanel panelNarbar;
	private JPanel panelSubCard;
	private JPanel panelStatistics;
	private JPanel panelApproval;
	private JPanel panelVacation;
	private JTabbedPane tabbedPaneApproval;
	private JPanel panelPending;
	private JPanel panelProcessed;
	private JTextField txtEmail;
	private JPasswordField txtPassword;
	private JLabel lblNameEmployeeLogin;
	private JCheckBox chkRememberMe;
	private Employees emp = null;
	private Processed processed;
	private PendingApproval pendingApproval;
	private Integer pageNumber = 1; // Trang thu may
	private Integer rowOfPage = 10; // So dong mac dinh trong trang
	private Integer totalRow = 0; // so dong trong database
	private Double totalPage = 1.0; // Tong so trang
	private VacationRequest vacationRequest;
	private JButton btnLeavetypes;
	private JPanel panelLeaveTypes;
	private LeaveTypes leaveTypes;
	private JLabel lblbell;
	private JButton btnLogout;
	private JButton btnProfileEmployee;
	private JPanel panelProfileEmployee;
	private ProfileEmployee profileEmployee;
	private AddLeaveRequest addLeaveRequest;
	private Role role;
	private Static static1;
	private JPanel panelVactionRequest;
	private NotificationPanel notificationPanel;
	private JLabel lblBellIcon;
	private JLabel lblSignout;
	private JButton btnUserData;
	private JLabel lblUser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrameMain frame = new JFrameMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
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

		// Tính toán kích thước hợp lý, có thể là 70% chiều rộng và 70% chiều cao của màn hình
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
		initSidebar();
	    initNavbar(); 
        loadSavedLoginDetails();
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/asset/image/a.png")));
        setIconImage(icon.getImage());

	}
	 private void initLoginPanel() {
	        panelLogin = new JPanel();
	        panelLogin.setBackground(new Color(243, 202, 82));
	        contentPaneCard.add(panelLogin, "Login");
	        panelLogin.setLayout(null);

	        JPanel panel = new JPanel();
	        panel.setBackground(new Color(122, 186, 120));
	        panel.setBounds(81, 25, 857, 555);
	        panelLogin.add(panel);
	        panel.setLayout(null);

	        JLabel lblEmail = new JLabel("Email:");
	        lblEmail.setFont(new Font("Tahoma", Font.BOLD, 18));
	        lblEmail.setBounds(194, 128, 88, 35);
	        panel.add(lblEmail);

	        txtEmail = new JTextField();
	        txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 18));
	        txtEmail.setBounds(321, 127, 344, 39);
	        panel.add(txtEmail);
	        txtEmail.setColumns(10);

	        JLabel lblPassword = new JLabel("Password:");
	        lblPassword.setFont(new Font("Tahoma", Font.BOLD, 18));
	        lblPassword.setBounds(194, 204, 112, 35);
	        panel.add(lblPassword);

	        txtPassword = new JPasswordField();
	        txtPassword.setBounds(321, 205, 344, 39);
	        panel.add(txtPassword);

	        txtPassword.addKeyListener(new KeyListener() {
	            @Override
	            public void keyTyped(KeyEvent e) {}

	            @Override
	            public void keyPressed(KeyEvent e) {
	                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
	                    checkLogin();
	                }
	            }

	            @Override
	            public void keyReleased(KeyEvent e) {}
	        });

	        chkRememberMe = new JCheckBox("Remember Me");
	        chkRememberMe.setBackground(new Color(115, 187, 163));
	        chkRememberMe.setFont(new Font("Tahoma", Font.BOLD, 11));
	        chkRememberMe.setBounds(341, 288, 129, 25);
	        panel.add(chkRememberMe);

	        JSeparator separator = new JSeparator();
	        separator.setBounds(321, 271, 344, 2);
	        panel.add(separator);

	        JButton btnLogin = new JButton("Login");
	        btnLogin.setBackground(new Color(0, 255, 0));
	        btnLogin.setForeground(Color.BLACK);
	        btnLogin.setFont(new Font("Tahoma", Font.BOLD, 18));
	        btnLogin.setBounds(341, 336, 128, 35);
	        btnLogin.addActionListener(this::checkEventButton);
	        panel.add(btnLogin);

	        JButton btnReset = new JButton("Reset");
	        btnReset.setBackground(new Color(76, 175, 80));
	        btnReset.setForeground(Color.BLACK);
	        btnReset.setFont(new Font("Tahoma", Font.BOLD, 18));
	        btnReset.setBounds(516, 336, 128, 35);
	        btnReset.addActionListener(this::checkEventButton);
	        panel.add(btnReset);

	        JLabel lblLoginTitle = new JLabel("Login");
	        lblLoginTitle.setFont(new Font("Tahoma", Font.BOLD, 42));
	        lblLoginTitle.setHorizontalAlignment(SwingConstants.CENTER);
	        lblLoginTitle.setBounds(10, 10, 837, 59);
	        panel.add(lblLoginTitle);
	    }
	 private void initMainPanel() {
	        panelMain = new JPanel();
	        contentPaneCard.add(panelMain, "Main");
	        panelMain.setLayout(new BorderLayout(0, 0));

	        penalSiledbar = new JPanel();
	        penalSiledbar.setBackground(new Color(104, 109, 118));
	        panelMain.add(penalSiledbar, BorderLayout.WEST);
	        initSidebar();

	        panelNarbar = new JPanel();
	        panelMain.add(panelNarbar, BorderLayout.NORTH);
	        panelNarbar.setPreferredSize(new Dimension(300, 60));
	        panelNarbar.setMinimumSize(new Dimension(300, 60));    // Kích thước nhỏ nhất
	        panelNarbar.setMaximumSize(new Dimension(300, 60));    // Kích thước lớn nhất
	        
	        panelSubCard = new JPanel();
	        panelMain.add(panelSubCard, BorderLayout.CENTER);
	        panelSubCard.setLayout(new CardLayout(0, 0));

	        initSubPanels();
	    }
	 private void initNavbar() {
		 lblNameEmployeeLogin = new JLabel("");
			lblNameEmployeeLogin.setFont(new Font("Tahoma", Font.PLAIN, 19));
			lblNameEmployeeLogin.setHorizontalAlignment(SwingConstants.LEFT);
			 ImageIcon iconbell = new ImageIcon(Objects.requireNonNull(JFrameMain.class.getResource("/asset/image/bell.png")));
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
			ImageIcon iconuser = new ImageIcon(Objects.requireNonNull(JFrameMain.class.getResource("/asset/image/user.png")));
			 Image resizedImageuser = iconuser.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH); 
			 ImageIcon resizedIconuser = new ImageIcon(resizedImageuser);
			 lblUser.setIcon(resizedIconuser);
			
			
			
			GroupLayout gl_panelNarbar = new GroupLayout(panelNarbar);
			gl_panelNarbar.setHorizontalGroup(
				gl_panelNarbar.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panelNarbar.createSequentialGroup()
						.addGap(60)
						.addComponent(lblUser, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblNameEmployeeLogin, GroupLayout.PREFERRED_SIZE, 471, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 486, Short.MAX_VALUE)
						.addComponent(lblbell, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
						.addGap(389))
			);
			gl_panelNarbar.setVerticalGroup(
				gl_panelNarbar.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panelNarbar.createSequentialGroup()
						.addComponent(lblbell, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
						.addGap(20))
					.addGroup(gl_panelNarbar.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblNameEmployeeLogin, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
						.addContainerGap())
					.addGroup(gl_panelNarbar.createSequentialGroup()
						.addGap(21)
						.addComponent(lblUser, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(29, Short.MAX_VALUE))
			);
			panelNarbar.setLayout(gl_panelNarbar);
		}

	 private void initSidebar() {
	        Color colorbutton = Color.decode("#88D66C");
	        btnStatistics = new JButton("Statistics");
	        btnStatistics.setFont(new Font("Tahoma", Font.BOLD, 16));
	        btnStatistics.setMnemonic('S');
	        btnStatistics.setBackground(colorbutton);
	        btnStatistics.addActionListener(this::checkEventButton);
	        ImageIcon iconstatistics = new ImageIcon(Objects.requireNonNull(getClass().getResource("/asset/image/statistics.png")));
	        Image resizedImagestatistics = iconstatistics.getImage().getScaledInstance(30,30, Image.SCALE_SMOOTH); 
			ImageIcon resizedIconstatistics = new ImageIcon(resizedImagestatistics);
			btnStatistics.setIcon(resizedIconstatistics);

	        btnApproval = new JButton("Approval");
	        btnApproval.setVisible(false);
	        btnApproval.setFont(new Font("Tahoma", Font.BOLD, 16));
	        btnApproval.setMnemonic('A');
	        btnApproval.setBackground(colorbutton);
	        btnApproval.addActionListener(this::checkEventButton);
	        btnStatistics.setIcon(new ImageIcon(new ImageIcon("image/statistic.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH)));
	        ImageIcon iconcheck = new ImageIcon(Objects.requireNonNull(getClass().getResource("/asset/image/check.png")));
	        Image resizedImagecheck = iconcheck.getImage().getScaledInstance(30,30, Image.SCALE_SMOOTH); 
			ImageIcon resizedIconcheck = new ImageIcon(resizedImagecheck);
			btnApproval.setIcon(resizedIconcheck);

	        btnVacation = new JButton("Vacation");
	        btnVacation.setFont(new Font("Tahoma", Font.BOLD, 16));
	        btnVacation.setMnemonic('V');
	        btnVacation.setBackground(colorbutton);
	        btnVacation.addActionListener(this::checkEventButton);
	        ImageIcon iconvacation = new ImageIcon(Objects.requireNonNull(getClass().getResource("/asset/image/vacation.png")));
			Image resizedImagevacation = iconvacation.getImage().getScaledInstance(30,30, Image.SCALE_SMOOTH); 
			ImageIcon resizedIconvacation = new ImageIcon(resizedImagevacation);
			btnVacation.setIcon(resizedIconvacation);

	        btnLeavetypes = new JButton("LeaveTypes");
	        btnLeavetypes.setVisible(false);
	        btnLeavetypes.setFont(new Font("Tahoma", Font.BOLD, 16));
	        btnLeavetypes.setMnemonic('L');
	        btnLeavetypes.setBackground(colorbutton);
	        btnLeavetypes.addActionListener(this::checkEventButton);
	        ImageIcon iconleave = new ImageIcon(Objects.requireNonNull(getClass().getResource("/asset/image/leave.png")));
	        Image resizedImageleave = iconleave.getImage().getScaledInstance(30,30, Image.SCALE_SMOOTH); 
			ImageIcon resizedIconleave = new ImageIcon(resizedImageleave);
			btnLeavetypes.setIcon(resizedIconleave);
	        
	        btnLogout = new JButton("Logout");
	        ImageIcon iconcheckout = new ImageIcon(Objects.requireNonNull(getClass().getResource("/asset/image/check-out.png")));
	        Image resizedImage = iconcheckout.getImage().getScaledInstance(30,30, Image.SCALE_SMOOTH); 
			ImageIcon resizedIcon = new ImageIcon(resizedImage);
			btnLogout.setIcon(resizedIcon);
	        btnLogout.setFont(new Font("Tahoma", Font.BOLD, 16));
	        btnLogout.setMnemonic('L');
	        btnLogout.setBackground(colorbutton);
	        btnLogout.addActionListener(this::checkEventButton);

	        btnProfileEmployee = new JButton("Profile");
	        btnProfileEmployee.setFont(new Font("Tahoma", Font.BOLD, 16));
	        btnProfileEmployee.setMnemonic('P');
	        btnProfileEmployee.setBackground(colorbutton);
	        btnProfileEmployee.addActionListener(this::checkEventButton);
	        ImageIcon iconprofile = new ImageIcon(Objects.requireNonNull(getClass().getResource("/asset/image/profile.png")));
	        Image resizedImageprofile = iconprofile.getImage().getScaledInstance(30,30, Image.SCALE_SMOOTH); 
			ImageIcon resizedIconprofile = new ImageIcon(resizedImageprofile);
			btnProfileEmployee.setIcon(resizedIconprofile);
	        
	        lblSignout = new JLabel("Sign Out",SwingConstants.CENTER);
	        lblSignout.setFont(new Font("Tahoma", Font.BOLD, 16));
	        lblSignout.setIcon(resizedIcon);
	        
	        btnUserData = new JButton("User Data");
	        btnUserData.setMnemonic('U');
	        btnUserData.setFont(new Font("Tahoma", Font.BOLD, 16));
	        btnUserData.setBackground(new Color(136, 214, 108));
	        ImageIcon iconpersonal = new ImageIcon(Objects.requireNonNull(getClass().getResource("/asset/image/client.png")));
	        Image resizedImageper = iconpersonal.getImage().getScaledInstance(30,30, Image.SCALE_SMOOTH); 
			ImageIcon resizedIconper = new ImageIcon(resizedImageper);
			btnUserData.setIcon(resizedIconper);
	    
	        GroupLayout gl_penalSiledbar = new GroupLayout(penalSiledbar);
	        gl_penalSiledbar.setHorizontalGroup(
	        	gl_penalSiledbar.createParallelGroup(Alignment.LEADING)
	        		.addGroup(gl_penalSiledbar.createSequentialGroup()
	        			.addGroup(gl_penalSiledbar.createParallelGroup(Alignment.LEADING)
	        				.addGroup(gl_penalSiledbar.createSequentialGroup()
	        					.addContainerGap()
	        					.addGroup(gl_penalSiledbar.createParallelGroup(Alignment.LEADING)
	        						.addComponent(btnVacation, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
	        						.addComponent(btnLogout, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
	        						.addComponent(btnProfileEmployee, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
	        						.addComponent(btnStatistics, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
	        						.addComponent(btnApproval, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
	        						.addComponent(btnLeavetypes, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)))
	        				.addGroup(gl_penalSiledbar.createSequentialGroup()
	        					.addGap(33)
	        					.addComponent(lblSignout, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE))
	        				.addGroup(gl_penalSiledbar.createSequentialGroup()
	        					.addContainerGap()
	        					.addComponent(btnUserData, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)))
	        			.addContainerGap())
	        );
	        gl_penalSiledbar.setVerticalGroup(
	        	gl_penalSiledbar.createParallelGroup(Alignment.LEADING)
	        		.addGroup(gl_penalSiledbar.createSequentialGroup()
	        			.addContainerGap()
	        			.addComponent(btnProfileEmployee)
	        			.addGap(11)
	        			.addComponent(btnStatistics)
	        			.addGap(11)
	        			.addComponent(btnApproval, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
	        			.addGap(18)
	        			.addComponent(btnLeavetypes, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
	        			.addPreferredGap(ComponentPlacement.UNRELATED)
	        			.addComponent(btnVacation, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
	        			.addGap(18)
	        			.addComponent(btnUserData)
	        			.addGap(266)
	        			.addComponent(lblSignout)
	        			.addPreferredGap(ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
	        			.addComponent(btnLogout, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
	        			.addGap(27))
	        );
	        penalSiledbar.setLayout(gl_penalSiledbar);
	    }
	 private void initSubPanels() {
	        panelStatistics = new JPanel();
	        panelStatistics.setBackground(new Color(243, 202, 82));
	        panelSubCard.add(panelStatistics, "Statistics");
	        panelStatistics.setLayout(null);

	        static1 = new Static();
	        static1.setBounds(10, 11, 1043, 649);
	        panelStatistics.add(static1);

	        panelApproval = new JPanel();
	        panelApproval.setBackground(new Color(243, 202, 82));
	        panelSubCard.add(panelApproval, "Approval");
	        panelApproval.setLayout(null);

	        tabbedPaneApproval = new JTabbedPane(JTabbedPane.TOP);
	        tabbedPaneApproval.setFont(new Font("Tahoma", Font.PLAIN, 16));
	        tabbedPaneApproval.setBounds(32, 11, 1001, 625);
	        panelApproval.add(tabbedPaneApproval);

	        panelProcessed = new JPanel();
	        panelProcessed.setBackground(new Color(191, 246, 195));
	        tabbedPaneApproval.addTab("Processed", null, panelProcessed, null);
	        panelProcessed.setLayout(null);

	        panelPending = new JPanel();
	        panelPending.setBackground(new Color(191, 246, 195));
	        tabbedPaneApproval.addTab("Pending Approval", null, panelPending, null);
	        panelPending.setLayout(new CardLayout(0, 0));

	        tabbedPaneApproval.addChangeListener(e -> {
	            int selectedIndex = tabbedPaneApproval.getSelectedIndex();
	            if (selectedIndex == 0) {
	            	loadProcesedData();
	            } else if (selectedIndex == 1) {
	                loadPendingData();
	            }
	        });

	        panelVacation = new JPanel();
	        panelVacation.setBackground(new Color(243, 202, 82));
	        panelSubCard.add(panelVacation, "Vacation");
	        panelVacation.setLayout(null);

	        panelVactionRequest = new JPanel();
	        panelVactionRequest.setLayout(null);
	        panelVactionRequest.setBackground(new Color(191, 246, 195));
	        panelVactionRequest.setBounds(0, 0, 1055, 671);
	        panelVacation.add(panelVactionRequest);

	        panelLeaveTypes = new JPanel();
	        panelLeaveTypes.setBackground(new Color(243, 202, 82));
	        panelSubCard.add(panelLeaveTypes, "LeaveTypes");
	        panelLeaveTypes.setLayout(null);

	        leaveTypes = new LeaveTypes();
	        leaveTypes.setBounds(10, 11, 1043, 649);
	        panelLeaveTypes.add(leaveTypes);

	        panelProfileEmployee = new JPanel();
	        panelProfileEmployee.setBackground(new Color(243, 202, 82));
	        panelSubCard.add(panelProfileEmployee, "ProfileEmployee");
	        panelProfileEmployee.setLayout(null);
	    }
	 private void checkEventButton(ActionEvent e) {
	        String actionCommand = e.getActionCommand();
	        switch (actionCommand) {
	            case "Statistics" -> showCard("Statistics", btnStatistics);
	            case "Approval" -> {
	                showCard("Approval", btnApproval);
	                loadProcesedData();
	                loadPendingData();
	            }
	            case "Vacation" -> {
	                showCard("Vacation", btnVacation);
	                loadVacationRequestsData();
	            }
	            case "LeaveTypes" -> showCard("LeaveTypes", btnLeavetypes);
	            case "ProfileEmployee" -> showCard("ProfileEmployee", btnProfileEmployee);
	            case "Logout" -> logout();
	            case "Login" -> checkLogin();
	            case "Reset" -> reset();
	        }
	    }
	 private void showCard(String cardName, JButton button) {
	        highlightButton(button);
	        CardLayout cardLayout = (CardLayout) panelSubCard.getLayout();
	        cardLayout.show(panelSubCard, cardName);
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
	


	public void checkLogin() {
		
	    String email = txtEmail.getText();
	    String password = new String(txtPassword.getPassword());

	    StringBuilder sb = new StringBuilder();
	    


	    if (email.equals("")) {
	        sb.append("Email cannot be blank!\n");
	    }
	    else if (!isValidEmail(email)) {
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
	    emp = dao.checkLogin(email, password);
	    
	    if (emp != null) {
	        // Save login details if "Remember Me" is selected
	        Preferences prefs = Preferences.userNodeForPackage(JFrameMain.class);
	        prefs.put("email", email);
	        if (chkRememberMe.isSelected()) {
	            prefs.put("password", password);
	        } else {
	            prefs.remove("password");
	        }

	        var cardLayout = (CardLayout) contentPaneCard.getLayout();
	        role = dao.selectRole(emp.getEmployeeID());
	        
	        
	        
	        switch (role.getRoleName()) {
	            case "User" -> {
	            }
	            case "Admin" -> {
	            	btnApproval.setVisible(true);
	            	btnLeavetypes.setVisible(true);
	            }
	            
	            case "Leader" -> {
	            	btnApproval.setVisible(true);
	            }
	            
	        }
	        
			
			notificationPanel = new NotificationPanel(emp.getEmployeeID());
			
	        
	        static1.loaddata(role, emp);
	        lblNameEmployeeLogin.setText("Welcome, " + emp.getEmployeeName() + " (" + role.getRoleName() + ")");
	        cardLayout.show(contentPaneCard, "Main");
	    } else {
	        JOptionPane.showMessageDialog(null, "The account does not exist, please re-enter");
	        reset();
	    }
	    profileEmployee = new ProfileEmployee();
		profileEmployee.populateProfile(emp);
		profileEmployee.setBounds(10, 11, 1042, 649);
		panelProfileEmployee.add(profileEmployee);
//		
	}
	
	
	
	
	private boolean isValidEmail(String email) {
	    // Regex pattern to validate email addresses ending with .com
		String emailRegex = "^[a-zA-Z0-9._%+-]+@(gmail\\.com|outlook\\.com|icloud\\.com|zoho\\.com|yahoo\\.com|aol\\.com|gmx\\.com|example\\.com)$";
	    return email.matches(emailRegex);
	}
	

	private void loadSavedLoginDetails() {
        Preferences prefs = Preferences.userNodeForPackage(JFrameMain.class);
        String savedEmail = prefs.get("email", "");
        String savedPassword = prefs.get("password", "");

        txtEmail.setText(savedEmail);
        txtPassword.setText(savedPassword);

        if (!savedEmail.isEmpty() && !savedPassword.isEmpty()) {
            chkRememberMe.setSelected(true);
        }
    }

    public void reset() {
        txtEmail.setText("");
        txtPassword.setText("");
        chkRememberMe.setSelected(false);

        Preferences prefs = Preferences.userNodeForPackage(JFrameMain.class);
        prefs.remove("email");
        prefs.remove("password");
    }
    private void logout() {
        resetButtonColors();
        loadSavedLoginDetails();
        CardLayout cardLayout = (CardLayout) contentPaneCard.getLayout();
        cardLayout.show(contentPaneCard, "Login");
        btnApproval.setVisible(false);
        btnLeavetypes.setVisible(false);
    }

//    private void loadNewRequestData() {
//    	panelVactionRequest.removeAll();
//		 addLeaveRequest = new AddLeaveRequest();
//		 addLeaveRequest.setBounds(52, 42, 898, 511);
//		 panelRequest.add(addLeaveRequest);  
//		 panelRequest.revalidate();
//	     panelRequest.repaint();
//	    }
	 private void loadVacationRequestsData() {
		 panelVactionRequest.removeAll();
		 vacationRequest = new VacationRequest(emp, role);
		 vacationRequest.setBounds(10, 11, 1035, 649);
		 panelVactionRequest.add(vacationRequest);  
		 panelVactionRequest.revalidate();
		 panelVactionRequest.repaint();
	    }
	 
	 private void loadProcesedData() { 
		panelProcessed.removeAll();
		processed = new Processed(emp, role);
		processed.setBounds(10, 11, 983, 581);
		panelProcessed.add(processed);
		panelProcessed.revalidate();
		panelProcessed.repaint();
	 }
	 
	 private void loadPendingData() {
		 panelPending.removeAll();
		 pendingApproval = new PendingApproval(emp, role);
		pendingApproval.setBounds(10, 11, 976, 577);
		 panelPending.add(pendingApproval);
		 panelPending.revalidate();
		 panelPending.repaint();
		 
	 }
}
