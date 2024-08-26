package entity;

import java.util.Date;

public class LeaveDocument {
	private int documentId ;
	private int LeaveRequestID;
	private String DocumentPath; 
	private Date UploadedDate ;
	private Boolean Status ;
	
	public LeaveDocument() {}
	
	public LeaveDocument(int documentId, int leaveRequestID, String documentPath, Date uploadedDate,
			Boolean status) {
		super();
		this.documentId = documentId;
		LeaveRequestID = leaveRequestID;
		DocumentPath = documentPath;
		UploadedDate = uploadedDate;
		Status = status;
	}

	public int getDocumentId() {
		return documentId;
	}

	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}

	public int getLeaveRequestID() {
		return LeaveRequestID;
	}

	public void setLeaveRequestID(int leaveRequestID) {
		LeaveRequestID = leaveRequestID;
	}


	public String getDocumentPath() {
		return DocumentPath;
	}

	public void setDocumentPath(String documentPath) {
		DocumentPath = documentPath;
	}

	public Date getUploadedDate() {
		return UploadedDate;
	}

	public void setUploadedDate(Date uploadedDate) {
		UploadedDate = uploadedDate;
	}

	public Boolean getStatus() {
		return Status;
	}

	public void setStatus(Boolean status) {
		Status = status;
	}

	@Override
	public String toString() {
		return "LeaveDocument [documentId=" + documentId + ", LeaveRequestID=" + LeaveRequestID 
				+ ", DocumentPath=" + DocumentPath + ", UploadedDate=" + UploadedDate + ", Status=" + Status + "]";
	}

	
	
}
