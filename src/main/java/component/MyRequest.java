package component;

import java.awt.Component;

import javax.swing.*;

public class MyRequest extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;
	private Component panelApproval;
	private JPanel panelReject;

	/**
	 * Create the panel.
	 */
	public MyRequest() {
		setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 401, 261);
		add(tabbedPane);
		
		panelApproval = new JPanel();
		tabbedPane.addTab("được duyệt", null, panelApproval, null);
//		panelApproval.setLayout(null);
		
		panelReject = new JPanel();
		tabbedPane.addTab("bị từ chối", null, panelReject, null);
		panelReject.setLayout(null);
		
	}
}
