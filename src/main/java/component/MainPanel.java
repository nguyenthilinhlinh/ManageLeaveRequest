package component;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import context.AuthenticationContextManager;
import context.CardController;
import entity.Employees;
import entity.Role;

public class MainPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private ProfilePanel profilePanel;
	private SidebarPanel sidebarPanel;
	private NavbarPanel navbarPanel;
	private JPanel cardContentPanel;
	private ApprovalPanel panelApproval;

	private final CardController cardController;
	
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
				profilePanel.updatePermissions();
				sidebarPanel.updatePermissions();
				navbarPanel.updatePermissions();
				panelApproval.updatePermissions();
			}
		});

	}

	private void initCardContentPanel() {
		var panelStatistics = new StatisticsPanel();
		cardController.addCard(panelStatistics, "Statistics");

		panelApproval = new ApprovalPanel();
		cardController.addCard(panelApproval, "Approval");

		var panelVacation = new VacationRequestPanel();
		cardController.addCard(panelVacation, "Vacation");

		var panelLeaveTypes = new LeaveTypes();
		cardController.addCard(panelLeaveTypes, "LeaveTypes");

		profilePanel = new ProfilePanel();
		cardController.addCard(profilePanel, "ProfileEmployee");
	}

	
}
