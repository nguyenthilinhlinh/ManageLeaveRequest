package component;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;

import dao.EmployeeDao;
import dao.LeaveHRDao;
import dao.LeaveRequestDao;
import dao.LeaveTypeDao;
import entity.Employees;
import entity.Role;
import javax.swing.border.EtchedBorder;

public class Processed extends JPanel {

	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;
	private JTable table;
	private JPanel panelSearch;
	private JPanel panelButton;
	private JTextField txtSearch;
	private JComboBox cbbLeaveType;
	private Employees employee = null;
	private Employees user = null;
	private Integer pageNumber = 1; // Trang thu may
	private Integer rowOfPage = 10; // So dong mac dinh trong trang
	private Integer totalRow = 0; // so dong trong database
	private Double totalPage = 1.0; // Tong so trang
	private Pagination pagination;
	private Role role = null;

	/**
	 * Create the panel.
	 */
	public Processed(Employees emp, Role r) {
		setBackground(new Color(191, 246, 195));
		role = r;
		user = emp;
		setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 16));
		scrollPane.setViewportView(table);
		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("Tahoma", Font.PLAIN, 16));

		panelSearch = new JPanel();
		panelSearch.setBackground(new Color(191, 246, 195));
		add(panelSearch, BorderLayout.NORTH);

		txtSearch = new JTextField();
		txtSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtSearchActionPerformed(e);
			}
		});
		txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtSearch.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"<html><div style=\"font-size:16px\">Search</html></div>", TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		txtSearch.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		txtSearch.setAlignmentX(Component.RIGHT_ALIGNMENT);
		txtSearch.setColumns(10);

		cbbLeaveType = new JComboBox();
		cbbLeaveType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cbbLeaveTypeActionPerformed(e);
			}
		});
		cbbLeaveType.setFont(new Font("Tahoma", Font.PLAIN, 16));
		cbbLeaveType.setModel(new DefaultComboBoxModel(new String[] {"", "Approved", "Rejected"}));
		GroupLayout gl_panelSearch = new GroupLayout(panelSearch);
		gl_panelSearch.setHorizontalGroup(gl_panelSearch.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelSearch.createSequentialGroup().addContainerGap(703, Short.MAX_VALUE)
						.addComponent(cbbLeaveType, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, 247, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_panelSearch.setVerticalGroup(gl_panelSearch.createParallelGroup(Alignment.TRAILING)
				.addComponent(txtSearch, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 48, Short.MAX_VALUE)
				.addComponent(cbbLeaveType, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE));
		panelSearch.setLayout(gl_panelSearch);

		panelButton = new JPanel();
		panelButton.setBackground(new Color(191, 246, 195));
		add(panelButton, BorderLayout.SOUTH);

		pagination = new Pagination();
		pagination.setBackground(new Color(191, 246, 195));
		GroupLayout gl_panelButton = new GroupLayout(panelButton);
		gl_panelButton.setHorizontalGroup(
			gl_panelButton.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelButton.createSequentialGroup()
					.addGap(222)
					.addComponent(pagination, GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE)
					.addGap(222))
		);
		gl_panelButton.setVerticalGroup(
			gl_panelButton.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelButton.createSequentialGroup()
					.addContainerGap()
					.addComponent(pagination, GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
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

		panelButton.setLayout(gl_panelButton);

		showHistoryRequest();
	}

	public void showHistoryRequest() {
        var model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // All cells are non-editable
            }
        };

        model.addColumn("LeaveRequestID");
        model.addColumn("EmployeeName");
        model.addColumn("LeaveType");
        model.addColumn("StartDate");
        model.addColumn("EndDate");
        model.addColumn("Reason");
        model.addColumn("SubmissionDate");
        model.addColumn("ApproverDate");
        model.addColumn("Status");
        
        var daoLH = new LeaveRequestDao();
        var daoEmp = new EmployeeDao();
        var daoLT = new LeaveTypeDao();
        
        totalRow = daoLH.countLHForLeader(user.getEmployeeID(), role); // lay tong so dong
        totalPage = Math.ceil(totalRow.doubleValue() / rowOfPage.doubleValue()); // tinh so trang
		daoLH.selLHistoryForLeader(1,rowOfPage, user.getEmployeeID(), role).stream().forEach(lr -> {
			employee = daoEmp.getEmp(lr.getEmployeeId());
//			if (employee.getEmployeeID() != user.getEmployeeID()) {
				
            model.addRow(new Object[]{
            		
                    lr.getLeaveRequestId(), employee.getEmployeeName(),
                    daoLT.selectLeaveTypeByIdLr(lr.getLeaveRequestId()).getLeaveTypeName(),
                    lr.getStartDate(),
                    lr.getEndDate(), lr.getReason(),
                    lr.getSubmissionDate(),lr.getApprovalDate(),lr.getStatusLR()
            });
//			}
        });
		table.setRowSorter( new TableRowSorter<>(model));

        table.setModel(model);
        table.getColumn("LeaveRequestID").setMinWidth(0);
		table.getColumn("LeaveRequestID").setMaxWidth(0);
		table.getColumn("LeaveRequestID").setWidth(0);
    }
	
	
    public void loadData(Integer pageNumber) {
    	JOptionPane.showMessageDialog(null, pageNumber);
		// TODO Auto-generated method stub
		var model = (DefaultTableModel) table.getModel();
		model.setRowCount(0); // xoa het du lieu con ben trong
		
		var daoLT = new LeaveTypeDao();
		var dao = new LeaveRequestDao();
		totalRow = dao.countLHForLeader(user.getEmployeeID(), role); // lay tong so dong
		totalPage = Math.ceil(totalRow.doubleValue() / rowOfPage.doubleValue()); // tinh so trang
		

		String lblStaPage = " page " + pageNumber + " of " + totalPage.intValue();
		var daoEmp = new EmployeeDao();
		dao.selLHistoryForLeader(pageNumber,rowOfPage, user.getEmployeeID(), role).stream().forEach(lr -> {
			employee = daoEmp.getEmp(lr.getEmployeeId());
//			if (employee.getEmployeeID() != user.getEmployeeID()) {
            model.addRow(new Object[]{
            		lr.getLeaveRequestId(), employee.getEmployeeName(),
            		daoLT.selectLeaveTypeByIdLr(lr.getLeaveRequestId()).getLeaveTypeName(), lr.getStartDate(),
                    lr.getEndDate(), lr.getReason(),
                    lr.getSubmissionDate(),lr.getApprovalDate(),lr.getStatusLR()
            });
//			}
        });
		
		table.setModel(model);
		table.getColumn("LeaveRequestID").setMinWidth(0);
		table.getColumn("LeaveRequestID").setMaxWidth(0);
		table.getColumn("LeaveRequestID").setWidth(0);
	}
    
    public void searchTable(String str) {
		var sorter = (DefaultRowSorter<?,?>)table.getRowSorter();
		sorter.setRowFilter(RowFilter.regexFilter(str));
		sorter.setSortKeys(null);
	}
	protected void comboBoxActionPerformed(ActionEvent e) {
		var str = cbbLeaveType.getSelectedItem().toString(); 
		var sorter = (DefaultRowSorter<?,?>)table.getRowSorter();
		sorter.setRowFilter(RowFilter.regexFilter(str));
		sorter.setSortKeys(null);
	}
	protected void textFieldActionPerformed(ActionEvent e) {
		var str = txtSearch.getText();
		if(cbbLeaveType.getSelectedItem().toString() == "") {
			var sorter = (DefaultRowSorter<?,?>)table.getRowSorter();
			sorter.setRowFilter(RowFilter.regexFilter(str));
			sorter.setSortKeys(null);
			return;
		}
		var sorter = new TableRowSorter<>(table.getModel());
//		table.setRowSorter(sorter);
		sorter.setRowFilter(RowFilter.regexFilter(str));
		sorter.setSortKeys(null);
		
	}
	protected void cbbLeaveTypeActionPerformed(ActionEvent e) {
		var str = cbbLeaveType.getSelectedItem().toString();
		txtSearch.setText("");

		var sorter = (DefaultRowSorter<?, ?>) table.getRowSorter();
		sorter.setRowFilter(RowFilter.regexFilter(str));
		sorter.setSortKeys(null);
	}
	protected void txtSearchActionPerformed(ActionEvent e) {
		var str = txtSearch.getText();
		if (cbbLeaveType.getSelectedItem().toString() == "") {
			var sorter = (DefaultRowSorter<?, ?>) table.getRowSorter();
			sorter.setRowFilter(RowFilter.regexFilter(str));
			sorter.setSortKeys(null);
			return;
		}
		var sorter = new TableRowSorter<>(table.getModel());
		table.setRowSorter(sorter);

		// Tạo bộ lọc cho trạng thái "Rejected"
		RowFilter<Object, Object> statusFilter = RowFilter.regexFilter(cbbLeaveType.getSelectedItem().toString(),
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
