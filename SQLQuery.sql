﻿use master
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
	DepartmentID INT NOT NULL,
    PositionName VARCHAR(100) NOT NULL,
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
	Status BIT NOT NULL DEFAULT 1,
);
GO

DROP TABLE IF EXISTS Employee_positions;
CREATE TABLE Employee_positions (
    ID INT IDENTITY(1,1) PRIMARY KEY,
	EmployeeID INT Not NULL,
	PositionID INT Not NUll,
	FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID),
	FOREIGN KEY (PositionID) REFERENCES Positions(PositionID),
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
	ID INT IDENTITY(1,1) PRIMARY KEY,
    EmployeeID INT,
    Image NVARCHAR(255),
    PhoneNumber VARCHAR(15),
    Address VARCHAR(255),
    DateOfBirth DATE NOT NULL,
    Gender VARCHAR(10),
	Status BIT NOT NULL DEFAULT 1,
    FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID),
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
	Image NVARCHAR(250),
    DocumentPath NVARCHAR(255),
    UploadedDate DATETIME NOT NULL DEFAULT GETDATE(),
	Status BIT NOT NULL DEFAULT 1,
    FOREIGN KEY (LeaveRequestID) REFERENCES LeaveRequests(LeaveRequestID)
);
GO


----------------------PROC-------------------------------

-- Check user login
CREATE OR ALTER PROC selectLogin
@Email NVARCHAR(100),
@Password NVARCHAR(100)
AS
BEGIN
SELECT 
    *
FROM 
    Employees

WHERE 
    Email = @Email AND Password = @Password;

END
GO
	
--- select Role by id emp
CREATE OR ALTER PROC selectRole
@idEmp Int
AS
BEGIN
SELECT 
    r.RoleName
FROM  Roles r
Join EmployeeRoles er on r.RoleID = er.RoleID
WHERE er.EmployeeID = @idEmp
    

END
GO

--- select Departments by id emp
CREATE OR ALTER PROC selectDepartments
@idEmp Int
AS
BEGIN
SELECT 
    d.DepartmentName, d.DepartmentID
FROM  Departments d
Join Positions p  on p.DepartmentID = d.DepartmentID
Join Employee_positions ep on ep.PositionID = p.PositionID
WHERE ep.EmployeeID = @idEmp
    

END
GO

--- select LeaveRequests by id emp
CREATE OR ALTER PROC selectLeaveRequest
@idEmp Int
AS
BEGIN
SELECT 
    *
FROM  LeaveRequests
WHERE EmployeeID = @idEmp
    

END
GO

--- select LeaveType by id leaveRequest
CREATE OR ALTER PROC selectLeaveTypeByLr
@idLeaveRequest Int
AS
BEGIN
SELECT 
    lt.*
FROM  LeaveTypes lt
join LeaveRequests lr on lr.LeaveTypeID =   lt.LeaveTypeID
WHERE lr.LeaveRequestID = @idLeaveRequest
  

END
GO

CREATE OR ALTER PROC selectLeaveType
@idLeaveType Int
AS
BEGIN
SELECT 
    *
FROM  LeaveTypes 
WHERE LeaveTypeID = @idLeaveType
  

END
GO

-- select count id all of statusLR in ('Submitted', 'Viewed by Lead') for (leader) 
create or alter proc countLRforLeader
@empID int 
as
begin
	select count(LeaveRequestID) total from LeaveRequests lr
	Join Employee_positions ep on ep.EmployeeID = lr.EmployeeID
	Join Positions p on p.PositionID = ep.PositionID
	Join Departments d on d.DepartmentID = p.DepartmentID 

	where StatusLR in  ('Submitted','Viewed by Lead') and d.DepartmentID = (SELECT d.DepartmentID
							FROM  Departments d
							Join Positions p  on p.DepartmentID = d.DepartmentID
							Join Employee_positions ep on ep.PositionID = p.PositionID
							WHERE ep.EmployeeID = @empID)
	
end
go

-- Phan trang cho gui dơn ('Submitted','Viewed by Lead') của Leader
create or alter proc selLRequestForLeader
@empID int ,
@pageNumber int,
@RowOfPage Int
as
begin
	Select  lr.*
	from LeaveRequests lr
	Join Employee_positions ep on ep.EmployeeID = lr.EmployeeID
	Join Positions p on p.PositionID = ep.PositionID
	Join Departments d on d.DepartmentID = p.DepartmentID
	Where lr.StatusLR in ('Submitted','Viewed by Lead')  and  d.DepartmentID = (SELECT 
																						d.DepartmentID
																					FROM  Departments d
																					Join Positions p  on p.DepartmentID = d.DepartmentID
																					Join Employee_positions ep on ep.PositionID = p.PositionID
																					WHERE ep.EmployeeID = @empID)
	order by lr.LeaveRequestID
		offset (@pageNumber - 1)*@RowOfPage rows 
		fetch next @RowOfPage rows only 
end
go

select emp.*,p.*,  er.* from Employees emp
join EmployeeRoles er on er.EmployeeID = emp.EmployeeID
join Employee_positions ep on ep.EmployeeID = emp.EmployeeID
join Positions p on p.PositionID = ep.PositionID
where  p.DepartmentID =(SELECT						 d.DepartmentID
																					FROM  Departments d
																					Join Positions p  on p.DepartmentID = d.DepartmentID
																					Join Employee_positions ep on ep.PositionID = p.PositionID
																					WHERE ep.EmployeeID = 3 )


-- select count id all of statusLR in ('Approved by Lead', 'Viewed by HR') for (admin) 
create or alter proc countLRforAdmin
@empID int 
as
begin
	select count(LeaveRequestID) total from LeaveRequests lr
	Join Employee_positions ep on ep.EmployeeID = lr.EmployeeID
	Join Positions p on p.PositionID = ep.PositionID
	Join Departments d on d.DepartmentID = p.DepartmentID 
	where StatusLR in  ('Approved by Lead', 'Viewed by HR')
	
end
go

-- Phan trang cho gui dơn ('Approved by Lead', 'Viewed by HR') của Admin
create or alter proc selLRequestForAdmin
@empID int ,
@pageNumber int,
@RowOfPage Int
as
begin
	Select  lr.*
	from LeaveRequests lr
	Join Employee_positions ep on ep.EmployeeID = lr.EmployeeID
	Join Positions p on p.PositionID = ep.PositionID
	Join Departments d on d.DepartmentID = p.DepartmentID
	Where lr.StatusLR in ('Approved by Lead', 'Viewed by HR')  
	order by lr.LeaveRequestID
		offset (@pageNumber - 1)*@RowOfPage rows 
		fetch next @RowOfPage rows only 
end
go

-- select all Employee or select by id
create or alter proc selectEmpID
	@EmployeeID INT = null

as
begin

	if	@EmployeeID is not null
		SELECT *
		FROM Employees 
		WHERE EmployeeID = @EmployeeID ;
	else
		SELECT *
		FROM Employees ;
end
go

-- Change confirmation or rejection status
create or alter proc updateStatusLR
	@statusLR varchar(20) = null,
	@approveId int = null,
	@leaveRequestId int = null
	
as
begin
		UPDATE LeaveRequests
		SET StatusLR = @statusLR,
		ApprovalDate = GETDATE(),
		ApproverID = @approveId
		WHERE LeaveRequestID = @leaveRequestId;
end
go

-- Insert leaveType
create or alter proc insertleaveType
@leaveTypeName VARCHAR(100),
@leaveTypeDescription TEXT,
@leaveDaysPerYear int
as
begin
	Insert into LeaveTypes(LeaveTypeName,LeaveTypeDescription,LeaveDaysPerYear)
	values (@leaveTypeName,@leaveTypeDescription,@leaveDaysPerYear)
end
go


-- Update leaveType
create or alter proc updateleaveType
@leaveTypeName VARCHAR(100),
@leaveTypeDescription TEXT,
@leaveDaysPerYear int,
@id int
as
begin
		Update LeaveTypes
		set leaveTypeName =@leaveTypeName,
		LeaveTypeDescription = @leaveTypeDescription,
		LeaveDaysPerYear = @leaveDaysPerYear
		Where LeaveTypeID = @id
end
go




-- select count id all of statusLR in ('Approved by Lead', 'Rejected') for (leader) 
create or alter proc countLHforLeader
@empID int 
as
begin
	select count(LeaveRequestID) total from LeaveRequests lr
	Join Employee_positions ep on ep.EmployeeID = lr.EmployeeID
	Join Positions p on p.PositionID = ep.PositionID
	Join Departments d on d.DepartmentID = p.DepartmentID 

	where StatusLR in  ('Approved by Lead', 'Rejected') and d.DepartmentID = (SELECT d.DepartmentID
							FROM  Departments d
							Join Positions p  on p.DepartmentID = d.DepartmentID
							Join Employee_positions ep on ep.PositionID = p.PositionID
							WHERE ep.EmployeeID = @empID)
	
end
go

-- Phan trang cho gui dơn ('Approved by Lead', 'Rejected') của Leader
create or alter proc selLHistoryForLeader
@empID int ,
@pageNumber int,
@RowOfPage Int
as
begin
	Select  lr.*
	from LeaveRequests lr
	Join Employee_positions ep on ep.EmployeeID = lr.EmployeeID
	Join Positions p on p.PositionID = ep.PositionID
	Join Departments d on d.DepartmentID = p.DepartmentID
	Where lr.StatusLR in ('Approved by Lead', 'Rejected')  and  d.DepartmentID = (SELECT 
																						d.DepartmentID
																					FROM  Departments d
																					Join Positions p  on p.DepartmentID = d.DepartmentID
																					Join Employee_positions ep on ep.PositionID = p.PositionID
																					WHERE ep.EmployeeID = @empID)
	order by lr.LeaveRequestID
		offset (@pageNumber - 1)*@RowOfPage rows 
		fetch next @RowOfPage rows only 
end
go

-- select count id all of statusLR in ('Approved by HR', 'Rejected') for (Admin) 
create or alter proc countLHforAdmin
@empID int 
as
begin
	select count(LeaveRequestID) total from LeaveRequests lr
	Join Employee_positions ep on ep.EmployeeID = lr.EmployeeID
	Join Positions p on p.PositionID = ep.PositionID
	Join Departments d on d.DepartmentID = p.DepartmentID 

	where StatusLR in  ('Approved by HR', 'Rejected')
	
end
go

-- Phan trang cho gui dơn ('Approved by HR', 'Rejected') của Admin
create or alter proc selLHistoryForAdmin
@empID int ,
@pageNumber int,
@RowOfPage Int
as
begin
	Select  lr.*
	from LeaveRequests lr
	Join Employee_positions ep on ep.EmployeeID = lr.EmployeeID
	Join Positions p on p.PositionID = ep.PositionID
	Join Departments d on d.DepartmentID = p.DepartmentID
	Where lr.StatusLR in ('Approved by HR', 'Rejected') 
	order by lr.LeaveRequestID
		offset (@pageNumber - 1)*@RowOfPage rows 
		fetch next @RowOfPage rows only 
end
go

-- delete 1 row for table by id 

create or alter proc delete1rowLR
@Id int

as
begin
	DELETE FROM LeaveRequests WHERE LeaveRequestID = @Id;
end
go

--select all Leave Type 
create or alter proc getAllLeaveType
as
begin
	Select * from LeaveTypes
end
go

-- select Employee by DeparmentId and RoleName
create or alter proc selEmpbyIdDeparment
@IdDepar int,
@roleName nvarchar(15)
as
begin
	if @roleName is not null 
		SELECT e.*
		FROM Employees e
		JOIN Employee_positions ep ON e.EmployeeID = ep.EmployeeID
		JOIN Positions p ON ep.PositionID = p.PositionID
		JOIN EmployeeRoles er ON e.EmployeeID = er.EmployeeID
		Join Roles r on er.RoleID = r.RoleID
		WHERE p.DepartmentID = @IdDepar AND r.RoleName = @roleName
	else 
		SELECT e.*
		FROM Employees e
		JOIN Employee_positions ep ON e.EmployeeID = ep.EmployeeID
		JOIN Positions p ON ep.PositionID = p.PositionID
		JOIN EmployeeRoles er ON e.EmployeeID = er.EmployeeID
		Join Roles r on er.RoleID = r.RoleID
		WHERE p.DepartmentID = @IdDepar
end
go 

-- select employee role admin
create or alter proc selEmpAdmin
@roleName VARCHAR(50)
as
begin
	SELECT e.*
	FROM Employees e
	JOIN EmployeeRoles er ON e.EmployeeID = er.EmployeeID
	Join Roles r on er.RoleID = r.RoleID
	WHERE r.RoleName =  @roleName
end
go

-- Select all employees on leave this day
create or alter proc selEmpDay
as
begin
SELECT 
    StartDate, EndDate,EmployeeID,Reason,LeaveTypeID
FROM 
    LeaveRequests 
WHERE 
   StartDate <= GETDATE() 
    AND EndDate >= GETDATE() 
    AND StatusLR IN ('Approved by HR');

end
go

-- Select all employees on leave this week
create or alter proc selEmpWeek
as
begin
SELECT 
    StartDate, EndDate,EmployeeID,Reason,LeaveTypeID
FROM 
    LeaveRequests 
WHERE 
    DATEPART(week,StartDate) = DATEPART(week, GETDATE()) -- Lấy tuần hiện tại
    AND YEAR(StartDate) = YEAR(GETDATE()) -- Trong năm hiện tại
    AND StatusLR IN ('Approved by HR');

end
go

-- Select all employees on leave this month
create or alter proc selEmpMonth
as
begin
SELECT 
    StartDate, EndDate,EmployeeID,Reason,LeaveTypeID
FROM 
    LeaveRequests 
WHERE 
    DATEPART(MONTH,StartDate) = DATEPART(MONTH, GETDATE()) -- Lấy tuần hiện tại
    AND YEAR(StartDate) = YEAR(GETDATE()) -- Trong năm hiện tại
    AND StatusLR IN ('Approved by HR');

end
go



-- Count employee leave this week
create or alter proc countEmpWeek
as
begin
SELECT COUNT(DISTINCT employeeId) AS total
FROM LeaveRequests
WHERE DATEPART(YEAR, startDate) = DATEPART(YEAR, GETDATE())
AND DATEPART(WEEK, startDate) = DATEPART(WEEK, GETDATE())
And StatusLR IN ('Approved by HR');

end
go

--Count employee leave this month
create or alter proc countEmpMonth
as
begin
SELECT COUNT(DISTINCT employeeId) AS total
FROM LeaveRequests
WHERE DATEPART(YEAR, startDate) = DATEPART(YEAR, GETDATE())
AND DATEPART(MONTH, startDate) = DATEPART(MONTH, GETDATE())
And StatusLR IN ('Approved by HR');

end
go



--Count employee leave this month
create or alter proc countEmpDay
as
begin
SELECT COUNT(DISTINCT employeeId) AS total
FROM LeaveRequests
WHERE CAST(startDate AS DATE) = CAST(GETDATE() AS DATE)
And StatusLR IN ('Approved by HR');

end
go

-- select all Employee leave this day/month/year


create or alter proc GetEmployeesOnLeaveByDate
    @SelectedDate DATE
AS
BEGIN
    SELECT 
         StartDate, EndDate,EmployeeID,Reason,LeaveTypeID
    FROM 
        LeaveRequests
    WHERE 
        @SelectedDate BETWEEN LeaveRequests.StartDate AND LeaveRequests.EndDate;
END
go 

-- Insert data for table LeaveRequests 
create or alter proc InsertLeaveRequest
@employeeId int,
@leaveTypeId int,
@startDate Date,
@endDate Date,
@reason NVARCHAR(255),
@statusLR VARCHAR(20),
@leaveDuration VARCHAR(20)
AS
BEGIN
    INSERT INTO LeaveRequests (EmployeeID, LeaveTypeID, StartDate, EndDate, Reason, StatusLR, LeaveDuration)
	VALUES
	(@employeeId, @leaveTypeId, @startDate, @endDate, @reason, @statusLR, @leaveDuration)

	SELECT SCOPE_IDENTITY() AS LeaveRequestID;
END
go


--update leaveRequest by id
create or alter proc updateLeaveRequest

@leaveTypeId nvarchar(50), 
@startDate date,
@endDate date,
@reason nvarchar(250),
@LeaveRequestId int
as
begin
	Update LeaveRequests
	set LeaveTypeID = @leaveTypeId , StartDate = @startDate, EndDate = @endDate, Reason = @reason, SubmissionDate = GETDATE()
	where  LeaveRequestID = @LeaveRequestId
end
go

-- đém tất cả số ngày nghỉ của loại nghỉ đó đã dùng
create or alter proc selAllLeaveRequestByIdLT

@EmployeeID int ,
 @LeaveTypeID int
as
begin
	SELECT 
		ISNULL(SUM(DATEDIFF(DAY, StartDate, EndDate) + 1), 0) AS TotalUsedDays
	FROM 
		LeaveRequests
	WHERE 
		EmployeeID = @EmployeeID
		AND LeaveTypeID = @LeaveTypeID
		AND YEAR(StartDate) = YEAR(GETDATE())
		AND StatusLR IN ('Approved by HR');
end
go

-- lấy tất cả các đơn của những ngày tiếp theo
create or alter proc selAllLeaveRequestNextDay
@EmployeeID int
as
begin
	SELECT lr.EndDate, lr.StartDate
	FROM LeaveRequests lr
	JOIN Employees e ON lr.EmployeeID = e.EmployeeID
	JOIN LeaveTypes lt ON lr.LeaveTypeID = lt.LeaveTypeID
	WHERE lr.StartDate >= GETDATE() and e.EmployeeID = 5
	ORDER BY lr.StartDate;
end
go

-- insert leave Documents
create or alter proc InsertLeaveDocument
    @LeaveRequestID INT,
    @DocumentPath NVARCHAR(255)
AS
BEGIN
    INSERT INTO LeaveDocuments (LeaveRequestID, DocumentPath, UploadedDate, Status)
    VALUES (@LeaveRequestID, @DocumentPath, GETDATE(), 1);
END;
GO

-- insert Notification

create or alter proc InsertNotification
    @LeaveRequestID INT,
    @ReceiverID INT,
    @Message NVARCHAR(255)
AS
BEGIN
    INSERT INTO Notifications (LeaveRequestID, NotificationDate, ReceiverID, Message, Status)
    VALUES (@LeaveRequestID, GETDATE(), @ReceiverID, @Message, 0);
END;
GO
SELECT EmployeeID 
FROM Employees 
WHERE EmployeeID = ReceiverID;
-- select leave Documents by idLeaverequest
create or alter proc selLeaveDocument
    @LeaveRequestID INT
AS
BEGIN
    select * 
	from LeaveDocuments
	where LeaveRequestID = @LeaveRequestID
END;
GO

--update leave Documents by id
create or alter proc updateLeaveDocument
@documentPath nvarchar(255),
@LeaveRequestId int
as
begin
	Update LeaveDocuments
	set DocumentPath = @documentPath ,UploadedDate = GETDATE()
	where  LeaveRequestID = @LeaveRequestId
end
go


select * from LeaveRequests where EmployeeID = 5


CREATE PROCEDURE GetNotificationsByEmployee
    @EmployeeID INT
AS
BEGIN
    -- Select notifications for the specified employee
    SELECT NotificationID, LeaveRequestID, NotificationDate, ReceiverID, Message, Status
    FROM Notifications
    WHERE ReceiverID = @EmployeeID
    ORDER BY Status DESC, NotificationDate DESC;
END
EXEC GetNotificationsByEmployee @EmployeeID = 1;

SELECT * FROM Notifications WHERE ReceiverID = 1;














-------------------------------------------
-- Insert data into Departments
INSERT INTO Departments (DepartmentName, Status) VALUES 
('Human Resources', 1),
('Finance', 1),
('IT', 1),
('Marketing', 1),
('Sales', 1),
('Operations', 1),
('Customer Service', 1);

-- Insert data into Positions
INSERT INTO Positions (DepartmentID, PositionName, Status) VALUES 
(1, 'HR Manager', 1),
(2, 'Accountant', 1),
(3, 'IT Specialist', 1),
(4, 'Marketing Specialist', 1),
(5, 'Sales Representative', 1),
(6, 'Operations Manager', 1),
(7, 'Customer Service Representative', 1),
(1, 'Project Manager', 1),
(2, 'Software Engineer', 1),
(3, 'Data Analyst', 1),
(4, 'UX/UI Designer', 1),
(5, 'Quality Assurance', 1),
(6, 'DevOps Engineer', 1),
(7, 'Product Owner', 1),
(1, 'Business Analyst', 1),
(2, 'System Architect', 1),
(3, 'Database Administrator', 1);


-- Insert data into Employees
INSERT INTO Employees (FullName, Email, Password, Status) VALUES 
('John Doe', 'john.doe@example.com', 'password123', 1),
('Jane Smith', 'jane.smith@example.com', 'password123', 1),
('Robert Johnson', 'robert.johnson@example.com', 'password123', 1),
('Emily Davis', 'emily.davis@example.com', 'password123', 1),
('Michael Brown', 'michael.brown@example.com', 'password123', 1),
('Linda Williams', 'linda.williams@example.com', 'password123', 1),
('David Wilson', 'david.wilson@example.com', 'password123', 1),
('Alice Johnson', 'alice.johnson@example.com', 'passwordAlice', 1),
('Bob Smith', 'bob.smith@example.com', 'passwordBob', 1),
('Charlie Davis', 'charlie.davis@example.com', 'passwordCharlie', 1),
('Diana Brown', 'diana.brown@example.com', 'passwordDiana', 1),
('Ethan White', 'ethan.white@example.com', 'passwordEthan', 1),
('Fiona Green', 'fiona.green@example.com', 'passwordFiona', 1),
('George Black', 'george.black@example.com', 'passwordGeorge', 1),
('Hannah Lewis', 'hannah.lewis@example.com', 'passwordHannah', 1),
('Ian Clark', 'ian.clark@example.com', 'passwordIan', 1),
('Jane Wright', 'jane.wright@example.com', 'passwordJane', 1);

-- Insert data into Employee_positions
INSERT INTO Employee_positions (EmployeeID, PositionID) VALUES 
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 12),
(11, 13),
(12, 14),
(13, 16),
(14, 10),
(15, 11),
(16, 17),
(17, 15);


INSERT INTO Roles (RoleName, Status) VALUES 
('Admin', 1),
('Leader', 1),
('User', 1);

-- Insert data into EmployeeRoles
INSERT INTO EmployeeRoles (EmployeeID, RoleID, Status) VALUES 
(1, 1, 1), -- John Doe as Admin
(2, 2, 1), -- Jane Smith as Manager
(3, 3, 1), -- Robert Johnson as User
(4, 3, 1), -- Emily Davis as User
(5, 2, 1), -- Michael Brown as Manager
(6, 3, 1), -- Linda Williams as User
(7, 3, 1), -- David Wilson as User
(8, 3, 1), -- EmployeeID 1 với RoleID 1
(9, 2, 1), -- EmployeeID 2 với RoleID 2
(10, 3, 1), -- EmployeeID 3 với RoleID 3
(11, 3, 1), 
(12, 2, 1), 
(13, 3, 1), 
(14, 3, 1), 
(15, 2, 1), 
(16, 3, 1), 
(17, 3, 1);

-- find leader
CREATE OR ALTER PROCEDURE GetLeaderRole
AS
BEGIN
    -- Tìm kiếm thông tin của vai trò 'Leader'
    SELECT RoleID, RoleName, Status
    FROM Roles
    WHERE RoleName = 'Leader';
END;
GO
EXEC GetLeaderRole;

-- Insert data into EmployeeDetails
INSERT INTO EmployeeDetails (EmployeeID, Image, PhoneNumber, Address, DateOfBirth, Gender, Status) VALUES 
(1, NULL, '123-456-7890', '123 Main St, Cityville', '1990-01-01', 'Male', 1),
(2, NULL, '234-567-8901', '456 Elm St, Townsville', '1992-02-02', 'Female', 1),
(3, NULL, '345-678-9012', '789 Oak St, Villagetown', '1988-03-03', 'Male', 1),
(4, NULL, '456-789-0123', '101 Pine St, Hamletburg', '1995-04-04', 'Female', 1),
(5, NULL, '567-890-1234', '202 Maple St, Smallcity', '1985-05-05', 'Male', 1),
(6, NULL, '678-901-2345', '303 Cedar St, Bigcity', '1991-06-06', 'Female', 1),
(7, NULL, '789-012-3456', '404 Birch St, Capitaltown', '1993-07-07', 'Male', 1),
(8, 'image1.png', '1234567890', '123 Main St', '1985-01-01', 'Male', 1),
(9, 'image2.png', '2345678901', '456 Elm St', '1988-02-02', 'Female', 1),
(10, 'image3.png', '3456789012', '789 Maple St', '1990-03-03', 'Male', 1),
(11, 'image4.png', '4567890123', '101 Pine St', '1992-04-04', 'Female', 1),
(12, 'image5.png', '5678901234', '202 Oak St', '1994-05-05', 'Male', 1),
(13, 'image6.png', '6789012345', '303 Birch St', '1996-06-06', 'Female', 1),
(14, 'image7.png', '7890123456', '404 Cedar St', '1998-07-07', 'Male', 1),
(15, 'image8.png', '8901234567', '505 Walnut St', '2000-08-08', 'Female', 1),
(16, 'image9.png', '9012345678', '606 Poplar St', '1982-09-09', 'Male', 1),
(17, 'image10.png', '0123456789', '707 Ash St', '1984-10-10', 'Female', 1);



-- Insert data into LeaveTypes
INSERT INTO LeaveTypes (LeaveTypeName, LeaveTypeDescription, LeaveDaysPerYear, Status) VALUES 
('Annual Leave', 'Paid time off for vacations and personal use.', 15, 1),
('Sick Leave', 'Paid leave for medical reasons.', 10, 1),
('Maternity Leave', 'Leave for maternity purposes.', 90, 1),
('Paternity Leave', 'Leave for fathers after the birth of a child.', 14, 1),
('Unpaid Leave', 'Time off without pay.', 0, 1);

-- Insert data into LeaveRequests
INSERT INTO LeaveRequests (EmployeeID, LeaveTypeID, StartDate, EndDate, Reason, StatusLR, SubmissionDate, ApproverID, ApprovalDate, Status) VALUES 
(1, 1, '2024-08-01', '2024-08-10', 'Annual vacation', 'Submitted', '2024-07-01', 2, NULL, 1),
(2, 2, '2024-08-05', '2024-08-10', 'Medical issue', 'Viewed by Lead', '2024-07-02', 3, NULL, 1),
(3, 3, '2024-08-10', '2024-08-20', 'Maternity leave', 'Approved by Lead', '2024-07-03', 4, '2024-07-04', 1),
(4, 4, '2024-08-15', '2024-08-25', 'Paternity leave', 'Viewed by HR', '2024-07-04', 5, NULL, 1),
(5, 5, '2024-08-20', '2024-08-30', 'Unpaid leave', 'Approved by HR', '2024-07-05', 6, '2024-07-06', 1),
(6, 1, '2024-09-01', '2024-09-07', 'Annual vacation', 'Rejected', '2024-07-06', 7, NULL, 1),
(7, 2, '2024-09-10', '2024-09-15', 'Medical issue', 'Submitted', '2024-07-07', 1, NULL, 1),
(1, 3, '2024-09-15', '2024-09-30', 'Maternity leave', 'Viewed by Lead', '2024-07-08', 2, NULL, 1),
(2, 4, '2024-10-01', '2024-10-07', 'Paternity leave', 'Approved by Lead', '2024-07-09', 3, '2024-07-10', 1),
(3, 5, '2024-10-10', '2024-10-15', 'Unpaid leave', 'Viewed by HR', '2024-07-10', 4, NULL, 1),
(4, 1, '2024-10-15', '2024-10-20', 'Annual vacation', 'Approved by HR', '2024-07-11', 5, '2024-07-12', 1),
(5, 2, '2024-11-01', '2024-11-10', 'Medical issue', 'Rejected', '2024-07-12', 6, NULL, 1),
(6, 3, '2024-11-15', '2024-11-30', 'Maternity leave', 'Submitted', '2024-07-13', 7, NULL, 1),
(7, 4, '2024-12-01', '2024-12-07', 'Paternity leave', 'Viewed by Lead', '2024-07-14', 1, NULL, 1),
(1, 5, '2024-12-10', '2024-12-20', 'Unpaid leave', 'Approved by Lead', '2024-07-15', 2, '2024-07-16', 1),
(2, 1, '2024-12-15', '2024-12-22', 'Annual vacation', 'Viewed by HR', '2024-07-16', 3, NULL, 1),
(3, 2, '2024-12-20', '2024-12-31', 'Medical issue', 'Approved by HR', '2024-07-17', 4, '2024-07-18', 1),
(4, 3, '2024-12-25', '2024-12-30', 'Maternity leave', 'Rejected', '2024-07-18', 5, NULL, 1),
(5, 4, '2024-12-26', '2024-12-31', 'Paternity leave', 'Submitted', '2024-07-19', 6, NULL, 1),
(6, 5, '2024-12-27', '2024-12-31', 'Unpaid leave', 'Viewed by Lead', '2024-07-20', 7, NULL, 1),
(7, 1, '2024-12-28', '2024-12-31', 'Annual vacation', 'Approved by Lead', '2024-07-21', 1, '2024-07-22', 1),
(1, 2, '2025-01-01', '2025-01-07', 'Medical issue', 'Viewed by HR', '2024-07-22', 2, NULL, 1),
(2, 3, '2025-01-10', '2025-01-15', 'Maternity leave', 'Approved by HR', '2024-07-23', 3, '2024-07-24', 1),
(3, 4, '2025-01-15', '2025-01-20', 'Paternity leave', 'Rejected', '2024-07-24', 4, NULL, 1),
(4, 5, '2025-01-20', '2025-01-25', 'Unpaid leave', 'Submitted', '2024-07-25', 5, NULL, 1),
(5, 1, '2025-01-25', '2025-01-31', 'Annual vacation', 'Viewed by Lead', '2024-07-26', 6, NULL, 1),
(6, 2, '2025-02-01', '2025-02-07', 'Medical issue', 'Approved by Lead', '2024-07-27', 7, '2024-07-28', 1),
(7, 3, '2025-02-05', '2025-02-15', 'Maternity leave', 'Viewed by HR', '2024-07-28', 1, NULL, 1),
(8, 1, '2024-08-01', '2024-08-05', 'Family vacation', 'Approved by Lead', '2024-07-25', 2, '2024-07-26', 1),
(9, 2, '2024-08-10', '2024-08-15', 'Medical leave', 'Approved by HR', '2024-07-30', 3, '2024-08-01', 1),
(10, 3, '2024-09-01', '2024-09-10', 'Training', 'Submitted', '2024-07-31', NULL, NULL, 1),
(11, 4, '2024-10-01', '2024-10-05', 'Conference', 'Viewed by Lead', '2024-08-01', 5, NULL, 1),
(12, 5, '2024-11-01', '2024-11-03', 'Family emergency', 'Rejected', '2024-08-10', 6, '2024-08-11', 1),
(13, 1, '2024-12-01', '2024-12-05', 'Vacation', 'Approved by HR', '2024-08-15', 2, '2024-08-16', 1),
(14, 2, '2024-09-20', '2024-09-22', 'Doctor appointment', 'Approved by Lead', '2024-08-20', 1, '2024-08-21', 1),
(15, 3, '2024-10-20', '2024-10-25', 'Study leave', 'Submitted', '2024-08-25', NULL, NULL, 1),
(16, 4, '2024-11-20', '2024-11-25', 'Project work', 'Viewed by HR', '2024-08-30', 3, NULL, 1),
(17, 5, '2024-12-10', '2024-12-15', 'Personal reasons', 'Approved by Lead', '2024-09-01', 4, '2024-09-02', 1);


-- Select all data from Departments
SELECT * FROM Departments;

-- Select all data from Positions
SELECT * FROM Positions;

-- Select all data from Employees
SELECT * FROM Employees;

-- Select all data from Employee_positions
SELECT * FROM Employee_positions;

-- Select all data from Roles
SELECT * FROM Roles;

-- Select all data from EmployeeRoles
SELECT * FROM EmployeeRoles;

-- Select all data from EmployeeDetails
SELECT * FROM EmployeeDetails;

-- Select all data from LeaveTypes
SELECT * FROM LeaveTypes;

-- Select all data from LeaveRequests
SELECT * FROM LeaveRequests;


-- utility

create or alter PROCEDURE usp_InsertEmployee
	@fullName varchar(100),
	@email varchar(100),
	@password nvarchar(255),
	@positionName varchar(100),
	@departmentName varchar(100),
	@roleName varchar(50),

	@address varchar(255),
	@phoneNumber varchar(15),
	@dob date,
	@gender varchar(10),


	@newEmployeeID INT OUTPUT
AS
BEGIN

	IF EXISTS (SELECT 1 FROM Employees e WHERE e.Email = @email)
		BEGIN
			return -1;
		END
	ELSE
		BEGIN

			DECLARE @FoundRoleID INT;
			DECLARE @FoundDepartmentID INT;
			DECLARE @FoundPositionID INT;



			-- Validate existing roles
			SELECT @FoundRoleID = r.RoleID
			FROM Roles r WHERE r.RoleName = @roleName;
			IF @FoundRoleID IS NULL
			BEGIN
				PRINT '@roleName ' + @roleName + ' not found!';
				return -1;
			END

			-- Validate existing departments
			SELECT @FoundDepartmentID = d.DepartmentID
			FROM Departments d WHERE d.DepartmentName = @departmentName;
			IF @FoundDepartmentID IS NULL
			BEGIN
				PRINT '@departmentName ' + @departmentName + ' not found!';
				return -1;
			END

			-- Validate existings position
			SELECT @FoundPositionID = p.PositionID
			FROM Positions p  WHERE p.PositionName = @positionName and p.DepartmentID = @FoundDepartmentID;
			IF @FoundPositionID IS NULL
			BEGIN
				PRINT '@positionName ' + @positionName + ' not found!';
				return -1;
			END

			BEGIN TRY
				BEGIN TRANSACTION;

				INSERT INTO Employees (FullName, Email, Password, Status)
				VALUES(@fullName, @email, @password, 1);
				SET @newEmployeeID = SCOPE_IDENTITY();

				INSERT INTO EmployeeRoles (EmployeeID, RoleID)
				VALUES(@newEmployeeID, @FoundRoleID);

				INSERT INTO Employee_positions (EmployeeID, PositionID)
				VALUES(@newEmployeeID, @FoundPositionID);

				INSERT INTO EmployeeDetails (EmployeeID, PhoneNumber, Address, DateOfBirth, Gender)
				VALUES (@newEmployeeID, @phoneNumber, @address, @dob, @gender);

				COMMIT TRANSACTION;
			END TRY
			BEGIN CATCH
		       ROLLBACK TRANSACTION;
		       PRINT ERROR_MESSAGE();
		       return -1;
			END CATCH

		END

END;


BEGIN
	DECLARE @newEmployeeID int;
	DECLARE @returnCode int;
	EXEC @returnCode = usp_InsertEmployee
	    @fullName = 'Nguyen Thuy Huy',
	    @Email = 'huy.nguyen@gmail.com',
	    @password = 'password123',
	    @positionName = 'DevOps Engineer',
	    @departmentName = 'Operations',
	    @roleName = 'User',
	    @address = '2 Tran Khanh Du',
	    @phoneNumber = '0938648184',
	    @dob = '1997-05-10',
	    @gender = 'Male',
	    @newEmployeeID = @newEmployeeID OUTPUT;

	PRINT '@returnCode = ' + CAST(@returnCode as VARCHAR);

	PRINT 'EMPLOYEE ID = ' + CAST(@newEmployeeID as VARCHAR);
END;
