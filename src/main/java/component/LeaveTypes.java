package component;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import dao.LeaveTypeDao;
import entity.LeaveType;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.MatteBorder;
import java.awt.Font;

public class LeaveTypes extends JPanel {

	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane		;
	private JTextField txtLeaveTypeID;
	private JTextField txtLeaveTypeName;
	private JTextField txtLeaveTypeDescription;
	private JTextField txtLeaveDaysPerYear;
	private JTable table;
	private JButton btnInsert;
	private JButton btnUpdate;
	private JLabel lblLeaveTypeID;
	private JLabel lblLeavetypename;
	private JLabel lblLeavetyppedescription;
	private JLabel lblLeavedaysperyear;
	private JButton btnDelete;

	/**
	 * Create the panel.
	 */
	public LeaveTypes() {
		setBackground(new Color(191, 246, 195));
		setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 1024, 494);
		add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		// Set the font for the table cells
		table.setFont(new Font("Tahoma", Font.PLAIN, 16));
		table.setRowHeight(24);
		// Set the font for the table header
		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("Tahoma", Font.BOLD, 16));

		txtLeaveTypeID = new JTextField();
		txtLeaveTypeID.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtLeaveTypeID.setBounds(127, 516, 59, 49);
		txtLeaveTypeID.setBackground(new Color(240, 240, 240));
		txtLeaveTypeID.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 255)));
		add(txtLeaveTypeID);
		txtLeaveTypeID.setColumns(10);

		txtLeaveTypeName = new JTextField();
		txtLeaveTypeName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtLeaveTypeName.setBounds(341, 518, 269, 49);
		txtLeaveTypeName.setBackground(new Color(240, 240, 240));
		txtLeaveTypeName.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 255)));
		add(txtLeaveTypeName);

		txtLeaveTypeDescription = new JTextField();
		txtLeaveTypeDescription.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtLeaveTypeDescription.setBounds(196, 576, 642, 43);
		txtLeaveTypeDescription.setBackground(new Color(240, 240, 240));
		txtLeaveTypeDescription.setColumns(10);
		txtLeaveTypeDescription.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 255)));
		add(txtLeaveTypeDescription);

		txtLeaveDaysPerYear = new JTextField();
		txtLeaveDaysPerYear.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtLeaveDaysPerYear.setBounds(779, 516, 59, 49);
		txtLeaveDaysPerYear.setBackground(new Color(240, 240, 240));
		txtLeaveDaysPerYear.setColumns(10);
		txtLeaveDaysPerYear.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(0, 0, 255)));
		add(txtLeaveDaysPerYear);

		btnInsert = new JButton("Insert");
		btnInsert.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnInsert.setBounds(853, 516, 181, 28);
		btnInsert.setMnemonic('I');
		add(btnInsert);

		btnInsert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				insertLeaveType();
			}
		});

		btnUpdate = new JButton("Update");
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnUpdate.setBounds(853, 555, 181, 28);
		btnUpdate.setMnemonic('u');
		add(btnUpdate);
		btnUpdate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateLeaveType();
			}
		});

		lblLeaveTypeID = new JLabel("LeaveTypeID:");
		lblLeaveTypeID.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblLeaveTypeID.setBounds(10, 515, 107, 49);
		add(lblLeaveTypeID);

		lblLeavetypename = new JLabel("LeaveTypeName:");
		lblLeavetypename.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblLeavetypename.setBounds(196, 515, 135, 49);
		add(lblLeavetypename);

		lblLeavetyppedescription = new JLabel("LeaveTyppeDescription:");
		lblLeavetyppedescription.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblLeavetyppedescription.setBounds(10, 576, 176, 43);
		add(lblLeavetyppedescription);

		lblLeavedaysperyear = new JLabel("LeaveDaysPerYear:");
		lblLeavedaysperyear.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblLeavedaysperyear.setBounds(620, 515, 149, 49);
		add(lblLeavedaysperyear);

		btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnDelete.setBounds(853, 591, 181, 28);
		btnDelete.setMnemonic('u');
		add(btnDelete);
		loadData();

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent event) {
		        if (!event.getValueIsAdjusting()) {
		            int selectedRow = table.getSelectedRow();
		            if (selectedRow != -1) {
		                // If a valid row is selected, populate the text fields with the selected row's data
		                txtLeaveTypeID.setText(table.getValueAt(selectedRow, 0).toString());
		                txtLeaveTypeName.setText(table.getValueAt(selectedRow, 1).toString());
		                txtLeaveTypeDescription.setText(table.getValueAt(selectedRow, 2).toString());
		                txtLeaveDaysPerYear.setText(table.getValueAt(selectedRow, 3).toString());
		            } else {
		                // If no row is selected, reset the form fields
		                txtLeaveTypeID.setText("");
		                txtLeaveTypeName.setText("");
		                txtLeaveTypeDescription.setText("");
		                txtLeaveDaysPerYear.setText("");
		            }
		        }
		    }
		});
	}

	private void loadData() {
		var dao = new LeaveTypeDao();
		var leaveTypes = dao.selectAllLeaveTypes();
		var model = new DefaultTableModel(
				new Object[] { "LeaveTypeID", "LeaveTypeName", "LeaveTypeDescription", "LeaveDaysPerYear" }, 0);

		for (LeaveType lt : leaveTypes) {
			model.addRow(new Object[] { lt.getLeaveTypeID(), lt.getLeaveTypeName(), lt.getLeaveTypeDescription(),
					lt.getLeaveDaysPerYear() });
		}

		table.setModel(model);

		// Adjust column widths
		TableColumn column = null;
		for (int i = 0; i < table.getColumnCount(); i++) {
			column = table.getColumnModel().getColumn(i);
			switch (i) {
			case 0: // LeaveTypeID
				column.setPreferredWidth(50);
				break;
			case 1: // LeaveTypeName
				column.setPreferredWidth(200);
				break;
			case 2: // LeaveTypeDescription
				column.setPreferredWidth(300);
				break;
			case 3: // LeaveDaysPerYear
				column.setPreferredWidth(100);
				break;
			}
		}
	}

	private void insertLeaveType() {


		String leaveTypeIDText = txtLeaveTypeID.getText();
		String leaveTypeName = txtLeaveTypeName.getText();
		String leaveTypeDescription = txtLeaveTypeDescription.getText();
		String leaveDaysPerYearText = txtLeaveDaysPerYear.getText();

		if (leaveTypeIDText.isEmpty() || leaveTypeName.isEmpty() || leaveTypeDescription.isEmpty()
		        || leaveDaysPerYearText.isEmpty()) {
		    JOptionPane.showMessageDialog(null, "All fields must be filled out.", "Input Error", JOptionPane.ERROR_MESSAGE);
		    return;
		}

		int leaveTypeID;
		int leaveDaysPerYear;
		try {
		    leaveTypeID = Integer.parseInt(leaveTypeIDText);
		    leaveDaysPerYear = Integer.parseInt(leaveDaysPerYearText);
		} catch (NumberFormatException e) {
		    JOptionPane.showMessageDialog(null, "LeaveTypeID and LeaveDaysPerYear must be valid integers.", "Input Error", JOptionPane.ERROR_MESSAGE);
		    return;
		}
		
		 LeaveTypeDao dao = new LeaveTypeDao();

	        if (dao.leaveTypeNameExists(leaveTypeName)) {
	            JOptionPane.showMessageDialog(null, "This type of leave is now available.", "Input Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

		// Create LeaveType object
		LeaveType leaveType = new LeaveType();
		leaveType.setLeaveTypeID(leaveTypeID);
		leaveType.setLeaveTypeName(leaveTypeName);
		leaveType.setLeaveTypeDescription(leaveTypeDescription);
		leaveType.setLeaveDaysPerYear(leaveDaysPerYear);
	

		// Insert into database
		boolean success = dao.insertLeaveType(leaveType);

		if (success) {
		    loadData();
		    txtLeaveTypeID.setText("");
		    txtLeaveTypeName.setText("");
		    txtLeaveTypeDescription.setText("");
		    txtLeaveDaysPerYear.setText("");
		} else {
		    JOptionPane.showMessageDialog(null, "Failed to insert LeaveType.", "Database Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void updateLeaveType() {
        String leaveTypeIDText = txtLeaveTypeID.getText();
        String leaveTypeName = txtLeaveTypeName.getText();
        String leaveTypeDescription = txtLeaveTypeDescription.getText();
        String leaveDaysPerYearText = txtLeaveDaysPerYear.getText();

        if (leaveTypeIDText.isEmpty() || leaveTypeName.isEmpty() || leaveTypeDescription.isEmpty()
                || leaveDaysPerYearText.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields must be filled out.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int leaveTypeID;
        int leaveDaysPerYear;
        try {
            leaveTypeID = Integer.parseInt(leaveTypeIDText);
            leaveDaysPerYear = Integer.parseInt(leaveDaysPerYearText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "LeaveTypeID and LeaveDaysPerYear must be valid integers.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create LeaveType object
        LeaveType leaveType = new LeaveType();
        leaveType.setLeaveTypeID(leaveTypeID);
        leaveType.setLeaveTypeName(leaveTypeName);
        leaveType.setLeaveTypeDescription(leaveTypeDescription);
        leaveType.setLeaveDaysPerYear(leaveDaysPerYear);

        // Update in database
        LeaveTypeDao dao = new LeaveTypeDao();
        boolean success = dao.updateLeaveType(leaveType);

        if (success) {
            loadData();
            txtLeaveTypeID.setText("");
            txtLeaveTypeName.setText("");
            txtLeaveTypeDescription.setText("");
            txtLeaveDaysPerYear.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "Failed to update LeaveType.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
