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
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.prefs.Preferences;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JCalendar;

import dao.EmployeeDao;
import dao.LeaveRequestDao;
import entity.Employees;
import entity.Role;
import helper.RegexConst;
import helper.Valid;
import component.Processed;
import component.PendingApproval;
import java.awt.event.MouseAdapter;
import javax.swing.event.AncestorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.AncestorEvent;
import component.AddLeaveRequest;
import component.VacationRequest;
import javax.swing.LayoutStyle.ComponentPlacement;
import component.LeaveTypes;
import component.NotificationPanel;
import component.ProfileEmployee;
import component.Static;

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
	private JPanel panel;
	private JTextField txtEmail;
	private JPasswordField txtPassword;
	private JLabel lblNewLabel_1;
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
	private JLabel lblNewLabel;
	private JButton btnLogout;
	private JButton btnProfileEmployee;
	private JPanel panelProfileEmployee;
	private ProfileEmployee profileEmployee;
	private AddLeaveRequest addLeaveRequest;
	private Role role;
	private Static static1;
	private JPanel panelVactionRequest;
	private NotificationPanel notificationPanel;

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
		setResizable(false);
		setTitle("Leave request");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1266, 775);
		contentPaneCard = new JPanel();
		contentPaneCard.setBackground(new Color(243, 202, 82));
		contentPaneCard.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPaneCard);
		contentPaneCard.setLayout(new CardLayout(0, 0));

		panelLogin = new JPanel();
//		Color panelyellow = Color.decode("#F6FB7A");
		Color panelyellow = Color.decode("#F3CA52");
//		Color panelgreen = Color.decode("#B4E380");
		Color panelgreen = Color.decode("#7ABA78");
		Color panelgrey = Color.decode("#686D76");
		panelLogin.setBackground(new Color(243, 202, 82));
		contentPaneCard.add(panelLogin, "Login");
		panelLogin.setLayout(null);

		panel = new JPanel();
		panel.setBackground(panelgreen);
		panel.setBounds(179, 93, 857, 555);
		panelLogin.add(panel);
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
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					checkLogin();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}
		});

		chkRememberMe = new JCheckBox("Remember Me");
		Color panelgreenf = Color.decode("#73BBA3");
		chkRememberMe.setBackground(panelgreenf);
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
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkEventButton(e);
			}
		});
		panel.add(btnLogin);

		JButton btnReset = new JButton("Reset");
		btnReset.setBackground(new Color(76, 175, 80));
		btnReset.setForeground(Color.BLACK);
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnReset.setBounds(516, 336, 128, 35);
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkEventButton(e);
			}
		});
		panel.add(btnReset);
		panel.setLayout(null);
		panel.add(lblEmail);
		panel.add(txtEmail);
		panel.add(lblPassword);
		panel.add(txtPassword);
		panel.add(chkRememberMe);
		panel.add(separator);
		panel.add(btnLogin);
		panel.add(btnReset);

		lblNewLabel_1 = new JLabel("Login");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 42));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(10, 21, 837, 59);
		panel.add(lblNewLabel_1);

		panelMain = new JPanel();
		contentPaneCard.add(panelMain, "Main");
		panelMain.setLayout(new BorderLayout(0, 0));

		penalSiledbar = new JPanel();
		penalSiledbar.setBackground(panelgrey);
		panelMain.add(penalSiledbar, BorderLayout.WEST);
		Color colorbutton = Color.decode("#88D66C");
		btnStatistics = new JButton("Statistics");
		btnStatistics.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnStatistics.setMnemonic('S');
		btnStatistics.setBackground(colorbutton);
		btnStatistics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkEventButton(e);
			}
		});

		btnApproval = new JButton("Approval");
		btnApproval.setVisible(false);
		btnApproval.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnApproval.setMnemonic('A');
		btnApproval.setBackground(new Color(136, 214, 108));
		btnApproval.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkEventButton(e);
			}
		});

		btnVacation = new JButton("Vacation");
		btnVacation.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnVacation.setMnemonic('V');
		btnVacation.setBackground(colorbutton);
		btnVacation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkEventButton(e);
			}
		});

		btnLeavetypes = new JButton("LeaveTypes");
		btnLeavetypes.setVisible(false);
		btnLeavetypes.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnLeavetypes.setMnemonic('L');
		btnLeavetypes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkEventButton(e);
			}
		});
		
        loadSavedLoginDetails();
		btnLeavetypes.setBackground(new Color(136, 214, 108));
		
		btnLogout = new JButton("Logout");
		btnLogout.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnLogout.setMnemonic('L');
		btnLogout.setBackground(new Color(136, 214, 108));
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkEventButton(e);
			}
		});
		
		btnProfileEmployee = new JButton("ProfileEmployee");
		btnProfileEmployee.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnProfileEmployee.setMnemonic('P');
		btnProfileEmployee.setBackground(new Color(136, 214, 108));
		btnProfileEmployee.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        checkEventButton(e);
		    }
		});
		
		
		GroupLayout gl_penalSiledbar = new GroupLayout(penalSiledbar);
		gl_penalSiledbar.setHorizontalGroup(
			gl_penalSiledbar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_penalSiledbar.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_penalSiledbar.createParallelGroup(Alignment.LEADING)
						.addComponent(btnStatistics, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
						.addComponent(btnApproval, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
						.addComponent(btnLeavetypes, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
						.addComponent(btnVacation, GroupLayout.DEFAULT_SIZE, 143, Short.MAX_VALUE)
						.addComponent(btnProfileEmployee, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnLogout, GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_penalSiledbar.setVerticalGroup(
			gl_penalSiledbar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_penalSiledbar.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnProfileEmployee, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnStatistics, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnApproval, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnLeavetypes, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnVacation, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnLogout, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(380, Short.MAX_VALUE))
		);
		penalSiledbar.setLayout(gl_penalSiledbar);

		panelNarbar = new JPanel();
		panelMain.add(panelNarbar, BorderLayout.NORTH);

		lblNameEmployeeLogin = new JLabel("");
		lblNameEmployeeLogin.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblNameEmployeeLogin.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblNewLabel = new JLabel("Thông Báo");

		
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

 
                
				notificationPanel.setVisible(true);
            }
		});
		lblNewLabel.setBackground(new Color(255, 255, 0));
		GroupLayout gl_panelNarbar = new GroupLayout(panelNarbar);
		gl_panelNarbar.setHorizontalGroup(
			gl_panelNarbar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelNarbar.createSequentialGroup()
					.addGap(76)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
					.addGap(275)
					.addComponent(lblNameEmployeeLogin, GroupLayout.PREFERRED_SIZE, 471, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(349, Short.MAX_VALUE))
		);
		gl_panelNarbar.setVerticalGroup(
			gl_panelNarbar.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelNarbar.createSequentialGroup()
					.addGroup(gl_panelNarbar.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNameEmployeeLogin, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
					.addContainerGap())
		);
		panelNarbar.setLayout(gl_panelNarbar);

		panelSubCard = new JPanel();
		panelMain.add(panelSubCard, BorderLayout.CENTER);
		panelSubCard.setLayout(new CardLayout(0, 0));

		panelStatistics = new JPanel();
		panelStatistics.setBackground(new Color(243, 202, 82));
		panelSubCard.add(panelStatistics, "Statistics");
		panelStatistics.setLayout(null);
		
		static1 = new Static();
		static1.setBounds(10, 11, 1043, 649);
		panelStatistics.add(static1);

		panelApproval = new JPanel();
		panelApproval.setBackground(panelyellow);
		panelSubCard.add(panelApproval, "Approval");
		panelApproval.setLayout(null);

		tabbedPaneApproval = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneApproval.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tabbedPaneApproval.setBounds(32, 11, 1001, 625);
		panelApproval.add(tabbedPaneApproval);

//		pendingApproval = new PendingApproval();
//		pendingApproval.setBounds(10, 11, 976, 577);
//		panelPending.add(pendingApproval);
		panelProcessed = new JPanel();
		panelProcessed.setBackground(new Color(191, 246, 195));
		tabbedPaneApproval.addTab("Processed", null, panelProcessed, null);
		
		panelProcessed.setLayout(null);

		panelPending = new JPanel();
		panelPending.setBackground(new Color(191, 246, 195));

		tabbedPaneApproval.addTab("Pending Approval", null, panelPending, null);
		panelPending.setLayout(new CardLayout(0, 0));

//		processed = new Processed();
//		processed.setBounds(10, 11, 983, 581);
//		panelProcessed.add(processed);

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
		
		tabbedPaneApproval.addChangeListener((ChangeListener) new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int selectedIndex = tabbedPaneApproval.getSelectedIndex();
                if (selectedIndex == 0) {
                	loadProcesedData();
                } else if (selectedIndex == 1) {
                	loadPendingData();
                }
            }
        });

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

	protected void checkEventButton(ActionEvent e) {
	    String actionCommand = e.getActionCommand();
	    switch (actionCommand) {
	        case "Statistics" -> {
	            highlightButton(btnStatistics);
	            var cardLayout = (CardLayout) panelSubCard.getLayout();
	            cardLayout.show(panelSubCard, "Statistics");
	        }

	        case "Approval" -> {
	            highlightButton(btnApproval);
	            var cardLayout = (CardLayout) panelSubCard.getLayout();
	            cardLayout.show(panelSubCard, "Approval");
	            processed = new Processed(emp, role);
	            processed.setBounds(10, 11, 983, 581);
	            panelProcessed.add(processed);

	            pendingApproval = new PendingApproval(emp, role);
	            pendingApproval.setBounds(10, 11, 976, 577);
	            panelPending.add(pendingApproval);
	        }

	        case "Vacation" -> {
	            highlightButton(btnVacation);
	            var cardLayout = (CardLayout) panelSubCard.getLayout();
	            cardLayout.show(panelSubCard, "Vacation");           
	            loadVacationRequestsData();
	        }

	        case "LeaveTypes" -> {
	            highlightButton(btnLeavetypes);
	            var cardLayoutLeaveTypes = (CardLayout) panelSubCard.getLayout();
	            cardLayoutLeaveTypes.show(panelSubCard, "LeaveTypes");
	        }

	        case "ProfileEmployee" -> {
	            highlightButton(btnProfileEmployee);
	            var cardLayoutProfile = (CardLayout) panelSubCard.getLayout();
	            cardLayoutProfile.show(panelSubCard, "ProfileEmployee");
	        }

	        case "Logout" -> {
	            highlightButton(btnLogout);
	            var cardLayout = (CardLayout) contentPaneCard.getLayout();
	            loadSavedLoginDetails();
	            cardLayout.show(contentPaneCard, "Login");
	            btnApproval.setVisible(false);
	            btnLeavetypes.setVisible(false);
	        }

	        case "GetMain" -> {
	            var cardLayout = (CardLayout) contentPaneCard.getLayout();
	        }

	        case "Login" -> {
	            checkLogin();
	            panelStatistics.setVisible(true);
//	            btnVacation.setVisible(false);
	        }

	        case "Reset" -> {
	            Reset();
	        }
	    }
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
	        Reset();
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

    public void Reset() {
        txtEmail.setText("");
        txtPassword.setText("");
        chkRememberMe.setSelected(false);

        Preferences prefs = Preferences.userNodeForPackage(JFrameMain.class);
        prefs.remove("email");
        prefs.remove("password");
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
