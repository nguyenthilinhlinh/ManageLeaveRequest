package component;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import constants.UIConstants;
import context.AuthenticationContextManager;
import context.MediatorColleague;
import entity.Employees;
import entity.Role;

public class ApprovalPanel extends JPanel implements MediatorColleague {

	private static final long serialVersionUID = 7266477092442756748L;
	private JPanel panelProcessed;
	private JPanel panelPending;
	private Role role;
	private Employees emp;
	private JTabbedPane tabbedPaneApproval;

	public ApprovalPanel() {
		setBackground(new Color(243, 202, 82));
		setLayout(null);

		tabbedPaneApproval = new JTabbedPane(JTabbedPane.TOP);
		tabbedPaneApproval.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 16));
		tabbedPaneApproval.setBounds(32, 11, 1001, 625);
		add(tabbedPaneApproval);

		panelProcessed = new JPanel();
		panelProcessed.setBackground(new Color(191, 246, 195));
		panelProcessed.setLayout(null);
		tabbedPaneApproval.addTab("Processed", null, panelProcessed, null);

		panelPending = new JPanel();
		panelPending.setBackground(new Color(191, 246, 195));
		panelPending.setLayout(new CardLayout(0, 0));
		tabbedPaneApproval.addTab("Pending Approval", null, panelPending, null);

		tabbedPaneApproval.addChangeListener(e -> {
			int selectedIndex = tabbedPaneApproval.getSelectedIndex();
			if (selectedIndex == 0) {
				loadProcesedData();
			} else if (selectedIndex == 1) {
				loadPendingData();
			}
		});
	}
	
	@Override
	public void onNotify() {
		emp = AuthenticationContextManager.getInstance().getAuthn();
		role = AuthenticationContextManager.getInstance().getAuthz();
		
		loadProcesedData();
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
