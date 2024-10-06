package component;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import constants.UIConstants;
import dao.DepartmentDao;
import dao.EmployeeDao;
import dao.LeaveDocumentDao;
import dao.LeaveRequestDao;
import dao.LeaveTypeDao;
import dao.NotificationDao;
import entity.Employees;
import entity.LeaveDocument;
import entity.LeaveRequests;
import entity.LeaveType;
import entity.Notification;
import entity.Role;
import helper.DateUtils;

import entity.LeaveDuration;

public class AddLeaveRequest extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel panelForm;
	private JPanel panel;
	private JLabel lblFullName;
	private JTextField txtfullName;
	private JLabel lblDepartment;
	private JComboBox<String> cmbDepartment;
	private JComboBox<String> cmbLeaveType;
	private JTextArea txtReason;
	private JDateChooser startDateChooser;
	private JDateChooser endDateChooser;

	private final JPanel panelbutton;
	private final JComboBox<String> cmbApprover;
	private final JButton btnChooseFile;
	private final JLabel lblNewLabel;
	private final JComboBox<String> cbbEmpName;
	private final JLabel lblAdditionalFiles;

	private List<Employees> listEmp = null;
	private Employees user;
	private Employees empReceiver;
	private Employees empOldReceiver;
	private List<LeaveType> listLT;
	private String statusLR;
	private VacationRequest va;
	private LeaveRequests oldLR;
	private LeaveType oldLT;
	private LeaveType newLT;

	private String fileNew = null;
	private String fileOld = null;
	private String dirNew = null;
	private String dirOld = null;
	private LeaveDocument oldLD;
	private JLabel lblImage;
	private JButton btnUpdate;
	private JButton btnSubmit;
	private JComboBox<LeaveDuration> cmbDuration;
	private final JLabel lblDuration;

	/**
	 * Create the panel.
	 */
	public AddLeaveRequest() {
		setBounds(100, 100, 900, 500);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 900, 0 };
		gridBagLayout.rowHeights = new int[] { 70, 404, 89, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		panelForm = new JPanel();
		panelForm.setBackground(new Color(191, 246, 195));
		GridBagConstraints gbc_panelForm = new GridBagConstraints();
		gbc_panelForm.fill = GridBagConstraints.BOTH;
		gbc_panelForm.gridx = 0;
		gbc_panelForm.gridy = 0;
		add(panelForm, gbc_panelForm);
		panelForm.setLayout(new CardLayout(0, 0));

		lblNewLabel = new JLabel("Submit a leave request");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font(UIConstants.FONT_FAMILY, Font.BOLD, 24));
		panelForm.add(lblNewLabel, "name_7090862684308");

		panel = new JPanel();
		panel.setBackground(new Color(191, 246, 195));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		add(panel, gbc_panel);
		panel.setLayout(null);

		lblFullName = new JLabel("FullName :");
		lblFullName.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 16));
		lblFullName.setBounds(60, 12, 107, 14);
		panel.add(lblFullName);

		txtfullName = new JTextField();
		txtfullName.setEditable(false);
		txtfullName.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 16));
		txtfullName.setBounds(60, 37, 295, 34);
		txtfullName.setColumns(10);
		panel.add(txtfullName);

		lblDepartment = new JLabel("Department :");
		lblDepartment.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 16));
		lblDepartment.setBounds(60, 83, 107, 20);
		panel.add(lblDepartment);

		cmbDepartment = new JComboBox<>();
		cmbDepartment.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 16));
		cmbDepartment.setBounds(60, 114, 295, 34);
		panel.add(cmbDepartment);

		JLabel lblApprover = new JLabel("Recipient of the application :");
		lblApprover.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 16));
		lblApprover.setBounds(60, 168, 237, 25);
		panel.add(lblApprover);

		cmbApprover = new JComboBox<String>();
		cmbApprover.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 16));
		cmbApprover.setBounds(60, 204, 295, 34);
		panel.add(cmbApprover);

		JLabel lblLeaveType = new JLabel("Types of Leave :");
		lblLeaveType.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 16));
		lblLeaveType.setBounds(481, 12, 120, 25);
		panel.add(lblLeaveType);

		cmbLeaveType = new JComboBox<>();
		cmbLeaveType.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 16));
		cmbLeaveType.setBounds(481, 37, 344, 34);
		panel.add(cmbLeaveType);

		JLabel lblReason = new JLabel("Reason for leaving:");
		lblReason.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 16));
		lblReason.setBounds(481, 238, 156, 25);
		panel.add(lblReason);

		txtReason = new JTextArea();
		txtReason.setFont(new Font("Dialog", Font.PLAIN, 14));
		txtReason.setBounds(481, 274, 344, 95);
		txtReason.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		panel.add(txtReason);

		btnChooseFile = new JButton("Add file");
		btnChooseFile.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 16));
		btnChooseFile.setBounds(60, 297, 295, 25);
		btnChooseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNewButtonActionPerformed(e);
			}
		});
		panel.add(btnChooseFile);

		JLabel lblStartDate = new JLabel("Start day:");
		lblStartDate.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 16));
		lblStartDate.setBounds(481, 85, 120, 25);
		panel.add(lblStartDate);

		startDateChooser = new JDateChooser();
		startDateChooser.setDate(new java.util.Date());
		startDateChooser.setDateFormatString("dd/MM/yyyy");
		startDateChooser.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 16));
		startDateChooser.setBounds(481, 119, 156, 35);
		panel.add(startDateChooser);

		JLabel lblEndDate = new JLabel("End date :");
		lblEndDate.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 16));
		lblEndDate.setBounds(686, 85, 120, 25);
		panel.add(lblEndDate);

		endDateChooser = new JDateChooser();
		endDateChooser.setDate(new java.util.Date());
		endDateChooser.setDateFormatString("dd/MM/yyyy");
		endDateChooser.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 16));
		endDateChooser.setBounds(669, 119, 156, 35);
		panel.add(endDateChooser);
		lblAdditionalFiles = new JLabel("Additional files :");
		lblAdditionalFiles.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 16));
		lblAdditionalFiles.setBounds(60, 264, 174, 25);
		panel.add(lblAdditionalFiles);

		lblImage = new JLabel("\r\n");
		lblImage.setBackground(new Color(255, 128, 64));
		lblImage.setOpaque(true);
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblImage.setBounds(60, 320, 295, 49);
		panel.add(lblImage);

		cmbDuration = new JComboBox<LeaveDuration>(LeaveDuration.values());
		cmbDuration.setFont(new Font("Dialog", Font.PLAIN, 16));
		cmbDuration.setBounds(481, 204, 344, 34);
		cmbDuration.setRenderer(new ListCellRenderer<LeaveDuration>() {
            @Override
            public Component getListCellRendererComponent(JList<? extends LeaveDuration> list, LeaveDuration value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = new JLabel();
                if (value != null) {
                    label.setText(value.getDescription());
                }
                
                if (isSelected) {
                    label.setBackground(list.getSelectionBackground());
                    label.setForeground(list.getSelectionForeground());
                } else {
                    label.setBackground(list.getBackground());
                    label.setForeground(list.getForeground());
                }
                
                label.setOpaque(true);
                return label;
            }
        });
		panel.add(cmbDuration);

		lblDuration = new JLabel("Duration:");
		lblDuration.setFont(new Font("Dialog", Font.PLAIN, 16));
		lblDuration.setBounds(481, 168, 237, 25);
		panel.add(lblDuration);
	
		panelbutton = new JPanel();
		panelbutton.setBackground(new Color(191, 246, 195));
		GridBagConstraints gbc_panelbutton = new GridBagConstraints();
		gbc_panelbutton.fill = GridBagConstraints.BOTH;
		gbc_panelbutton.gridx = 0;
		gbc_panelbutton.gridy = 2;
		add(panelbutton, gbc_panelbutton);
		panelbutton.setLayout(null);

		btnUpdate = new JButton("Update");
		btnUpdate.setVisible(false);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnUpdateActionPerformed(e);
			}
		});
		btnUpdate.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 16));
		btnUpdate.setBounds(339, 8, 250, 37);
		panelbutton.add(btnUpdate);

		btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSubmitActionPerformed(e);
			}
		});
		btnSubmit.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 16));
		btnSubmit.setBounds(339, 8, 249, 37);
		panelbutton.add(btnSubmit);

		cbbEmpName = new JComboBox<>();
		cbbEmpName.setVisible(false);

	}

	public void loadDataDetail(Employees emp, LeaveRequests leaveRequest) {
		var daoEmp = new EmployeeDao();
		txtfullName.setText(daoEmp.getEmp(leaveRequest.getEmployeeId()).getEmployeeName());
		
		// department
		var departmentDao = new DepartmentDao();
		var department = departmentDao.selectDepartment(emp.getEmployeeID());
		Optional.ofNullable(department).ifPresent(dep -> {
			cmbDepartment.addItem(dep.getDepartmentName());
		});
		
		// leave type
		var daoLT = new LeaveTypeDao();
		daoLT.getAll().stream().filter(leaveType -> leaveType.getLeaveTypeID() == leaveRequest.getLeaveTypeId()).findFirst().map(LeaveType::getLeaveTypeName)
			.ifPresent(cmbLeaveType::addItem);
		
		startDateChooser.setDate(leaveRequest.getStartDate());
		((JTextFieldDateEditor)startDateChooser.getDateEditor()).setEditable(false);
		endDateChooser.setDate(leaveRequest.getEndDate());
		((JTextFieldDateEditor)endDateChooser.getDateEditor()).setEditable(false);
		
		var duration = DateUtils.between(leaveRequest.getStartDate(), leaveRequest.getEndDate());
		var days = duration.toHours() / 24;
		
		var displayDuration = "";
		if (days == 0) {
			var isFullDay = leaveRequest.getLeaveDuration() == LeaveDuration.FULL_DAY;
			displayDuration = isFullDay ? "1 day" : 
				(leaveRequest.getLeaveDuration() == LeaveDuration.HALF_DAY_MORNING ? "0.5 day (Morning)" : "0.5 day (Afternoon)");
		} else {
			displayDuration = ((days + 1) + " days");
		}
		
		// duration
		lblDuration.setText(lblDuration.getText() + " " + displayDuration);
		cmbDuration.removeAllItems();
		cmbDuration.addItem(leaveRequest.getLeaveDuration());
		
		txtReason.setText(leaveRequest.getReason());
		
		var role = daoEmp.selectRole(emp.getEmployeeID());
		switch (role.getRoleName()) {
			case "User" -> {
				empReceiver = daoEmp.selectEmpbyidDeparment(department.getDepartmentId(), "Leader");
				cmbApprover.addItem(empReceiver.getEmployeeName());
			}
	
			case "Leader" -> {
				empReceiver = daoEmp.selectEmpbyRoleName("Admin");
				JOptionPane.showMessageDialog(null, empReceiver.getEmployeeName());
				cmbApprover.addItem(empReceiver.getEmployeeName());
			}
		}
		
		// leave document
		var leaveDocument = new LeaveDocumentDao().selectLeaveDocument(leaveRequest.getLeaveRequestId());
		if (leaveDocument.getDocumentPath() != null) {
			lblImage.setIcon(new ImageIcon(new ImageIcon(leaveDocument.getDocumentPath()).getImage()
					.getScaledInstance(lblImage.getY(), lblImage.getX(), Image.SCALE_SMOOTH)));
		}
		
		lblNewLabel.setText("Detail request leave");
		
		btnUpdate.setVisible(false);
		btnSubmit.setVisible(false);
	}
	
	public void loadDataAdd(Employees emp, Role r, Boolean b, VacationRequest vR) {
		va = vR;
		user = emp;
		var daoEmp = new EmployeeDao();
		var dao = new DepartmentDao();
		var daoLT = new LeaveTypeDao();
		listLT = daoLT.getAll();
		var d = dao.selectDepartment(emp.getEmployeeID());
		switch (r.getRoleName()) {
		case "User" -> {
			empReceiver = daoEmp.selectEmpbyidDeparment(d.getDepartmentId(), "Leader");
			cmbApprover.addItem(empReceiver.getEmployeeName());
			statusLR = "Submitted";
		}

		case "Leader" -> {
			empReceiver = daoEmp.selectEmpbyRoleName("Admin");
			JOptionPane.showMessageDialog(null, empReceiver.getEmployeeName());
			cmbApprover.addItem(empReceiver.getEmployeeName());
			statusLR = "Submitted";
		}
		}

		txtfullName.setText(emp.getEmployeeName());

		listLT.stream().forEach(e -> cmbLeaveType.addItem(e.getLeaveTypeName()));
		cmbDepartment.addItem(d.getDepartmentName());

		if (b) {
			listEmp = daoEmp.selectEmpbyidDeparment(d.getDepartmentId());
			cbbEmpName.setBounds(60, 62, 200, 34);
			panel.add(cbbEmpName);
			cbbEmpName.setVisible(b);
			listEmp.stream().forEach(e -> {
				if (e.getEmployeeID() != emp.getEmployeeID()) {
					cbbEmpName.addItem(e.getEmployeeName());
				}
			});
			txtfullName.setVisible(!b);

		}

	}

	public void LoadDataEdit(Employees emp, LeaveRequests lr, LeaveType lt, String fullname, Role r, LeaveDocument ld,
			VacationRequest vR) {
		va = vR;
		oldLT = lt;
		oldLR = lr;
		fileOld = ld.getDocumentPath();
		user = emp;
		var daoEmp = new EmployeeDao();
		var dao = new DepartmentDao();
		var daoLT = new LeaveTypeDao();
		listLT = daoLT.getAll();
		var d = dao.selectDepartment(emp.getEmployeeID());
		switch (r.getRoleName()) {
		case "User" -> {
			empReceiver = daoEmp.selectEmpbyidDeparment(d.getDepartmentId(), "Leader");
			cmbApprover.addItem(empReceiver.getEmployeeName());
			statusLR = "Submitted";
		}

		case "Leader" -> {
			empReceiver = daoEmp.selectEmpbyRoleName("Admin");
			JOptionPane.showMessageDialog(null, empReceiver.getEmployeeName());
			cmbApprover.addItem(empReceiver.getEmployeeName());
			statusLR = "Submitted";
		}
		}
		empOldReceiver = daoEmp.getEmp(lr.getApproverId());

		txtfullName.setText(fullname);

		listLT.stream().forEach(e -> {

			cmbLeaveType.addItem(e.getLeaveTypeName());
			if (e.getLeaveTypeName().equals(oldLT.getLeaveTypeName())) {
				cmbLeaveType.setSelectedItem(e.getLeaveTypeName());
			}

		});

		cmbDepartment.addItem(d.getDepartmentName());
		txtReason.setText(lr.getReason());
		startDateChooser.setDate(lr.getStartDate());
		endDateChooser.setDate(lr.getEndDate());
		cmbApprover.addItem(empOldReceiver.getEmployeeName());
		;

		if (ld.getDocumentPath() != null) {
			oldLD = ld;
			lblImage.setIcon(new ImageIcon(new ImageIcon(ld.getDocumentPath()).getImage()
					.getScaledInstance(lblImage.getY(), lblImage.getX(), Image.SCALE_SMOOTH)));
		}

		btnUpdate.setVisible(true);
//		btnSubmit.setVisible(false);

	}

//	check file
	protected void btnNewButtonActionPerformed(ActionEvent e) {
		var MAX_SIZE_MB = 1;
		var chooser = new JFileChooser();
		chooser.setDialogTitle("open image");
		chooser.setFileFilter(new FileNameExtensionFilter("image", "jpg", "png", "gif", "jpeg")

		);
		chooser.setAcceptAllFileFilterUsed(false);

		int result = chooser.showOpenDialog(null);

		if (result == JFileChooser.APPROVE_OPTION) {
			var file = chooser.getSelectedFile();
//			bat file gioi han bao nhiu mb
			long fileSizeInMB = file.length() / (1024 * 1024); // Kích thước tệp tính bằng MB
			if (fileSizeInMB > MAX_SIZE_MB) { // MAX_SIZE_MB là kích thước tối đa cho phép
				JOptionPane.showMessageDialog(null,
						"File is too large. Please select a file smaller than " + MAX_SIZE_MB + " MB.");
				return; // Dừng thực hiện nếu tệp quá lớn
			}
//			Lay ten hinh len tu cai thu muc tu chon
			fileNew = file.getName();
			JOptionPane.showMessageDialog(null, "luc lấy : " + fileNew);
//			lay duong dan chua file
			dirOld = file.getAbsolutePath();
			JOptionPane.showMessageDialog(null, "luc lấy : " + dirOld);
			lblImage.setIcon(new ImageIcon(new ImageIcon(file.getAbsolutePath()).getImage()
					.getScaledInstance(lblImage.getY(), lblImage.getX(), Image.SCALE_SMOOTH)));

		}
	}

	protected void btnUpdateActionPerformed(ActionEvent e) {

		var selectedEndDate = endDateChooser.getDate();

		var selectedStartDate = startDateChooser.getDate();

		isDateValid(selectedStartDate, selectedEndDate);

		// Calculate the difference in milliseconds between start date and end date
		long diffInMillies = selectedEndDate.getTime() - selectedStartDate.getTime();
		long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);

//	    // Check if the difference is more than 7 days
//	    if (diffInDays > 7) {
//	        JOptionPane.showMessageDialog(this, "The leave period cannot be more than 7 days.", "Invalid Date Range", JOptionPane.ERROR_MESSAGE);
//	        return;
//	    }

		var lr = new LeaveRequests();
		lr.setStartDate(selectedStartDate);
		lr.setEndDate(selectedEndDate);

		if (cmbLeaveType.getSelectedItem().toString().equals(oldLT.getLeaveTypeName())) {
			lr.setLeaveTypeId(oldLT.getLeaveTypeID());
		} else {
			listLT.stream().forEach(lt -> {
				if (lt.getLeaveTypeName().equals(cmbLeaveType.getSelectedItem().toString())) {
					lr.setLeaveTypeId(lt.getLeaveTypeID());
				}
			});
		}

		lr.setReason(txtReason.getText());
		lr.setLeaveRequestId(oldLR.getLeaveRequestId());

		var lD = new LeaveDocument();

		if (!(cmbLeaveType.getSelectedItem().equals("Orther"))) {
			newLT = new LeaveTypeDao().selectLeaveTypeById(lr.getLeaveTypeId());

			var TotalUserday = new LeaveTypeDao().countLRByIdLTVsEmp(lr.getEmployeeId(), lr.getLeaveTypeId());
			var requestedDay = getNumberOfLeaveDays(lr.getStartDate(), lr.getEndDate());
			if (TotalUserday + requestedDay >= newLT.getLeaveDaysPerYear()) {
				JOptionPane.showMessageDialog(null,
						"Bạn đã sử dụng hết số ngày nghỉ phép của loại " + newLT.getLeaveTypeName() + " này.");
				return;
			}
		} else {
			if (lblImage.getIcon() == null) {
				JOptionPane.showMessageDialog(this, "Please submit additional supporting documents.");
				return;
			}
		}

		if (fileNew != null) {
			dirNew = System.getProperty("user.dir") + "\\image";
//				duong dan tam anh tai noi chon tam hinh thay doi
			Path pathold = Paths.get(dirOld);
//				lay duong dan noi luu tru tam hinh cua ung dung
			Path pathnew = Paths.get(dirNew);

			try {
//					coppy hinh tu thu muc hien tai vao thu muc chua anh
//					cua do an de chay
				Files.copy(pathold, pathnew.resolve(fileNew), StandardCopyOption.REPLACE_EXISTING);
			} catch (Exception e2) {
				e2.printStackTrace();
			}

			lD.setDocumentPath("image/" + fileNew);

		} else {
			lD.setDocumentPath(fileOld);
		}

		var result = new LeaveRequestDao().UpdateLeaveRequestById(lr);
		if (result) {
			if (lD != null) {
				var daoLd = new LeaveDocumentDao();
				daoLd.updateLeaveDocument(lr.getLeaveRequestId(), lD.getDocumentPath());
			}

			va.getJformEdit().setVisible(false);
			va.showleaveRequest();
		}

	}

	protected void btnSubmitActionPerformed(ActionEvent e) {
		Date currentDate = new Date();
		
		Date selectedEndDate = endDateChooser.getDate();
		Date selectedStartDate = startDateChooser.getDate();
		isDateValid(selectedStartDate, selectedEndDate);

		// Check leave Type

		// Calculate the difference in milliseconds between start date and end date
		long diffInMillies = selectedEndDate.getTime() - selectedStartDate.getTime();
		long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);

		var lD = new LeaveDocument();
		var lr = new LeaveRequests();

		lr.setStartDate(selectedStartDate);
		lr.setEndDate(selectedEndDate);
		lr.setReason(txtReason.getText());
		lr.setEmployeeId(user.getEmployeeID());
		lr.setSubmissionDate(currentDate);
		
		// Leave duration
		lr.setLeaveDuration((LeaveDuration) cmbDuration.getSelectedItem());

		// Check leave Type

		listLT.forEach(lt -> {
			if (lt.getLeaveTypeName().equals(cmbLeaveType.getSelectedItem().toString())) {
				lr.setLeaveTypeId(lt.getLeaveTypeID());

			}
		});

		if (!(cmbLeaveType.getSelectedItem().equals("Orther"))) {
			newLT = new LeaveTypeDao().selectLeaveTypeById(lr.getLeaveTypeId());

			var TotalUserday = new LeaveTypeDao().countLRByIdLTVsEmp(lr.getEmployeeId(), lr.getLeaveTypeId());
			var requestedDay = getNumberOfLeaveDays(lr.getStartDate(), lr.getEndDate());
			if (TotalUserday + requestedDay >= newLT.getLeaveDaysPerYear()) {
				JOptionPane.showMessageDialog(null,
						"Bạn đã sử dụng hết số ngày nghỉ phép của loại " + newLT.getLeaveTypeName() + " này.");
				return;
			}
		} else {
			if (lblImage.getIcon() == null) {
				JOptionPane.showMessageDialog(this, "Please submit additional supporting documents.");
				return;
			}
		}

		if (fileNew != null) {
			dirNew = System.getProperty("user.dir") + "\\image";
//			duong dan tam anh tai noi chon tam hinh thay doi
			Path pathold = Paths.get(dirOld);
//			lay duong dan noi luu tru tam hinh cua ung dung
			Path pathnew = Paths.get(dirNew);
			JOptionPane.showMessageDialog(null, "luc add : " + pathold);
			JOptionPane.showMessageDialog(null, "luc add : " + pathnew);
			try {
//				coppy hinh tu thu muc hien tai vao thu muc chua anh
//				cua do an de chay
				Files.copy(pathold, pathnew.resolve(fileNew), StandardCopyOption.REPLACE_EXISTING);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			lD.setDocumentPath("image/" + fileNew);
			JOptionPane.showMessageDialog(null, "luc add : " + fileNew);
		}

		var dao = new LeaveRequestDao();
		var leaveRequestID = dao.insertLR(lr, statusLR);
		
		if (leaveRequestID > 0) {
			if (lD.getDocumentPath() != null) {
				var daoLd = new LeaveDocumentDao();
				daoLd.insertLeaveDocument(leaveRequestID, lD.getDocumentPath());
			}

			va.getJformEdit().setVisible(false);
			va.showleaveRequest();

			// Determine the receiver of the notification
//	        if (r.getRoleName().equals("Leader")) {
//	            // If the user is a Leader, send the request to Admin
//	            empReceiver = daoEmp.selectEmpbyRoleName("Admin");
//	        }

			var noti = new Notification();
			var notificationDao = new NotificationDao();
			noti = new Notification();
			noti.setLeaveRequestID(leaveRequestID);
			noti.setMessage("abc");
			noti.setReceiverID(empReceiver.getEmployeeID());
			var notification = notificationDao.insertNotification(noti);
			if (notification > 0) {
				JOptionPane.showMessageDialog(null, "Notification successfully inserted.");
			} else {
				JOptionPane.showMessageDialog(null, "Failed to insert notification.");
			}

		}
	}

	private boolean isDateValid(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ ngày bắt đầu và ngày kết thúc");
			return false;
		}

		Date currentDate = new Date();
		if (startDate.before(currentDate)) {
			JOptionPane.showMessageDialog(this, "Start date cannot be in the past.", "Invalid Start Date",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (endDate.before(startDate)) {
			JOptionPane.showMessageDialog(this, "End date cannot be before the start date.", "Invalid End Date",
					JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (duplicateCheck(startDate, endDate)) {
			JOptionPane.showMessageDialog(null, "Ngày bắt đầu nghỉ của bạn bị trùng với số ngày nghỉ của đơn cũ ");
			return false;
		}

		return true;
	}

	public static long getNumberOfLeaveDays(Date startDate, Date endDate) {
		// Tính khoảng cách giữa startDate và endDate theo đơn vị milli giây
		long diffInMillies = endDate.getTime() - startDate.getTime();

		// Chuyển đổi milli giây thành ngày và cộng thêm 1 để bao gồm ngày kết thúc
		return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) + 1;
	}

	public Boolean duplicateCheck(Date startDate, Date emdDate) {

		var list = new LeaveRequestDao().getAllLeaveRequestNextDay(user.getEmployeeID());

		Optional<LeaveRequests> overlappingRequest = list.stream().filter(el -> {
			boolean isStartDateOverlap = !startDate.after(el.getEndDate()) && !startDate.before(el.getStartDate());

			boolean isEndDateOverlap = !emdDate.before(el.getStartDate()) && !emdDate.after(el.getEndDate());

			boolean isDateRangeOverlap = startDate.before(el.getStartDate()) && emdDate.after(el.getEndDate());

			return isStartDateOverlap || isEndDateOverlap || isDateRangeOverlap;
		}).findFirst();

		if (overlappingRequest.isPresent()) {
			// Xử lý khi có chồng lấn
			LeaveRequests overlapping = overlappingRequest.get();
			JOptionPane.showMessageDialog(null,
					"Đơn nghỉ bị trùng với đơn từ " + overlapping.getStartDate() + " đến " + overlapping.getEndDate());
			return true;
		}
		return false;
	}

	public Boolean duplicateCheckForEdit(Date startDate, Date emdDate) {
		var list = new LeaveRequestDao().getAllLeaveRequestNextDay(user.getEmployeeID());

		Optional<LeaveRequests> overlappingRequest = list.stream()
				.filter(el -> el.getLeaveRequestId() != oldLR.getLeaveRequestId()) // Bỏ qua đơn đang chỉnh sửa
				.filter(el -> {
					boolean isStartDateOverlap = !startDate.after(el.getEndDate())
							&& !startDate.before(el.getStartDate());

					boolean isEndDateOverlap = !emdDate.before(el.getStartDate()) && !emdDate.after(el.getEndDate());

					boolean isDateRangeOverlap = startDate.before(el.getStartDate()) && emdDate.after(el.getEndDate());

					return isStartDateOverlap || isEndDateOverlap || isDateRangeOverlap;
				}).findFirst();

		if (overlappingRequest.isPresent()) {
			// Xử lý khi có chồng lấn
			LeaveRequests overlapping = overlappingRequest.get();
			JOptionPane.showMessageDialog(null,
					"Đơn nghỉ bị trùng với đơn từ " + overlapping.getStartDate() + " đến " + overlapping.getEndDate());
			return true;
		}

		return false;
	}
}
