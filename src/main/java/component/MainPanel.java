package component;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import constants.UIConstants;
import context.AuthenticationContextManager;
import context.CardController;
import entity.Employees;
import entity.Role;

public class MainPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel panelStatistics;
	private JPanel panelApproval;
	private JPanel panelVacation;
	private JTabbedPane tabbedPaneApproval;
	private JPanel panelPending;
	private JPanel panelProcessed;
	private Employees emp = null;
	private Processed processed;
	private PendingApproval pendingApproval;
	private JPanel panelLeaveTypes;
	private LeaveTypes leaveTypes;
	private JPanel panelProfileEmployee;
	private ProfileEmployee profileEmployee;

	private Role role;
	private Static static1;
	private JPanel panelVactionRequest;
	private NotificationPanel notificationPanel;

	private JPanel sidebarPanel;
	private JPanel cardContentPanel;
	private CardController cardController;

	public MainPanel() {

		setLayout(new BorderLayout(0, 0));


		cardContentPanel = new JPanel();
		add(cardContentPanel, BorderLayout.CENTER);
		cardContentPanel.setLayout(new CardLayout(0, 0));
		
		cardController = new CardController(cardContentPanel);

		initCardContentPanel();
		
		sidebarPanel = new SidebarPanel(cardController);
		add(sidebarPanel, BorderLayout.WEST);
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				emp = AuthenticationContextManager.getInstance().getAuthn();
				role = AuthenticationContextManager.getInstance().getAuthz();
				
				notificationPanel = new NotificationPanel(emp.getEmployeeID());
//				static1.loaddata(role, emp);
				
				profileEmployee = new ProfileEmployee();
				profileEmployee.populateProfile(emp);
				profileEmployee.setBounds(10, 11, 1042, 649);
				panelProfileEmployee.add(profileEmployee);
			}
		});

	}

	private void initCardContentPanel() {
		panelStatistics = new JPanel();
		panelStatistics.setBackground(new Color(243, 202, 82));
		cardController.addCard(panelStatistics, "Statistics");
		panelStatistics.setLayout(null);

		static1 = new Static();
		static1.setBounds(10, 11, 1043, 649);
		panelStatistics.add(static1);

		panelApproval = new JPanel();
		panelApproval.setBackground(new Color(243, 202, 82));
		cardController.addCard(panelApproval, "Approval");
		panelApproval.setLayout(null);

		tabbedPaneApproval = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneApproval.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 16));
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

		panelVacation = new VacationRequest();
		panelVacation.setBackground(new Color(243, 202, 82));
		cardController.addCard(panelVacation, "Vacation");
		panelVacation.setLayout(null);

		panelVactionRequest = new JPanel();
		panelVactionRequest.setLayout(null);
		panelVactionRequest.setBackground(new Color(191, 246, 195));
		panelVactionRequest.setBounds(0, 0, 1055, 671);
		panelVacation.add(panelVactionRequest);

		panelLeaveTypes = new JPanel();
		panelLeaveTypes.setBackground(new Color(243, 202, 82));
		cardController.addCard(panelLeaveTypes, "LeaveTypes");
		panelLeaveTypes.setLayout(null);

		leaveTypes = new LeaveTypes();
		leaveTypes.setBounds(10, 11, 1043, 649);
		panelLeaveTypes.add(leaveTypes);

		panelProfileEmployee = new JPanel();
		panelProfileEmployee.setBackground(new Color(243, 202, 82));
		cardController.addCard(panelProfileEmployee, "ProfileEmployee");
		panelProfileEmployee.setLayout(null);
	}

//	private void loadVacationRequestsData() {
//		panelVactionRequest.removeAll();
//		vacationRequest = new VacationRequest(emp, role);
//		vacationRequest.setBounds(10, 11, 1035, 649);
//		panelVactionRequest.add(vacationRequest);
//		panelVactionRequest.revalidate();
//		panelVactionRequest.repaint();
//	}

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
