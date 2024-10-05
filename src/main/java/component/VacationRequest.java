package component;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.DateUtil;

import dao.LeaveDocumentDao;
import dao.LeaveHRDao;
import dao.LeaveRequestDao;
import dao.LeaveTypeDao;
import entity.Employees;
import entity.LeaveDuration;
import entity.LeaveRequests;
import entity.LeaveType;
import entity.Role;
import gui.JFrameMain;
import helper.DateUtils;

import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import java.awt.Color;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.border.EtchedBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultRowSorter;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Font;

public class VacationRequest extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton editButton;
	private JButton deleteButton;
	private JTextField txtSearch;
	private JScrollPane scrollPane;
	private JTable table;
	private Employees user;
	private JFrame JformEdit;
	private Role role;
	private JMenuBar menuBarRequset;
	private JMenu mnNewMenu;
	private JMenuItem mntmRequestforme;
	private JMenuItem mntmRequestForEmployee;
	private JComboBox<String> cbbStatusApproved;
	private JButton addButton;

	private final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy");

	public JFrame getJformEdit() {
		return JformEdit;
	}

	/**
	 * Create the panel.
	 */
	public VacationRequest(Employees emp, Role r) {
		setBackground(new Color(191, 246, 195));
		role = r;
		user = emp;
		setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 75, 952, 444);
		add(scrollPane);

		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 16));

		table.setAutoCreateRowSorter(true);
		table.setRowHeight(30);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tableMouseClicked(e);
			}
		});

		scrollPane.setViewportView(table);
		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("Tahoma", Font.PLAIN, 16));

		deleteButton = new JButton("Delete Request");
		deleteButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		deleteButton.setBounds(656, 530, 137, 36);
		add(deleteButton);
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkEventButton(e);
			}
		});
		deleteButton.setMnemonic('d');

		editButton = new JButton("Edit Request");
		editButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		editButton.setBounds(825, 530, 137, 36);
		add(editButton);
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkEventButton(e);
			}
		});
		editButton.setMnemonic('e');

		txtSearch = new JTextField();
		txtSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldActionPerformed(e);
			}
		});
		txtSearch.setBackground(new Color(240, 240, 240));
		txtSearch.setBounds(674, 11, 288, 53);
		add(txtSearch);
		txtSearch.setColumns(10);
		txtSearch.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"<html><div style=\"font-size:16px\">Search", TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));

		menuBarRequset = new JMenuBar();
		menuBarRequset.setBounds(481, 528, 137, 36);
		add(menuBarRequset);
		menuBarRequset.setVisible(false);

		mnNewMenu = new JMenu("New Request ");
		mnNewMenu.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		menuBarRequset.add(mnNewMenu);

		mntmRequestforme = new JMenuItem("Request for me");
		mntmRequestforme.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmRequestforme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkEventButton(e);
			}
		});
		mnNewMenu.add(mntmRequestforme);

		mntmRequestForEmployee = new JMenuItem("Request for employee");
		mntmRequestForEmployee.setFont(new Font("Segoe UI", Font.PLAIN, 16));
		mntmRequestForEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkEventButton(e);
			}
		});
		mnNewMenu.add(mntmRequestForEmployee);

		cbbStatusApproved = new JComboBox<>();
		cbbStatusApproved.setBackground(new Color(240, 240, 240));
		cbbStatusApproved.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cbbStatusApprovedActionPerformed(e);
			}
		});

		cbbStatusApproved.setModel(new DefaultComboBoxModel<String>(new String[] { "", "Submitted", "Viewed by Lead",
				"Approved by Lead", "Viewed by HR", "Approved by HR", "Rejected" }));
		cbbStatusApproved.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"<html><div style=\"font-size:16px\">Status</div></html>", TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		cbbStatusApproved.setBounds(449, 11, 190, 53);
		add(cbbStatusApproved);

		addButton = new JButton("Add Request");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkEventButton(e);
			}
		});
		addButton.setMnemonic('d');
		addButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		addButton.setBounds(491, 530, 137, 36);
		add(addButton);

		showleaveRequest();
	}

	protected void tableMouseClicked(MouseEvent e) {
		// check double click
		if (e.getClickCount() != 2) {
			return;
		}

		AddLeaveRequest formEdit = new AddLeaveRequest();

		if (JformEdit != null && JformEdit.isShowing()) {
			JformEdit.dispose();
		}

		int row = table.getSelectedRow();
		int leaveRequestId = (int) table.getValueAt(row, 0);

		var daoLR = new LeaveRequestDao();
		daoLR.getLeaveRequestById(leaveRequestId).ifPresent(leaveRequest -> {
			JformEdit = new JFrame("View request");
			JformEdit.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			JformEdit.getContentPane().add(formEdit);
			formEdit.loadDataDetail(user, leaveRequest);
			JformEdit.setSize(900, 600); // Set the width and height of the frame
			JformEdit.setResizable(false); // Disable resizing
			JformEdit.setLocationRelativeTo(null);
			JformEdit.setVisible(true);
		});
	}

	public void showleaveRequest() {

		var daoLR = new LeaveRequestDao();
		var daoLT = new LeaveTypeDao();

		var model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // All cells are non-editable
			}
		};

		var leaveRequestTypes = daoLT.getAll();

//		tạo cột
		model.addColumn("Id");
		model.addColumn("IdLeaveType");
		model.addColumn("IdApproved");
		model.addColumn("FullName");
		model.addColumn("LeaveType");
		model.addColumn("StartDate");
		model.addColumn("EndDate");
		model.addColumn("Duration");
		model.addColumn("Reason");
		model.addColumn("ApprovalStatus");
		model.addColumn("SubmissionDate");

//		add hang vao	

		daoLR.getLeaveRequests(user.getEmployeeID()).forEach(lr -> {
			model.addRow(this.buildRow(lr, leaveRequestTypes));
		});
//		});
		table.setModel(model);
		table.getColumn("Id").setMinWidth(0);
		table.getColumn("Id").setMaxWidth(0);
		table.getColumn("Id").setWidth(0);
		table.getColumn("IdLeaveType").setMinWidth(1);
		table.getColumn("IdLeaveType").setMaxWidth(1);
		table.getColumn("IdLeaveType").setWidth(1);
		table.getColumn("IdApproved").setMinWidth(2);
		table.getColumn("IdApproved").setMaxWidth(2);
		table.getColumn("IdApproved").setWidth(2);
		table.getColumn("Reason").setMinWidth(0);
		table.getColumn("Reason").setMaxWidth(0);
		table.getColumn("Reason").setWidth(0);
		table.getColumn("StartDate").setMinWidth(0);
		table.getColumn("StartDate").setPreferredWidth(60);
		table.getColumn("EndDate").setMinWidth(0);
		table.getColumn("EndDate").setPreferredWidth(60);
		table.getColumn("Duration").setMinWidth(0);
		table.getColumn("Duration").setPreferredWidth(110);
		table.getColumn("ApprovalStatus").setPreferredWidth(105);
	}

	private Object[] buildRow(LeaveRequests lr, List<LeaveType> leaveRequestTypes) {
		var leaveRequestTypeName = leaveRequestTypes.stream()
				.filter(leaveRequestType -> leaveRequestType.getLeaveTypeID() == lr.getLeaveTypeId()).findFirst()
				.map(LeaveType::getLeaveTypeName).orElse("");

		var duration = DateUtils.between(lr.getStartDate(), lr.getEndDate());
		var days = duration.toHours() / 24;

		var displayDuration = "";
		if (days == 0) {
			var isFullDay = lr.getLeaveDuration() == LeaveDuration.FULL_DAY;
			displayDuration = isFullDay ? "1 day"
					: (lr.getLeaveDuration() == LeaveDuration.HALF_DAY_MORNING ? "0.5 day (Morning)"
							: "0.5 day (Afternoon)");
		} else {
			displayDuration = ((days + 1) + " days");
		}

		return new Object[] { lr.getLeaveRequestId(), lr.getLeaveTypeId(), lr.getApproverId(), user.getEmployeeName(),
				leaveRequestTypeName, DATE_FORMATTER.format(lr.getStartDate()), DATE_FORMATTER.format(lr.getEndDate()),
				displayDuration, lr.getReason(), lr.getStatusLR(), DATE_FORMATTER.format(lr.getSubmissionDate()) };
	}

	public void checkEventButton(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		switch (actionCommand) {
		case "Add Request" -> {
			add(false);
		}

		case "Edit Request" -> {

			edit();
		}

		case "Delete Request" -> {
			delete();
		}

		case "Request for me" -> {
			add(true);
		}

		case "Request for employee" -> {
			add(false);
		}

		}
	}

	protected void delete() {
		var str = role.getRoleName().equals("User") ? "Submitted" : "Approved by Lead";
		if (!table.isRowSelected(table.getSelectedRow())) {
			JOptionPane.showMessageDialog(scrollPane, "Please select row to delete");
			return;
		}
		int i = JOptionPane.showConfirmDialog(scrollPane, "Are you sure you want to delete?", "thông báo",
				JOptionPane.YES_NO_OPTION);
		if (i == JOptionPane.YES_OPTION) {

			int id = (int) table.getValueAt(table.getSelectedRow(), 0);

//			kiểm tra trạng thái của đơn xin phép

			if (!(table.getValueAt(table.getSelectedRow(), 8).toString().equals(str))) {
				JOptionPane.showMessageDialog(scrollPane, "This leave request cannot be deleted.");
				return;
			}

			var dao = new LeaveHRDao();
			dao.DeleteLR(id);
			showleaveRequest();
		}
	}

	protected void edit() {
		var str = role.getRoleName().equals("Leader") || role.getRoleName().equals("User") ? "Submitted" : "";
		if (!table.isRowSelected(table.getSelectedRow())) {
			JOptionPane.showMessageDialog(scrollPane, "Please select row to update");
			return;
		}
		if (!(table.getValueAt(table.getSelectedRow(), 8).toString().equals(str))) {
			JOptionPane.showMessageDialog(scrollPane, "You cannot edit this leave application.");
			return;
		}

		int id = (int) table.getValueAt(table.getSelectedRow(), 0);
//		var leaveType = table.getValueAt(table.getSelectedRow(), 4).toString();
		var fullName = table.getValueAt(table.getSelectedRow(), 3).toString();
		var lr = new LeaveRequests();
		lr.setLeaveRequestId(id);
		lr.setLeaveTypeId((int) table.getValueAt(table.getSelectedRow(), 1));
		lr.setStartDate((Date) table.getValueAt(table.getSelectedRow(), 5));
		lr.setEndDate((Date) table.getValueAt(table.getSelectedRow(), 6));
		lr.setReason(table.getValueAt(table.getSelectedRow(), 7).toString());
		lr.setApproverId((int) table.getValueAt(table.getSelectedRow(), 2));
		var ld = new LeaveDocumentDao().selectLeaveDocument(id);
		var lt = new LeaveTypeDao().selectLeaveTypeById(lr.getLeaveTypeId());
		AddLeaveRequest formEdit = new AddLeaveRequest();
		if (JformEdit == null || !JformEdit.isShowing()) {
			JformEdit = new JFrame("Edit Request");
			JformEdit.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			JformEdit.getContentPane().add(formEdit);
			formEdit.LoadDataEdit(user, lr, lt, fullName, role, ld, this);
			JformEdit.setSize(900, 600); // Set the width and height of the frame
			JformEdit.setResizable(false); // Disable resizing
			JformEdit.setLocationRelativeTo(null);
			JformEdit.setVisible(true);
		}

	}

	protected void add(Boolean b) {
		AddLeaveRequest formEdit = new AddLeaveRequest();
		if (JformEdit == null || !JformEdit.isShowing()) {

			JformEdit = new JFrame("Add Request");
			JformEdit.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			JformEdit.getContentPane().add(formEdit);
			formEdit.loadDataAdd(user, role, b, this);
			JformEdit.setSize(900, 600); // Set the width and height of the frame
			JformEdit.setResizable(false); // Disable resizing
			JformEdit.setLocationRelativeTo(null);
			JformEdit.setVisible(true);
		}
	}

	protected void cbbStatusApprovedActionPerformed(ActionEvent e) {
		var str = cbbStatusApproved.getSelectedItem().toString();
		txtSearch.setText("");

		var sorter = (DefaultRowSorter<?, ?>) table.getRowSorter();
		sorter.setRowFilter(RowFilter.regexFilter(str));
		sorter.setSortKeys(null);
	}

	protected void textFieldActionPerformed(ActionEvent e) {
		var str = txtSearch.getText();
		if (cbbStatusApproved.getSelectedItem().toString() == "") {
			var sorter = (DefaultRowSorter<?, ?>) table.getRowSorter();
			sorter.setRowFilter(RowFilter.regexFilter(str));
			sorter.setSortKeys(null);
			return;
		}
		var sorter = new TableRowSorter<>(table.getModel());
		table.setRowSorter(sorter);

		// Tạo bộ lọc cho trạng thái "Rejected"
		RowFilter<Object, Object> statusFilter = RowFilter.regexFilter(cbbStatusApproved.getSelectedItem().toString(),
				8);
		// Tạo bộ lọc cho chuỗi tìm kiếm
		RowFilter<Object, Object> searchFilter = RowFilter.regexFilter(str);

		// Kết hợp cả hai bộ lọc
		List<RowFilter<Object, Object>> filters = new ArrayList<>(2);
		filters.add(statusFilter);
		filters.add(searchFilter);
		RowFilter<Object, Object> combinedFilter = RowFilter.andFilter(filters);

		sorter.setRowFilter(combinedFilter);
		sorter.setSortKeys(null);
	}

}
