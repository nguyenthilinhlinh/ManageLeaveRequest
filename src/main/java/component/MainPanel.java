package component;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JPanel;

import context.MediatorCardController;
import context.MediatorColleague;

public class MainPanel extends JPanel implements MediatorColleague {

	private static final long serialVersionUID = 1L;
	
	private ProfilePanel profilePanel;
	private SidebarPanel sidebarPanel;
	private NavbarPanel navbarPanel;
	private JPanel cardContentPanel;
	private ApprovalPanel panelApproval;

	public MainPanel(MediatorCardController frameCardController) {
		setLayout(new BorderLayout(0, 0));
		cardContentPanel = new JPanel();
		add(cardContentPanel, BorderLayout.CENTER);
		cardContentPanel.setLayout(new CardLayout(0, 0));
		
		var cardController = new MediatorCardController(cardContentPanel);

		var panelStatistics = new StatisticsPanel();
		cardController.addCardLazy(panelStatistics, "Statistics");
		panelApproval = new ApprovalPanel();
		cardController.addCardLazy(panelApproval, "Approval");
		var panelVacation = new VacationRequestPanel();
		cardController.addCardLazy(panelVacation, "Vacation");
		var panelLeaveTypes = new LeaveTypes();
		cardController.addCardLazy(panelLeaveTypes, "LeaveTypes");
		profilePanel = new ProfilePanel();
		cardController.addCardLazy(profilePanel, "ProfileEmployee");
		
		sidebarPanel = new SidebarPanel(cardController, frameCardController);
		add(sidebarPanel, BorderLayout.WEST);
		navbarPanel = new NavbarPanel();
		add(navbarPanel, BorderLayout.NORTH);

	}

	@Override
	public void onNotify() {
		sidebarPanel.updatePermissions();
		navbarPanel.updatePermissions();
	}

}
