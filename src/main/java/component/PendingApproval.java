package component;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import dao.EmployeeDao;
import dao.LeaveHRDao;
import dao.LeaveRequestDao;
import dao.LeaveTypeDao;
import entity.Employees;
import entity.LeaveHistory;
import entity.Role;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultRowSorter;
import java.awt.event.MouseAdapter;
import java.awt.Color;
import java.awt.Font;

public class PendingApproval extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel panelTop;
	private JPanel panelbottom;
	private JScrollPane scrollPane;
	private JTable table;
	private Pagination pagination;
	private JComboBox comboBox;
	private Employees user;
	private JFrame processingLeaveRequestFrame;
	private ProcessingLeaveRequests pLR;
	private Employees employee = null;
	private Role role = null;
	private Integer pageNumber = 1; // Trang thu may
	private Integer rowOfPage = 10; // So dong mac dinh trong trang
	private Integer totalRow = 0; // so dong trong database
	private Double totalPage = 1.0; // Tong so trang

	/**
	 * Create the panel.
	 */

	public PendingApproval(Employees emp,  Role r) {
		setBackground(new Color(191, 246, 195));
		role = r;
		user = emp;
		setLayout(new BorderLayout(0, 0));

		panelTop = new JPanel();
		panelTop.setBackground(new Color(191, 246, 195));
		add(panelTop, BorderLayout.NORTH);

		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				comboBoxActionPerformed(e);
			}
		});
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 16));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"", "Pending", "Wathching"}));
		GroupLayout gl_panelTop = new GroupLayout(panelTop);
		gl_panelTop.setHorizontalGroup(
			gl_panelTop.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panelTop.createSequentialGroup()
					.addGap(638)
					.addComponent(comboBox, 0, 0, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panelTop.setVerticalGroup(
			gl_panelTop.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panelTop.createSequentialGroup()
					.addContainerGap()
					.addComponent(comboBox, GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
					.addGap(4))
		);
		panelTop.setLayout(gl_panelTop);

		panelbottom = new JPanel();
		panelbottom.setBackground(new Color(191, 246, 195));
		add(panelbottom, BorderLayout.SOUTH);

		pagination = new Pagination();
		pagination.setBackground(new Color(191, 246, 195));
		GroupLayout gl_panelbottom = new GroupLayout(panelbottom);
		gl_panelbottom.setHorizontalGroup(
			gl_panelbottom.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panelbottom.createSequentialGroup()
					.addGap(153)
					.addComponent(pagination, GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
					.addGap(126))
		);
		gl_panelbottom.setVerticalGroup(
			gl_panelbottom.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panelbottom.createSequentialGroup()
					.addContainerGap()
					.addComponent(pagination, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addContainerGap())
		);

		pagination.addFirstButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Xử lý sự kiện cho nút "First"
				var num = pagination.setPagination(e, pageNumber, totalPage);
				loadData(num);
				pageNumber = num;
			}
		});

		pagination.addPreviousButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Xử lý sự kiện cho nút "Previous"
				var num = pagination.setPagination(e, pageNumber, totalPage);
				loadData(num);
				pageNumber = num;
			}
		});

		pagination.addNextButtonListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Xử lý sự kiện cho nút "Next"
				var num = pagination.setPagination(e, pageNumber, totalPage);
				loadData(num);
				pageNumber = num;

			}
		});

		pagination.addLastButtonListener(new ActionListener() {
			// Xử lý sự kiện cho nút "Last"
			@Override
			public void actionPerformed(ActionEvent e) {
				var num = pagination.setPagination(e, pageNumber, totalPage);
				loadData(num);
				pageNumber = num;
			}
		});


		panelbottom.setLayout(gl_panelbottom);

		scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 16));
		JTableHeader header = table.getTableHeader();
	    header.setFont(new Font("Tahoma", Font.PLAIN, 16));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tableMouseClicked(e);
			}
		});
		scrollPane.setViewportView(table);
		showAllRequest();

//		pLR.addApproveActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				pLR.UpdateApprovateStatus(e);
//				showAllRequest()
//			}
//		});
	}



	public JFrame getProcessingLeaveRequestFrame() {
		return processingLeaveRequestFrame;
	}



	public void showAllRequest() {
//	user = emp;
		var model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // All cells are non-editable
			}
		};
		// Create columns
		model.addColumn("IdHR");
		model.addColumn("IDEmp");
		model.addColumn("EmployeeName");
		model.addColumn("LeaveType");
		model.addColumn("StartDate");
		model.addColumn("EndDate");
		model.addColumn("Reason");
		model.addColumn("ApproverID");
		model.addColumn("SubmissionDate");
		model.addColumn("Status");

		// Add rows
		var daoLT = new LeaveTypeDao();
		var dao = new LeaveRequestDao();
		var daoEmp = new EmployeeDao();

		totalRow = dao.countLRForLeader(user.getEmployeeID(), role); // lay tong so dong
		totalPage = Math.ceil(totalRow.doubleValue() / rowOfPage.doubleValue()); // tinh so trang
		
		dao.selLRequestForLeader(1, rowOfPage, user.getEmployeeID(), role).stream().forEach(lr -> {
		employee = daoEmp.getEmp(lr.getEmployeeId());
				if (employee.getEmployeeID() != user.getEmployeeID()) {

					model.addRow(new Object[] { lr.getLeaveRequestId(), employee.getEmployeeID(), employee.getEmployeeName(),
							daoLT.selectLeaveTypeByIdLr(lr.getLeaveRequestId()).getLeaveTypeName()
							,lr.getStartDate(), lr.getEndDate(), lr.getReason(), lr.getApproverId(),
							lr.getSubmissionDate(), lr.getStatusLR() });
				}
			
		});
		table.setModel(model);
		table.getColumn("IdHR").setMinWidth(0);
		table.getColumn("IdHR").setMaxWidth(0);
		table.getColumn("IdHR").setWidth(0);
		table.getColumn("IDEmp").setMinWidth(0);
		table.getColumn("IDEmp").setMaxWidth(0);
		table.getColumn("IDEmp").setWidth(0);

//		rowSorter = new TableRowSorter<>(model);
//		table.setRowSorter(rowSorter);

		TableColumn column = table.getColumnModel().getColumn(1);
		column.setPreferredWidth(150);
	}

	protected void tableMouseClicked(MouseEvent e) {
		var str = role.getRoleName().equals("Admin") ? "Viewed by HR" : "Viewed by Lead";
		if (e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
			int selectedRow = table.getSelectedRow();
			if (selectedRow != -1) {
				int modelRow = table.convertRowIndexToModel(selectedRow);
				var lh = new LeaveHistory();

				lh.setHistoryId(Integer.parseInt(table.getModel().getValueAt(modelRow, 0).toString()));
				lh.setReason(table.getModel().getValueAt(modelRow, 6).toString());
				
				lh.setApproverID(Integer.parseInt(table.getModel().getValueAt(modelRow, 7).toString()));
				lh.setEmployeeId(Integer.parseInt(table.getModel().getValueAt(modelRow, 1).toString()));
				lh.setEndDate((Date) table.getModel().getValueAt(modelRow, 5));
				lh.setStartDate((Date) table.getModel().getValueAt(modelRow, 4));
				lh.setLeaveType(table.getModel().getValueAt(modelRow, 3).toString());
				lh.setSubmissionDate((Date) table.getModel().getValueAt(modelRow, 8));
				lh.setApprovalStatus(str);
				String employeeName = table.getModel().getValueAt(modelRow, 2).toString();
				
				// Update the table status to "Watching"
				table.getModel().setValueAt(str, modelRow, 9);

				// Update the status in the database
				var dao = new LeaveHRDao();
				dao.UpdateApproveStatus(str, user.getEmployeeID(), lh.getHistoryId());

				ProcessingLeaveRequests form = new ProcessingLeaveRequests(lh, employeeName, this, user, role);
//		            form.setFormData(employeeName, leaveType, startDate, endDate, reason, approverID, submissionDate, status);

				// Check if the frame is already open
				if (processingLeaveRequestFrame == null || !processingLeaveRequestFrame.isShowing()) {
					processingLeaveRequestFrame = new JFrame("Processing Leave Request");
					processingLeaveRequestFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					processingLeaveRequestFrame.getContentPane().add(form);
					processingLeaveRequestFrame.setSize(580, 300); // Set the width and height of the frame
					processingLeaveRequestFrame.setResizable(false); // Disable resizing
					processingLeaveRequestFrame.setLocationRelativeTo(null);
					processingLeaveRequestFrame.setVisible(true);
				} else {
					// Bring the existing frame to the front
					processingLeaveRequestFrame.toFront();
					processingLeaveRequestFrame.repaint();
				}
			}
		}
	}

	public void loadData(Integer pageNumber) {
		// TODO Auto-generated method stub
		var model = (DefaultTableModel) table.getModel();
		model.setRowCount(0); // xoa het du lieu con ben trong
		var dao = new LeaveRequestDao();
		totalRow = dao.countLRForLeader(user.getEmployeeID(), role); // lay tong so dong
		JOptionPane.showMessageDialog(null, totalRow);
		totalPage = Math.ceil(totalRow.doubleValue() / rowOfPage.doubleValue()); // tinh so trang

		String lblStaPage = " page " + pageNumber + " of " + totalPage.intValue();
		var daoLT = new LeaveTypeDao();
		var daoEmp = new EmployeeDao();
		dao.selLRequestForLeader(1, rowOfPage, user.getEmployeeID(), role).stream().forEach(lr -> {
		employee = daoEmp.getEmp(lr.getEmployeeId());
				if (employee.getEmployeeID() != user.getEmployeeID()) {
					model.addRow(new Object[] { lr.getLeaveRequestId(),employee.getEmployeeID(), employee.getEmployeeName(),
							daoLT.selectLeaveTypeByIdLr(lr.getLeaveRequestId()).getLeaveTypeName(),
							lr.getStartDate(), lr.getEndDate(), lr.getReason(), lr.getApproverId(),
							lr.getSubmissionDate(), lr.getStatusLR() });
				}
			
		});

		table.setModel(model);
		table.getColumn("IdHR").setMinWidth(0);
		table.getColumn("IdHR").setMaxWidth(0);
		table.getColumn("IdHR").setWidth(0);
		table.getColumn("IDEmp").setMinWidth(0);
		table.getColumn("IDEmp").setMaxWidth(0);
		table.getColumn("IDEmp").setWidth(0);

	}

	public void searchTable(String str) {
		var sorter = (DefaultRowSorter<?, ?>) table.getRowSorter();
		sorter.setRowFilter(RowFilter.regexFilter(str));
		sorter.setSortKeys(null);
	}

	public void updateStatus(int row, String newStatus) {
		int modelRow = table.convertRowIndexToModel(row);
		table.getModel().setValueAt(newStatus, modelRow, 7);
	}

	protected void comboBoxActionPerformed(ActionEvent e) {
		var str = comboBox.getSelectedItem().toString();

		var sorter = (DefaultRowSorter<?, ?>) table.getRowSorter();
		sorter.setRowFilter(RowFilter.regexFilter(str));
		sorter.setSortKeys(null);
	}
}
