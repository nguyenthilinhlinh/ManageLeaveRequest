package entity;

import java.util.Date;

import javax.swing.Icon;

public class ProfileEmp {
	private int employeeID;
	private String image;
	private String phoneNumber;
	private String address;
	private Date dateOfBirth;
	private String gender;
	private boolean status;

	public ProfileEmp() {
	}

	public ProfileEmp(int employeeID, String image, String phoneNumber, String address, Date dateOfBirth, String gender,
			boolean status) {
		this.employeeID = employeeID;
		this.image = image;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.status = status;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ProfileEmp [employeeID=" + employeeID + ", image=" + image + ", phoneNumber=" + phoneNumber
				+ ", address=" + address + ", dateOfBirth=" + dateOfBirth + ", gender=" + gender + ", status=" + status
				+ "]";
	}
}
