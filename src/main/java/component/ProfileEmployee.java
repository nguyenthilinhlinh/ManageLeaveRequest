package component;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.*;

import java.awt.Font;
import java.awt.Image;
import java.util.Date;
import java.util.Optional;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import entity.Employees;
import entity.ProfileEmp;
import dao.EmployeeDao;
import dao.ProfileEmpDao;

import java.awt.GridLayout;
import javax.swing.border.TitledBorder;

import constants.UIConstants;

import javax.swing.border.EtchedBorder;

public class ProfileEmployee extends JPanel {

	private static final long serialVersionUID = 1L;
	private JPanel panel_1;
	private JPanel panelImage;
	private JPanel panelProfile;
	private JLabel lblImage;
	private JLabel lblFullName;
	private JLabel lblNewLabel_4;
	private JLabel lblDateOfBirth;
	private JLabel lblGender;
	private JPanel panelGender;
	private JRadioButton rbFemale;
	private JRadioButton rbMale;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel lblPhoneNumber;
	private JLabel lblAddress;
	private JTextField textFullName;
	private JTextField textEmail;
	private JTextField textDateOfBirth;
	private JTextField textPhone;
	private JTextField textAddress;
	private JPanel panel;
	private JLabel lblNewLabel;

	/**
	 * Create the panel.
	 */
	public ProfileEmployee() {
		setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(64, 0, 64)));
		setSize(988, 530);
		setLayout(new BorderLayout(0, 0));

		panel_1 = new JPanel();
		add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		panelImage = new JPanel();
		panelImage.setBackground(new Color(191, 246, 195));
		panel_1.add(panelImage, BorderLayout.WEST);

		lblImage = new JLabel();
		lblImage.setBackground(Color.WHITE);
		lblImage.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblImage.setHorizontalAlignment(JLabel.CENTER);
		lblImage.setIcon(new ImageIcon("C:"));
		lblImage.setPreferredSize(new Dimension(250, 200));
		lblImage.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		GroupLayout gl_panelImage = new GroupLayout(panelImage);
		gl_panelImage.setHorizontalGroup(
			gl_panelImage.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panelImage.createSequentialGroup()
					.addContainerGap(76, Short.MAX_VALUE)
					.addComponent(lblImage, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(25))
		);
		gl_panelImage.setVerticalGroup(
			gl_panelImage.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelImage.createSequentialGroup()
					.addGap(83)
					.addComponent(lblImage, GroupLayout.PREFERRED_SIZE, 275, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(129, Short.MAX_VALUE))
		);
		panelImage.setLayout(gl_panelImage);

		panelProfile = new JPanel();
		panelProfile.setBackground(new Color(191, 246, 195));
		panel_1.add(panelProfile, BorderLayout.CENTER);
		panelProfile.setLayout(null);

		lblFullName = new JLabel("<html><b>Full Name :</b></html>\r\n");
		lblFullName.setBounds(39, 77, 131, 41);
		lblFullName.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 18));
		lblFullName.setHorizontalAlignment(SwingConstants.LEFT);
		panelProfile.add(lblFullName);

		lblNewLabel_4 = new JLabel("<html><b>Email :</b></html>\r\n");
		lblNewLabel_4.setBounds(39, 129, 131, 41);
		lblNewLabel_4.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 18));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.LEFT);
		panelProfile.add(lblNewLabel_4);

		lblDateOfBirth = new JLabel("Date Of Birth :");
		lblDateOfBirth.setBounds(39, 180, 131, 41);
		lblDateOfBirth.setFont(new Font(UIConstants.FONT_FAMILY, Font.BOLD, 18));
		lblDateOfBirth.setHorizontalAlignment(SwingConstants.LEFT);
		panelProfile.add(lblDateOfBirth);

		lblGender = new JLabel("Gender :");
		lblGender.setBounds(39, 232, 131, 41);
		lblGender.setFont(new Font(UIConstants.FONT_FAMILY, Font.BOLD, 18));
		lblGender.setHorizontalAlignment(SwingConstants.LEFT);
		panelProfile.add(lblGender);

		panelGender = new JPanel();
		panelGender.setBackground(new Color(191, 246, 195));
		panelGender.setBounds(222, 232, 392, 41);
		panelProfile.add(panelGender);
		panelGender.setLayout(null);

		rbMale = new JRadioButton("Male");
		rbMale.setBackground(new Color(191, 246, 195));
		rbMale.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 18));
		rbMale.setBounds(6, 5, 84, 36);
		rbMale.setMnemonic('M');
		rbMale.setEnabled(false);
		buttonGroup.add(rbMale);
		panelGender.add(rbMale);

		rbFemale = new JRadioButton("Female");
		rbFemale.setBackground(new Color(191, 246, 195));
		rbFemale.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 18));
		rbFemale.setBounds(129, 5, 84, 36);
		rbFemale.setMnemonic('F');
		rbFemale.setEnabled(false);
		buttonGroup.add(rbFemale);
		rbFemale.setSelected(true);
		panelGender.add(rbFemale);

		lblPhoneNumber = new JLabel("Phone Number :");
		lblPhoneNumber.setBounds(39, 284, 173, 41);
		lblPhoneNumber.setHorizontalAlignment(SwingConstants.LEFT);
		lblPhoneNumber.setFont(new Font(UIConstants.FONT_FAMILY, Font.BOLD, 18));
		panelProfile.add(lblPhoneNumber);

		lblAddress = new JLabel("Address :");
		lblAddress.setBounds(39, 336, 173, 41);
		lblAddress.setHorizontalAlignment(SwingConstants.LEFT);
		lblAddress.setFont(new Font(UIConstants.FONT_FAMILY, Font.BOLD, 18));
		panelProfile.add(lblAddress);

		textFullName = new JTextField();
		textFullName.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 18));
		textFullName.setEditable(false);
		textFullName.setColumns(10);
		textFullName.setBounds(222, 76, 392, 41);
		panelProfile.add(textFullName);

		textEmail = new JTextField();
		textEmail.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 18));
		 textEmail.setEditable(false);
		textEmail.setColumns(10);
		textEmail.setBounds(222, 129, 392, 41);
		panelProfile.add(textEmail);

		textDateOfBirth = new JTextField();
		textDateOfBirth.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 18));
		textDateOfBirth.setEditable(false);
		textDateOfBirth.setColumns(10);
		textDateOfBirth.setBounds(222, 180, 392, 41);
		panelProfile.add(textDateOfBirth);

		textPhone = new JTextField();
		textPhone.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 18));
		textPhone.setEditable(false);
		textPhone.setColumns(10);
		textPhone.setBounds(222, 284, 392, 41);
		panelProfile.add(textPhone);

		textAddress = new JTextField();
		textAddress.setFont(new Font(UIConstants.FONT_FAMILY, Font.PLAIN, 18));
		textAddress.setEditable(false);
		textAddress.setColumns(10);
		textAddress.setBounds(222, 336, 392, 41);
		panelProfile.add(textAddress);
		
		panel = new JPanel();
		panel.setBackground(new Color(191, 246, 195));
		add(panel, BorderLayout.NORTH);
		
		lblNewLabel = new JLabel("ProFile Employee");
		lblNewLabel.setFont(new Font(UIConstants.FONT_FAMILY, Font.BOLD, 24));
		panel.add(lblNewLabel);

	}

	public void populateProfile(Employees emp) {
		var dao = new ProfileEmpDao();
		var daoEmp = new EmployeeDao();
		var profile = dao.getProfileById(emp.getEmployeeID());
		if (profile != null) {
			textFullName.setText(emp.getEmployeeName());
			textEmail.setText(emp.getEmail());
			Optional.ofNullable(profile.getDateOfBirth()).map(Date::toString)
				.ifPresent(textDateOfBirth::setText);
			textPhone.setText(profile.getPhoneNumber());
			textAddress.setText(profile.getAddress());

			if ("Male".equalsIgnoreCase(profile.getGender())) {
				rbMale.setSelected(true);
			} else {
				rbFemale.setSelected(true);
			}

			if (profile.getImage() != null) {
				lblImage.setIcon(new ImageIcon(profile.getImage()));
			} else {
				lblImage.setIcon(null);
			}
		} else {
			JOptionPane.showMessageDialog(this, "Profile data not found.");
		}
	}
}
