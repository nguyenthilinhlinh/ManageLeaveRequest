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
	
	private JTabbedPane tabbedPaneApproval;
	private JPanel panelPending;
	private JPanel panelProcessed;
	private Employees emp = null;
	
	private ProfilePanel profilePanel;

	private Role role;
	private SidebarPanel sidebarPanel;
	private NavbarPanel navbarPanel;
	private JPanel cardContentPanel;
	private CardController cardController;
	
	public MainPanel(CardController frameCardController) {

		setLayout(new BorderLayout(0, 0));
		
		cardContentPanel = new JPanel();
		add(cardContentPanel, BorderLayout.CENTER);
		cardContentPanel.setLayout(new CardLayout(0, 0));
		
		cardController = new CardController(cardContentPanel);

		initCardContentPanel();
		
		sidebarPanel = new SidebarPanel(cardController, frameCardController);
		add(sidebarPanel, BorderLayout.WEST);
		navbarPanel = new NavbarPanel();
		add(navbarPanel, BorderLayout.NORTH);
		
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				emp = AuthenticationContextManager.getInstance().getAuthn();
				role = AuthenticationContextManager.getInstance().getAuthz();
				
				profilePanel.populateProfile(emp);
				sidebarPanel.updatePermissions();
				navbarPanel.updatePermissions();
			}
		});

	}

	private void initCardContentPanel() {
		var panelStatistics = new StatisticsPanel();
		cardController.addCard(panelStatistics, "Statistics");

		var panelApproval = new JPanel();
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

		var panelVacation = new VacationRequestPanel();
		cardController.addCard(panelVacation, "Vacation");

		var panelLeaveTypes = new LeaveTypes();
		cardController.addCard(panelLeaveTypes, "LeaveTypes");

		profilePanel = new ProfilePanel();
		cardController.addCard(profilePanel, "ProfileEmployee");
	}

	private void loadProcesedData() {
		panelProcessed.removeAll();
		var processed = new Processed(emp, role);
		processed.setBounds(10, 11, 983, 581);
		panelProcessed.add(processed);
		panelProcessed.revalidate();
		panelProcessed.repaint();
	}

	private void loadPendingData() {
		panelPending.removeAll();
		var pendingApproval = new PendingApproval(emp, role);
		pendingApproval.setBounds(10, 11, 976, 577);
		panelPending.add(pendingApproval);
		panelPending.revalidate();
		panelPending.repaint();

	}
}
