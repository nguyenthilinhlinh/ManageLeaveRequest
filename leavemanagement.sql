use master
go

DROP DATABASE IF EXISTS LeaveManagement
CREATE DATABASE LeaveManagement;
GO

USE LeaveManagement;
GO

DROP TABLE IF EXISTS Departments
CREATE TABLE Departments (
	DepartmentID INT IDENTITY(1,1) PRIMARY KEY,
    DepartmentName VARCHAR(100) NOT NULL,
	Status BIT NOT NULL DEFAULT 1
);
GO

DROP TABLE IF EXISTS Positions;
CREATE TABLE Positions (
    PositionID INT IDENTITY(1,1) PRIMARY KEY,
    PositionName VARCHAR(100) NOT NULL,
	DepartmentID INT NOT NULL,
	Status BIT NOT NULL DEFAULT 1,
	FOREIGN KEY (DepartmentID) REFERENCES Departments(DepartmentID)
);
GO

DROP TABLE IF EXISTS Employees
CREATE TABLE Employees (
    EmployeeID INT IDENTITY(1,1) PRIMARY KEY,
    FullName VARCHAR(100) NOT NULL,
    Email VARCHAR(100) UNIQUE NOT NULL,
	Password NVARCHAR(255) NOT NULL,
    DepartmentID INT,
	Status BIT NOT NULL DEFAULT 1,
    --Role VARCHAR(50) NOT NULL, -- 'Employee', 'Team Lead', 'PM'
    FOREIGN KEY (DepartmentID) REFERENCES Departments(DepartmentID)
	
);
GO
DROP TABLE IF EXISTS Roles;
CREATE TABLE Roles (
    RoleID INT IDENTITY(1,1) PRIMARY KEY,
    RoleName VARCHAR(50) NOT NULL,
	Status BIT NOT NULL DEFAULT 1
);
GO
DROP TABLE IF EXISTS EmployeeRoles;
CREATE TABLE EmployeeRoles (
    EmployeeRoleID INT IDENTITY(1,1) PRIMARY KEY,
    EmployeeID INT,
    RoleID INT,Status BIT NOT NULL DEFAULT 1,
    FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID),
    FOREIGN KEY (RoleID) REFERENCES Roles(RoleID)
);
GO

DROP TABLE IF EXISTS EmployeeDetails;
CREATE TABLE EmployeeDetails (
    EmployeeID INT PRIMARY KEY,
    Image NVARCHAR(255),
    PhoneNumber VARCHAR(15),
    Address VARCHAR(255),
    DateOfBirth DATE NOT NULL,
    Gender VARCHAR(10),
    PositionID INT,
	Status BIT NOT NULL DEFAULT 1,
    FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID),
    FOREIGN KEY (PositionID) REFERENCES Positions(PositionID)
);
GO

DROP TABLE IF EXISTS LeaveTypes
CREATE TABLE LeaveTypes (
    LeaveTypeID INT IDENTITY(1,1) PRIMARY KEY,
    LeaveTypeName VARCHAR(100) NOT NULL,
    LeaveTypeDescription TEXT,
	LeaveDaysPerYear INT NOT NULL, -- Number of leave days allowed per year
	Status BIT NOT NULL DEFAULT 1
);

DROP TABLE IF EXISTS LeaveRequests
CREATE TABLE LeaveRequests (
    LeaveRequestID INT IDENTITY(1,1) PRIMARY KEY,
    EmployeeID INT NOT NULL,
	LeaveTypeID INT NOT NULL, 
    StartDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    Reason NVARCHAR(255),
    StatusLR VARCHAR(20) DEFAULT 'Submitted' CHECK (StatusLR IN ('Submitted', 'Viewed by Lead', 'Approved by Lead', 'Viewed by HR', 'Approved by HR', 'Rejected')), -- 'Draft', 'Submitted', 'Viewed by Team Lead', 'Approved by Team Lead', 'Viewed by PM', 'Approved by PM', 'Not Approved', 'Canceled'
    SubmissionDate DATETIME NOT NULL DEFAULT GETDATE(),
	ApproverID INT,
    ApprovalDate DATETIME,
	Status BIT NOT NULL DEFAULT 1,
    FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID),
	FOREIGN KEY (LeaveTypeID) REFERENCES LeaveTypes(LeaveTypeID),
	FOREIGN KEY (ApproverID) REFERENCES Employees(EmployeeID)
);
go

-- send 
drop table if exists Notifications
CREATE TABLE Notifications (
    NotificationID INT IDENTITY(1,1) PRIMARY KEY,
    LeaveRequestID INT NOT NULL,
    NotificationDate DATE NOT NULL, --Ngày thông báo được gửi
    ReceiverID INT NOT NULL, -- ID of the Team Lead or PM who receives the notification, xác định người nhận đc thông báo 
    Message NVARCHAR(255), --Nội dung thông báo,  chi tiết việc thay đổi trạng thái hoặc hành động. NV ycau nghỉ --> 1 thông báo gửi đến lead (app/reje) -> tb cho pm(app/re) --> thông báo cuối cùng sẽ đc gửi cho NV
	Status BIT NOT NULL DEFAULT 1,
    FOREIGN KEY (LeaveRequestID) REFERENCES LeaveRequests(LeaveRequestID),
    FOREIGN KEY (ReceiverID) REFERENCES Employees(EmployeeID)
);
GO
-------nếu leavetype cần giấy tờ chứng nhận tải lên bất kỳ tài liệu hỗ trợ nào xin nghỉ phép lâu dài ứng dụng sẽ lưu một bản ghi vào bảng LeaveDocuments, bao gồm cả đường dẫn đến tài liệu.
DROP TABLE IF EXISTS LeaveDocuments
CREATE TABLE LeaveDocuments (
    DocumentID INT IDENTITY(1,1) PRIMARY KEY,
    LeaveRequestID INT NOT NULL,
    --DocumentName VARCHAR(100) NOT NULL,
    DocumentPath NVARCHAR(255) NOT NULL,
    UploadedDate DATETIME NOT NULL DEFAULT GETDATE(),
	Status BIT NOT NULL DEFAULT 1,
    FOREIGN KEY (LeaveRequestID) REFERENCES LeaveRequests(LeaveRequestID)
);
GO



	