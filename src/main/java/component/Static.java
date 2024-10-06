package component;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import com.toedter.calendar.JCalendar;

import constants.UIConstants;
import dao.EmployeeDao;
import dao.LeaveRequestDao;
import dao.LeaveTypeDao;
import entity.Employees;
import entity.LeaveRequests;
import entity.Role;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Component;

import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.beans.VetoableChangeListener;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

public class Static extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JPanel todayPanel;
	private JPanel weekPanel;
	private JPanel monthPanel;
	private JPanel panel_1;
	private JPanel calendarPanel;
	private JCalendar calendar;
	private JPanel holidaysPanel;
	private JTextArea holidaysArea;
	private JLabel holidaysLabel;
	private JLabel todayCountLabel;
	private JButton todayDetailsButton;
	private JLabel weekCountLabel;
	private JButton weekDetailsButton;
	private JLabel monthCountLabel;
	private JButton monthDetailsButton;
	 
	private Role role;
	private List<LeaveRequests> leaveWeek;
	private List<LeaveRequests> leaveday;
	private List<LeaveRequests> leaveMonth;
	/**
	 * Create the panel.
	 */
	public Static() {
		setBackground(new Color(191, 246, 195));
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(new Color(191, 246, 195));
		panel.setBounds(10, 11, 1025, 161);
		add(panel);
		panel.setLayout(null);
		
		todayPanel = new JPanel((LayoutManager) null);
		todayPanel.setBackground(new Color(240, 240, 240));
		todayPanel.setBorder(new TitledBorder(null, "<html><div style=\"font-size:16px\">Employees On Leave : TODAY", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		todayPanel.setBounds(10, 0, 316, 118);
		panel.add(todayPanel);
		todayPanel.setLayout(new BorderLayout());
		
		todayCountLabel = new JLabel("1", SwingConstants.CENTER);
		todayCountLabel.setFont(new Font("Arial", Font.BOLD, 36));
		todayPanel.add(todayCountLabel, BorderLayout.CENTER);
		
		todayDetailsButton = new JButton("VIEW DETAILS");
		todayDetailsButton.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 16));
		todayDetailsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				todayDetailsButtonActionPerformed(e);
			}
		});
		todayPanel.add(todayDetailsButton, BorderLayout.SOUTH);
		
		weekPanel = new JPanel((LayoutManager) null);
		weekPanel.setBorder(new TitledBorder(null, "<html><div style=\"font-size:16px\">Employees On Leave : THIS WEEK", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		weekPanel.setBounds(356, 0, 316, 118);
		panel.add(weekPanel);
		weekPanel.setLayout(new BorderLayout());
		
		weekCountLabel = new JLabel("1", SwingConstants.CENTER);
		weekCountLabel.setFont(new Font("Arial", Font.BOLD, 36));
		weekPanel.add(weekCountLabel, BorderLayout.CENTER);
		
		weekDetailsButton = new JButton("VIEW DETAILS");
		weekDetailsButton.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 16));
		weekDetailsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				weekDetailsButtonActionPerformed(e);
			}
		});
		weekPanel.add(weekDetailsButton, BorderLayout.SOUTH);
		
		monthPanel = new JPanel((LayoutManager) null);
		monthPanel.setBorder(new TitledBorder(null, "<html><div style=\"font-size:16px\">Employees On Leave : THIS MONTH", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		monthPanel.setBounds(699, 0, 316, 118);
		panel.add(monthPanel);
		monthPanel.setLayout(new BorderLayout());
		
		monthCountLabel = new JLabel("3", SwingConstants.CENTER);
		monthCountLabel.setFont(new Font("Arial", Font.BOLD, 36));
		monthPanel.add(monthCountLabel, BorderLayout.CENTER);
		
		monthDetailsButton = new JButton("VIEW DETAILS");
		monthDetailsButton.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 16));
		monthDetailsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				monthDetailsButtonActionPerformed(e);
			}
		});
		monthPanel.add(monthDetailsButton, BorderLayout.SOUTH);
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(191, 246, 195));
		panel_1.setBounds(10, 183, 1025, 463);
		add(panel_1);
		panel_1.setLayout(null);
		
		calendarPanel = new JPanel((LayoutManager) null);
		calendarPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		calendarPanel.setBounds(10, 11, 582, 437);
		panel_1.add(calendarPanel);
		calendarPanel.setLayout(new BorderLayout());
		
		calendar = new JCalendar();
		calendar.getMonthChooser().getSpinner().setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 16));
		calendar.getMonthChooser().getComboBox().setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 16));
		calendar.getYearChooser().getSpinner().setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 16));
		calendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				calendarPropertyChange(evt);
			}
		});
	
		calendarPanel.add(calendar, BorderLayout.CENTER);
		
		holidaysPanel = new JPanel((LayoutManager) null);
		holidaysPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		holidaysPanel.setBounds(602, 11, 413, 437);
		panel_1.add(holidaysPanel);
		holidaysPanel.setLayout(new BorderLayout());
		
		holidaysArea = new JTextArea("Fri 2020/10/23 - Wed 2020/10/28\nDashain\nSat 2020/11/14 - Mon 2020/11/16\nTihar\nFri 2021/01/01\nEnglish New Year\nThu 2021/03/11\nShivaratri\nSun 2021/03/28\nHoli");
		holidaysArea.setFont(new Font("Courier New", Font.PLAIN, 16));
		holidaysArea.setEditable(false);
		holidaysPanel.add(holidaysArea, BorderLayout.CENTER);
		
		holidaysLabel = new JLabel("Holidays");
		holidaysLabel.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 24));
		holidaysPanel.add(holidaysLabel, BorderLayout.NORTH);
	}
	

	
	public void loaddata(Role r,Employees emp) {		
		
		var dao = new LeaveRequestDao();
		var totalDay = dao.countAllLeave("day");
		var totalWeek = dao.countAllLeave("week");
		var totalMonth = dao.countAllLeave("month");
		todayCountLabel.setText(totalDay.toString());
		weekCountLabel.setText(totalWeek.toString());
		monthCountLabel.setText(totalMonth.toString());
		
		if (totalDay == 0) {
	        todayDetailsButton.setVisible(false);
	    }
	    
	    if (totalWeek == 0) {
	        weekDetailsButton.setVisible(false);
	    }
	    
	    if (totalMonth == 0) {
	        monthDetailsButton.setVisible(false);
	    }
		
	}
	
	
	protected void todayDetailsButtonActionPerformed(ActionEvent e) {
		var dao = new LeaveRequestDao();
		var source = (JButton) e.getSource();
        showEmployeeDetails(source, "day");
	}
	protected void weekDetailsButtonActionPerformed(ActionEvent e) {
		
		var dao = new LeaveRequestDao();
		var source = (JButton) e.getSource();
        showEmployeeDetails(source, "week");
		
	}
	protected void monthDetailsButtonActionPerformed(ActionEvent e) {
		var dao = new LeaveRequestDao();
		var source = (JButton) e.getSource();
        showEmployeeDetails(source, "month");
	}
	
	private void showEmployeeDetails(JButton button, String str) {
	  
	    JTable table = new StyledTable();
	    JScrollPane scrollPane = new JScrollPane(table);
	    
	    var model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // All cells are non-editable
			}
		};
		
		model.addColumn("IDEmp");
		model.addColumn("LeaveTypeId");
		model.addColumn("EmployeeName");
		model.addColumn("LeaveType");
		model.addColumn("StartDate");
		model.addColumn("EndDate");
		model.addColumn("Reason");
		var daoLt = new LeaveTypeDao();
		var daoEmp = new EmployeeDao();
		var dao = new LeaveRequestDao();
		dao.selAllLeave(str).stream().forEach(e -> model.addRow(new Object[] {
				e.getEmployeeId(),
				e.getLeaveTypeId(),
				daoEmp.getEmp(e.getEmployeeId()).getEmployeeName(),
				daoLt.selectLeaveTypeById(e.getLeaveTypeId()).getLeaveTypeName(),
				e.getStartDate(),
				e.getEndDate(),
				e.getReason()
				}));
		
		table.setModel(model);
		table.getColumn("IDEmp").setMinWidth(0);
		table.getColumn("IDEmp").setMaxWidth(0);
		table.getColumn("IDEmp").setWidth(0);
		table.getColumn("LeaveTypeId").setMinWidth(0);
		table.getColumn("LeaveTypeId").setMaxWidth(0);
		table.getColumn("LeaveTypeId").setWidth(0);
	    
	    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Tắt chế độ tự động điều chỉnh mặc định
	    adjustColumnWidths(table); // Điều chỉnh chiều rộng của các cột
	    table.setPreferredScrollableViewportSize(table.getPreferredSize());

	   
	    JPopupMenu popupMenu = new JPopupMenu();
	    popupMenu.add(scrollPane);
	    
	    // Tính toán vị trí để hiển thị JPopupMenu ở giữa nút
	    int x = button.getWidth() / 2 - scrollPane.getPreferredSize().width / 2;
	    int y = button.getHeight() / 2 - scrollPane.getPreferredSize().height / 2;

	    // Hiển thị JPopupMenu tại vị trí đã tính toán
	    popupMenu.show(button, x, 0);
	}
	
	private void adjustColumnWidths(JTable table) {
	    for (int column = 0; column < table.getColumnCount(); column++) {
	        TableColumn tableColumn = table.getColumnModel().getColumn(column);
	        int preferredWidth = tableColumn.getMinWidth();
	        int maxWidth = tableColumn.getMaxWidth();

	        for (int row = 0; row < table.getRowCount(); row++) {
	            TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
	            Component c = table.prepareRenderer(cellRenderer, row, column);
	            int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
	            preferredWidth = Math.max(preferredWidth, width);

	            // Ở đây bạn có thể giới hạn chiều rộng tối đa của cột
	            if (preferredWidth >= maxWidth) {
	                preferredWidth = maxWidth;
	                break;
	            } 
	        }

	        tableColumn.setPreferredWidth(preferredWidth);
	    }
	}
	
	protected void calendarPropertyChange(PropertyChangeEvent evt) {
		StringBuilder sb = new StringBuilder();
	var sqlDate = new java.util.Date(calendar.getDate().getTime());
	var dao = new LeaveRequestDao();
	var list = dao.selAllLeaveByDate(sqlDate);
	if(list.isEmpty()) {
		holidaysArea.setText("No employees asked for leave today.");
		return;
	}
	list.stream().forEach(
			e -> {
				var emp = new EmployeeDao().getEmp(e.getEmployeeId());
				
				sb.append(emp.getEmployeeName() + " ").append(e.getStartDate() + " - ").append(e.getEndDate() + "\n\n");
				
			} 
			);
	holidaysArea.setText(sb.toString());
	
	}
}
