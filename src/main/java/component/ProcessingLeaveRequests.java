package component;

import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dao.LeaveHRDao;
import dao.LeaveRequestDao;
import dao.NotificationDao;
import entity.*;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.Font;
import component.PendingApproval;
import entity.Notification;

public class ProcessingLeaveRequests extends JPanel {

    private static final long serialVersionUID = 1L;
    private JPanel panelApproveAnRequest;
    private JPanel panelReason;
    private JButton btnReject;
    private JLabel lblEmployeeName;
    private JButton btnApprove;
    private JTextField txtStatus;
    private JTextField txtEmployeeName;
    private JLabel lblLeaveType;
    private JTextField txtLeaveType;
    private JLabel lblStartDate;
    private JTextField txtStartDate;
    private JLabel lblStartDate_1;
    private JTextField txtEndDate;
    private JLabel lblReason;
    private JTextField txtReason;
    private JLabel lblSubmissionDate;
    private JTextField txtSubmissionDate;
    private JButton btnSend;
    private JTextArea textArea;
    private JButton btnBack;
    private PendingApproval pA;
    private Employees user;
    private Role role = null;
    private Integer idRequest;
    private int id;
    private Employees emp = new Employees();
   private LeaveHistory lh;

    public ProcessingLeaveRequests(LeaveHistory lH, String name, PendingApproval pAl, Employees emp, Role r) {
    	role = r;
    	user = emp;
    	pA = pAl;
    	lh = lH;
        setLayout(new CardLayout(0, 0));

        panelApproveAnRequest = new JPanel();
        panelApproveAnRequest.setBackground(new Color(191, 246, 195));
        add(panelApproveAnRequest, "panelApproveAnRequest");
        panelApproveAnRequest.setLayout(null);

        btnReject = new JButton("Reject");
        btnReject.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnReject.setBounds(150, 11, 130, 31);
        panelApproveAnRequest.add(btnReject);

        lblEmployeeName = new JLabel("Employee Name:");
        lblEmployeeName.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblEmployeeName.setBounds(10, 58, 153, 31);
        panelApproveAnRequest.add(lblEmployeeName);

        btnApprove = new JButton("Approved");
        btnApprove.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnApprove.setBounds(10, 11, 130, 31);
        panelApproveAnRequest.add(btnApprove);

        txtStatus = new JTextField();
        txtStatus.setFont(new Font("Tahoma", Font.PLAIN, 16));
        txtStatus.setEditable(false);
        txtStatus.setColumns(10);
        txtStatus.setBounds(345, 11, 114, 31);
        panelApproveAnRequest.add(txtStatus);

        txtEmployeeName = new JTextField(name);
        txtEmployeeName.setFont(new Font("Tahoma", Font.PLAIN, 16));
        txtEmployeeName.setEditable(false);
        txtEmployeeName.setColumns(10);
        txtEmployeeName.setBounds(173, 58, 286, 31);
        panelApproveAnRequest.add(txtEmployeeName);

        lblLeaveType = new JLabel("Leave Type:");
        lblLeaveType.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblLeaveType.setBounds(10, 100, 153, 31);
        panelApproveAnRequest.add(lblLeaveType);

        txtLeaveType = new JTextField(lH.getLeaveType());
        txtLeaveType.setFont(new Font("Tahoma", Font.PLAIN, 16));
        txtLeaveType.setEditable(false);
        txtLeaveType.setColumns(10);
        txtLeaveType.setBounds(173, 100, 286, 31);
        panelApproveAnRequest.add(txtLeaveType);

        lblStartDate = new JLabel("Effective Time:");
        lblStartDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblStartDate.setBounds(10, 142, 153, 31);
        panelApproveAnRequest.add(lblStartDate);

        txtStartDate = new JTextField(lH.getStartDate().toString());
        txtStartDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
        txtStartDate.setEditable(false);
        txtStartDate.setColumns(10);
        txtStartDate.setBounds(173, 142, 114, 31);
        panelApproveAnRequest.add(txtStartDate);

        lblStartDate_1 = new JLabel("=>");
        lblStartDate_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblStartDate_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblStartDate_1.setBounds(297, 146, 26, 23);
        panelApproveAnRequest.add(lblStartDate_1);

        txtEndDate = new JTextField(lH.getEndDate().toString());
        txtEndDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
        txtEndDate.setEditable(false);
        txtEndDate.setColumns(10);
        txtEndDate.setBounds(345, 142, 114, 31);
        panelApproveAnRequest.add(txtEndDate);

        lblReason = new JLabel("Reason:");
        lblReason.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblReason.setBounds(10, 204, 100, 23);
        panelApproveAnRequest.add(lblReason);

        txtReason = new JTextField(lH.getReason());
        txtReason.setFont(new Font("Tahoma", Font.PLAIN, 16));
        txtReason.setEditable(false);
        txtReason.setColumns(10);
        txtReason.setBounds(173, 190, 286, 118);
        panelApproveAnRequest.add(txtReason);

        lblSubmissionDate = new JLabel("Submission Date:");
        lblSubmissionDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblSubmissionDate.setBounds(10, 320, 153, 29);
        panelApproveAnRequest.add(lblSubmissionDate);

        txtSubmissionDate = new JTextField(lH.getSubmissionDate().toString());
        txtSubmissionDate.setFont(new Font("Tahoma", Font.PLAIN, 16));
        txtSubmissionDate.setEditable(false);
        txtSubmissionDate.setColumns(10);
        txtSubmissionDate.setBounds(173, 319, 286, 31);
        panelApproveAnRequest.add(txtSubmissionDate);

        panelReason = new JPanel();
        panelReason.setBorder(new TitledBorder(
                new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
                "<html><div style=\"font-size:20px\">Reason For Refusal</div></html>", TitledBorder.LEADING,
                TitledBorder.TOP, null, new Color(0, 0, 0)));
        add(panelReason, "panelReason");
        panelReason.setLayout(null);

        btnSend = new JButton("Send");
        btnSend.setBounds(20, 225, 247, 23);
        panelReason.add(btnSend);

        textArea = new JTextArea();
        textArea.setFont(new Font("Courier New", Font.PLAIN, 15));
        textArea.setBounds(10, 30, 355, 184);
        panelReason.add(textArea);

        btnBack = new JButton("Back");
        btnBack.setBounds(276, 225, 89, 23);
        panelReason.add(btnBack);

        btnApprove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UpdateApprovateStatus(e);
            }
        });

        btnReject.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UpdateApprovateStatus(e);
            }
        });

        btnSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                submitReasonForRefusal();
            }
        });

        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout layout = (CardLayout) getLayout();
                layout.show(ProcessingLeaveRequests.this, "panelApproveAnRequest");
            }
        });
    }

//    public void setFormData() {
//        txtEmployeeName.setText(employeeName);
//        txtLeaveType.setText(leaveType);
//        txtStartDate.setText(startDate);
//        txtEndDate.setText(endDate);
//        txtReason.setText(reason);
//        txtApproverID.setText(approverID);
//        txtSubmissionDate.setText(submissionDate);
//        txtStatus.setText(status);
//    }

    public void UpdateApprovateStatus(ActionEvent e) {
    	
        String actionCommand = e.getActionCommand();

        var dao = new LeaveHRDao();

         
        

        switch (actionCommand) {
        case "Approved" -> {
        	var str = role.getRoleName().equals("Admin") ? "Approved by HR" : "Approved by Lead";
            dao.UpdateApproveStatus(str, user.getEmployeeID(), lh.getHistoryId());
            JOptionPane.showMessageDialog(null, "Successfully");
            this.addNotificationApprove(lh, user, true);
            txtStatus.setText(btnApprove.getText());
            txtStatus.setText("Approved");
            pA.getProcessingLeaveRequestFrame().setVisible(false);
            this.setVisible(false);
        }
        case "Reject" -> {
            if (JOptionPane.showConfirmDialog(null, "You definitely want to reject?", "Notification",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                return;
            }
            
            
            CardLayout layout = (CardLayout) getLayout();
            layout.show(this, "panelReason");
        }
        }
        
        
        pA.showAllRequest();
    }

    private void addNotificationApprove(LeaveHistory lr, Employees user, boolean approved) {
        var noti = new entity.Notification();
        var notificationDao = new NotificationDao();
        noti = new Notification();
        noti.setLeaveRequestID(lr.getHistoryId());
        noti.setMessage(user.getEmployeeName() + (approved ? " approved " : " rejected") + " your leave request");
        noti.setReceiverID(lr.getEmployeeId());
        var notification = notificationDao.insertNotification(noti);
        if (notification > 0) {
            System.out.println("Notification inserted");
        } else {
            System.out.println("Notification failed to insert");
        }
    }

//    public void addApproveActionListener(ActionListener Listener) {
//    	btnApprove.addActionListener(Listener);
//    }
//    public void addRejeckActionListener(ActionListener Listener) {
//    	btnReject.addActionListener(Listener);
//    }

    private void submitReasonForRefusal() {
        LeaveHRDao dao = new LeaveHRDao();

        String reason = textArea.getText();
        if (reason.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please provide a reason for refusal.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        
        
        
        dao.UpdateApproveStatus("Rejected", lh.getApproverID(), lh.getHistoryId());
//        dao.UpdateRejectionReason(reason, idRequest);

//        ApproveAnRequest parentFrame = ApproveAnRequest.getInstance();
//        parentFrame.showAllRequest();
        
        JOptionPane.showMessageDialog(this, "Leave request rejected successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        this.addNotificationApprove(lh, user, false);
        pA.getProcessingLeaveRequestFrame().setVisible(false);
        CardLayout layout = (CardLayout) getLayout();
        layout.show(this, "panelApproveAnRequest");
    }
}
